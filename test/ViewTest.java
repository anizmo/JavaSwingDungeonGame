import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import dungeon.RandomGenerator;
import dungeon.location.MovementDirection;
import dungeon.maze.DungeonGuiController;
import dungeon.maze.DungeonMaze;
import dungeon.maze.DungeonSwingController;
import dungeon.view.DungeonSwingView;
import dungeon.view.DungeonView;
import dungeon.maze.Maze;
import dungeon.view.MockView;

/**
 * This class tests the methods of the view by mocking it and calling the controller methods in
 * order to validate that appropriate methods are called in the controller by the view. All the
 * public methods of the view and the constructor are validated here using the MockView and the
 * original controller and the model. As the model is mocked we know what will be the output of
 * each move of the player.
 */
public class ViewTest {

  private DungeonView dungeonView;
  private DungeonGuiController dungeonGuiController;
  private Maze model;
  private Appendable appendable;

  @Before
  public void setup() {
    appendable = new StringBuilder();
    RandomGenerator randomGenerator = new RandomGenerator();
    randomGenerator.setRandomParameters(List.of(59, 44, 0, 36, 24, 44, 33, 0, 46, 45, 49, 47, 36,
            32, 31, 28, 21, 13, 33, 28, 29, 11, 37, 8, 7, 32, 0, 29, 10, 27, 14, 18, 6, 19, 10, 5,
            6, 7, 15, 8, 17, 3, 17, 15, 7, 5, 1, 3, 4, 4, 7, 3, 6, 4, 0, 1, 3, 2, 1, 0, 15, 23, 22,
            8, 12, 6, 12, 7, 2, 0, 2, 1, 2, 2, 2, 2, 0, 1, 2, 27, 34, 6, 3, 1, 30, 2, 16, 12, 7, 17,
            12));

    model = new DungeonMaze(6, 6, 3,
            false, 20, 1, 1, 1,
            true, randomGenerator);
    dungeonView = new MockView(appendable);
    dungeonGuiController = new DungeonSwingController(model, dungeonView);
  }

  @Test
  public void testIllegalPlayerMovement() {
    dungeonGuiController.movePlayer(MovementDirection.WEST);
    Assert.assertEquals("\ntitle = Dungeon"
            + "\nmessage = Player cannot move west, there is no "
            + "opening available\nrefresh", appendable.toString());
  }

  @Test
  public void testGameRestart() {
    dungeonGuiController.handleRestartGame(4, 6,
            1, false, 100, 1,
            1, 1, true);
    Assert.assertEquals("\n"
            + "dispose view called", appendable.toString());
  }

  @Test
  public void testGameReset() {
    dungeonGuiController.handleResetGame();
    Assert.assertEquals("\n"
            + "refresh", appendable.toString());
  }

  @Test
  public void testCollectTreasure() {
    dungeonGuiController.handleCollectRubies();
    dungeonGuiController.handleCollectDiamonds();
    dungeonGuiController.handleCollectSapphires();
    Assert.assertEquals("\n"
            + "title = Collect Rubies\n"
            + "message = There is no treasure in the current location.\n"
            + "refresh\n"
            + "title = Collect Diamonds\n"
            + "message = There is no treasure in the current location.\n"
            + "refresh\n"
            + "title = Collect Sapphires\n"
            + "message = There is no treasure in the current location.\n"
            + "refresh", appendable.toString());

  }

  @Test
  public void testLegalPlayerMovement() {
    dungeonGuiController.movePlayer(MovementDirection.NORTH);
    Assert.assertEquals("\nrefresh", appendable.toString());
  }

  @Test
  public void testPlayerPunchNoBerbalangInDungeon() {
    RandomGenerator randomGenerator = new RandomGenerator();
    randomGenerator.setRandomParameters(List.of(26, 11, 1, 21, 32, 17, 18, 5, 15, 10, 6, 14, 11, 2,
            17, 19, 3, 2, 0, 15, 4, 8, 7, 10, 8, 8, 10, 1, 9, 2, 4, 1, 2, 0, 3, 0, 1, 0, 4, 1, 0,
            10, 7, 7, 2, 3, 3, 6, 2, 5));

    model = new DungeonMaze(4, 6, 3,
            false, 0, 1, 0, 0,
            false, randomGenerator);
    dungeonView = new MockView(appendable);
    dungeonGuiController = new DungeonSwingController(model, dungeonView);
    dungeonGuiController.handlePlayerPunch();
    Assert.assertEquals("\ntitle = Punch"
            + "\nmessage = This Dungeon does not have a Barbalang\n"
            + "refresh", appendable.toString());
  }

  @Test
  public void testBerbalangNoEscape() {
    dungeonGuiController.movePlayer(MovementDirection.NORTH);
    dungeonGuiController.movePlayer(MovementDirection.NORTH);
    dungeonGuiController.movePlayer(MovementDirection.NORTH);
    Assert.assertEquals("\n"
            + "refresh\n"
            + "refresh\n"
            + "title = Dungeon\n"
            + "message = Cannot move the player away from this!\n"
            + "refresh", appendable.toString());
  }

  @Test
  public void testValidShoot() {
    dungeonGuiController.handleShootArrow(1, MovementDirection.NORTH);
    Assert.assertEquals("\ntitle = Arrow Shot!\nmessage = You hear nothing!\n" + "refresh",
            appendable.toString());
  }

  @Test
  public void testInvalidShoot() {
    dungeonGuiController.handleShootArrow(1, MovementDirection.SOUTH);
    Assert.assertEquals("\n" + "title = Arrow!\nmessage ="
                    + " There is no opening towards SOUTH, an arrow is wasted."
                    + "\n" + "refresh",
            appendable.toString());
  }

  @Test(expected = IllegalArgumentException.class)
  public void createInvalidView() {
    dungeonView = new DungeonSwingView(null);
  }

  @Test
  public void createValidView() {
    dungeonView = new MockView(appendable);
    dungeonView.showMessage("title", "sample message");
    Assert.assertEquals("\n"
            + "title = title\nmessage = sample message", appendable.toString());
  }

  @Test(expected = IllegalArgumentException.class)
  public void createInvalidController() {
    new DungeonSwingController(null, null);
  }

  @Test
  public void createValidController() {
    DungeonGuiController controller = new DungeonSwingController(model, dungeonView);
    controller.movePlayer(MovementDirection.NORTH);
    Assert.assertEquals("\nrefresh", appendable.toString());
  }

  @Test
  public void testMakeVisible() {
    dungeonView.makeVisible();
    Assert.assertEquals("\n"
            + "make visible called", appendable.toString());
  }

  @Test
  public void testHandleKeyboardListener() {
    dungeonView.setupKeyboardListener(dungeonGuiController);
    Assert.assertEquals("\n"
            + "keyboard listener setup called", appendable.toString());
  }

  @Test
  public void testJumpPlayer() {
    dungeonGuiController.jumpPlayer(MovementDirection.NORTH);
    Assert.assertEquals("\n"
            + "refresh", appendable.toString());
  }

  @Test
  public void testPlayGame() {
    dungeonGuiController.playGame(model);
    Assert.assertEquals("\n"
            + "game menu setup called\n"
            + "maze board setup called\n"
            + "action panel setup called\n"
            + "make visible called\n"
            + "keyboard listener setup called", appendable.toString());
  }


}
