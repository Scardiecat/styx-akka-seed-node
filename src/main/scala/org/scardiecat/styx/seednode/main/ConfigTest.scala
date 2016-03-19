package org.scardiecat.styx.seednode.main

import akka.actor.ActorSystem
import com.typesafe.config.{ConfigFactory, Config}
import kamon.ConfigProvider

/**
  * Created by ralfmueller on 2016-03-18.
  */
class ConfigTest extends ConfigProvider
{
  def config(): Config ={
    SeedNodeBoot.config
  }
}
