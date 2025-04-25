import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import dungeon.RandomGenerator;
import dungeon.location.Collectible;
import dungeon.location.MovementDirection;
import dungeon.location.paths.LocationAddress;
import dungeon.location.weapon.PunchingGloves;
import dungeon.player.MazePlayer;
import dungeon.player.Player;

import static org.junit.Assert.assertEquals;

/**
 * The Player Test class tests the movement of a Player in a wrapping, and a non-wrapping dungeon,
 * it also performs testing of other important methods of player such as collecting treasure from
 * a location, increasing score while collecting a treasure and test the description of the player
 * when moving from one cell location to another.
 */
public class PlayerTest {

  Player player;

  @Before
  public void setup() {
    player = new MazePlayer(new LocationAddress(0, 0),
            4, 5, false, 3, new PunchingGloves(new RandomGenerator()));
  }

  @Test
  public void testValidPlayerCreation() {
    Player player = new MazePlayer(new LocationAddress(0, 0),
            4, 6, false, 3, new PunchingGloves(new RandomGenerator()));
    assertEquals(new LocationAddress(0, 0), player.getCurrentLocation());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidPlayerCreation() {
    new MazePlayer(new LocationAddress(0, 0), -1,
            -1, false, 3,
            new PunchingGloves(new RandomGenerator()));
  }

  @Test
  public void testPlayerCurrentLocation() {
    assertEquals(new LocationAddress(0, 0), player.getCurrentLocation());
  }

  @Test
  public void testMovementEast() {
    player.move(MovementDirection.EAST);
    assertEquals(new LocationAddress(0, 1), player.getCurrentLocation());
  }

  @Test
  public void testMovementSouth() {
    player.move(MovementDirection.SOUTH);
    assertEquals(new LocationAddress(1, 0), player.getCurrentLocation());
  }

  @Test
  public void testMovementNorth() {
    player = new MazePlayer(new LocationAddress(2, 2),
            4, 5, false, 3,
            new PunchingGloves(new RandomGenerator()));
    player.move(MovementDirection.NORTH);
    assertEquals(new LocationAddress(1, 2), player.getCurrentLocation());
  }

  @Test
  public void testMovementWest() {
    player = new MazePlayer(new LocationAddress(2, 2),
            4, 5, false, 3, new PunchingGloves(new RandomGenerator()));
    player.move(MovementDirection.WEST);
    assertEquals(new LocationAddress(2, 1), player.getCurrentLocation());
  }

  @Test
  public void testMovementWrap() {
    player = new MazePlayer(new LocationAddress(0, 0),
            4, 5, true, 3, new PunchingGloves(new RandomGenerator()));
    assertEquals(new LocationAddress(0, 0), player.getCurrentLocation());
    player.move(MovementDirection.WEST);
    assertEquals(new LocationAddress(0, 4), player.getCurrentLocation());
    player.move(MovementDirection.EAST);
    assertEquals(new LocationAddress(0, 0), player.getCurrentLocation());
    player.move(MovementDirection.NORTH);
    assertEquals(new LocationAddress(3, 0), player.getCurrentLocation());
    player.move(MovementDirection.SOUTH);
    assertEquals(new LocationAddress(0, 0), player.getCurrentLocation());
  }

  @Test
  public void testTreasurePickupOfPlayer() {
    player.collectTreasure(Collectible.DIAMONDS);
    player.collectTreasure(Collectible.RUBIES);
    player.collectTreasure(Collectible.RUBIES);
    player.collectTreasure(Collectible.SAPPHIRES);
    player.collectTreasure(Collectible.SAPPHIRES);
    player.collectTreasure(Collectible.SAPPHIRES);

    Assert.assertEquals(3, player.getCollectedTreasureMap().size());

    int numberOfDiamonds = player.getCollectedTreasureMap().get(Collectible.DIAMONDS);
    int numberOfRubies = player.getCollectedTreasureMap().get(Collectible.RUBIES);
    int numberOfSapphires = player.getCollectedTreasureMap().get(Collectible.SAPPHIRES);
    Assert.assertEquals(1, numberOfDiamonds);
    Assert.assertEquals(2, numberOfRubies);
    Assert.assertEquals(3, numberOfSapphires);
  }

  @Test
  public void testPlayerDescription() {
    assertEquals("Player (Treasure = {}, Current Location = (0, 0))",
            player.toString());
  }

  @Test
  public void testPlayerArrowCountOnShoot() {
    Assert.assertEquals(3, player.getNumberOfArrowsAvailable());
    player.fireArrow(1);
    player.fireArrow(1);
    player.fireArrow(1);
    Assert.assertEquals(0, player.getNumberOfArrowsAvailable());
  }

  @Test
  public void testPlayerArrowCountOnPickup() {
    Assert.assertEquals(3, player.getNumberOfArrowsAvailable());
    player.addArrow();
    Assert.assertEquals(4, player.getNumberOfArrowsAvailable());
  }

}