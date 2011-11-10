package fi.jawsy.jawscala
package zk
package gritter

import org.specs2.mutable._

class GritterSpec extends Specification {

  "A Gritter notification" should {

    "require only title and text attributes" in {
      val n = Gritter.Notification("Title", "Text")
      n.toJSONString must be_==("{\"title\" : \"Title\", \"text\" : \"Text\"}")
    }
    /* Impossible to test without proper ZK execution scope
    "support image attribute" in {}
    */
    "support sticky attribute" in {
      val n = Gritter.Notification("Title", "Text", sticky = true)
      n.toJSONString must be_==("{\"title\" : \"Title\", \"text\" : \"Text\", \"sticky\" : true}")
    }
    "support time attribute" in {
      val n = Gritter.Notification("Title", "Text", time = 1)
      n.toJSONString must be_==("{\"title\" : \"Title\", \"text\" : \"Text\", \"time\" : 1}")
    }
    "support class_name attribute" in {
      val n = Gritter.Notification("Title", "Text", sclass = "clz")
      n.toJSONString must be_==("{\"title\" : \"Title\", \"text\" : \"Text\", \"class_name\" : \"clz\"}")
    }

  }

}
