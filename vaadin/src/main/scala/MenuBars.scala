package fi.jawsy.jawscala
package vaadin

import com.vaadin.ui.MenuBar

object MenuBars {

  def command(f: (MenuBar#MenuItem) => _) = new MenuBar.Command {
    def menuSelected(selectedItem: MenuBar#MenuItem) = f(selectedItem)
  }

}
