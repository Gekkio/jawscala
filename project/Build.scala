import fi.jawsy.sbtplugins.jrebel.JRebelPlugin.{ jrebel, jrebelSettings }

import sbt._
import Keys._

object Dependencies {
  object Versions {
    val akka = "1.2"
    val atmosphere = "0.8.1"
    val dispatch = "0.8.6"
    val javaxServlet = "3.0.1"
    val sjson = "0.15"
    val specs2 = "1.6.1"
    val vaadin = "6.7.1"
    val zk = "5.0.9"
  }

  val akkaActor = "se.scalablesolutions.akka" % "akka-actor" % Versions.akka
  val atmosphereRuntime = "org.atmosphere" % "atmosphere-runtime" % Versions.atmosphere exclude("org.atmosphere", "atmosphere-ping")
  val dispatchJson = "net.databinder" %% "dispatch-json" % Versions.dispatch exclude("org.apache.httpcomponents", "httpclient")
  val javaxServlet = "javax.servlet" % "javax.servlet-api" % Versions.javaxServlet
  val specs2 = "org.specs2" %% "specs2" % Versions.specs2
  val vaadin = "com.vaadin" % "vaadin" % Versions.vaadin
  val zkZk = "org.zkoss.zk" % "zk" % Versions.zk
  val zkZul = "org.zkoss.zk" % "zul" % Versions.zk

}

object Resolvers {
  val akkaRepo = "m2-akka" at "http://akka.io/repository"
  val zkossRepo = "m2-zk" at "http://mavensync.zkoss.org/maven2"
}

object JawscalaBuild extends Build {
  val buildSettings = Defaults.defaultSettings ++ Seq(
    organization := "fi.jawsy.jawscala",
    organizationName := "Jawsy Solutions",
    organizationHomepage := Some(new URL("http://jawsy.fi")),
    homepage := Some(new URL("http://github.com/Gekkio/jawscala")),
    licenses += ("Apache 2" -> new URL("http://www.apache.org/licenses/LICENSE-2.0.txt")),
    version := "0.2.1-SNAPSHOT",
    scalaVersion := "2.9.1",
    publishTo <<= (version) { version: String =>
      val nexus = "https://jawsy.fi/nexus/content/repositories/"
      if (version.trim.endsWith("SNAPSHOT"))
        Some("Jawsy.fi M2 snapshots" at nexus + "snapshots")
      else
        Some("Jawsy.fi M2 releases" at nexus + "releases")
    }
  ) ++ jrebelSettings

  lazy val jawscala = Project(
    "jawscala",
    file("."),
    settings = buildSettings ++ Seq(
      publishArtifact in (Compile, packageBin) := false,
      publishArtifact in (Compile, packageDoc) := false,
      publishArtifact in (Compile, packageSrc) := false
    )
  ) aggregate(
    jawscalaZk,
    jawscalaZkAsync,
    jawscalaZkCleditor,
    jawscalaZkGritter,
    jawscalaVaadin
  )

  lazy val jawscalaZk = Project(
    "jawscala-zk",
    file("zk"),
    settings = buildSettings ++ Seq(
      libraryDependencies ++= {
        import Dependencies._
        Seq(
          javaxServlet % "provided",
          zkZk,
          zkZul
        )
      },
      resolvers += Resolvers.zkossRepo
    ))
  lazy val jawscalaZkAsync = Project(
    "jawscala-zk-async",
    file("zk-async"),
    settings = buildSettings ++ Seq(
      libraryDependencies ++= {
        import Dependencies._
        Seq(
          atmosphereRuntime,
          javaxServlet % "provided",
          zkZk
        )
      },
      resolvers += Resolvers.zkossRepo
    ))
  lazy val jawscalaZkCleditor = Project(
    "jawscala-zk-cleditor",
    file("zk-cleditor"),
    settings = buildSettings ++ Seq(
      libraryDependencies ++= {
        import Dependencies._
        Seq(
          javaxServlet % "provided",
          specs2 % "test",
          zkZul
        )
      },
      resolvers += Resolvers.zkossRepo
    ))
  lazy val jawscalaZkGritter = Project(
    "jawscala-zk-gritter",
    file("zk-gritter"),
    settings = buildSettings ++ Seq(
      libraryDependencies ++= {
        import Dependencies._
        Seq(
          javaxServlet % "provided",
          dispatchJson,
          specs2 % "test",
          zkZk
        )
      },
      resolvers += Resolvers.zkossRepo
    ))
  lazy val jawscalaVaadin = Project(
    "jawscala-vaadin",
    file("vaadin"),
    settings = buildSettings ++ Seq(
      libraryDependencies ++= {
        import Dependencies._
        Seq(
          javaxServlet % "provided",
          vaadin
        )
      }
    )
  )

}
