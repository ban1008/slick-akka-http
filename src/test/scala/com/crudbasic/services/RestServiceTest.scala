package com.crudbasic.services

import akka.http.scaladsl.testkit.{RouteTestTimeout, ScalatestRouteTest}
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers
import akka.actor.ActorSystem
import akka.http.scaladsl.model.StatusCodes.OK
import akka.testkit.TestDuration
import com.crudbasic.connection.H2DBComponent
import com.crudbasic.repository._
import com.google.inject.{AbstractModule, Guice}
import org.scalatest.BeforeAndAfter

import scala.concurrent.Future
import scala.concurrent.duration.DurationInt

class RestServiceTest extends AnyWordSpec with Matchers with ScalatestRouteTest with JsonHelper with BeforeAndAfter {

  implicit def default(implicit system: ActorSystem) = RouteTestTimeout(new DurationInt(5).second.dilated(system))

  private val injector = Guice.createInjector(new AbstractModule() {
    override def configure() {
      bind(classOf[StudentRepository]).to(classOf[StudentRepositoryTestImpl])
    }
  })

  private val testService = injector.getInstance(classOf[Routes])


  "The service" should {
    "get all student details" in {
      Get("/students") ~> testService.route ~> check {
        status shouldBe OK
        contentType.toString() shouldBe "application/json"
        responseAs[List[Student]].length === 1
        responseAs[List[Student]] shouldEqual List(Student(Some(1), "Student 1", "Student1@gmail.com"))
      }
    }
    "save student detail" in {
      val studSave = Student(name = "Student 7", email = "Student7@gmail.com")
      Post("/student", studSave) ~> testService.route ~> check {
        status shouldBe OK
        contentType.toString() shouldBe "application/json"
        responseAs[String] shouldEqual "{\"txt\":\"Success\"}"
      }
    }
    "update student detail" in {
      val studUpdate = Student(id = Some(7), name = "Student 7", email = "Student7@gmail.com")
      Put("/student", studUpdate) ~> testService.route ~> check {
        status shouldBe OK
        contentType.toString() shouldBe "application/json"
        responseAs[String] shouldEqual "{\"txt\":\"Success\"}"
      }
    }

    "delete student detail by id" in {
      Delete("/student/1") ~> testService.route ~> check {
        status shouldBe OK
        contentType.toString() shouldBe "application/json"
        responseAs[String] shouldEqual "{\"txt\":\"Success\"}"
      }
    }
  }
}

class StudentRepositoryTestImpl extends StudentRepository with H2DBComponent {
  override def addStudent(student: Student): Future[Int] = Future.successful(1)

  override def updateStudent(student: Student): Future[Int] = Future.successful(1)

  override def fetchAll(): Future[List[Student]] = Future.successful(List(Student(Some(1), "Student 1", "Student1@gmail.com")))

  override def deleteStudent(id: Int): Future[Int] = Future.successful(1)

}