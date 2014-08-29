package actor

import java.io.File

import akka.actor.{PoisonPill, Props, Actor}
import model.FileOperation

class RootActor extends Actor{

  override def receive: Receive = {
    case fileOperation: FileOperation => {
      val actor = context.actorOf(Props(new DirectoryActor))
      actor ! fileOperation
      actor ! PoisonPill
    }
    case _ => Unit
  }
}
