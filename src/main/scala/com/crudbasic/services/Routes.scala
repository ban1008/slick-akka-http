package com.crudbasic.services

import akka.http.scaladsl.model.HttpResponse
import akka.http.scaladsl.model.StatusCodes.InternalServerError
import akka.http.scaladsl.server.Directives._
import com.crudbasic.repository.{Student, StudentRepository}
import com.google.inject.Inject

import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}

class Routes @Inject()(stRep:StudentRepository) extends JsonHelper {

  private val cors = new CORSHandler {}

  val route = {
    cors.corsHandler((path("students") & get) {
      (complete(stRep.fetchAll()))
    }~
      (path("student") & post) {
        entity(as[Student]) { student =>
            onComplete(stRep.addStudent(student)){
              case Success(i) => if(i>=1){complete(Message("Success"))}
              else{complete(Message("Failure"))}
              case Failure(e) => complete(Message("Failure"))
            }
        }
      } ~
      (path("student") & put) {
        entity(as[Student]) { student =>
            onComplete(stRep.updateStudent(student)){
              case Success(i) => if(i>=1){complete(Message("Success"))}
              else{complete(Message("Failure"))}
              case Failure(e) => complete(Message("Failure"))
            }
          }
      } ~
      (path("student" / IntNumber) & delete) { id =>
        onComplete(stRep.deleteStudent(id)){
          case Success(i) => if(i>=1){complete(Message("Success"))}
                              else{complete(Message("Failure"))}
          case Failure(e) => complete(Message("Failure"))
        }
      })
  }

}