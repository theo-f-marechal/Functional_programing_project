ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.7"

lazy val root = (project in file("."))
  .settings(
    name := "Functional_programing_project"
  )

libraryDependencies += "org.scalameta" %% "munit" % "0.7.26" % Test

testFrameworks += new TestFramework("munit.Framework")
