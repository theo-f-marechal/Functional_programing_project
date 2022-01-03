import Point.deserialization

object Main {
  def main(args: Array[String]): Unit = {
    val ListPoint = reader.csv("./data/points.csv",header = true, ";")(deserialization)
    println(ListPoint)
  }
}
