package fi.jawsy.jawscala
package vaadin

import com.vaadin.data.Property

object Properties {

  def readOnlyStatusChangeListener(f: (Property.ReadOnlyStatusChangeEvent) => _) = new Property.ReadOnlyStatusChangeListener {
    def readOnlyStatusChange(e: Property.ReadOnlyStatusChangeEvent) = f(e)
  }

  def valueChangeListener(f: (Property.ValueChangeEvent) => _) = new Property.ValueChangeListener {
    def valueChange(e: Property.ValueChangeEvent) = f(e)
  }

  object Implicits extends Implicits

  trait Implicits {
    implicit def propertyReadOnlyStatusChangeListener = readOnlyStatusChangeListener _
    implicit def propertyValueChangeListener = valueChangeListener _
  }

}
