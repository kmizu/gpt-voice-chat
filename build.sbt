import Dependencies._

ThisBuild / scalaVersion     := "2.13.8"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "com.github.kmizu"

lazy val root = (project in file("."))
  .settings(
    name := "gpt-voice-chat",
    libraryDependencies ++= Seq(
      "com.theokanning.openai-gpt3-java" % "client" % "0.11.0",
      "com.jgoodies" % "jgoodies-forms" % "1.9.0",
      "org.scalaj" %% "scalaj-http" % "2.4.2",
      "com.typesafe.play" %% "play-json" % "2.9.4",
      scalaTest % Test
    )
  )
