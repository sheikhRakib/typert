package utility;

import javax.swing.Timer;

import main.GameWindow;

public class Clock {
    private int seconds = 0;
    private int minutes = 0;
    private int hours = 0;

    private Timer timer;

    public void start() {
        if(timer == null) {
            timer = new Timer(1000, e -> tick());
        }
        timer.start();
    }

    public void tick() {
        if(GameWindow.gameState != GameState.PLAYING) {
            return;
        }

        seconds++;
        
        if(seconds >= 60) {
            seconds = 0;
            minutes++;
        }

        if(minutes >= 60) {
            minutes = 0;
            hours++;
        }
    }

    public void reset() {
        seconds = 0;
        minutes = 0;
        hours = 0;
    }

    public String getTime() {
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }
    
}
