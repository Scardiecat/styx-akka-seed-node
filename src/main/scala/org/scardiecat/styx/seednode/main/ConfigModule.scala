package org.scardiecat.styx.seednode.main

import com.google.inject.AbstractModule
import com.typesafe.config.{ConfigFactory, Config}
import net.codingwell.scalaguice.ScalaModule

class ConfigModule(config:Config, actorSystemName:String)  extends AbstractModule with ScalaModule{
  val finalConfig =
    ConfigFactory.parseString(
      s"""
         |styx.actor-system-name=$actorSystemName
         """.stripMargin).
        withFallback(config)

  override  def configure(): Unit = {
    bind[Config].toInstance(this.finalConfig)
  }
}
