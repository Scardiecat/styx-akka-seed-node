package org.scardiecat.styx.seednode.example

import akka.actor.{ActorLogging, Actor, Props}

object Consumer {

  def props(): Props = Props(new Generator())
}

class Consumer() extends Actor with ActorLogging{
  def receive = {
    case message:ProcessThis => {
      Thread.sleep(message.number)
      //log.info("Processed message ")
    }
    case message:SheduleMode => {
      sender() ! GenerateMore(message.number)
      //log.info("Shedule more messages ")
    }
  }
}