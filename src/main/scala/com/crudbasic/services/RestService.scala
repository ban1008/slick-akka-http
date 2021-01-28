package com.crudbasic.services
import scala.concurrent.ExecutionContext.Implicits.global
import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.model.HttpResponse
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer
import com.crudbasic.connection.MySqlDBComponent
import com.crudbasic.repository._
import com.google.inject.{AbstractModule, Guice, Inject}

class StudentRepImpl extends StudentRepository with MySqlDBComponent

object RestService {

  private val injector = Guice.createInjector(new AbstractModule() {
    override def configure() {
      bind(classOf[StudentRepository]).to(classOf[StudentRepImpl])
    }
  })

  private val routeService = injector.getInstance(classOf[Routes])

  implicit val system: ActorSystem = ActorSystem()

  implicit val materializer = ActorMaterializer()

  implicit val dispatcher = system.dispatcher

  def main(args: Array[String]): Unit = {
    Http().bindAndHandle(routeService.route, "localhost", 8080)
  }
}
