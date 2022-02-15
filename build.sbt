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
      "com.github.japgolly.scalajs-react" %%% "core"   % "1.7.7",
      "io.github.cquiroz.react"           %%% "common" % "0.11.2"
    )
  )
  .enablePlugins(ScalablyTypedConverterGenSourcePlugin)
