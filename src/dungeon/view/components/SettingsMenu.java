package dungeon.view.components;

import java.awt.Checkbox;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import dungeon.maze.DungeonGuiController;

/**
 * The Settings Menu is a window that pops up when the user needs to start a game with new settings
 * or even with the existing settings but a new game with a new dungeon of the same configuration
 * but different random parameters.
 */
public class SettingsMenu extends JFrame {

    private final JTextField numberOfRowsField;
    private final JTextField numberOfColumnsField;
    private final JTextField degreeOfInterconnectivityField;
    private final JTextField difficultyField;
    private final JTextField treasurePercentageField;
    private final JTextField numberOfThievesField;
    private final JTextField numberOfPitsField;
    private final Checkbox dungeonWrappingField;
    private final Checkbox movingMonsterField;
    private final JButton startGameButton;
    private final JButton cancelButton;
    private final DungeonGuiController listener;

    /**
     * Creates a settings menu that lets the user create a new game with the desired settings as long
     * as it does not violate any constraints of the dungeon.
     *
     * @param numberOfRows              the number of rows in the maze.
     * @param numberOfColumns           the number of columns in the maze.
     * @param degreeOfInterconnectivity the degree of connectivity of the maze.
     * @param treasurePercentage        the minimum percentage of caves that need to have treasures.
     * @param difficulty                the number of monsters in the maze.
     * @param wrapping                  whether the maze is wrapping or not.
     * @param numberOfPits              the number of pits to be inserted in the dungeon.
     * @param numberOfThieves           the number of thieves to be inserted in the dungeon.
     * @param hasMovingMonster          whether the dungeon would have a berbalang or not.
     * @param listener                  the gui controller to interact indirectly with the model.
     */
    public SettingsMenu(int numberOfRows, int numberOfColumns, int degreeOfInterconnectivity,
                        int treasurePercentage, int difficulty, boolean wrapping, int numberOfPits,
                        int numberOfThieves, boolean hasMovingMonster,
                        DungeonGuiController listener) {
        super("Change Game Settings");
        if (listener == null) {
            throw new IllegalArgumentException("A Dungeon Controller Listener is required.");
        }
        numberOfRowsField = new JTextField(String.valueOf(numberOfRows));
        numberOfColumnsField = new JTextField(String.valueOf(numberOfColumns));
        degreeOfInterconnectivityField = new JTextField(String.valueOf(degreeOfInterconnectivity));
        treasurePercentageField = new JTextField(String.valueOf(treasurePercentage));
        difficultyField = new JTextField(String.valueOf(difficulty));
        this.numberOfThievesField = new JTextField(String.valueOf(numberOfThieves));
        this.numberOfPitsField = new JTextField(String.valueOf(numberOfPits));

        dungeonWrappingField = new Checkbox("Dungeon Wrapping", wrapping);
        movingMonsterField = new Checkbox("Moving Monster", hasMovingMonster);
        startGameButton = new JButton("Start Game");

        startGameButton.addActionListener(l -> {
            this.dispose();
            validateFields(numberOfRowsField.getText(),
                    numberOfColumnsField.getText(), degreeOfInterconnectivityField.getText(),
                    dungeonWrappingField.getState(), treasurePercentageField.getText(),
                    difficultyField.getText(), this.numberOfPitsField.getText(),
                    numberOfThievesField.getText(), movingMonsterField.getState());
        });

        cancelButton = new JButton("Exit Menu");
        this.listener = listener;
        setLayout(new GridLayout(5, 4, 10, 10));
        populateGrid();
        pack();
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void validateFields(String numberOfRowsFieldText, String numberOfColumnsFieldText,
                                String degreeOfInterconnectivityFieldText, boolean wrapping,
                                String treasurePercentageFieldText, String difficultyFieldText,
                                String numberOfPitsFieldText, String numberOfThievesFieldText,
                                boolean hasMovingMonster) {
        try {
            int noOfRows = Integer.parseInt(numberOfRowsFieldText);
            int noOfColumns = Integer.parseInt(numberOfColumnsFieldText);
            int degreeOfInterconnectivity = Integer.parseInt(degreeOfInterconnectivityFieldText);
            int treasurePercentage = Integer.parseInt(treasurePercentageFieldText);
            int difficulty = Integer.parseInt(difficultyFieldText);
            int numberOfPits = Integer.parseInt(numberOfPitsFieldText);
            int numberOfThieves = Integer.parseInt(numberOfThievesFieldText);

            listener.handleRestartGame(noOfRows, noOfColumns, degreeOfInterconnectivity, wrapping,
                    treasurePercentage, difficulty, numberOfPits, numberOfThieves, hasMovingMonster);
        } catch (NumberFormatException numberFormatException) {
            JOptionPane.showMessageDialog(this, "All the fields should be numeric",
                    "Invalid Input", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException illegalArgumentException) {
            JOptionPane.showMessageDialog(this, illegalArgumentException.getLocalizedMessage(),
                    "Error Creating Dungeon", JOptionPane.ERROR_MESSAGE);
        }
    }


    private void populateGrid() {
        add(new JLabel("Number of Rows"));
        add(numberOfRowsField);
        add(new JLabel("Number of Columns"));
        add(numberOfColumnsField);
        add(new JLabel("Degree of Interconnectivity"));
        add(degreeOfInterconnectivityField);
        add(new JLabel("Difficulty"));
        add(difficultyField);
        add(new JLabel("Treasure Percentage"));
        add(treasurePercentageField);
        add(new JLabel("Number of Thieves"));
        add(numberOfThievesField);
        add(new JLabel("Number of Pits"));
        add(numberOfPitsField);
        add(dungeonWrappingField);
        add(movingMonsterField);
        add(new JLabel());
        cancelButton.addActionListener(l -> dispose());
        add(startGameButton);
        add(cancelButton);
        add(new JLabel());
    }


}
