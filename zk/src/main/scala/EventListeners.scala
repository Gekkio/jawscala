package fi.jawsy.jawscala
package zk

import org.zkoss.zk.ui.Component
import org.zkoss.zk.ui.event.{ Event, EventListener }

object EventListeners {

  implicit def function1EventListener[E <: Event](f: (E) => _) = new EventListener {
    def onEvent(e: Event) = f(e.asInstanceOf[E])
  }

  def listenTo[E <: Event](c: Component, name: String)(f: (E) => _) = {
    val el = (e: E) => f(e)
    c.addEventListener(name, el)
    el
  }

}
