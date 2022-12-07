package org.cscie88c.week9

import scala.util.{ Try }

final case class RawTransaction(
    customer_id: String,
    trans_date: String,
    tran_amount: Double
  )
// https://docs.scala-lang.org/tour/case-classes.html
final case class CustomerTransaction(
    customerId: String,
    transactionDate: String,
    transactionAmount: Double
  ) {
  def transactionYear: String =
    transactionDate.split("-")(2)
  def transactionCategory: String =
    if (transactionAmount > 80) "High"
    else "Standard"
}

object CustomerTransaction {
  def apply(csvRow: String): Option[CustomerTransaction] = Try {
    val fields = csvRow.split(",")
    CustomerTransaction(
      customerId = fields(0),
      transactionDate = fields(1),
      transactionAmount = fields(2).toDouble
    )
  }.toOption

  def apply(raw: RawTransaction): CustomerTransaction =
    CustomerTransaction(
      customerId = raw.customer_id,
      transactionDate = raw.trans_date,
      transactionAmount = raw.tran_amount
    )
}
//import org.apache.spark.sql._
//import org.apache.spark.sql.functions._
//import org.apache.spark.sql.types.{IntegerType, LongType, StructType, StringType}
//
//
//object TopSponsors {
//
//  final case class Sponsor(sponsorID: String)
//
//  def main(args: Array[String]) {
//
//
//    // Use new SparkSession
//    val spark = SparkSession
//      .builder
//      .appName("Sponsorship rankings")
//      .master("local[*]")
//      .getOrCreate()
//
//    // create new schema https://sparkbyexamples.com/spark/spark-schema-explained-with-examples/
//    val sponsorSchema = new StructType()
//      .add("billNum", StringType, nullable = true)
//      .add("billTitle", StringType, nullable = true)
//      .add("billSponsor", StringType, nullable = true)
//      .add("billDates", StringType, nullable = true)
//
//    import spark.implicits._
//
//    // create dataset https://www.educba.com/spark-dataset/
//    val sponsorSet = spark.read
//      .option("sep", ",")
//      .schema(sponsorSchema)
//      .csv("src/main/resources/data/LegislationAndSponsors.csv")
//      .as[Sponsor]
//
//    // https://sparkbyexamples.com/spark/spark-how-to-sort-dataframe-column-explained/
//    val hardestWorking = sponsorSet.groupBy("billSponsor")
//      .count()
//      .orderBy(desc("count"))
//
//    // convert to csv and store in cleaned Data
//    hardestWorking.write.format("csv").save("src/main/resources/cleanedData")
//
//    spark.stop()
//  }
//
//}