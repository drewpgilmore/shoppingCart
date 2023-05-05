ThisBuild / organization := "com.example"
ThisBuild / scalaVersion := "2.13.5"

val CatsEffectVersion = "3.4.10"
val Http4sVersion = "0.23.18"
val CirceVersion = "0.14.5"

lazy val root = (project in file(".")).settings(
  name := "cats-effect-3-quick-start",
  libraryDependencies ++= Seq(
    // cats-effect
    "org.typelevel" %% "cats-effect"         % CatsEffectVersion,
    "org.typelevel" %% "cats-effect-kernel"  % CatsEffectVersion,
    "org.typelevel" %% "cats-effect-std"     % CatsEffectVersion,
    compilerPlugin("com.olegpy"  %% "better-monadic-for" % "0.3.1"),
    "org.typelevel" %% "munit-cats-effect-3" % "1.0.7" % Test,

    // http4s
    "org.http4s" %% "http4s-ember-client" % Http4sVersion,
    "org.http4s" %% "http4s-circe" % Http4sVersion,

    // circe
    "io.circe" %% "circe-core"    % CirceVersion,
    "io.circe" %% "circe-generic" % CirceVersion,
    "io.circe" %% "circe-parser"  % CirceVersion
  )
)
