package fi.jawsy.jawscala
package vaadin

import com.vaadin.ui.Tree

object Trees {

  def collapseListener(f: (Tree#CollapseEvent) => _) = new Tree.CollapseListener {
    def nodeCollapse(e: Tree#CollapseEvent) = f(e)
  }

  def expandListener(f: (Tree#ExpandEvent) => _) = new Tree.ExpandListener {
    def nodeExpand(e: Tree#ExpandEvent) = f(e)
  }

  def treeItemStyleGenerator(f: (AnyRef) => String) = new Tree.ItemStyleGenerator {
    def getStyle(itemId: AnyRef) = f(itemId)
  }

  object Implicits extends Implicits

  trait Implicits {
    implicit def treeCollapseListener = collapseListener _
    implicit def treeExpandListener = expandListener _
  }

}
