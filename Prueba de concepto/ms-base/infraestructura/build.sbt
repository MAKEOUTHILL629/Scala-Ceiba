name := Common.NamePrefix + "infraestructura"

libraryDependencies ++= Dependencies.infraestructuraDependencias



coverageExcludedPackages := ".*router*.;.*ReverseControladorComandoUsuario*;.*ReverseControladorConsultaUsuario*;.*router*.;.*ReverseRoute*.;.*JavaScriptReverseRoutes*."