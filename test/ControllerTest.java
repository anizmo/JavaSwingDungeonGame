import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.StringReader;
import java.util.List;

import dungeon.RandomGenerator;
import dungeon.maze.DungeonConsoleController;
import dungeon.maze.DungeonController;
import dungeon.maze.DungeonMaze;
import dungeon.maze.Maze;

/**
 * The Controller Test class tests the movement of a Player in a wrapping, and a non-wrapping
 * dungeon, it also performs testing of other important methods of player such as collecting
 * treasure from a location, increasing score while collecting a treasure and test the description
 * of the player when moving from one cell location to another.
 */
public class ControllerTest {

  private Maze dungeonMaze;
  private RandomGenerator randomGenerator;
  private DungeonController controller;
  private Appendable gameLog;

  @Before
  public void setup() {
    randomGenerator = new RandomGenerator();

    randomGenerator.setRandomParameters(List.of(32, 5, 32, 21, 11, 31, 16, 9, 22, 25, 27, 16, 11, 7,
            12, 6, 3, 18, 10, 11, 17, 15, 0, 9, 2, 10, 0, 1, 0, 7, 3, 1, 5, 1, 0, 1, 1, 0, 14, 2, 1,
            2, 0, 1, 1, 1));

    dungeonMaze = new DungeonMaze(4, 6,
            0, false, 0, 1,
            0, 0, false, randomGenerator);


    gameLog = new StringBuilder();

  }

  @Test (expected = IllegalArgumentException.class)
  public void testInvalidModel() {
    StringReader input = new StringReader("");
    controller = new DungeonConsoleController(input, gameLog);
    controller.playGame(null);
  }

  @Test
  public void testSmellBad() {
    randomGenerator.setRandomParameters(List.of(13, 32, 17, 33, 13, 3, 15, 0, 0, 17, 15, 6, 12, 1,
            15, 17, 10, 2, 19, 4, 9, 1, 2, 2, 9, 4, 2, 3, 7, 6, 3, 1, 2, 4, 2, 1, 1, 0, 6, 0));

    dungeonMaze = new DungeonMaze(4, 6,
            0, false, 0, 1,
            0, 0, false, randomGenerator);

    StringReader input = new StringReader("m w m n m e m n m e m n m e m e m s");
    controller = new DungeonConsoleController(input, gameLog);
    controller.playGame(dungeonMaze);
    String[] gameLogArray = gameLog.toString().split("\n");
    Assert.assertEquals("You smell something bad", gameLogArray[gameLogArray.length - 4]);
    Assert.assertEquals("You are in a cave", gameLogArray[gameLogArray.length - 3]);
    Assert.assertEquals("Doors lead to the north, south, east, west",
            gameLogArray[gameLogArray.length - 2]);
  }

  @Test
  public void testSmellTerrible() {
    randomGenerator.setRandomParameters(List.of(13, 32, 17, 33, 13, 3, 15, 0, 0, 17, 15, 6, 12, 1,
            15, 17, 10, 2, 19, 4, 9, 1, 2, 2, 9, 4, 2, 3, 7, 6, 3, 1, 2, 4, 2, 1, 1, 0, 6, 0));

    dungeonMaze = new DungeonMaze(4, 6,
            0, false, 0, 1,
            0, 0, false, randomGenerator);

    StringReader input = new StringReader("m w m n m e m n m e m n m e m e m s m e");
    controller = new DungeonConsoleController(input, gameLog);
    controller.playGame(dungeonMaze);
    String[] gameLogArray = gameLog.toString().split("\n");
    Assert.assertEquals("You smell something terrible nearby",
            gameLogArray[gameLogArray.length - 4]);
    Assert.assertEquals("You are in a tunnel", gameLogArray[gameLogArray.length - 3]);
  }

  @Test
  public void testSmellAfterKilling() {
    randomGenerator.setRandomParameters(List.of(13, 32, 17, 33, 13, 3, 15, 0, 0, 17, 15, 6, 12, 1,
            15, 17, 10, 2, 19, 4, 9, 1, 2, 2, 9, 4, 2, 3, 7, 6, 3, 1, 2, 4, 2, 1, 1, 0, 6, 0));

    dungeonMaze = new DungeonMaze(4, 6,
            0, false, 0, 1,
            0, 0, false, randomGenerator);

    StringReader input = new StringReader("m w m n m e m n m e m n m e m e m s m e s n 1 s n 1");
    controller = new DungeonConsoleController(input, gameLog);
    controller.playGame(dungeonMaze);
    String[] gameLogArray = gameLog.toString().split("\n");
    //One location away from the monster.
    Assert.assertEquals("You smell something terrible nearby", gameLogArray[54]);
    //An arrow is shot at the monster at west distance 1.
    Assert.assertEquals("An arrow is shot in the dark!", gameLogArray[60]);
    //Arrow hits the Otyugh.
    Assert.assertEquals("You hear a great howl in the distance!", gameLogArray[61]);

    //Smell continues to exist even after one arrow hits the monster.
    Assert.assertEquals("You smell something terrible nearby", gameLogArray[64]);
    Assert.assertEquals("You are in a tunnel", gameLogArray[65]);
    Assert.assertEquals("An arrow is shot in the dark!", gameLogArray[70]);
    Assert.assertEquals("You hear a great howl in the distance!", gameLogArray[71]);
    //Arrow count decreases further.
    Assert.assertEquals("You now have 1 arrows with you", gameLogArray[72]);
    //Smell disappears once the monster is killed
    Assert.assertEquals("You are in a tunnel", gameLogArray[74]);
  }

  @Test
  public void testPlayerDeath() {
    randomGenerator.setRandomParameters(List.of(13, 32, 17, 33, 13, 3, 15, 0, 0, 17, 15, 6, 12, 1,
            15, 17, 10, 2, 19, 4, 9, 1, 2, 2, 9, 4, 2, 3, 7, 6, 3, 1, 2, 4, 2, 1, 1, 0, 6, 0));

    dungeonMaze = new DungeonMaze(4, 6,
            0, false, 0, 1,
            0, 0, false, randomGenerator);

    StringReader input = new StringReader("m w m n m e m n m e m n m e m e m s m e m n");
    controller = new DungeonConsoleController(input, gameLog);
    controller.playGame(dungeonMaze);
    String[] gameLogArray = gameLog.toString().split("\n");
    Assert.assertEquals("You smell something terrible nearby",
            gameLogArray[gameLogArray.length - 10]);
    Assert.assertEquals("Chomp, chomp, chomp, you are eaten by an Otyugh!",
            gameLogArray[gameLogArray.length - 4]);
    Assert.assertEquals("Better luck next time!",
            gameLogArray[gameLogArray.length - 3]);
    Assert.assertEquals(
            "Your stats are as follows - (treasure = {}, current location = (0, 5))",
            gameLogArray[gameLogArray.length - 1]);
  }

  @Test
  public void testPlayerWin() {
    randomGenerator.setRandomParameters(List.of(13, 32, 17, 33, 13, 3, 15, 0, 0, 17, 15, 6, 12, 1,
            15, 17, 10, 2, 19, 4, 9, 1, 2, 2, 9, 4, 2, 3, 7, 6, 3, 1, 2, 4, 2, 1, 1, 0, 6, 0));

    dungeonMaze = new DungeonMaze(4, 6,
            0, false, 0, 1,
            0, 0, false, randomGenerator);

    StringReader input = new StringReader("m w m n m e m n m e m n m e m e m s m e s n 1 s n 1 "
            + "m n");
    controller = new DungeonConsoleController(input, gameLog);
    controller.playGame(dungeonMaze);
    String[] gameLogArray = gameLog.toString().split("\n");
    Assert.assertEquals("You look around and find yourself standing on a dead Otyugh!",
            gameLogArray[79]);
  }

  @Test
  public void testPlayerEscapeInjuredOtyugh() {
    randomGenerator.setRandomParameters(List.of(13, 32, 17, 33, 13, 3, 15, 0, 0, 17, 15, 6, 12, 1,
            15, 17, 10, 2, 19, 4, 9, 1, 2, 2, 9, 4, 2, 3, 7, 6, 3, 1, 2, 4, 2, 1, 1, 0, 6, 0));

    dungeonMaze = new DungeonMaze(4, 6,
            0, false, 0, 1,
            0, 0, false, randomGenerator);

    StringReader input = new StringReader("m w m n m e m n m e m n m e m e m s m e s n 1 m n");
    randomGenerator.setRandomParameters(List.of(1));
    controller = new DungeonConsoleController(input, gameLog);
    controller.playGame(dungeonMaze);
    String[] gameLogArray = gameLog.toString().split("\n");
    Assert.assertEquals("There is an injured Otyugh in the cave, you can still escape!",
            gameLogArray[70]);
    Assert.assertEquals("Move quickly!", gameLogArray[71]);
    //Surviving and getting the chance to move even when the player is in the Otyugh cave
    Assert.assertEquals("You are in a cave", gameLogArray[73]);
  }

  @Test
  public void testPlayerEscapeFailedInjuredOtyugh() {
    randomGenerator.setRandomParameters(List.of(13, 32, 17, 33, 13, 3, 15, 0, 0, 17, 15, 6, 12, 1,
            15, 17, 10, 2, 19, 4, 9, 1, 2, 2, 9, 4, 2, 3, 7, 6, 3, 1, 2, 4, 2, 1, 1, 0, 6, 0));

    dungeonMaze = new DungeonMaze(4, 6,
            0, false, 0, 1,
            0, 0, false, randomGenerator);

    StringReader input = new StringReader("m w m n m e m n m e m n m e m e m s m e s n 1 m n");
    randomGenerator.setRandomParameters(List.of(0));
    controller = new DungeonConsoleController(input, gameLog);
    controller.playGame(dungeonMaze);
    String[] gameLogArray = gameLog.toString().split("\n");
    Assert.assertEquals("There is an injured Otyugh in the cave, you can still escape!",
            gameLogArray[70]);
    Assert.assertEquals("Move quickly!", gameLogArray[71]);
    Assert.assertEquals("Chomp, chomp, chomp, you are eaten by an Otyugh!",
            gameLogArray[72]);
    Assert.assertEquals("Better luck next time!",
            gameLogArray[73]);
  }

  @Test
  public void testPlayerArrowFire() {
    StringReader input = new StringReader("s w 1");
    controller = new DungeonConsoleController(input, gameLog);
    controller.playGame(dungeonMaze);
    String[] gameLogArray = gameLog.toString().split("\n");
    Assert.assertEquals("Shoot in which direction?", gameLogArray[6]);
    //Valid input 'w' given here.
    Assert.assertEquals("How far? (in number of caves)", gameLogArray[7]);
    //Valid input '1' given here.
    Assert.assertEquals("An arrow is shot in the dark!", gameLogArray[8]);
    Assert.assertEquals("You do not hear anything", gameLogArray[9]);
    Assert.assertEquals("You now have 2 arrows with you", gameLogArray[10]);
  }

  @Test
  public void testMonsterOneHit() {
    randomGenerator.setRandomParameters(List.of(13, 32, 17, 33, 13, 3, 15, 0, 0, 17, 15, 6, 12, 1,
            15, 17, 10, 2, 19, 4, 9, 1, 2, 2, 9, 4, 2, 3, 7, 6, 3, 1, 2, 4, 2, 1, 1, 0, 6, 0));

    dungeonMaze = new DungeonMaze(4, 6,
            0, false, 0, 1,
            0, 0, false, randomGenerator);

    StringReader input = new StringReader("m w m n m e m n m e m n m e m e m s m e s n 1");
    controller = new DungeonConsoleController(input, gameLog);
    controller.playGame(dungeonMaze);
    String[] gameLogArray = gameLog.toString().split("\n");
    Assert.assertEquals("Shoot in which direction?", gameLogArray[58]);
    //Valid input 'w' given here.
    Assert.assertEquals("How far? (in number of caves)", gameLogArray[59]);
    //Valid input '1' given here.
    Assert.assertEquals("An arrow is shot in the dark!", gameLogArray[60]);
    //Arrow hits the Otyugh.
    Assert.assertEquals("You hear a great howl in the distance!", gameLogArray[61]);
  }

  @Test
  public void testMonsterTwoHit() {
    randomGenerator.setRandomParameters(List.of(13, 32, 17, 33, 13, 3, 15, 0, 0, 17, 15, 6, 12, 1,
            15, 17, 10, 2, 19, 4, 9, 1, 2, 2, 9, 4, 2, 3, 7, 6, 3, 1, 2, 4, 2, 1, 1, 0, 6, 0));

    dungeonMaze = new DungeonMaze(4, 6,
            0, false, 0, 1,
            0, 0, false, randomGenerator);

    StringReader input = new StringReader("m w m n m e m n m e m n m e m e m s m e s n 1 s n 1");
    controller = new DungeonConsoleController(input, gameLog);
    controller.playGame(dungeonMaze);
    String[] gameLogArray = gameLog.toString().split("\n");
    Assert.assertEquals("Shoot in which direction?", gameLogArray[58]);
    //Valid input 'w' given here.
    Assert.assertEquals("How far? (in number of caves)", gameLogArray[59]);
    //Valid input '1' given here.
    Assert.assertEquals("An arrow is shot in the dark!", gameLogArray[60]);
    //Arrow hits the Otyugh.
    Assert.assertEquals("You hear a great howl in the distance!", gameLogArray[61]);
    //Arrow count decreases from 2 to 1.
    Assert.assertEquals("You now have 2 arrows with you", gameLogArray[62]);
    //Valid input 'w' given here.
    Assert.assertEquals("How far? (in number of caves)", gameLogArray[69]);
    //Valid input '1' given here.
    Assert.assertEquals("An arrow is shot in the dark!", gameLogArray[70]);
    //Arrow hits the Otyugh.
    Assert.assertEquals("You hear a great howl in the distance!", gameLogArray[71]);
    //Arrow count decreases further.
    Assert.assertEquals("You now have 1 arrows with you", gameLogArray[72]);
  }

  @Test
  public void testMonsterAfterTwoHit() {
    randomGenerator.setRandomParameters(List.of(13, 32, 17, 33, 13, 3, 15, 0, 0, 17, 15, 6, 12, 1,
            15, 17, 10, 2, 19, 4, 9, 1, 2, 2, 9, 4, 2, 3, 7, 6, 3, 1, 2, 4, 2, 1, 1, 0, 6, 0));

    dungeonMaze = new DungeonMaze(4, 6,
            0, false, 0, 1,
            0, 0, false, randomGenerator);

    StringReader input = new StringReader("m w m n m e m n m e m n m e m e m s m e s n 1 s n 1");
    controller = new DungeonConsoleController(input, gameLog);
    controller.playGame(dungeonMaze);
    String[] gameLogArray = gameLog.toString().split("\n");
    Assert.assertEquals("Shoot in which direction?", gameLogArray[58]);
    //Valid input 'w' given here.
    Assert.assertEquals("How far? (in number of caves)", gameLogArray[59]);
    //Valid input '1' given here.
    Assert.assertEquals("An arrow is shot in the dark!", gameLogArray[60]);
    //Arrow hits the Otyugh.
    Assert.assertEquals("You hear a great howl in the distance!", gameLogArray[61]);
    //Arrow count decreases from 2 to 1.
    Assert.assertEquals("You now have 2 arrows with you", gameLogArray[62]);
    //Valid input 'w' given here.
    Assert.assertEquals("How far? (in number of caves)", gameLogArray[69]);
    //Valid input '1' given here.
    Assert.assertEquals("An arrow is shot in the dark!", gameLogArray[70]);
    //Arrow hits the Otyugh.
    Assert.assertEquals("You hear a great howl in the distance!", gameLogArray[71]);
    //Arrow count decreases further.
    Assert.assertEquals("You now have 1 arrows with you", gameLogArray[72]);
  }

  @Test
  public void testWalkingOverDeadMonster() {
    randomGenerator.setRandomParameters(List.of(13, 32, 17, 33, 13, 3, 15, 0, 0, 17, 15, 6, 12, 1,
            15, 17, 10, 2, 19, 4, 9, 1, 2, 2, 9, 4, 2, 3, 7, 6, 3, 1, 2, 4, 2, 1, 1, 0, 6, 0));

    dungeonMaze = new DungeonMaze(4, 6,
            0, false, 0, 1,
            0, 0, false, randomGenerator);

    StringReader input = new StringReader("m w m n m e m n m e m n m e m e m s m e s n 1 "
            + "s n 1 m n");
    controller = new DungeonConsoleController(input, gameLog);
    controller.playGame(dungeonMaze);
    String[] gameLogArray = gameLog.toString().split("\n");
    Assert.assertEquals("An arrow is shot in the dark!", gameLogArray[60]);
    //Arrow hits the Otyugh.
    Assert.assertEquals("You hear a great howl in the distance!", gameLogArray[61]);
    //Arrow is shot at the Otyugh again.
    Assert.assertEquals("An arrow is shot in the dark!", gameLogArray[70]);
    //Arrow hits the Otyugh, it is assumed the monster is dead.
    Assert.assertEquals("You hear a great howl in the distance!", gameLogArray[71]);

    //Arrow hits the Otyugh, it is assumed the monster is dead.
    Assert.assertEquals("You look around and find yourself standing on a dead Otyugh!",
            gameLogArray[79]);
  }

  @Test
  public void testPlayerShootNoArrow() {
    //Exhaust 3 arrows by shooting them at west 1 distance thrice.
    StringReader input = new StringReader("s w 1 s w 1 s w 1 s w 1");
    controller = new DungeonConsoleController(input, gameLog);
    controller.playGame(dungeonMaze);
    String[] gameLogArray = gameLog.toString().split("\n");
    Assert.assertEquals("You now have 0 arrows with you",
            gameLogArray[28]);
    Assert.assertEquals("Cannot shoot because you have no arrows left!",
            gameLogArray[35]);
  }

  @Test
  public void testPlayerMoveNoOpeningAvailable() {
    randomGenerator.setRandomParameters(List.of(13, 32, 17, 33, 13, 3, 15, 0, 0, 17, 15, 6, 12, 1,
            15, 17, 10, 2, 19, 4, 9, 1, 2, 2, 9, 4, 2, 3, 7, 6, 3, 1, 2, 4, 2, 1, 1, 0, 6, 0));

    dungeonMaze = new DungeonMaze(4, 6,
            0, false, 0, 1,
            0, 0, false, randomGenerator);

    StringReader input = new StringReader("m e");
    controller = new DungeonConsoleController(input, gameLog);
    controller.playGame(dungeonMaze);
    String[] gameLogArray = gameLog.toString().split("\n");
    Assert.assertEquals("Doors lead to the west", gameLogArray[4]);
    Assert.assertEquals("Player cannot move east, there is no opening available",
            gameLogArray[7]);
  }

  @Test
  public void testPlayerShootNoOpeningAvailable() {
    StringReader input = new StringReader("s s 1");
    controller = new DungeonConsoleController(input, gameLog);
    controller.playGame(dungeonMaze);
    String[] gameLogArray = gameLog.toString().split("\n");
    Assert.assertEquals("Doors lead to the north, east, west", gameLogArray[4]);
    Assert.assertEquals("There is no opening towards SOUTH, an arrow is wasted.",
            gameLogArray[9]);
  }

  @Test
  public void testPlayerPickupNoTreasureAvailable() {
    StringReader input = new StringReader("p diamonds");
    controller = new DungeonConsoleController(input, gameLog);
    controller.playGame(dungeonMaze);
    String[] gameLogArray = gameLog.toString().split("\n");
    Assert.assertEquals("Doors lead to the north, east, west", gameLogArray[4]);
    Assert.assertEquals("There are no diamonds in the player's current location",
            gameLogArray[7]);
  }

  @Test
  public void testPlayerPickupNoArrowAvailable() {
    StringReader input = new StringReader("p arrow");
    controller = new DungeonConsoleController(input, gameLog);
    controller.playGame(dungeonMaze);
    String[] gameLogArray = gameLog.toString().split("\n");
    Assert.assertEquals("Doors lead to the north, east, west", gameLogArray[4]);
    Assert.assertEquals("There is no arrow in this location",
            gameLogArray[7]);
  }

  @Test
  public void testPlayerShootNegativeDistance() {
    StringReader input = new StringReader("s w -1");
    controller = new DungeonConsoleController(input, gameLog);
    controller.playGame(dungeonMaze);
    String[] gameLogArray = gameLog.toString().split("\n");
    Assert.assertEquals("Doors lead to the north, east, west", gameLogArray[4]);
    Assert.assertEquals("Distance cannot be -1, enter a positive integer", gameLogArray[9]);
  }

  @Test
  public void testPlayerShootWordDistance() {
    StringReader input = new StringReader("s w one");
    controller = new DungeonConsoleController(input, gameLog);
    controller.playGame(dungeonMaze);
    String[] gameLogArray = gameLog.toString().split("\n");
    Assert.assertEquals("How far? (in number of caves)", gameLogArray[7]);
    Assert.assertEquals("Please enter a number", gameLogArray[8]);
  }

  @Test
  public void testPlayerShootZeroDistance() {
    StringReader input = new StringReader("s w 0");
    controller = new DungeonConsoleController(input, gameLog);
    controller.playGame(dungeonMaze);
    String[] gameLogArray = gameLog.toString().split("\n");
    Assert.assertEquals("Doors lead to the north, east, west", gameLogArray[4]);
    Assert.assertEquals("Distance cannot be 0, enter a positive integer", gameLogArray[9]);
  }

  @Test
  public void testPlayerShootMonsterAtExtraDistance() {
    randomGenerator.setRandomParameters(List.of(13, 32, 17, 33, 13, 3, 15, 0, 0, 17, 15, 6, 12, 1,
            15, 17, 10, 2, 19, 4, 9, 1, 2, 2, 9, 4, 2, 3, 7, 6, 3, 1, 2, 4, 2, 1, 1, 0, 6, 0));

    dungeonMaze = new DungeonMaze(4, 6,
            0, false, 0, 1,
            0, 0, false, randomGenerator);

    StringReader input = new StringReader("m w m n m e m n m e m n m e m e m s m e s n 10");
    controller = new DungeonConsoleController(input, gameLog);
    controller.playGame(dungeonMaze);
    String[] gameLogArray = gameLog.toString().split("\n");
    Assert.assertEquals("Shoot in which direction?", gameLogArray[58]);
    //Valid input '1' given here.
    Assert.assertEquals("An arrow is shot in the dark!", gameLogArray[60]);
    //Arrow does not hit the Otyugh because the distance was bigger than the distance of monster.
    Assert.assertEquals("You do not hear anything", gameLogArray[61]);
  }

  @Test
  public void testPlayerShootMiss() {
    StringReader input = new StringReader("s w 1");
    controller = new DungeonConsoleController(input, gameLog);
    controller.playGame(dungeonMaze);
    String[] gameLogArray = gameLog.toString().split("\n");
    Assert.assertEquals("Shoot in which direction?", gameLogArray[6]);
    //Valid input 'w' given here.
    Assert.assertEquals("How far? (in number of caves)", gameLogArray[7]);
    //Valid input '1' given here.
    Assert.assertEquals("An arrow is shot in the dark!", gameLogArray[8]);
    Assert.assertEquals("You do not hear anything", gameLogArray[9]);
  }

  @Test
  public void testControllerQuit() {
    Maze m = new DungeonMaze(4, 6, 0,
            false, 20, 1, 0, 0, false);
    StringReader input = new StringReader("q");
    Appendable gameLog = new StringBuilder();
    controller = new DungeonConsoleController(input, gameLog);
    controller.playGame(m);
    String[] gameLogArray = gameLog.toString().split("\n");
    Assert.assertEquals("You quit the game, come back again to fight the Otyughs!",
            gameLogArray[gameLogArray.length - 2]);
  }

  @Test
  public void testInvalidMPS() {
    StringReader input = new StringReader("t m t w p t d s b e t 1");
    controller = new DungeonConsoleController(input, gameLog);
    controller.playGame(dungeonMaze);
    String[] gameLogArray = gameLog.toString().split("\n");
    Assert.assertEquals("Move, Pickup, or Shoot (M-P-S)?", gameLogArray[5]);
    //Input 't' here and get an error because there is no 't' option available.
    Assert.assertEquals("Invalid Input, try again.", gameLogArray[6]);
    //We input invalid direction 't' here and get an error of invalid direction.
    Assert.assertEquals("Which direction?", gameLogArray[11]);
    Assert.assertEquals("Invalid Direction, Try Again.", gameLogArray[12]);
    //We then input valid direction 'w' here.
    Assert.assertEquals("Pickup what? (rubies/diamonds/sapphires/arrow)", gameLogArray[18]);
    //Invalid input 't' given here.
    Assert.assertEquals("Invalid Input, try again.", gameLogArray[19]);

    Assert.assertEquals("Shoot in which direction?", gameLogArray[26]);
    //Invalid shooting direction 't' given here.
    Assert.assertEquals("Invalid Direction, Try Again.", gameLogArray[27]);

    //After providing valid direction, we provide invalid distance 't' which is not a number.
    Assert.assertEquals("How far? (in number of caves)", gameLogArray[29]);
    //Error given.
    Assert.assertEquals("Please enter a number", gameLogArray[30]);
  }

  @Test
  public void testValidMoveLandInCave() {
    StringReader input = new StringReader("m w");
    controller = new DungeonConsoleController(input, gameLog);
    controller.playGame(dungeonMaze);
    String[] gameLogArray = gameLog.toString().split("\n");
    Assert.assertEquals("You are in a cave", gameLogArray[3]);
    Assert.assertEquals("Doors lead to the north, east, west", gameLogArray[4]);
    Assert.assertEquals("Move, Pickup, or Shoot (M-P-S)?", gameLogArray[5]);
    Assert.assertEquals("Which direction?", gameLogArray[6]);
    //Valid input 'w' given here.
    Assert.assertEquals("You are in a cave", gameLogArray[8]);
    Assert.assertEquals("Doors lead to the east", gameLogArray[9]);
  }

  @Test
  public void testValidMoveLandInTunnel() {
    StringReader input = new StringReader("m e");
    controller = new DungeonConsoleController(input, gameLog);
    controller.playGame(dungeonMaze);
    String[] gameLogArray = gameLog.toString().split("\n");
    Assert.assertEquals("You are in a cave", gameLogArray[3]);
    Assert.assertEquals("Doors lead to the north, east, west", gameLogArray[4]);
    Assert.assertEquals("Move, Pickup, or Shoot (M-P-S)?", gameLogArray[5]);
    Assert.assertEquals("Which direction?", gameLogArray[6]);
    //Valid input 'e' given here.
    Assert.assertEquals("You are in a tunnel", gameLogArray[8]);
    Assert.assertEquals("Doors lead to the east, west", gameLogArray[9]);
  }

  @Test
  public void moveInLocationWithArrowAndTreasure() {
    //Creates a wrapping dungeon where the Otyugh is 2 caves and three tunnels apart from player.
    randomGenerator.setRandomParameters(List.of(26, 26, 26, 31, 29, 3, 6, 16, 9, 12, 14, 18, 15,
            15, 18, 10, 9, 19, 4, 3, 16, 16, 6, 5, 2, 4, 1, 8, 7, 8, 1, 1, 4, 3, 2, 1, 1, 0, 4, 0,
            7, 13, 7, 10, 14, 0, 9, 5, 13, 14, 0, 3, 16, 8, 12, 1, 11, 15, 3, 9, 9, 4, 11, 1, 4,
            16, 4, 9, 15, 11, 15, 0, 13, 9, 7, 15, 8, 7, 1, 8, 13, 12, 0, 14, 15, 2, 9, 4, 5, 11,
            16, 5, 11, 7, 10, 9, 11, 2, 6, 1, 0, 3, 2, 0, 2, 0, 2, 2, 1, 1, 1, 3, 1, 0, 1, 1, 2, 3,
            0, 1, 2, 2, 2, 2, 2, 0, 2, 1, 0, 1, 1, 3, 0, 0, 1, 2, 2, 2, 1, 2, 0, 1, 2, 2, 1, 2, 0,
            1, 3, 1, 0, 2, 1, 2, 1, 2, 2, 0, 1, 6, 23, 15, 23, 4, 13, 8, 1, 4, 9, 6, 0, 6, 16, 4,
            5, 20, 9, 20, 22, 4, 14, 6, 5, 22, 15, 9, 21, 7, 3, 9, 1, 5, 11, 20, 13, 17, 6, 1, 2,
            5, 14, 11, 14, 9, 14, 3, 7, 7, 12, 12, 15, 2, 3, 0, 4, 1, 15, 11, 16, 17, 1, 8, 20, 13,
            9, 5, 9, 14, 6, 4, 20, 18, 18, 22, 20, 10, 16, 2, 20, 15, 5, 6, 8, 15, 4, 12, 16, 0,
            22, 17, 23, 21, 15, 9, 2, 21, 14, 22, 14, 0, 6, 6, 0, 13, 9, 1, 6, 10, 18, 11, 11, 11,
            0, 18, 19, 13, 0, 0, 2, 0, 1, 2, 1, 1, 0, 0, 1, 1, 0, 0, 2, 2, 1, 1, 0, 0, 1, 1, 0, 0,
            2, 3, 2, 2, 1, 1, 2, 1, 1, 2, 0, 0, 2, 0, 2, 7));

    Maze dungeon = new DungeonMaze(4, 6, 8,
            false, 100, 2, 0, 0,
            false, randomGenerator);

    StringReader input = new StringReader("m n");
    controller = new DungeonConsoleController(input, gameLog);
    controller.playGame(dungeon);
    String[] gameLogArray = gameLog.toString().split("\n");
    Assert.assertEquals("Which direction?", gameLogArray[9]);
    Assert.assertEquals("You are in a cave", gameLogArray[12]);
    Assert.assertEquals("You find diamonds, rubies", gameLogArray[13]);
    Assert.assertEquals("You find an arrow here", gameLogArray[14]);
    Assert.assertEquals("Doors lead to the north, south, west", gameLogArray[15]);
  }

  @Test
  public void testTreasurePickup() {

    randomGenerator.setRandomParameters(List.of(26, 26, 26, 31, 29, 3, 6, 16, 9, 12, 14, 18, 15,
            15, 18, 10, 9, 19, 4, 3, 16, 16, 6, 5, 2, 4, 1, 8, 7, 8, 1, 1, 4, 3, 2, 1, 1, 0, 4, 0,
            7, 13, 7, 10, 14, 0, 9, 5, 13, 14, 0, 3, 16, 8, 12, 1, 11, 15, 3, 9, 9, 4, 11, 1, 4,
            16, 4, 9, 15, 11, 15, 0, 13, 9, 7, 15, 8, 7, 1, 8, 13, 12, 0, 14, 15, 2, 9, 4, 5, 11,
            16, 5, 11, 7, 10, 9, 11, 2, 6, 1, 0, 3, 2, 0, 2, 0, 2, 2, 1, 1, 1, 3, 1, 0, 1, 1, 2, 3,
            0, 1, 2, 2, 2, 2, 2, 0, 2, 1, 0, 1, 1, 3, 0, 0, 1, 2, 2, 2, 1, 2, 0, 1, 2, 2, 1, 2, 0,
            1, 3, 1, 0, 2, 1, 2, 1, 2, 2, 0, 1, 6, 23, 15, 23, 4, 13, 8, 1, 4, 9, 6, 0, 6, 16, 4,
            5, 20, 9, 20, 22, 4, 14, 6, 5, 22, 15, 9, 21, 7, 3, 9, 1, 5, 11, 20, 13, 17, 6, 1, 2,
            5, 14, 11, 14, 9, 14, 3, 7, 7, 12, 12, 15, 2, 3, 0, 4, 1, 15, 11, 16, 17, 1, 8, 20, 13,
            9, 5, 9, 14, 6, 4, 20, 18, 18, 22, 20, 10, 16, 2, 20, 15, 5, 6, 8, 15, 4, 12, 16, 0,
            22, 17, 23, 21, 15, 9, 2, 21, 14, 22, 14, 0, 6, 6, 0, 13, 9, 1, 6, 10, 18, 11, 11, 11,
            0, 18, 19, 13, 0, 0, 2, 0, 1, 2, 1, 1, 0, 0, 1, 1, 0, 0, 2, 2, 1, 1, 0, 0, 1, 1, 0, 0,
            2, 3, 2, 2, 1, 1, 2, 1, 1, 2, 0, 0, 2, 0, 2, 7));

    Maze dungeon = new DungeonMaze(4, 6, 8,
            false, 100, 2, 0, 0,
            false, randomGenerator);

    StringReader input = new StringReader("p diamonds q");
    controller = new DungeonConsoleController(input, gameLog);
    controller.playGame(dungeon);
    String[] gameLogArray = gameLog.toString().split("\n");
    Assert.assertEquals("You are in a cave", gameLogArray[4]);
    Assert.assertEquals("You find diamonds", gameLogArray[5]);
    Assert.assertEquals("You find an arrow here", gameLogArray[6]);
    Assert.assertEquals("Doors lead to the north, south, west", gameLogArray[7]);
    Assert.assertEquals("Pickup what? (rubies/diamonds/sapphires/arrow)", gameLogArray[9]);
    Assert.assertEquals("You picked up diamonds", gameLogArray[10]);
    //Arrow count increases to 4 from default 3
    Assert.assertEquals(
            "Your stats are as follows - (treasure = {diamonds=1},"
                    + " current location = (2, 5))", gameLogArray[19]);
  }

  @Test
  public void testPlayerArrowPickup() {

    randomGenerator.setRandomParameters(List.of(26, 26, 26, 31, 29, 3, 6, 16, 9, 12, 14, 18, 15,
            15, 18, 10, 9, 19, 4, 3, 16, 16, 6, 5, 2, 4, 1, 8, 7, 8, 1, 1, 4, 3, 2, 1, 1, 0, 4, 0,
            7, 13, 7, 10, 14, 0, 9, 5, 13, 14, 0, 3, 16, 8, 12, 1, 11, 15, 3, 9, 9, 4, 11, 1, 4,
            16, 4, 9, 15, 11, 15, 0, 13, 9, 7, 15, 8, 7, 1, 8, 13, 12, 0, 14, 15, 2, 9, 4, 5, 11,
            16, 5, 11, 7, 10, 9, 11, 2, 6, 1, 0, 3, 2, 0, 2, 0, 2, 2, 1, 1, 1, 3, 1, 0, 1, 1, 2, 3,
            0, 1, 2, 2, 2, 2, 2, 0, 2, 1, 0, 1, 1, 3, 0, 0, 1, 2, 2, 2, 1, 2, 0, 1, 2, 2, 1, 2, 0,
            1, 3, 1, 0, 2, 1, 2, 1, 2, 2, 0, 1, 6, 23, 15, 23, 4, 13, 8, 1, 4, 9, 6, 0, 6, 16, 4,
            5, 20, 9, 20, 22, 4, 14, 6, 5, 22, 15, 9, 21, 7, 3, 9, 1, 5, 11, 20, 13, 17, 6, 1, 2,
            5, 14, 11, 14, 9, 14, 3, 7, 7, 12, 12, 15, 2, 3, 0, 4, 1, 15, 11, 16, 17, 1, 8, 20, 13,
            9, 5, 9, 14, 6, 4, 20, 18, 18, 22, 20, 10, 16, 2, 20, 15, 5, 6, 8, 15, 4, 12, 16, 0,
            22, 17, 23, 21, 15, 9, 2, 21, 14, 22, 14, 0, 6, 6, 0, 13, 9, 1, 6, 10, 18, 11, 11, 11,
            0, 18, 19, 13, 0, 0, 2, 0, 1, 2, 1, 1, 0, 0, 1, 1, 0, 0, 2, 2, 1, 1, 0, 0, 1, 1, 0, 0,
            2, 3, 2, 2, 1, 1, 2, 1, 1, 2, 0, 0, 2, 0, 2, 7));

    Maze dungeon = new DungeonMaze(4, 6, 8,
            false, 100, 2, 0, 0,
            false, randomGenerator);

    StringReader input = new StringReader("p arrow");
    controller = new DungeonConsoleController(input, gameLog);
    controller.playGame(dungeon);
    String[] gameLogArray = gameLog.toString().split("\n");
    Assert.assertEquals("You are in a cave", gameLogArray[4]);
    Assert.assertEquals("You find diamonds", gameLogArray[5]);
    Assert.assertEquals("You find an arrow here", gameLogArray[6]);
    Assert.assertEquals("Pickup what? (rubies/diamonds/sapphires/arrow)", gameLogArray[9]);
    Assert.assertEquals("You picked up an arrow", gameLogArray[10]);
    //Arrow count increases to 4 from default 3
    Assert.assertEquals("You now have 4 arrows with you", gameLogArray[11]);
  }

}