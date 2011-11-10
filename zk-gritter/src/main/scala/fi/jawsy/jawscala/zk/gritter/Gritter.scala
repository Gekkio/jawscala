package fi.jawsy.jawscala
package zk
package gritter

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
    def toJSONString = {
      import dispatch.json._
      JsObject(
        List(
          "title" -> Some(title).map(JsValue.apply),
          "text" -> Some(text).map(JsValue.apply),
          "image" -> Option(image).filter(!_.isEmpty).map(Executions.encodeURL).map(JsValue.apply),
          "sticky" -> Option(sticky).filter(_ == true).map(JsValue.apply),
          "time" -> Option(time).filter(_ != DefaultTime).map(JsValue.apply),
          "class_name" -> Option(sclass).filter(!_.isEmpty).map(JsValue.apply)
        ).flatten { entry =>
          val (key, valueOption) = entry
          for (value <- valueOption) yield (JsString(key), value)
        }
      ).toString
    }
  }

  def add(title: String, text: String, image: String, sticky: Boolean = false, time: Int = DefaultTime, sclass: String = ""): Unit = add(Notification(title, text, image, sticky, time, sclass))

  def add(n: Notification): Unit = Clients.evalJavaScript("jq.gritter.add(" + n.toJSONString() + ")")

  def removeAll(): Unit = Clients.evalJavaScript("jq.gritter.removeAll()")

}
