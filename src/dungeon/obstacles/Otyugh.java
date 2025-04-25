package dungeon.obstacles;

import java.util.Objects;

import dungeon.location.MovementDirection;
import dungeon.location.paths.LocationAddress;
import dungeon.location.weapon.Weapon;
import dungeon.location.weapon.WeaponType;

/**
 * An Otyugh is a monster that resides in the darkness of the dungeon and hides in Caves of the
 * dungeon. It has a pungent smell that can be detected from one and two locations away. It has max
 * health of 100, and it requires to be hit by 2 arrows to be killed when each arrow does damage to
 * 50. They can only be found in caves and not in tunnels.
 */
public class Otyugh implements Monster {

    private final LocationAddress locationAddress;
    private int health;

    /**
     * Constructs an Otyugh monster with the specified health (usually 100). The health changes as
     * and when the monster takes damage and it does not fall below 0. The monster is considered dead
     * once the health falls to 0.
     *
     * @param initialHealth The level of health at which the monster is entered in dungeon.
     */
    public Otyugh(int initialHealth, LocationAddress locationAddress) {
        if (initialHealth > 100 || initialHealth <= 0) {
            throw new IllegalArgumentException("Otyugh health should be between 0 and 100");
        }

        if (locationAddress == null) {
            throw new IllegalArgumentException("Otyugh location should not be null");
        }

        this.health = initialHealth;
        this.locationAddress = locationAddress;
    }

    @Override
    public boolean isAlive() {
        return (health > 0);
    }

    @Override
    public void takeDamage(Weapon weapon) {
        if (weapon.getWeaponType() != WeaponType.ARROW) {
            throw new IllegalArgumentException("Only arrows can hurt an Otyugh!");
        }

        health = health - weapon.getDamage();
        if (health < 0) {
            health = 0;
        }
    }

    @Override
    public int getHealth() {
        return health;
    }

    @Override
    public int attack() {
        return 100;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Otyugh otyugh = (Otyugh) o;

        return health == otyugh.health;
    }

    @Override
    public int hashCode() {
        return Objects.hash(health);
    }

    @Override
    public ObstacleType getObstacleType() {
        return ObstacleType.OTYUGH;
    }

    @Override
    public void move(MovementDirection movementDirection) {
        throw new IllegalArgumentException("Otyughs cannot move, they reside in one cave only");
    }

    @Override
    public LocationAddress getLocation() {
        return new LocationAddress(locationAddress);
    }

    @Override
    public void reset() {
        health = 100;
    }
}
