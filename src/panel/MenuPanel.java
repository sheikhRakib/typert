package panel;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

import font.Arial;
import main.GameWindow;
import utility.GameState;

public class MenuPanel {
    private BufferedImage bg;
    private BufferedImage play;
    private BufferedImage pause;

    public MenuPanel() {
        try {
            bg = ImageIO.read(getClass().getResourceAsStream("/images/bg.png"));
            play = ImageIO.read(getClass().getResourceAsStream("/images/play.png"));
            pause = ImageIO.read(getClass().getResourceAsStream("/images/pause.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2d) {
        g2d.drawImage(bg, 0, 0, GameWindow.WIDTH, GameWindow.HEIGHT, null);

        if(GameWindow.gameState == GameState.MENU) {
            g2d.drawImage(play, 350, 200, 200, 200, null);
    
            g2d.setFont(new Arial(Font.BOLD, 12 ));
            g2d.setColor(Color.BLACK);
            g2d.drawString("> Press the ‘i’ key to view instructions.", 20, 20);

            g2d.setFont(new Arial(Font.BOLD, 24 ));
            g2d.setColor(Color.BLACK);
            g2d.drawString("Press Enter to Start Game...", 300, 450);
        }

        if(GameWindow.gameState == GameState.PAUSED) {
            g2d.drawImage(pause, 350, 200, 200, 200, null);

            g2d.setFont(new Arial(Font.BOLD, 24 ));
            g2d.setColor(Color.BLACK);
            g2d.drawString("Press Enter to Resume Game...", 300, 450);
        }

        if(GameWindow.gameState == GameState.GAME_OVER) {
            g2d.setFont(new Arial(Font.BOLD, 58 ));
            g2d.setColor(Color.ORANGE);
            g2d.drawString("Game Over", 300, 300);

            g2d.setFont(new Arial(Font.BOLD, 35 ));
            g2d.setColor(Color.BLUE);
            g2d.drawString("Score: " + GameWindow.score.getScore(), 325, 350);
            g2d.drawString("Accuracy: " + GameWindow.score.getAccuracy(), 325, 380);

            g2d.setFont(new Arial(Font.BOLD, 24 ));
            g2d.setColor(Color.BLACK);
            g2d.drawString("Press Enter to Restart Game...", 300, 450);
        }

        if(GameWindow.gameState == GameState.INSTRUCTIONS) {
            g2d.setFont(new Arial(Font.BOLD, 24 ));
            g2d.setColor(Color.BLACK);
            g2d.drawString("Instructions:", 280, 220);
            g2d.setFont(new Arial(Font.BOLD, 18 ));
            g2d.drawString("1. Type the words as they appear.", 300, 240);
            g2d.drawString("2. Press Enter/Space to submit word.", 300, 260);
            g2d.drawString("3. Press Enter to start the game.", 300, 280);
            g2d.drawString("4. Press I to view instructions on menu.", 300, 300);
            g2d.drawString("5. Press Esc to pause the game.", 300, 320);
            g2d.drawString("6. Cheatcode: bang.", 300, 340);

            g2d.drawString("Press Enter to return to Menu...", 300, 450);
        }
    }
}
