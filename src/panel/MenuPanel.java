package panel;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import javax.imageio.ImageIO;

import font.Arial;
import main.GameWindow;
import utility.GameState;

public class MenuPanel {
    private static final Font FONT_SMALL = new Arial(Font.BOLD, 12);
    private static final Font FONT_MEDIUM = new Arial(Font.BOLD, 18);
    private static final Font FONT_LARGE = new Arial(Font.BOLD, 24);
    private static final Font FONT_XL = new Arial(Font.BOLD, 35);
    private static final Font FONT_XXL = new Arial(Font.BOLD, 58);
    private static final String[] INSTRUCTION_LINES = {
        "1. Type the visible words with A-Z keys.",
        "2. Press Space or Enter to submit your input.",
        "3. Correct normal word: +1 point.",
        "4. Correct special word (orange): +3 points.",
        "5. Wrong submit: no points and lower accuracy.",
        "6. If a word reaches the left edge, game over.",
        "7. Press Esc to pause and Enter to resume/restart.",
        "8. Press I in menu to open this page.",
        "9. Cheat code: type 'bang' and submit."
    };

    private BufferedImage bg;
    private BufferedImage play;
    private BufferedImage pause;

    public MenuPanel() {
        try {
            bg = loadImage("/images/bg.png", Path.of("res/images/bg.png"));
            play = loadImage("/images/play.png", Path.of("res/images/play.png"));
            pause = loadImage("/images/pause.png", Path.of("res/images/pause.png"));
        } catch (IOException e) {
            throw new IllegalStateException("Failed to load menu image assets", e);
        }
    }

    private BufferedImage loadImage(String classpathResource, Path fileFallback) throws IOException {
        try (InputStream classpathStream = getClass().getResourceAsStream(classpathResource)) {
            if (classpathStream != null) {
                return ImageIO.read(classpathStream);
            }
        }

        if (Files.exists(fileFallback)) {
            try (InputStream fileStream = Files.newInputStream(fileFallback)) {
                return ImageIO.read(fileStream);
            }
        }

        throw new IOException("Image resource not found: " + classpathResource + " or " + fileFallback);
    }

    public void draw(Graphics2D g2d) {
        g2d.drawImage(bg, 0, 0, GameWindow.WIDTH, GameWindow.HEIGHT, null);

        switch (GameWindow.gameState) {
            case MENU:
                g2d.drawImage(play, 350, 200, 200, 200, null);
                g2d.setFont(FONT_SMALL);
                g2d.setColor(Color.BLACK);
                g2d.drawString("> Press the 'i' key to view instructions.", 20, 20);
                g2d.setFont(FONT_LARGE);
                g2d.drawString("Press Enter to Start Game...", 300, 450);
                break;
            case PAUSED:
                g2d.drawImage(pause, 350, 200, 200, 200, null);
                g2d.setFont(FONT_LARGE);
                g2d.setColor(Color.BLACK);
                g2d.drawString("Press Enter to Resume Game...", 300, 450);
                break;
            case GAME_OVER:
                g2d.setFont(FONT_XXL);
                g2d.setColor(Color.ORANGE);
                g2d.drawString("Game Over", 300, 300);
                g2d.setFont(FONT_XL);
                g2d.setColor(Color.BLUE);
                g2d.drawString("Score: " + GameWindow.score.getScore(), 325, 350);
                g2d.drawString("Accuracy: " + GameWindow.score.getAccuracy(), 325, 380);
                g2d.setFont(FONT_LARGE);
                g2d.setColor(Color.BLACK);
                g2d.drawString("Press Enter to Restart Game...", 300, 450);
                break;
            case INSTRUCTIONS:
                g2d.setFont(FONT_LARGE);
                g2d.setColor(Color.BLACK);
                g2d.drawString("How to Play", 320, 250);
                g2d.setFont(FONT_MEDIUM);
                int y = 285;
                for (String line : INSTRUCTION_LINES) {
                    g2d.drawString(line, 190, y);
                    y += 24;
                }
                g2d.drawString("Press Enter to return to Menu...", 270, 550);
                break;
            default:
                break;
        }
    }
}
