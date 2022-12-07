package com.cscie88cfinal

import org.apache.spark.sql.{Row, SparkSession}
import org.apache.spark.sql.types.{DateType, IntegerType, StringType, StructField, StructType}

object ScalaSparkDF extends App {

  // creating a spark session https://sparkbyexamples.com/spark/spark-schema-explained-with-examples/
  val spark = SparkSession.builder()
    .appName("Legislation Basics")
    .config("spark.master", "local")
    .getOrCreate()

  // reading a DF
  val firstDF = spark.read
    .format("csv")
    .option("inferSchema", "true")
    .load("src/main/resources/data/Bills and laws 1999 to 2022 - 1999to2022.csv")

  // showing a DF
  firstDF.show()
  firstDF.printSchema()

  // get rows
  firstDF.take(10).foreach(println)


  // schema as a struct type

  val LegSchema = StructType(
    Array(
      StructField("Legislation Number", StringType),
      StructField("URL", StringType),
      StructField("Congress", StringType),
      StructField("Title", StringType),
      StructField("Amends Bill", StringType),
      StructField("Sponsor", StringType),
      StructField("Date Offered", DateType),
      StructField("Date of Introduction", StringType),
      StructField("Number of Cosponsors", IntegerType),
      StructField("Date Submitted", DateType),
      StructField("Date Proposed", DateType),
      StructField("Committees", StringType),
      StructField("Latest Action", StringType),
      StructField("Latest Action Date", DateType),
      StructField("Cosponsors", StringType),
      StructField("Subject", StringType),
      StructField("Related Bills", StringType),
      StructField("Latest Summary", StringType)
    )
  )

  // obtain a schema
  val legislationSchema = firstDF.schema

  // read a DF with your schema
  val legislationDFWithSchema = spark.read
    .format("csv")
    .schema(legislationSchema)
    .load("src/main/resources/data/Bills and laws 1999 to 2022 - 1999to2022.csv")

  // create rows by hand
  val myRow = Row("chevrolet chevelle malibu", 18.0, 8L, 307.0, 130L, 3504L, 12.0, "1970-01-01", "USA")

  // create DF from Tuples
  val cars = Seq(
    ("chevrolet chevelle malibu", 18.0, 8L, 307.0, 130L, 3504L, 12.0, "1970-01-01", "USA"),
    ("buick skylark 320", 15.0, 8L, 350.0, 165L, 3693L, 11.5, "1970-01-01", "USA"),
    ("plymouth satellite", 18.0, 8L, 318.0, 150L, 3436L, 11.0, "1970-01-01", "USA"),
    ("amc rebel sst", 16.0, 8L, 304.0, 150L, 3433L, 12.0, "1970-01-01", "USA"),
    ("ford torino", 17.0, 8L, 302.0, 140L, 3449L, 10.5, "1970-01-01", "USA"),
    ("ford galaxie 500", 15.0, 8L, 429.0, 198L, 4341L, 10.0, "1970-01-01", "USA"),
    ("chevrolet impala", 14.0, 8L, 454.0, 220L, 4354L, 9.0, "1970-01-01", "USA"),
    ("plymouth fury iii", 14.0, 8L, 440.0, 215L, 4312L, 8.5, "1970-01-01", "USA"),
    ("pontiac catalina", 14.0, 8L, 455.0, 225L, 4425L, 10.0, "1970-01-01", "USA"),
    ("amc ambassador dpl", 15.0, 8L, 390.0, 190L, 3850L, 8.5, "1970-01-01", "USA")
  )

  val manualCarsDF = spark.createDataFrame(cars) // schema auto-inferred

  // note: DFs have schemas, rows do not

  // create DFs with implicits

  import spark.implicits._

  val manualCarsDFWithImplicits = cars.toDF("Name", "MPG", "Cylinders", "Displacement", "HP", "Weight", "Acceleration", "Year", "CountryOrigin")

  manualCarsDF.printSchema()
  manualCarsDFWithImplicits.printSchema()

  println("-" * 50)

}
