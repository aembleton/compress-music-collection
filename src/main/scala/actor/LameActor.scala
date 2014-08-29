package actor

import akka.actor.Actor
import akka.actor.Actor.Receive
import model.FileOperation
import sys.process._

class LameActor extends Actor{
  override def receive: Receive = {
    case fileOperation: FileOperation => readFile(fileOperation)
    case _ => Unit
  }

  def readFile (fileOperation:FileOperation) = {

    val cmd = Seq("lame", "--quiet", "-V5", fileOperation.source, fileOperation.destination)

    cmd !

    println(fileOperation.destination)
  }
}
