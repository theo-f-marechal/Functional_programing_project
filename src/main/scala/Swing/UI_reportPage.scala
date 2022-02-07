package Swing
import scala.swing._
import scala.swing.event.ButtonClicked

class UI_reportPage extends MainFrame {

  title = "Report page"
  val reportType = new ComboBox(List(
    "10 countries with highest number of airports (with count) and countries  with lowest number of airports.",
    "Type of runways (as indicated in \"surface\" column) per country.",
    "The top 10 most common runway latitude (indicated in \"le_ident\" column)."
  ))

  val getResult: Button = new Button("Proceed") {}
  val result = new Label("")

  contents = new BoxPanel(Orientation.Vertical) {
    contents += new BoxPanel(Orientation.Horizontal) {
      contents += new Label("Query by country : ")
      contents += Swing.HStrut(5)
      contents += reportType
    }
    contents += Swing.VStrut(10)
    contents += Swing.Glue
    contents += getResult
    contents += Swing.VStrut(10)
    contents += Swing.Glue
    contents += result
  }

  listenTo(getResult)

  reactions += {
    case ButtonClicked(`getResult`) =>
      println(reportType.selection.item)
      result.text =  reportType.selection.item
  }
}
