import java.io.File

import actor.{VbrActor, LameActor, RootActor}
import akka.actor.{PoisonPill, Props, ActorSystem}
import akka.routing.BalancingPool
import model.FileOperation
import sys.process._

object Run {

  def main (args: Array[String]): Unit = {
    //read in the args and start up akka
//    if (args.length != 3) {
//      println(
//        """You must enter the following three arguments:
//          | - Directory from which to read from (eg ~/Music)
//          | - Directory to create the new, smaller collection (eg ~/small-music)
//          | - Maximum size of the new collection in GB (eg 32)
//        """.stripMargin)
//      System.exit(1)
//    }

    //val fileOperation = FileOperation("""/home/arthur/Music/Anjunabeats Vol. 11 (Mixed By Above & Beyond) (320kbps) (Split + Mixed) (Inspiron)/Split/CD 1/single/01 Thomas Schwartz & Fausto Fanizza - You Would.mp3""", "/home/arthur/output/01 Thomas Schwartz & Fausto Fanizza - You Would.mp3")


    val system = ActorSystem("actor-system")
    val rootActor = system.actorOf(Props[RootActor])
    val vbrActor = system.actorOf(Props[VbrActor])
    val lamePool = system.actorOf(BalancingPool(4).props(Props[LameActor]), "lamePool")

    val fileOperation = FileOperation("""/home/arthur/Music/Anjunabeats Vol. 11 (Mixed By Above & Beyond) (320kbps) (Split + Mixed) (Inspiron)/Split""", "/home/arthur/output", lamePool, vbrActor)

    rootActor ! fileOperation
  }
}
