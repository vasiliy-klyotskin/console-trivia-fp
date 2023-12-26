ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.3.1"

val http4sVersion = "0.23.19"
lazy val root = (project in file("."))
  .settings(
    name := "trivia-fp",
    idePackagePrefix := Some("org.kyotskin.trivia"),
    libraryDependencies ++= Seq(
      "org.scalatest" %% "scalatest" % "3.2.15" % "test",
      "org.typelevel" %% "cats-effect" % "3.5.2",
      "org.http4s"    %% "http4s-ember-client" % "1.0.0-M40",
      "org.http4s"    %% "http4s-dsl" % "1.0.0-M40",
      "org.http4s"    %% "http4s-circe" % "1.0.0-M40",
      "org.typelevel" %% "log4cats-slf4j" % "2.5.0",
      "io.circe"      %% "circe-generic" % "0.14.6",
      "ch.qos.logback" % "logback-classic" % "1.4.7"
    )
  )