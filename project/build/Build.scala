import fi.jawsy.sbtplugins.jrebel.JRebelJarPlugin
import sbt._

class JawscalaProject(info: ProjectInfo) extends ParentProject(info) {

  object Versions {
    val vaadin = "6.4.8"
  }

  lazy val vaadin = project("vaadin", "jawscala-vaadin", new JawscalaVaadin(_))

  class JawscalaVaadin(info: ProjectInfo) extends DefaultProject(info) with JRebelJarPlugin {
    val vaadin = "com.vaadin" % "vaadin" % Versions.vaadin withSources
  }

  override def pomExtra =
    <url>http://github.com/Gekkio/jawscala</url>
    <organization>
      <name>Jawsy Solutions</name>
      <url>http://www.jawsy.fi</url>
    </organization>
    <licenses>
      <license>
        <name>Apache 2</name>
        <url>http://www.apache.org/licenses/LICENSE-2.0.txt</url>
        <distribution>repo</distribution>
      </license>
    </licenses>

}
