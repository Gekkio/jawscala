package fi.jawsy.jawscala
package zk
package async

import org.atmosphere.cpr.AtmosphereResource
import org.slf4j.LoggerFactory
import org.zkoss.zk.au.out.AuScript
import org.zkoss.zk.ui.event.{ EventListener, Event }
import org.zkoss.zk.ui.sys.{ ServerPush, Scheduler }
import org.zkoss.zk.ui.util.Clients
import org.zkoss.zk.ui.Desktop

import javax.servlet.http.{ HttpServletResponse, HttpServletRequest }

object AtmosphereServerPush {
  val timeout = 300000
}

class AtmosphereServerPush extends ServerPush {

  private val log = LoggerFactory.getLogger(this.getClass)

  @volatile
  private var desktop: Option[Desktop] = None
  @volatile
  private var resource: Option[AtmosphereResource[HttpServletRequest, HttpServletResponse]] = None

  def updateResource(resource: AtmosphereResource[HttpServletRequest, HttpServletResponse]) {
    if (!this.resource.isDefined) {
      resource.suspend(AtmosphereServerPush.timeout, false)
      this.resource = Some(resource)
    }
  }

  def onPiggyback() {
  }
  def activate(timeout: Long): Boolean = {
    throw new UnsupportedOperationException("activate is not supported")
  }
  def deactivate(stop: Boolean): Boolean = {
    throw new UnsupportedOperationException("deactivate is not supported")
  }
  def isActive = {
    throw new UnsupportedOperationException("isActive is not supported")
  }

  def schedule(task: EventListener, event: Event, scheduler: Scheduler) {
    scheduler.schedule(task, event)
    for {
      desktop <- this.desktop
      resource <- this.resource
    } {
      resource.resume()
      this.resource = None
    }
  }

  def setDelay(min: Int, max: Int, factor: Int) {
    throw new UnsupportedOperationException("setDelay is not supported")
  }

  def start(desktop: Desktop) {
    this.desktop = Option(desktop)
    for (desktop <- this.desktop) {
      log.debug("Starting server push for " + desktop)
      Clients.response("jawscala.serverpush",
        new AuScript(null, "jawscala.startServerPush('" + desktop.getId() + "');"));
    }
  }
  def stop() {
    for (desktop <- this.desktop) {
      log.debug("Stopping server push for " + desktop)
      Clients.response("jawscala.serverpush", new AuScript(null, "jawscala.stopServerPush('" + desktop.getId() + "', " + AtmosphereServerPush.timeout + ");"));
      this.desktop = None
    }
  }

}