package dungeon.maze;

import dungeon.location.Collectible;
import dungeon.location.MovementDirection;
import dungeon.location.weapon.ShootResult;
import dungeon.obstacles.ObstacleType;

/**
 * The maze interface represents the methods of a maze. A maze is expected to have the following
 * methods in order to function properly, these include specifying the degree of interactivity,
 * setting the maze to be either wrapping or not wrapping, the start of the maze game and the end
 * of the maze game.
 */
public interface Maze extends ReadonlyMaze {

    /**
     * A player can move in any of the four directions namely, NORTH, SOUTH, EAST, and WEST exactly
     * one and only one unit. Here, we take the input in the maze to move the player accordingly and
     * update the player's current location according to the maze's context.
     *
     * @param direction direction in which the player is to be moved by one unit.
     * @return the type of obstacle that the player faces after moving.
     */
    ObstacleType movePlayer(MovementDirection direction);

    /**
     * Resets the game to all the original values while not generating any new random parameters.
     */
    void resetGame();

    /**
     * A player can jump similar to moving, where he skips one location in order to avoid the pit, the
     * player can only jump if there is an opening in the pointed direction else he/she gets stopped
     * at the intermediary position.
     *
     * @param direction direction in which the player is to be moved by one unit.
     * @return the type of obstacle that the player faces after moving.
     */
    ObstacleType jumpPlayer(MovementDirection direction);

    /**
     * The player hits the Berbalang if it is in the same position and the berbalang hits the player
     * back until either of them has their health reduced to zero.
     */
    void hitBerbalang();

    /**
     * Represents the player collecting collectible from its current location. It throws an exception
     * if the current location does not have the specific collectible and the player still tries to
     * collect it.
     *
     * @param collectible specifies the collectible to collected.
     */
    void pickup(Collectible collectible);

    /**
     * This method shoots an arrow in the given direction upto the given distance and returns the
     * result of the shot that was fired, whether the shot was a hit or a miss. This result is
     * signified by the ShootResult enum. If the arrow hits the monster at the exact distance
     * specified then it is a HIT else a MISS.
     *
     * @param distance  The number of caves the player expects the monster to be.
     * @param direction The direction in which the arrow is fired.
     * @return result in HIT or MISS by true or false.
     */
    ShootResult shootArrow(int distance, MovementDirection direction);

    /**
     * Sets the state of the game to QUIT and stops taking input for the player.
     */
    void quit();

}
