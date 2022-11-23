package scenes;

import enemies.Enemy;
import helpz.LoadSave;
import main.Game;
import managers.EnemyMenager;
import managers.ProjectileManager;
import managers.TowerManager;
import objects.PathPoint;
import objects.Tower;
import ui.ActionBar;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import static helpz.Constants.Tiles.*;

public class Playing extends GameScene implements SceneMethods {

    private static int[][] lvl;
    private ActionBar actionBar;
    private int mouseX, mouseY;
    private EnemyMenager enemyMenager;
    private ProjectileManager projectileManager;
    private TowerManager towerManager;
    private PathPoint start, end;
    private Tower selectedTower;

    public Playing(Game game) {
        super(game);
        LoadDefoultLevel();
        actionBar = new ActionBar(1280, 0, 256, 1280, this);
        enemyMenager = new EnemyMenager(this, start, end);
        towerManager = new TowerManager(this);
        projectileManager = new ProjectileManager(this);
    }

    public void setLevel(int[][] lvl) {
        this.lvl = lvl;
    }

    public void update() {
        updateTick();
        enemyMenager.update();
        towerManager.update();
        projectileManager.update();
    }

    private void LoadDefoultLevel() {
        lvl = LoadSave.GetLevelData("newlevel");
        ArrayList<PathPoint> points = LoadSave.getPathPoints("newlevel");
        start = points.get(0);
        end = points.get(1);
    }

    @Override
    public void render(Graphics g) {
        drawLevel(g);
        actionBar.draw(g);
        enemyMenager.draw(g);
        towerManager.draw(g);
        projectileManager.draw(g);

        drawSelectedTower(g);
        drawHighlight(g);
    }

    private void drawHighlight(Graphics g) {
        g.setColor(Color.white);
        g.drawRect(mouseX,mouseY,64,64);
    }

    private void drawSelectedTower(Graphics g) {
        if (selectedTower != null) {
            g.drawImage(towerManager.getTowerImgs()[selectedTower.getTowerType()], mouseX, mouseY, null);
        }
    }

    private void drawLevel(Graphics g) {
        for (int y = 0; y < lvl.length; y++) {
            for (int x = 0; x < lvl[y].length; x++) {
                int id = lvl[y][x];
                if (isAnimation(id)) {
                    g.drawImage(getSprite(id, animationIndex), x * 64, y * 64, null);
                } else {
                    g.drawImage(getSprite(id), x * 64, y * 64, null);
                }
            }
        }
    }


    public int getTileType(int x, int y) {
        int xCord = x / 64;
        int yCord = y / 64;

        if (xCord > 19 || xCord < 0) {
            return 0;
        } else if (yCord > 19 || yCord < 0) {
            return 0;
        }
        int id = lvl[y / 64][x / 64];
        return game.getTileManager().getTile(id).getTileType();
    }


    @Override
    public void mouseClicked(int x, int y) {
        if (x >= 1280) {
            actionBar.mouseClicked(x, y);
        } else {
            if (selectedTower != null) {
                if (isTileGrass(mouseX, mouseY)) {
                    if (getTowerAt(mouseX, mouseY) == null) {
                        towerManager.addTower(selectedTower, mouseX, mouseY);
                        selectedTower = null;
                    }
                }
            } else {
                //check tower on map
                Tower t = getTowerAt(mouseX, mouseY);
                    actionBar.displayTower(t);

            }
        }
    }

    private Tower getTowerAt(int x, int y) {
        return towerManager.getTowerAt(x, y);
    }

    private boolean isTileGrass(int x, int y) {
        int id = lvl[y / 64][x / 64];
        int tileType = game.getTileManager().getTile(id).getTileType();
        return tileType == GRASS_TILE;
    }

    @Override
    public void mouseMoved(int x, int y) {
        if (x >= 1280) {
            actionBar.mouseMoved(x, y);
        } else {
            mouseX = (x / 64) * 64;
            mouseY = (y / 64) * 64;
        }
    }

    @Override
    public void mouseReleased(int x, int y) {
        if (x >= 1280) {
            actionBar.mouseReleased(x, y);
        }
    }


    @Override
    public void mousePressed(int x, int y) {
        if (x >= 1280) {
            actionBar.mousePressed(x, y);
        }
    }

    @Override
    public void mouseDragged(int x, int y) {
        if (x >= 1280) {

        } else {
            mouseX = (x / 64) * 64;
            mouseY = (y / 64) * 64;
        }
    }

    public TowerManager getTowerManager() {
        return towerManager;
    }

    public void setSelectedTower(Tower selectedTower) {
        this.selectedTower = selectedTower;
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            setSelectedTower(null);
        }
    }

    public EnemyMenager getEnemyMenager(){
        return enemyMenager;
    }

    public void shootEnemy(Tower t, Enemy e) {
        projectileManager.newProjectile(t,e);
    }
}
