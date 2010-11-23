package fi.jawsy.jawscala
package vaadin

import com.vaadin.ui.Button

object Buttons {

  def clickListener(f: (Button#ClickEvent) => _) = new Button.ClickListener {
    def buttonClick(e: Button#ClickEvent) = f(e)
  }

  trait Implicits {
    implicit def buttonClickListener = clickListener _
  }

}
