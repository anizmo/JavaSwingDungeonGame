package dungeon.maze;

/**
 * This enum represents the state of the play, in a game state manager it is important to have a
 * track of a definite game state according to which one can bind the data to UI or control their
 * flow. The game state keeps a track of what state the game currently is in, the game starts with
 * PLAYING stage where the player is still at the starting location of the maze, and then changes to
 * playing state when the player is neither at the starting position nor at the end location, when
 * the player reaches the end location, the game goes to WIN state which signifies that the
 * player has reached the destination and the game has ended. If the player decides to quit the game
 * in between then the game state changes to QUIT. If the player is eaten the by the Monster then
 * the game state changes to LOSE.
 */
public enum GameState {
    PLAYING, WIN, LOSE, QUIT
}
