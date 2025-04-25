package dungeon.view;

import java.io.IOException;

import dungeon.maze.DungeonGuiController;

/**
 * This class represents a mock view used for testing purposes, this implementation appends the
 * methods called to the appendable log in order to verify if the view's appropriate functions are
 * called from the controller or not. This is used only for testing and should not be used in the
 * src, the actual view has a lot more functionalities that are not present here.
 */
public class MockView implements DungeonView {

  private final Appendable log;

  /**
   * Constructs a mock view that is used only for testing purposes. This logs all the method calls
   * to the appendable that is provided as the constructor parameter to it.
   *
   * @param log the appendable log which maintains the record of method calls.
   */
  public MockView(Appendable log) {
    this.log = log;
  }

  @Override
  public void setupKeyboardListener(DungeonGuiController listener) {
    try {
      log.append("\n").append("keyboard listener setup called");
    } catch (IOException e) {
      // do nothing
    }
  }

  @Override
  public void setupMazeBoard(DungeonGuiController listener) {
    try {
      log.append("\n").append("maze board setup called");
    } catch (IOException e) {
      // do nothing
    }
  }

  @Override
  public void setupActionPanel(DungeonGuiController listener) {
    try {
      log.append("\n").append("action panel setup called");
    } catch (IOException e) {
      // do nothing
    }
  }

  @Override
  public void setupGameMenu(DungeonGuiController listener) {
    try {
      log.append("\n").append("game menu setup called");
    } catch (IOException e) {
      // do nothing
    }
  }

  @Override
  public void showMessage(String title, String message) {
    try {
      log.append("\n").append("title = ").append(title).append("\n").append("message = ")
              .append(message);
    } catch (IOException e) {
      // do nothing
    }
  }

  @Override
  public void makeVisible() {
    try {
      log.append("\n").append("make visible called");
    } catch (IOException e) {
      // do nothing
    }
  }

  @Override
  public void refresh() {
    try {
      log.append("\nrefresh");
    } catch (IOException e) {
      // do nothing
    }
  }

  @Override
  public void disposeView() {
    try {
      log.append("\n").append("dispose view called");
    } catch (IOException e) {
      // do nothing
    }
  }
}
