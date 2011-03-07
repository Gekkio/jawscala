package fi.jawsy.jawscala
package vaadin

import com.vaadin.ui.AbstractSplitPanel

object SplitPanels {

  def splitterClickListener(f: (AbstractSplitPanel#SplitterClickEvent) => _) = new AbstractSplitPanel.SplitterClickListener {
    def splitterClick(e: AbstractSplitPanel#SplitterClickEvent) = f(e)
  }

  object Implicits extends Implicits

  trait Implicits {
    implicit def splitPanelSplitterClickListener = splitterClickListener _
  }

}
