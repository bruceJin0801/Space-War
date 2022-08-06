import javafx.application.Application
import javafx.application.Platform
import javafx.event.EventHandler
import javafx.scene.Scene
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyEvent
import javafx.stage.Stage

class SpaceInvaders : Application() {

    enum class SCENES {
        SCENE1, SCENE2
    }
    var scene1: Scene? = null
    var scene2: Scene? = null
    override fun start(stage: Stage?) {
        stage!!.title = "Space Invaders"
        scene1 = Scene(Homepage.start(), 1280.0, 800.0)
        // pressing '2' or pressing the button will switch to scene 2
        EventHandler { event: KeyEvent ->
            if (event.code == KeyCode.ENTER) {
                START = true
                setScene(stage, SCENES.SCENE2)
            }
            if(event.code == KeyCode.Q){
                Platform.exit()
            }
            if(event.code == KeyCode.DIGIT1){
                LEVEL = 1
                PROB = 3
                ENEMY_SPEED = 0.5
                ENEMY_VERTICAL_SPEED = 4.0
                levelLabel.text = ("Level: $LEVEL")
            }
            if(event.code == KeyCode.DIGIT2){
                LEVEL = 2
                PROB = 6
                ENEMY_SPEED = 1.0
                ENEMY_VERTICAL_SPEED = 8.0
                levelLabel.text =("Level: $LEVEL")
            }
            if(event.code == KeyCode.DIGIT3){
                LEVEL = 3
                PROB = 9
                ENEMY_SPEED = 1.5
                ENEMY_VERTICAL_SPEED = 12.0
                levelLabel.text =("Level: $LEVEL")
            }
        }.also { scene1?.onKeyPressed = it }
        // scene two
        scene2 = game.gameStart()

        // show starting scene
        setScene(stage, SCENES.SCENE1)
        stage.isResizable = false;
        stage.show()

    }
    private fun setScene(stage: Stage, scene: SCENES?) {
        when (scene) {
            SCENES.SCENE1 -> {
                stage.title = "Space Invaders"
                stage.scene = scene1
            }
            SCENES.SCENE2 -> {
                stage.title = "Space Invaders"
                stage.scene = scene2
            }
            else -> return
        }
    }
}