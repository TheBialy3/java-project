package inputs;

import main.Game;
import main.GameStates;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import static main.GameStates.*;

public class KeyboardListener implements KeyListener {
    private Game game;

    public KeyboardListener(Game game){
        this.game=game;

    }


    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
       if(GameStates.gameStates==EDITING){
            game.getEditing().keyPressed(e);
       }else if(GameStates.gameStates==PLAYING){
           game.getPlaying().keyPressed(e);
       }

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
