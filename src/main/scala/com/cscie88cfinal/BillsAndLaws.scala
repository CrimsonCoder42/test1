package com.cscie88cfinal

import org.apache.spark.sql.SparkSession
import com.typesafe.scalalogging.{ LazyLogging }
import com.cscie88cfinal.config.ConfigUtils
import com.cscie88cfinal.utils.{ SparkUtils }
import org.apache.spark.sql.{ Dataset }
import pureconfig.generic.auto._


case class SparkDSConfig(
                          name: String,
                          masterUrl: String,
                          transactionFile: String
                        )

object BillsAndLaws {

  val SPARK_DS_PATH = "com.cscie88cfinal.spark-legislation-application"

  // application main entry point
  def main(args: Array[String]): Unit = {
    implicit val conf: SparkDSConfig = readConfig()
    val spark = SparkUtils.sparkSession(conf.name, conf.masterUrl)
    val transactionDS = loadData(spark)
    transactionDS.show()
    spark.stop()
  }
 // All this abstraction is killing me I can't figure out values like this.

  def readConfig(): SparkDSConfig =
    ConfigUtils.loadAppConfig[SparkDSConfig](SPARK_DS_PATH)

  def loadData(
                spark: SparkSession
              )(implicit
                conf: SparkDSConfig
              ): Dataset[String] = { // <- Doesn't want to take Billsandlaws99to22 Error
    import spark.implicits._
    val cleanContent = spark.sparkContext.textFile(conf.transactionFile)
    cleanContent.toDS()
  }

}
