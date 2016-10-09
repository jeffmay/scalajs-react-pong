enablePlugins(ScalaJSPlugin)

scalaVersion in ThisBuild := "2.11.8"

lazy val root = project.in(file(".")).
  aggregate(demoJS, demoJVM)

lazy val demo = crossProject.in(file(".")).
  settings(
    name := "akka-pong",
    fork in run := true
  ).
  jvmSettings(
    resolvers += "Akka Snapshots" at " http://repo.akka.io/snapshots/",
    libraryDependencies ++= Seq(
      "com.typesafe.akka" %% "akka-actor" % "2.4.11"
    )
  ).
  jsSettings(
    resolvers += Resolver.sonatypeRepo("releases"),
    libraryDependencies ++= Seq(
      "com.lihaoyi" %%% "scalatags" % "0.6.0",
      "eu.unicredit" %%% "akkajsactor" % "0.2.4.11",
      "org.scala-js" %%% "scalajs-dom" % "0.9.0"
    ),
    persistLauncher in Compile := true,
    scalaJSStage in Global := FastOptStage,
    scalaJSUseRhino in Global := false
  )

lazy val demoJVM = demo.jvm
lazy val demoJS = demo.js

cancelable in Global := true

