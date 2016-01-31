package org.scardiecat.styx.seednode.main


import akka.actor.ActorSystem
import com.google.inject.{Guice, Injector}
import com.typesafe.config.{Config, ConfigFactory}
import org.scardiecat.styx.DockerAkkaUtils
import org.scardiecat.styx.akkaguice.AkkaModule
import net.codingwell.scalaguice.InjectorExtensions._
import org.scardiecat.styx.utils.commandline.CommandlineParser

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
  val akkaModule = new AkkaModule()

  val injector: Injector = Guice.createInjector(confModule,akkaModule)

  val system = injector.instance[ActorSystem]

}