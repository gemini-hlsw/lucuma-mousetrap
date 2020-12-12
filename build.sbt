/* ScalablyTyped configuration */
enablePlugins(ScalablyTypedConverterGenSourcePlugin)

Global / onChangedBuildSource := ReloadOnSourceChanges

inThisBuild(
  Seq(
    homepage := Some(url("https://github.com/gemini-hlsw/lucuma-mousetrap")),
    Global / onChangedBuildSource := ReloadOnSourceChanges
  ) ++ lucumaPublishSettings
)

lazy val root = project
  .in(file("."))
  .settings(name := "lucuma-mousetrap")
  .settings(
    // shade into another package
    stOutputPackage := "lucuma.mt",
    /* javascript / typescript deps */
    Compile / npmDependencies ++= Seq(
      "mousetrap"        -> "1.6.5",
      "@types/mousetrap" -> "1.6.5"
    ),
    stSourceGenMode := SourceGenMode.ResourceGenerator,
    /* disabled because it somehow triggers many warnings */
    scalaJSLinkerConfig ~= (_.withSourceMap(false)),
    // because npm is slow
    useYarn := true,
    stUseScalaJsDom := true,
    scalacOptions ~= (_.filterNot(
      Set(
        // By necessity facades will have unused params
        "-Wdead-code",
        "-Wunused:params",
        "-Wunused:imports",
        "-Wunused:explicits"
      )
    )),
    sources in (Compile, doc) := Seq(),
    // focus only on these libraries
    stMinimize := Selection.AllExcept("mousetrap"),
    libraryDependencies ++= Seq(
      "com.github.japgolly.scalajs-react" %%% "core"   % "1.7.7",
      "io.github.cquiroz.react"           %%% "common" % "0.11.2"
    )
  )
  .settings(lucumaScalaJsSettings: _*)
  .enablePlugins(ScalablyTypedConverterGenSourcePlugin)
