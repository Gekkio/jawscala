package fi.jawsy.jawscala
package vaadin

import com.vaadin.ui.{ Table, Component }

object Tables {

  def cellStyleGenerator(f: (AnyRef, AnyRef) => String) = new Table.CellStyleGenerator {
    def getStyle(itemId: AnyRef, propertyId: AnyRef) = f(itemId, propertyId)
  }

  def columnGenerator(f: (Table, AnyRef, AnyRef) => Component) = new Table.ColumnGenerator {
    def generateCell(source: Table, itemId: AnyRef, columnId: AnyRef) = f(source, itemId, columnId)
  }

  def columnResizeListener(f: (Table.ColumnResizeEvent) => _) = new Table.ColumnResizeListener {
    def columnResize(e: Table.ColumnResizeEvent) = f(e)
  }

  def footerClickListener(f: (Table.FooterClickEvent) => _) = new Table.FooterClickListener {
    def footerClick(e: Table.FooterClickEvent) = f(e)
  }

  def headerClickListener(f: (Table.HeaderClickEvent) => _) = new Table.HeaderClickListener {
    def headerClick(e: Table.HeaderClickEvent) = f(e)
  }

  object Implicits extends Implicits

  trait Implicits {
    implicit def tableColumnResizeListener = columnResizeListener _
    implicit def tableFooterClickListener = footerClickListener _
    implicit def tableHeaderClickListener = headerClickListener _
  }

}
