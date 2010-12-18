package fi.jawsy.jawscala
package zk

import org.zkoss.zk.ui.Component
import org.zkoss.zk.ui.event.{ Event, EventListener }
import EventListeners.function1EventListener

object ComponentBinding {

  trait ReadBinder[C <: Component, T] {
    val readEventListener: EventListener
    def read: Unit
  }

  trait WriteBinder[C <: Component, T] {
    val writeEventListener: EventListener
    def write: Unit
  }

  trait ReadWriteBinder[C <: Component, T] extends ReadBinder[C, T] with WriteBinder[C, T]

  trait Read[C <: Component, T] {
    val readEvents: List[String]
    def setter: (C) => (T) => _
  }

  object Read {
    def apply[C <: Component, T](re: List[String], r: (C) => (T) => Unit) = new Read[C, T] {
      val readEvents = re
      def setter = r
    }
  }

  trait Write[C <: Component, T] {
    val writeEvents: List[String]
    def getter: (C) => T
  }

  object Write {
    def apply[C <: Component, T](we: List[String], w: (C) => (T)) = new Write[C, T] {
      val writeEvents = we
      def getter = w
    }
  }

  case class ReadWrite[C <: Component, T](readEvents: List[String], getter: (C) => T, writeEvents: List[String], setter: (C) => (T) => Unit) extends Read[C, T] with Write[C, T]

  def bind[C <: Component, T](c: C, readFunction: => T)(implicit r: Read[C, T]): ReadBinder[C, T] = new ReadBinder[C, T] {
    val readEventListener: EventListener = (e: Event) => read
    r.readEvents.foreach(c.addEventListener(_, readEventListener))

    def read {
      r.setter(c)(readFunction)
    }
  }

  def bind[C <: Component, T](c: C, writeFunction: (T) => _)(implicit w: Write[C, T]): WriteBinder[C, T] = new WriteBinder[C, T] {
    val writeEventListener: EventListener = (e: Event) => write
    w.writeEvents.foreach(c.addEventListener(_, writeEventListener))

    def write {
      writeFunction(w.getter(c))
    }
  }

  def bind[C <: Component, T](c: C, readFunction: => T, writeFunction: (T) => _)(implicit r: Read[C, T], w: Write[C, T]): ReadWriteBinder[C, T] = new ReadWriteBinder[C, T] {
    private val readBinder = bind(c, readFunction)(r)
    private val writeBinder = bind(c, writeFunction)(w)

    val readEventListener = readBinder.readEventListener
    val writeEventListener = writeBinder.writeEventListener
    def read = readBinder.read
    def write = writeBinder.write
  }

  object Defaults {
    import org.zkoss.zul._
    import org.zkoss.zk.ui.event.Events._
    implicit val CheckboxReadWrite = ReadWrite[Checkbox, Boolean](Nil, _.isChecked, ON_CHECK :: Nil, _.setChecked)
    implicit val DateboxReadWrite = ReadWrite[Datebox, java.util.Date](Nil, _.getValue, ON_CHANGE :: Nil, _.setValue)
    implicit val DecimalboxReadWrite = ReadWrite[Decimalbox, java.math.BigDecimal](Nil, _.getValue, ON_CHANGE :: Nil, _.setValue)
    implicit val DoubleboxReadWrite = ReadWrite[Doublebox, java.lang.Double](Nil, _.getValue, ON_CHANGE :: Nil, _.setValue)
    implicit val IntboxReadWrite = ReadWrite[Intbox, java.lang.Integer](Nil, _.getValue, ON_CHANGE :: Nil, _.setValue)
    implicit val LongboxReadWrite = ReadWrite[Longbox, java.lang.Long](Nil, _.getValue, ON_CHANGE :: Nil, _.setValue)
    implicit val RadioReadWrite = ReadWrite[Radio, Boolean](Nil, _.isChecked, ON_CHECK :: Nil, _.setChecked)
    implicit val SliderReadWrite = ReadWrite[Slider, Int](Nil, _.getCurpos, ON_SCROLL :: Nil, _.setCurpos)
    implicit val SpinnerReadWrite = ReadWrite[Spinner, java.lang.Integer](Nil, _.getValue, ON_CHANGE :: Nil, _.setValue)
    implicit val TextboxReadWrite = ReadWrite[Textbox, String](Nil, _.getValue, ON_CHANGE :: Nil, _.setValue)
    implicit val TimeboxReadWrite = ReadWrite[Timebox, java.util.Date](Nil, _.getValue, ON_CHANGE :: Nil, _.setValue)
  }

}
