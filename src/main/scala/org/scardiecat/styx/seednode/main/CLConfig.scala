package org.scardiecat.styx.seednode.main

case class CLConfig(roles: Seq[String] = Seq("Seed")
                    , seeds: Seq[String] = Seq(java.net.InetAddress.getLocalHost.getHostAddress +":2551")
                    , port: Int = 2551
                    , actorSystemName: String = "ActorSystemName")
