import Structure_CSV._
import MongoDB._

object Main {
  def main(args: Array[String]): Unit = {
    val countryEitherList = Reader.csv("./data/countries.csv", header = true, ",")(Country.deserialization)
    val countryList = countryEitherList.collect { case Right(value) => value}
    MongoDB.insertCountry(countryList)

    //val airportEitherList = Reader.csv("./data/airports.csv", header = true, ",")(Airport.deserialization)
    //val airportList = airportEitherList.collect { case Right(value) => value}
    //MongoDB.insertAirport(airportList)
  }
}
