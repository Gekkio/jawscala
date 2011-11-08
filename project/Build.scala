import fi.jawsy.sbtplugins.jrebel.JRebelPlugin.{ jrebel, jrebelSettings }

import sbt._
import Keys._

object Dependencies {
  object Versions {
    val akka = "1.2"
    val atmosphere = "0.7.2"
    val javaxServlet = "3.0.1"
    val lift = "2.4-M4"
    val specs2 = "1.6.1"
    val vaadin = "6.7.0"
    val zk = "5.0.8"
  }

  val akkaActor = "se.scalablesolutions.akka" % "akka-actor" % Versions.akka
  val atmosphereRuntime = "org.atmosphere" % "atmosphere-runtime" % Versions.atmosphere
  val javaxServlet = "javax.servlet" % "javax.servlet-api" % Versions.javaxServlet
  val liftJson = "net.liftweb" %% "lift-json" % Versions.lift
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
    version := "0.1.0-SNAPSHOT",
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
  ) aggregate(jawscalaZk, jawscalaZkAsync, jawscalaVaadin)

  lazy val jawscalaZk = Project(
    "jawscala-zk",
    file("zk"),
    settings = buildSettings ++ Seq(
      libraryDependencies ++= {
        import Dependencies._
        Seq(
          javaxServlet % "provided",
          liftJson,
          specs2 % "test",
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
