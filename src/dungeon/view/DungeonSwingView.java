package dungeon.view;

import java.awt.GridLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import dungeon.location.MovementDirection;
import dungeon.maze.DungeonGuiController;
import dungeon.maze.ReadonlyMaze;
import dungeon.view.components.ActionsPanel;
import dungeon.view.components.MazeBoard;
import dungeon.view.components.ScrollPanel;
import dungeon.view.components.SettingsMenu;

/**
 * This is an implementation of DungeonView interface that represents a Graphical User Interface
 * that is built using the Java Swing Library and the maze board comprises a grid layout where
 * each cell is a location in the dungeon, and it contains representation of whether there is an
 * arrow or a treasure present in the location or not. The user interacts only with the view and
 * the controller interacts with the model.
 */
public class DungeonSwingView extends JFrame implements DungeonView {

    public static final int MAIN_WIDTH = 1920;
    public static final int MAIN_HEIGHT = 1080;
    private final ReadonlyMaze readonlyMaze;
    private final ActionsPanel actionsPanel;
    private boolean takingJumpInput = false;
    private boolean takingShootInput = false;
    private boolean takingDistance = false;
    private MovementDirection shootDirection;

    /**
     * Constructs a Swing View that renders a JFrame of the Dungeon Game and adds the Dungeon to it,
     * along with the controls to interact with the elements present in the view to perform all write
     * operations to the model.
     *
     * @param readonlyMaze the readOnly variant of the model used to get the updated information.
     */
    public DungeonSwingView(ReadonlyMaze readonlyMaze) {
        super("Dungeon Maze");
        this.readonlyMaze = readonlyMaze;
        this.setSize(MAIN_WIDTH, MAIN_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        GridLayout layoutManager = new GridLayout(1, 2);
        this.setLayout(layoutManager);
        actionsPanel = new ActionsPanel(readonlyMaze);
        makeVisible();
        setResizable(false);
        shootDirection = null;
    }

    @Override
    public void setupGameMenu(DungeonGuiController listener) {
        setJMenuBar(setupJMenu(listener));
    }

    @Override
    public void setupMazeBoard(DungeonGuiController listener) {
        int cellWidth = ((MAIN_WIDTH / 2) / readonlyMaze.getNumberOfColumns());
        int cellHeight = ((MAIN_HEIGHT) / readonlyMaze.getNumberOfRows());
        if (readonlyMaze.getNumberOfRows() == readonlyMaze.getNumberOfColumns()) {
            cellHeight = ((MAIN_WIDTH / 2) / readonlyMaze.getNumberOfColumns());
        }

        MazeBoard mazeBoard = new MazeBoard(readonlyMaze, listener, cellWidth, cellHeight);
        ScrollPanel scrollPanel = new ScrollPanel(readonlyMaze, mazeBoard, cellWidth, cellHeight);

        add(scrollPanel);
    }

    @Override
    public void setupActionPanel(DungeonGuiController listener) {
        add(actionsPanel);
        actionsPanel.setupButtons(listener);
    }

    private JMenuBar setupJMenu(DungeonGuiController listener) {
        JMenuBar jMenuBar = new JMenuBar();
        JMenu gameMenu = new JMenu("Game");
        JMenuItem restartGame = gameMenu.add(new JMenuItem("Restart Game"));
        restartGame.addActionListener(l -> {
            listener.handleRestartGame(readonlyMaze.getNumberOfRows(),
                    readonlyMaze.getNumberOfColumns(), readonlyMaze.getDegreeOfInterconnectivity(),
                    readonlyMaze.isWrapping(), readonlyMaze.getTreasurePercentage(),
                    readonlyMaze.getDifficulty(), readonlyMaze.getNumberOfPits(),
                    readonlyMaze.getNumberOfThieves(), readonlyMaze.hasMovingMonster());
        });

        JMenuItem resetThisGame = gameMenu.add(new JMenuItem("Reset Game"));
        resetThisGame.addActionListener(l -> {
            listener.handleResetGame();
        });


        JMenuItem exitButton = new JMenuItem("Exit");
        exitButton.addActionListener(l -> dispose());

        gameMenu.add(exitButton);
        jMenuBar.add(gameMenu);

        JMenu settingsMenu = new JMenu("Settings");

        JMenuItem numberOfRowsItem = new JMenuItem("Change Game Settings");
        numberOfRowsItem.addActionListener(l -> {
            new SettingsMenu(readonlyMaze.getNumberOfRows(), readonlyMaze.getNumberOfColumns(),
                    readonlyMaze.getDegreeOfInterconnectivity(), readonlyMaze.getTreasurePercentage(),
                    readonlyMaze.getDifficulty(), readonlyMaze.isWrapping(),
                    readonlyMaze.getNumberOfPits(), readonlyMaze.getNumberOfThieves(),
                    readonlyMaze.hasMovingMonster(), listener
            );
        });
        settingsMenu.add(numberOfRowsItem);

        jMenuBar.add(settingsMenu);

        return jMenuBar;
    }

    @Override
    public void makeVisible() {
        setVisible(true);
    }

    @Override
    public void refresh() {
        repaint();
        setFocusable(true);
        this.requestFocus();
    }

    @Override
    public void showMessage(String title, String message) {
        JOptionPane.showMessageDialog(this, message, title, JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void setupKeyboardListener(DungeonGuiController listener) {
        KeyAdapter keyAdapter = new KeyAdapter() {

            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                int key = e.getKeyCode();

                if (takingDistance) {
                    try {
                        listener.handleShootArrow(Integer.parseInt(Character.toString(e.getKeyChar())),
                                shootDirection);
                        takingDistance = false;
                        takingShootInput = false;
                    } catch (NumberFormatException numberFormatException) {
                        showMessage("Shoot Distance", "Enter a number for shoot distance");
                    }
                }

                if (key == KeyEvent.VK_LEFT) {
                    if (takingShootInput) {
                        shootDirection = MovementDirection.WEST;
                        takingDistance = true;
                    } else if (takingJumpInput) {
                        takingJumpInput = false;
                        listener.jumpPlayer(MovementDirection.WEST);
                    } else {
                        listener.movePlayer(MovementDirection.WEST);
                    }
                }

                if (key == KeyEvent.VK_RIGHT) {
                    if (takingShootInput) {
                        shootDirection = MovementDirection.EAST;
                        takingDistance = true;
                    } else if (takingJumpInput) {
                        takingJumpInput = false;
                        listener.jumpPlayer(MovementDirection.EAST);
                    } else {
                        listener.movePlayer(MovementDirection.EAST);
                    }
                }

                if (key == KeyEvent.VK_UP) {
                    if (takingShootInput) {
                        shootDirection = MovementDirection.NORTH;
                        takingDistance = true;
                    } else if (takingJumpInput) {
                        takingJumpInput = false;
                        listener.jumpPlayer(MovementDirection.NORTH);
                    } else {
                        listener.movePlayer(MovementDirection.NORTH);
                    }
                }

                if (key == KeyEvent.VK_DOWN) {
                    if (takingShootInput) {
                        shootDirection = MovementDirection.SOUTH;
                        takingDistance = true;
                    } else if (takingJumpInput) {
                        takingJumpInput = false;
                        listener.jumpPlayer(MovementDirection.SOUTH);
                    } else {
                        listener.movePlayer(MovementDirection.SOUTH);
                    }
                }

                if (key == KeyEvent.VK_A) {
                    listener.handleCollectArrow();
                }

                if (key == KeyEvent.VK_C) {
                    takingShootInput = true;
                }

                if (key == KeyEvent.VK_S) {
                    listener.handleCollectSapphires();
                }

                if (key == KeyEvent.VK_D) {
                    listener.handleCollectDiamonds();
                }

                if (key == KeyEvent.VK_R) {
                    listener.handleCollectRubies();
                }

                if (key == KeyEvent.VK_J) {
                    takingJumpInput = true;
                }

                if (key == KeyEvent.VK_P) {
                    listener.handlePlayerPunch();
                }
            }
        };

        this.addKeyListener(keyAdapter);
    }

    @Override
    public void disposeView() {
        this.dispose();
    }
}
