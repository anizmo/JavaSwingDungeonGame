package dungeon.location.weapon;

/**
 * The weapon class represents a weapon that the player can be equipped with at the time of
 * introduction in the dungeon. A weapon can be categorised as different types and can either
 * require or not require an ammo to attack. For example, a bow requires an arrow as the ammo to
 * fire, a gun requires bullets as an ammo to fire but sword and axe do not require an ammo to
 * attack, they can attack independently.
 */
public interface Weapon {

    /**
     * Returns the type of the weapon in WeaponType enum. This is useful when an ammo requires a
     * weapon to fire.
     *
     * @return weaponType.
     */
    WeaponType getWeaponType();

    /**
     * The getDamage method returns the damage done by the weapon each time it hits the monster. It
     * can be different for different types of weapons.
     *
     * @return points of damage the weapon does.
     */
    int getDamage();


}
