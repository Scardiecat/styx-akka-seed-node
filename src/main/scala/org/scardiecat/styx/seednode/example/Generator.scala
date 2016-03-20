package org.scardiecat.styx.seednode.example

import akka.actor.{ActorLogging, Actor, Props}


object Generator {

  def props(): Props = Props(new Generator())
}

class Generator() extends Actor  with ActorLogging{
  val consumer = context.actorOf(Props[Consumer], name = "Consumer")

  def receive = {
    case message:GenerateMore => {
      for( a <- 1 until message.number){
        consumer ! ProcessThis(2000)
      }
      consumer ! SheduleMode(message.number)
      //log.info("Generated messages ")
    }
  }
}