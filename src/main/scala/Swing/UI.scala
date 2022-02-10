package Swing
import StructureCSV.{Code, CountryId, NonEmptyString}

import scala.swing.MenuBar.NoMenuBar.border
import scala.swing._
import scala.swing.event.ButtonClicked


class UI extends MainFrame {
  def restrictHeight(s: Component): Unit = {
    s.maximumSize = new Dimension(Short.MaxValue, s.preferredSize.height)
  }

  preferredSize = new Dimension(1000, 240)

  title = "functional programming project"

  val reportType = new ComboBox(List(
    "10 countries with highest number of airports (with count) and countries  with lowest number of airports.",
    "Type of runways (as indicated in \"surface\" column) per country.",
    "The top 10 most common runway latitude (indicated in \"le_ident\" column)."
  ))

  val getQuery: Button = new Button("Proceed") {}
  val resultQuery: TextArea = new TextArea("") { editable = false }

  val getReport: Button = new Button("Proceed") {}
  val resultReport: TextArea = new TextArea("") { editable = false }

  val countryName = new RadioButton("Name")
  val countryCode = new RadioButton("Code")
  countryName.selected = true
  val statusGroup = new ButtonGroup(countryName, countryCode)
  val countryIdentifier: TextField = new TextField {columns = 32}
  countryIdentifier.text = ""

  restrictHeight(countryIdentifier)
  restrictHeight(reportType)

  val report_type = new BoxPanel(Orientation.Horizontal)
  report_type.contents += new Label("Type of Report wanted : ")
  report_type.contents += Swing.HStrut(5)
  report_type.contents += reportType

  val report = new BoxPanel(Orientation.Vertical)
  report.contents += report_type
  report.contents += Swing.VStrut(5)
  report.contents += getReport
  report.contents += Swing.VStrut(5)
  val resultReportScroll = new ScrollPane(resultReport)
  resultReportScroll.verticalScrollBar
  report.contents += resultReportScroll

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

  contents = new TabbedPane() {
    pages += new TabbedPane.Page("Report", report )
    pages += new TabbedPane.Page("Query", query)
  }

  border = Swing.EmptyBorder(10, 10, 10, 10)

  listenTo(getQuery)
  listenTo(getReport)

  reactions += {
    //Query
    case ButtonClicked(`getQuery`) => NonEmptyString.newNES(countryIdentifier.text) match {
      case None =>
        resultQuery.text = s"## ERROR ## : please enter a proper ${if (countryName.selected) "name" else "code"}"
      case Some(value) =>
        if (countryName.selected) {
          resultQuery.text = value.str + " Name"
        } else {
          Code.newCode(Some(value.str)) match {
            case None => resultQuery.text = "## ERROR ## : the format of the country code you provided is incorrect"
            case Some(value) => resultQuery.text = value + " Code"
          }
        }
    }

    //Report
    case ButtonClicked(`getReport`) =>
      resultReport.text = reportType.selection.index match {
        case 0 => "1"
        case 1 => "2"
        case 2 => "3"
    }
  }
}
