import Structure_CSV._

class Test_Country extends munit.FunSuite{
  test("Testing padding"){
    val listToPad = List("302618","AE","United Arab Emirates","AS","http://en.wikipedia.org/wiki/United_Arab_Emirates","UAE")
    println(Country.deserialization(listToPad))
  }
}