package dungeon.view.components;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JPanel;

import dungeon.maze.DungeonGuiController;
import dungeon.maze.ReadonlyMaze;

/**
 * This is the board on which the dungeon is rendered, it is a grid layout and each of the cell
 * of the grid is a DungeonMazeLocation that houses the location's representation. This view is
 * further encapsulated in a Scroll Panel which makes it better accessible in terms of readability
 * of the view.
 */
public class MazeBoard extends JPanel {

    private final ReadonlyMaze model;
    private final DungeonGuiController listener;
    private final int numberOfRows;
    private final int numberOfColumns;
    private final int cellWidth;
    private final int cellHeight;

    /**
     * Constructs a maze board that draws the dungeon as a grid. Each cell in the grid layout is a
     * location of the dungeon.
     *
     * @param model      the readOnly variant of the model used to get the updated information.
     * @param listener   the Gui Controller that is used to interact with the model.
     * @param cellWidth  the width of which this cell is to be constructed.
     * @param cellHeight the height of which this cell is to be constructed.
     */
    public MazeBoard(ReadonlyMaze model, DungeonGuiController listener, int cellWidth,
                     int cellHeight) {
        if (model == null || listener == null) {
            throw new IllegalArgumentException("Model or Listener should not be null");
        }

        if (cellHeight <= 0 || cellWidth <= 0) {
            throw new IllegalArgumentException("Cell Height and Cell Width cannot be less "
                    + "than or equal to 0");
        }
        this.model = model;
        this.listener = listener;
        this.numberOfRows = model.getNumberOfRows();
        this.numberOfColumns = model.getNumberOfColumns();
        this.cellWidth = cellWidth;
        this.cellHeight = cellHeight;
        setupGrid();
    }

    private void setupGrid() {
        GridLayout gridLayout = new GridLayout(numberOfRows, numberOfColumns);
        setLayout(gridLayout);
        setVisible(true);
        setPreferredSize(new Dimension(numberOfColumns * cellWidth,
                numberOfRows * cellHeight));

        for (int i = 0; i < model.getMazeMap().length; i++) {
            for (int j = 0; j < model.getMazeMap()[i].length; j++) {
                DungeonMazeLocation dungeonMazeLocation = new DungeonMazeLocation(model.getMazeMap()[i][j],
                        model, listener, cellWidth, cellHeight);
                dungeonMazeLocation.setSize(cellWidth, cellHeight);
                add(dungeonMazeLocation);
            }
        }
    }

}
