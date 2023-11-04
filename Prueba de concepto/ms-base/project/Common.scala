import sbt._
import Keys._

object Common {
  val NamePrefix = "ms-base-"
  val appVersion = "0.1"

  val settings: Seq[Def.Setting[_]] = Seq(
    version := appVersion,
    scalaVersion := "2.12.11",
    javacOptions ++= Seq("-source", "1.8", "-target", "1.8"), //, "-Xmx2G"),
    scalacOptions ++= Seq("-deprecation", "-unchecked"),
    resolvers ++= Seq(DefaultMavenRepository,
      Classpaths.typesafeReleases,
      Classpaths.sbtPluginReleases,
      "Apache Staging" at "https://repository.apache.org/content/repositories/staging/",
      "Typesafe repository" at "https://repo.typesafe.com/typesafe/releases/",
      "Java.net Maven2 Repository" at "https://download.java.net/maven/2/",
      "Eclipse repositories" at "https://repo.eclipse.org/service/local/repositories/egit-releases/content/",
      "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots",
      "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases",
      "SpinGo OSS" at "https://spingo-oss.s3.amazonaws.com/repositories/releases",
      "The New Motion Public Repo" at "https://nexus.thenewmotion.com/content/groups/public/",
      Resolver.bintrayRepo("iheartradio", "maven")
    )
  )
}