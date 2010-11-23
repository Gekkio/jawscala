package fi.jawsy.jawscala
package vaadin

import com.vaadin.event.LayoutEvents

object LayoutEventsUtil {

  def layoutClickListener(f: (LayoutEvents.LayoutClickEvent) => _) = new LayoutEvents.LayoutClickListener {
    def layoutClick(e: LayoutEvents.LayoutClickEvent) = f(e)
  }

  object Implicits extends Implicits

  trait Implicits {
    implicit def layoutEventsLayoutClickListener = layoutClickListener _
  }

}
