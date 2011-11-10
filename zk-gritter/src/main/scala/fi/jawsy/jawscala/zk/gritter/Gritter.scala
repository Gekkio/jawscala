package fi.jawsy.jawscala
package zk
package gritter

import sjson.json._
import JsonSerialization._
import org.zkoss.json.JSONAware
import org.zkoss.zk.ui.Executions
import org.zkoss.zk.ui.util.Clients

object Gritter {

  private val DefaultTime = 6000

  implicit object NotificationWrites extends Writes[Notification] {
    import dispatch.json._
    import DefaultProtocol._
    def writes(n: Notification): JsValue = {
      JsObject(
        List[(JsString, Option[JsValue])](
          ( JsString("title"), Some(tojson(n.title)) ),
          ( JsString("text"), Some(tojson(n.text)) ),
          ( JsString("image"), Option(n.image).filter(!_.isEmpty).map(Executions.encodeURL).map(tojson[String]) ),
          ( JsString("sticky"), Option(n.sticky).filter(_ == true).map(tojson[Boolean]) ),
          ( JsString("time"), Option(n.time).filter(_ != DefaultTime).map(tojson[Int]) ),
          ( JsString("class_name"), Option(n.sclass).filter(!_.isEmpty).map(tojson[String]) )
        ).flatten { x =>
          for (v <- x._2) yield (x._1, v)
        }
      )
    }
  }

  case class Notification(
    title: String,
    text: String,
    image: String = "",
    sticky: Boolean = false,
    time: Int = DefaultTime,
    sclass: String = ""
  ) extends JSONAware {
    def toJSONString = {
      tojson(this).toString
    }
  }

  def add(title: String, text: String, image: String, sticky: Boolean = false, time: Int = DefaultTime, sclass: String = ""): Unit = add(Notification(title, text, image, sticky, time, sclass))

  def add(n: Notification): Unit = Clients.evalJavaScript("jq.gritter.add(" + n.toJSONString() + ")")

  def removeAll(): Unit = Clients.evalJavaScript("jq.gritter.removeAll()")

}
