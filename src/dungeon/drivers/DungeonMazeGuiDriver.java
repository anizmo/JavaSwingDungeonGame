package dungeon.drivers;

import java.io.InputStreamReader;

import dungeon.maze.DungeonConsoleController;
import dungeon.maze.DungeonController;
import dungeon.maze.DungeonMaze;
import dungeon.maze.DungeonSwingController;
import dungeon.maze.Maze;
import dungeon.view.DungeonSwingView;
import dungeon.view.DungeonView;

/**
 * The DungeonMazeControllerDriver represents a run of the GUI Adventure Game which creates a Maze
 * with the specified command line arguments. If the user does not give any command line argument
 * then a maze is created with 6 rows and 6 columns and a 3 degree of interconnectivity with 20%
 * of caves having treasures in it. Here, the maze is printed to the console such that the start
 * location is printed in red color and the end location is printed in green color. This class also
 * displays the potential paths in the maze, the shortlisted connected paths after applying the
 * modified Kruskal's algorithm and the status of the player's movement after every move. Here we
 * are using the Controller and pass along the model. We take user inputs and pass them on to the
 * model from the controller.
 */
public class DungeonMazeGuiDriver {

    /**
     * The main method executes the program by taking the command line arguments in the following
     * order - numberOfRows, numberOfColumns, degreeOfInterconnectivity, percentage of caves holding
     * one or more treasures, the difficulty and whether the treasure is wrapping or not.
     *
     * @param args Command line arguments given to the class.
     */
    public static void main(String[] args) {
        int numberOfRows = 10;
        int numberOfColumns = 10;
        int degreeOfInterconnectivity = 3;
        int caveTreasurePercentage = 50;
        boolean isWrapping = false;
        int difficulty = 3;

        if (args.length > 0) {
            try {
                numberOfRows = Integer.parseInt(args[0]);
                numberOfColumns = Integer.parseInt(args[1]);
                degreeOfInterconnectivity = Integer.parseInt(args[2]);
                caveTreasurePercentage = Integer.parseInt(args[3]);
                difficulty = Integer.parseInt(args[4]);
            } catch (NumberFormatException exception) {
                exception.printStackTrace();
            }
            isWrapping = Boolean.parseBoolean(args[5]);
        }

        Maze model;
        DungeonController controller;
        if (args.length > 0) {
            model = new DungeonMaze(numberOfRows, numberOfColumns, degreeOfInterconnectivity,
                    isWrapping, caveTreasurePercentage, difficulty, 0, 0,
                    false);
            Readable input = new InputStreamReader(System.in);
            Appendable output = System.out;
            controller = new DungeonConsoleController(input, output);
        } else {
            model = new DungeonMaze(numberOfRows, numberOfColumns, degreeOfInterconnectivity,
                    isWrapping, caveTreasurePercentage, difficulty, 1, 1,
                    true);
            DungeonView dungeonView = new DungeonSwingView(model);
            controller = new DungeonSwingController(model, dungeonView);
        }

        controller.playGame(model);
    }

}
