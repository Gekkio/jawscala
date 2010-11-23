package fi.jawsy.jawscala
package vaadin

import com.vaadin.ui.PopupView

object PopupViews {

  def popupVisibilityListener(f: (PopupView#PopupVisibilityEvent) => _) = new PopupView.PopupVisibilityListener {
    def popupVisibilityChange(e: PopupView#PopupVisibilityEvent) = f(e)
  }

  object Implicits extends Implicits

  trait Implicits {
    implicit def popupViewPopupVisibilityListener = popupVisibilityListener _
  }

}
