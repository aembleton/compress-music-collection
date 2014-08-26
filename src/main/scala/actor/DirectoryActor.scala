package actor

import java.io.File

import akka.actor.{Actor, Props}
import scala.collection.mutable._

class DirectoryActor extends Actor {

  var path:String = ""
  val filesBeingPrcoessed:Set[String] = Set.empty

  override def receive: Receive = {
    case path: File => readPath(path)
    case Completed(filePath) => {
      completed(filePath)
    }
    case _ => Unit
  }

  def readPath (fileToRead: File) = {
    this.path = fileToRead.getAbsolutePath
    fileToRead match {
      case dir:File if (dir.isDirectory) => readDirectory(dir)
      case file:File if (file.isFile) => readFile(file)
      case _ => Unit
    }
  }

  def readDirectory (dir: File) = dir.listFiles().map(file => {
    filesBeingPrcoessed += file.getAbsolutePath
    println(s"Adding $filesBeingPrcoessed")
    context.actorOf(Props(new DirectoryActor)) ! file
  })

  def readFile (file: File) = {
    println(file.getAbsolutePath)
    sender ! Completed(path)
  }

  def completed (filePath: String): Unit = {
    filesBeingPrcoessed -= filePath
    if (filesBeingPrcoessed.size < 1) {
      println(s"Shutdown $path")
      sender ! Completed(path)
    }
  }
}
