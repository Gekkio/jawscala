package fi.jawsy.jawscala.zk

import org.zkoss.zk.ui.ext.Scope

class ScopeAttribute[T](name: String) {

  def this(clazz: Class[_], name: String) {
    this(clazz.getName + "." + name)
  }

  def apply(s: Scope) = Option(s.getAttribute(name).asInstanceOf[T])
  def update(s: Scope, value: T) = s.setAttribute(name, value)

}
