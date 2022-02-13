package Swing
import MongoDB.Insertion.emptyDb
import MongoDB.{Insertion, Reader}
import Parser.Parser
import StructureCSV.Error.AllErrorsOr
import StructureCSV.{Airport, Code, Country, Error, NonEmptyString, Runways}
import cats.data.Validated.{Invalid, Valid}

import scala.concurrent.Await
import scala.concurrent.duration.Duration
import scala.swing.MenuBar.NoMenuBar.border
import scala.swing._
import scala.swing.event.ButtonClicked


class UI extends MainFrame {

  def restrictHeight(s: Component): Unit = {
    s.maximumSize = new Dimension(Short.MaxValue, s.preferredSize.height)
  }

  preferredSize = new Dimension(1000, 240)

  title = "functional programming project"

  border = Swing.EmptyBorder(10, 10, 10, 10)


  // ############################################################################################################

  // DB tab

  val db = new BoxPanel(Orientation.Vertical)
  val populateDB: Button = new Button("Populate the Data Base") {}
  val emptyDB: Button = new Button("Empty the Data Base") {}
  val dbButtons = new BoxPanel(Orientation.Horizontal)
  dbButtons.contents += Swing.Glue
  dbButtons.contents += populateDB
  dbButtons.contents += Swing.HStrut(10)
  dbButtons.contents += emptyDB
  dbButtons.contents += Swing.Glue
  val dbErrorMsg: TextArea = new TextArea("") { editable = false }
  val dbErrorMsgScroll = new ScrollPane(dbErrorMsg)
  db.contents += new Label("Welcome to the airport software. You can use that tab to populate the data base or empty it.")
  db.contents += Swing.VStrut(10)
  db.contents += dbButtons
  db.contents += Swing.VStrut(10)
  db.contents += dbErrorMsgScroll


  // ############################################################################################################

  // Report tab

  val reportType = new ComboBox(List(
    "Top 10 countries with the highest and lowest number of airports.",
    "Type of runways' surfaces per country.",
    "The top 10 most common runway latitude."
  ))
  restrictHeight(reportType)

  val getReport: Button = new Button("Proceed") {}
  val resultReport: TextArea = new TextArea("") { editable = false }

  val reportTypeBox = new BoxPanel(Orientation.Horizontal)
  reportTypeBox.contents += new Label("Type of Report wanted : ")
  reportTypeBox.contents += Swing.HStrut(5)
  reportTypeBox.contents += reportType

  val report = new BoxPanel(Orientation.Vertical)
  report.contents += reportTypeBox
  report.contents += Swing.VStrut(5)
  report.contents += getReport
  report.contents += Swing.VStrut(5)
  val resultReportScroll = new ScrollPane(resultReport)
  resultReportScroll.verticalScrollBar
  report.contents += resultReportScroll


  // ############################################################################################################

  // Query tab

  val getQuery: Button = new Button("Proceed") {}
  val resultQuery: TextArea = new TextArea("") { editable = false }

  val countryName = new RadioButton("Name")
  val countryCode = new RadioButton("Code")
  countryName.selected = true
  val statusGroup = new ButtonGroup(countryName, countryCode)

  val countryIdentifier: TextField = new TextField {columns = 32}
  countryIdentifier.text = ""
  restrictHeight(countryIdentifier)

  val query_nameCode = new BoxPanel(Orientation.Horizontal)
  query_nameCode.contents += new Label("Query by country's ")
  query_nameCode.contents += Swing.HStrut(5)
  query_nameCode.contents += countryName
  query_nameCode.contents += Swing.HStrut(5)
  query_nameCode.contents += countryCode

  val query_askIdentifier = new BoxPanel(Orientation.Horizontal)
  query_askIdentifier.contents += new Label("Country identifier : ")
  query_askIdentifier.contents += Swing.HStrut(5)
  query_askIdentifier.contents += countryIdentifier

  val query = new BoxPanel(Orientation.Vertical)
  query.contents += query_nameCode
  query.contents += Swing.VStrut(10)
  query.contents += query_askIdentifier
  query.contents += Swing.VStrut(10)
  query.contents += getQuery
  query.contents += Swing.VStrut(10)
  val resultQueryScroll = new ScrollPane(resultQuery)
  resultQueryScroll.verticalScrollBar
  query.contents += resultQueryScroll


  // ############################################################################################################

  // Tab creation

  contents = new TabbedPane() {
    pages += new TabbedPane.Page("Data Base", db )
    pages += new TabbedPane.Page("Report", report )
    pages += new TabbedPane.Page("Query", query)
  }


  // ############################################################################################################

  // Reactive part of the UI

  listenTo(getQuery)
  listenTo(getReport)
  listenTo(populateDB)
  listenTo(emptyDB)

  reactions += {
    //Query
    case ButtonClicked(`getQuery`) => NonEmptyString.newNES(countryIdentifier.text) match {
      case None =>
        resultQuery.text = s"## ERROR ## : please enter a proper ${if (countryName.selected) "name" else "code"}"
      case Some(value) =>
        if (countryName.selected) {
          val reQuery = Reader.query(value.str,isName = true)
          val docQuery = Await.result(reQuery.toFuture(),Duration(5, java.util.concurrent.TimeUnit.SECONDS))
          resultQuery.text = Formatting.query(docQuery,name = true)
        } else {
          Code.validateCode(Some(value.str)) match {
            case Invalid(error) => resultQuery.text = "## ERROR ##" + error.head.value
            case Valid(value) =>
              val reQuery = Reader.query(value.code,isName = false)
              val docQuery = Await.result(reQuery.toFuture(),Duration(5, java.util.concurrent.TimeUnit.SECONDS))
              resultQuery.text = Formatting.query(docQuery,name = false)
          }
        }
    }

    // ---------------------------------------------------------------------------------------------------------

    // Report
    case ButtonClicked(`getReport`) =>
      resultReport.text = reportType.selection.index match {
        case 0 =>
          Formatting.report1(Reader.getReport1)
        case 1 =>
          val reportObservable = Reader.getReport2
          val report =  Await.result(reportObservable.toFuture(), Duration(10, java.util.concurrent.TimeUnit.SECONDS))
          Formatting.report2(report)
        case 2 =>
          Formatting.report3(Reader.getReport3)
      }

    // ---------------------------------------------------------------------------------------------------------

    // DB

    case ButtonClicked(`populateDB`) =>
      // insertion is defined after the reaction
      dbErrorMsg.text ="Starting insertion\n\n"
      insertion("./data/countries.csv", "countries", header = true)(Country.deserialization)(Insertion.insertCountry) // "./data/countries.csv"
      insertion("./data/airports.csv", "airports", header = true)(Airport.deserialization)(Insertion.insertAirport) // "./data/airports.csv"
      insertion("./data/runways.csv", "runways", header = true)(Runways.deserialization)(Insertion.insertRunways) // "./data/runways.csv"
      dbErrorMsg.text +="\n\nEnd of insertion"

    case ButtonClicked(`emptyDB`) =>
      emptyDb()
      dbErrorMsg.text = "The database has been emptied"
  }

  def insertion[T](path: String, collection : String, header: Boolean)
                  (deserializationFunction: List[String] => AllErrorsOr[T])
                  (insertionFunction: List[T] => Unit): Unit = {
    val eitherList = Parser.csv(path, header)(deserializationFunction)
    val (percentageValid, errorList, validList) = Error.separateErrorsResults(eitherList)
    if (percentageValid > 50) {
      insertionFunction(validList)
      dbErrorMsg.text += Error.formatError(errorList,collection,path)
    } else {
      dbErrorMsg.text += "### Too many invalid lines, insertion of" + path + " as " +  collection + " was aborted ###\n"
      dbErrorMsg.text += Error.formatError(errorList,collection,path)
    }
  }

}