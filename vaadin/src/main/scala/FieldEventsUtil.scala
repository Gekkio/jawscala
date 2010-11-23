package fi.jawsy.jawscala
package vaadin

import com.vaadin.event.FieldEvents

object FieldEventsUtil {

  def blurListener(f: (FieldEvents.BlurEvent) => _) = new FieldEvents.BlurListener {
    def blur(e: FieldEvents.BlurEvent) = f(e)
  }

  def focusListener(f: (FieldEvents.FocusEvent) => _) = new FieldEvents.FocusListener {
    def focus(e: FieldEvents.FocusEvent) = f(e)
  }

  object Implicits extends Implicits

  trait Implicits {
    implicit def fieldEventsBlurListener = blurListener _
    implicit def fieldEventsFocusListener = focusListener _
  }

}
