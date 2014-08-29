package actor

import akka.actor.Actor
import model.{VbrResponse, VbrRequest, FileOperation}


class VbrActor extends Actor{
  override def receive: Receive = {
    case request: VbrRequest => {
      println("Setting vbr to 5")
      sender ! VbrResponse(5)
    }
    case _ => Unit
  }

}
