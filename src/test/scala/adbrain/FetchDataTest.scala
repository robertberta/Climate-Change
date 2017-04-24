package adbrain
/**
 * Created by robertb on 20.06.2015.
 */

import java.util.Date

import org.scalatest.FlatSpec
import org.scalatest.Matchers

class FetchDataTest  extends FlatSpec with Matchers {

  "getByDate()" should " return 25282 elements " in {
    val results= FetchData("2010-05-01")
    results.size should be(25282)
  }

}
