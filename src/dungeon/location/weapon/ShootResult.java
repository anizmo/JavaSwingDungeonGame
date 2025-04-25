package dungeon.location.weapon;

import dungeon.location.paths.LocationAddress;

/**
 * This class represents the result of an arrow that is shot by the player. Whether the outcome was
 * a hit or a miss is specified in the shotHit variable and the location at which the arrow landed
 * is also recorded in this class. This is useful to parse the information to the view and provide
 * an end outcome result of the arrow or any other form of projectile weapon fired.
 */
public class ShootResult {

    private final boolean shotHit;

    private final LocationAddress arrowLandingAddress;

    /**
     * Represents the result of an arrow that is shot by the player. Whether the outcome was a hit or
     * a miss is specified in the shotHit variable and the location at which the arrow landed is also
     * recorded in this class. This is useful to parse the information to the view and provide an end
     * outcome result of the arrow or any other form of projectile weapon fired.
     *
     * @param shotHit             the result whether it was a hit or a miss.
     * @param arrowLandingAddress the landing location address of the ammo used to hit the monster.
     */
    public ShootResult(boolean shotHit, LocationAddress arrowLandingAddress) {
        this.shotHit = shotHit;
        this.arrowLandingAddress = arrowLandingAddress;
    }

    /**
     * Returns the result whether it was a hit or a miss.
     *
     * @return shotHit.
     */
    public boolean isShotHit() {
        return shotHit;
    }

    /**
     * Returns the location address at which the arrow landed after firing.
     *
     * @return arrowLandingAddress.
     */
    public LocationAddress getArrowLandingAddress() {
        return arrowLandingAddress;
    }

}
