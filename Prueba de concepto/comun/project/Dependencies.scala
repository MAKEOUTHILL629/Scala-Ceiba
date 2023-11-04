import sbt._
import Keys._
import play.sbt.PlayImport._

object Dependencies {

  val slf4jVersion = "1.6.4"
  

  val commonDependencies: Seq[ModuleID] = Seq(
    "org.scalatest" %% "scalatest" % "3.0.7" % "test")

  val json : Seq[ModuleID] = Seq()

  val dominioDependencies : Seq[ModuleID] = commonDependencies

  val aplicacionDependencies : Seq[ModuleID] = commonDependencies

  val infraestructuraDependencias    : Seq[ModuleID] = commonDependencies ++ json
}