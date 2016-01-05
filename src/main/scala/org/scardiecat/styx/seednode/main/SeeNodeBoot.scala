package org.scardiecat.styx.seednode.main


import akka.actor.ActorSystem
import com.google.inject.{Guice, Injector}
import com.typesafe.config.{ConfigValueFactory, Config, ConfigFactory}
import org.scardiecat.styx.DockerAkkaUtils
import org.scardiecat.styx.akkaguice.AkkaModule
import net.codingwell.scalaguice.InjectorExtensions._
import org.scardiecat.styx.utils.environment

/**
  * expects role port [seednodes]
  */
object SeedNodeBoot extends App{

  val parser = new scopt.OptionParser[CLConfig]("SeedNodeBoot") {
    head("SeedNodeBoot", meta.BuildInfo.version)
    opt[Seq[String]]('r', "roles") valueName("<role>,<role>...") action { (x,c) =>
      c.copy(roles = x) } text("roles")
    opt[Seq[String]]('s', "seeds") valueName("<seednode>,<seednode>...") action { (x,c) =>
      c.copy(seeds = x) } text("seed nodes")
    opt[Int]('p', "--port") action { (x, c) =>
      c.copy(port = x) } text("port the actor system listens too")
    opt[String]('n', "--name") action { (x, c) =>
      c.copy(actorSystemName = x) } text("Name of the actor system")
  }
  parser.parse(args, CLConfig()) match {
    case Some(clConfig) =>
      val actorSystemName:String = Option(System.getenv().get(environment.ENV_ACTORSYTEM_NAME)).getOrElse(clConfig.actorSystemName)

      val hostAddress: String = java.net.InetAddress.getLocalHost.getHostAddress
      val config: Config = DockerAkkaUtils.dockerAkkaConfigProvider(ConfigFactory.load()
        , actorSystemName, clConfig.roles.toList, clConfig.seeds.toList, hostAddress, clConfig.port.toString)
      System.out.println(clConfig)

      //Startup ActorSystem
      val confModule = new ConfigModule(config, actorSystemName)
      val akkaModule = new AkkaModule()

      val injector: Injector = Guice.createInjector(confModule,akkaModule)

      val system = injector.instance[ActorSystem]
    case None =>
      System.out.println("Not started")
  }


}