package scenes;

import enemies.Enemy;
import main.Game;
import objects.Card;
import towers.Tower;
import ui.MyButton;

import java.awt.*;
import java.util.ArrayList;

//future scene for showing all possible enemies and towers
public class Bestiary extends GameScene implements SceneMethods{
    private ArrayList<Enemy> enemies=new ArrayList<>();
    private ArrayList<Tower> towers=new ArrayList<>();
    private MyButton  bMenu;
    public Bestiary(Game game) {
        super(game);
        initButtons();
        initTowers();
        initEnemies();

    }

    public void update() {
        updateTick();
    }
    @Override
    public void render(Graphics g) {
        drawButtons(g);
        drawUpgradeImg(g);
    }

    private void drawButtons(Graphics g) {
        bMenu.draw(g);
    }

    private void initButtons() {
        int w = 200;
        int h = w / 4;
        int x = 1536 / 2 - w / 2;
        int y = 1150;

        bMenu = new MyButton("Menu", x, y, w, h);
    }

    private void initEnemies() {
    }

    private void initTowers() {
    }


    private void drawUpgradeImg(Graphics g) {
        int diff=99;

        int tilePixelNumber=64;
        for (Card card:cards) {
            if (card.isUnlocked()) {///////////////////////////
                g.setColor(new Color(200, 200, 200));
            } else {
                g.setColor(new Color(161, 0, 0));
            }
            g.fillRect(40+(diff*(card.getId()%15)),50+(diff*(card.getId()/15)), tilePixelNumber,tilePixelNumber);
            g.setColor(new Color(0, 26, 147));
            g.drawString(String.valueOf(card.getId()), 40+(diff*(card.getId()%15))+25, 50+(diff*(card.getId()/15))+39);
            //g.drawImage(playing.getTowerManager().getTowerImgs()[b.getId()], (40+(diff*(card.getId()%15)), 50+(diff*(card.getId()/15)), tilePixelNumber, tilePixelNumber, null);

        }
    }


    @Override
    public void mouseClicked(int x, int y) {

    }

    @Override
    public void mouseMoved(int x, int y) {

    }

    @Override
    public void mouseReleased(int x, int y) {

    }

    @Override
    public void mousePressed(int x, int y) {

    }

    @Override
    public void mouseDragged(int x, int y) {

    }
}
