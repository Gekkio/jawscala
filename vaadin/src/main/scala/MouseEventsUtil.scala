package fi.jawsy.jawscala
package vaadin

import com.vaadin.event.MouseEvents

object MouseEventsUtil {

  def clickListener(f: (MouseEvents.ClickEvent) => _) = new MouseEvents.ClickListener {
    def click(e: MouseEvents.ClickEvent) = f(e)
  }

  def doubleClickListener(f: (MouseEvents.DoubleClickEvent) => _) = new MouseEvents.DoubleClickListener {
    def doubleClick(e: MouseEvents.DoubleClickEvent) = f(e)
  }

  object Implicits extends Implicits

  trait Implicits {
    implicit def mouseEventsClickListener = clickListener _
    implicit def mouseEventsDoubleClickListener = doubleClickListener _
  }

}
