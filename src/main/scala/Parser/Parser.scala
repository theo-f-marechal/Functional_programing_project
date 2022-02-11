package Parser

import StructureCSV.Error.AllErrorsOr

import scala.io.Source

object Parser {
  def csv[T](path: String, header: Boolean = false, sep: String = ",")
            (function: List[String] => AllErrorsOr[T]): List[AllErrorsOr[T]] = {
    val source = Source.fromFile(path)
    val lines = source.getLines.toList
    source.close()
    (if (header) lines.tail else lines)
      .map(_.split(sep).map(_.replaceAll("^\"|\"$", "").trim).toList)
      .map(function)
  }
}
