package fi.jawsy.jawscala
package vaadin

import com.vaadin.ui.Component

object Components {

  def componentErrorListener(f: (Component.ErrorEvent) => _) = new Component.ErrorListener {
    def componentError(e: Component.ErrorEvent) = f(e)
  }

  def componentListener(f: (Component.Event) => _) = new Component.Listener {
    def componentEvent(e: Component.Event) = f(e)
  }

  object Implicits extends Implicits

  trait Implicits {
    implicit def componentComponentErrorListener = componentErrorListener _
    implicit def componentComponentListener = componentListener _
  }

}
