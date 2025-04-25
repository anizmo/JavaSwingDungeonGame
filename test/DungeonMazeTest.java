import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import dungeon.RandomGenerator;
import dungeon.location.Collectible;
import dungeon.location.Location;
import dungeon.location.LocationType;
import dungeon.location.MovementDirection;
import dungeon.location.paths.LocationAddress;
import dungeon.location.weapon.PunchingGloves;
import dungeon.location.weapon.ShootResult;
import dungeon.maze.DungeonMaze;
import dungeon.maze.GameState;
import dungeon.maze.Maze;
import dungeon.obstacles.Berbalang;
import dungeon.obstacles.Monster;
import dungeon.obstacles.ObstacleType;
import dungeon.obstacles.Pit;
import dungeon.obstacles.Thief;
import dungeon.player.MazePlayer;
import dungeon.player.Player;

import static dungeon.location.MovementDirection.EAST;
import static dungeon.location.MovementDirection.NORTH;
import static dungeon.location.MovementDirection.SOUTH;
import static dungeon.location.MovementDirection.WEST;

/**
 * The Dungeon Maze Test class tests the methods of a dungeon randomly as well as pseudo randomly by
 * taking random values as input for the dungeon creation within the acceptable ranges such that
 * there is no IllegalArgument exception with the use of random values.
 */
public class DungeonMazeTest {

  private Maze dungeonMaze;

  private RandomGenerator randomGenerator;

  @Before
  public void setup() {
    randomGenerator = new RandomGenerator();

    randomGenerator.setRandomParameters(List.of(13, 32, 17, 33, 13, 3, 15, 0, 0, 17, 15, 6, 12, 1,
            15, 17, 10, 2, 19, 4, 9, 1, 2, 2, 9, 4, 2, 3, 7, 6, 3, 1, 2, 4, 2, 1, 1, 0, 6, 0));

    dungeonMaze = new DungeonMaze(4, 6, 0,
            false, 0, 1, 0, 0,
            false, randomGenerator);

  }

  @Test
  public void testValidDungeonCreation() {
    DungeonMaze dungeonMaze = new DungeonMaze(4, 6,
            0, false, 20, 1,
            1, 1, false);
    Assert.assertEquals(4, dungeonMaze.getNumberOfRows());
    Assert.assertEquals(6, dungeonMaze.getNumberOfColumns());
  }

  @Test
  public void testValidWrappingDungeonCreation() {
    DungeonMaze dungeonMaze = new DungeonMaze(4, 6,
            0, true, 20, 1,
            1, 1, false);
    Assert.assertEquals(4, dungeonMaze.getNumberOfRows());
    Assert.assertEquals(6, dungeonMaze.getNumberOfColumns());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testZeroRowsColumnsDungeonCreation() {
    new DungeonMaze(0, 0,
            0, false, 20, 1,
            1, 1, false);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNegativeRowsColumnsDungeonCreation() {
    new DungeonMaze(-1, -10,
            0, false, 20, 1,
            1, 1, false);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSmallDungeonCreation() {
    new DungeonMaze(2, 4,
            0, false, 20, 1,
            1, 1, false);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testTreasurePercentageAbove100() {
    new DungeonMaze(4, 6,
            0, false, 120, 1,
            1, 1, false);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNegativeTreasurePercentage() {
    new DungeonMaze(4, 6,
            0, false, -100, 1,
            1, 1, false);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidDegreeOfInterconnectivity() {
    new DungeonMaze(4, 6,
            100, false, 100, 1,
            1, 1, false);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNegativeDegreeOfInterconnectivity() {
    new DungeonMaze(2, 4,
            -10, false, 100, 1,
            1, 1, false);
  }

  @Test
  public void testTreasurePercentage() {
    DungeonMaze dungeonMaze = new DungeonMaze(4, 6,
            0, false, 40, 1,
            1, 1, false);

    List<Location> caves = new ArrayList<>();
    List<Location> cavesWithTreasure = new ArrayList<>();

    for (Location[] locations : dungeonMaze.getMazeMap()) {
      for (Location location : locations) {
        if (location.getType() == LocationType.CAVE) {
          caves.add(location);
          if (!location.getTreasures().isEmpty()) {
            cavesWithTreasure.add(location);
          }
        }
      }
    }

    double treasurePercentage =
            Math.ceil((float) (cavesWithTreasure.size() * 100) / (float) (caves.size()));

    Assert.assertEquals(40, treasurePercentage, 5);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testPlayerIllegalMovement() {
    dungeonMaze.movePlayer(SOUTH);
  }

  @Test
  public void testConnectedPathsForDegreeOfInterconnectivityZero() {
    RandomGenerator randomGenerator = new RandomGenerator();
    int numberOfRows = randomGenerator.getRandomNumberBetween(4, 10);
    int numberOfColumns = randomGenerator.getRandomNumberBetween(4, 10);
    DungeonMaze dungeonMaze = new DungeonMaze(numberOfRows, numberOfColumns,
            0, false, 0, 1,
            1, 1, false);
    Assert.assertEquals(((long) numberOfColumns * numberOfRows) - 1,
            dungeonMaze.getConnectedPaths().size());
  }

  @Test
  public void testConnectedPathsForDegreeOfInterconnectivityNonZero() {
    RandomGenerator randomGenerator = new RandomGenerator();
    int numberOfRows = randomGenerator.getRandomNumberBetween(4, 8);
    int numberOfColumns = randomGenerator.getRandomNumberBetween(4, 8);
    int degreeOfInterconnectivity = randomGenerator.getRandomNumberBetween(1, 8);
    DungeonMaze dungeonMaze = new DungeonMaze(numberOfRows, numberOfColumns,
            degreeOfInterconnectivity, false, 0, 1,
            1, 1, false);
    Assert.assertEquals(((long) numberOfColumns * numberOfRows) - 1 + degreeOfInterconnectivity,
            dungeonMaze.getConnectedPaths().size());
  }

  @Test
  public void testEachPathIsConnected() {
    RandomGenerator randomGenerator = new RandomGenerator();
    int numberOfRows = randomGenerator.getRandomNumberBetween(4, 8);
    int numberOfColumns = randomGenerator.getRandomNumberBetween(4, 8);
    int degreeOfInterconnectivity = 0;
    DungeonMaze dungeonMaze = new DungeonMaze(numberOfRows, numberOfColumns,
            degreeOfInterconnectivity, false, 0, 1,
            1, 1, false);

    for (Location[] locations : dungeonMaze.getMazeMap()) {
      for (Location location : locations) {
        Assert.assertNotEquals(0, location.getOpenings().size());
      }
    }
  }

  @Test
  public void testEachLocationIsConnectedToAtMostFourLocations() {
    RandomGenerator randomGenerator = new RandomGenerator();
    int numberOfRows = randomGenerator.getRandomNumberBetween(4, 10);
    int numberOfColumns = randomGenerator.getRandomNumberBetween(4, 10);
    int degreeOfInterconnectivity = 0;
    DungeonMaze dungeonMaze = new DungeonMaze(numberOfRows, numberOfColumns,
            degreeOfInterconnectivity, false, 0, 1,
            1, 1, false);

    for (Location[] locations : dungeonMaze.getMazeMap()) {
      for (Location location : locations) {
        Assert.assertFalse(location.getOpenings().size() > 4);
      }
    }
  }

  @Test
  public void checkStartAndEndIsCave() {
    DungeonMaze dungeonMaze = new DungeonMaze(4, 6,
            0, false, 0, 1,
            1, 1, false);
    Location[][] map = dungeonMaze.getMazeMap();
    Location startLoc = map[dungeonMaze.getStartRow()][dungeonMaze.getStartColumn()];
    Location endLoc = map[dungeonMaze.getEndRow()][dungeonMaze.getEndColumn()];

    Assert.assertEquals(LocationType.CAVE, startLoc.getType());
    Assert.assertEquals(LocationType.CAVE, endLoc.getType());
  }

  /**
   * True random testing of start and end point being atleast 5 locations away from each other. Here
   * we are generating new maze each time with different number of rows and columns.
   */
  @Test
  public void testRouteLengthBetweenStartAndFinish() {
    randomGenerator = new RandomGenerator();
    int numberOfRows = randomGenerator.getRandomNumberBetween(4, 8);
    int numberOfColumns = randomGenerator.getRandomNumberBetween(4, 8);
    int degreeOfInterconnectivity = 0;
    DungeonMaze dungeonMaze = new DungeonMaze(numberOfRows, numberOfColumns,
            degreeOfInterconnectivity, true, 0, 1,
            1, 1, false);

    Player checkPlayer = new MazePlayer(new LocationAddress(dungeonMaze.getStartRow(),
            dungeonMaze.getStartColumn()), dungeonMaze.getNumberOfRows(),
            dungeonMaze.getNumberOfColumns(), dungeonMaze.isWrapping(), 3,
            new PunchingGloves(new RandomGenerator()));


    RandomGenerator randomGenerator = new RandomGenerator();

    Stack<LocationAddress> visitedNodes = new Stack<>();

    LocationAddress startLocation = new LocationAddress(dungeonMaze.getStartRow(),
            dungeonMaze.getStartColumn());
    LocationAddress endLocation = new LocationAddress(dungeonMaze.getEndRow(),
            dungeonMaze.getEndColumn());
    visitedNodes.push(startLocation);

    while (!checkPlayer.getCurrentLocation().equals(endLocation)) {
      Location location = dungeonMaze.getMazeMap()
              [checkPlayer.getCurrentLocation().getRowNumber()]
              [checkPlayer.getCurrentLocation().getColumnNumber()];
      List<MovementDirection> possibleMovements = new ArrayList<>(location.getOpenings());
      MovementDirection moveTo = randomGenerator.pickNRandom(possibleMovements, 1).get(0);
      checkPlayer.move(moveTo);
      if (!visitedNodes.contains(checkPlayer.getCurrentLocation())) {
        visitedNodes.push(checkPlayer.getCurrentLocation());
      } else if (startLocation.equals(checkPlayer.getCurrentLocation())) {
        visitedNodes.clear();
        visitedNodes.push(startLocation);
      } else {
        while (!visitedNodes.peek().equals(checkPlayer.getCurrentLocation())) {
          visitedNodes.pop();
        }
      }
    }

    Assert.assertTrue(visitedNodes.size() >= 5);
  }

  @Test(expected = IllegalStateException.class)
  public void testCollectingTreasureWhenItDoesNotExist() {
    dungeonMaze.pickup(Collectible.DIAMONDS);
  }

  @Test
  public void testMonsterAtLocation() {
    dungeonMaze.movePlayer(WEST);
    dungeonMaze.movePlayer(NORTH);
    dungeonMaze.movePlayer(EAST);
    dungeonMaze.movePlayer(NORTH);
    dungeonMaze.movePlayer(EAST);
    dungeonMaze.movePlayer(NORTH);
    dungeonMaze.movePlayer(EAST);
    dungeonMaze.movePlayer(EAST);
    dungeonMaze.movePlayer(SOUTH);
    dungeonMaze.movePlayer(EAST);

    Location monsterLocation = dungeonMaze.getMazeMap()
            [dungeonMaze.getPlayerCurrentLocation().getAddress().getRowNumber() - 1]
            [dungeonMaze.getPlayerCurrentLocation().getAddress().getColumnNumber()];

    Assert.assertNotNull(monsterLocation.getOtyugh());
    Assert.assertTrue(monsterLocation.getOtyugh().isAlive());
  }

  @Test
  public void testMonsterSmellBad() {
    dungeonMaze.movePlayer(WEST);
    dungeonMaze.movePlayer(NORTH);
    dungeonMaze.movePlayer(EAST);
    dungeonMaze.movePlayer(NORTH);
    dungeonMaze.movePlayer(EAST);
    dungeonMaze.movePlayer(NORTH);
    dungeonMaze.movePlayer(EAST);
    dungeonMaze.movePlayer(EAST);
    dungeonMaze.movePlayer(SOUTH);

    Assert.assertEquals(1, dungeonMaze.detectSmell());
  }

  @Test
  public void testMonsterSmellTerrible() {
    dungeonMaze.movePlayer(WEST);
    dungeonMaze.movePlayer(NORTH);
    dungeonMaze.movePlayer(EAST);
    dungeonMaze.movePlayer(NORTH);
    dungeonMaze.movePlayer(EAST);
    dungeonMaze.movePlayer(NORTH);
    dungeonMaze.movePlayer(EAST);
    dungeonMaze.movePlayer(EAST);
    dungeonMaze.movePlayer(SOUTH);
    dungeonMaze.movePlayer(EAST);

    Assert.assertEquals(2, dungeonMaze.detectSmell());
  }

  @Test
  public void testNoMonsterAtStart() {
    RandomGenerator randomGenerator = new RandomGenerator();
    int numberOfRows = randomGenerator.getRandomNumberBetween(4, 8);
    int numberOfColumns = randomGenerator.getRandomNumberBetween(4, 8);
    int degreeOfInterconnectivity = 0;
    DungeonMaze testDungeon = new DungeonMaze(numberOfRows, numberOfColumns,
            degreeOfInterconnectivity, true, 0, 1, 1, 1, false);

    Assert.assertNull(testDungeon.getPlayerCurrentLocation().getOtyugh());
  }

  @Test
  public void testMonsterAtEnd() {
    RandomGenerator randomGenerator = new RandomGenerator();
    int numberOfRows = randomGenerator.getRandomNumberBetween(4, 8);
    int numberOfColumns = randomGenerator.getRandomNumberBetween(4, 8);
    int degreeOfInterconnectivity = 0;
    DungeonMaze testDungeon = new DungeonMaze(numberOfRows, numberOfColumns,
            degreeOfInterconnectivity, true, 0, 1, 1, 1, false);

    Assert.assertNotNull(testDungeon.getMazeMap()[testDungeon.getEndRow()]
            [testDungeon.getEndColumn()].getOtyugh());

    Assert.assertTrue(testDungeon.getMazeMap()[testDungeon.getEndRow()]
            [testDungeon.getEndColumn()].getOtyugh().isAlive());
  }

  @Test
  public void shootMonsterAtExactDistance() {
    dungeonMaze.movePlayer(WEST);
    dungeonMaze.movePlayer(NORTH);
    dungeonMaze.movePlayer(EAST);
    dungeonMaze.movePlayer(NORTH);
    dungeonMaze.movePlayer(EAST);
    dungeonMaze.movePlayer(NORTH);
    dungeonMaze.movePlayer(EAST);
    dungeonMaze.movePlayer(EAST);
    dungeonMaze.movePlayer(SOUTH);
    dungeonMaze.movePlayer(EAST);

    Location monsterLocation = dungeonMaze.getMazeMap()
            [dungeonMaze.getPlayerCurrentLocation().getAddress().getRowNumber() - 1]
            [dungeonMaze.getPlayerCurrentLocation().getAddress().getColumnNumber()];

    Assert.assertTrue(monsterLocation.getOtyugh().isAlive());
    Assert.assertEquals(100, monsterLocation.getOtyugh().getHealth());
    Assert.assertEquals(2, dungeonMaze.detectSmell());

    Assert.assertTrue(dungeonMaze.shootArrow(1, NORTH).isShotHit());
  }

  @Test
  public void testMonsterHealthChange() {
    dungeonMaze.movePlayer(WEST);
    dungeonMaze.movePlayer(NORTH);
    dungeonMaze.movePlayer(EAST);
    dungeonMaze.movePlayer(NORTH);
    dungeonMaze.movePlayer(EAST);
    dungeonMaze.movePlayer(NORTH);
    dungeonMaze.movePlayer(EAST);
    dungeonMaze.movePlayer(EAST);
    dungeonMaze.movePlayer(SOUTH);
    dungeonMaze.movePlayer(EAST);

    Location monsterLocation = dungeonMaze.getMazeMap()
            [dungeonMaze.getPlayerCurrentLocation().getAddress().getRowNumber() - 1]
            [dungeonMaze.getPlayerCurrentLocation().getAddress().getColumnNumber()];

    Assert.assertTrue(monsterLocation.getOtyugh().isAlive());
    Assert.assertEquals(100, monsterLocation.getOtyugh().getHealth());
    Assert.assertEquals(2, dungeonMaze.detectSmell());

    Assert.assertTrue(dungeonMaze.shootArrow(1, NORTH).isShotHit());
    Assert.assertEquals(50, monsterLocation.getOtyugh().getHealth());
  }

  @Test
  public void testMonsterIsDead() {
    dungeonMaze.movePlayer(WEST);
    dungeonMaze.movePlayer(NORTH);
    dungeonMaze.movePlayer(EAST);
    dungeonMaze.movePlayer(NORTH);
    dungeonMaze.movePlayer(EAST);
    dungeonMaze.movePlayer(NORTH);
    dungeonMaze.movePlayer(EAST);
    dungeonMaze.movePlayer(EAST);
    dungeonMaze.movePlayer(SOUTH);
    dungeonMaze.movePlayer(EAST);

    Location monsterLocation = dungeonMaze.getMazeMap()
            [dungeonMaze.getPlayerCurrentLocation().getAddress().getRowNumber() - 1]
            [dungeonMaze.getPlayerCurrentLocation().getAddress().getColumnNumber()];

    Assert.assertTrue(monsterLocation.getOtyugh().isAlive());
    Assert.assertEquals(100, monsterLocation.getOtyugh().getHealth());
    Assert.assertEquals(2, dungeonMaze.detectSmell());

    Assert.assertTrue(dungeonMaze.shootArrow(1, NORTH).isShotHit());
    Assert.assertEquals(50, monsterLocation.getOtyugh().getHealth());
    Assert.assertTrue(dungeonMaze.shootArrow(1, NORTH).isShotHit());
    Assert.assertEquals(0, monsterLocation.getOtyugh().getHealth());

    //Monster is dead after taking 2 hits of arrow
    Assert.assertFalse(monsterLocation.getOtyugh().isAlive());
  }

  @Test
  public void testSmellIsGoneAfterMonsterIsDead() {
    dungeonMaze.movePlayer(WEST);
    dungeonMaze.movePlayer(NORTH);
    dungeonMaze.movePlayer(EAST);
    dungeonMaze.movePlayer(NORTH);
    dungeonMaze.movePlayer(EAST);
    dungeonMaze.movePlayer(NORTH);
    dungeonMaze.movePlayer(EAST);
    dungeonMaze.movePlayer(EAST);
    dungeonMaze.movePlayer(SOUTH);
    dungeonMaze.movePlayer(EAST);

    Location monsterLocation = dungeonMaze.getMazeMap()
            [dungeonMaze.getPlayerCurrentLocation().getAddress().getRowNumber() - 1]
            [dungeonMaze.getPlayerCurrentLocation().getAddress().getColumnNumber()];

    Assert.assertTrue(monsterLocation.getOtyugh().isAlive());
    Assert.assertEquals(100, monsterLocation.getOtyugh().getHealth());
    Assert.assertEquals(2, dungeonMaze.detectSmell());

    Assert.assertTrue(dungeonMaze.shootArrow(1, NORTH).isShotHit());
    Assert.assertEquals(50, monsterLocation.getOtyugh().getHealth());
    Assert.assertTrue(dungeonMaze.shootArrow(1, NORTH).isShotHit());
    Assert.assertEquals(0, monsterLocation.getOtyugh().getHealth());

    //Monster is dead after taking 2 hits of arrow
    Assert.assertFalse(monsterLocation.getOtyugh().isAlive());
    //The location at which the player was getting terrible smell is gone after monster is dead.
    Assert.assertEquals(0, dungeonMaze.detectSmell());
  }

  @Test
  public void testPlayerEscapeMonster() {
    dungeonMaze.movePlayer(WEST);
    dungeonMaze.movePlayer(NORTH);
    dungeonMaze.movePlayer(EAST);
    dungeonMaze.movePlayer(NORTH);
    dungeonMaze.movePlayer(EAST);
    dungeonMaze.movePlayer(NORTH);
    dungeonMaze.movePlayer(EAST);
    dungeonMaze.movePlayer(EAST);
    dungeonMaze.movePlayer(SOUTH);
    dungeonMaze.movePlayer(EAST);

    Location monsterLocation = dungeonMaze.getMazeMap()
            [dungeonMaze.getPlayerCurrentLocation().getAddress().getRowNumber() - 1]
            [dungeonMaze.getPlayerCurrentLocation().getAddress().getColumnNumber()];

    Assert.assertTrue(monsterLocation.getOtyugh().isAlive());
    Assert.assertEquals(100, monsterLocation.getOtyugh().getHealth());
    Assert.assertEquals(2, dungeonMaze.detectSmell());

    Assert.assertTrue(dungeonMaze.shootArrow(1, NORTH).isShotHit());
    Assert.assertEquals(50, monsterLocation.getOtyugh().getHealth());

    //Mocking the player's 50% chance of escape to true
    randomGenerator.setRandomParameters(List.of(1));
    dungeonMaze.movePlayer(NORTH);

    //Here, the player did not die and the game state did not change to LOSE
    Assert.assertEquals(GameState.PLAYING, dungeonMaze.getState());
  }

  @Test
  public void testPlayerEscapeMonsterFailed() {
    dungeonMaze.movePlayer(WEST);
    dungeonMaze.movePlayer(NORTH);
    dungeonMaze.movePlayer(EAST);
    dungeonMaze.movePlayer(NORTH);
    dungeonMaze.movePlayer(EAST);
    dungeonMaze.movePlayer(NORTH);
    dungeonMaze.movePlayer(EAST);
    dungeonMaze.movePlayer(EAST);
    dungeonMaze.movePlayer(SOUTH);
    dungeonMaze.movePlayer(EAST);

    Location monsterLocation = dungeonMaze.getMazeMap()
            [dungeonMaze.getPlayerCurrentLocation().getAddress().getRowNumber() - 1]
            [dungeonMaze.getPlayerCurrentLocation().getAddress().getColumnNumber()];

    Assert.assertTrue(monsterLocation.getOtyugh().isAlive());
    Assert.assertEquals(100, monsterLocation.getOtyugh().getHealth());
    Assert.assertEquals(2, dungeonMaze.detectSmell());

    Assert.assertTrue(dungeonMaze.shootArrow(1, NORTH).isShotHit());
    Assert.assertEquals(50, monsterLocation.getOtyugh().getHealth());

    //Mocking the player's 50% chance of escape to false
    randomGenerator.setRandomParameters(List.of(0));
    dungeonMaze.movePlayer(NORTH);

    //Here, the player did die and the game state changed to LOSE
    Assert.assertEquals(GameState.LOSE, dungeonMaze.getState());
  }

  @Test
  public void shotMonsterAtMoreDistance() {
    dungeonMaze.movePlayer(WEST);
    dungeonMaze.movePlayer(NORTH);
    dungeonMaze.movePlayer(EAST);
    dungeonMaze.movePlayer(NORTH);
    dungeonMaze.movePlayer(EAST);
    dungeonMaze.movePlayer(NORTH);
    dungeonMaze.movePlayer(EAST);
    dungeonMaze.movePlayer(EAST);
    dungeonMaze.movePlayer(SOUTH);
    dungeonMaze.movePlayer(EAST);

    Location monsterLocation = dungeonMaze.getMazeMap()
            [dungeonMaze.getPlayerCurrentLocation().getAddress().getRowNumber() - 1]
            [dungeonMaze.getPlayerCurrentLocation().getAddress().getColumnNumber()];

    Assert.assertTrue(monsterLocation.getOtyugh().isAlive());
    Assert.assertEquals(100, monsterLocation.getOtyugh().getHealth());
    Assert.assertEquals(2, dungeonMaze.detectSmell());

    //The monster is at a distance of 1 towards West, but we fire it at 100 distance.
    Assert.assertFalse(dungeonMaze.shootArrow(5, NORTH).isShotHit());
  }

  @Test
  public void testDungeonDumping() {
    StringBuilder out = new StringBuilder();
    Location[][] mazeMap = dungeonMaze.getMazeMap();

    for (Location[] locations : mazeMap) {
      for (Location location : locations) {
        out.append(location.getDirectionalSymbol());
      }
      out.append("\n");
    }

    Assert.assertEquals(
             " ╦==╦  ╦=====╦  ╦ \n"
                     + " ╩  ╬==╩  ╦==╬==╩ \n"
                     + " ╦==╩==╦  ╬  ╩=== \n"
                     + " ╩===  ╩  ╩====== \n",
            out.toString());
  }

  @Test
  public void testArrowTravellingThroughTunnel() {
    dungeonMaze.movePlayer(WEST);
    ShootResult fireOne = dungeonMaze.shootArrow(1, EAST);
    Assert.assertEquals(new LocationAddress(3, 1), fireOne.getArrowLandingAddress());

    ShootResult fireTwo = dungeonMaze.shootArrow(2, NORTH);
    Assert.assertEquals(new LocationAddress(3, 2), fireTwo.getArrowLandingAddress());
  }

  @Test
  public void testArrowTravellingThroughCave() {
    ShootResult fireOne = dungeonMaze.shootArrow(1, WEST);
    Assert.assertEquals(new LocationAddress(2, 1), fireOne.getArrowLandingAddress());

    ShootResult fireTwo = dungeonMaze.shootArrow(2, WEST);
    Assert.assertEquals(new LocationAddress(3, 2), fireTwo.getArrowLandingAddress());

  }

  @Test
  public void testArrowFiringMoreThanAvailableDistance() {
    Assert.assertEquals(new LocationAddress(3, 1),
            dungeonMaze.getPlayerCurrentLocation().getAddress());
    ShootResult fireThree = dungeonMaze.shootArrow(5, WEST);
    Assert.assertEquals(new LocationAddress(3, 2),
            fireThree.getArrowLandingAddress());
  }

  @Test
  public void testArrowPercentageEqualToTreasurePercentage() {
    DungeonMaze dungeonMaze = new DungeonMaze(4, 6,
            0, false, 40, 1,
            1, 1, false);

    int cavesAndTunnels = 0;
    int cavesAndTunnelsWithArrow = 0;
    int caves = 0;
    int cavesWithTreasure = 0;

    for (Location[] locations : dungeonMaze.getMazeMap()) {
      for (Location location : locations) {
        cavesAndTunnels++;
        if (location.isHasArrow()) {
          cavesAndTunnelsWithArrow++;
        }
        if (location.getType() == LocationType.CAVE) {
          caves++;
          if (!location.getTreasures().isEmpty()) {
            cavesWithTreasure++;
          }
        }
      }
    }

    double treasurePercentage =
            Math.ceil((float) (cavesWithTreasure * 100) / (float) (caves));

    double arrowsPercentage =
            Math.ceil((float) (cavesAndTunnelsWithArrow * 100) / (float) (cavesAndTunnels));

    Assert.assertEquals(40, treasurePercentage, 5);
    Assert.assertEquals(40, arrowsPercentage, 5);
    Assert.assertEquals(treasurePercentage, arrowsPercentage, 5);
  }

  /**
   * True random testing of the correlation between the number of Otyughs and the difficulty set
   * in the model.
   */
  @Test
  public void testDifficultyAndNumberOfOtyughs() {
    RandomGenerator randomGenerator = new RandomGenerator();
    int numberOfRows = randomGenerator.getRandomNumberBetween(4, 8);
    int numberOfColumns = randomGenerator.getRandomNumberBetween(4, 8);

    int difficulty = randomGenerator.getRandomNumberBetween(1, 4);

    int degreeOfInterconnectivity = 0;
    DungeonMaze testDungeon = new DungeonMaze(numberOfRows, numberOfColumns,
            degreeOfInterconnectivity, true, 0, difficulty, 0, 0, false);


    int numberOfMonsters = 0;

    for (Location[] locations : testDungeon.getMazeMap()) {
      for (Location location : locations) {
        if (location.getOtyugh() != null) {
          numberOfMonsters++;
        }
      }
    }

    Assert.assertEquals(difficulty, numberOfMonsters);
  }

  @Test
  public void testValidPit() {
    Pit pit = new Pit(new LocationAddress(0, 0));
    Assert.assertEquals(new LocationAddress(0, 0), pit.getLocation());
    Assert.assertEquals(ObstacleType.PIT, pit.getObstacleType());
  }

  @Test (expected = IllegalArgumentException.class)
  public void testInvalidPit() {
    new Pit(null);
  }

  @Test
  public void testValidThief() {
    Thief thief = new Thief(new LocationAddress(0, 0));
    Assert.assertEquals(new LocationAddress(0, 0), thief.getLocation());
    Assert.assertEquals(ObstacleType.THIEF, thief.getObstacleType());
  }

  @Test (expected = IllegalArgumentException.class)
  public void testInvalidThief() {
    new Thief(null);
  }

  @Test
  public void stealTreasure() {
    Player player = new MazePlayer(new LocationAddress(0, 0),
            1 ,1, true, 3, new PunchingGloves(randomGenerator));
    player.collectTreasure(Collectible.DIAMONDS);
    Thief thief = new Thief(new LocationAddress(0, 0));
    Assert.assertEquals("{DIAMONDS=1}", player.getCollectedTreasureMap().toString());
    if (thief.getLocation().equals(player.getCurrentLocation())) {
      player.emptyTreasure();
    }
    Assert.assertEquals("{}", player.getCollectedTreasureMap().toString());
  }

  @Test
  public void detectBreeze() {
    int numberOfRows = 6;
    int numberOfColumns = 6;
    int degreeOfInterconnectivity = 3;
    int caveTreasurePercentage = 20;
    boolean isWrapping = false;
    int difficulty = 1;

    RandomGenerator randomGenerator = new RandomGenerator();
    randomGenerator.setRandomParameters(List.of(59, 44, 0, 36, 24, 44, 33, 0, 46, 45, 49, 47, 36,
            32, 31, 28, 21, 13, 33, 28, 29, 11, 37, 8, 7, 32, 0, 29, 10, 27, 14, 18, 6, 19, 10, 5,
            6, 7, 15, 8, 17, 3, 17, 15, 7, 5, 1, 3, 4, 4, 7, 3, 6, 4, 0, 1, 3, 2, 1, 0, 15, 23, 22,
            8, 12, 6, 12, 7, 2, 0, 2, 1, 2, 2, 2, 2, 0, 1, 2, 27, 34, 6, 3, 1, 30, 2, 16, 12, 7, 17,
            12));
    Maze model = new DungeonMaze(numberOfRows, numberOfColumns, degreeOfInterconnectivity,
            isWrapping, caveTreasurePercentage, difficulty, 1, 1,
            true, randomGenerator);

    model.movePlayer(NORTH);
    model.movePlayer(EAST);
    model.movePlayer(NORTH);

    Assert.assertEquals(1, model.detectWindLevel());
  }

  @Test
  public void moveBerbalang() {
    int numberOfRows = 6;
    int numberOfColumns = 6;
    int degreeOfInterconnectivity = 3;
    int caveTreasurePercentage = 20;
    boolean isWrapping = false;
    int difficulty = 1;

    RandomGenerator randomGenerator = new RandomGenerator();
    randomGenerator.setRandomParameters(List.of(59, 44, 0, 36, 24, 44, 33, 0, 46, 45, 49, 47, 36,
            32, 31, 28, 21, 13, 33, 28, 29, 11, 37, 8, 7, 32, 0, 29, 10, 27, 14, 18, 6, 19, 10, 5,
            6, 7, 15, 8, 17, 3, 17, 15, 7, 5, 1, 3, 4, 4, 7, 3, 6, 4, 0, 1, 3, 2, 1, 0, 15, 23, 22,
            8, 12, 6, 12, 7, 2, 0, 2, 1, 2, 2, 2, 2, 0, 1, 2, 27, 34, 6, 3, 1, 30, 2, 16, 12, 7, 17,
            12));
    Maze model = new DungeonMaze(numberOfRows, numberOfColumns, degreeOfInterconnectivity,
            isWrapping, caveTreasurePercentage, difficulty, 1, 1,
            true, randomGenerator);

    Assert.assertEquals(new LocationAddress(2, 0), model.getBerbalangLocation());

    model.movePlayer(NORTH);

    Assert.assertEquals(new LocationAddress(3, 0), model.getBerbalangLocation());

  }

  @Test
  public void testValidBerbalang() {
    Monster berbalang = new Berbalang(100, new RandomGenerator(),
            new LocationAddress(0, 0), 4, 4,
            false);

    Assert.assertEquals(100, berbalang.getHealth());
  }

  @Test (expected = IllegalArgumentException.class)
  public void testInvalidBerbalang() {
    new Berbalang(0, new RandomGenerator(),
            new LocationAddress(0, 0), 4, 4,
            false);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testBerbalangEncounter() {
    int numberOfRows = 6;
    int numberOfColumns = 6;
    int degreeOfInterconnectivity = 3;
    int caveTreasurePercentage = 20;
    boolean isWrapping = false;
    int difficulty = 1;

    RandomGenerator randomGenerator = new RandomGenerator();
    randomGenerator.setRandomParameters(List.of(59, 44, 0, 36, 24, 44, 33, 0, 46, 45, 49, 47, 36,
            32, 31, 28, 21, 13, 33, 28, 29, 11, 37, 8, 7, 32, 0, 29, 10, 27, 14, 18, 6, 19, 10, 5,
            6, 7, 15, 8, 17, 3, 17, 15, 7, 5, 1, 3, 4, 4, 7, 3, 6, 4, 0, 1, 3, 2, 1, 0, 15, 23, 22,
            8, 12, 6, 12, 7, 2, 0, 2, 1, 2, 2, 2, 2, 0, 1, 2, 27, 34, 6, 3, 1, 30, 2, 16, 12, 7, 17,
            12));
    Maze model = new DungeonMaze(numberOfRows, numberOfColumns, degreeOfInterconnectivity,
            isWrapping, caveTreasurePercentage, difficulty, 1, 1,
            true, randomGenerator);

    model.movePlayer(NORTH);
    model.movePlayer(NORTH);

    model.movePlayer(NORTH);

  }

  @Test
  public void testBerbalangBattleLost() {
    int numberOfRows = 6;
    int numberOfColumns = 6;
    int degreeOfInterconnectivity = 3;
    int caveTreasurePercentage = 20;
    boolean isWrapping = false;
    int difficulty = 1;

    RandomGenerator randomGenerator = new RandomGenerator();
    randomGenerator.setRandomParameters(List.of(59, 44, 0, 36, 24, 44, 33, 0, 46, 45, 49, 47, 36,
            32, 31, 28, 21, 13, 33, 28, 29, 11, 37, 8, 7, 32, 0, 29, 10, 27, 14, 18, 6, 19, 10, 5,
            6, 7, 15, 8, 17, 3, 17, 15, 7, 5, 1, 3, 4, 4, 7, 3, 6, 4, 0, 1, 3, 2, 1, 0, 15, 23, 22,
            8, 12, 6, 12, 7, 2, 0, 2, 1, 2, 2, 2, 2, 0, 1, 2, 27, 34, 6, 3, 1, 30, 2, 16, 12, 7, 17,
            12));
    Maze model = new DungeonMaze(numberOfRows, numberOfColumns, degreeOfInterconnectivity,
            isWrapping, caveTreasurePercentage, difficulty, 1, 1,
            true, randomGenerator);

    model.movePlayer(NORTH);
    model.movePlayer(NORTH);

    randomGenerator.setRandomParameters(List.of(20, 24, 17, 21, 12, 25, 14, 22, 14, 16));

    model.hitBerbalang();
    model.hitBerbalang();
    model.hitBerbalang();
    model.hitBerbalang();
    model.hitBerbalang();

    Assert.assertEquals(0, model.getPlayerHealth());
    Assert.assertEquals(23, model.getBerbalangHealth());
  }

  @Test
  public void testBerbalangBattleWon() {
    int numberOfRows = 6;
    int numberOfColumns = 6;
    int degreeOfInterconnectivity = 3;
    int caveTreasurePercentage = 20;
    boolean isWrapping = false;
    int difficulty = 1;

    RandomGenerator randomGenerator = new RandomGenerator();
    randomGenerator.setRandomParameters(List.of(59, 44, 0, 36, 24, 44, 33, 0, 46, 45, 49, 47, 36,
            32, 31, 28, 21, 13, 33, 28, 29, 11, 37, 8, 7, 32, 0, 29, 10, 27, 14, 18, 6, 19, 10, 5,
            6, 7, 15, 8, 17, 3, 17, 15, 7, 5, 1, 3, 4, 4, 7, 3, 6, 4, 0, 1, 3, 2, 1, 0, 15, 23, 22,
            8, 12, 6, 12, 7, 2, 0, 2, 1, 2, 2, 2, 2, 0, 1, 2, 27, 34, 6, 3, 1, 30, 2, 16, 12, 7, 17,
            12));
    Maze model = new DungeonMaze(numberOfRows, numberOfColumns, degreeOfInterconnectivity,
            isWrapping, caveTreasurePercentage, difficulty, 1, 1,
            true, randomGenerator);

    model.movePlayer(NORTH);
    model.movePlayer(NORTH);

    randomGenerator.setRandomParameters(List.of(25, 24, 25, 21, 25, 10, 25, 22));

    model.hitBerbalang();

    Assert.assertEquals(76, model.getPlayerHealth());
    Assert.assertEquals(75, model.getBerbalangHealth());

    model.hitBerbalang();
    model.hitBerbalang();
    model.hitBerbalang();

    Assert.assertEquals(45, model.getPlayerHealth());
    Assert.assertEquals(0, model.getBerbalangHealth());
  }

  @Test
  public void testPitAndThiefAtStartAndEnd() {
    RandomGenerator randomGenerator = new RandomGenerator();
    int numberOfRows = randomGenerator.getRandomNumberBetween(4, 8);
    int numberOfColumns = randomGenerator.getRandomNumberBetween(4, 8);
    int numberOfPits = randomGenerator.getRandomNumberBetween(1, 4);
    int numberOfThieves = randomGenerator.getRandomNumberBetween(1, 4);
    int degreeOfInterconnectivity = 0;

    DungeonMaze testDungeon = new DungeonMaze(numberOfRows, numberOfColumns,
            degreeOfInterconnectivity, true, 0, 1,
            numberOfThieves, numberOfPits, false);

    Assert.assertNull(testDungeon.getMazeMap()[testDungeon.getEndRow()]
            [testDungeon.getEndColumn()].getPit());

    Assert.assertNull(testDungeon.getMazeMap()[testDungeon.getEndRow()]
            [testDungeon.getEndColumn()].getThief());

    Assert.assertNull(testDungeon.getMazeMap()[testDungeon.getStartRow()]
            [testDungeon.getStartColumn()].getPit());

    Assert.assertNull(testDungeon.getMazeMap()[testDungeon.getStartRow()]
            [testDungeon.getStartColumn()].getThief());

  }

  @Test
  public void testResetGame() {
    Assert.assertEquals(new LocationAddress(3, 1),
            dungeonMaze.getPlayerCurrentLocation().getAddress());
    dungeonMaze.movePlayer(WEST);
    Assert.assertEquals(new LocationAddress(3, 0),
            dungeonMaze.getPlayerCurrentLocation().getAddress());
    dungeonMaze.resetGame();
    Assert.assertEquals(new LocationAddress(3, 1),
            dungeonMaze.getPlayerCurrentLocation().getAddress());
  }

}