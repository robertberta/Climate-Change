package adbrain

import java.util.Date

import org.scalatest.{Matchers, FlatSpec}
import DemoApp.sc

/**
 * Created by robertb on 20.06.2015.
 */
class DataLayerTest extends FlatSpec with Matchers {

  val dataLayer=new DataLayer()
  dataLayer.keyspace="test"

  "write" should " save to cassandra " in {
    val records = List(Temperature("date1","station1",Some(1),Some(11)),Temperature("date1","station2",Some(1),Some(22)),Temperature("date2","station2",Some(2),Some(22)))
    val writeRdd= sc.parallelize(records)
    dataLayer.write(writeRdd)
    val readRdd = dataLayer.read()
    records should be(readRdd.collect())
  }
}
