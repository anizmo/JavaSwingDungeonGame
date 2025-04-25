package dungeon.location;

import java.util.Set;

import dungeon.location.paths.LocationAddress;
import dungeon.obstacles.Monster;
import dungeon.obstacles.Obstacle;
import dungeon.obstacles.Pit;
import dungeon.obstacles.Thief;

/**
 * The location can be either a cave or a tunnel depending on the number of openings it has, it
 * represents a single cell in the grid of the maze. When there are 1, 3 or 4 openings to a location
 * it is classified as a cave and if it has exactly 2 openings then it is classified as a tunnel.
 */
public interface Location {

    /**
     * Get the type of location that is either a cave or a tunnel depending on the number of openings
     * it has.
     *
     * @return locationType.
     */
    LocationType getType();

    /**
     * Returns all the obstacles present in the given location as a defensive copy.
     *
     * @return set of obstacles.
     */
    Set<Obstacle> getAllObstacles();

    /**
     * Add treasures to the location.
     *
     * @param collectibles list of treasures.
     */
    void addTreasure(Set<Collectible> collectibles);

    /**
     * Add an opening to the location in the specified direction.
     *
     * @param movementDirection the direction to which the opening is to be added.
     */
    void addOpenings(MovementDirection movementDirection);

    /**
     * get the openings of the location.
     *
     * @return list of movement directions.
     */
    Set<MovementDirection> getOpenings();

    /**
     * Enlist all the treasures that are present at a given location.
     *
     * @return set of treasures present at the location.
     */
    Set<Collectible> getTreasures();

    /**
     * Marks the treasure as collected from the given location and removes the specific treasure from
     * the treasure set at this location.
     *
     * @param collectible specifies the collectible to be marked as collected from the location.
     */
    void markTreasureCollected(Collectible collectible);

    /**
     * Marks the arrow as collected from the given location by setting the hasArrow field to false of
     * the location.
     */
    void markArrowCollected();

    /**
     * Returns a symbolic representation of the location with respect to its openings, very useful
     * when the user wants to print the maze map without going through the complications of parsing
     * each location and assigning a symbol to it. A user of this library can also use their own
     * representation of the maze by using the method get openings and parsing a similar logic at
     * their end of the code.
     *
     * @return directionalSymbol of the location.
     */
    String getDirectionalSymbol();

    /**
     * Add an arrow to the location.
     */
    void addArrow();

    /**
     * Adds a monster to the location only if it is a cave. The monster added has a health of 100
     * when it is added to the cave.
     *
     * @param monster Monster to be added.
     */
    void addMonster(Monster monster);

    /**
     * Adds a pit to this location, the pit is an obstacle in which if the player falls down loses the
     * game.
     *
     * @param pit the pit to be added in the location.
     */
    void addPit(Pit pit);

    /**
     * Adds a thief to this location, the thief is an obstacle to which if the player meets, loses
     * all its treasure.
     *
     * @param thief the thief to be added in the location.
     */
    void addThief(Thief thief);

    /**
     * Returns the Otyugh that is located at the specified position, if there is no Otyugh then it
     * returns a null.
     *
     * @return the monster located at the location.
     */
    Monster getOtyugh();

    /**
     * Returns the thief that is located at the specified position, if there is no thief then it
     * returns a null.
     *
     * @return the thief located at the location.
     */
    Thief getThief();

    /**
     * The thief escapes the dungeon after stealing the treasure from the player.
     */
    void makeThiefEscapeDungeon();

    /**
     * Returns the pit that is located at the specified position, if there is no pit then it
     * returns a null.
     *
     * @return the pit located at the location.
     */
    Pit getPit();

    /**
     * Get the address of the location in reference of the map maze, this is provided in the form of
     * row number and column number encapsulated in the LocationAddress object.
     *
     * @return address of the location.
     */
    LocationAddress getAddress();

    /**
     * This methods specifies if this location has an arrow that can be collected by the player or
     * not. Once the arrow is collected this method returns false.
     *
     * @return boolean flag stating whether the location has an arrow or not.
     */
    boolean isHasArrow();

    /**
     * Add a set of obstacles to the location. Useful to reset the game.
     *
     * @param obstacles the set of obstacles to be added to the location.
     */
    void addObstacles(Set<Obstacle> obstacles);
}
