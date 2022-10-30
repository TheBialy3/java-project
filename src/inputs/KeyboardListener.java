package inputs;

import main.GameStates;


import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import static main.GameStates.*;

public class KeyboardListener implements KeyListener {
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            System.out.println("up");
        } else if (e.getKeyCode() == KeyEvent.VK_A) {
            GameStates.gameStates = MENU;
        } else if (e.getKeyCode() == KeyEvent.VK_D) {
            GameStates.gameStates = SETTINGS;
        } else if (e.getKeyCode() == KeyEvent.VK_S) {
            GameStates.gameStates = PLAYING;
        } else if (e.getKeyCode() == KeyEvent.VK_F) {
            GameStates.gameStates = EDITING;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
