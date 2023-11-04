organization := "co.com.ceiba"

scalaVersion := "2.13.1"

lazy val aplicacion = project.in(file("comun-aplicacion")).
    settings(Common.settings: _*)

lazy val dominio = project.in(file("comun-dominio")).
  settings(Common.settings: _*)

lazy val infraestructura = project.in(file("comun-infraestructura")).
    settings(Common.settings: _*)
	
lazy val test = project.in(file("comun-test")).
    settings(Common.settings: _*)


lazy val root_comun = (project in file(".")).settings(
  publish := {},
  publishLocal := {}).
    aggregate(aplicacion,dominio, infraestructura, test)


target in assembly := file("target")

