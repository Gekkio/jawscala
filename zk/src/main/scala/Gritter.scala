package fi.jawsy.jawscala
package zk

import net.liftweb.json.Implicits._
import net.liftweb.json.JsonAST._
import net.liftweb.json.JsonDSL._
import org.zkoss.json.JSONAware
import org.zkoss.zk.ui.Executions
import org.zkoss.zk.ui.util.Clients

object Gritter {

  private val DefaultTime = 6000

  case class Notification(
    title: String,
    text: String,
    image: String = "",
    sticky: Boolean = false,
    time: Int = DefaultTime,
    sclass: String = ""
  ) extends JSONAware {
    def toJSONString = compact(render(
      ("title", title) ~
      ("text", text) ~
      ("image", Option(image).filter(!_.isEmpty).map(Executions.encodeURL)) ~
      ("sticky", Option(sticky).filter(_ == true)) ~
      ("time", Option(time).filter(_ != DefaultTime)) ~
      ("class_name", Option(sclass).filter(!_.isEmpty))
    ))
  }

  def add(title: String, text: String, image: String, sticky: Boolean = false, time: Int = DefaultTime, sclass: String = ""): Unit = add(Notification(title, text, image, sticky, time, sclass))

  def add(n: Notification): Unit = Clients.evalJavaScript("jq.gritter.add(" + n.toJSONString() + ")")

  def removeAll(): Unit = Clients.evalJavaScript("jq.gritter.removeAll()")

}
