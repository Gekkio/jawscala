import fi.jawsy.sbtplugins.jrebel.JRebelJarPlugin
import sbt._

class JawscalaProject(info: ProjectInfo) extends ParentProject(info) {

  object Versions {
    val akka = "1.2"
    val atmosphere = "0.7.2"
    val geronimoServlet = "1.0"
    val lift = "2.4-M4"
    val specs = "1.6.9"
    val vaadin = "6.6.6"
    val zk = "5.0.8"
  }

  lazy val vaadin = project("vaadin", "jawscala-vaadin", new JawscalaVaadin(_))
  lazy val zk = project("zk", "jawscala-zk", new JawscalaZk(_))

  class JawscalaVaadin(info: ProjectInfo) extends DefaultProject(info) with JRebelJarPlugin {
    val vaadin = "com.vaadin" % "vaadin" % Versions.vaadin withSources
  }

  class JawscalaZk(info: ProjectInfo) extends DefaultProject(info) with JRebelJarPlugin {
    val akkaActor = "se.scalablesolutions.akka" % "akka-actor" % Versions.akka % "provided" withSources
    val atmospehereRuntime = "org.atmosphere" % "atmosphere-runtime" % Versions.atmosphere withSources
    val geronimoServlet = "org.apache.geronimo.specs" % "geronimo-servlet_3.0_spec" % Versions.geronimoServlet % "provided" withSources
    val liftJson = "net.liftweb" %% "lift-json" % Versions.lift withSources
    val zkZk = "org.zkoss.zk" % "zk" % Versions.zk withSources
    val zkZul = "org.zkoss.zk" % "zul" % Versions.zk withSources

    val specs = "org.scala-tools.testing" %% "specs" % Versions.specs % "test" withSources
  }

  val akkaRepo = "m2-akka" at "http://akka.io/repository"
  val zkossConfig = ModuleConfiguration("org.zkoss.*", zkossRepo)
  def zkossRepo = "m2-zk" at "http://mavensync.zkoss.org/maven2"

  override def managedStyle = ManagedStyle.Maven

  val publishTo =
    if (version.toString.endsWith("-SNAPSHOT"))
    ("Nexus" at "https://jawsy.fi/nexus/content/repositories/snapshots")
    else ("Nexus" at "https://jawsy.fi/nexus/content/repositories/releases")

  Credentials(Path.userHome / ".ivy2" / "jawsy.credentials", log)

  override def pomExtra =
    <url>http://github.com/Gekkio/jawscala</url>
    <organization>
      <name>Jawsy Solutions</name>
      <url>http://jawsy.fi</url>
    </organization>
    <licenses>
      <license>
        <name>Apache 2</name>
        <url>http://www.apache.org/licenses/LICENSE-2.0.txt</url>
        <distribution>repo</distribution>
      </license>
    </licenses>

}
