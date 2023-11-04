name := "comun-aplicacion"

scalaVersion := "2.12.11"

libraryDependencies ++= Seq(
  "org.typelevel" %% "cats-core" % "2.1.1",
  "org.typelevel" %% "cats-macros" %"2.1.1",
  "org.typelevel" %% "cats-kernel" % "2.1.1",
  "io.monix" %% "monix" % "3.2.1",
)

lazy val comun_dominio = RootProject(file("../comun-dominio"))

dependsOn(comun_dominio)