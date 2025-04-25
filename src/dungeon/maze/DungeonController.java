package dungeon.maze;

/**
 * The DungeonController outlines the methods of the controller that runs the DungeonMaze Model, the
 * job of the controller is to receive an input from the users via the View, then processes the
 * user's data with the help of Model and passes the results back to the View.
 */
public interface DungeonController {

    /**
     * Starts the game with the specified model. It requires the model to not be null and to have the
     * methods required to provide the input to it and get appropriate output from it.
     *
     * @param dungeonMazeModel an instance of the dungeonMaze model.
     */
    void playGame(Maze dungeonMazeModel);

}
