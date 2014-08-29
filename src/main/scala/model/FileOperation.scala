package model

import akka.actor.ActorRef

case class FileOperation(source:String, destination:String, lameActor:ActorRef, vbrActor:ActorRef, vbr:Double=5) {
  def appendChild(child:String) = copy(source=s"$source/$child", destination=s"$destination/$child")
  def setVbr(vbrValue:Double) = copy(vbr=vbrValue)
}