name := Common.NamePrefix + "aplicacion"

libraryDependencies ++= Dependencies.aplicacionDependencies

Test / scalaSource := baseDirectory.value / "src" / "test"