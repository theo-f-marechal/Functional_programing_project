import StructureCSV.Error
import StructureCSV.Error.{formatError, separateErrorsResults}
import cats.data.NonEmptyList
import cats.data.Validated.Invalid
import cats.implicits.catsSyntaxValidatedId

class TestError extends munit.FunSuite {
  test("Testing separateErrorsResults") {
    val re = separateErrorsResults(List(
      Invalid(NonEmptyList(Error("1"),List(Error("12"),Error("13")))),
      Error("2").invalidNel,
      "3".validNel,
      Error("4").invalidNel,
      "5".validNel))
    println(re)
  }

  test("Testing formatError") {
    val re = formatError(List(" - Line no°0 : 1 12 13  | end"," - Line no°1 : 2  | end"," - Line no°3 : 4  | end"), "/test/TestCSV")
    assertEquals(re,"### Errors for /test/TestCSV ###\n - Line no°0 : 1 12 13  | end\n - Line no°1 : 2  | end\n - Line no°3 : 4  | end\n### End of logs for /test/TestCSV ###")
  }
}