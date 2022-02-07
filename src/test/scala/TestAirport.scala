import StructureCSV._

class TestAirport extends munit.FunSuite{
  test("Testing padding"){
   val listToPad = List("6523","00A","heliport","Total Rf Heliport","40.07080078125","-74.93360137939453","11","NA","US","US-PA","Bensalem","no","00A","","00A","","","")
    println(Airport.deserialization(listToPad))
  }
}