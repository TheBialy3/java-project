package scenes;

import enemies.Enemy;
import helpz.Constants;
import helpz.LoadSave;
import main.Game;
import managers.EnemyMenager;
import managers.ProjectileManager;
import managers.TowerManager;
import managers.WaveManager;
import objects.PathPoint;

import towers.Tower;
import ui.ActionBar;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import static helpz.Constants.Tiles.*;
import static helpz.Constants.TowerType.*;

public class Playing extends GameScene implements SceneMethods {

    private static int[][] lvl;
    private ActionBar actionBar;
    private int mouseX, mouseY;
    private EnemyMenager enemyMenager;
    private ProjectileManager projectileManager;
    private TowerManager towerManager;
    private WaveManager waveManager;
    private PathPoint start, end;
    private Tower selectedTower;
    private int endWaveGold = 150;
    private int passiveIncomGold = 5;
    private int goldTickLimit = 60 * 13;
    private int goldTick = 0;
    private boolean paused, gameOver, startOfGame = true;

    public Playing(Game game) {
        super(game);
        LoadDefoultLevel();
        actionBar = new ActionBar(1280, 0, 256, 1280, this);
        enemyMenager = new EnemyMenager(this, start, end);
        towerManager = new TowerManager(this);
        projectileManager = new ProjectileManager(this);
        waveManager = new WaveManager(this);
    }

    public void setLevel(int[][] lvl) {
        this.lvl = lvl;
    }

    public void update() {
        if (!gameOver) {
            if (!paused) {
                updateTick();
                getWaveManager().update();
                if (startOfGame) {
                    waveManager.startWaveTimer();
                }


                //passiveIncom
                passiveIncom();

                if (isAllEnemysDead()) {
                    if (isThereMoreWaves()) {
                        //start timer albo inicjuj karty
                        waveManager.startWaveTimer();
                        //timer lub wybór karty
                        if (isWaveTimerOver()) {
                            waveManager.increaseWaveIndex();
                            enemyMenager.getEnemies().clear();
                            waveManager.resetEnemyIndex();
                        }
                    }
                }

                if (isTimeForNewWave()) {
                    spawnEnemy();
                }
                enemyMenager.update();
                towerManager.update();
                projectileManager.update();
                if (actionBar.getLives() <= 0) {
                    gameOver = true;
                }
            } else {

            }
        }
    }

    private void passiveIncom() {
        goldTick++;
        if (goldTick % goldTickLimit == 0) {
            actionBar.earnGold(passiveIncomGold);
        }
    }

    public void drawPause(Graphics g) {
        g.setColor(new Color(171, 0, 0));
        g.setFont(new Font("Serif", Font.BOLD, 50));
        String text = "PAUSED";
        int h = g.getFontMetrics().getHeight();
        int w = g.getFontMetrics().stringWidth(text);
        g.drawString(text, 640 - w / 2, 15 + 640 - h / 2);
    }


    private boolean isWaveTimerOver() {
        return waveManager.isWaveTimerOver();
    }

    private boolean isThereMoreWaves() {

        return waveManager.isThereMoreWaves();
    }

    public boolean isAllEnemysDead() {
        if (waveManager.isTherMoreEnemysInWave()) {
            return false;
        }
        for (Enemy e : enemyMenager.getEnemies()) {
            if (e.isAlive()) {
                return false;
            }
        }

        return true;
    }

    private void spawnEnemy() {
        enemyMenager.spawnEnemy(waveManager.getNextEnemy());
    }

    private boolean isTimeForNewWave() {
        if (waveManager.isTimeForNewWave()) {
            if (waveManager.isTherMoreEnemysInWave()) {
                return true;
            }
        }
        return false;
    }

    private void LoadDefoultLevel() {
        lvl = LoadSave.GetLevelData();
        ArrayList<PathPoint> points = LoadSave.getPathPoints();
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

        if (paused) {
            drawPause(g);
        }
    }


    private void drawHighlight(Graphics g) {
        g.setColor(Color.white);
        g.drawRect(mouseX, mouseY, 64, 64);
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
                        actionBar.goldSpend(Constants.TowerType.GetCost(selectedTower.getTowerType()));

                        selectedTower = null;


                    }
                }
            } else {
                //check tower on map
                Tower t = getTowerAt(mouseX, mouseY);
                actionBar.displayTower(t);

            }
        }

        paused = false;
    }

    private Tower getTowerAt(int x, int y) {
        return towerManager.getTowerAt(x, y);
    }

    public void removeTower(Tower displayedTower) {
        towerManager.removeTower(displayedTower);
    }

    private boolean isTileGrass(int x, int y) {
        int id = lvl[y / 64][x / 64];
        int tileType = game.getTileManager().getTile(id).getTileType();
        return tileType == GRASS_TILE;
    }

    public void shootEnemy(Tower t, Enemy e) {
        projectileManager.newProjectile(t, e);
    }

    @Override
    public void mouseMoved(int x, int y) {
        if (x >= 1280) {
            actionBar.mouseMoved(x, y);
            mouseX = -111;
            mouseY = -111;
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
            actionBar.mouseDragged(x, y);
        } else {
            mouseX = (x / 64) * 64;
            mouseY = (y / 64) * 64;
        }
    }

    public void rewardPlayerAfterWave() {
        actionBar.earnGold(endWaveGold);
    }

    public void rewardPlayer(int enemyType) {
        actionBar.earnGold(Constants.EnemyType.GetGoldWorth(enemyType));
    }

    public TowerManager getTowerManager() {
        return towerManager;
    }

    public void setSelectedTower(Tower selectedTower) {
        this.selectedTower = selectedTower;
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            paused = true;
        }
    }

    public EnemyMenager getEnemyMenager() {
        return enemyMenager;
    }

    public WaveManager getWaveManager() {
        return waveManager;
    }

    public void mouseClickedR() {
        setSelectedTower(null);

        paused = false;
    }

    public void removeOneLive() {
        actionBar.removeOneLive();
    }

    public void resetEvrything() {
        actionBar.resetEvrything();

        enemyMenager.reset();
        projectileManager.reset();
        towerManager.reset();
        waveManager.reset();

        mouseX = 0;
        mouseY = 0;
        gameOver = false;
        startOfGame = true;
        selectedTower = null;
        goldTick = 0;
        paused = false;
    }

    public void setMine(Tower t, PathPoint e) {
        projectileManager.newMine(t, e);
    }

    public ProjectileManager getProjectileManager() {
        return projectileManager;
    }

    public int[][] getRoadDirArr() {
        return enemyMenager.getRoadDirArr();
    }
}
