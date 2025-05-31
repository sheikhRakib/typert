package entity;

import java.awt.Color;
import java.awt.Graphics2D;

import main.GameWindow;
import utility.GameState;

public class Word {
    public static final int WRONG = 0;
    public static final int NORMAL = 1;
    public static final int SPECIAL = 3;
    
    private String text;
    private int posX;
    private int posY;
    public int type = NORMAL;

    public Word() {
        this("default", NORMAL);
    }

    public Word(String text) {
        this(text, NORMAL);
    }
    
    public Word(String text, int type) {
        this.text = text;
        this.type = type;

        int max = GameWindow.HEIGHT - 60;
        int min = 25;

        posX = GameWindow.WIDTH + 100;
        posY = (int) (Math.random() * (max - min)) + min;
    }

    public String getWord() {
        return text;
    }

    public void update() {
        if(GameWindow.gameState != GameState.PLAYING) return;
        
        posX--;

        GameWindow.gameState = (posX <= 0) ? GameState.GAME_OVER : GameState.PLAYING;
    }

    public void draw(Graphics2D g2d) {
        if(GameWindow.gameState != GameState.PLAYING) return;
        
        if(type == SPECIAL) {
            g2d.setColor(Color.BLUE);
            g2d.drawString(text, posX, posY);
            return;
        }

        g2d.setColor(Color.BLACK);
        g2d.drawString(text, posX, posY);
    }
}
