package Swing
import scala.swing._
import scala.swing.event.ButtonClicked

class UI_queryPage extends MainFrame {
  title = "Query page"
  val countryName = new RadioButton("Name")
  val countryCode = new RadioButton("Code")
  countryName.selected = true
  val statusGroup = new ButtonGroup(countryName, countryCode)
  val countryIdentifier: TextField = new TextField { columns = 32 }
  countryIdentifier.text = ""
  val getResult: Button = new Button("Proceed") {}
  val result = new Label("")

  contents = new BoxPanel(Orientation.Vertical) {
    contents += new BoxPanel(Orientation.Horizontal) {
      contents += new Label("Query by country's ")
      contents += Swing.HStrut(5)
      contents += countryName
      contents += Swing.HStrut(5)
      contents += countryCode
    }
    contents += Swing.VStrut(10)
    contents += Swing.Glue
    contents += new BoxPanel(Orientation.Horizontal) {
      contents += new Label("My name")
      contents += Swing.HStrut(5)
      contents += countryIdentifier
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
    case ButtonClicked(`getResult`) => if (countryName.selected)
      println("Name : " + countryIdentifier.text)
    else
      println("Code : " + countryIdentifier.text)
      result.text =  "Wow results"
  }

}
