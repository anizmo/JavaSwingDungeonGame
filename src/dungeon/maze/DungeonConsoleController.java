package dungeon.maze;

import java.io.IOException;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Scanner;
import java.util.Set;

import dungeon.location.Collectible;
import dungeon.location.Location;
import dungeon.location.MovementDirection;

/**
 * The DungeonConsoleController controls the DungeonMaze model to play the game and take input from
 * the user as a Readable and provides the output of the game as Appendable. Here, the player can
 * press Q at any time to exit the game or even enter the commands in the sequence of execution of
 * the input to get the desired output and play the game. The game keeps on playing until the model
 * is in PLAYING state, post that it shows the end result as either a win, a loss or quit.
 */
public class DungeonConsoleController implements DungeonController {

    private final Appendable out;
    private final Scanner scan;

    /**
     * Constructs a DungeonConsoleController that requires an input and an output. The input can be
     * given by any form of Readable such as a StringReader, ByteReader, etc. and the output can be
     * given as an appendable which can be used by the scanner class.
     *
     * @param in  the input reader.
     * @param out the output appendable.
     */
    public DungeonConsoleController(Readable in, Appendable out) {
        if (in == null || out == null) {
            throw new IllegalArgumentException("Readable and Appendable can't be null");
        }
        this.scan = new Scanner(in);
        this.out = out;
    }

    @Override
    public void playGame(Maze dungeonMazeModel) {
        if (dungeonMazeModel == null) {
            throw new IllegalArgumentException("Dungeon Model cannot be null");
        }
        Objects.requireNonNull(dungeonMazeModel);

        try {
            out.append("Welcome to the Dungeon!").append("\n")
                    .append("You can quit anytime by entering 'Q'").append("\n\n");

            while (dungeonMazeModel.getState().equals(GameState.PLAYING)) {
                Location playerCurrentLocation = dungeonMazeModel.getPlayerCurrentLocation();
                checkCurrentLocationStatus(dungeonMazeModel, playerCurrentLocation);

                String input = scan.next();

                switch (input) {
                    case "M":
                    case "m":
                        movePlayer(dungeonMazeModel);
                        break;

                    case "P":
                    case "p":
                        pickup(dungeonMazeModel);
                        break;

                    case "S":
                    case "s":
                        shootArrow(dungeonMazeModel);
                        break;

                    case "Q":
                    case "q":
                    case "quit":
                    case "QUIT":
                        dungeonMazeModel.quit();
                        break;

                    default:
                        out.append("Invalid Input, try again.").append("\n");
                }

                if (dungeonMazeModel.getPlayerCurrentLocation().getOtyugh() != null
                        && !dungeonMazeModel.getPlayerCurrentLocation().getOtyugh().isAlive()) {
                    out.append("\n").append("You look around and find yourself standing on a dead Otyugh!")
                            .append("\n");
                } else if (dungeonMazeModel.getPlayerCurrentLocation().getOtyugh() != null
                        && dungeonMazeModel.getPlayerCurrentLocation().getOtyugh().isAlive()
                        && dungeonMazeModel.getPlayerCurrentLocation().getOtyugh().getHealth() < 100) {
                    out.append("\n").append("There is an injured Otyugh in the cave, you can still escape!")
                            .append("\n");
                    out.append("Move quickly!");
                }

                out.append("\n");

            }

            if (dungeonMazeModel.getState().equals(GameState.WIN)) {
                out.append("You have successfully reached the end of the dungeon!").append("\n");
                out.append("You Win!").append("\n");
            } else if (dungeonMazeModel.getState().equals(GameState.LOSE)) {
                out.append("Chomp, chomp, chomp, you are eaten by an Otyugh!").append("\n");
                out.append("Better luck next time!").append("\n");
            } else if (dungeonMazeModel.getState().equals(GameState.QUIT)) {
                out.append("You quit the game, come back again to fight the Otyughs!");
            }

            out.append("\n").append("Your stats are as follows - ")
                    .append(dungeonMazeModel.getPlayerDetails().toLowerCase(Locale.ROOT)
                            .replace("player ", ""));

        } catch (NoSuchElementException e) {
            dungeonMazeModel.quit();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void checkCurrentLocationStatus(Maze dungeonMazeModel, Location playerCurrentLocation)
            throws IOException {
        if (dungeonMazeModel.detectSmell() == 1) {
            out.append("You smell something bad").append("\n");
        } else if (dungeonMazeModel.detectSmell() >= 2) {
            out.append("You smell something terrible nearby").append("\n");
        }
        out.append("You are in a ")
                .append(playerCurrentLocation.getType().toString().toLowerCase(Locale.ROOT))
                .append("\n");

        if (!playerCurrentLocation.getTreasures().isEmpty()) {
            out.append("You find ")
                    .append(convertSetToReadableString(playerCurrentLocation.getTreasures()))
                    .append("\n");
        }

        if (playerCurrentLocation.isHasArrow()) {
            out.append("You find an arrow here").append("\n");
        }

        out.append("Doors lead to the ")
                .append(convertSetToReadableString(playerCurrentLocation.getOpenings()));

        out.append("\n");

        out.append("Move, Pickup, or Shoot (M-P-S)?").append("\n");
    }

    private void pickup(Maze dungeonMazeModel) {
        try {
            out.append("Pickup what? (rubies/diamonds/sapphires/arrow)").append("\n");
            String pickupOption = scan.next();

            if (pickupOption.equalsIgnoreCase("diamonds")
                    || pickupOption.equalsIgnoreCase("d")) {
                if (dungeonMazeModel.getPlayerCurrentLocation()
                        .getTreasures().contains(Collectible.DIAMONDS)) {
                    dungeonMazeModel.pickup(Collectible.DIAMONDS);
                    out.append("You picked up diamonds").append("\n");
                } else {
                    out.append("There are no diamonds in the player's current location").append("\n");
                }
            } else if (pickupOption.equalsIgnoreCase("rubies")
                    || pickupOption.equalsIgnoreCase("r")) {
                if (dungeonMazeModel.getPlayerCurrentLocation()
                        .getTreasures().contains(Collectible.RUBIES)) {
                    dungeonMazeModel.pickup(Collectible.RUBIES);
                    out.append("You picked up rubies").append("\n");
                } else {
                    out.append("There are no rubies in the your current location").append("\n");
                }
            } else if (pickupOption.equalsIgnoreCase("sapphires")
                    || pickupOption.equalsIgnoreCase("s")) {
                if (dungeonMazeModel.getPlayerCurrentLocation()
                        .getTreasures().contains(Collectible.SAPPHIRES)) {
                    dungeonMazeModel.pickup(Collectible.SAPPHIRES);
                    out.append("You picked up sapphires").append("\n");
                } else {
                    out.append("There are no sapphires in your current location").append("\n");
                }
            } else if (pickupOption.equalsIgnoreCase("arrow")
                    || pickupOption.equalsIgnoreCase("a")) {
                if (dungeonMazeModel.getPlayerCurrentLocation().isHasArrow()) {
                    dungeonMazeModel.pickup(Collectible.ARROW);
                    out.append("You picked up an arrow").append("\n");
                    out.append("You now have ").append(String.valueOf(dungeonMazeModel.getNumberOfArrows()))
                            .append(" arrows with you").append("\n");
                } else {
                    out.append("There is no arrow in this location");
                }
            } else if (pickupOption.equalsIgnoreCase("Q")
                    || pickupOption.equalsIgnoreCase("quit")) {
                dungeonMazeModel.quit();
            } else {
                out.append("Invalid Input, try again.").append("\n");
                pickup(dungeonMazeModel);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void movePlayer(Maze dungeonMazeModel) {
        try {
            out.append("Which direction?").append("\n");
            String direction = scan.next();

            if (direction.equalsIgnoreCase("Q")
                    || direction.equalsIgnoreCase("quit")) {
                dungeonMazeModel.quit();
            } else if (stringToDirection(direction) != null) {
                dungeonMazeModel.movePlayer(stringToDirection(direction));
            } else {
                out.append("Invalid Direction, Try Again.").append("\n");
                movePlayer(dungeonMazeModel);
            }
        } catch (IllegalArgumentException | IOException exception) {
            try {
                if (exception instanceof IllegalArgumentException) {
                    out.append(exception.getLocalizedMessage());
                } else {
                    exception.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void shootArrow(Maze dungeonMazeModel) {
        try {
            out.append("Shoot in which direction?").append("\n");
            String input = scan.next();

            if (input.equalsIgnoreCase("Q")
                    || input.equalsIgnoreCase("quit")) {
                dungeonMazeModel.quit();
            } else if (stringToDirection(input) != null) {
                shootAtDistance(dungeonMazeModel, stringToDirection(input));
            } else {
                out.append("Invalid Direction, Try Again.").append("\n");
                shootArrow(dungeonMazeModel);
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
        } catch (IllegalStateException | IllegalArgumentException illegalArgumentException) {
            try {
                out.append(illegalArgumentException.getLocalizedMessage()).append("\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private MovementDirection stringToDirection(String input) {
        if (input.equalsIgnoreCase("N")
                || input.equalsIgnoreCase("north")) {
            return MovementDirection.NORTH;
        } else if (input.equalsIgnoreCase("S")
                || input.equalsIgnoreCase("south")) {
            return MovementDirection.SOUTH;
        } else if (input.equalsIgnoreCase("W")
                || input.equalsIgnoreCase("west")) {
            return MovementDirection.WEST;
        } else if (input.equalsIgnoreCase("E")
                || input.equalsIgnoreCase("east")) {
            return MovementDirection.EAST;
        } else {
            return null;
        }
    }

    private void shootAtDistance(Maze dungeonMazeModel, MovementDirection direction) {
        try {
            out.append("How far? (in number of caves)").append("\n");
            String input = scan.next();
            int distanceNumber = 0;
            if (input.equalsIgnoreCase("Q")) {
                dungeonMazeModel.quit();
            }
            try {
                distanceNumber = Integer.parseInt(input);
            } catch (NumberFormatException numberFormatException) {
                out.append("Please enter a number").append("\n");
                shootAtDistance(dungeonMazeModel, direction);
            }

            if (dungeonMazeModel.getNumberOfArrows() != 0) {
                out.append("An arrow is shot in the dark!").append("\n");
                if (dungeonMazeModel.shootArrow(distanceNumber, direction).isShotHit()) {
                    out.append("You hear a great howl in the distance!").append("\n");
                } else {
                    out.append("You do not hear anything").append("\n");
                }
                out.append("You now have ").append(String.valueOf(dungeonMazeModel.getNumberOfArrows()))
                        .append(" arrows with you").append("\n");
            } else {
                out.append("Cannot shoot because you have no arrows left!");
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    private <T> String convertSetToReadableString(Set<T> set) {
        return set.toString().replace("[", "")
                .replaceAll("]", "").toLowerCase(Locale.ROOT);
    }

}
