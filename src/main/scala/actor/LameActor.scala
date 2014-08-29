package actor

import akka.actor.{Props, Actor}
import akka.actor.Actor.Receive
import model.{VbrResponse, VbrRequest, FileOperation}
import scala.concurrent.Future
import sys.process._
import akka.pattern.ask
import akka.util.Timeout
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global

class LameActor extends Actor{
  override def receive: Receive = {
    case fileOperation: FileOperation => readFile(fileOperation)
    case _ => Unit
  }

  def readFile (fileOperation:FileOperation) = {

    implicit val timeout = Timeout(5 seconds)

    val vbrFuture = ask(fileOperation.vbrActor, VbrRequest).mapTo[VbrResponse]

    vbrFuture.onSuccess{
      case vbr =>

      val cmd = Seq("lame", "--quiet", s"-V$vbr", fileOperation.source, fileOperation.destination)
      println(cmd)
      cmd !

      println(fileOperation.destination)


    }


  }
}
