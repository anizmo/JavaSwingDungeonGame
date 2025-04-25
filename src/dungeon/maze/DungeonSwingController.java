package dungeon.maze;

import dungeon.location.Collectible;
import dungeon.location.MovementDirection;
import dungeon.obstacles.ObstacleType;
import dungeon.view.DungeonSwingView;
import dungeon.view.DungeonView;

/**
 * The DungeonSwingController is representative of a GUI based controller which consists of methods
 * that are used to communicate between a GUI and the model. The GUI (view) only has the read only
 * variant of the model and does not have the power to mutate any state of the model. Thus, the
 * controller is a bridge between the model and the view to mutate the state of the model.
 */
public class DungeonSwingController implements DungeonGuiController {

    private Maze dungeonMazeModel;

    private DungeonView dungeonView;

    /**
     * Constructs a Controller that is used for a Gui based game. This constructor takes a read-write
     * version of the model and an instance of the view to play the game.
     *
     * @param dungeonMazeModel read-write variant of the model.
     * @param dungeonView      instance of the view.
     */
    public DungeonSwingController(Maze dungeonMazeModel, DungeonView dungeonView) {
        if (dungeonMazeModel == null) {
            throw new IllegalArgumentException("Model cannot be null, assign a proper maze model.");
        }

        if (dungeonView == null) {
            throw new IllegalArgumentException("View cannot be null for a Gui Controller,"
                    + " try a console controller instead.");
        }

        this.dungeonMazeModel = dungeonMazeModel;
        this.dungeonView = dungeonView;
    }

    @Override
    public void playGame(Maze dungeonMazeModel) {

        if (dungeonMazeModel == null) {
            throw new IllegalArgumentException("Model cannot be null, assign a proper maze model.");
        }

        dungeonView.setupGameMenu(this);
        dungeonView.setupMazeBoard(this);
        dungeonView.setupActionPanel(this);
        dungeonView.makeVisible();
        dungeonView.setupKeyboardListener(this);
    }

    @Override
    public void handleCollectArrow() {
        try {
            dungeonMazeModel.pickup(Collectible.ARROW);
        } catch (IllegalStateException illegalStateException) {
            dungeonView.showMessage("Collect Arrow", illegalStateException.getLocalizedMessage());
        }
        dungeonView.refresh();
    }

    @Override
    public void handleCollectRubies() {
        try {
            dungeonMazeModel.pickup(Collectible.RUBIES);
        } catch (IllegalStateException illegalStateException) {
            dungeonView.showMessage("Collect Rubies", illegalStateException.getLocalizedMessage());
        }
        dungeonView.refresh();
    }

    @Override
    public void handleCollectDiamonds() {
        try {
            dungeonMazeModel.pickup(Collectible.DIAMONDS);
        } catch (IllegalStateException illegalStateException) {
            dungeonView.showMessage("Collect Diamonds", illegalStateException.getLocalizedMessage());
        }
        dungeonView.refresh();
    }


    @Override
    public void handleCollectSapphires() {
        try {
            dungeonMazeModel.pickup(Collectible.SAPPHIRES);
        } catch (IllegalStateException illegalStateException) {
            dungeonView.showMessage("Collect Sapphires", illegalStateException.getLocalizedMessage());
        }
        dungeonView.refresh();
    }

    @Override
    public void handleShootArrow(int distance, MovementDirection direction) {
        try {
            if (dungeonMazeModel.shootArrow(distance, direction).isShotHit()) {
                dungeonView.showMessage("Arrow Shot!",
                        "You hear a great howl in the distance!");
            } else {
                dungeonView.showMessage("Arrow Shot!", "You hear nothing!");
            }
        } catch (IllegalStateException | IllegalArgumentException illegalArgumentException) {
            dungeonView.showMessage("Arrow!", illegalArgumentException.getLocalizedMessage());
        }

        dungeonView.refresh();
    }

    @Override
    public void movePlayer(MovementDirection movementDirection) {
        try {
            handleMovementObstacles(dungeonMazeModel.movePlayer(movementDirection));
        } catch (IllegalArgumentException | IllegalStateException illegalStateException) {
            dungeonView.showMessage("Dungeon", illegalStateException.getLocalizedMessage());
        }
        dungeonView.refresh();
    }

    @Override
    public void jumpPlayer(MovementDirection movementDirection) {
        handleMovementObstacles(dungeonMazeModel.jumpPlayer(movementDirection));
        dungeonView.refresh();
    }

    @Override
    public void handleRestartGame(int numberOfRows, int numberOfColumns,
                                  int degreeOfInterconnectivity, boolean setWrapping,
                                  int treasurePercentage, int difficulty, int numberOfPits,
                                  int numberOfThieves, boolean hasMovingMonster) {
        Maze dungeonMazeModel = new DungeonMaze(numberOfRows, numberOfColumns,
                degreeOfInterconnectivity, setWrapping, treasurePercentage, difficulty, numberOfThieves,
                numberOfPits, hasMovingMonster);
        this.dungeonMazeModel = dungeonMazeModel;
        this.dungeonView.disposeView();
        this.dungeonView = new DungeonSwingView(dungeonMazeModel);
        this.playGame(dungeonMazeModel);
        dungeonView.refresh();

    }

    @Override
    public void handlePlayerPunch() {
        try {
            dungeonMazeModel.hitBerbalang();
            if (dungeonMazeModel.getPlayerHealth() == 0) {
                dungeonView.showMessage("You Lose", "You lost the battle with the Berbalang!");
            } else if (dungeonMazeModel.getBerbalangHealth() == 0) {
                dungeonView.showMessage("Berbalang Killed",
                        "You won the battle with the Berbalang!");
            }
        } catch (IllegalArgumentException | IllegalStateException illegalStateException) {
            dungeonView.showMessage("Punch", illegalStateException.getLocalizedMessage());
        }
        dungeonView.refresh();
    }

    @Override
    public void handleResetGame() {
        this.dungeonMazeModel.resetGame();
        dungeonView.refresh();
    }

    private void handleMovementObstacles(ObstacleType obstacleType) {
        if ((obstacleType == ObstacleType.OTYUGH || obstacleType == ObstacleType.INJURED_OTYUGH)
                && dungeonMazeModel.getState() == GameState.LOSE) {
            dungeonView.showMessage("You Lose!",
                    "Chomp, chomp, chomp! You are eaten by an Otyugh");
        }
        if (obstacleType == ObstacleType.PIT && dungeonMazeModel.getState() == GameState.LOSE) {
            dungeonView.showMessage("You Lose!",
                    "You fell into a Pit");
        } else if (obstacleType == ObstacleType.DEAD_OTYUGH
                && dungeonMazeModel.getState() != GameState.WIN) {
            dungeonView.showMessage("Otyugh Alert!", "You are standing on a dead Otyugh!");
        } else if (obstacleType == ObstacleType.DEAD_OTYUGH
                && dungeonMazeModel.getState() == GameState.WIN) {
            dungeonView.showMessage("You Win!", "You have reached the end on the Dungeon!");
        } else if (obstacleType == ObstacleType.PIT) {
            dungeonView.showMessage("Pit!", "You fell into a pit!");
        } else if (obstacleType == ObstacleType.INJURED_OTYUGH) {
            dungeonView.showMessage("Otyugh Alert!",
                    "There is an injured Otyugh in the cave, escape quick!");
        } else if (obstacleType == ObstacleType.THIEF) {
            dungeonView.showMessage("Thief Alert!",
                    "A thief ran away with all of your collected treasure!");
        }
    }

}
