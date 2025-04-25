package dungeon.obstacles;

import dungeon.location.paths.LocationAddress;

/**
 * An obstacle represents the different types of challenges the player faces while moving into a
 * location and this is categorised by the ObstacleType enum which enlists all the different types
 * of obstacles that can be faced by the player in the dungeon.
 */
public interface Obstacle {

    /**
     * Returns the type of obstacle that the current implementation is. The obstacle type enum is used
     * to represent this.
     *
     * @return type of the obstacle.
     */
    ObstacleType getObstacleType();

    /**
     * Returns the current location of the obstacle, in case of a moving obstacle the most updated
     * location is returned for the obstacle.
     *
     * @return location address of the obstacle.
     */
    LocationAddress getLocation();

}
