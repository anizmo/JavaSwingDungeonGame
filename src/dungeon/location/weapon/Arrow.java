package dungeon.location.weapon;

import dungeon.location.MovementDirection;
import dungeon.location.paths.LocationAddress;

/**
 * This class represents an Arrow which can be fired by the player to attack the monster that is
 * located in the dungeon maze. An arrow can move as per the specification in the shoot function,
 * that is different types of arrows can move differently in the maze as per their movement strategy
 * and have different damage on each hit. Arrow is not a weapon but a form of Ammo that is fired by
 * the weapon Bow.
 */
public interface Arrow extends Weapon {

    /**
     * Provides the current location of the arrow, this changes after the arrow is fired. When an
     * arrow is constructed it begins at the start position and then moves ahead as per the strategy
     * defined in the shoot method.
     *
     * @return current location of the arrow.
     */
    LocationAddress getCurrentLocation();

    /**
     * Shoot the arrow towards a given direction and a distance. This distance represents the number
     * of caves travelled by the arrow.
     *
     * @param distance  the distance covered in number of caves.
     * @param direction the direction in which the arrow is fired.
     */
    void shoot(int distance, MovementDirection direction, Weapon bow);

    /**
     * Provides the number of caves the arrow actually travelled before halting.
     *
     * @return number of caves covered.
     */
    int getDistanceTravelled();

}
