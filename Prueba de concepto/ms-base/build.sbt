import sbtbuildinfo.BuildInfoKey

organization := "co.com.ceiba"

scalaVersion := "2.12.11"

resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"

// ---------------comun-----------------------------

lazy val comun_aplicacion = RootProject(file("../comun/comun-aplicacion"))
lazy val comun_dominio = RootProject(file("../comun/comun-dominio"))
lazy val comun_infraestructura = RootProject(file("../comun/comun-infraestructura"))


lazy val aplicacion = project.in(file("aplicacion")).
  dependsOn(dominio, comun_aplicacion % "test->test;compile->compile").
  settings(Common.settings: _*)

lazy val dominio = project.in(file("dominio"))
  .dependsOn(comun_dominio % "test->test;compile->compile")
  .settings(Common.settings: _*)

lazy val infraestructura = project.in(file("infraestructura")).
  dependsOn(comun_infraestructura,dominio,aplicacion % "test->test;compile->compile").
  settings(Common.settings: _*)


val dtf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
val generationDate = dtf.format(new java.util.Date())

// ---------------web-----------------------------
lazy val web = project.
    dependsOn( infraestructura % "test->test;compile->compile").
    settings(Common.settings: _*)
    .settings(buildInfoKeys := Seq[BuildInfoKey](name,
      "builtAt" -> generationDate,
      "branch " -> git.gitCurrentBranch.value ,
      "commit" -> git.gitHeadCommit.value.getOrElse(""),
      "commitDate" -> git.gitHeadCommitDate.value.getOrElse(""),
      "describedVersion " -> git.gitDescribedVersion.value.getOrElse(""),
      version,
      scalaVersion,
      sbtVersion
    ) ,
      buildInfoPackage := "co.com.servicio_base",
      libraryDependencies ++= Seq()
    )
    .enablePlugins(PlayScala,GitVersioning, BuildInfoPlugin)

// ---------------ms-base-----------------------------
lazy val ms_base = (project in file(".")).settings(
  publish := {},
  publishLocal := {}).
    aggregate(infraestructura, aplicacion, dominio, web)


target in assembly := file("target")

publishArtifact in ms_base := false

coverageMinimum := 80

coverageExcludedPackages := ".*router*.;.*ReverseControladorComandoUsuario*;.*ReverseControladorConsultaUsuario*;.*router*.;.*ReverseRoute*.;.*JavaScriptReverseRoutes*."

coverageHighlighting := true

addCommandAlias("testCoverage", "clean;coverage;test;coverageOff;coverageReport;coverageAggregate")
