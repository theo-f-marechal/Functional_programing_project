sealed trait Point{}

case class Point2D private(x: Float, y: Float) extends Point{}
case class Point3D private(x: Float, y: Float, z: Float) extends Point{}

object Point {
  def deserialization(line: List[String]): Either[String,Point] ={
    val pattern = """^[+-]?[0-9]+\.?[0-9]*"""
    line match {
      case Nil => Left("No arguments given")
      case _::Nil => Left("Missing at least one argument")
      case x::y::Nil =>
        if (x.matches(pattern) && y.matches(pattern))
          Right(Point2D(x.toFloat,y.toFloat))
        else Left("One of the argument is invalid")
      case x::y::z::Nil =>
        if (x.matches(pattern) && y.matches(pattern) && z.matches(pattern))
          Right(Point3D(x.toFloat,y.toFloat,z.toFloat))
        else Left("One of the argument is invalid")
      case _ => Left("Too much argument")
    }
  }
}
