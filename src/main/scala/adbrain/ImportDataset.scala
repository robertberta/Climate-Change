/**
 * Created by robertb on 20.06.2015.
 */
package adbrain
import java.text.SimpleDateFormat
import java.util.Date
import DemoApp.sc

import org.apache.commons.lang.time.DateUtils
import org.apache.log4j.Logger
import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD
import scalaj.http.{HttpOptions, Http}
import DemoApp.sc

class ImportDataset() {
  val logger = Logger.getLogger(this.getClass)

  def range(start:Date,end:Date):List[String] = {
    val formatter = new SimpleDateFormat("yyyy-MM-dd")

    val timestampRange = (start.getTime to end.getTime by DateUtils.MILLIS_PER_DAY)
    timestampRange.map(timestamp => formatter.format(new Date(timestamp))).toList
  }

  def readAll(start:Date,end:Date):RDD[Temperature] = {
    val dates = sc.parallelize(range(start,end))

    logger.info("total days to read" + dates.count())
    dates.flatMap(FetchData(_))
  }
}
