package org.scardiecat.styx.seednode.main


import akka.actor.{Props, ActorSystem}
import akka.cluster.Cluster
import com.google.inject.{Guice, Injector}
import com.typesafe.config.{Config, ConfigFactory}
import kamon.Kamon
import org.scardiecat.styx.DockerAkkaUtils
import org.scardiecat.styx.akkaguice.AkkaModule
import net.codingwell.scalaguice.InjectorExtensions._
import org.scardiecat.styx.utils.commandline.CommandlineParser

import scala.concurrent.Await
import scala.util.Try
import scala.concurrent.duration._

import org.scardiecat.styx.seednode.example.{GenerateMore, Generator}

/**
  * expects role port [seednodes]
  */
object SeedNodeBoot extends App{
  val fallbackConfig:Config = ConfigFactory.load()
  var commandline = CommandlineParser.parse(args,meta.BuildInfo.name+":"+meta.BuildInfo.version, fallbackConfig, Seq[String]("seed"))
  val hostAddress: String = java.net.InetAddress.getLocalHost.getHostAddress
  if (commandline.seeds.length != 0 && commandline.seeds(0).length == 0) {
    // if an empty list use ourselves as the only seed
    commandline = commandline.copy(seeds = Seq[String](java.net.InetAddress.getLocalHost.getHostAddress + ":2551"))
  }
  val config: Config = DockerAkkaUtils.dockerAkkaConfigProvider(fallbackConfig,hostAddress,commandline)

  //Startup ActorSystem
  val confModule = new ConfigModule(config, commandline.actorSystemName)

  Kamon.start()
  val akkaModule = new AkkaModule()

  val injector: Injector = Guice.createInjector(confModule,akkaModule)

  val system = injector.instance[ActorSystem]

  val generator = system.actorOf(Props[Generator], name = "Generator")
  val cluster = Cluster(system)
  cluster.registerOnMemberRemoved {
    System.out.println("Terminating actor system : ")
    // exit JVM when ActorSystem has been terminated
    system.registerOnTermination(System.exit(0))
    // shut down ActorSystem
    system.terminate()
    // In case ActorSystem shutdown takes longer than 10 seconds,
    // exit the JVM forcefully anyway.
    // We must spawn a separate thread to not block current thread,
    // since that would have blocked the shutdown of the ActorSystem.
    new Thread {
      override def run(): Unit = {
        if (Try(Await.ready(system.whenTerminated, 10.seconds)).isFailure)
          System.exit(-1)
      }
    }.start()
  }

  Runtime.getRuntime.addShutdownHook(new Thread() {
    override def run {
      System.out.println("Inside cluster shutdown hook : " + Thread.currentThread.getName)
      cluster.leave(cluster.selfAddress)
    }
  })

  generator ! GenerateMore(20)
}