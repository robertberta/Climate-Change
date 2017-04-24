import adbrain.{DemoApp, ImportDataset}
import org.scalatest.FlatSpec
import org.scalatest.Matchers
/**
 * Created by robertb on 20.06.2015.
 */
import java.util.Date
class DemoAppTest extends FlatSpec with Matchers {
    val importDataset=new ImportDataset()

//    "getDates()" should " return a list dates starting from provided param to today " in {
//        importDataset.range(new Date(115,5,30),new Date(115,6,1)).collect() should be(List("2015-06-30","2015-07-01"))
//    }
      "test" {
        DemoApp.main(null)
        0
      }

}
