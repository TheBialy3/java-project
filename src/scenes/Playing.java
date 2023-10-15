package scenes;

import enemies.Enemy;
import helpz.Constants;
import helpz.LoadSave;
import main.Game;
import managers.EnemyMenager;
import managers.ProjectileManager;
import managers.TowerManager;
import managers.WaveManager;
import objects.Beam;
import objects.Card;
import objects.PathPoint;

import towers.*;
import ui.PlayingBar;
import ui.MyButton;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;


import static helpz.Constants.Tiles.*;
import static helpz.Constants.TowerType.*;
import static helpz.LoadSave.*;

public class Playing extends GameScene implements SceneMethods {

    private static int[][] lvl;
    private int[][] road;
    private PlayingBar playingBar;
    private int mouseX, mouseY, mX, mY;
    private EnemyMenager enemyMenager;
    private ProjectileManager projectileManager;
    private TowerManager towerManager;
    private WaveManager waveManager;
    private MyButton bReplay;
    private MyButton bCard1, bCard2, bCard3;
    private BufferedImage card = getCardSprite(), cardChoose = getCardChooseSprite();
    private PathPoint start, end;
    private ArrayList<Beam> beams = new ArrayList<>();
    private ArrayList<Card> cards = new ArrayList<>();
    private ArrayList<Card> threeCards = new ArrayList<>();
    private Random random = new Random();
    private Tower selectedTower;
    private int goldTick = 0, goldTickLimit = 60 * 13, passiveIncomeGold = 5, afterEveryThisNumberOfWaveIsCardSelect = 5;
    private boolean paused, gameOver, startOfGame = false, win = false, cardSelect = false;
    // if showTowersOrEnemyType is false show towers
    private boolean showTowersOrEnemyType = false;
    private static int chosenLvl = 1;

    public Playing(Game game) {
        super(game);
        LoadDefaultLevel();
        playingBar = new PlayingBar(1280, 0, 256, 1280, this, game);
        projectileManager = new ProjectileManager(this);
        waveManager = new WaveManager(this);
        enemyMenager = new EnemyMenager(this, start, end, waveManager);
        towerManager = new TowerManager(this);
        initCardButtons();

        // xp.UpgradesItemsCard();
    }

    private void getCards() {
        cards = game.getCards();
    }

    private void initCardButtons() {
        int cardX = 140;
        int cardY = 100;
        int cardW = 1000;
        int cardH = 300;
        int diffCard = 400;


        bCard1 = new MyButton("Card1", cardX, cardY, cardW, cardH);
        bCard2 = new MyButton("Card2", cardX, cardY + diffCard, cardW, cardH);
        bCard3 = new MyButton("Card3", cardX, cardY + diffCard + diffCard, cardW, cardH);
    }

    public void update() {
        if (!win) {
            if (!gameOver) {
                if (!paused) {
                    updateTick();
                    getWaveManager().update();
                    if (startOfGame) {
                        waveManager.startWaveTimer();
                    }
                    if (cardSelect) {
                    } else {
                        passiveIncome();
                        if (isAllEnemyDead()) {
                            if (isThereMoreWaves()) {

                                //start timer or card draw
                                if (waveManager.getWaveIndex() % afterEveryThisNumberOfWaveIsCardSelect == 2) {

                                    cardSelectStart();

                                }
                                waveManager.startWaveTimer();

                                if (isWaveTimerOver()) {
                                    waveManager.increaseWaveIndex();
                                    enemyMenager.getEnemies();
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

                    if (playingBar.getLives() <= 0) {
                        gameOver = true;
                        game.saveGame();
                    }
                }
            }

        }
    }

    private void cardSelectStart() {////////////////////////////////////////////////////////////////////////////
        threeCards.clear();
        getCards();
        get3Cards();
        cardSelect = true;

    }

    private void get3Cards() {
        while (true) {

            int ran = random.nextInt(cards.size());
            if (cards.get(ran).isUnlocked()) {
                if (!cards.get(ran).isActive()) {
                    if (threeCards.size() == 0) {
                        threeCards.add(cards.get(ran));
                    }
                    if (threeCards.size() == 1) {
                        if (cards.get(ran) != threeCards.get(0)) {
                            threeCards.add(cards.get(ran));
                        }
                    }
                    if (threeCards.size() == 2) {
                        if (cards.get(ran) != threeCards.get(0) && cards.get(ran) != threeCards.get(1)) {
                            threeCards.add(cards.get(ran));
                        }
                    }
                }
            }
            if (threeCards.size() == 3) {
                System.out.println(threeCards.get(0).toString());
                System.out.println(threeCards.get(1).toString());
                System.out.println(threeCards.get(2).toString());
                return;
            }
        }
    }

    public void Finish() {
        win = true;
        game.saveGame();
    }


    private void passiveIncome() {
        goldTick++;
        if (goldTick % goldTickLimit == 0) {
            playingBar.earnGold(passiveIncomeGold);
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

    public void drawWinScreen(Graphics g) {
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

    public boolean isAllEnemyDead() {
        if (waveManager.isTheirMoreEnemyInWave()) {
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
            if (waveManager.isTheirMoreEnemyInWave()) {
                return true;
            }
        }
        return false;
    }

    public void LoadDefaultLevel() {
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

        drawSelectedTower(g);
        drawHighlight(g);

        playingBar.draw(g);
        projectileManager.draw(g);
        drawBeam(g);

        if (cardSelect) {
            drawCardsToSelect(g);
        }
        if (win) {
            drawWinScreen(g);
        }
        if (paused) {
            drawPause(g);
        }

    }

    private void drawCardsToSelect(Graphics g) {
        int cardX = 140;
        int cardY = 100;
        int diffCard = 400;
        int nextLineH=50;
        int centerH=100;
        int centerW=100;

        bCard1.draw(g);
        bCard2.draw(g);
        bCard3.draw(g);
////make card compinent and add good background for it
        if (!bCard1.isMouseOver()) {
            g.drawImage(card, cardX, cardY, null);
        } else {
            g.drawImage(cardChoose, cardX, cardY, null);
        }
        g.setColor(new Color(28, 28, 28));
        g.setFont(new Font("Serif", Font.BOLD, 30));
        g.drawString(threeCards.get(0).getName(), cardX+centerW, cardY+centerH);
        g.drawString(threeCards.get(0).getDescription(), cardX+centerW, cardY+centerH+nextLineH);
        if (!bCard2.isMouseOver()) {
            g.drawImage(card, cardX, cardY + diffCard, null);
        } else {
            g.drawImage(cardChoose, cardX, cardY + diffCard, null);
        }
        g.drawString(threeCards.get(1).getName(), cardX+centerW, cardY+centerH+ diffCard );
        g.drawString(threeCards.get(1).getDescription(), cardX+centerW, cardY+centerH+nextLineH+ diffCard );
        if (!bCard3.isMouseOver()) {
            g.drawImage(card, cardX, cardY + diffCard + diffCard, null);
        } else {
            g.drawImage(cardChoose, cardX, cardY + diffCard + diffCard, null);
        }
        g.drawString(threeCards.get(2).getName(), cardX+centerW, cardY+centerH+ diffCard + diffCard );
        g.drawString(threeCards.get(2).getDescription(), cardX+centerW, cardY+centerH+nextLineH+ diffCard + diffCard );

    }

    private void drawBeam(Graphics g) {
        for (Beam beam : beams) {
            if (beam.getActive()) {
                g.setColor(new Color(255, 0, 0));
                g.drawLine(beam.getxStart(), beam.getyStart(), beam.getxEnd(), beam.getyEnd());
                beam.timerDown();

            }
        }

    }


    private void drawHighlight(Graphics g) {
        g.setColor(Color.white);
        if (mouseX < 1280) {
            g.drawRect(mouseX, mouseY, 64, 64);
        }
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


    @Override
    public void mouseClicked(int x, int y) {
        if (x >= 1280) {
            playingBar.mouseClicked(x, y);
        } else if (win) {
            if (bReplay.getBounds().contains(x, y)) {
                resetEverything();
            }
        } else {
            if (selectedTower != null) {
                if (isTileGrass(mouseX, mouseY)) {
                    if (getTowerAt(mouseX, mouseY) == null) {
                        towerManager.addTower(selectedTower, mouseX, mouseY);
                        playingBar.goldSpend(Constants.TowerType.getCost(selectedTower.getTowerType()));
                        selectedTower = null;
                    }
                }
            } else {
                //check tower on map
                Tower t = getTowerAt(mouseX, mouseY);
                playingBar.displayTower(t);

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
        if (t.getTowerType() == CROSSBOW) {
            projectileManager.crossbowNewProjectile(t);
        } else {
            projectileManager.newProjectile(t, e);
        }

    }

    @Override
    public void mouseMoved(int x, int y) {
        mX = x;
        mY = y;
        mouseX = (x / 64) * 64;
        mouseY = (y / 64) * 64;
        if (cardSelect) {
            bCard1.setMouseOver(false);
            bCard2.setMouseOver(false);
            bCard3.setMouseOver(false);
            if (bCard1.getBounds().contains(x, y)) {
                bCard1.setMouseOver(true);
            } else if (bCard2.getBounds().contains(x, y)) {
                bCard2.setMouseOver(true);
            } else if (bCard3.getBounds().contains(x, y)) {
                bCard3.setMouseOver(true);
            }
        }
        if (win) {
            bReplay.setMouseOver(false);
        }
        if (x >= 1280) {
            playingBar.mouseMoved(x, y);

        } else if (win) {
            if (bReplay.getBounds().contains(x, y)) {
                bReplay.setMouseOver(true);
            }
        }
    }

    @Override
    public void mouseReleased(int x, int y) {
        if (cardSelect) {
            bCard1.resetBooleans();
            bCard2.resetBooleans();
            bCard3.resetBooleans();
        }
        if (win) {
            bReplay.resetBooleans();
        }
        if (x >= 1280) {
            playingBar.mouseReleased(x, y);
        }
    }


    @Override
    public void mousePressed(int x, int y) {
        if (cardSelect) {
            if (bCard1.getBounds().contains(x, y)) {

            } else if (bCard2.getBounds().contains(x, y)) {

            } else if (bCard3.getBounds().contains(x, y)) {

            }
        }
        if (x >= 1280) {
            playingBar.mousePressed(x, y);
        } else if (win) {
            if (bReplay.getBounds().contains(x, y)) {
                bReplay.setMousePressed(true);
            }
        }
    }

    @Override
    public void mouseDragged(int x, int y) {
        mouseClicked(x, y);
    }

    public void rewardPlayerAfterWave() {
        int endWaveGold = 150;
        playingBar.earnGold(endWaveGold);
    }

    public void rewardPlayer(int enemyType) {
        playingBar.earnGold(Constants.EnemyType.getGoldWorth(enemyType));
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
        if (e.getKeyCode() == KeyEvent.VK_Q) {
            if (playingBar.isEnoughGold(Constants.TowerType.getCost(ARCHER))) {
                selectedTower = new Archer(1555, 15555, 0, ARCHER, road);
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_W) {
            if (playingBar.isEnoughGold(Constants.TowerType.getCost(CANNON))) {
                selectedTower = new Cannon(1555, 15555, 0, CANNON, road);
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_E) {
            if (playingBar.isEnoughGold(Constants.TowerType.getCost(FROST_MAGE))) {
                selectedTower = new FrostMage(1555, 15555, 0, FROST_MAGE, road);
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_R) {
            if (playingBar.isEnoughGold(Constants.TowerType.getCost(MINES_FACTORY))) {
                selectedTower = new MineFactory(1555, 15555, 0, MINES_FACTORY, road);
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_T) {
            if (playingBar.isEnoughGold(Constants.TowerType.getCost(POISON_TOWER))) {
                selectedTower = new PoisonTower(1555, 15555, 0, POISON_TOWER, road);
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_Y) {
            if (playingBar.isEnoughGold(Constants.TowerType.getCost(BOOM_VOLCANO))) {
                selectedTower = new BoomTower(1555, 15555, 0, BOOM_VOLCANO, road);
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_A) {
            if (playingBar.isEnoughGold(Constants.TowerType.getCost(CROSSBOW))) {
                selectedTower = new Crossbow(1555, 15555, 0, CROSSBOW, road);
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_S) {
            if (playingBar.isEnoughGold(Constants.TowerType.getCost(MOUSE_FOLLOWS_TOWER))) {
                selectedTower = new MauseFollowsTower(1555, 15555, 0, MOUSE_FOLLOWS_TOWER, road);
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_D) {
            if (playingBar.isEnoughGold(Constants.TowerType.getCost(SNIPER))) {
                selectedTower = new Sniper(1555, 15555, 0, SNIPER, road);
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_F) {
            if (playingBar.isEnoughGold(Constants.TowerType.getCost(LASER_TOWER))) {
                selectedTower = new LaserTower(1555, 15555, 0, LASER_TOWER, road);
            }
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
        playingBar.removeOneLive();
    }

    public void resetEverything() {
        playingBar.resetEvrything();
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
        beamReset();
    }

    public void setMine(Tower t, PathPoint e) {
        projectileManager.newMine(t, e);
    }

    public ProjectileManager getProjectileManager() {
        return projectileManager;
    }

    public int[][] getRoadDirArr() {
        road = enemyMenager.getRoadDirArr();
        return road;
    }

    public int getMouseX() {
        return mX;
    }

    public int getMouseY() {
        return mY;
    }

    public void beamEnemy(Tower t, Enemy e) {
        beams.add(new Beam(t.getX() + 45, t.getY() + 20, (int) e.getX() + 32, (int) e.getY() + 32));
    }

    public void beamReset() {
        beams.clear();
    }
}
