package model

import akka.actor.ActorRef

case class FileOperation(source:String, destination:String, lameActor:ActorRef) {
  def appendChild(child:String) = FileOperation(s"$source/$child", s"$destination/$child", lameActor)
}