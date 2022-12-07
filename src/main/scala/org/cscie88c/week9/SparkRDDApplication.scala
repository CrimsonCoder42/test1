package org.cscie88c.week9

import org.apache.spark.sql.SparkSession
import com.typesafe.scalalogging.{ LazyLogging }
import org.cscie88c.config.{ ConfigUtils }
import org.cscie88c.utils.{ SparkUtils }
import org.apache.spark.rdd.{ RDD }
import pureconfig.generic.auto._

// write case class below Write a case class, SparkRDDConfig, to read the configuration.
case class SparkRDDConfig(
    name: String,
    masterUrl: String,
    transactionFile: String
  )

// run with: sbt "runMain org.cscie88c.week9.SparkRDDApplication"
object SparkRDDApplication {

  val SPARK_RDD_PATH = "org.cscie88c.spark-rdd-application"

  //application entry point
  def main(args: Array[String]): Unit = {
    implicit val conf: SparkRDDConfig = readConfig() // 1. read configuration
    val spark = SparkUtils.sparkSession(
      conf.name,
      conf.masterUrl
    ) // 2. initialize spark session
    val rddLines = loadData(spark) // 3.load data
    val rddTransactions = lineToTransactions(
      rddLines
    ) // 4. convert lines to transaction objects
    val yearlyTransactionsRDD = transactionsAmountsByYear(
      rddTransactions
    ) // 5. transform data
    printTransactionsAmountsByYear(yearlyTransactionsRDD) // 6. print results
    spark.stop() // 7. stop spark cluster
  }

  // returns the configuration value as a case class. Define a
  //function readConfig that returns the configuration values in the case class.

  def readConfig(): SparkRDDConfig =
    ConfigUtils.loadAppConfig[SparkRDDConfig](SPARK_RDD_PATH)

  //uses the configuration and loads an RDD from the data file
  def loadData(
      spark: SparkSession
    )(implicit
      conf: SparkRDDConfig
    ): RDD[String] = spark.sparkContext.textFile(conf.transactionFile)

  def lineToTransactions(lines: RDD[String]): RDD[CustomerTransaction] =
    MapCollect.mapCollectCase(lines)

  // https://sparkbyexamples.com/apache-spark-rdd/spark-reducebykey-usage-with-examples/
  def transactionsAmountsByYear(
      transactions: RDD[CustomerTransaction]
    ): RDD[(String, Double)] =
    transactions
      .map(n => (n.transactionDate, n.transactionAmount))
      .reduceByKey(_ + _)

  def printTransactionsAmountsByYear(
      transactions: RDD[(String, Double)]
    ): Unit =
    transactions.foreach(transaction =>
      println(s"Year: ${transaction._1}: Sum Transactions:${transaction._2}")
    )

}
