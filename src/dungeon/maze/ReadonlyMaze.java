package dungeon.maze;

import java.util.List;
import java.util.Map;
import java.util.Set;

import dungeon.location.Collectible;
import dungeon.location.Location;
import dungeon.location.paths.LocationAddress;
import dungeon.location.paths.Path;

/**
 * The maze interface represents the only the read methods of the maze. A maze is expected to have
 * the following methods in order to function properly, these include specifying the degree of
 * interactivity, setting the maze to be either wrapping or not wrapping, the start of the maze game
 * and the end of the maze game. This interface exposes methods that are used by the view to provide
 * an output for the appropriate state of the game.
 */
public interface ReadonlyMaze {

    /**
     * Get whether the maze is wrapping or not wrapping, that is if the player can move from one end
     * of the maze to another.
     *
     * @return isWrapping.
     */
    boolean isWrapping();

    /**
     * Get the degree of interconnectivity, this number represents the number of paths from one given
     * location in the maze to another. When set to one, there is exactly one path from one point to
     * another.
     *
     * @return degreeOfInterconnectivity.
     */
    int getDegreeOfInterconnectivity();

    /**
     * Get the row number of the starting location of the maze.
     *
     * @return startRow.
     */
    int getStartRow();

    /**
     * Get the column number of the starting location of the maze.
     *
     * @return startColumn.
     */
    int getStartColumn();

    /**
     * Get the row number of the destination of the maze.
     *
     * @return endRow.
     */
    int getEndRow();

    /**
     * Get the column number of the destination of the maze.
     *
     * @return endColumn.
     */
    int getEndColumn();

    /**
     * Returns the total number of rows in the maze.
     *
     * @return numberOfRows in the maze.
     */
    int getNumberOfRows();

    /**
     * Returns the total number of columns in the maze.
     *
     * @return numberOfColumns in the maze.
     */
    int getNumberOfColumns();

    /**
     * Provides a call to the toString method of the MazePlayer class and returns the information of
     * the player which includes its current location, score and treasure that he/she is carrying.
     *
     * @return player details string.
     */
    String getPlayerDetails();

    /**
     * Returns the treasure map that represents the treasure and the count of each treasure that the
     * player is carrying.
     *
     * @return treasureCountMap.
     */
    Map<Collectible, Integer> getTreasureMap();

    /**
     * The game state represents the current state of the player navigation in the maze, the game can
     * be in either of the three states, namely, START, PLAYING, and GAME_OVER. This method returns
     * the state of the game in an enum.
     *
     * @return current state of the game.
     */
    GameState getState();

    /**
     * Get the copy of the current Location of the player, this returns a copy of the player's current
     * location in the maze grid.
     *
     * @return copy of the player's current location.
     */
    Location getPlayerCurrentLocation();

    /**
     * This provides a list of all the paths connected such that there exists one and only one path
     * between 2 points when the degree of interconnectivity is 0.
     *
     * @return list of connected paths.
     */
    List<Path> getConnectedPaths();

    /**
     * Returns a defensive deep copy of the maze map that is generated in the dungeon, this is useful
     * to print in the driver class or UI.
     *
     * @return location grid.
     */
    Location[][] getMazeMap();

    /**
     * This method detects the smell of the player's current location at runtime and returns an
     * integer value which specifies the level of smell in a given location.
     *
     * @return the level of smell in the current location of the player as int.
     */
    int detectSmell();

    /**
     * This method detects the smell of the player's current location at runtime and returns an
     * integer value which specifies the level of smell in a given location. Any non-zero value of
     * the wind represents that there is wind in the location and a pit in the next location.
     *
     * @return the level of wind in the current location of the player as int.
     */
    int detectWindLevel();

    /**
     * Returns the number of arrows the player is carrying at the given state.
     *
     * @return number of arrows.
     */
    int getNumberOfArrows();

    /**
     * Provides a set of visited locations, this set is updated each time the player moves and
     * explores a new location in the dungeon.
     *
     * @return set of visited location addresses.
     */
    Set<LocationAddress> getVisitedLocations();

    /**
     * Returns the percentage of treasure that is provided by the user while constructing the
     * DungeonMaze class. This is useful for restarting the game with the same settings.
     *
     * @return percentage of caves having treasures.
     */
    int getTreasurePercentage();

    /**
     * Returns the difficulty i.e. the number of monsters in the dungeon, this is specified by the
     * user while creating the dungeon.
     *
     * @return number of monsters (Otyughs)
     */
    int getDifficulty();

    /**
     * Returns the number of thieves in the dungeon, this is specified by the user while creating the
     * dungeon.
     *
     * @return number of thieves.
     */
    int getNumberOfThieves();

    /**
     * Returns the number of pits in the dungeon, this is specified by the user while creating the
     * dungeon.
     *
     * @return number of pits.
     */
    int getNumberOfPits();

    /**
     * Returns whether the dungeon has a moving monster (Berbalang) or not.
     *
     * @return boolean value stating whether the dungeon has a moving monster or not.
     */
    boolean hasMovingMonster();

    /**
     * Provides the location address of the current location of the Berbalang monster that is moving
     * throughout the dungeon.
     *
     * @return location address of the moving monster.
     */
    LocationAddress getBerbalangLocation();

    /**
     * Returns the updated health of the Berbalang monster.
     *
     * @return berbalang health.
     */
    int getBerbalangHealth();

    /**
     * Returns the updated health of the player.
     *
     * @return player's health.
     */
    int getPlayerHealth();

}
