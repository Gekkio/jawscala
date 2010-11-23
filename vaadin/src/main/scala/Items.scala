package fi.jawsy.jawscala
package vaadin

import com.vaadin.data.Item

object Items {

  def propertySetChangeListener(f: (Item.PropertySetChangeEvent) => _) = new Item.PropertySetChangeListener {
    def itemPropertySetChange(e: Item.PropertySetChangeEvent) = f(e)
  }

  object Implicits extends Implicits

  trait Implicits {
    implicit def itemPropertySetChangeListener = propertySetChangeListener _
  }

}
