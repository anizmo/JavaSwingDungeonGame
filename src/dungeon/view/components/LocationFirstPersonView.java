package dungeon.view.components;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import dungeon.location.Collectible;
import dungeon.location.Location;
import dungeon.maze.ReadonlyMaze;
import dungeon.view.DungeonSwingView;

/**
 * This class represents a view that shows the first person view of the player, it shows the
 * specification of the player's current location and the collectibles as well as all the obstacles
 * present in it. The view is located at the top right of the main window and is updated at player's
 * each move.
 */
public class LocationFirstPersonView extends JPanel {

    private static final int ROOM_SIZE_WIDTH = DungeonSwingView.MAIN_WIDTH / 2;
    private final ReadonlyMaze model;
    private Location location;
    private BufferedImage locationImage;

    /**
     * Constructs a first person view that displays the player's current location's obstacles and
     * collectibles.
     *
     * @param model the readOnly variant of the model used to get the updated information.
     */
    public LocationFirstPersonView(ReadonlyMaze model) {
        if (model == null) {
            throw new IllegalArgumentException("Model Cannot be null");
        }
        this.model = model;
        setSize(new Dimension(ROOM_SIZE_WIDTH, ROOM_SIZE_WIDTH / 2));
        setPreferredSize(new Dimension(ROOM_SIZE_WIDTH, ROOM_SIZE_WIDTH / 2));
        setMinimumSize(new Dimension(ROOM_SIZE_WIDTH, ROOM_SIZE_WIDTH / 2));
        setMaximumSize(new Dimension(ROOM_SIZE_WIDTH, ROOM_SIZE_WIDTH / 2));
        this.location = model.getPlayerCurrentLocation();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.location = model.getPlayerCurrentLocation();
        try {
            drawLocation(g);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void drawLocation(Graphics g) throws IOException {
        InputStream imageStream = getClass().getResourceAsStream("/cavebg.png");
        if (imageStream != null) {
            locationImage = ImageIO.read(imageStream);
        }
        g.drawImage(locationImage, 0, 0, ROOM_SIZE_WIDTH, ROOM_SIZE_WIDTH / 2, null);
        if (location.isHasArrow()) {
            drawArrow(g);
        }

        if (!location.getTreasures().isEmpty()) {
            location.getTreasures().forEach(collectible -> {
                try {
                    drawTreasure(g, collectible);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }

        if (location.equals(model.getPlayerCurrentLocation())) {
            drawSmell(g, model);
        }

        if (location.equals(model.getPlayerCurrentLocation())) {
            drawWind(g, model);
        }

        if (location.getOtyugh() != null && location.getOtyugh().getHealth() == 100) {
            drawMonster(g);
        } else if (location.getOtyugh() != null && location.getOtyugh().getHealth() == 50) {
            drawInjuredMonster(g);
        } else if (location.getOtyugh() != null) {
            drawDeadMonster(g);
        }

        if (location.getPit() != null) {
            drawPit(g);
        }

        if (location.getThief() != null) {
            drawThief(g);
        }

        if (model.hasMovingMonster() && location.getAddress().equals(model.getBerbalangLocation())) {
            drawBerbalang(g, model);
        }

    }

    private void drawBerbalang(Graphics g, ReadonlyMaze model) throws IOException {
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

        drawImageOnFirstPersonView(g, imageName, ROOM_SIZE_WIDTH / 10,
                0, ROOM_SIZE_WIDTH / 3, ROOM_SIZE_WIDTH / 3);
    }

    private void drawPit(Graphics g) throws IOException {
        drawImageOnFirstPersonView(g, "pit_maze.png", ROOM_SIZE_WIDTH / 10,
                0, ROOM_SIZE_WIDTH / 3, ROOM_SIZE_WIDTH / 3);
    }

    private void drawThief(Graphics g) throws IOException {
        drawImageOnFirstPersonView(g, "thief_maze.png", ROOM_SIZE_WIDTH / 10,
                0, ROOM_SIZE_WIDTH / 3, ROOM_SIZE_WIDTH / 3);
    }

    private void drawMonster(Graphics g) throws IOException {
        drawImageOnFirstPersonView(g, "otyugh_maze.png", ROOM_SIZE_WIDTH / 10, 0,
                ROOM_SIZE_WIDTH / 3, ROOM_SIZE_WIDTH / 3);
    }

    private void drawInjuredMonster(Graphics g) throws IOException {
        drawImageOnFirstPersonView(g, "injured_otyugh.png", ROOM_SIZE_WIDTH / 10,
                0, ROOM_SIZE_WIDTH / 3, ROOM_SIZE_WIDTH / 3);
    }

    private void drawDeadMonster(Graphics g) throws IOException {
        drawImageOnFirstPersonView(g, "dead_otyugh.png", ROOM_SIZE_WIDTH / 10, 0,
                ROOM_SIZE_WIDTH / 3, ROOM_SIZE_WIDTH / 3);
        g.drawImage(locationImage, 0, 0, ROOM_SIZE_WIDTH, ROOM_SIZE_WIDTH / 2, null);
    }

    private void drawSmell(Graphics g, ReadonlyMaze model) throws IOException {
        int smellLevel = model.detectSmell();
        if (smellLevel == 2) {
            drawImageOnFirstPersonView(g, "terrible_smell.png", ROOM_SIZE_WIDTH / 10,
                    0, ROOM_SIZE_WIDTH / 3, ROOM_SIZE_WIDTH / 3);
        } else if (smellLevel == 1) {
            drawImageOnFirstPersonView(g, "bad_smell.png", ROOM_SIZE_WIDTH / 10, 0,
                    ROOM_SIZE_WIDTH / 3, ROOM_SIZE_WIDTH / 3);
        }
    }

    private void drawWind(Graphics g, ReadonlyMaze model) throws IOException {
        int windLevel = model.detectWindLevel();
        if (windLevel != 0) {
            drawImageOnFirstPersonView(g, "high_wind.png", ROOM_SIZE_WIDTH / 10,
                    0, ROOM_SIZE_WIDTH / 3, ROOM_SIZE_WIDTH / 3);
        }
    }

    private void drawArrow(Graphics g) throws IOException {
        drawImageOnFirstPersonView(g, "arrow.png", 140 + ROOM_SIZE_WIDTH / 20,
                10, ROOM_SIZE_WIDTH / 10, ROOM_SIZE_WIDTH / 10);
    }

    private void drawTreasure(Graphics g, Collectible collectible) throws IOException {
        if (collectible == Collectible.DIAMONDS) {
            drawImageOnFirstPersonView(g, "diamond.png", 140 + ROOM_SIZE_WIDTH / 20,
                    ROOM_SIZE_WIDTH / 7, ROOM_SIZE_WIDTH / 10, ROOM_SIZE_WIDTH / 10);
        } else if (collectible == Collectible.SAPPHIRES) {
            drawImageOnFirstPersonView(g, "sapphire.png", 0, ROOM_SIZE_WIDTH / 7,
                    ROOM_SIZE_WIDTH / 7, ROOM_SIZE_WIDTH / 10);
        } else if (collectible == Collectible.RUBIES) {
            drawImageOnFirstPersonView(g, "ruby.png", 300 + ROOM_SIZE_WIDTH / 20,
                    ROOM_SIZE_WIDTH / 7, ROOM_SIZE_WIDTH / 10, ROOM_SIZE_WIDTH / 10);
        }
    }

    private void drawImageOnFirstPersonView(Graphics g, String imageName, int offsetX, int offsetY,
                                            int offsetWidth, int offsetHeight) throws IOException {
        locationImage = overlay(locationImage, "/" + imageName, offsetX, offsetY, offsetWidth,
                offsetHeight);
        g.drawImage(locationImage, 0, 0, ROOM_SIZE_WIDTH, ROOM_SIZE_WIDTH / 2, null);
    }

    private BufferedImage overlay(BufferedImage starting, String fpath, int offsetX, int offsetY,
                                  int offsetWidth, int offsetHeight) throws IOException {
        InputStream imageStream = getClass().getResourceAsStream(fpath);
        BufferedImage overlay = null;
        if (imageStream != null) {
            overlay = ImageIO.read(imageStream);
        }
        BufferedImage combined = new BufferedImage(starting.getWidth(), starting.getHeight(),
                BufferedImage.TYPE_INT_ARGB);
        Graphics g = combined.getGraphics();
        g.drawImage(starting, 0, 0, null);
        g.drawImage(overlay, offsetX, offsetY, offsetWidth, offsetHeight, null);
        return combined;
    }
}
