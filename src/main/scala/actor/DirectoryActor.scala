package actor

import java.io.File

import akka.actor.{PoisonPill, Actor, Props}
import model.FileOperation
import sys.process._

class DirectoryActor extends Actor {

  override def receive: Receive = {
    case fileOperation: FileOperation => readPath(fileOperation)
    case _ => Unit
  }

  def readPath(fileOperation:FileOperation) = {
    new File(fileOperation.source) match {
      case dir:File if (dir.isDirectory) => readDirectory(fileOperation, dir)
      case file:File if (file.isFile) => readFile(fileOperation)
      case _ => Unit
    }
  }

  def readDirectory (fileOperation:FileOperation, dir: File) = dir.listFiles().map(file => {
    val childActor = context.actorOf(Props(new DirectoryActor))
    childActor ! fileOperation.appendChild(file.getName)
    childActor ! PoisonPill
  })

  def readFile (fileOperation:FileOperation) = {

    fileOperation.lameActor ! fileOperation
  }
}
