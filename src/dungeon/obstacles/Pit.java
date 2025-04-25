package dungeon.obstacles;

import dungeon.location.paths.LocationAddress;

/**
 * Represents a pit that the player can fall into. A player loses the game once he/she falls into
 * the pit. A pit's presence can be felt by user by the surrounding wind, it can only be detected
 * from one location away from it.
 */
public class Pit implements Obstacle {

    private final LocationAddress locationAddress;

    /**
     * Constructs a pit that kills the player and ends the game when the player falls into it.
     *
     * @param locationAddress the location address of the pit.
     */
    public Pit(LocationAddress locationAddress) {
        if (locationAddress == null) {
            throw new IllegalArgumentException("Cannot create a Pit without location address.");
        }

        this.locationAddress = locationAddress;
    }

    @Override
    public ObstacleType getObstacleType() {
        return ObstacleType.PIT;
    }

    @Override
    public LocationAddress getLocation() {
        return new LocationAddress(locationAddress);
    }

}
