# Space-War

> This game is inspired by a famous 2D video game: Space Invaders, released by Taito in Japan in 1978 and the US in 1980. It was hugely popular, and helped to launch the commercial video game industry. The object of the game is to move a ship along the bottom of the screen and shoot up at aliens that are descending from the sky, without being hit in return.

<h2>1. Demo: </h2>

![demo](https://user-images.githubusercontent.com/85118325/183269109-3b150d9b-6359-4dc7-9aae-c8c02a4c6fe9.gif)



<h2>2. Technique: </h2>

1. Building an interactive application in Kotlin.

2. Using JavaFX to handle drawing and animation of simple graphics.

3. Providing visual and audial feedback, and gameplay elements to make the game more enjoyable.


<h2>3. Features: </h2>

* **<ins>Homepage: </ins>** When launched, the title screen will be displayed including:
> 
> 1. The title, “Space Invaders”, at the top of the screen.
>
> 2. Instructions on how to play and/or quit your application.
> 
> 3. The player should be able to start or quit the game from this screen.


* **<ins>Game start: </ins>** When game starts, it will show:

> 1. A game screen, including at least 10 columns and 5 rows of aliens,
> 
> 2. A player-controlled ship that can move along the bottom of the screen.
> 
> 3. A score indicator.
> 
> 4. A level indicator to show which level is in-play.

> 5. An indicator of the number of ships remaining.
> 
> 6. Status information (current score, level, number of ships) should be visible during gameplay, without obscuring the board.

* **<ins>Game start: </ins>**  The alien ships move together as a group. They initially move from their starting position towards the right-side of the screen. When they reach the edge of the screen, the following happens:

> 1. The ships all descend one row.
> 
> 2. One of the ships fires a missile straight down.
> 
> 3. The ships reverse their direction and start moving in the opposite direction.
> 
> 4. The ships continue moving until they reach the left-edge of the screen, and the pattern repeats and they start moving right again. They repeat this pattern, descending and alternating directions, until they reach the bottom of the screen or the game ends.
> 
> 5. Every time the aliens move, there is a random chance that one of them fires a missle. You should design the rate-of-fire so that there are never more than a few missiles on-screen at a given time.
> 
> 6. Every time the player destroys an alien, the remaining aliens speed up. Effectively the game starts out fairly slow, and gets progressively faster as aliens are destroyed.

<h2>4. Rules: </h2>

 * **<ins>Lives: </ins>** The player has three ships at the start of the game.If the player has ships remaining and their ship is destroyed (either by contacting an alien or being struck by a missile), one of the extra ships will be removed If they die for a third time (i.e. they’ve exhausted all ships), then they lose the game.

* **<ins>Next: </ins>** If all of the aliens are destroyed successfully, the next level is launched. Subsequent levels all have the same layout, but the ships and missiles move faster than previous levels, and there may be a slightly higher chance of missiles firing in later levels. The level indicator should clearly indicate the level in play. There does not need to be any other visual changes.

* **<ins>Levels: </ins>** There are three levels, and pressing “1”, “2” or “3” from the start screen will launch the game at that level. 

* **<ins>Win: </ins>** If the player succeeds in clearing all three levels, you should show a message telling them that they have won the game and display their final score. You should prompt them to restart or quit the game.

* **<ins>Lose: </ins>** If the player’s ships are all destroyed, you should display a message telling them that they have lost the game, along with their final score. You should prompt them to restart or quit the game.

<h2>5. Control: </h2>

* **<ins> Move: </ins>**  The player interacts with the game by moving the ship left and right to avoid alien missiles.fire: ‘A’ moves the ship left, ‘D’ moves the ship right, and SPACE fires missiles. 

* **<ins> Missiles: </ins>** The player may fire missiles as often as they wish (with the rate of fire not exceeding 2 missiles per second, and it’s possible for multiple missiles to be in motion). Missiles fly straight up until they strike an alien ship. Missiles will destroy any alien that they strike. Missiles that do not hit an alien disappear when they reach the top of the screen.
