import Dependencies._

ThisBuild / organization := "com.devinsideyou"
ThisBuild / scalaVersion := "2.13.1"
ThisBuild / version := "0.0.1-SNAPSHOT"

ThisBuild / scalacOptions ++= Seq(
  "-deprecation",
  "-feature",
  "-language:_",
  "-unchecked",
  "-Ymacro-annotations"
)

lazy val scala2 =
  project
    .in(file("."))
    .settings(
      name := "Scala 2",
      addCompilerPlugin(org.typelevel.`kind-projector`),
      libraryDependencies ++= Seq(
        "com.beachape" %% "enumeratum-cats" % "1.5.16",
        "com.beachape" %% "enumeratum-circe" % "1.5.23",
        "com.beachape" %% "enumeratum-scalacheck" % "1.5.16", // not necessary
        "com.beachape" %% "enumeratum" % "1.5.15",
        "io.circe" %% "circe-parser" % "0.13.0"
      ),
      libraryDependencies ++= Seq(
        com.github.alexarchambault.`scalacheck-shapeless_1.14`,
        org.scalacheck.scalacheck,
        org.scalatest.scalatest,
        org.scalatestplus.`scalatestplus-scalacheck`
      ).map(_ % Test),
      Compile / console / scalacOptions --= Seq(
        "-Wunused:_",
        "-Xfatal-warnings"
      ),
      Test / console / scalacOptions :=
        (Compile / console / scalacOptions).value
    )
