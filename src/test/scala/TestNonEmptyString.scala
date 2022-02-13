import StructureCSV.NonEmptyString

class TestNonEmptyString extends munit.FunSuite {
  test("Empty String") {
    val t = NonEmptyString.newNES("")
    assertEquals(t ,None)
  }

  test("Non Empty String"){
    val str = NonEmptyString.newNES("a")
    assertEquals({
      str match{
        case None => ""
        case Some(x) => x.str}}
      ,"a")
  }
}
