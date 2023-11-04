name := "comun-test"

scalaVersion := "2.13.1"


libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "3.1.1",
  "org.scalatestplus.play" %% "scalatestplus-play" % "4.0.3",
  "org.typelevel" %% "cats-core" % "2.1.1",
  "org.typelevel" %% "cats-macros" %"2.1.1",
  "org.typelevel" %% "cats-kernel" % "2.1.1",
)

