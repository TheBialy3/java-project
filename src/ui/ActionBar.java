package ui;

import objects.Tower;
import scenes.Playing;

import java.awt.*;

import static main.GameStates.MENU;
import static main.GameStates.SetGameState;

public class ActionBar extends Bar {

    private Playing playing;
    private MyButton bMenu, bPause;

    private MyButton[] towerButtons;
    private Tower selectedTower;
    private Tower displayedTower;

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
        //displayTower
        drawDispalyTower(g);
    }

    private void drawDispalyTower(Graphics g) {
        if(displayedTower!=null){
            g.drawImage(playing.getTowerManager().getTowerImgs()[displayedTower.getTowerType()],)
        }
    }

    private void initButtons() {
        int w = 64;
        int h = 64;
        int x = 1293;
        int y = 64;
        int diff = 84;

        bMenu = new MyButton("Menu", 1293, 10, 108, 40);
        bPause = new MyButton("Pause", 1417, 10, 108, 40);
        towerButtons = new MyButton[4];


        for (int i = 0; i < towerButtons.length; i++) {
            int row = i / 3;
            int reszta = i % 3;
            towerButtons[i] = new MyButton("", x + diff * reszta, y + diff * row, w, h, i)
            ;
        }
    }

    private void drawButtons(Graphics g) {
        bMenu.draw(g);
        bPause.draw(g);
        for (MyButton b : towerButtons) {
            g.setColor(new Color(22, 125, 211));
            g.fillRect(b.x, b.y, b.width, b.height);
            g.drawImage(playing.getTowerManager().getTowerImgs()[b.getId()], b.x, b.y, b.width, b.height, null);
            drawButtonFeedback(g, b);
        }
    }

    public void mouseClicked(int x, int y) {
        if (bMenu.getBounds().contains(x, y)) {
            SetGameState(MENU);
        } else if (bPause.getBounds().contains(x, y)) {
            SetGameState(MENU);
        }else {
            for (MyButton b : towerButtons) {
                if (b.getBounds().contains(x, y)) {
                    selectedTower = new Tower(0,0,-1,b.getId());
                    playing.setSelectedTower(selectedTower);
                    return;
                }
            }
        }
    }

    public void mouseMoved(int x, int y) {
        bMenu.setMouseOver(false);
        bPause.setMouseOver(false);
        for (MyButton b : towerButtons) {
            b.setMouseOver(false);
        }
        if (bMenu.getBounds().contains(x, y)) {
            bMenu.setMouseOver(true);
        } else if (bPause.getBounds().contains(x, y)) {
            bPause.setMouseOver(true);
        } else {
            for (MyButton b : towerButtons) {
                if (b.getBounds().contains(x, y)) {
                    b.setMouseOver(true);
                    return;
                }
            }
        }

    }

    public void mouseReleased(int x, int y) {
        resetButtons();
    }

    public void mousePressed(int x, int y) {
        if (bMenu.getBounds().contains(x, y)) {
            bMenu.setMousePressed(true);
        } else if (bPause.getBounds().contains(x, y)) {
            bPause.setMousePressed(true);
        } else {
            for (MyButton b : towerButtons) {
                if (b.getBounds().contains(x, y)) {
                    b.setMousePressed(true);
                    return;
                }
            }
        }
    }

    private void resetButtons() {
        bMenu.resetBooleans();
        bPause.resetBooleans();
        for (MyButton b : towerButtons) {
            b.resetBooleans();
        }
    }

    public void displayTower(Tower t) {
displayedTower=t;
    }
}
