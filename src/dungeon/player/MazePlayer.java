package dungeon.player;

import java.util.HashMap;
import java.util.Map;

import dungeon.location.Collectible;
import dungeon.location.MovementDirection;
import dungeon.location.paths.LocationAddress;
import dungeon.location.weapon.Weapon;

/**
 * The MazePlayer is an implementation of the player class that represents all the methods of the
 * Player interface and implements them. Here, the task of navigating through the maze is done by
 * the player for both wrapping and non-wrapping mazes in the dungeon.
 */
public class MazePlayer implements Player {

    private final Map<Collectible, Integer> collectedTreasureMap;
    private final int numberOfRows;
    private final int numberOfColumns;
    private final boolean isWrap;
    private final Weapon weapon;
    private final LocationAddress startLocation;
    private LocationAddress currentLocation;
    private int arrowCount;
    private int health;

    /**
     * Constructs an instance of the MazePlayer class within the dungeon with the specified number of
     * rows and columns. We also specify a start location for the player which represents the starting
     * point of the player in the maze and the isWrapping field specifies if the player can move
     * through the maze assuming it is wrapping or not.
     *
     * @param startLocation   the start location of the player composed of row and column number.
     * @param numberOfRows    the number of rows of the dungeon to which the player belongs.
     * @param numberOfColumns the number of columns of the dungeon to which the player belongs.
     * @param isWrapping      specifies if dungeon to which the player belongs is wrapping or not.
     * @param arrowCount      the number of arrows that the player has when they begin the game.
     * @param weapon          the weapon used by the player to attempt slaying the monsters.
     */
    public MazePlayer(LocationAddress startLocation, int numberOfRows,
                      int numberOfColumns, boolean isWrapping, int arrowCount, Weapon weapon) {
        if (numberOfColumns <= 0 || numberOfRows <= 0) {
            throw new IllegalArgumentException("The player cannot be a part of maze that has zero or "
                    + "negative number of rows or columns");
        }
        this.collectedTreasureMap = new HashMap<>();
        this.startLocation = startLocation;
        this.currentLocation = startLocation;
        this.numberOfColumns = numberOfColumns;
        this.numberOfRows = numberOfRows;
        this.isWrap = isWrapping;
        this.arrowCount = arrowCount;
        this.weapon = weapon;
        this.health = 100;
    }

    @Override
    public void move(MovementDirection movementDirection) {

        switch (movementDirection) {
            case WEST: {
                if (isWrap && currentLocation.getColumnNumber() == 0) {
                    currentLocation = new LocationAddress(currentLocation.getRowNumber(),
                            numberOfColumns - 1);
                } else {
                    currentLocation
                            = new LocationAddress(
                            currentLocation.getRowNumber(),
                            currentLocation.getColumnNumber() - 1);
                }
            }
            break;

            case EAST: {
                if (isWrap && currentLocation.getColumnNumber() == numberOfColumns - 1) {
                    currentLocation = new LocationAddress(currentLocation.getRowNumber(), 0);
                } else {
                    currentLocation
                            = new LocationAddress(
                            currentLocation.getRowNumber(),
                            currentLocation.getColumnNumber() + 1);
                }

            }
            break;

            case SOUTH: {
                if (isWrap && currentLocation.getRowNumber() == numberOfRows - 1) {
                    currentLocation = new LocationAddress(0,
                            currentLocation.getColumnNumber());
                } else {
                    currentLocation
                            = new LocationAddress(currentLocation.getRowNumber() + 1,
                            currentLocation.getColumnNumber());
                }
            }
            break;

            case NORTH: {
                if (isWrap && currentLocation.getRowNumber() == 0) {
                    currentLocation = new LocationAddress(numberOfRows - 1,
                            currentLocation.getColumnNumber());
                } else {
                    currentLocation
                            = new LocationAddress(currentLocation.getRowNumber() - 1,
                            currentLocation.getColumnNumber());
                }
            }
            break;

            default: {
                currentLocation
                        = new LocationAddress(currentLocation.getRowNumber(),
                        currentLocation.getColumnNumber());
            }
            break;
        }

    }

    @Override
    public LocationAddress getCurrentLocation() {
        return new LocationAddress(currentLocation);
    }

    @Override
    public void collectTreasure(Collectible collectible) {
        if (collectedTreasureMap.containsKey(collectible)) {
            int treasureCount = collectedTreasureMap.get(collectible);
            treasureCount++;
            collectedTreasureMap.put(collectible, treasureCount);
        } else {
            collectedTreasureMap.put(collectible, 1);
        }
    }

    @Override
    public Map<Collectible, Integer> getCollectedTreasureMap() {
        return new HashMap<>(collectedTreasureMap);
    }

    @Override
    public int getNumberOfArrowsAvailable() {
        return arrowCount;
    }

    @Override
    public void fireArrow(int distance) {
        if (arrowCount == 0) {
            throw new IllegalStateException("Player has no arrows left to fire!");
        } else if (distance <= 0) {
            throw new IllegalArgumentException(
                    "Distance cannot be " + distance + ", enter a positive integer");
        } else {
            arrowCount--;
        }
    }

    @Override
    public void addArrow() {
        arrowCount++;
    }

    @Override
    public Weapon getWeapon() {
        return weapon;
    }

    @Override
    public void emptyTreasure() {
        collectedTreasureMap.clear();
    }

    @Override
    public void takeDamage(int damage) {
        health = health - damage;
        if (health < 0) {
            health = 0;
        }
    }

    @Override
    public int getHealth() {
        return health;
    }

    @Override
    public void reset() {
        health = 100;
        arrowCount = 3;
        currentLocation = startLocation;
        emptyTreasure();
    }

    @Override
    public String toString() {
        return new StringBuilder().append("Player").append(" (").append("Treasure = ")
                .append(collectedTreasureMap).append(", Current Location = ")
                .append(currentLocation).append(')').toString();
    }
}
