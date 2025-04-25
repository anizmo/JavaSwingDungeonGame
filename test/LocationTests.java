import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Set;

import dungeon.location.Collectible;
import dungeon.location.Location;
import dungeon.location.LocationType;
import dungeon.location.MazeLocation;
import dungeon.location.MovementDirection;
import dungeon.location.paths.LocationAddress;
import dungeon.location.paths.Path;
import dungeon.location.weapon.Arrow;
import dungeon.location.weapon.CrookedArrow;
import dungeon.obstacles.Monster;
import dungeon.obstacles.Otyugh;

import static org.junit.Assert.assertEquals;

/**
 * The Location Tests class tests all the public methods and working of locations and paths, here
 * we perform testing of valid and invalid variations of inputs and test if the behaviour of the
 * location class and the path class is as per the problem statement and that all the rules are
 * followed effectively.
 */
public class LocationTests {

  private MazeLocation location;

  @Before
  public void setup() {
    location = new MazeLocation(0, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidLocationCreation() {
    new LocationAddress(-1, -1);
  }

  @Test
  public void testAddingTreasureToCave() {
    location.addOpenings(MovementDirection.NORTH);
    location.addTreasure(Set.of(Collectible.DIAMONDS, Collectible.RUBIES, Collectible.SAPPHIRES));
    assertEquals(LocationType.CAVE, location.getType());
    assertEquals(3, location.getTreasures().size());
  }

  @Test(expected = IllegalStateException.class)
  public void testAddingTreasureToTunnel() {
    location.addOpenings(MovementDirection.NORTH);
    location.addOpenings(MovementDirection.SOUTH);
    location.addTreasure(Set.of(Collectible.DIAMONDS, Collectible.RUBIES, Collectible.SAPPHIRES));
  }

  @Test
  public void testTreasurePickup() {
    location.addOpenings(MovementDirection.NORTH);
    location.addTreasure(Set.of(Collectible.DIAMONDS, Collectible.RUBIES, Collectible.SAPPHIRES));
    Assert.assertEquals(3, location.getTreasures().size());
    location.markTreasureCollected(Collectible.DIAMONDS);
    Assert.assertEquals(2, location.getTreasures().size());
    location.markTreasureCollected(Collectible.RUBIES);
    Assert.assertEquals(1, location.getTreasures().size());
    location.markTreasureCollected(Collectible.SAPPHIRES);
    Assert.assertEquals(0, location.getTreasures().size());
  }

  @Test(expected = IllegalStateException.class)
  public void testLocationWithNoOpening() {
    Location location = new MazeLocation(0, 0);
    location.getType();
  }

  @Test
  public void testLocationDescription() {
    location.addOpenings(MovementDirection.NORTH);
    location.addOpenings(MovementDirection.SOUTH);
    assertEquals("Possible moves are [NORTH, SOUTH]\n"
            + "The location is a Tunnel and there is no treasure in it.", location.toString());
    location.addOpenings(MovementDirection.WEST);
    assertEquals("Possible moves are [NORTH, SOUTH, WEST]\n"
            + "The location is a Cave with no treasure in it.", location.toString());
    location.addTreasure(Set.of(Collectible.DIAMONDS));
    assertEquals("Possible moves are [NORTH, SOUTH, WEST]\n"
            + "The location is a Cave with the following treasure in it: \n"
            + "[DIAMONDS]", location.toString());
  }

  @Test
  public void pathEqualsTest() {
    Path pathOne = new Path(new LocationAddress(0, 1),
            new LocationAddress(0, 0));

    Path pathTwo = new Path(new LocationAddress(0, 0),
            new LocationAddress(0, 1));

    Assert.assertEquals(pathOne, pathTwo);
  }

  @Test
  public void pathInequalityTest() {
    Path pathOne = new Path(new LocationAddress(0, 1),
            new LocationAddress(0, 0));

    Path pathTwo = new Path(new LocationAddress(0, 2),
            new LocationAddress(0, 1));

    Assert.assertNotEquals(pathOne, pathTwo);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testPathBetweenSameLocations() {
    new Path(new LocationAddress(0, 0),
            new LocationAddress(0, 0));
  }

  @Test (expected = IllegalStateException.class)
  public void addMonsterToCaveWithExistingMonster() {
    location.addOpenings(MovementDirection.NORTH);
    Otyugh otyugh = new Otyugh(100, new LocationAddress(0,0));
    location.addMonster(otyugh);
    Assert.assertEquals(otyugh, location.getOtyugh());
    location.addMonster(new Otyugh(100, new LocationAddress(0,0)));
  }

  @Test
  public void addMonsterToCave() {
    location.addOpenings(MovementDirection.NORTH);
    Otyugh otyugh = new Otyugh(100, new LocationAddress(0,0));
    location.addMonster(otyugh);
    Assert.assertEquals(otyugh, location.getOtyugh());
  }

  @Test(expected = IllegalStateException.class)
  public void addMonsterToTunnel() {
    location.addOpenings(MovementDirection.NORTH);
    location.addOpenings(MovementDirection.SOUTH);
    location.addMonster(new Otyugh(100, new LocationAddress(0,0)));
  }

  @Test
  public void createValidMonster() {
    Monster monster = new Otyugh(100, new LocationAddress(0,0));
    Assert.assertEquals(100, monster.getHealth());
    Assert.assertTrue(monster.isAlive());
  }

  @Test (expected = IllegalArgumentException.class)
  public void createInvalidMonster() {
    new Otyugh(-10, new LocationAddress(0,0));
  }

  @Test
  public void createValidArrow() {
    Location[][] locations = new Location[2][2];
    LocationAddress firingLocation = new LocationAddress(1, 1);
    Arrow arrow = new CrookedArrow(firingLocation, false, locations);
    Assert.assertEquals(firingLocation, arrow.getCurrentLocation());
  }

  @Test (expected = IllegalArgumentException.class)
  public void createInvalidArrow() {
    new CrookedArrow(null, false, null);
  }

  @Test (expected = IllegalStateException.class)
  public void collectArrowFromALocationWhichDoesNotHaveArrow() {
    Assert.assertFalse(location.isHasArrow());
    location.markArrowCollected();
  }

  @Test
  public void collectArrowFromALocationWhichHasArrow() {
    location.addArrow();
    Assert.assertTrue(location.isHasArrow());
    location.markArrowCollected();
    Assert.assertFalse(location.isHasArrow());
  }

  @Test (expected = IllegalArgumentException.class)
  public void shootArrowWithoutABow() {
    Location[][] locations = new Location[2][2];
    LocationAddress firingLocation = new LocationAddress(1, 1);
    Arrow arrow = new CrookedArrow(firingLocation, false, locations);
    arrow.shoot(1, MovementDirection.NORTH, null);
  }

  @Test
  public void testArrowEquality() {
    Location[][] locations = new Location[2][2];
    LocationAddress firingLocation = new LocationAddress(1, 1);
    Arrow arrow = new CrookedArrow(firingLocation, false, locations);
    Arrow arrowTwo = new CrookedArrow(firingLocation, false, locations);
    Assert.assertEquals(arrow, arrowTwo);
  }

}