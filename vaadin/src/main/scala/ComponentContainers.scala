package fi.jawsy.jawscala
package vaadin

import com.vaadin.ui.ComponentContainer

object ComponentContainers {

  def componentAttachListener(f: (ComponentContainer.ComponentAttachEvent) => _) = new ComponentContainer.ComponentAttachListener {
    def componentAttachedToContainer(e: ComponentContainer.ComponentAttachEvent) = f(e)
  }

  def componentDetachListener(f: (ComponentContainer.ComponentDetachEvent) => _) = new ComponentContainer.ComponentDetachListener {
    def componentDetachedFromContainer(e: ComponentContainer.ComponentDetachEvent) = f(e)
  }

  trait Implicits {
    implicit def componentContainerComponentAttachListener = componentAttachListener _
    implicit def componentContainerComponentDetachListener = componentDetachListener _
  }

}
