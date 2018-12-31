package org.sstudio.stonehenge.build

import sbt._
import Keys._

object Dependencies {

  private[this] val akkaHttpVersion = "0.9.16"
  private[this] val sparkVersion = "1.9.19"
  private[this] val log4jVersion = "2.6.2"
  private[this] val circeVersion = "0.8.0"

  val typesafeConfig = "com.typesafe" % "config" % "1.3.0"
  val jodaTime = "joda-time" % "joda-time" % "2.3"
  val akka = "com.typesafe.akka" %% "akka-actor" % "2.4.14"
  val akkaHttp = "com.typesafe.akka" %% "akka-http" % akkaHttpVersion
  val akkaHttpCore = "com.typesafe.akka" %% "akka-http-core" % akkaHttpVersion
  val sparkCore = "org.apache.spark" %% "spark-core" % sparkVersion
  val sparkSql = "org.apache.spark" %% "spark-sql" % sparkVersion
  val log4jCore = "org.apache.logging.log4j" % "log4j-core" % log4jVersion
  val log4jApi = "org.apache.logging.log4j" % "log4j-api" % log4jVersion
  val log4jWeb = "org.apache.logging.log4j" % "log4j-web" % log4jVersion
  val scalaj = "org.scalaj" % "scalaj-http_2.11" % "2.3.0"
  val zeromq = "org.zeromq" % "jeromq" % "0.3.6"
  val circeCore =  "io.circe" %% "circe-core" % circeVersion
  val circeGeneric = "io.circe" %% "circe-generic" % circeVersion
  val circeParse = "io.circe" %% "circe-parser" % circeVersion
  val doobie = "org.tpolecat" %% "doobie-core-cats" % "0.4.2"
}