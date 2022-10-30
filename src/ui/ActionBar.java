package ui;

import scenes.Playing;

import java.awt.*;

import static main.GameStates.MENU;
import static main.GameStates.SetGameState;

public class ActionBar extends Bar {

    private Playing playing;
    private MyButton bMenu;


    public ActionBar(int x, int y, int width, int height, Playing playing) {
        super(x, y, width, height);
        this.playing = playing;
        initButtons();
    }

    public void draw(Graphics g) {
        //backGround
        g.setColor(new Color(0, 102, 102));
        g.fillRect(x, y, width, height);
        //buttons
        drawButtons(g);
    }

    private void initButtons() {
//        int w = 64;
//        int h = 64;
//        int x = 1300;
//        int y = 64;
//        int diff = 100;
//        int i = 0;
//        int ii = 0;
        bMenu = new MyButton("Menu", 1295, 10, 108, 40);

    }

    private void drawButtons(Graphics g) {
        bMenu.draw(g);
    }

    public void mouseClicked(int x, int y) {
        if (bMenu.getBounds().contains(x, y)) {
            SetGameState(MENU);
        }
    }

    public void mouseMoved(int x, int y) {
        bMenu.setMouseOver(false);
        if (bMenu.getBounds().contains(x, y)) {
            bMenu.setMouseOver(true);
        }
    }

    public void mouseReleased(int x, int y) {
        resetButtons();
    }

    public void mousePressed(int x, int y) {
        if (bMenu.getBounds().contains(x, y)) {
            bMenu.setMousePressed(true);
        }
    }

    private void resetButtons() {
        bMenu.resetBooleans();

    }

}
