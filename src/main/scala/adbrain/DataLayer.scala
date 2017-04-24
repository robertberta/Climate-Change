package adbrain

import org.apache.log4j.Logger
import org.apache.spark.SparkContext
import DemoApp.sc
import org.apache.spark.rdd.RDD
import com.datastax.spark.connector._

class DataLayer() {
  var keyspace = "demoapp"
  val table = "temperatures"

  val logger = Logger.getLogger(this.getClass)

  def write(records:RDD[Temperature]): Unit ={
    logger.info("writing to cassandra")
    records.saveToCassandra(keyspace, table)
  }

  def read():RDD[Temperature] ={
    logger.info("reading from cassandra")
    sc.cassandraTable(keyspace, table)
  }

}
