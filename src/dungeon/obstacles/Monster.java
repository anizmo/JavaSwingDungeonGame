package dungeon.obstacles;

import dungeon.location.MovementDirection;
import dungeon.location.weapon.Weapon;

/**
 * This interface represents the monster that resides in the cave who eats the player when they are
 * at their full health or when their health is half the fight with the player and has a 50-50
 * chance of either eating the player or the player escaping. This is the case of the Otyugh monster
 * the Berbalang monster follows the player throughout the dungeon.
 */
public interface Monster extends Obstacle {

    /**
     * Recording the damage done to the monster by the arrow that is fired to it.
     *
     * @param weapon the arrow that hits the monster.
     */
    void takeDamage(Weapon weapon);

    /**
     * Signifies if the monster is alive or not by checking its health. If the health of the monster
     * is 0 then this method returns a false.
     *
     * @return isAlive boolean value.
     */
    boolean isAlive();

    /**
     * Returns the health out of 100 of the monster.
     *
     * @return health.
     */
    int getHealth();

    /**
     * Returns the damage points of the attack that is done by the monster.
     *
     * @return damage points of the attack.
     */
    int attack();

    /**
     * Moves the movable monster in the specified direction, this requires the monster to have the
     * ability to move.
     *
     * @param movementDirection the direction in which the monster is to be moved.
     */
    void move(MovementDirection movementDirection);


    /**
     * Reset the attributes of the monster to the one's assigned while constructing the monster, used
     * while resetting the game.
     */
    void reset();

}
