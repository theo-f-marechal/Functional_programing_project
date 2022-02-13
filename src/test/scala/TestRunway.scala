import StructureCSV._
import cats.data.NonEmptyList
import cats.data.Validated.{Invalid, Valid}

class TestRunway extends munit.FunSuite{
  test("Testing valid"){
    val listToPad = List("269408","6523","00A","80","80","ASPH-G","1","0","H1","","","","","","","","","","","")
    assertEquals(Runways.deserialization(listToPad),
      Valid(Runways(RunwayId(269408),AirportId(6523),AirportIdent("00A"),Some(Surface("ASPH-G")),Some(LeIdent("H1")))))
  }

  // ############################################################################################################
  // Id
  test("Testing error for id: empty"){
    val listToPad = List("","6523","00A","80","80","ASPH-G","1","0","H1","","","","","","","","","","","")
    assertEquals(Runways.deserialization(listToPad),
      Invalid(NonEmptyList(Error("Runway Id can't be empty or blank."),Nil)))
  }

  test("Testing error for id: blank"){
    val listToPad = List("    ","6523","00A","80","80","ASPH-G","1","0","H1","","","","","","","","","","","")
    println(Runways.deserialization(listToPad))
  }

  test("Testing error for id: not a Long"){
    val listToPad = List("aaaaa269408","6523","00A","80","80","ASPH-G","1","0","H1","","","","","","","","","","","")
    assertEquals(Runways.deserialization(listToPad),
      Invalid(NonEmptyList(Error("Runway Id must be a Long."),Nil)))
  }

  test("Testing error for id: negative"){
    val listToPad = List("-269408","6523","00A","80","80","ASPH-G","1","0","H1","","","","","","","","","","","")
    println(Runways.deserialization(listToPad),
      Invalid(NonEmptyList(Error("Runway Id can't be negative."),Nil)))
  }

  // ############################################################################################################
  // Airport Ref / Airport Ident

  // See the TestAirport file for those two parameters


  // ############################################################################################################
  // Surface
  test("Testing valid for Surface: doesn't exist"){
    val listToPad = List("269408","6523","00A","80","80")
    println(Runways.deserialization(listToPad),
      Valid(Runways(RunwayId(269408),AirportId(6523),AirportIdent("00A"),None,None)))
  }

  test("Testing valid for Surface: empty"){
    val listToPad = List("269408","6523","00A","80","80","","1","0","H1","","","","","","","","","","","")
    assertEquals(Runways.deserialization(listToPad),
      Valid(Runways(RunwayId(269408),AirportId(6523),AirportIdent("00A"),None,Some(LeIdent("H1")))))
  }

  test("Testing valid for Surface: blank"){
    val listToPad = List("269408","6523","00A","80","80","    ","1","0","H1","","","","","","","","","","","")
    assertEquals(Runways.deserialization(listToPad),
      Valid(Runways(RunwayId(269408),AirportId(6523),AirportIdent("00A"),None,Some(LeIdent("H1")))))
  }

  // ############################################################################################################
  // leIdent
  test("Testing valid for leIdent: doesn't exist"){
    val listToPad = List("269408","6523","00A","80","80","ASPH-G","1","0")
    assertEquals(Runways.deserialization(listToPad),
      Valid(Runways(RunwayId(269408),AirportId(6523),AirportIdent("00A"),Some(Surface("ASPH-G")),None)))
  }

  test("Testing valid for leIdent: empty"){
    val listToPad = List("269408","6523","00A","80","80","ASPH-G","1","0","","","","","","","","","","","","")
    assertEquals(Runways.deserialization(listToPad),
      Valid(Runways(RunwayId(269408),AirportId(6523),AirportIdent("00A"),Some(Surface("ASPH-G")),None)))
  }

  test("Testing valid for leIdent: blank"){
    val listToPad = List("269408","6523","00A","80","80","ASPH-G","1","0","    ","","","","","","","","","","","")
    assertEquals(Runways.deserialization(listToPad),
      Valid(Runways(RunwayId(269408),AirportId(6523),AirportIdent("00A"),Some(Surface("ASPH-G")),None)))
  }
}