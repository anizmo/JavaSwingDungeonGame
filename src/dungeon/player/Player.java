package dungeon.player;

import java.util.Map;

import dungeon.location.Collectible;
import dungeon.location.MovementDirection;
import dungeon.location.paths.LocationAddress;
import dungeon.location.weapon.Weapon;

/**
 * The player interface houses the methods of the player that are required to navigate through the
 * maze and collect treasure. The most important method of the player is to move through the maze,
 * the get current location methods returns the location of the player in the 2D-Grid of the maze.
 */
public interface Player {

    /**
     * Move the player one unit in the direction specified as the parameter.
     *
     * @param movementDirection Specifies the direction, North, South, East, West where the player is
     *                          to be moved.
     */
    void move(MovementDirection movementDirection);

    /**
     * Gives the current location of the player in the 2D-Grid of the maze of the dungeon in row and
     * column number format.
     *
     * @return LocationAddress of the player's current location.
     */
    LocationAddress getCurrentLocation();

    /**
     * Collects the treasure from the location and updates the score accordingly. Each treasure has a
     * different score associated with it.
     *
     * @param collectible treasure of the cave.
     */
    void collectTreasure(Collectible collectible);

    /**
     * Returns a defensive copy of the collected treasure map that enlists all the treasures collected
     * by the player along with the count of it.
     *
     * @return collectedTreasureMap.
     */
    Map<Collectible, Integer> getCollectedTreasureMap();

    /**
     * Returns the weapon equipped by the player to slay the monster.
     *
     * @return equippedWeapon.
     */
    Weapon getWeapon();

    /**
     * Returns the total number of un-shot arrows available with the player.
     *
     * @return numberOfArrows.
     */
    int getNumberOfArrowsAvailable();

    /**
     * Reduces an arrowCount from the player and validates the distance.
     *
     * @param distance the number of caves the arrow is expected to cover.
     */
    void fireArrow(int distance);

    /**
     * Increases the arrow count of the player by one.
     */
    void addArrow();

    /**
     * Clears out the treasure map that show the treasures and the quantity of it the player is
     * carrying with him.
     */
    void emptyTreasure();

    /**
     * Record damage to the player and deduct health according, the health is capped at a lowest of 0,
     * and it cannot go negative.
     *
     * @param damage the amount of health to be deducted from the player.
     */
    void takeDamage(int damage);

    /**
     * Returns the current health point of the player, this is from a scale of 0-100.
     *
     * @return the current health points of the player.
     */
    int getHealth();

    /**
     * Reset player abilities to the defaults that were provided while constructing the player.
     */
    void reset();
}
