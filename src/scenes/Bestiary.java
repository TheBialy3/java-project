package scenes;


import helpz.Constants;
import looks.BestiaryPopUpLook;
import main.Game;
import looks.EnemyBestiaryLook;

import looks.TowerBestiaryLook;
import ui.MyButton;

import java.awt.*;
import java.util.ArrayList;

import static main.GameStates.*;

//future scene for showing all possible enemies and towers
public class Bestiary extends GameScene implements SceneMethods {
    private ArrayList<EnemyBestiaryLook> enemies = new ArrayList<>();
    private ArrayList<BestiaryPopUpLook> popUp = new ArrayList<>();
    int numberOfEnemiesType = Constants.NumbersOf.NUMBER_OF_ENEMIES;
    private ArrayList<TowerBestiaryLook> towers = new ArrayList<>();
    int numberOfTowersType = Constants.NumbersOf.NUMBER_OF_TOWERS;
    private MyButton bReturn;

    public Bestiary(Game game) {
        super(game);
        initButtons();
        initTowers();
        initEnemies();
        initPopUp();
    }

    private void initPopUp() {
        for (EnemyBestiaryLook enemy : enemies) {
            popUp.add(new BestiaryPopUpLook(enemy, 50, 640));
        }
        for (TowerBestiaryLook tower : towers) {
            popUp.add(new BestiaryPopUpLook(tower, 50,50 ));
        }

    }

    public void update() {
        updateTick();
    }

    @Override
    public void render(Graphics g) {
        drawBackground(g);
        drawButtons(g);
        drawIconsImg(g);
        drawDetailInfo(g);
    }

    private void drawBackground(Graphics g) {
        g.drawImage(helpz.LoadSave.getImg("BackgroundBestiary"), 0, 0, 1536, 1280, null);
    }

    private void drawButtons(Graphics g) {
        bReturn.draw(g);
    }

    private void initButtons() {
        int w = 200;
        int h = w / 4;
        int x = 1536 / 2 - w / 2;
        int y = 1150;

        bReturn = new MyButton("Return", x, y, w, h);
    }

    private void initEnemies() {

        for (int i = 0; i < numberOfEnemiesType; i++) {
            enemies.add(new EnemyBestiaryLook(i));
        }

    }

    private void initTowers() {

        for (int i = 0; i < numberOfTowersType; i++) {
            towers.add(new TowerBestiaryLook(i));
        }
    }


    public void drawIconsImg(Graphics g) {
        int diff = 99;
        int tilePixelNumber = 64;
        for (EnemyBestiaryLook enemy : enemies) {
            if (enemy.isUnlocked()) {
                g.setColor(new Color(200, 200, 200));
            } else {
                g.setColor(new Color(161, 0, 0));
            }
            g.fillRect(40 + (diff * (enemy.getEnemyType() % 15)), 50 + (diff * (enemy.getEnemyType() / 15)), tilePixelNumber, tilePixelNumber);
            g.setColor(new Color(0, 26, 147));
            g.drawString(String.valueOf(enemy.getEnemyType()), 40 + (diff * (enemy.getEnemyType() % 15)) + 25, 50 + (diff * (enemy.getEnemyType() / 15)) + 39);
            g.drawImage(game.getPlaying().getEnemyManager().getEnemyImages()[enemy.getEnemyType()], 40 + (diff * (enemy.getEnemyType() % 15)), 50 + (diff * (enemy.getEnemyType() / 15)), tilePixelNumber, tilePixelNumber, null);
        }

        int heightDifferenceForSectionTowers = 640;
        for (TowerBestiaryLook tower : towers) {
            if (tower.isUnlocked()) {
                g.setColor(new Color(200, 200, 200));
            } else {
                g.setColor(new Color(161, 0, 0));
            }
            g.fillRect(40 + (diff * (tower.getTowerType() % 15)), heightDifferenceForSectionTowers + 50 + (diff * (tower.getTowerType() / 15)), tilePixelNumber, tilePixelNumber);
            g.setColor(new Color(0, 26, 147));
            g.drawString(String.valueOf(tower.getTowerType()), 40 + (diff * (tower.getTowerType() % 15)) + 25, heightDifferenceForSectionTowers + 50 + (diff * (tower.getTowerType() / 15)) + 39);
            g.drawImage(game.getPlaying().getTowerManager().getTowerImg()[tower.getTowerType()], 40 + (diff * (tower.getTowerType() % 15)), heightDifferenceForSectionTowers + 50 + (diff * (tower.getTowerType() / 15)), tilePixelNumber, tilePixelNumber, null);
        }
    }

    private void drawDetailInfo(Graphics g) {
        for (BestiaryPopUpLook pop : popUp) {
            if (pop.isMouseOver()) {
                pop.draw(g);
            }
        }
    }


    @Override
    public void mouseClicked(int x, int y) {
        if (bReturn.getBounds().contains(x, y)) {
            SetGameState(MENU);
        }
    }

    @Override
    public void mouseMoved(int x, int y) {
        for (BestiaryPopUpLook pop:popUp) {
            pop.setMouseOver(false);
        }
        for (BestiaryPopUpLook pop:popUp) {
            if(pop.getBounds().contains(x,y)){
                pop.setMouseOver(true);
            }
        }

    }

    @Override
    public void mouseReleased(int x, int y) {

    }

    @Override
    public void mousePressed(int x, int y) {

    }

    @Override
    public void mouseDragged(int x, int y) {
        mouseClicked(x, y);
    }
}
