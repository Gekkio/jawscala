package fi.jawsy.jawscala
package vaadin

import com.vaadin.ui.SplitPanel

object SplitPanels {

  def splitterClickListener(f: (SplitPanel#SplitterClickEvent) => _) = new SplitPanel.SplitterClickListener {
    def splitterClick(e: SplitPanel#SplitterClickEvent) = f(e)
  }

  object Implicits extends Implicits

  trait Implicits {
    implicit def splitPanelSplitterClickListener = splitterClickListener _
  }

}
