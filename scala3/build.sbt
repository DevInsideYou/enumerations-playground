ThisBuild / organization := "com.devinsideyou"
ThisBuild / scalaVersion := "0.23.0-RC1"
ThisBuild / version := "0.0.1-SNAPSHOT"

ThisBuild / scalacOptions ++= Seq(
  "-deprecation",
  "-feature",
  "-language:implicitConversions",
  "-rewrite", "-noindent",
  "-unchecked"
)

lazy val scala3 =
  project
    .in(file("."))
    .settings(
      name := "Scala 3",
      Compile / console / scalacOptions --= Seq(
        "-Wunused:_",
        "-Xfatal-warnings"
      ),
      Test / console / scalacOptions :=
        (Compile / console / scalacOptions).value
    )
