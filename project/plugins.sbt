resolvers += Resolver.sonatypeRepo("snapshots")
resolvers += MavenRepository("sonatype-s01-snapshots",
                             "https://s01.oss.sonatype.org/content/repositories/snapshots"
)
addSbtPlugin("edu.gemini"                  % "sbt-lucuma-lib" % "0.11.13")
addSbtPlugin("org.scalablytyped.converter" % "sbt-converter"  % "1.0.0-beta36")
