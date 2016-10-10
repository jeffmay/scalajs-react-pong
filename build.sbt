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
    libraryDependencies ++= {
      val monocleVersion = "1.2.2"
      Seq(
        "com.github.japgolly.scalajs-react" %%% "core" % "0.11.2",
        "com.github.julien-truffaut"  %%%  "monocle-core"    % monocleVersion,
        "com.github.julien-truffaut"  %%%  "monocle-generic" % monocleVersion,
        "com.github.julien-truffaut"  %%%  "monocle-macro"   % monocleVersion,
        "com.github.julien-truffaut"  %%%  "monocle-state"   % monocleVersion,
        "com.github.julien-truffaut"  %%%  "monocle-refined" % monocleVersion,
        "com.github.julien-truffaut"  %%%  "monocle-law"     % monocleVersion % Test,
        "eu.unicredit" %%% "akkajsactor" % "0.2.4.11"
      )
    },
    addCompilerPlugin("org.scalamacros" %% "paradise" % "2.1.0" cross CrossVersion.full),
    jsDependencies ++= Seq(
      "org.webjars.bower" % "react" % "15.3.2"
        /        "react-with-addons.js"
        minified "react-with-addons.min.js"
        commonJSName "React",
      "org.webjars.bower" % "react" % "15.3.2"
        /         "react-dom.js"
        minified  "react-dom.min.js"
        dependsOn "react-with-addons.js"
        commonJSName "ReactDOM",
      "org.webjars.bower" % "react" % "15.3.2"
        /         "react-dom-server.js"
        minified  "react-dom-server.min.js"
        dependsOn "react-dom.js"
        commonJSName "ReactDOMServer"
    ),
    persistLauncher in Compile := true,
    scalaJSStage in Global := FastOptStage,
    scalaJSUseRhino in Global := false
  )

lazy val demoJVM = demo.jvm
lazy val demoJS = demo.js

cancelable in Global := true

