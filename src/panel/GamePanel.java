package panel;

import java.awt.Color;
import java.awt.Graphics2D;

import main.GameWindow;
import panel.sub.ScoreSubPanel;
import utility.GameState;

public class GamePanel {
    private ScoreSubPanel scoreSubPanel = new ScoreSubPanel();

    public void draw(Graphics2D g2d) {
        if(GameWindow.gameState == GameState.PLAYING) {
            g2d.setColor(Color.GRAY);
            g2d.fillRect(0, 0, GameWindow.WIDTH, GameWindow.HEIGHT);
            
            scoreSubPanel.draw(g2d);
        }
    }
}
