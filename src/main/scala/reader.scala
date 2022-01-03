import scala.io.Source

object reader {
  def csv[T](path : String, header: Boolean = false,sep: String = ",")
            (function: (String,String) => Either[String,T]):List[Either[String,T]] = {
    val source = Source.fromFile(path)
    val fileContents = source.getLines.toList
    source.close
    val lines = (fileContents, header) match {
      case (Nil,_) => fileContents
      case (_,false) => fileContents
      case (_::tail,_) => tail
    }
    lines.map((x:String) => function(x,sep))
  }
}
