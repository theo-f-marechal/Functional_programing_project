import StructureCSV._
import MongoDB._
import Swing.UI
import Parser.Parser

object Main {
  def main(args: Array[String]): Unit = {
    /*val countryEitherList = Parser.csv("./data/countries.csv", header = true, ",")(Country.deserialization)
    val countryList = countryEitherList.collect { case Right(value) => value}
    Insertion.insertCountry(countryList)

    val airportEitherList = Parser.csv("./data/airports.csv", header = true, ",")(Airport.deserialization)
    val airportList = airportEitherList.collect { case Right(value) => value}
    Insertion.insertAirport(airportList)

    val runwaysEitherList = Parser.csv("./data/runways.csv", header = true, ",")(Runways.deserialization)
    val runwaysList = runwaysEitherList.collect { case Right(value) => value}
    Insertion.insertRunways(runwaysList)*/

    //Reader.query("GL")
    //Reader.query("IO")
    //Reader.getReport1()

    val ui = new UI
    ui.visible = true
  }
}
