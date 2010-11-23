package fi.jawsy.jawscala
package vaadin

import com.vaadin.event.Action

object Actions {

  def listener(f: (AnyRef, AnyRef) => _) = new Action.Listener {
    def handleAction(sender: AnyRef, target: AnyRef) = f(sender, target)
  }

}
