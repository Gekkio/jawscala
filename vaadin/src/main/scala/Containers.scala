package fi.jawsy.jawscala
package vaadin

import com.vaadin.data.Container

object Containers {

  def itemSetChangeListener(f: (Container.ItemSetChangeEvent) => _) = new Container.ItemSetChangeListener {
    def containerItemSetChange(e: Container.ItemSetChangeEvent) = f(e)
  }

  def propertySetChangeListener(f: (Container.PropertySetChangeEvent) => _) = new Container.PropertySetChangeListener {
    def containerPropertySetChange(e: Container.PropertySetChangeEvent) = f(e)
  }

  object Implicits extends Implicits

  trait Implicits {
    implicit def containerItemSetChangeListener = itemSetChangeListener _
    implicit def containerPropertySetChangeListener = propertySetChangeListener _
  }

}
