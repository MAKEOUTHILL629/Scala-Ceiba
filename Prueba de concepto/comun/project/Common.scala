import sbt._
import Keys._

object Common {
  val appVersion = "0.0.5"
  val NamePrefix = "comun-"

  val settings: Seq[Def.Setting[_]] = Seq(
    version := appVersion,
    scalaVersion := "2.12.8",
    javacOptions ++= Seq("-source", "1.8", "-target", "1.8"), //, "-Xmx2G"),
    scalacOptions ++= Seq("-deprecation", "-unchecked"),
    resolvers ++= Seq(DefaultMavenRepository,
      "Apache Staging" at "https://repository.apache.org/content/repositories/staging/",
      Classpaths.typesafeReleases,
      "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/",
      "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots",
      "Java.net Maven2 Repository" at "http://download.java.net/maven/2/",
      Classpaths.sbtPluginReleases,
      "Eclipse repositories" at "https://repo.eclipse.org/service/local/repositories/egit-releases/content/"
      , "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"
      , "SpinGo OSS" at "http://spingo-oss.s3.amazonaws.com/repositories/releases"
      , "The New Motion Public Repo" at "http://nexus.thenewmotion.com/content/groups/public/"
    )

  )
}