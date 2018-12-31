package org.sstudio.stonehenge.actors

import akka.actor.Actor
import org.sstudio.stonehenge.common.utils.LogUtil

trait ActorStack extends Actor {

  val logger = LogUtil.getLogContext.getLogger(getClass.toString)

  def wrappedReceive: Receive

  def receive: Receive = {
    case x => if (wrappedReceive.isDefinedAt(x)) wrappedReceive(x) else unhandled(x)
  }
}