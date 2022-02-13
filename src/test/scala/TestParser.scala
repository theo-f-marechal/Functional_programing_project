import StructureCSV.Error
import cats.data.Validated.Valid
import cats.implicits.catsSyntaxValidatedId

class TestParser extends munit.FunSuite{
  test("test parser with header") {
    val re = Parser.Parser.csv("src/test/TestData/dummyDataWithHeader.csv",header = true)(x => Error(x.foldLeft("")((acc, elt) => acc + elt + " ")).validNel)
    assertEquals(re,
      List(Valid(Error("1 l 1l ")), Valid(Error("2 ll 2l ")), Valid(Error("3 lll 3l ")), Valid(Error("4 llll 4l "))))
  }

  test("test parser without header") {
    val re = Parser.Parser.csv("src/test/TestData/dummyDataWithoutHeader.csv")(x => Error(x.foldLeft("")((acc, elt) => acc + elt + " ")).validNel)
    assertEquals(re,
      List(Valid(Error("1 l 1l ")), Valid(Error("2 ll 2l ")), Valid(Error("3 lll 3l ")), Valid(Error("4 llll 4l "))))
  }

}
