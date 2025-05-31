package handler;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import main.GameWindow;
import utility.GameState;

public class KeyHandler implements KeyListener {
    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();

        switch (GameWindow.gameState) {
            case MENU:
                this.keyPressedInMenuState(keyCode);
                break;
            case PAUSED:
                this.keyPressedInPausedState(keyCode);
                break;
            case GAME_OVER:
                this.keyPressedInGameOverState(keyCode);
                break;
            case PLAYING:
                this.keyPressedInPlayingState(keyCode);
                break;
            case INSTRUCTIONS:
                keyPressedInInstructionsState(keyCode);
                break;
            default:
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}

    private void keyPressedInMenuState(int keyCode) {
        if(keyCode == KeyEvent.VK_ENTER) {
            GameWindow.gameState = GameState.PLAYING;
        } else if(keyCode == KeyEvent.VK_I) {
            GameWindow.gameState = GameState.INSTRUCTIONS;
        }
    }

    private void keyPressedInPausedState(int keyCode) {
        if(keyCode == KeyEvent.VK_ENTER) {
            GameWindow.gameState = GameState.PLAYING;
        }
    }
    
    private void keyPressedInGameOverState(int keyCode) {
        if(keyCode == KeyEvent.VK_ENTER) {
            GameWindow.resetGame();
            GameWindow.gameState = GameState.PLAYING;
        }
    }

    private void keyPressedInInstructionsState(int keyCode) {
        if(keyCode == KeyEvent.VK_ENTER) {
            GameWindow.gameState = GameState.MENU;
        }
    }

    private void keyPressedInPlayingState(int keyCode) {
        if(keyCode == KeyEvent.VK_ESCAPE) {
            GameWindow.gameState = GameState.PAUSED;
        }

        if(keyCode >= KeyEvent.VK_A && keyCode <= KeyEvent.VK_Z) {
            GameWindow.input.setInput(KeyEvent.getKeyText(keyCode));
        }
            
        if(keyCode == KeyEvent.VK_SPACE || keyCode == KeyEvent.VK_ENTER) {
            GameWindow.checkInput();
            GameWindow.input.reset();
        }
    }
}
