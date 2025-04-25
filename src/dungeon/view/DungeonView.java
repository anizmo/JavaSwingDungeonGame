package dungeon.view;

import dungeon.maze.DungeonGuiController;

/**
 * The DungeonView is a GUI based view that shows the output of the model and the controller by
 * means of a graphical user interface and takes in input through the keyboard and the mouse,
 * providing appropriate output to the end user.
 */
public interface DungeonView {

    /**
     * Sets up the keyboard event listening to listen to keyboard interactions from the user and pass
     * it on to the model.
     *
     * @param listener the Gui Controller that is used to interact with the model.
     */
    void setupKeyboardListener(DungeonGuiController listener);

    /**
     * Adds a maze board to the view with the interaction listener in order for the view to interact
     * with the controller and write variant of the model indirectly.
     *
     * @param listener the Gui Controller that is used to interact with the model.
     */
    void setupMazeBoard(DungeonGuiController listener);

    /**
     * Adds an action panel to the view that is used to perform the action interaction of the view
     * with the controller and indirectly the model.
     *
     * @param listener the Gui Controller that is used to interact with the model.
     */
    void setupActionPanel(DungeonGuiController listener);

    /**
     * Adds a game menu to the view for providing options such as start new game, restart same game
     * and change settings and start a new game.
     *
     * @param listener the Gui Controller that is used to interact with the model.
     */
    void setupGameMenu(DungeonGuiController listener);

    /**
     * Shows a messaging to the end user in an appropriate way, this is dependent on the
     * implementation as there can be a number of ways to show messaging based on different libraries
     * and the type of framework and compoenents used.
     *
     * @param title   the title of the message.
     * @param message the message to be displayed.
     */
    void showMessage(String title, String message);

    /**
     * Shows the view window by calling the setVisible of the JFrame that is extended by the view. A
     * call to this method is required in order to render the view.
     */
    void makeVisible();

    /**
     * Refreshes the view which calls the paintComponent method of every component present in the
     * view.
     */
    void refresh();

    /**
     * Disposes the current view by closing it.
     */
    void disposeView();

}
