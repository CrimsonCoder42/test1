package com.cscie88cfinal

import org.apache.log4j.{Level, Logger}
import org.apache.spark._
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types.{IntegerType, StringType, StructType}

// ranks congress by how laws were passed
object CongressRankings {

  final case class LawsByElectionCycle(Congress: String)

  def main(args: Array[String]): Unit = {

    Logger.getLogger("org").setLevel(Level.ERROR)

    val spark = SparkSession
      .builder
      .appName("Congress Rankings")
      .master("local[*]")
      .getOrCreate()

    // Create schema
    val politicalSchema = new StructType()
      .add("LegislationNum", StringType, nullable = true)
      .add("Congress", StringType, nullable = true)

    // Loads up CSV by using implicit Schema
    import spark.implicits._

    // Load up dataset and separate by ,
    val congressDS = spark.read
      .option("sep", ",")
      .schema(politicalSchema)
      .csv("src/main/resources/data/congressAndLeg.csv")
      .as[LawsByElectionCycle]

    // Group by congress by election session then order by in count column
    val congressRank = congressDS.groupBy("Congress").count().orderBy(desc("count"))

    congressRank.show()

    spark.stop()
  }
}