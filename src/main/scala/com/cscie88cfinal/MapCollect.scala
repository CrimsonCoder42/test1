//package com.cscie88cfinal
//import org.apache.spark.sql.SparkSession
//import com.typesafe.scalalogging.{ LazyLogging }
//import com.cscie88cfinal.config.{ ConfigUtils }
//import com.cscie88cfinal.utils.{ SparkUtils }
//import org.apache.spark.rdd.{ RDD }
//import pureconfig.generic.auto._
//
//case class MapCollect()
//
//object MapCollect{
//  // https://alvinalexander.com/scala/best-practice-option-some-none-pattern-scala-idioms/
//  def mapCollectCase(lines: RDD[String]): RDD[Billsandlaws99to22] = {
//    val incomingLines = lines.map(x => Billsandlaws99to22(x))
//    incomingLines.collect {
//      case Some(n) => n
//    }
//  }
//}
