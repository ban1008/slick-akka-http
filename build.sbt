name := "slick-crud-akka-http-project"

version := "0.1"

scalaVersion := "2.13.4"
val akkaVersion = "2.5.26"
val akkaHttpVersion = "10.1.11"
libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "3.2.2" % "test",
  "com.typesafe.slick" %% "slick" % "3.3.2",
  "org.slf4j" % "slf4j-nop" % "1.6.4",
  "com.typesafe.slick" %% "slick-hikaricp" % "3.3.2",
  "mysql" % "mysql-connector-java" % "8.0.13",
  "com.h2database" % "h2" % "1.3.166",
  "com.typesafe.akka" %% "akka-stream" % akkaVersion,
  "com.typesafe.akka" %% "akka-http" % akkaHttpVersion,
  "com.typesafe.akka" %% "akka-http-spray-json" % akkaHttpVersion,
  "net.codingwell" %% "scala-guice" % "4.2.11",
  "com.typesafe.akka" %% "akka-stream-testkit" % akkaVersion,
  "com.typesafe.akka" %% "akka-http-testkit" % akkaHttpVersion,
  "com.typesafe.akka" %% "akka-actor-testkit-typed" % akkaVersion % Test,
)




scalacOptions ++= Seq("-deprecation", "-feature")
