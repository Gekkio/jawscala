package fi.jawsy.jawscala.zk

import org.zkoss.zk.ui.util.UiLifeCycle
import org.zkoss.zk.ui.Page
import org.zkoss.zk.ui.Component
import org.zkoss.zk.ui.Desktop
import scala.annotation.tailrec
import scala.collection.JavaConversions._

object LifeCycleHooks {

  val ComponentAttachAttribute = new ScopeAttribute[List[() => Unit]](classOf[LifeCycleHooks], "ComponentAttach")
  val ComponentDetachAttribute = new ScopeAttribute[List[() => Unit]](classOf[LifeCycleHooks], "ComponentDetach")

  def onComponentAttach(c: Component, f: () => Unit) = ComponentAttachAttribute(c) = f :: ComponentAttachAttribute(c).getOrElse(Nil)
  def onComponentDetach(c: Component, f: () => Unit) = ComponentDetachAttribute(c) = f :: ComponentDetachAttribute(c).getOrElse(Nil)

}

class LifeCycleHooks extends UiLifeCycle {

  import LifeCycleHooks._

  private def processComponentAttachListeners(c: Component) {
    for (
      attr <- ComponentAttachAttribute(c);
      function <- attr
    ) {
      function()
    }
  }

  @tailrec
  private def processComponentAttachListeners(c: List[Component]) {
    c match {
      case Nil =>
      case head :: Nil => {
        processComponentAttachListeners(head)
        processComponentAttachListeners(head.getChildren.asInstanceOf[java.util.List[Component]].toList)
      }
      case head :: tail => {
        processComponentAttachListeners(head)
        processComponentAttachListeners(head.getChildren.asInstanceOf[java.util.List[Component]].toList ::: tail)
      }
    }
  }

  private def processComponentDetachListeners(c: Component) {
    for (
      attr <- ComponentDetachAttribute(c);
      function <- attr
    ) {
      function()
    }
  }

  @tailrec
  private def processComponentDetachListeners(c: List[Component]) {
    c match {
      case Nil =>
      case head :: Nil => {
        processComponentDetachListeners(head)
        processComponentDetachListeners(head.getChildren.asInstanceOf[java.util.List[Component]].toList)
      }
      case head :: tail => {
        processComponentDetachListeners(head)
        processComponentDetachListeners(head.getChildren.asInstanceOf[java.util.List[Component]].toList ::: tail)
      }
    }
  }

  def afterComponentAttached(comp: Component, page: Page) {
    processComponentAttachListeners(comp)
  }

  def afterComponentDetached(comp: Component, page: Page) {
    processComponentDetachListeners(comp)
  }

  def afterComponentMoved(parent: Component, child: Component, previousParent: Component) {
  }

  def afterPageAttached(page: Page, desktop: Desktop) {
  }

  def afterPageDetached(page: Page, desktop: Desktop) {
  }

}
