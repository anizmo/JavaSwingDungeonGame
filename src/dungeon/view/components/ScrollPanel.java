package dungeon.view.components;

import java.awt.Dimension;

import javax.swing.JScrollPane;

import dungeon.maze.ReadonlyMaze;

import static dungeon.view.DungeonSwingView.MAIN_HEIGHT;
import static dungeon.view.DungeonSwingView.MAIN_WIDTH;

/**
 * This class represents the scroll view that houses the maze board and in it the dungeon is dumped
 * visually, it only shows the locations that have been visited by the player and rest of them
 * remain dark until they are explored.
 */
public class ScrollPanel extends JScrollPane {

    /**
     * Constructs a scroll panel that the user can scroll to look around the explored locations.
     *
     * @param readonlyMaze the readOnly variant of the model used to get the updated information.
     * @param mazeBoard    the maze board which is to be scrolled that houses the dungeon.
     * @param cellWidth    the width of which this cell is to be constructed.
     * @param cellHeight   the height of which this cell is to be constructed.
     */
    public ScrollPanel(ReadonlyMaze readonlyMaze, MazeBoard mazeBoard, int cellWidth,
                       int cellHeight) {
        super(mazeBoard);
        setSize(MAIN_WIDTH / 2, MAIN_HEIGHT);
        setMaximumSize(new Dimension(MAIN_WIDTH / 2, MAIN_HEIGHT));
        setMinimumSize(new Dimension(MAIN_WIDTH / 2, MAIN_HEIGHT));
        this.setPreferredSize(new Dimension(readonlyMaze.getNumberOfColumns() * cellWidth,
                readonlyMaze.getNumberOfRows() * cellHeight));
    }

}
