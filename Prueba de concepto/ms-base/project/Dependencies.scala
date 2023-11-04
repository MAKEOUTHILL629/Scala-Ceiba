import sbt._
import Keys._
import play.sbt.PlayImport._

object Dependencies {

  val slf4jVersion = "1.6.4"
  val playVersion = "2.7.4"
  val catsVersion = "2.1.1"
  val SwaggerVersion = "1.5.24"
  

  val commonDependencies: Seq[ModuleID] = Seq(
    "org.scalatestplus.play" %% "scalatestplus-play" % "4.0.3" % Test,
    "org.mockito" % "mockito-core" % "3.3.3" % Test,
    "org.specs2" %% "specs2-mock" % "4.9.4" % Test,
    "joda-time" % "joda-time" % "2.10.6",
    "org.typelevel" %% "cats-core" % catsVersion,
    "org.typelevel" %% "cats-macros" % catsVersion,
    "org.typelevel" %% "cats-kernel" % catsVersion,
    "io.monix" %% "monix" % "3.2.1",
    "javax.inject" % "javax.inject" % "1"
  )

  val playDepencies : Seq[ModuleID] = Seq(
    "com.typesafe.play"           %% "play-ws"                  % playVersion,
    "com.typesafe.play"           %% "play-json"                % playVersion,
    "com.typesafe.play"           %% "filters-helpers"          % playVersion,
    "com.typesafe.play"           %% "play-test"                % playVersion,
    "com.typesafe.play"           %% "play-slick"               % "4.0.2",
    "com.typesafe.play"           %% "play-slick-evolutions"    % "4.0.2",
    "com.h2database"              % "h2"                        % "1.4.200",
    "org.mockito"                 % "mockito-core"              % "3.3.3" % Test,
    "org.specs2"                  %% "specs2-mock"              % "4.9.4" % Test,
    "org.scalatestplus.play"      %% "scalatestplus-play"       % "4.0.3" % Test,
    "io.swagger"        %% "swagger-play2"              % "1.7.1",
    "io.swagger"         % "swagger-parser"             % "1.0.44",
    "org.webjars" %% "webjars-play" % "2.6.3",
    "org.webjars" % "swagger-ui" % "2.2.0"
  )

  //And add these to your library dependencies
  // Excluding scale and annotation because I was not happy with the defaults versions.


  val dominioDependencies : Seq[ModuleID] = commonDependencies
  val aplicacionDependencies : Seq[ModuleID] = commonDependencies

  val infraestructuraDependencias    : Seq[ModuleID] = commonDependencies ++ playDepencies
  
  val webDependencies    : Seq[ModuleID] = commonDependencies ++ playDepencies


}