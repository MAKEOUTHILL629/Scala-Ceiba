import scala.collection.Seq

name := Common.NamePrefix + "app"


libraryDependencies ++= Dependencies.webDependencies

Test / scalaSource := baseDirectory.value / "test"
Test / resourceDirectory := baseDirectory.value / "test" / "resources"


PlayKeys.devSettings := Seq("play.server.http.port" -> "9006")

coverageExcludedPackages := ".*router*.;.*ReverseControladorComandoUsuario*;.*ReverseControladorConsultaUsuario*;.*ReverseControladorConsultaVersion*;.*router*.;.*modulo*.;.*ReverseRoute*.;.*JavaScriptReverseRoutes*."


