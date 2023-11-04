import sbt.Keys._
name := "comun-infraestructura"

scalaVersion := "2.12.11"

libraryDependencies ++= Seq(
  "joda-time" % "joda-time" % "2.10.6",
  "org.typelevel" %% "cats-core" % "2.1.1",
  "org.typelevel" %% "cats-macros" % "2.1.1",
  "org.typelevel" %% "cats-kernel" % "2.1.1",
  "com.typesafe.play"           %% "play-slick"               % "4.0.2",
  "io.monix" %% "monix" % "3.2.1",
  ws,
  filters
)

lazy val comun_aplicacion = RootProject(file("../comun-aplicacion"))
lazy val comun_dominio = RootProject(file("../comun-dominio"))

dependsOn(comun_aplicacion,comun_dominio)

resolvers ++= Seq(
  "Typesafe Releases"               at "https://repo.typesafe.com/typesafe/maven-releases",
 )