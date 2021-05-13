import javafx.event.EventHandler
import javafx.scene.Cursor
import javafx.scene.Node
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.image.ImageView
import javafx.scene.input.MouseEvent
import javafx.scene.layout.BorderPane
import javafx.scene.layout.Pane
import javafx.scene.layout.Region
import javafx.stage.Screen
import javafx.stage.Stage
import tornadofx.addChildIfPossible
import kotlin.math.abs


class InternalCustomWindow: Region() {
    private var RESIZE_BOTTOM = false
    private var RESIZE_RIGHT = false

    fun setRoot(node: Node) {
        children.add(node)
    }

    fun makeFocusable() {
        this.onMouseClicked = EventHandler { mouseEvent: MouseEvent? -> toFront() }
    }

    fun makeDraggable(node: Node) {
        var dragDelta = Delta()
        node.onMousePressed = EventHandler { mouseEvent: MouseEvent? ->
            dragDelta.x = layoutX - mouseEvent!!.screenX
            dragDelta.y = layoutY - mouseEvent!!.screenY
            toFront()
        }
        node.onMouseDragged = EventHandler { mouseEvent: MouseEvent? ->
            layoutX = mouseEvent!!.screenX + dragDelta.x
            layoutY = mouseEvent!!.screenY + dragDelta.y
        }
    }

    fun makeResizable(mouseBorderWidth: Double) {
        this.onMouseMoved = EventHandler { mouseEvent: MouseEvent ->
            //local window's coordiantes
            val mouseX = mouseEvent.x
            val mouseY = mouseEvent.y
            //window size
            val width = boundsInLocalProperty().get().width
            val height = boundsInLocalProperty().get().height
            //if we on the edge, change state and cursor
            if (abs(mouseX - width) < mouseBorderWidth
                && abs(mouseY - height) < mouseBorderWidth
            ) {
                RESIZE_RIGHT = true
                RESIZE_BOTTOM = true
                this.cursor = Cursor.NW_RESIZE
            } else {
                RESIZE_BOTTOM = false
                RESIZE_RIGHT = false
                this.cursor = Cursor.DEFAULT
            }
        }
        this.onMouseDragged = EventHandler { mouseEvent: MouseEvent ->
            val region = children[0] as Region
            if (RESIZE_BOTTOM && RESIZE_RIGHT) {
                region.setPrefSize(mouseEvent.x, mouseEvent.y)
            } else if (RESIZE_RIGHT) {
                region.prefWidth = mouseEvent.x
            } else if (RESIZE_BOTTOM) {
                region.prefHeight = mouseEvent.y
            }
        }
    }

    fun setCloseButton(btn: Button) {
        btn.onAction = EventHandler {
            (parent as Pane).children.remove(this)
        }
    }

    private fun constructWindow(): InternalCustomWindow? {
        val imageView = ImageView("https://upload.wikimedia.org/wikipedia/commons/thumb/a/a9/Cheetah4.jpg/250px-Cheetah4.jpg")
        val titleBar = BorderPane()
        titleBar.style = "-fx-background-color: green; -fx-padding: 3"
        val label = Label("header")
        titleBar.left = label
        val closeButton = Button("x")
        titleBar.right = closeButton
        val windowPane = BorderPane()
        windowPane.style = "-fx-border-width: 100; -fx-border-color: black"
        windowPane.top = titleBar
        windowPane.center = imageView
        var internalWindow = InternalCustomWindow()
        internalWindow.setRoot(windowPane)
        internalWindow.makeDraggable(titleBar)
        internalWindow.makeDraggable(label)
        internalWindow.makeResizable(20.0)
        internalWindow.makeFocusable()
        internalWindow.setCloseButton(closeButton)
        return internalWindow
    }

    fun startInternalWindow(primaryStage:Stage){
        val screen : Screen = Screen.getScreens()[0]
        val root = Pane()
        root.style = "-fx-background-color: rgba(255, 255, 255, 0);"
        root.children.add(constructWindow())
        root.children.add(constructWindow())
        primaryStage.scene = Scene(root, screen.bounds.width, screen.bounds.height -100)
        primaryStage.centerOnScreen()
        primaryStage.show()
    }

}

private  class Delta {
     var x:Double = 0.0
     var y:Double = 0.0
}