import StructureCSV._

class TestRunway extends munit.FunSuite{
  test("Testing padding"){
    val listToPad = List("269408","6523","00A","80","80","ASPH-G","1","0","H1","","","","","","","","","","","")
    println(Runways.deserialization(listToPad))
  }
}