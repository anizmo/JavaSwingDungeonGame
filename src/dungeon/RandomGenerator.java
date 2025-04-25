package dungeon;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * This class encapsulates all the random generation logic required for the random logic throughout
 * the gameplay. There are methods to pick random items from a list of any objects. There is also
 * a provision to provide a stream of numbers that is predefined as random numbers to mock the
 * behaviour of the model as per the requirement.
 */
public class RandomGenerator {

    private List<Integer> randomParameters;

    private int counter;

    /**
     * This is used for generating a sequence of false random numbers that are specified, this is used
     * only for testing or demonstration.
     *
     * @param randomParameters a list of sequence of false random numbers.
     */
    public void setRandomParameters(List<Integer> randomParameters) {
        this.randomParameters = randomParameters;
        counter = 0;
    }

    /**
     * Returns a list of n random objects from a larger list of objects.
     *
     * @param items a list of any items.
     * @param n     number of items required to be given randomly.
     * @return list of n items.
     */
    public <T> List<T> pickNRandom(List<T> items, int n) {
        List<T> randomNList = new ArrayList<>();
        Set<Integer> randomIndexes = new LinkedHashSet<>();
        while (randomIndexes.size() < n) {
            randomIndexes.add(getRandomNumberBetween(0, items.size() - 1));
        }
        randomIndexes.forEach(integer -> {
            randomNList.add(items.get(integer));
        });
        return randomNList;
    }

    /**
     * Generates a random number between the min and max, both the boundaries are inclusive. For
     * testing, this method will take up a value from the randomParameters list if it is not empty.
     *
     * @param min the lower bound (inclusive).
     * @param max the upper bound (inclusive).
     * @return randomNumber between the min and max.
     */
    public int getRandomNumberBetween(int min, int max) {
        if (randomParameters != null && !randomParameters.isEmpty()
                && counter < randomParameters.size()) {
            int falseRandomNumberForTesting = randomParameters.get(counter);
            counter++;
            return falseRandomNumberForTesting;
        } else {
            return (int) Math.floor(Math.random() * (max - min + 1) + min);
        }
    }

}
