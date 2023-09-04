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

import towers.*;
import ui.ActionBar;
import ui.MyButton;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;


import static helpz.Constants.Tiles.*;
import static helpz.Constants.TowerType.*;
import static helpz.LoadSave.GetLevelDir;

public class Playing extends GameScene implements SceneMethods {

    private static int[][] lvl;
    private ActionBar actionBar;
    private int mouseX, mouseY, mX, mY;
    private EnemyMenager enemyMenager;
    private ProjectileManager projectileManager;
    private TowerManager towerManager;
    private WaveManager waveManager;
    private MyButton bReplay;
    private PathPoint start, end;
    private Tower selectedTower;
    private int endWaveGold = 150, goldTick = 0, goldTickLimit = 60 * 13, passiveIncomGold = 5;
    private boolean paused, gameOver, startOfGame = false, win = false;
    private static int chosenLvl = 1;

    public Playing(Game game) {
        super(game);
        LoadDefoultLevel();
        actionBar = new ActionBar(1280, 0, 256, 1280, this, game);
        projectileManager = new ProjectileManager(this);
        waveManager = new WaveManager(this);
        enemyMenager = new EnemyMenager(this, start, end, waveManager);
        towerManager = new TowerManager(this);
    }

//    public void setLevel(int[][] lvl) {
//        this.lvl = lvl;
//    }

    public void update() {
        if (!win) {
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

                            //start timer or card draw
                            waveManager.startWaveTimer();

                            if (isWaveTimerOver()) {
                                waveManager.increaseWaveIndex();
                                enemyMenager.getEnemies().clear();
                                waveManager.resetEnemyIndex();

                            }
                        } else {
                            Finish();
                        }
                    }


                    if (isTimeForNewEnemy()) {
                        spawnEnemy();
                    }
                    enemyMenager.update();
                    towerManager.update();
                    projectileManager.update();
                }

                if (actionBar.getLives() <= 0) {
                    gameOver = true;
                    game.saveGame();
                }
            }

        }
    }

    public void Finish() {
        win = true;
        game.saveGame();
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

    public void drawWinScrean(Graphics g) {
        g.setColor(new Color(10, 255, 50));
        g.setFont(new Font("Serif", Font.BOLD, 50));
        String text = "VICTORY";
        int h = g.getFontMetrics().getHeight();
        int w = g.getFontMetrics().stringWidth(text);
        g.drawString(text, 640 - w / 2, 15 + 640 - h / 2);
        w = 400;
        h = w / 4;
        int x = 640 - w / 2;
        int y = 324;


        bReplay = new MyButton("Replay", x, y, w, h);
        bReplay.draw(g);
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

    private boolean isTimeForNewEnemy() {
        if (waveManager.isTimeForNewEnemy()) {
            if (waveManager.isTherMoreEnemysInWave()) {
                return true;
            }
        }
        return false;
    }

    public void LoadDefoultLevel() {
        lvl = LoadSave.GetLevelData();
        ArrayList<PathPoint> points = LoadSave.getPathPoints();
        start = points.get(0);
        end = points.get(1);

    }

    @Override
    public void render(Graphics g) {
        drawLevel(g);

        enemyMenager.draw(g);
        towerManager.draw(g);
        actionBar.draw(g);
        projectileManager.draw(g);

        drawSelectedTower(g);
        drawHighlight(g);
        if (win) {
            drawWinScrean(g);
        }
        if (paused) {
            drawPause(g);
        }
    }


    private void drawHighlight(Graphics g) {
        g.setColor(Color.white);
        if(mouseX<1280){
        g.drawRect(mouseX, mouseY, 64, 64);}
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
        } else if (win) {
            if (bReplay.getBounds().contains(x, y)) {
                resetEvrything();
            }
        } else {
            if (selectedTower != null) {
                if (isTileGrass(mouseX, mouseY)) {
                    if (getTowerAt(mouseX, mouseY) == null) {
                        towerManager.addTower(selectedTower, mouseX, mouseY);
                        actionBar.goldSpend(Constants.TowerType.getCost(selectedTower.getTowerType()));
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
        if (t.getTowerType()==CROSSBOW){
            projectileManager.crossbowNewProjectile(t);
        }else {
            projectileManager.newProjectile(t, e);
        }

    }

    @Override
    public void mouseMoved(int x, int y) {
        mX=x;
        mY=y;
        mouseX = (x / 64) * 64;
        mouseY = (y / 64) * 64;
        if (win) {
            bReplay.setMouseOver(false);
        }
        if (x >= 1280) {
            actionBar.mouseMoved(x, y);

        } else if (win) {
            if (bReplay.getBounds().contains(x, y)) {
                bReplay.setMouseOver(true);
            }
        }
    }

    @Override
    public void mouseReleased(int x, int y) {
        if (win) {
            bReplay.resetBooleans();
        }
        if (x >= 1280) {
            actionBar.mouseReleased(x, y);
        }
    }


    @Override
    public void mousePressed(int x, int y) {
        if (x >= 1280) {
            actionBar.mousePressed(x, y);
        } else if (win) {
            if (bReplay.getBounds().contains(x, y)) {
                bReplay.setMousePressed(true);
            }
        }
    }

    @Override
    public void mouseDragged(int x, int y) {
        if (x >= 1280) {
            actionBar.mouseDragged(x, y);
        } else if (win) {
            if (bReplay.getBounds().contains(x, y)) {
                resetEvrything();
            }
        } else {
            if (selectedTower != null) {
                if (isTileGrass(mouseX, mouseY)) {
                    if (getTowerAt(mouseX, mouseY) == null) {
                        towerManager.addTower(selectedTower, mouseX, mouseY);
                        actionBar.goldSpend(Constants.TowerType.getCost(selectedTower.getTowerType()));
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

    public void rewardPlayerAfterWave() {
        actionBar.earnGold(endWaveGold);
    }

    public void rewardPlayer(int enemyType) {
        actionBar.earnGold(Constants.EnemyType.getGoldWorth(enemyType));
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

    private void rotateTower() {//////////////////////////////////////

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
        win = false;
        enemyMenager.reset();
        projectileManager.reset();
        towerManager.reset();
        waveManager.reset();

        mouseX = 0;
        mouseY = 0;
        gameOver = false;
        startOfGame = false;
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

public int getMouseX(){
        return mX;
}

    public int getMouseY(){
        return mY;
    }


}
