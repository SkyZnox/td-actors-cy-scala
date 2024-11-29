ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.3.4"

val PekkoVersion = "1.1.2"
val PekkoHttpVersion = "1.1.0"

lazy val root = (project in file("."))
  .settings(
    name := "tp-actors",
    idePackagePrefix := Some("fr.cytech.icc"),
    libraryDependencies ++= Seq(
      "org.apache.pekko" %% "pekko-actor-typed" % PekkoVersion,
      "org.apache.pekko" %% "pekko-stream" % PekkoVersion,
      "org.apache.pekko" %% "pekko-http" % PekkoHttpVersion,
      "org.apache.pekko" %% "pekko-http-spray-json" % PekkoHttpVersion,
      "ch.qos.logback" % "logback-classic" % "1.5.6",
    )
  )
