package entity;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.util.concurrent.ThreadLocalRandom;

import main.GameWindow;
import utility.GameState;

public class Word {
    public static final int WRONG = 0;
    public static final int NORMAL = 1;
    public static final int SPECIAL = 3;
    private static final int REMOVE_ANIMATION_FRAMES = 14;
    private static final Color SPECIAL_WORD_COLOR = new Color(255, 140, 0);
    
    private final String text;
    private int posX;
    private int posY;
    private final int type;
    private boolean removing;
    private int removeFramesLeft;
    private int removeOffsetY;
    private float removeAlpha = 1.0f;

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
        posY = ThreadLocalRandom.current().nextInt(min, max);
    }

    public String getWord() {
        return text;
    }

    public int getType() {
        return type;
    }

    public boolean isRemoving() {
        return removing;
    }

    public void startRemoveAnimation() {
        if (removing) {
            return;
        }

        removing = true;
        removeFramesLeft = REMOVE_ANIMATION_FRAMES;
        removeOffsetY = 0;
        removeAlpha = 1.0f;
    }

    public boolean isRemoveAnimationComplete() {
        return removing && removeFramesLeft <= 0;
    }

    public void update() {
        if(GameWindow.gameState != GameState.PLAYING) return;

        if (removing) {
            removeFramesLeft--;
            posX += 2;
            removeOffsetY--;

            if (removeFramesLeft > 0) {
                removeAlpha = (float) removeFramesLeft / REMOVE_ANIMATION_FRAMES;
            } else {
                removeAlpha = 0.0f;
            }
            return;
        }

        posX--;
    }

    public boolean reachedLeftBoundary() {
        return posX <= 0;
    }

    public void draw(Graphics2D g2d) {
        if(GameWindow.gameState != GameState.PLAYING) return;

        Composite originalComposite = g2d.getComposite();
        if (removing) {
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, removeAlpha));
        }

        if(type == SPECIAL) {
            g2d.setColor(SPECIAL_WORD_COLOR);
        } else {
            g2d.setColor(Color.BLACK);
        }
        g2d.drawString(text, posX, posY + removeOffsetY);

        if (removing) {
            g2d.setComposite(originalComposite);
        }
    }
}
