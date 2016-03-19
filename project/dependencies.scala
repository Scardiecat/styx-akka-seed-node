import sbt._

object Dependencies {

  object Version {
    val akka = "2.4.2"
    val kamonVersion = "0.6.0-affe465fdcc002fb12c54b3bb139ba3ef4fb1d85"
  }

  val common = Seq(
    "ch.qos.logback" % "logback-classic" % "1.1.3",
    "com.typesafe.akka" %% "akka-actor" % Version.akka,
    "com.typesafe.akka" %% "akka-cluster" % Version.akka,
    "com.typesafe.akka" %% "akka-slf4j" % Version.akka,
    "org.scardiecat" %% "styx-utils" % "0.0.4",
    "org.scardiecat" %% "styx-akka-guice" % "0.0.2",
    "com.github.scopt" %% "scopt" % "3.3.0",
    "com.typesafe.akka" %% "akka-cluster-metrics" % Version.akka,
    "io.kamon" % "sigar-loader" % "1.6.6-rev002",
    "io.kamon" %% "kamon-core" % Version.kamonVersion,
    "io.kamon" %% "kamon-akka" % Version.kamonVersion,
    "io.kamon" %% "kamon-akka-remote_akka-2.4" % Version.kamonVersion,
    "io.kamon" %% "kamon-statsd" % Version.kamonVersion,
    //"io.kamon" %% "kamon-log-reporter" % Version.kamonVersion,
    "io.kamon" %% "kamon-system-metrics" % Version.kamonVersion,
    "io.kamon" %% "kamon-autoweave" % Version.kamonVersion
  )
}
