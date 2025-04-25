package dungeon.view.components;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import dungeon.location.Location;
import dungeon.location.MovementDirection;
import dungeon.maze.DungeonGuiController;
import dungeon.maze.ReadonlyMaze;

/**
 * This represents a single cell in the maze which can either be a cave or a tunnel, it displays an
 * overview of the elements present in the location that it represents. The maze location displays
 * if there is an arrow in the location or not, if there is a single or multiple treasures available
 * in the location or not by either showing a chest or not showing it at all. It also shows the
 * moving monster moving through the maze. For a more detailed view that shows exactly what
 * treasures are present and the monster that the player is facing there is a First Person View also
 * available.
 */
public class DungeonMazeLocation extends JPanel {

    private final ReadonlyMaze model;
    private final int cellWidth;
    private final int cellHeight;
    private Location location;
    private BufferedImage locationImage;

    /**
     * Constructs a cell in the maze grid that houses a single location of the maze in order to
     * represent the contents of the location.
     *
     * @param location   the Location object that is to be represented in the cell.
     * @param model      the readOnly variant of the model used to get the updated information.
     * @param listener   the Gui Controller that is used to interact with the model.
     * @param cellWidth  the width of which this cell is to be constructed.
     * @param cellHeight the height of which this cell is to be constructed.
     */
    public DungeonMazeLocation(Location location, ReadonlyMaze model, DungeonGuiController listener,
                               int cellWidth, int cellHeight) {
        if (location == null || model == null || listener == null) {
            throw new IllegalArgumentException("Location, Model or Listener should not be null");
        }

        if (cellHeight <= 0 || cellWidth <= 0) {
            throw new IllegalArgumentException("Cell Height and Cell Width cannot be less "
                    + "than or equal to 0");
        }

        this.location = location;
        this.model = model;
        this.cellWidth = cellWidth;
        this.cellHeight = cellHeight;
        setSize(cellWidth, cellHeight);
        setMaximumSize(new Dimension(cellWidth, cellHeight));
        setMinimumSize(new Dimension(cellWidth, cellHeight));
        setPreferredSize(new Dimension(cellWidth, cellHeight));
        setupMouseClickListener(listener);
    }

    private void setupMouseClickListener(DungeonGuiController listener) {
        MouseAdapter mouseAdapter = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (model.getPlayerCurrentLocation().equals(location)) {
                    int x = e.getX();
                    int y = e.getY();

                    MovementDirection moveTo
                            = model.getPlayerCurrentLocation().getOpenings().iterator().next();

                    if (x > 0 && x < cellWidth / 5 && y > cellHeight / 5 && y < 4 * (cellHeight / 5)) {
                        moveTo = MovementDirection.WEST;
                    } else if (x < cellWidth && y > cellHeight / 5 && x > 4 * (cellWidth / 5)
                            && y < 4 * (cellHeight / 5)) {
                        moveTo = MovementDirection.EAST;
                    } else if (y > 0 && y < cellHeight / 5 && x > cellWidth / 5 && x < 4 * (cellWidth / 5)) {
                        moveTo = MovementDirection.NORTH;
                    } else if (y < cellHeight && x > cellWidth / 5 && y > 4 * (cellHeight / 5)
                            && x < 4 * (cellWidth / 5)) {
                        moveTo = MovementDirection.SOUTH;
                    }

                    listener.movePlayer(moveTo);
                }
            }
        };

        addMouseListener(mouseAdapter);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        location = model.getMazeMap()[location.getAddress().getRowNumber()]
                [location.getAddress().getColumnNumber()];

        try {
            if (model.getVisitedLocations().contains(location.getAddress())) {
                drawLocation(g);
            } else {
                InputStream imageStream = getClass().getResourceAsStream("/black.png");
                if (imageStream != null) {
                    locationImage = ImageIO.read(imageStream);
                }
                g.drawImage(locationImage, 0, 0, cellWidth, cellHeight, null);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void drawLocation(Graphics g) throws IOException {
        InputStream imageStream = getClass().getResourceAsStream("/"
                + getDirectionImageName(location.getOpenings()));
        if (imageStream != null) {
            locationImage = ImageIO.read(imageStream);
        }
        g.drawImage(locationImage, 0, 0, cellWidth, cellHeight, null);

        if (location.isHasArrow()) {
            drawImageAsOverlay(g, "arrow.png");
        }

        if (location.getTreasures() != null && !location.getTreasures().isEmpty()) {
            drawImageAsOverlay(g, "treasure.png");
        }

        if (location.getAddress().equals(model.getPlayerCurrentLocation().getAddress())
                && location.getPit() == null) {
            drawImageAsOverlay(g, "player.png");
            int smellLevel = model.detectSmell();
            if (smellLevel == 2) {
                drawImageAsOverlay(g, "terrible_smell.png");
            } else if (smellLevel == 1) {
                drawImageAsOverlay(g, "bad_smell.png");
            }
            int windLevel = model.detectWindLevel();
            if (windLevel != 0) {
                drawImageAsOverlay(g, "high_wind.png");
            }
        } else if (location.getPit() != null) {
            drawImageAsOverlay(g, "pit_maze.png");
        } else if (location.getThief() != null) {
            drawImageAsOverlay(g, "thief_maze.png");
        }

        if (location.getAddress().getRowNumber() == (model.getEndRow())
                && location.getAddress().getColumnNumber() == (model.getEndColumn())) {
            drawImageAsOverlay(g, "endflag.png");
        }

        if (model.hasMovingMonster() && location.getAddress().equals(model.getBerbalangLocation())) {
            String imageName = "berbalang.png";
            if (model.getBerbalangHealth() == 100) {
                imageName = "berbalang.png";
            } else if (model.getBerbalangHealth() > 75) {
                imageName = "injured_1_berbalang.png";
            } else if (model.getBerbalangHealth() > 50) {
                imageName = "injured_2_berbalang.png";
            } else if (model.getBerbalangHealth() > 0) {
                imageName = "injured_3_berbalang.png";
            } else if (model.getBerbalangHealth() == 0) {
                imageName = "dead_berbalang.png";
            }
            drawImageAsOverlay(g, imageName);
        }

        if (location.getOtyugh() != null && location.getOtyugh().getHealth() == 100) {
            drawImageAsOverlay(g, "otyugh_maze.png");
        } else if (location.getOtyugh() != null && location.getOtyugh().getHealth() == 50) {
            drawImageAsOverlay(g, "injured_otyugh.png");
        } else if (location.getOtyugh() != null) {
            drawImageAsOverlay(g, "dead_otyugh.png");
        }
    }

    private void drawImageAsOverlay(Graphics g, String imageName) throws IOException {
        locationImage = overlay(locationImage, "/" + imageName);
        g.drawImage(locationImage, 0, 0, cellWidth, cellHeight, null);
    }

    private String getDirectionImageName(Set<MovementDirection> movementDirections) {
        StringBuilder imageNameBuilder = new StringBuilder();
        movementDirections.forEach(direction ->
                imageNameBuilder.append(direction.toString().charAt(0)));
        imageNameBuilder.append(".png");
        return imageNameBuilder.toString();
    }

    private BufferedImage overlay(BufferedImage starting, String fpath) throws IOException {
        InputStream imageStream = getClass().getResourceAsStream(fpath);
        BufferedImage overlay = null;
        if (imageStream != null) {
            overlay = ImageIO.read(imageStream);
        }
        BufferedImage combined = new BufferedImage(starting.getWidth(), starting.getHeight(),
                BufferedImage.TYPE_INT_ARGB);
        Graphics g = combined.getGraphics();
        g.drawImage(starting, 0, 0, null);
        g.drawImage(overlay, 0, 0, starting.getWidth(), starting.getHeight(), null);
        return combined;
    }

}
