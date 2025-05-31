package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import dictionary.Dictionary;
import entity.Word;
import handler.KeyHandler;
import panel.GamePanel;
import panel.MenuPanel;
import utility.Clock;
import utility.GameState;
import utility.Input;
import utility.Score;

public class GameWindow extends JPanel implements Runnable {
    public final static String NAME = "TYPERT";

    public final static int WIDTH = 900;
    public final static int HEIGHT = 600;
    public final static int FPS = 60;

    public static GameState gameState = GameState.MENU;
    public static MenuPanel menuPanel = new MenuPanel();
    public static GamePanel gamePanel = new GamePanel();
    
    public static Clock clock = new Clock();
    public static Score score = new Score();
    public static Input input = new Input();

    private static Dictionary dictionary = new Dictionary();
    public static List<Word> words = new ArrayList<>();

    private Thread gameThread;
    private static KeyHandler keyHandler = new KeyHandler();

    public GameWindow() {
        this.setPreferredSize(new Dimension(GameWindow.WIDTH, GameWindow.HEIGHT));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);

        clock.start();
        addKeyListener(keyHandler);
        
        this.setFocusable(true);
    }

    @Override
    public void run() {
        double drawInterval = 1000000000 / GameWindow.FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        
        while(gameThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;
            if(delta >= 1) {
                update();
                repaint();
                delta--;
            }        
        }
    }

    public void startGame() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    public static void resetGame() {
        gameState = GameState.MENU;
        words.clear();
        score.reset();
        dictionary.reset();
        clock.reset();
        input.reset();
    }

    public static void checkInput() {
        if(gameState != GameState.PLAYING) return;
        if(input.getInput().isEmpty()) return;

        // cheat
        if(input.getInput().equalsIgnoreCase("bang")) {
            score.addPoint(Word.NORMAL);
            words.remove(0);
            input.reset();
            return;
        }

        for (int i = 0; i < words.size(); i++) {
            Word word = words.get(i);
            if(word.getWord().equalsIgnoreCase(input.getInput())) {
                words.remove(i);
                score.addPoint(word.type);
                return;
            }
        }
        score.addPoint(Word.WRONG);
    }

    public void update() {
        dictionary.generateWord();

        for(Word word: words) word.update();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        menuPanel.draw(g2d);
        gamePanel.draw(g2d);

        for (Word word : words) word.draw(g2d);

        g2d.dispose();
    }
}
