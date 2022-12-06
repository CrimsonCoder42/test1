package com.cscie88cfinal.spark

import org.apache.spark.sql.SparkSession

// parse election xml info for senate
object ReadingXML extends App{

  val spark = SparkSession
    .builder
    .appName("Read_XML")
    .master("local[*]")
    .getOrCreate()

  //https://www.edureka.co/community/53045/read-multiple-xml-files-in-spark

    val votes = spark.read
      .option("rootTag","roll_call_vote")
      .option("rowTag", "member")
      .format("com.databricks.spark.xml").load("src/main/resources/data/senate_votes/*.xml")

  val document = spark.read
    .option("rootTag", "roll_call_vote")
    .option("rowTag", "document")
    .format("com.databricks.spark.xml").load("src/main/resources/data/senate_votes/*.xml")

  votes.show()
  document.show()
}
