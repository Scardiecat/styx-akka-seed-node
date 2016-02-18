import sbt._

object Dependencies {

  object Version {
    val akka = "2.4.1"
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
    "io.kamon" % "sigar-loader" % "1.6.6-rev002"
  )
}
