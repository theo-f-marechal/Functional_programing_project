import NonEmptyString.newNES

class Test_NonEmptyString extends munit.FunSuite {
  test("Empty String") {
    val t = newNES("")
    assertEquals(t ,None)
  }

  test("Non Empty String"){
    val t = newNES("a")
    assertEquals({t match{
      case None => ""
      case Some(x) => x.str}}
      ,"a")
  }
}
