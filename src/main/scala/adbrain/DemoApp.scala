package adbrain


import java.io.File
import java.util.Date

import com.typesafe.config.ConfigFactory
import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._
import org.apache.spark.SparkConf


object DemoApp {

  val sparkConf = new SparkConf()
    .setAppName("DemoApp")
    .setMaster("local")
    .set("spark.cassandra.connection.host", "127.0.0.1")

  val sc = new SparkContext(sparkConf)

  def main(args: Array[String]) {
    val importDataset = new ImportDataset
    val records = importDataset.readAll(new Date(115,3,20),new Date())
    val dataLayer = new DataLayer
    dataLayer.write(records)
  }
}
