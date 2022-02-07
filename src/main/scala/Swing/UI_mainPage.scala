package Swing
import scala.swing._

class UI_mainPage extends MainFrame {
  title = "functional programming project"

  contents = new BoxPanel(Orientation.Vertical) {
    contents += new Label("Click the button corresponding to the action you wish to accomplish :")
    contents += Swing.VStrut(10)
    contents += Swing.Glue
    contents += new BoxPanel(Orientation.Horizontal) {
      contents += Button("Query") { query() }
      contents += Swing.HStrut(5)
      contents += Button("Rapport") { report() }
    }
    contents += Swing.VStrut(5)
    contents += Button("Close") { closeMe() }
    border = Swing.EmptyBorder(10, 10, 10, 10)
  }

  def query(): Unit = { val ui = new UI_queryPage ; ui.visible = true }
  def report(): Unit = { val ui = new UI_reportPage ; ui.visible = true }

  def closeMe(): Unit = {
    val res = Dialog.showConfirmation(contents.head,
      "Do you really want to quit?",
      optionType=Dialog.Options.YesNo,
      title=title)
    if (res == Dialog.Result.Ok)
      sys.exit(0)
  }
}
