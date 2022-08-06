import javafx.animation.*
import javafx.application.Platform
import javafx.geometry.Insets
import javafx.scene.Scene
import javafx.scene.control.Label
import javafx.scene.image.ImageView
import javafx.scene.input.KeyCode
import javafx.scene.layout.*
import javafx.scene.paint.Color
import javafx.scene.text.Font
import javafx.scene.media.Media
import javafx.scene.media.MediaPlayer
import java.util.logging.Level

object game {
    val classLoader = Thread.currentThread().contextClassLoader
    val timer = object : AnimationTimer() {
        override fun handle(now: Long) {
            if (PLAYER_MOVE == "LEFT" && player.layoutX > 0.0) {
                player.layoutX -= 5.0

            } else if (PLAYER_MOVE == "RIGHT" && player.layoutX < 1150.0) {
                player.layoutX += 5.0
            } else {
                player.layoutX += 0.0
            }
            update()
            win()
            lose()
        }
    }
    val root = BorderPane()
    var aliens = mutableListOf<ImageView>()
    var playerBullet = mutableListOf<ImageView>()
    var enemyBullet = mutableListOf<ImageView>()
    var player = ImageView("player.png")
    var gamePane = Pane()
    var infoPane = Pane()
    var scene2 = Scene(root, 1280.0, 800.0)
    public fun gameStart(): Scene {

        new()
        timer.start()
        root.background = Background(BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY))
        key()

        return scene2
    }

    fun new() {
        scoreLabel = Label("Score: $SCORE")
        scoreLabel.font = Font.font("Helvetica", 40.0)
        scoreLabel.style = "-fx-text-fill: green;"
        liveslable = Label("Lives: $LIVES")
        liveslable.font = Font.font("Helvetica", 40.0)
        liveslable.style = "-fx-text-fill: green;"
        liveslable.layoutX = 950.0
        levelLabel = Label("Level: $LEVEL")
        levelLabel.font = Font.font("Helvetica", 40.0)
        levelLabel.style = "-fx-text-fill: green;"
        levelLabel.layoutX = 1120.0
        infoPane.children.addAll(scoreLabel, liveslable, levelLabel)
        root.top = infoPane
        gamePane.prefWidth = 800.0
        player.scaleX = 0.5
        player.scaleY = 0.5
        player.layoutY = 660.0
        player.layoutX = 600.0
        gamePane.children.addAll(player)
        root.center = gamePane

        for (i in 0..49) {
            var alien = ImageView("enemy2.png")
            alien.scaleX = 0.5
            alien.scaleY = 0.5
            if (i < 10) {
                alien.layoutX = 60.0 * i
            } else if (i in 10..19) {
                alien = ImageView("enemy1.png")
                alien.scaleX = 0.43
                alien.scaleY = 0.5
                alien.layoutY = 50.0
                alien.layoutX = 60.0 * (i - 10)
            } else if (i in 20..29) {
                alien = ImageView("enemy3.png")
                alien.scaleX = 0.60
                alien.scaleY = 0.45
                alien.layoutY = 100.0
                alien.layoutX = 62.0 * (i - 20) + 10.0
            } else if (i in 30..39) {
                alien = ImageView("enemy1.png")
                alien.scaleX = 0.43
                alien.scaleY = 0.5
                alien.layoutY = 150.0
                alien.layoutX = 60.0 * (i - 30)
            } else {
                alien.layoutY = 200.0
                alien.layoutX = 60.0 * (i - 40)

            }
            aliens.add(alien)
            gamePane.children.add(alien)
        }
    }

    fun update() {
        if (START) {
            var toRight = true
            //enemy move
            for (i in 0 until aliens.size) {
                if (aliens[i].layoutX > 1150 || aliens[i].layoutX < 0) {
                    toRight = false
                }
            }
            if (!toRight) {
                // enemy shoot
                var shootRandom = (0 until aliens.size).random()
                var bullet = ImageView("bullet1.png")
                if ((shootRandom in 0..9) ||
                    (shootRandom in 40..49)
                ) {
                    bullet = ImageView("bullet2.png")
                } else if (shootRandom in 20..29) {
                    bullet = ImageView("bullet3.png")
                } else {
                    bullet = ImageView("bullet1.png")
                }
                bullet.scaleX = 0.5
                bullet.scaleY = 0.5
                bullet.layoutX = aliens[shootRandom].layoutX + 50
                bullet.layoutY = aliens[shootRandom].layoutY + 60
                enemyBullet.add(bullet)
                MediaPlayer(Media(classLoader.getResource("fastinvader1.wav")?.toString())).play()
                gamePane.children.add(enemyBullet[enemyBullet.size - 1])
                for (i in 0 until aliens.size) {
                    aliens[i].layoutY += 25
                    if (aliens[i].layoutY > player.layoutY) {
                        timer.stop()
                        FAIL = true
                    }
                }
                PROB = 3 * LEVEL
                ENEMY_SPEED *= -1
                ENEMY_VERTICAL_SPEED = 4.0 * LEVEL
                toRight = true
            }
            var count2 = 0
            for (i in enemyBullet) {
                if (i.layoutX > -100) {
                    count2++
                }
            }
            if (toRight) {
                var shootRandom2 = (0 until aliens.size).random()
                var shootRandom3 = (0 until 2001).random()
                var bullet = ImageView("bullet1.png")
                if ((shootRandom2 in 0..9) ||
                    (shootRandom2 in 40..49)
                ) {
                    bullet = ImageView("bullet2.png")
                } else if (shootRandom2 in 20..29) {
                    bullet = ImageView("bullet3.png")
                } else {
                    bullet = ImageView("bullet1.png")
                }
                if (count2 < 2 && shootRandom3 <= PROB) {
                    bullet.scaleX = 0.5
                    bullet.scaleY = 0.5
                    bullet.layoutX = aliens[shootRandom2].layoutX + 50
                    bullet.layoutY = aliens[shootRandom2].layoutY + 60
                    enemyBullet.add(bullet)
                    if(LEVEL == 3){
                        MediaPlayer(Media(classLoader.getResource("fastinvader4.wav")?.toString())).play()
                    }else if(LEVEL == 2){
                        MediaPlayer(Media(classLoader.getResource("fastinvader3.wav")?.toString())).play()
                    }else{
                        MediaPlayer(Media(classLoader.getResource("fastinvader2.wav")?.toString())).play()
                    }
                    gamePane.children.add(enemyBullet[enemyBullet.size - 1])
                }


                for (i in 0 until aliens.size) {
                    aliens[i].layoutX += ENEMY_SPEED
                }
            }
            //enemy shoot
            if (enemyBullet.isNotEmpty()) {
                for (i in 0 until enemyBullet.size) {
                    enemyBullet[i].layoutY += ENEMY_VERTICAL_SPEED
                    if (enemyBullet[i].layoutY >= 700) {
                        gamePane.children.remove(enemyBullet[i])
                        enemyBullet[i].layoutY = -100.0
                        enemyBullet[i].layoutX = -100.0
                    }
                }
            }

            //player shoot
            if (playerBullet.isNotEmpty()) {
                for (i in 0 until playerBullet.size) {
                    playerBullet[i].layoutY -= PLAYER_BULLET_SPEED
                    if (playerBullet[i].layoutY <= 0) {
                        gamePane.children.remove(playerBullet[i])
                        playerBullet[i].layoutY = -100.0
                        playerBullet[i].layoutX = -100.0
                    }
                }
            }
            try {
                for (i in 0 until playerBullet.size) {
                    for (j in aliens) {
                        if ((playerBullet[i].layoutX > j.layoutX) &&
                            (playerBullet[i].layoutX < j.layoutX + 80) &&
                            (playerBullet[i].layoutY > j.layoutY) &&
                            (playerBullet[i].layoutY < j.layoutY + 50)
                        ) {
                            gamePane.children.remove(j)
                            gamePane.children.remove(playerBullet[i])
                            aliens.remove(j)
                            MediaPlayer(Media(classLoader.getResource("invaderkilled.wav")?.toString())).play()
                            playerBullet[i].layoutY = -100.0
                            playerBullet[i].layoutX = -100.0
                            ENEMY_SPEED *= 1.05
                            SCORE += 10
                            scoreLabel.text = "Score: " + SCORE

                        }
                    }
                }
                for (j in aliens) {
                    if ((player.layoutX > j.layoutX) &&
                        (player.layoutX < j.layoutX + 80) &&
                        (player.layoutY > j.layoutY) &&
                        (player.layoutY < j.layoutY + 50)
                    ) {
                        gamePane.children.remove(j)
                        player.layoutX = 600.0
                        aliens.remove(j)
                        MediaPlayer(Media(classLoader.getResource("explosion.wav")?.toString())).play()
                        ENEMY_SPEED *= 1.05
                        SCORE += 10
                        LIVES--
                        scoreLabel.text = "Score: " + SCORE
                    }
                }

            } catch (ex: Exception) {
            }
            //enemy destroyed

            //player destroyed
            for (i in 0 until enemyBullet.size) {
                if ((enemyBullet[i].layoutX > player.layoutX) &&
                    (enemyBullet[i].layoutX < player.layoutX + 80) &&
                    (enemyBullet[i].layoutY > player.layoutY) &&
                    (enemyBullet[i].layoutY < player.layoutY + 70)
                ) {
                    enemyBullet[i].layoutY = -100.0
                    enemyBullet[i].layoutX = -100.0
                    LIVES -= 1
                    player.layoutX = 600.0
                    MediaPlayer(Media(classLoader.getResource("explosion.wav")?.toString())).play()
                    liveslable.text = "Lives: $LIVES"
                }
            }
        }
    }

    fun key() {
        scene2.setOnKeyPressed { e ->
            if (e.code == KeyCode.A || e.code == KeyCode.LEFT) {
                PLAYER_MOVE = "LEFT"
            }
            if (e.code == KeyCode.D || e.code == KeyCode.RIGHT) {
                PLAYER_MOVE = "RIGHT"
            }
            if (e.code == KeyCode.Q) {
                Platform.exit()
            }
            if (e.code == KeyCode.SPACE) {
                var bullet = ImageView("player_bullet.png")
                bullet.layoutX = player.layoutX + 55
                bullet.layoutY = player.layoutY - 25
                bullet.scaleX = 0.5
                bullet.scaleY = 0.5
                var count = 0
                for (i in playerBullet) {
                    if (i.layoutX > -100) {
                        count++
                    }
                }
                if (count <= 1) {
                    playerBullet.add(bullet)
                    MediaPlayer(Media(classLoader.getResource("shoot.wav")?.toString())).play()
                    gamePane.children.add(playerBullet[playerBullet.size - 1])
                }


            }
        }
        scene2.setOnKeyReleased { e ->
            if (e.code == KeyCode.A || e.code == KeyCode.LEFT) {
                PLAYER_MOVE = ""
            }
            if (e.code == KeyCode.D || e.code == KeyCode.RIGHT) {
                PLAYER_MOVE = ""
            }
        }
    }

    fun win() {
        if (aliens.size == 0) {

            timer.stop()
            if (LEVEL < 3) {
                var curlevel = LEVEL
                var result2 = Pane()
                var gameOVER2 = Label("You Win at Level " + LEVEL)
                var finalScore2 = Label("Your Current Score is $SCORE")
                var restart2 = Label("ENTER - Go to Next Level")
                var instruction2 = Label("I - Back to Instruction")
                var quit2 = Label("Q - Quit Game")
                gameOVER2.layoutX = 350.0
                gameOVER2.layoutY = 200.0
                finalScore2.layoutX = 450.0
                finalScore2.layoutY = 300.0
                restart2.layoutX = 450.0
                restart2.layoutY = 400.0
                instruction2.layoutX = 500.0
                instruction2.layoutY = 500.0
                quit2.layoutX = 550.0
                quit2.layoutY = 600.0
                finalScore2.font = Font.font("Helvetica", 40.0)
                finalScore2.style = "-fx-text-fill: WHITE;"
                gameOVER2.font = Font.font("Helvetica", 80.0)
                gameOVER2.style = "-fx-text-fill: RED;"
                quit2.font = Font.font("Helvetica", 40.0)
                quit2.style = "-fx-text-fill: WHITE;"
                instruction2.font = Font.font("Helvetica", 40.0)
                instruction2.style = "-fx-text-fill: WHITE;"
                restart2.font = Font.font("Helvetica", 40.0)
                restart2.style = "-fx-text-fill: WHITE;"
                result2.children.addAll(gameOVER2, finalScore2, quit2, restart2, instruction2)
                root.children.clear()
                root.children.add(result2)
                timer.stop()
                scene2.setOnKeyPressed { e1 ->
                    if (e1.code == KeyCode.Q) {
                        Platform.exit()
                    }
                    if (e1.code == KeyCode.ENTER) {
                        LEVEL++
                        PROB = 3 * LEVEL
                        ENEMY_SPEED = 0.5 * LEVEL
                        ENEMY_VERTICAL_SPEED = 4.0 * LEVEL
                        root.children.clear()
                        root.background = Background(BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY))
                        aliens.clear()
                        playerBullet.clear()
                        enemyBullet.clear()
                        gamePane.children.clear()
                        infoPane.children.clear()
                        new()
                        key()

                        timer.start()
                    }
                    if (e1.code == KeyCode.I) {
                        LEVEL = 1
                        root.children.clear()
                        val image = ImageView("logo.png")
                        val logo = Label("", image)
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
                        root1.children.addAll(logo, ins, enter, move1, move2, fire, quit, level, info)
                        root.children.add(root1)
                        root.background = Background(BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY))
                        scene2.setOnKeyPressed { e ->
                            if (e.code == KeyCode.Q) {
                                Platform.exit()
                            }
                            if (e.code == KeyCode.ENTER) {
                                SCORE = 0
                                LIVES = 3
                                root.children.clear()
                                root.background = Background(BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY))
                                aliens.clear()
                                playerBullet.clear()
                                enemyBullet.clear()
                                gamePane.children.clear()
                                infoPane.children.clear()
                                new()
                                key()

                                timer.start()
                            }
                            if (e.code == KeyCode.DIGIT1) {
                                LEVEL = 1
                                PROB = 3
                                ENEMY_SPEED = 0.5
                                ENEMY_VERTICAL_SPEED = 4.0
                            }
                            if (e.code == KeyCode.DIGIT2) {
                                LEVEL = 2
                                PROB = 6
                                ENEMY_SPEED = 1.0
                                ENEMY_VERTICAL_SPEED = 8.0
                            }
                            if (e.code == KeyCode.DIGIT3) {
                                LEVEL = 3
                                PROB = 9
                                ENEMY_SPEED = 1.5
                                ENEMY_VERTICAL_SPEED = 12.0
                            }
                        }
                    }
                }
            }

            else {
                LEVEL = 1
                LIVES = 3
                var result = Pane()
                var gameOVER = Label("Congrats! You Destroy All the Aliens")
                var finalScore = Label("Your Final Score is $SCORE")
                SCORE = 0
                val restart = Label("ENTER - Restart Game")
                val instruction = Label("I - Back to Instruction")
                val quit = Label("Q - Quit Game")
                val relevel = Label("1 or 2 or 3 - Start Game at specific level")
                gameOVER.layoutX = 250.0
                gameOVER.layoutY = 200.0
                finalScore.layoutX = 450.0
                finalScore.layoutY = 300.0
                restart.layoutX = 450.0
                restart.layoutY = 400.0
                instruction.layoutX = 470.0
                instruction.layoutY = 500.0
                quit.layoutX = 530.0
                quit.layoutY = 600.0
                relevel.layoutX = 308.0
                relevel.layoutY = 700.0
                finalScore.font = Font.font("Helvetica", 40.0)
                finalScore.style = "-fx-text-fill: WHITE;"
                gameOVER.font = Font.font("Helvetica", 60.0)
                gameOVER.style = "-fx-text-fill: RED;"
                quit.font = Font.font("Helvetica", 40.0)
                quit.style = "-fx-text-fill: WHITE;"
                instruction.font = Font.font("Helvetica", 40.0)
                instruction.style = "-fx-text-fill: WHITE;"
                restart.font = Font.font("Helvetica", 40.0)
                restart.style = "-fx-text-fill: WHITE;"
                relevel.font = Font.font("Helvetica", 40.0)
                relevel.style = "-fx-text-fill: WHITE;"
                result.children.addAll(gameOVER, finalScore, relevel, quit, restart, instruction)
                root.children.clear()
                root.children.add(result)
                timer.stop()
                scene2.setOnKeyPressed { e ->
                    if (e.code == KeyCode.Q) {
                        Platform.exit()
                    }
                    if (e.code == KeyCode.ENTER) {
                        PROB = 3*LEVEL
                        ENEMY_SPEED = 0.5*LEVEL
                        ENEMY_VERTICAL_SPEED = 4.0*LEVEL
                        root.children.clear()
                        root.background = Background(BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY))
                        aliens.clear()
                        playerBullet.clear()
                        enemyBullet.clear()
                        gamePane.children.clear()
                        infoPane.children.clear()
                        new()
                        key()

                        timer.start()
                    }
                    if (e.code == KeyCode.DIGIT1) {
                        LEVEL = 1
                        PROB = 3
                        ENEMY_SPEED = 0.5
                        ENEMY_VERTICAL_SPEED = 4.0
                    }
                    if (e.code == KeyCode.DIGIT2) {
                        LEVEL = 2
                        ENEMY_SPEED = 1.0
                        ENEMY_VERTICAL_SPEED = 8.0
                    }
                    if (e.code == KeyCode.DIGIT3) {
                        LEVEL = 3
                        ENEMY_SPEED = 1.5
                        ENEMY_VERTICAL_SPEED = 12.0
                    }
                    if (e.code == KeyCode.I) {
                        root.children.clear()
                        val image = ImageView("logo.png")
                        val logo = Label("", image)
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
                        root1.children.addAll(logo, ins, enter, move1, move2, fire, quit, level, info)
                        root.children.add(root1)
                        root.background = Background(BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY))
                    }
                }
            }
        }
    }

    fun lose() {
        if (LIVES == 0 || FAIL) {
            LEVEL = 1
            PROB = 3
            ENEMY_SPEED = 0.5
            ENEMY_VERTICAL_SPEED = 4.0
            FAIL = false
            var result = Pane()
            var gameOVER = Label("GAME OVER")
            var finalScore = Label("Your Final Score is $SCORE")
            val restart = Label("ENTER - Restart Game")
            val instruction = Label("I - Back to Instruction")
            val quit = Label("Q - Quit Game")
            val relevel = Label("1 or 2 or 3 - Start Game at specific level")
            gameOVER.layoutX = 400.0
            gameOVER.layoutY = 200.0
            finalScore.layoutX = 450.0
            finalScore.layoutY = 300.0
            restart.layoutX = 450.0
            restart.layoutY = 400.0
            instruction.layoutX = 450.0
            instruction.layoutY = 500.0
            quit.layoutX = 500.0
            quit.layoutY = 600.0
            relevel.layoutX = 308.0
            relevel.layoutY = 700.0
            finalScore.font = Font.font("Helvetica", 40.0)
            finalScore.style = "-fx-text-fill: WHITE;"
            gameOVER.font = Font.font("Helvetica", 80.0)
            gameOVER.style = "-fx-text-fill: RED;"
            quit.font = Font.font("Helvetica", 40.0)
            quit.style = "-fx-text-fill: WHITE;"
            instruction.font = Font.font("Helvetica", 40.0)
            instruction.style = "-fx-text-fill: WHITE;"
            restart.font = Font.font("Helvetica", 40.0)
            restart.style = "-fx-text-fill: WHITE;"
            relevel.font = Font.font("Helvetica", 40.0)
            relevel.style = "-fx-text-fill: WHITE;"
            result.children.addAll(gameOVER, finalScore, relevel, quit, restart, instruction)
            root.children.clear()
            root.children.add(result)
            timer.stop()
            scene2.setOnKeyPressed { e ->
                if (e.code == KeyCode.Q) {
                    Platform.exit()
                }
                if (e.code == KeyCode.ENTER) {
                    SCORE = 0
                    LIVES = 3
                    root.children.clear()
                    root.background = Background(BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY))
                    aliens.clear()
                    playerBullet.clear()
                    enemyBullet.clear()
                    gamePane.children.clear()
                    infoPane.children.clear()
                    new()
                    key()

                    timer.start()
                }
                if (e.code == KeyCode.DIGIT1) {
                    LEVEL = 1
                    PROB = 3
                    ENEMY_SPEED = 0.5
                    ENEMY_VERTICAL_SPEED = 4.0
                }
                if (e.code == KeyCode.DIGIT2) {
                    LEVEL = 2
                    PROB = 6
                    ENEMY_SPEED = 1.0
                    ENEMY_VERTICAL_SPEED = 8.0
                }
                if (e.code == KeyCode.DIGIT3) {
                    LEVEL = 3
                    PROB = 9
                    ENEMY_SPEED = 1.5
                    ENEMY_VERTICAL_SPEED = 12.0
                }
                if (e.code == KeyCode.I) {
                    root.children.clear()
                    LEVEL = 1
                    val image = ImageView("logo.png")
                    val logo = Label("", image)
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
                    root1.children.addAll(logo, ins, enter, move1, move2, fire, quit, level, info)
                    root.children.add(root1)
                    root.background = Background(BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY))
                }
            }
        }
    }
}

