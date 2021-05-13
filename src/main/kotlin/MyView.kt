import javafx.beans.property.SimpleStringProperty
import javafx.scene.layout.Pane
import tornadofx.*

class MyView: View() {
    var controller: MyController = MyController()
    val input = SimpleStringProperty()
    override val root = form {
        fieldset {
            field("Input") {
                textfield(input)
            }
            button("Commit") {
                action {
                    controller.writeToDb(input.value)
                    input.value = ""
                }
            }
        }
    }
}

class MyController: Controller() {
    fun writeToDb(inputValue: String) {
        println("Writing $inputValue to database!")
        var internalWindow = InternalCustomWindow()
        internalWindow.startInternalWindow(primaryStage)
    }
}