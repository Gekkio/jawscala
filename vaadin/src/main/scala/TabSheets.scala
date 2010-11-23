package fi.jawsy.jawscala
package vaadin

import com.vaadin.ui.{ TabSheet, Component }

object TabSheets {

  def selectedTabChangeListener(f: (TabSheet#SelectedTabChangeEvent) => _) = new TabSheet.SelectedTabChangeListener {
    def selectedTabChange(e: TabSheet#SelectedTabChangeEvent) = f(e)
  }

  def closeHandler(f: (TabSheet, Component) => _) = new TabSheet.CloseHandler {
    def onTabClose(tabSheet: TabSheet, tabContent: Component) = f(tabSheet, tabContent)
  }

  object Implicits extends Implicits

  trait Implicits {
    implicit def tabSheetSelectedTabChangeListener = selectedTabChangeListener _
  }

}
