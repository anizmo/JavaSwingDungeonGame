package dungeon.view.components;

import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import dungeon.maze.DungeonGuiController;
import dungeon.maze.ReadonlyMaze;

/**
 * This class represents the panel that encapsulates the action buttons which perform the tasks of
 * picking up specific treasures, picking up arrows along with the health indicators and the punch
 * button that is used to fight the moving monster, that is the Berbalang. This panel is located at
 * the lower right half of the screen.
 */
public class ActionsPanel extends JPanel {

    private final ReadonlyMaze model;

    /**
     * Constructs an Action Panel at the mid right of the screen. The action panel is an important
     * aspect of the UI as it house all the interaction of the player with the dungeon and the
     * obstacles.
     *
     * @param readonlyMaze the readOnly variant of the model used to get the updated information.
     */
    public ActionsPanel(ReadonlyMaze readonlyMaze) {
        if (readonlyMaze == null) {
            throw new IllegalArgumentException("Model Cannot be null");
        }
        this.model = readonlyMaze;
        JLabel indicator = new JLabel("Welcome to the Dungeon!");
        indicator.setFont(new Font("Bold Font", Font.BOLD, 12));
        setLayout(new GridLayout(2, 1));
        JPanel roomImage = new LocationFirstPersonView(readonlyMaze);
        this.add(roomImage);
    }


    /**
     * Sets up interaction buttons to interact with model to perform write operations from the view.
     * Takes in a controller as the listener to perform interaction.
     *
     * @param listener the gui controller to interact indirectly with the model.
     */
    public void setupButtons(DungeonGuiController listener) {

        JPanel statusPanel = new JPanel();
        statusPanel.setLayout(new GridLayout(4, 1));

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridLayout(1, 4));

        JButton pickupArrow = new JButton("Pickup Arrow");

        buttonsPanel.add(pickupArrow);

        JButton pickupDiamond = new JButton("Pickup Diamond");

        buttonsPanel.add(pickupDiamond);

        JButton pickupRuby = new JButton("Pickup Ruby");

        buttonsPanel.add(pickupRuby);

        JButton pickupSapphire = new JButton("Pickup Sapphire");

        buttonsPanel.add(pickupSapphire);

        pickupArrow.addActionListener(e -> listener.handleCollectArrow());

        pickupDiamond.addActionListener(e -> listener.handleCollectDiamonds());

        pickupRuby.addActionListener(e -> {
            listener.handleCollectRubies();
        });

        pickupSapphire.addActionListener(e -> {
            listener.handleCollectSapphires();
        });

        statusPanel.add(buttonsPanel);

        statusPanel.add(new InventoryPanel(model));
        statusPanel.add(new BattleStatusPanel(model));

        JPanel fightPanel = new JPanel();
        JButton punchButton = new JButton("Punch");
        punchButton.addActionListener(l -> listener.handlePlayerPunch());
        fightPanel.setLayout(new GridLayout(1, 3));

        fightPanel.add(new JLabel(""));
        fightPanel.add(punchButton);
        fightPanel.add(new JLabel(""));
        statusPanel.add(fightPanel);

        this.add(statusPanel);
    }

}
