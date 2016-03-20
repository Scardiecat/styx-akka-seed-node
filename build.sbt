val commonSettings = Seq(
  organization := "org.scardiecat",
  git.baseVersion := "0.0.6",
  git.gitTagToVersionNumber := { tag: String =>
    if(tag matches "[0-9]+\\..*") Some(tag)
    else None
  },
  git.useGitDescribe := true,
  scalaVersion := "2.11.7",
  scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature", "-language:existentials", "-language:higherKinds"),

  // build info
  buildInfoPackage := "meta",
  buildInfoOptions += BuildInfoOption.ToJson,
  buildInfoOptions += BuildInfoOption.BuildTime,
  buildInfoKeys := Seq[BuildInfoKey](
    name, version, scalaVersion
  ),
  publishMavenStyle := true,
  bintrayReleaseOnPublish in ThisBuild := true,
  bintrayPackageLabels := Seq("styx", "scala", "Akka"),
  licenses += ("MIT", url("http://opensource.org/licenses/MIT")),
  resolvers += "kamon" at "http://snapshots.kamon.io/",
  fork in run := true
  )

val dockerSettings = Seq(
  dockerBaseImage := "anapsix/alpine-java:jdk",
  dockerExposedPorts := Seq(2551),
  maintainer in Docker := "Ralf Mueller <docker@scardiecat.org>",
  dockerRepository := Some("magicmoose-docker-registry.bintray.io/scardiecat")
)

lazy val root = (project in file("."))
  .enablePlugins(BuildInfoPlugin, JavaAppPackaging, DockerPlugin, GitVersioning)
  .settings(
    name := """styx-akka-seed-node""",
    commonSettings,
    libraryDependencies ++= Dependencies.common,
    dockerSettings
  )
