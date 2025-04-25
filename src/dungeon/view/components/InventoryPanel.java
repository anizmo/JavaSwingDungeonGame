package dungeon.view.components;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import dungeon.location.Collectible;
import dungeon.maze.ReadonlyMaze;

/**
 * The inventory panel displays all the collectibles and their counts that is collected by the
 * player, it also shows the number of arrows the player is carrying at each point of movement and
 * it reflects the values after every move or after an arrow is shot.
 */
public class InventoryPanel extends JPanel {

    private final ReadonlyMaze model;

    /**
     * Constructs an inventory panel that is located at the mid right of the screen, it takes in the
     * read only variant of the model to display the latest inventory count at all times.
     *
     * @param model ready only variant of the model.
     */
    public InventoryPanel(ReadonlyMaze model) {
        if (model == null) {
            throw new IllegalArgumentException("Model Cannot be null");
        }
        this.model = model;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int diamondCount = 0;
        int rubyCount = 0;
        int sapphireCount = 0;

        if (model.getTreasureMap().get(Collectible.DIAMONDS) != null) {
            diamondCount = model.getTreasureMap().get(Collectible.DIAMONDS);
        }

        if (model.getTreasureMap().get(Collectible.RUBIES) != null) {
            rubyCount = model.getTreasureMap().get(Collectible.RUBIES);
        }

        if (model.getTreasureMap().get(Collectible.SAPPHIRES) != null) {
            sapphireCount = model.getTreasureMap().get(Collectible.SAPPHIRES);
        }

        Font font = new Font("Bold Font", Font.BOLD, 32);

        BufferedImage diamondImage;
        BufferedImage sapphireImage;
        BufferedImage rubyImage;
        try {
            drawIndicatorImage(g, "/arrow.png", 32, 16);
            drawIndicatorImage(g, "/diamond.png", 230, 18);
            drawIndicatorImage(g, "/ruby.png", 410, 18);
            drawIndicatorImage(g, "/sapphire.png", 590, 18);
        } catch (IOException e) {
            e.printStackTrace();
        }

        g.setFont(font);
        g.drawString(String.valueOf(model.getNumberOfArrows()), 100, 60);
        g.drawString(String.valueOf(diamondCount), 290, 60);
        g.drawString(String.valueOf(rubyCount), 470, 60);
        g.drawString(String.valueOf(sapphireCount), 650, 60);
    }

    private void drawIndicatorImage(Graphics g, String filePath, int x, int y) throws IOException {
        InputStream imageStream = getClass().getResourceAsStream(filePath);
        BufferedImage image = null;
        if (imageStream != null) {
            image = ImageIO.read(imageStream);
        }
        g.drawImage(image, x, y, 64, 64, null);
    }
}
