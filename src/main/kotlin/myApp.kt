import javafx.geometry.Rectangle2D
import javafx.scene.Scene
import javafx.scene.layout.StackPane
import javafx.scene.paint.Color
import javafx.stage.Screen
import javafx.stage.Stage
import javafx.stage.StageStyle
import tornadofx.*
import tornadofx.FX.Companion.primaryStage

fun main(args: Array<String>) {
    launch<MyApp>(args)
}
//class MyApp: App(MyView::class)
class MyApp: App(MyView::class) {
//    override fun start(stage: Stage) {
//        val myView = MyView()
//        stage.title = "TuneUs"
//        stage.scene = Scene(myView.root)
//
//        val screen : Screen = Screen.getScreens()[0]
//        stage.initStyle(StageStyle.TRANSPARENT)
//        val stackPane = StackPane()
//        stackPane.style = "-fx-background-color: rgba(255, 255, 255, 0.5);"
//        val scene = Scene(stackPane, screen.bounds.width, screen.bounds.height)
//        scene.fill = Color.TRANSPARENT
//        stage.scene = scene
//
//        val bounds: Rectangle2D = screen.visualBounds
//        stage.x = bounds.minX + 100
//        stage.y = bounds.minY + 100
//        stage.show()
//
//        //super.start(stage)
//        //myView.controller.writeToDb("t")
//    }
}
