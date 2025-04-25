package dungeon.location.weapon;

import dungeon.RandomGenerator;

/**
 * The player can battle the moving monster (Berbalang) in a hand-to-hand combat for which the
 * weapon used is the punching gloves. The player does random damage in the range of 10 and 25,
 * which is recorded on the monster if it is in the same location.
 */
public class PunchingGloves implements Weapon {

    private final RandomGenerator randomGenerator;

    /**
     * Creates an instance of the weapon type punching gloves that are used to slay the moving monster
     * called the Berbalang.
     *
     * @param randomGenerator an instance of random generator.
     */
    public PunchingGloves(RandomGenerator randomGenerator) {
        if (randomGenerator == null) {
            throw new IllegalArgumentException("Random Generator is required to perform attacks");
        }
        this.randomGenerator = randomGenerator;
    }

    @Override
    public WeaponType getWeaponType() {
        return WeaponType.PUNCHING_GLOVES;
    }

    @Override
    public int getDamage() {
        return randomGenerator.getRandomNumberBetween(10, 25);
    }
}
