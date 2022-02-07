import StructureCSV._
import MongoDB._
import Swing.UI

object Main {
  def main(args: Array[String]): Unit = {
    //val countryEitherList = Reader.csv("./data/countries.csv", header = true, ",")(Country.deserialization)
    //val countryList = countryEitherList.collect { case Right(value) => value}
    //MongoDB.insertCountry(countryList)

    //val airportEitherList = Reader.csv("./data/airports.csv", header = true, ",")(Airport.deserialization)
    //val airportList = airportEitherList.collect { case Right(value) => value}
    //MongoDB.insertAirport(airportList)

    //val runwaysEitherList = Reader.csv("./data/runways.csv", header = true, ",")(Runways.deserialization)
    //val runwaysList = runwaysEitherList.collect { case Right(value) => value}
    //MongoDB.insertRunways(runwaysList)
    val ui = new UI
    ui.visible = true
  }
}
