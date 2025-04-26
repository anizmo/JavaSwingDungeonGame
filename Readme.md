# Dungeon Maze Graphical Game

The goal of this assignment is design and implement an asynchronous controller and view using the model that we built in the previous projects.

![alt text](https://github.com/anizmo/DisplayOnly/blob/main/screens/6.%20Scroll.png?raw=true)

## About/Overview

The Graphical Adventure Game Project extends the functionality created with a maze of rows and columns specified by the user where a start point and an end point is selected randomly from the locations in the Maze grid with some added features of having a moving monster, a thief that runs away with the treasure, a pit that the player can fall in and the ability of the player to slay the monster by combating with him hand-to-hand. In addition to the model features there is also an addition of  a graphical user interface that is used to represent the maze and the player along with the obstacle and the collectibles on  the screen.  
The player is expected to navigate through the grid and reach the end location cave by taking steps in either North, South, East or West directions and move one unit at a time.   
The player will have to beat a monster that is housing at the end location of the dungeon maze by hitting the monster with an arrow twice doing damage to 50% each time.  Along with this now there is also a moving monster in the dungeon that keeps moving towards the player and the player has to engage in a combat with the moving monster - Berbalang.
There are thieves located in caves and tunnel that can steal the treasure carried by the player, and run away. There are also pits in caves that the player can fall into and lose the game,
The Maze can be either wrapping or non-wrapping. Each step of the player is taken as an input from  the user and the description of the current location, the collectible in it, whether it has arrows or not is provided as the output by the controller with an appendable. The GUI has two views namely, one which shows the dungeon and another which shows the location's specific obstacles.  
The project has been tested with more than 100 test cases that completely cover cases ranging from valid inputs, invalid inputs, valid object creation, invalid object creation, and business logic validation.

## List of features

- Create a maze with r number of rows and c number of columns.
- Each cell in the grid of the maze represents either a cave or a tunnel depending on the number of openings it has.
- There are a maximum of 4 and a minimum of 1 opening to each location.
- Based on the number of openings a location is categorised as either a cave or a tunnel.
- A tunnel can have only 2 openings, they can be in any direction as long as they are exactly 2. It cannot hold any collectible.
- A cave can have 1, 3 or 4 openings and it can hold zero, single or multiple collectibles.
- Each cave can hold zero or many collectibles that are randomly allotted to it from a list of 3 available collectibles namely Diamonds, Rubies and Sapphires.
- Each cave and tunnel can have one or zero arrow in them, the percentage of arrows in the dungeon is equal to the percentage of caves having collectible.
- Arrows can be fired from any location of the dungeon irrespective of the location being a cave or a tunnel.
- A Crooked Arrow is a specialised form of an Arrow that can bend through tunnels and go through caves if there is an opening in the direction of shooting of the arrow.
- Each time a crooked arrow hits the monster a damage of 50 is incurred by the monster.
- A dungeon can have one or more Outyugh monster in it that can be killed by taking total damage to 100.
- An Outyugh can attack and eat up a player when the player lands in the cave where the Outyugh is located.
- The Outyugh's neighbouring locations that are one location apart have a terrible smell and the locations that are two location apart have a bad smell.
- The smell signifies the presence of a monster and when the monster is killed by the arrows of the player the smell disappears.
- There are different levels of smell depending of the number of monsters nearby and the number spaces that the monster and player are apart.
- The collectibles have points associated with them as 500, 1000, and 2000 for Diamonds, Rubies and Sapphires respectively.
- The player starts the game randomly from any location in the grid, this location is chosen completely random and has to be a cave. If the selected random location is not a cave then the random is redrawn until a cave is not obtained as the start location.
- A cave that is at least 5 or more nodes away from the starting cave is selected as the end point of the maze.
- The player can pickup collectible from every cave if there exists one or more collectibles in it.
- An attempt to pickup collectible from a location other than a cave or from a cave that has no collectible results in an exception.
- When the collectible is collected from one cave, it no longer exists in that cave.
- The user can specify what percentage of caves can contain collectibles from 0 to 100.
- The user can specify the degree of interconnectivity, which is the number of additional edges to be inserted after each location is connected by one path to another. A degree of interconnectivity of 0 means that there is exactly one path from one location to another.
- A user can make a maze wrapping and non-wrapping by specifying a command line parameter.
- The player can move through the wrapping and non-wrapping dungeons similarly without any additional efforts from the driver side as the handling of wrapping is done in the model.
- All the paths in the maze are bidirectional, meaning a path from A to B is the same as a path from B to A.
- There are now pits in the dungeon which can be located in any cave apart from the start and the end cave, where a player can fall and lose.
- There are thieves that steal the player's treasure and run away from the dungeon which can be located in caves as well as tunnels.
- There is a moving monster that moves towards the player each time the player moves, the moving monster can not be hit by arrows, fall into pits or be attacked by the Otyugh as it has wings and flies within the dungeon.
- Player can be involved in a hand-to-hand combat with the moving monster, where the outcome of the battle is random based on the hits, the player will lose the game if the monster defeats him in hand to hand combat.
- Player can detect that there is a pit from one location away by the wind that surrounds the pit, and now has the option to jump over it if there is an opening in the direction of the jump.
- There is a bird's eye view of the dungeon where the player can only see the caves and tunnel that the player has visited and the rest of it remains dark.
- There is also a First Person View of the location of the player which shows exact treasure, arrow and obstacles that are in the location.
- Each time the player moves into a new location the first person view changes.
- The player can attack using Crooked Arrows as well as by Punching. The punching does not work on Otyugh and the Arrows do not work on the Berbalang which is the moving monster as it can fly and dodge the arrows.

## How To Run

How to run the jar file -
- Locate the .jar file and place it in a folder.
- Make sure you have JRE installed on your computer.
- There is only one jar files in the res folder it takes command line argument and can be run even without the arguments.

### Graphical Game

To run the graphical game, go through the following procedure,

- On a Mac, navigate to the folder in the terminal using cd and the directory’s absolute path.
- Run the following command -

  ``` java -jar dungeon_game.jar ```

- The above command runs the Graphical Game, and it opens a new window which has the graphical game.
- The graphical game takes no command line arguments and contains a panel to set settings which are exposed as command line arguments in the text based game.

### Text Based Game

- This program takes 6 command line arguments in the following order - numberOfRows (int), numberOfColumns (int), degreeOfInterconnectivity (int), caveTreasurePercentage (int), difficulty (int), isWrapping (boolean).
- For example, if you want to make a dungeon with a maze of 6 rows and 6 columns with degree of interconnectivity 0 and 20% caves having collectibles in it and set it to not wrapping with one monster (Otyugh) the following command will work -

  ``` java -jar dungeon_game.jar 6 6 0 20 1 false```

- The above jar also runs without any command line arguments with the default settings specified in the code.
- There are 2 games in one Jar file, and there are 2 ways to run this.

## Game Controls

- Move Around - Arrow Keys
- Pickup Arrow, Sapphire, Diamond, Ruby - A, S, D, R respectively.
- Fire Crooked Arrow - C followed by the direction with Arrow Keys followed by Number for Distance.
- Punch - P
- Jump - J followed by the direction with Arrow Keys
- Mouse click on any of the openings for the player to go through it.

##### Shoot Example : To shoot in the North Direction at Distance 1 : Press C then ↑ then 1

##### Jump Example : To jump in the North Direction : Press J then ↑

There are also on-screen buttons available for all these actions except for shooting arrow and , as it requires additional input of direction and distance.


## Description of Examples

### Graphical Game

There are screenshots of the Graphical Game available in the "screens" folder, alternatively they can also be found in this [Google Drive folder](https://drive.google.com/drive/folders/1sACwgV-bSZJznlF54CpduCG4OHva_VhZ?usp=sharing) inside the res folder which are as follows -

1. GameSettings.png - Demonstrates the settings pane of the game from where the settings of the game can be changed.

![alt text](https://github.com/anizmo/DisplayOnly/blob/main/screens/1.%20GameSettings.png?raw=true)

2. RestartGame.png - Shows the JMenu and the options that can be used to restart, reset and exit the game.

![alt text](https://github.com/anizmo/DisplayOnly/blob/main/screens/2.%20RestartGame.png?raw=true)

3. InvalidMove.png - Invalid movement of the player, resulting in an error dialog. This can be done by clicking in an opening that does not exist.

![alt text](https://github.com/anizmo/DisplayOnly/blob/main/screens/3.%20InvalidMove.png?raw=true)

4. PickedArrow.png - Player picking up an arrow and the view displaying the message.

![alt text](https://github.com/anizmo/DisplayOnly/blob/main/screens/4.%20PickedArrow.png?raw=true)

5. PickedTreasure.png - Player picking up a treasure and it getting added to the player's inventory.

![alt text](https://github.com/anizmo/DisplayOnly/blob/main/screens/5.%20PickedTreasure.png?raw=true)

6. Scroll.png - Scrolling to view the dungeon completely.

![alt text](https://github.com/anizmo/DisplayOnly/blob/main/screens/6.%20Scroll.png?raw=true)

7. WindNearPit.png - The player sensing wind one location away from the pit.

![alt text](https://github.com/anizmo/DisplayOnly/blob/main/screens/7.%20WindNearPit.png?raw=true)

8. FellInPit.png -  The player fell in the pit and lost the game.

![alt text](https://github.com/anizmo/DisplayOnly/blob/main/screens/8.%20FellInPit.png?raw=true)

9. PitInLocation.png - The first person view of the pit in the player's location.

![alt text](https://github.com/anizmo/DisplayOnly/blob/main/screens/9.%20PitInLocation.png?raw=true)

10. ThiefEncountered.png - The player encounters a thief that steals all the treasure.

![alt text](https://github.com/anizmo/DisplayOnly/blob/main/screens/10.%20ThiefEncountered.png?raw=true)

11. ThiefRanAwayWithTreasure.png - First person view of the thief escaping with the player's treasure.

![alt text](https://github.com/anizmo/DisplayOnly/blob/main/screens/11.%20ThiefRanAwayWithTreasure.png?raw=true)

12. PlayerHandToHandBattle.png - Player encounters the moving monster in a combat.

![alt text](https://github.com/anizmo/DisplayOnly/blob/main/screens/12.%20PlayerHandToHandBattle.png?raw=true)

13. PlayerLostBattle.png - Player lost the battle to the Berbalang monster.

![alt text](https://github.com/anizmo/DisplayOnly/blob/main/screens/13.%20PlayerLostBattle.png?raw=true)

14. PlayerWonBattle - Player won the battle against the Berbalang monster.

![alt text](https://github.com/anizmo/DisplayOnly/blob/main/screens/14.%20PlayerWonBattle.png?raw=true)

15. TerribleSmell.png - Player detecting terrible smell one location away from the Otyugh.

![alt text](https://github.com/anizmo/DisplayOnly/blob/main/screens/15.%20TerribleSmell.png?raw=true)

16. ShootHit.png - Player shot an arrow on the Otyugh and heard a great howl.

![alt text](https://github.com/anizmo/DisplayOnly/blob/main/screens/16.%20ShootHit.png?raw=true)

17. KilledOtyugh.png - Player killed the Otyugh and reached the end location.

![alt text](https://github.com/anizmo/DisplayOnly/blob/main/screens/17.%20KilledOtyugh.png?raw=true)

18. SmellGoneAfterOtyughDead.png - Player cannot detect smell once the Otyugh has been killed.

![alt text](https://github.com/anizmo/DisplayOnly/blob/main/screens/18.%20SmellGoneAfterOtyughDead.png?raw=true)

19. PlayerLostOtyugh.png - Player getting killed by the Otyugh Monster with the dialog.

![alt text](https://github.com/anizmo/DisplayOnly/blob/main/screens/19.%20PlayerLostOtyugh.png?raw=true)

20. Treasure.png - Player collecting treasure from a cave.

![alt text](https://github.com/anizmo/DisplayOnly/blob/main/screens/20.%20Treasure.png?raw=true)


### Text Based Game

There are 6 Text-Based-Game run outputs that demonstrate different outcomes and parameters of the maze formation they are as follows-

Explanation:

### Run 1 -- TestRun1_NonWrapping_PlayerWins.txt:

- Creates a Dungeon with a 4 rows and 6 columns maze that is not wrapping with degree of interconnectivity 0.
- The player moves around the maze and starts picking up arrows and treasures as and when they find it and are ordered to collect it.
- The player smells something bad and starts shooting towards all the available direction until they hear a growl of the Otyugh been attacked.
- When the arrow hits the Otyugh, the Otyugh screams indicating that it has been attacked.
- After attacking twice one more arrow is fired and since the monster is already dead the player does not hear anything.
- The player then navigates through the dungeon and reaches the end,
- The player finds a dead Otyugh in the end cave where it was hiding.
- The player wins the game by reaching the end game and the stats of the player are shown, revealing the end cave location, the treasure collected and the total score of the player.

### Run 2 -- TestRun2_Wrapping_PlayerWins.txt:

- This run is similar to the Run 1 but the Dungeon Maze used here is a wrapping dungeon and the player can move from one end of the cave to the other by moving in the opposite direction.
- The player moves around the maze and starts picking up arrows and treasures as and when they find it and are ordered to collect it.
- The player smells something bad and starts shooting towards all the available direction until they hear a growl of the Otyugh been attacked.
- When the arrow hits the Otyugh, the Otyugh screams indicating that it has been attacked.
- After attacking twice one more arrow is fired and since the monster is already dead the player does not hear anything.
- The player then navigates through the dungeon and reaches the end,
- The player finds a dead Otyugh in the end cave where it was hiding.
- The player wins the game by reaching the end game and the stats of the player are shown, revealing the end cave location, the treasure collected and the total score of the player.

### Run 3 -- TestRun3_Wrapping_PlayerLoses.txt:
- In this run, the player ends up in a cave which was the end cave but without killing the Otyugh monster, so the game results in defeat of the player.
- The player is eaten by the Otyugh and the stats of the player are shown at the end.

### Run 4 -- TestRun4_NonWrapping_PlayerLosses.txt:
- This run is similar to Run 3 but with a non-wrapping dungeon, here the player again loses by entering the cave that had a Otyugh with no injuries.
- The Otyugh eats the player and the player loses the round.
- The stats of the player are still displayed at the end.

### Run 5 -- TestRun5_NonWrapping_PlayerEscapesOtyugh.txt:
- Here, the player navigates through the tunnel, collects arrows and treasures throughout the dungeon.
- The player shoots an arrow in the dark and it hits the Otyugh.
- The player moves towards the injured Otyugh and then enters the cave with the Otyugh having 50% health.
- The Otyugh starts attacking player, the player has a 50-50 chance of escaping the cave when attacked by an injured Otyugh.
- In this run the player successfully escapes the Otyugh and then attacks it and kills it off.
- The player wins the game by entering the end cave with the dead Otyugh.

### Run 6 -- TestRun6_NonWrapping_PlayerEscapeFailedOtyugh.txt:
- Here, the player navigates through the tunnel, collects arrows and treasures throughout the dungeon.
- The player shoots an arrow in the dark and it hits the Otyugh.
- The player moves towards the injured Otyugh and then enters the cave with the Otyugh having 50% health.
- The Otyugh starts attacking player, the player has a 50-50 chance of escaping the cave when attacked by an injured Otyugh.
- In this run the player is not successful in escaping the Otyugh and is eaten by the Otyugh.
- The player loses the game and the stats are printed on the screen.

## Design/Model Changes

- Earlier I had kept the reference of all the locations to the north, south, east and west of a particular location in it.
- Now, I have changed it to only hold the openings that each location has, a set of openings is maintained at all the locations.
- I have added some additional public methods to the Maze interface that make it more testable.
- Design methodology of Player has been changed to make it such that all the movement specific logic has been shifted to the Player class.
- During the first design document submission I had kept the option to add a name to the player but since there is only one player the need to add a name to the player did not feel much usefull.
- Added a Game State Manager which was not present in the first design, this keeps a track of the state in which the game is. There are three states of the game namely, WIN, LOSE, PLAYING and QUIT which are represented by an enum.
- Added more public methods to the maze interface that returns defensive copies of some private fields that were earlier not accessible, this is done to improve the testing capability of the code.
- With the addition of controller I have added an option to quit the game.
- Changed the ShotResult from an enum to a Java Object that stores the end location of the fired arrow and the outcome of it.
- Earlier I had not considered a Bow, but in the final updated design I have added a weapon interface that is extended by a bow class. An arrow requires the player to have a bow in order to be fired or shot.
- Changed the pick all treasures to selectively pick a particular type of treasure.
- Refactored the Treasure enum to a Collectible enum as there is a new collectible "Arrow" that can be collected from the caves and tunnels now.
- Added a new weapon called Punching Gloves which the player can use for hand-to-hand combat.
- The monster interface now extends the Obstacle interface and there is one more type of monster, Berbalang that moves throughout the Dungeon searching for the player.
- Added health parameter to the player in order to be able to fight the Berbalang monster in combat.
- Devised a combat system that allows a hand to hand combat battle between the player and the moving monster.
- Added the ability for the player to jump one location in order to avoid the pit.

No other changes have been made to the design.

## Assumptions

- There are only a maximum of one of each type of collectible in a cave that is there is only one set of rubies, sapphires, diamonds and arrow in each cave or tunnel (in case of arrow).
- The player can navigate freely through the maze as long as there is a route.
- All the paths between 2 nodes in the maze are bidirectional, that is a path going from A to B is the same as a path going from B to A.
- Only caves can have collectibles in them and tunnel do not have collectibles in them.
- A location with 2 openings is a tunnel and otherwise is a cave.
- Treasure collected from one cave does not replenish again in the cave, meaning once the collectible is taken it does not exist in the cave anymore.
- The player does not directly collect the collectible as and when the land on the cave containing a collectible but explicit call for the same needs to be given.
- The player will get an exception if there is no collectible available in a cave and they still try to collect it.
- The player will get an exception if they try to go in a direction in a location which does not have an opening.
- The player finds their path through the maze by visiting each location and selecting one opening from all the available openings.
- The player starts from a random cave and has to reach a random cave that is atleast 5 moves away from the start.
- There should at least be 10 locations in a maze for it to function properly, this is to ensure that the distance between the start and end cave to be at least 5.
- Each location has only a maximum of one quantity of each type of collectible, meaning one cave can have a maximum of 1 ruby, 1 diamond and 1 sapphire.
- Each location can have a maximum of only one monster in it.
- Every arrow collected from the dungeon is a crooked arrow.
- An arrow once shot cannot be retreived and is considered as used and reduced from the player's arrow count.
- A dungeon can have multiple monsters in different locations, even in neighbouring caves.
- Otyughs can only be found in caves.
- Otyughs health is in the range of 0 to 100, it cannot go negative or above 100.
- The end user can exit the game by pressing Q at any time.
- A thief steals all the treasure from the player and runs away, he cannot be found again.
- There can be multiple thieves and pits in the dungeon.
- The Berbalang is a moving monster that listens to the player's footsteps and keeps moving towards the player in the dungeon.
- A Berbalang can move through a cave that has the Otyugh, as it flies from the caves, it cannot be hurt by arrows as it can dodge them.
- A Berbalang can only be fought in hand to hand combat and if the player's health drops to zero then the player loses the game.

## Limitations

- Since the requirement stated that the end and start caves must be atleast 5 moves apart the minimum size of the maze is set such that the sum of rows and columns should atleast be 10 or greater than 10 to ensure that when an end location is chosen randomly it does not get stuck in a loop, more of a design choice than a limitation.
- When the degree of interconnectivity increases there is a chance that the player can reach from start to end with an alternative path with a route smaller than 5 moves.
- I have used a LinkedHashSet instead of a faster option of HashSet. An HashSet can be accessed faster but provides the values randomly, making it not possible to mock the output sequence each run. Hence, for the ease of testing and being able to fully mock the outcome each time with the random generator I have used the LinkedHashSet.
- For mocking the randomness and increasing the testability of the code some methods are made public that were not originally intended to be public.
- The dungeon game runs in a fixed resolution of 1920 * 1080 and cannot be resized.
- The cells get stretched and do not maintain aspect ratio when the number of rows and columns is not equal.
- Some of the fields were not made final that were originally expected to be private due to the requirement of restarting and reseting the game to the original state.
- The fight between the moving monster and the player does not have a delay in attack which could have made the battle look even more realistic.
- The scroll does not automatically scroll to the player's position.

## Citations
- BFS algorithm in Java - Javatpoint_. www.javatpoint.com. (n.d.). Retrieved December 10, 2021, from https://www.javatpoint.com/bfs-algorithm-in-java.
- Jain, S. (2019, August 31). _Graph implementation – adjacency list java: Tutorialhorizon_. Algorithms. Retrieved December 10, 2021, from https://algorithms.tutorialhorizon.com/graph-implementation-adjacency-list-better-set-2/.
- Collections (java platform SE 6). (2015, November 19). Retrieved December 10, 2021, from https://docs.oracle.com/javase/6/docs/api/java/util/Collections.html#min%28java.util.Collection%29.
- Wildcards and subtyping. Wildcards and Subtyping (The Java™ Tutorials &gt; Learning the Java Language &gt; Generics (Updated)). (n.d.). Retrieved October 13, 2021, from https://docs.oracle.com/javase/tutorial/java/generics/subtyping.html
- Command-line arguments. Command-Line Arguments (The Java™ Tutorials &gt; Essential Java Classes &gt; The Platform Environment). (n.d.). Retrieved October 30, 2021, from https://docs.oracle.com/javase/tutorial/essential/environment/cmdLineArgs.html
