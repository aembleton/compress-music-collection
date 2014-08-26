package actor

import java.io.File

import akka.actor.{Props, Actor}

class RootActor extends Actor{

  override def receive: Receive = {
    case file: File => context.actorOf(Props(new DirectoryActor)) ! file
    case Completed(_) => println("SHUTDOWN");context.system.shutdown();
    case _ => Unit
  }
}
