package fi.jawsy.jawscala
package vaadin

import com.vaadin.ui.Window

object Windows {

  def closeListener(f: (Window#CloseEvent) => _) = new Window.CloseListener {
    def windowClose(e: Window#CloseEvent) = f(e)
  }

  def resizeListener(f: (Window#ResizeEvent) => _) = new Window.ResizeListener {
    def windowResized(e: Window#ResizeEvent) = f(e)
  }

  object Implicits extends Implicits

  trait Implicits {
    implicit def windowCloseListener = closeListener _
    implicit def windowResizeListener = resizeListener _
  }

}
