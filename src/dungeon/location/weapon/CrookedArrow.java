package dungeon.location.weapon;

import java.util.Arrays;
import java.util.Objects;
import java.util.Set;

import dungeon.location.Location;
import dungeon.location.LocationType;
import dungeon.location.MovementDirection;
import dungeon.location.paths.LocationAddress;

/**
 * This class represents a CrookedArrow that can bend through the tunnels and endlessly navigate
 * through tunnels irrespective of the distance specified with it while firing the arrow. When a
 * crooked arrow is fired from a cave or a tunnel with a specified direction it navigates through
 * the location until it is stopped by a cave that does not have an opening in the firing direction
 * or it has already travelled the distance specified while firing.
 */
public class CrookedArrow implements Arrow {

    private final LocationAddress startLocation;
    private final boolean wrapping;
    private final int numberOfColumns;
    private final int numberOfRows;
    private final Location[][] map;
    private final int damage;
    private LocationAddress currentLocation;
    private int cavesVisitedByTheArrow;

    /**
     * Constructs a Crooked Arrow with that is fired from the firing address, it navigates through the
     * maze in a fashion such that it bends through the tunnels and travels through the caves if there
     * exists an opening in the cave in the direction of the shooting of the arrow.
     *
     * @param firingAddress The location address from which the address was shot.
     * @param isWrap        Specifies whether the maze in which the arrow is shot is wrapping or not.
     * @param map           The maze in which the arrow is to be moved.
     */
    public CrookedArrow(LocationAddress firingAddress, boolean isWrap, Location[][] map) {
        if (firingAddress == null) {
            throw new IllegalArgumentException("Arrow requires a firing location.");
        }

        if (map == null) {
            throw new IllegalArgumentException("Arrow needs to know the maze to travel through it.");
        }

        this.startLocation = firingAddress;
        this.currentLocation = firingAddress;
        this.wrapping = isWrap;
        this.numberOfRows = map.length;
        this.numberOfColumns = map[0].length;
        this.map = map;
        this.cavesVisitedByTheArrow = 0;
        this.damage = 50;
    }

    @Override
    public void shoot(int distance, MovementDirection direction, Weapon weapon) {

        if (weapon == null) {
            throw new IllegalArgumentException("Cannot fire an arrow without a weapon (bow)");
        }

        if (direction == null) {
            throw new IllegalArgumentException("Direction cannot be null.");
        } else if (distance <= 0) {
            throw new IllegalArgumentException("Distance cannot be zero or negative.");
        }

        Location arrowCurrentLocation = map[getCurrentLocation().getRowNumber()]
                [getCurrentLocation().getColumnNumber()];

        while ((arrowCurrentLocation.getType().equals(LocationType.CAVE)
                && arrowCurrentLocation.getOpenings().contains(direction)
                && cavesVisitedByTheArrow != distance)
                || (arrowCurrentLocation.getType().equals(LocationType.TUNNEL))) {

            moveOnePlace(direction);
            arrowCurrentLocation = map[currentLocation.getRowNumber()][currentLocation.getColumnNumber()];

            if (arrowCurrentLocation.getType().equals(LocationType.CAVE)
                    && !arrowCurrentLocation.getAddress().equals(startLocation)) {
                cavesVisitedByTheArrow++;
                if (cavesVisitedByTheArrow == distance) {
                    break;
                }
            } else if (arrowCurrentLocation.getType().equals(LocationType.TUNNEL)) {
                Set<MovementDirection> tunnelOpenings = arrowCurrentLocation.getOpenings();
                tunnelOpenings.remove(direction.getOpposite());
                direction = tunnelOpenings.iterator().next();
            }

        }
    }

    @Override
    public int getDamage() {
        return damage;
    }

    @Override
    public int getDistanceTravelled() {
        return cavesVisitedByTheArrow;
    }

    @Override
    public LocationAddress getCurrentLocation() {
        return currentLocation;
    }

    private void moveOnePlace(MovementDirection movementDirection) {
        if (movementDirection == null) {
            throw new IllegalArgumentException("Direction cannot be null, provide a valid direction");
        }

        switch (movementDirection) {
            case WEST: {
                if (wrapping && currentLocation.getColumnNumber() == 0) {
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
                if (wrapping && currentLocation.getColumnNumber() == numberOfColumns - 1) {
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
                if (wrapping && currentLocation.getRowNumber() == numberOfRows - 1) {
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
                if (wrapping && currentLocation.getRowNumber() == 0) {
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
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CrookedArrow that = (CrookedArrow) o;
        return wrapping == that.wrapping && numberOfColumns == that.numberOfColumns
                && numberOfRows == that.numberOfRows && damage == that.damage
                && cavesVisitedByTheArrow == that.cavesVisitedByTheArrow
                && Objects.equals(startLocation, that.startLocation)
                && Arrays.deepEquals(map, that.map)
                && Objects.equals(currentLocation, that.currentLocation);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(startLocation, wrapping, numberOfColumns, numberOfRows, damage,
                currentLocation, cavesVisitedByTheArrow);
        result = 31 * result + Arrays.deepHashCode(map);
        return result;
    }

    @Override
    public WeaponType getWeaponType() {
        return WeaponType.ARROW;
    }
}
