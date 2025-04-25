package dungeon.view.components;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import dungeon.maze.ReadonlyMaze;

/**
 * This panel displays the health of the moving monster which is called the Berbalang and the player
 * that is representative of their HP (Health Points). Both of them fight each other until one of
 * their health drops to zero. This panel is located below the inventory panel and if the game
 * settings do not have a moving monster then it only displays the player's health.
 */
public class BattleStatusPanel extends JPanel {

    private final ReadonlyMaze model;

    /**
     * Constructs a battle status panel that displays the health of the moving monster and player.
     * This constructor takes in the read only variant of the model that has only the methods used to
     * get statuses, and it cannot write to the model directly.
     *
     * @param model the Readonly variant of the model.
     */
    public BattleStatusPanel(ReadonlyMaze model) {
        this.model = model;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        try {
            InputStream playerHealthImage = getClass().getResourceAsStream("/player_health.png");
            BufferedImage playerHealthIndicator = null;
            if (playerHealthImage != null) {
                playerHealthIndicator = ImageIO.read(playerHealthImage);
            }
            g.drawImage(playerHealthIndicator, 120, 0, 100, 100, null);

            Font font = new Font("Bold Font", Font.BOLD, 32);
            g.setFont(font);
            g.drawString("" + model.getPlayerHealth(), 230, 60);

            if (model.hasMovingMonster()) {
                InputStream berbalangHealthImage
                        = getClass().getResourceAsStream("/ber_health.png");
                BufferedImage berbalangHealthIndicator = null;
                if (berbalangHealthImage != null) {
                    berbalangHealthIndicator = ImageIO.read(berbalangHealthImage);
                }
                g.drawImage(berbalangHealthIndicator, 420, 0, 100, 100, null);
                g.drawString("" + model.getBerbalangHealth(), 530, 60);
            }


            g.setFont(font);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
