package com.crudbasic.repository

import com.crudbasic.connection.H2DBComponent
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest._

import scala.concurrent.duration.Duration
import matchers.should.Matchers._

import scala.concurrent.Await

class StudentRepositoryTest extends AnyFlatSpec with StudentRepository with H2DBComponent{

  "createSchemaIfNotExists" should "return length = 1" in {
    println(Await.result(createSchemaIfNotExists,Duration.Inf))
    Await.result(createSchemaIfNotExists,Duration.Inf).length should be (0)
  }

  "addStudent" should "return more than 0 if insert operation is successful" in {
    Await.result(addStudent(Student(name="Student 3",email="student3@email.com")),Duration.Inf) should be > 0
  }

  "updateStudent" should "return more than 0" in {
    Await.result(updateStudent(Student(id=Some(2),name="Student Four",email="studentfour@gmail.com")),Duration.Inf) should be > 0
  }

  "fetchAll" should "return a list length > 0" in {
    println(Await.result(fetchAll(),Duration.Inf))
    Await.result(fetchAll(),Duration.Inf).length should be > 0
  }

  "deleteStudent" should "return more than 0" in {
    Await.result(deleteStudent(2),Duration.Inf) should be > 0
  }
}
