sealed trait Point{}

case class Point2D private(x: Float, y: Float) extends Point{}
case class Point3D private(x: Float, y: Float, z: Float) extends Point {}

object Point {
  def newPoint2D(x: Float, y: Float): Point = Point2D(x,y)
  def newPoint3D(x: Float, y: Float, z: Float): Point = Point3D(x,y,z)

  def deserialization(line: String,sep: String = ","): Either[String,Point] = {
    val pattern2 = ("""^[+-]?[0-9]+\.?[0-9]*\""" +
      sep + """[+-]?[0-9]+\.?[0-9]*$""").r
    val pattern3 = ("""^[+-]?[0-9]+\.?[0-9]*\""" +
      sep + """[+-]?[0-9]+\.?[0-9]*\""" +
      sep + """[+-]?[0-9]+\.?[0-9]*$""").r
    line match {
      case pattern2() => Right(newPoint2D(
        line.split(sep)(0).toFloat,
        line.split(sep)(1).toFloat
      ))
      case pattern3() => Right(newPoint3D(
        line.split(sep)(0).toFloat,
        line.split(sep)(1).toFloat,
        line.split(sep)(2).toFloat
      ))
      case _ => Left("No point could be built from the line: " + line)
    }
  }
}
