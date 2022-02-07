import StructureCSV._

class TestCountry extends munit.FunSuite{
  test("Testing padding"){
    val listToPad = List("302619","AF","Afghanistan","AS","http://en.wikipedia.org/wiki/Afghanistan","")
    println(Country.deserialization(listToPad))
  }
}