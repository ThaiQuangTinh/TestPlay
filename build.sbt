ThisBuild / scalaVersion := "2.13.13"

ThisBuild / version := "3.0"

lazy val root = (project in file("."))
  .enablePlugins(PlayJava)
  .settings(
    name := """LoginDemo""",
    libraryDependencies ++= Seq(
      guice,
      "com.microsoft.sqlserver" % "mssql-jdbc" % "12.6.0.jre11"
    )
  )