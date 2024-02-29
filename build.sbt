ThisBuild / tlBaseVersion := "0.2"

/* ScalablyTyped configuration */
enablePlugins(ScalablyTypedConverterGenSourcePlugin)

Global / onChangedBuildSource := ReloadOnSourceChanges

lazy val root = project
  .in(file("."))
  .settings(name := "lucuma-mousetrap")
  .settings(
    // shade into another package
    stOutputPackage         := "lucuma.mt",
    /* javascript / typescript deps */
    Compile / npmDependencies ++= Seq(
      "mousetrap"        -> "1.6.5",
      "@types/mousetrap" -> "1.6.5"
    ),
    stSourceGenMode         := SourceGenMode.ResourceGenerator,
    /* disabled because it somehow triggers many warnings */
    scalaJSLinkerConfig ~= (_.withSourceMap(false)),
    // because npm is slow
    useYarn                 := true,
    stUseScalaJsDom         := true,
    tlFatalWarnings         := false, // By necessity facades will have unused params
    Compile / doc / sources := Seq(),
    // focus only on these libraries
    stMinimize              := Selection.AllExcept("mousetrap"),
    libraryDependencies ++= Seq(
      "com.github.japgolly.scalajs-react" %%% "core"   % "2.1.1",
      "io.github.cquiroz.react"           %%% "common" % "0.11.2"
    )
  )
  .enablePlugins(ScalablyTypedConverterGenSourcePlugin)
