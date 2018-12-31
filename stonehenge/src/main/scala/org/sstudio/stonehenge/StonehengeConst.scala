package org.sstudio.stonehenge

import akka.actor.ActorSystem

private[stonehenge] object StonehengeConst {

  implicit val ACTOR_SYSTEM = ActorSystem("stonehenge")
}
