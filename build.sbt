name := """votingq_backend"""
organization := "io.votingq"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.13.2"

libraryDependencies += guice
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "5.0.0" % Test
libraryDependencies ++= Seq(evolutions, jdbc)
libraryDependencies += "org.postgresql" % "postgresql" % "42.2.14"
libraryDependencies += "org.apache.logging.log4j" % "log4j-core" % "2.13.3"
// Adds additional packages into Twirl
//TwirlKeys.templateImports += "io.votingq.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "io.votingq.binders._"
