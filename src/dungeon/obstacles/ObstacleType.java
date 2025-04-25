package dungeon.obstacles;

/**
 * This enum categorises the different types of obstacles that the player faces in the dungeon while
 * moving through it. It also acts as a status report of every move, representing what the player
 * came across in every location that it visits. An injured Otyugh means an Otyugh that has taken
 * one arrow and not yet dead.
 */
public enum ObstacleType {

    OTYUGH, INJURED_OTYUGH, DEAD_OTYUGH, THIEF, PIT, BERBALANG, NO_OBSTACLE;

}
