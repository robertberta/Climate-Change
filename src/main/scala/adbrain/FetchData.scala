/**
 * Created by robertb on 20.06.2015.
 */
package adbrain

import net.liftweb.json
import scalaj.http.{HttpRequest, HttpOptions, Http}
import net.liftweb.json._
import org.apache.log4j.Logger
import java.util.Date

case class Results(date: String, datatype: String, station: String, value: Short)

case class Response(metadata: MetaData, results: List[Results])

case class MetaData(resultset: ResultSet)

case class ResultSet(offset: Short, count: Short, limit: Short)

case class Temperature(date: String, station: String, tmax: Option[Short], tmin: Option[Short])

object FetchData {

  val requestTemplate =
    Http("http://www.ncdc.noaa.gov/cdo-web/api/v2/data")
      .param("datasetid", "GHCND")
      .param("limit", "1000")
      .header("token", "ySuvzlVvpVKbbUDdGUwgABAlSKWGLmNp")
      .option(HttpOptions.readTimeout(10000))

  val logger = Logger.getLogger(this.getClass)

  def fetch(date: String, dataType: String, offset: Int = 0): List[Results] = {
    logger.info("fetch" + List(date, dataType, offset).mkString("(", ",", ")"))

    val request = requestTemplate
      .param("offset", offset.toString)
      .param("datatypeid", dataType)
      .param("startdate", date)
      .param("enddate", date)

    implicit val formats = DefaultFormats // Brings in default date formats etc.
    val httpResponse = request.asString
    if (httpResponse.isError) {
      throw new Exception(httpResponse.body)
    }

    val response = json.parse(request.asString.body).extract[Response]
    logger.info("received " + response.results.size + "objects")

    val resultset = response.metadata.resultset

    if (resultset.count > resultset.offset + resultset.limit) {
      response.results ::: fetch(date, dataType, resultset.offset + resultset.limit)
    }
    else {
      response.results
    }
  }

  def apply(date: String) = {
    val records = fetch(date, "TMAX") ::: fetch(date, "TMIN")
    convert(records)
  }

  def convert(records: List[Results]): List[Temperature] = {
    records.groupBy(_.station).map({ recordsByStation =>
      val sortedByDataType = recordsByStation._2.sortBy(_.datatype)
      sortedByDataType match {
        case List(Results(date, "TMAX", station, tmax), Results(_, "TMIN", _, tmin)) => Temperature(date, station, Some(tmax), Some(tmin))
        case List(Results(date, "TMAX", station, tmax)) => Temperature(date, station, Some(tmax), None)
        case List(Results(date, "TMIN", station, tmin)) => Temperature(date, station, None, Some(tmin))
        case _ => throw new Exception("malformed")
      }
    }).toList
  }
}
