package dungeon.obstacles;

import dungeon.location.paths.LocationAddress;

/**
 * Represents a thief that hides in the dungeon. When the player encounters a thief, the player
 * loses all its treasure and thief runs away from the location. Hence, the thief cannot be found in
 * the same location twice.
 */
public class Thief implements Obstacle {

    private final LocationAddress locationAddress;

    /**
     * Constructs a thief that steals the player's treasure and runs away with it when encountered.
     *
     * @param locationAddress the location address of the thief.
     */
    public Thief(LocationAddress locationAddress) {
        if (locationAddress == null) {
            throw new IllegalArgumentException("Cannot create a thief without location address.");
        }
        this.locationAddress = locationAddress;
    }

    @Override
    public ObstacleType getObstacleType() {
        return ObstacleType.THIEF;
    }

    @Override
    public LocationAddress getLocation() {
        return new LocationAddress(locationAddress);
    }
}
