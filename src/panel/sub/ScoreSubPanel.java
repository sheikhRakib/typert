package panel.sub;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import font.Arial;
import main.GameWindow;

public class ScoreSubPanel {
    private final int posX = 0;
    private final int posY = GameWindow.HEIGHT - 50;

    public void draw(Graphics2D g2d) {
        g2d.setColor(Color.WHITE);
        g2d.fillRect(posX, posY, GameWindow.WIDTH, 50);
        g2d.fill3DRect(posX, posY, GameWindow.WIDTH, 50, true);

        g2d.setFont(new Arial(Font.BOLD, 24));

        g2d.setColor(Color.BLACK);
        g2d.drawString("Time: " + GameWindow.clock.getTime(), posX + 10, posY + 35);
        g2d.drawString("Accuracy: " + GameWindow.score.getAccuracy(), posX + 200, posY + 35);
        g2d.drawString("Score: " + GameWindow.score.getScore(), posX + 430, posY + 35);

        g2d.drawString("Input: " + GameWindow.input.getInput(), posX + 580, posY + 35);
    }
}
