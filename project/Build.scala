import sbt._
import Keys._
import play.Project._

object ApplicationBuild extends Build {

  val appName         = "ruahatu"
  val appVersion      = "1.0-SNAPSHOT"

  val appDependencies = Seq(
    jdbc,

    // Slick
    "com.typesafe.play" %% "play-slick" % "0.3.2",
    "com.typesafe.slick" %% "slick" % "1.0.0",
    "com.h2database" % "h2" % "1.3.166",

    // Swagger
    "com.wordnik" %% "swagger-play2-utils" % "1.2.4",
    "com.wordnik" %% "swagger-play2" % "1.2.4"
  )


  val main = play.Project(appName, appVersion, appDependencies).settings(
    sbt.Keys.fork in Test := false
  )

}
