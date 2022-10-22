package inputs;


import main.Game;
import main.GameStates;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MyMouseListener implements MouseListener, MouseMotionListener {

    private Game game;

    public MyMouseListener(Game game){
        this.game = game;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1){
            switch(GameStates.gameStates){
                case MENU :
                        game.getMenu().mouseClicked(e.getX(),e.getY());
                    break;
                case PLAYING:

                    break;
                case SETTINGS:

                    break;
            }
        }else if (e.getButton() == MouseEvent.BUTTON3){

        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
