import javafx.geometry.Insets
import javafx.scene.control.Label
import javafx.scene.image.ImageView
import javafx.scene.layout.Background
import javafx.scene.layout.BackgroundFill
import javafx.scene.layout.CornerRadii
import javafx.scene.layout.Pane
import javafx.scene.paint.Color
import javafx.scene.text.Font

object Homepage {
    public fun start(): Pane{
        val image = ImageView("logo.png")
        val logo = Label("",image)
        val enter = Label("ENTER - Start Game")
        val move1 = Label("A or < - Move ship to the left")
        val move2 = Label("D or > - Move ship to the right")
        val fire = Label("SPACE - Fire")
        val quit = Label("Q - Quit Game")
        val level = Label("1 or 2 or 3 - Start Game at specific level")
        val ins = Label("Instructions")
        val info = Label("Implemented by Chengyu Jin, Student ID: 20874575")
        val root1 = Pane()
        enter.font = Font.font("Helvetica", 33.0)
        move1.font = Font.font("Helvetica", 33.0)
        move2.font = Font.font("Helvetica", 33.0)
        fire.font = Font.font("Helvetica", 33.0)
        quit.font = Font.font("Helvetica", 33.0)
        level.font = Font.font("Helvetica", 33.0)
        ins.font = Font.font("impact", 66.0)
        info.font = Font.font("Helvetica", 18.0)

        logo.layoutX = 385.0
        logo.layoutY = 60.0
        logo.scaleX = 1.5
        logo.scaleY = 1.5
        ins.layoutX = 481.0
        ins.layoutY = 350.0
        enter.layoutX = 490.0
        enter.layoutY = 450.0
        move1.layoutX = 430.0
        move1.layoutY = 500.0
        move2.layoutX = 420.0
        move2.layoutY = 550.0
        fire.layoutX = 538.0
        fire.layoutY = 600.0
        quit.layoutX = 530.0
        quit.layoutY = 650.0
        level.layoutX = 358.0
        level.layoutY = 700.0
        info.layoutX = 430.0
        info.layoutY = 770.0
        root1.children.addAll(logo, ins, enter, move1, move2, fire, quit, level,info)
        root1.background = Background(BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY))
        return root1
    }

}