(function() {
  jawscala.startServerPush = function(dtid, timeout) {
    var dt = zk.Desktop.$(dtid);
    if (dt._serverpush)
      dt._serverpush.stop();

    var spush = new jawscala.ServerPush(dt, timeout);
    spush.start();
  };
  jawscala.stopServerPush = function(dtid) {
    var dt = zk.Desktop.$(dtid);
    if (dt._serverpush)
      dt._serverpush.stop();
  };
  jawscala.ServerPush = zk.$extends(zk.Object, {
    desktop: null,
    active: false,
    timeout: 300000,

    $init: function(desktop, timeout) {
      this.desktop = desktop;
      this.timeout = timeout;
    },
    _send: function() {
      if (!this.active)
        return;

      var me = this;
      var jqxhr = $.ajax({
        url: zk.ajaxURI("/comet", { au: true }),
        type: "GET",
        cache: false,
        async: true,
        global: false,
        data: {
          dtid: this.desktop.id
        },
        accepts: "text/plain",
        dataType: "text/plain",
        timeout: me.timeout,
        error: function(jqxhr, textStatus, errorThrown) {
          setTimeout(me.proxy(me._send), 1000);
        },
        success: function(data) {
          zAu.cmd0.echo(me.desktop);
          setTimeout(me.proxy(me._send), 1000);
        }
      });
      this._req = jqxhr;
    },
    start: function() {
      this.desktop._serverpush = this;
      this.active = true;
      this._send();
    },
    stop: function() {
      this.desktop._serverpush = null;
      this.active = false;
      if (this._req) {
        this._req.abort();
        this._req = null;
      }
    }
  });
})();
