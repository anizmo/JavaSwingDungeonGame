package dungeon.maze;

import dungeon.location.MovementDirection;

/**
 * The DungeonController outlines the methods of the controller that runs the DungeonMaze Model, the
 * job of the controller is to receive an input from the users via the View, then processes the
 * user's data with the help of Model and passes the results back to the View.
 */
public interface DungeonGuiController extends DungeonController {

    /**
     * Handle the collect arrow command and pass it on the read write variant of the model by either
     * player's click or by keyboard command.
     */
    void handleCollectArrow();

    /**
     * Handle the collect rubies command and pass it on the read write variant of the model by either
     * player's click or by keyboard command.
     */
    void handleCollectRubies();

    /**
     * Handle the collect diamond command and pass it on the read write variant of the model by either
     * player's click or by keyboard command.
     */
    void handleCollectDiamonds();

    /**
     * Handle the collect sapphire command and pass it on the read write variant of the model by
     * either player's click or by keyboard command.
     */
    void handleCollectSapphires();

    /**
     * Battle the moving monster (Berbalang) by punching it and then the Berbalang punches back, the
     * player cannot move unless and the until the monster is defeated. The fight is turn based.
     */
    void handlePlayerPunch();

    /**
     * Provides a shoot command to the maze model specifying the direction and the distance in order
     * to shoot a crooked arrow in the direction and distance that is specified.
     *
     * @param distance  shoot distance in number of caves.
     * @param direction the direction in which the arrow is to be shot.
     */
    void handleShootArrow(int distance, MovementDirection direction);

    /**
     * Handles the movement of the player and propagates it to the model through the Gui controller.
     * The movement direction is taking from the user in the form of arrow inputs.
     *
     * @param movementDirection the direction in which the player is to be moved.
     */
    void movePlayer(MovementDirection movementDirection);

    /**
     * Handles the jump of the player and propagates it to the model through the Gui controller.
     * The jump direction is taking from the user in the form of arrow inputs. The user can go from
     * normal movement mode to the jump mode by pressing "J" on the keyboard.
     *
     * @param movementDirection the direction in which the player is to be moved.
     */
    void jumpPlayer(MovementDirection movementDirection);

    /**
     * Passes a restart game command to the model through the controller to restart the game with the
     * new settings provided through this method's arguments.
     *
     * @param numberOfRows              the number of rows in the maze.
     * @param numberOfColumns           the number of columns in the maze.
     * @param degreeOfInterconnectivity the degree of connectivity of the maze.
     * @param setWrapping               whether the maze is wrapping or not.
     * @param treasurePercentage        the minimum percentage of caves that need to have treasures.
     * @param difficulty                the number of monsters in the maze.
     * @param numberOfThieves           the number of thieves to be inserted in the dungeon.
     * @param numberOfPits              the number of pits to be inserted in the dungeon.
     * @param hasMovingMonster          whether the dungeon would have a berbalang or not.
     */
    void handleRestartGame(int numberOfRows, int numberOfColumns, int degreeOfInterconnectivity,
                           boolean setWrapping, int treasurePercentage, int difficulty,
                           int numberOfPits, int numberOfThieves, boolean hasMovingMonster);

    /**
     * Reset the game to the start state with the same dungeon, the same set of collectibles,
     * obstacles and moving monster. The player is again restored with all its health and the default
     * inventory.
     */
    void handleResetGame();
}
