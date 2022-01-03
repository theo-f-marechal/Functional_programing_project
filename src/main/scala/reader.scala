import scala.io.Source

object reader {
  def csv[T](path : String, header: Boolean = false,sep: String = ",")
            (function: List[String] => Either[String,T]):List[Either[String,T]] = {
    val source = Source.fromFile(path)
    val lines = source.getLines.toList
    source.close()
    (if (header) lines.tail else lines).map(_.split(sep).map(_.trim).toList).map(function)
  }
}
