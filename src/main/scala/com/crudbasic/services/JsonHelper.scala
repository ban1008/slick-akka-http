package com.crudbasic.services

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import com.crudbasic.repository.Student
import spray.json.DefaultJsonProtocol

case class Message(txt:String)

trait JsonHelper extends SprayJsonSupport with DefaultJsonProtocol {

  implicit val studFormat = jsonFormat3(Student.apply)
  implicit val msgFormat = jsonFormat1(Message.apply)
}