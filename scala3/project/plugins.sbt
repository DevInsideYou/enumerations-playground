ThisBuild / scalaVersion := "2.12.11"
ThisBuild / useSuperShell := false
ThisBuild / autoStartServer := false

addSbtPlugin("ch.epfl.lamp" % "sbt-dotty" % "0.4.1")
addSbtPlugin("com.timushev.sbt" % "sbt-updates" % "0.5.0")
addSbtPlugin("com.typesafe.sbt" % "sbt-git" % "1.0.0")
addSbtPlugin("io.spray" % "sbt-revolver" % "0.9.1")
addSbtPlugin("org.scalameta" % "sbt-scalafmt" % "2.3.4")
