import Dependencies._

ThisBuild / scalaVersion     := "2.12.10"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "com.8thlight"
ThisBuild / organizationName := "8thlight"

val http4sVersion = "0.21.1"
val circeVersion = "0.13.0"
val logbackVersion = "1.2.3"

lazy val root = (project in file("."))
  .settings(
    name := "scala-book-bot",
    libraryDependencies += "org.scalamock" %% "scalamock" % "4.4.0" % Test,
    libraryDependencies += scalaTest % Test,
    libraryDependencies += "ch.qos.logback" % "logback-classic" % logbackVersion,
    libraryDependencies +=  "org.scalaj" %% "scalaj-http" % "2.4.2",
    libraryDependencies ++= Seq(
      "io.circe" %% "circe-core",
      "io.circe" %% "circe-generic",
      "io.circe" %% "circe-parser"
    ).map(_ % circeVersion),
    libraryDependencies ++= Seq(
      "org.http4s" %% "http4s-blaze-server",
      "org.http4s" %% "http4s-blaze-client",
      "org.http4s" %% "http4s-circe",
      "org.http4s" %% "http4s-dsl",      
    ).map(_ % http4sVersion),
    addCompilerPlugin("org.typelevel" %% "kind-projector"     % "0.10.3"),
    addCompilerPlugin("com.olegpy"    %% "better-monadic-for" % "0.3.0")
  )

enablePlugins(JavaAppPackaging)

scalacOptions ++= Seq(
  "-deprecation",
  "-encoding", "UTF-8",
  "-language:higherKinds",
  "-language:postfixOps",
  "-feature",
  "-Xfatal-warnings",
)
// See https://www.scala-sbt.org/1.x/docs/Using-Sonatype.html for instructions on how to publish to Sonatype.
