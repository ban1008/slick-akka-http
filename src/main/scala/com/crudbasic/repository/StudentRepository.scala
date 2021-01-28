package com.crudbasic.repository

import scala.concurrent.ExecutionContext.Implicits.global
import com.crudbasic.connection.{DBComponent, MySqlDBComponent}
import slick.jdbc.meta.MTable
import scala.concurrent.Future
trait StudentRepository extends StudentTable {
  this: DBComponent =>

  import driver.api._

  def createSchemaIfNotExists:Future[List[Unit]] = {
    val existing = db.run(MTable.getTables)
    val tables=List(studentTableQuery)
    val f = existing.flatMap( v => {
      val names = v.map(mt => mt.name.name)
      val createIfNotExist = tables.filter( table =>
        (!names.contains(table.baseTableRow.tableName))).map(_.schema.create)
      db.run(DBIO.sequence(createIfNotExist))
    })
    studentTableQuery.schema.createIfNotExists
    f
  }

  def addStudent(student: Student):Future[Int]=db.run{
    studentTableQuery += student
  }

  def updateStudent(stud:Student):Future[Int]=db.run{
    studentTableQuery.filter(_.id===stud.id).update(stud)
  }

  def deleteStudent(id:Int):Future[Int]=db.run{
    studentTableQuery.filter(_.id===id).delete
  }

  def fetchAll():Future[List[Student]]=db.run{
    studentTableQuery.to[List].result
  }
}

trait StudentTable {
  this: DBComponent =>

  import driver.api._

  val studentTableQuery = TableQuery[StudentTable]
  class StudentTable(tag: Tag) extends Table[Student](tag, "student"){
    def id = column[Int]("ID",O.PrimaryKey,O.AutoInc)
    def name = column[String]("Name")
    def email =column[String]("Email")
    def * = (id.?,name,email) <> (Student.tupled, Student.unapply)
  }

}

object StudentRepository extends StudentRepository with MySqlDBComponent
case class Student(id:Option[Int]=None,name:String,email:String)