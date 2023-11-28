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
        getListsOfEnemyAndTowers();
        initButtons();
    }
    private void initButtons() {
        int w = 200;
        int h = w / 4;
        int x = 1536 / 2 - w / 2;
        int y = 1150;

        bMenu = new MyButton("Menu", x, y, w, h);
    }

    private void getListsOfEnemyAndTowers() {
    }

    public void update() {
        updateTick();
    }
    @Override
    public void render(Graphics g) {

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
