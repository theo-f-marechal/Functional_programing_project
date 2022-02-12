import StructureCSV._
import cats.data.NonEmptyList
import cats.data.Validated.{Invalid, Valid}

class TestCountry extends munit.FunSuite{
  test("Testing valid"){
    val listToPad = List("302618","AE","United Arab Emirates","AS","http://en.wikipedia.org/wiki/United_Arab_Emirates","UAE")
    assertEquals(Country.deserialization(listToPad),
      Valid(Country(CountryId(302618),Code("AE"),Name("United Arab Emirates"),Some(Continent("AS")),Some(Keywords("UAE")))))
  }

  // ############################################################################################################
  // Id
  test("Testing error for id: empty"){
    val listToPad = List("","AE","United Arab Emirates","AS","http://en.wikipedia.org/wiki/United_Arab_Emirates","UAE")
    assertEquals(Country.deserialization(listToPad),
      Invalid(NonEmptyList(Error("Country Id must be a Long."),Nil)))
  }

  test("Testing error for id: blank"){
    val listToPad = List("    ","AE","United Arab Emirates","AS","http://en.wikipedia.org/wiki/United_Arab_Emirates","UAE")
    assertEquals(Country.deserialization(listToPad),
      Invalid(NonEmptyList(Error("Country Id must be a Long."),Nil)))
  }

  test("Testing error for id: not Long"){
    val listToPad = List("aaaaa302618","AE","United Arab Emirates","AS","http://en.wikipedia.org/wiki/United_Arab_Emirates","UAE")
    assertEquals(Country.deserialization(listToPad),
      Invalid(NonEmptyList(Error("Country Id must be a Long."),Nil)))
  }

  test("Testing error for id: negative"){
    val listToPad = List("-302618","AE","United Arab Emirates","AS","http://en.wikipedia.org/wiki/United_Arab_Emirates","UAE")
    assertEquals(Country.deserialization(listToPad),
      Invalid(NonEmptyList(Error("Country Id can't be negative."),Nil)))
  }

  // ############################################################################################################
  // Code
  test("Testing error for Code: doesn't exist"){
    val listToPad = List("302618")
    assertEquals(Country.deserialization(listToPad),
      Invalid(NonEmptyList(Error("Country code must exist."), List(Error("Country name must exist.")))))
  }

  test("Testing error for Code: empty"){
    val listToPad = List("302618","","United Arab Emirates","AS","http://en.wikipedia.org/wiki/United_Arab_Emirates","UAE")
    assertEquals(Country.deserialization(listToPad),
      Invalid(NonEmptyList(Error("Country code can't be empty or blank."),Nil)))
  }

  test("Testing error for Code: blank"){
    val listToPad = List("302618","   ","United Arab Emirates","AS","http://en.wikipedia.org/wiki/United_Arab_Emirates","UAE")
    assertEquals(Country.deserialization(listToPad),
      Invalid(NonEmptyList(Error("Country code can't be empty or blank."),Nil)))
  }

  test("Testing error for Code: negative"){
    val listToPad = List("302618","eAE","United Arab Emirates","AS","http://en.wikipedia.org/wiki/United_Arab_Emirates","UAE")
    assertEquals(Country.deserialization(listToPad),
      Invalid(NonEmptyList(Error("Country code must follow the pattern."),Nil)))
  }

  // ############################################################################################################
  // Name
  test("Testing error for Name: doesn't exist"){
    val listToPad = List("302618","AE")
    assertEquals(Country.deserialization(listToPad),
      Invalid(NonEmptyList(Error("Country name must exist."),Nil)))
  }

  test("Testing error for Name: empty"){
    val listToPad = List("302618","AE","","AS","http://en.wikipedia.org/wiki/United_Arab_Emirates","UAE")
    assertEquals(Country.deserialization(listToPad),
      Invalid(NonEmptyList(Error("Country name can't be empty or blank."),Nil)))
  }

  test("Testing error for Name: blank"){
    val listToPad = List("302618","AE","     ","AS","http://en.wikipedia.org/wiki/United_Arab_Emirates","UAE")
    assertEquals(Country.deserialization(listToPad),
      Invalid(NonEmptyList(Error("Country name can't be empty or blank."),Nil)))
  }

  // ############################################################################################################
  // Continent
  test("Testing valid for Continent: doesn't exist"){
    val listToPad = List("302618","AE","United Arab Emirates")
    assertEquals(Country.deserialization(listToPad),
      Valid(Country(CountryId(302618),Code("AE"),Name("United Arab Emirates"),None,None)))
  }

  test("Testing valid for Continent: empty"){
    val listToPad = List("302618","AE","United Arab Emirates","","http://en.wikipedia.org/wiki/United_Arab_Emirates","UAE")
    assertEquals(Country.deserialization(listToPad),
      Valid(Country(CountryId(302618),Code("AE"),Name("United Arab Emirates"),None,Some(Keywords("UAE")))))
  }

  test("Testing valid for Continent: blank"){
    val listToPad = List("302618","AE","United Arab Emirates","   ","http://en.wikipedia.org/wiki/United_Arab_Emirates","UAE")
    assertEquals(Country.deserialization(listToPad),
      Valid(Country(CountryId(302618),Code("AE"),Name("United Arab Emirates"),None,Some(Keywords("UAE")))))
  }

  test("Testing error for Continent: unknown"){
    val listToPad = List("302618","AE","United Arab Emirates","ASe","http://en.wikipedia.org/wiki/United_Arab_Emirates","UAE")
    assertEquals(Country.deserialization(listToPad),
      Invalid(NonEmptyList(Error("Country continent is unknown."),Nil)))
  }

  // ############################################################################################################
  // Keywords
  test("Testing error for Keywords: doesn't exist"){
    val listToPad = List("302618","AE","United Arab Emirates","AS","http://en.wikipedia.org/wiki/United_Arab_Emirates")
    assertEquals(Country.deserialization(listToPad),
      Valid(Country(CountryId(302618),Code("AE"),Name("United Arab Emirates"),Some(Continent("AS")),None)))
  }

  test("Testing valid for Keywords: empty"){
    val listToPad = List("302618","AE","United Arab Emirates","AS","http://en.wikipedia.org/wiki/United_Arab_Emirates","")
    assertEquals(Country.deserialization(listToPad),
      Valid(Country(CountryId(302618),Code("AE"),Name("United Arab Emirates"),Some(Continent("AS")),None)))
  }

  test("Testing valid for Keywords: blank"){
    val listToPad = List("302618","AE","United Arab Emirates","AS","http://en.wikipedia.org/wiki/United_Arab_Emirates","   ")
    assertEquals(Country.deserialization(listToPad),
      Valid(Country(CountryId(302618),Code("AE"),Name("United Arab Emirates"),Some(Continent("AS")),None)))
  }

}