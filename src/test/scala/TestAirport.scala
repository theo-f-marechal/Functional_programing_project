import StructureCSV._
import cats.data.NonEmptyList
import cats.data.Validated.{Invalid, Valid}

class TestAirport extends munit.FunSuite{
  test("Testing valid"){
   val listToPad = List("6523","00A","heliport","Total Rf Heliport","40.07080078125","-74.93360137939453","11","NA","US","US-PA","Bensalem","no","00A","","00A","","","")
   assertEquals(Airport.deserialization(listToPad),
      Valid(Airport(AirportId(6523),AirportIdent("00A"),AirportType("heliport"),AirportName("Total Rf Heliport"),IsoCountry("US"))))
  }

  // ############################################################################################################
  // Id
  test("Testing error for id: empty"){
    val listToPad = List("","00A","heliport","Total Rf Heliport","40.07080078125","-74.93360137939453","11","NA","US","US-PA","Bensalem","no","00A","","00A","","","")
    assertEquals(Airport.deserialization(listToPad),
      Invalid(NonEmptyList(Error("Airport can't be empty or blank"),Nil)))
  }

  test("Testing error for id: blank"){
    val listToPad = List("  ","00A","heliport","Total Rf Heliport","40.07080078125","-74.93360137939453","11","NA","US","US-PA","Bensalem","no","00A","","00A","","","")
    assertEquals(Airport.deserialization(listToPad),
      Invalid(NonEmptyList(Error("Airport can't be empty or blank"),Nil)))
  }

  test("Testing error for id: not a Long"){
    val listToPad = List("LLL","00A","heliport","Total Rf Heliport","40.07080078125","-74.93360137939453","11","NA","US","US-PA","Bensalem","no","00A","","00A","","","")
    assertEquals(Airport.deserialization(listToPad),
      Invalid(NonEmptyList(Error("Airport Id must be a Long."),Nil)))
  }

  test("Testing error for id: negative"){
    val listToPad = List("-89","00A","heliport","Total Rf Heliport","40.07080078125","-74.93360137939453","11","NA","US","US-PA","Bensalem","no","00A","","00A","","","")
    assertEquals(Airport.deserialization(listToPad),
      Invalid(NonEmptyList(Error("Airport Id can't be negative."),Nil)))
  }

  // ############################################################################################################
  // Ident
  test("Testing error for Ident: doesn't exist"){
    val listToPad = List("6523")
    assertEquals(Airport.deserialization(listToPad),
      Invalid(NonEmptyList(Error("Airport Ident must exist."),
        List(Error("Airport type must exist."), Error("Airport name must exist."),Error("Country iso must exist."))
      )))
  }

  test("Testing error for Ident: empty"){
    val listToPad = List("6523","","heliport","Total Rf Heliport","40.07080078125","-74.93360137939453","11","NA","US","US-PA","Bensalem","no","00A","","00A","","","")
    assertEquals(Airport.deserialization(listToPad),
      Invalid(NonEmptyList(Error("Airport Ident can't be empty or blank."),Nil)))
  }

  test("Testing error for Ident: blank"){
    val listToPad = List("6523","     ","heliport","Total Rf Heliport","40.07080078125","-74.93360137939453","11","NA","US","US-PA","Bensalem","no","00A","","00A","","","")
    assertEquals(Airport.deserialization(listToPad),
      Invalid(NonEmptyList(Error("Airport Ident can't be empty or blank."),Nil)))
  }

  // ############################################################################################################
  // Type
  test("Testing error for Type: doesn't exist"){
    val listToPad = List("6523","00A")
    assertEquals(Airport.deserialization(listToPad),
      Invalid(NonEmptyList(Error("Airport type must exist."),
        List(Error("Airport name must exist."),Error("Country iso must exist."))
      )))
  }

  test("Testing error for Type: empty"){
    val listToPad = List("6523","00A","","Total Rf Heliport","40.07080078125","-74.93360137939453","11","NA","US","US-PA","Bensalem","no","00A","","00A","","","")
    assertEquals(Airport.deserialization(listToPad),
      Invalid(NonEmptyList(Error("Airport type can't be empty or blank."),Nil)))
  }

  test("Testing error for Type: blank"){
    val listToPad = List("6523","00A","      ","Total Rf Heliport","40.07080078125","-74.93360137939453","11","NA","US","US-PA","Bensalem","no","00A","","00A","","","")
    assertEquals(Airport.deserialization(listToPad),
      Invalid(NonEmptyList(Error("Airport type can't be empty or blank."),Nil)))
  }

  // ############################################################################################################
  // Name
  test("Testing error for Name: doesn't exist"){
    val listToPad = List("6523","00A","heliport")
    assertEquals(Airport.deserialization(listToPad),
      Invalid(NonEmptyList(Error("Airport name must exist."),
        List(Error("Country iso must exist."))
      )))
  }

  test("Testing error for Name: empty"){
    val listToPad = List("6523","00A","heliport","","40.07080078125","-74.93360137939453","11","NA","US","US-PA","Bensalem","no","00A","","00A","","","")
    assertEquals(Airport.deserialization(listToPad),
      Invalid(NonEmptyList(Error("Airport name can't be empty or blank."),Nil)))
  }

  test("Testing error for Name: blank"){
    val listToPad = List("6523","00A","heliport","   ","40.07080078125","-74.93360137939453","11","NA","US","US-PA","Bensalem","no","00A","","00A","","","")
    assertEquals(Airport.deserialization(listToPad),
      Invalid(NonEmptyList(Error("Airport name can't be empty or blank."),Nil)))
  }

  // ############################################################################################################
  // Iso
  test("Testing error for Country Iso: doesn't exist"){
    val listToPad = List("6523","00A","heliport","Total Rf Heliport")
    assertEquals(Airport.deserialization(listToPad),
      Invalid(NonEmptyList(Error("Country iso must exist."),Nil)))
  }

  test("Testing error for Country Iso: empty"){
    val listToPad = List("6523","00A","heliport","Total Rf Heliport","40.07080078125","-74.93360137939453","11","NA","","US-PA","Bensalem","no","00A","","00A","","","")
    assertEquals(Airport.deserialization(listToPad),
      Invalid(NonEmptyList(Error("Country iso can't be empty or blank."),Nil)))
  }

  test("Testing error for Country Iso: blank"){
    val listToPad = List("6523","00A","heliport","Total Rf Heliport","40.07080078125","-74.93360137939453","11","NA","    ","US-PA","Bensalem","no","00A","","00A","","","")
    assertEquals(Airport.deserialization(listToPad),
      Invalid(NonEmptyList(Error("Country iso can't be empty or blank."),Nil)))
  }

  test("Testing error for Country Iso: bad format 1"){
    val listToPad = List("6523","00A","heliport","Total Rf Heliport","40.07080078125","-74.93360137939453","11","NA","aez","US-PA","Bensalem","no","00A","","00A","","","")
    assertEquals(Airport.deserialization(listToPad),
      Invalid(NonEmptyList(Error("Country iso's format is incorrect."),Nil)))
  }

  test("Testing error for Country Iso: bad format 2"){
    val listToPad = List("6523","00A","heliport","Total Rf Heliport","40.07080078125","-74.93360137939453","11","NA","7a","US-PA","Bensalem","no","00A","","00A","","","")
    assertEquals(Airport.deserialization(listToPad),
      Invalid(NonEmptyList(Error("Country iso's format is incorrect."),Nil)))
  }
}