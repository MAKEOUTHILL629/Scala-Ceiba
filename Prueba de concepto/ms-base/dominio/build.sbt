name := Common.NamePrefix + "dominio"

libraryDependencies ++= Dependencies.dominioDependencies

Test / scalaSource := baseDirectory.value / "src" / "test"