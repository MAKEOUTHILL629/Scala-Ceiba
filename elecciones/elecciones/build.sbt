name := """elecciones"""
organization := "com.ceiba"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.13.12"
resolvers += "Maven Central" at "https://repo1.maven.org/maven2/"
resolvers += "Typesafe Repository" at "https://repo.typesafe.com/typesafe/releases/"
libraryDependencies += guice
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "5.1.0" % Test

dependencyOverrides += "org.scala-lang.modules" %% "scala-xml" % "1.2.0"
libraryDependencies += "com.typesafe.play" %% "play-slick" % "5.0.0"
libraryDependencies += "com.typesafe.play" %% "play-slick-evolutions" % "5.0.0"

libraryDependencies += "mysql" % "mysql-connector-java" % "8.0.26"
libraryDependencies += "com.typesafe.akka" %% "akka-stream" % "2.6.0"
libraryDependencies ++= Seq(
  ehcache
)

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "com.ceiba.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "com.ceiba.binders._"
