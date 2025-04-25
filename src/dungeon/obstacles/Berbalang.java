package dungeon.obstacles;

import dungeon.RandomGenerator;
import dungeon.location.MovementDirection;
import dungeon.location.paths.LocationAddress;
import dungeon.location.weapon.Weapon;
import dungeon.location.weapon.WeaponType;

/**
 * A Berbalang is a monster that keeps roaming through the dungeon to find the player, it listens
 * to the player's footsteps, and it can see in darkness. It follows the player and tries to kill
 * him/her. The Berbalang has wings, so it can fly over pits and stay in the same cave as the Otyugh
 * by flying out of reach of it.
 */
public class Berbalang implements Monster {

    private final LocationAddress startLocation;
    private final int numberOfRows;
    private final int numberOfColumns;
    private final boolean isWrap;
    private final RandomGenerator randomGenerator;
    private int health;
    private LocationAddress currentLocation;

    /**
     * Constructs a moving monster that roams throughout the dungeon looking for the player by
     * listening to the footsteps of the player.
     *
     * @param health          initial health of the Berbalang.
     * @param randomGenerator instance of random generator for attacking.
     * @param locationAddress the starting location of the monster.
     * @param numberOfRows    the number of rows of the maze that the monster has to move in.
     * @param numberOfColumns the number of columns of the maze that the monster has to move in.
     * @param isWrap          whether the dungeon is wrapping or not.
     */
    public Berbalang(int health, RandomGenerator randomGenerator, LocationAddress locationAddress,
                     int numberOfRows, int numberOfColumns, boolean isWrap) {

        if (health == 0) {
            throw new IllegalArgumentException("Cannot create a dead Berbalang Monster");
        }

        if (locationAddress == null) {
            throw new IllegalArgumentException("Start location address cannot be null, "
                    + "the Berbalang needs a start location");
        }

        if (randomGenerator == null) {
            throw new IllegalArgumentException("Random Generator is required for attacking.");
        }

        if (numberOfColumns <= 0 || numberOfRows <= 0) {
            throw new IllegalArgumentException("Invalid number of rows and columns.");
        }

        this.health = health;
        this.randomGenerator = randomGenerator;
        this.startLocation = locationAddress;
        this.currentLocation = locationAddress;
        this.numberOfColumns = numberOfColumns;
        this.numberOfRows = numberOfRows;
        this.isWrap = isWrap;
    }

    @Override
    public void takeDamage(Weapon weapon) {
        if (!weapon.getWeaponType().equals(WeaponType.PUNCHING_GLOVES)) {
            throw new IllegalArgumentException("Berbalang can only be attacked"
                    + " by punches and not by arrows");
        } else {
            health = health - weapon.getDamage();
            if (health < 0) {
                health = 0;
            }
        }
    }

    @Override
    public boolean isAlive() {
        return health != 0;
    }

    @Override
    public int getHealth() {
        return health;
    }

    @Override
    public int attack() {
        return randomGenerator.getRandomNumberBetween(10, 25);
    }

    @Override
    public ObstacleType getObstacleType() {
        return ObstacleType.BERBALANG;
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
    public LocationAddress getLocation() {
        return new LocationAddress(currentLocation);
    }

    @Override
    public void reset() {
        health = 100;
        currentLocation = startLocation;
    }
}
