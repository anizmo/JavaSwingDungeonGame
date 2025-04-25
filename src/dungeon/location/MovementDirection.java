package dungeon.location;

/**
 * The MovementDirection enum represents all the possible directions which can have openings for a
 * location as well as all the possible turns that the player can take while navigating through the
 * Maze. Since the maze only follows the four directions (that is there are no diagonal openings),
 * we classify Movement Direction only as North, South, West and East.
 */
public enum MovementDirection {

    NORTH,
    SOUTH,
    EAST,
    WEST;

    /**
     * Provides the direction that is opposite to this direction.
     *
     * @return opposite movement direction.
     */
    public MovementDirection getOpposite() {
        switch (this) {
            case SOUTH:
                return NORTH;
            case NORTH:
                return SOUTH;
            case EAST:
                return WEST;
            case WEST:
                return EAST;
            default:
                return this;
        }
    }
}
