package scenes;

import enemies.Enemy;
import helpz.Constants;
import helpz.LoadSave;
import main.Game;
import managers.EnemyManager;
import managers.ProjectileManager;
import managers.TowerManager;
import managers.WaveManager;
import objects.Beam;
import objects.Card;
import objects.CardLook;
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
    private CardLook cardLook1, cardLook2, cardLook3;
    private static int[][] lvl;
    private int[][] road;
    private PlayingBar playingBar;
    private int mouseX, mouseY, mX, mY, tilePixelNumber = 64;
    private EnemyManager enemyManager;
    private ProjectileManager projectileManager;
    private TowerManager towerManager;
    private WaveManager waveManager;
    private MyButton bReplay;
    private MyButton bCard1, bCard2, bCard3;
    private BufferedImage card = getCardSprite(), cardChoose = getCardChooseSprite(), logos[] = getLogos();
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
        projectileManager = new ProjectileManager(this);
        waveManager = new WaveManager(this);
        enemyManager = new EnemyManager(this, start, end, waveManager);
        towerManager = new TowerManager(this);
        playingBar = new PlayingBar(1280, 0, 256, 1280, this, game,towerManager);
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
                                waveManager.startWaveTimer();

                                if (isWaveTimerOver()) {
                                    if (waveManager.getWaveIndex() % afterEveryThisNumberOfWaveIsCardSelect == 2) {

                                        cardSelectStart();

                                    }
                                    waveManager.increaseWaveIndex();
                                    enemyManager.getEnemies();
                                    waveManager.resetEnemyIndex();

                                }
                            } else {
                                Finish();
                            }
                        }


                        if (isTimeForNewEnemy()) {
                            spawnEnemy();
                        }
                        enemyManager.update();
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
        int cardX = 140;
        int cardY = 100;
        int diffCard = 400;
        logos = getLogos();
        cardLook1 = new CardLook(threeCards.get(0).getName(), threeCards.get(0).getDescription(),threeCards.get(0).getTowertype(), logos[threeCards.get(0).getId()], card, cardChoose, cardX, cardY);
        cardLook2 = new CardLook(threeCards.get(1).getName(), threeCards.get(1).getDescription(),threeCards.get(1).getTowertype(), logos[threeCards.get(1).getId()], card, cardChoose, cardX, cardY + diffCard);
        cardLook3 = new CardLook(threeCards.get(2).getName(), threeCards.get(2).getDescription(),threeCards.get(2).getTowertype(), logos[threeCards.get(2).getId()], card, cardChoose, cardX, cardY + diffCard + diffCard);
        cardSelect = true;
    }

    private void cardSelected(int cardChosen) {
       System.out.println(threeCards.get(cardChosen).getId());
        switch (threeCards.get(cardChosen).getId()){
            case 0: //for all
                towerManager.damageUp(10);
                towerManager.setCard0(true);
            case 1:
                towerManager.speedUp(10);
                towerManager.setCard1(true);
            case 2:
                towerManager.rangeUp(10);
                towerManager.setCard2(true);
            case 3:
                towerManager.upgradesTrue();
                towerManager.setCard3(true);
            case 4:
                towerManager.speedUp(10);
                towerManager.setCard4(true);
            case 5:
                towerManager.damageUp(10);
                towerManager.setCard5(true);
            case 6:
                towerManager.durationUp(10);
                towerManager.setCard6(true);
            case 7:   //ENEMY
                enemyManager.setCard7(true);
            case 8:
                enemyManager.setCard8(true);
            case 9:
                enemyManager.setCard9(true);
            case 10:
                enemyManager.setCard10(true);
            case 11:
                enemyManager.setCard11(true);
            case 12:
                enemyManager.setCard12(true);
                towerManager.setCard12(true);
                projectileManager.setCard12(true);
            case 13:
                projectileManager.setCard13(true);
            case 14:
                towerManager.setCard14(true);
            case 15:
                towerManager.setCard15(true);
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
            case 26:
            case 27:
            case 28:
            case 29:
            case 30:
            case 31:
            case 32:
            case 33:
            case 34:
            case 35:
            case 36:
            case 37:
            case 38:
            case 39:
            case 40:
            case 41:
            case 42:
            case 43:
            case 44:
            case 45:
            case 46:
            case 47:
            case 48:
            case 49:
            case 50:
            case 51:
            case 52:
            case 53:
            case 54:
            case 55:
            case 56:
            case 57:
            case 58:
            case 59:
            case 60:
            case 61:
            case 62:
            case 63:
            case 64:
            case 65:
            case 66:
            case 67:
            case 68:
            case 69:
            default:
        }
        cards.get(threeCards.get(cardChosen).getId()).setActive(true);
        cardSelect=false;
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

    private BufferedImage[] getLogos() {
        BufferedImage logosAtlas = LoadSave.getSpriteLogos();
        int logoNr = 100;
        BufferedImage logo[] = new BufferedImage[logoNr];
        for (int i = 0; i < logoNr; i++) {
            logo[i] = logosAtlas.getSubimage((0 + i % 10) * tilePixelNumber, (0 + i / 10) * tilePixelNumber, tilePixelNumber, tilePixelNumber);
        }
        return logo;
    }

    private void passiveIncome() {
        goldTick++;
        if (goldTick % goldTickLimit == 0) {
            if(enemyManager.isCard9()){
                passiveIncomeGold*=2;
            }
            if(enemyManager.isCard10()){
                passiveIncomeGold*=2;
            }
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
        for (Enemy e : enemyManager.getEnemies()) {
            if (e.isAlive()) {
                return false;
            }
        }

        return true;
    }

    private void spawnEnemy() {
        enemyManager.spawnEnemy(waveManager.getNextEnemy());
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

        enemyManager.draw(g);
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
        bCard1.draw(g);
        bCard2.draw(g);
        bCard3.draw(g);

        cardLook1.draw(g, bCard1.isMouseOver());
        cardLook2.draw(g, bCard2.isMouseOver());
        cardLook3.draw(g, bCard3.isMouseOver());
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
            g.drawRect(mouseX, mouseY, tilePixelNumber, tilePixelNumber);
        }
    }

    private void drawSelectedTower(Graphics g) {
        if (selectedTower != null) {

            g.drawImage(towerManager.getTowerImg()[selectedTower.getTowerType()], mouseX, mouseY, null);

        }
    }

    private void drawLevel(Graphics g) {
        for (int y = 0; y < lvl.length; y++) {
            for (int x = 0; x < lvl[y].length; x++) {
                int id = lvl[y][x];
                if (isAnimation(id)) {
                    g.drawImage(getSprite(id, animationIndex), x * tilePixelNumber, y * tilePixelNumber, null);
                } else {
                    g.drawImage(getSprite(id), x * tilePixelNumber, y * tilePixelNumber, null);
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
        } else if (cardSelect) {
            if (bCard1.getBounds().contains(x, y)) {
                cardSelected(0);
            } else if (bCard2.getBounds().contains(x, y)) {
                cardSelected(1);
            } else if (bCard3.getBounds().contains(x, y)) {
                cardSelected(2);
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
        int id = lvl[y / tilePixelNumber][x / tilePixelNumber];
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
        mouseX = (x / tilePixelNumber) * tilePixelNumber;
        mouseY = (y / tilePixelNumber) * tilePixelNumber;
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
                bCard1.setMousePressed(true);
            } else if (bCard2.getBounds().contains(x, y)) {
                bCard2.setMousePressed(true);
            } else if (bCard3.getBounds().contains(x, y)) {
                bCard3.setMousePressed(true);
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

    public void rewardPlayer(int earnGold) {
        playingBar.earnGold(earnGold);
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
                selectedTower = new Archer(1555, 15555, 0, ARCHER,towerManager, road);
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_W) {
            if (playingBar.isEnoughGold(Constants.TowerType.getCost(CANNON))) {
                selectedTower = new Cannon(1555, 15555, 0, CANNON,towerManager, road);
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_E) {
            if (playingBar.isEnoughGold(Constants.TowerType.getCost(FROST_MAGE))) {
                selectedTower = new FrostMage(1555, 15555, 0, FROST_MAGE,towerManager, road);
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_R) {
            if (playingBar.isEnoughGold(Constants.TowerType.getCost(MINES_FACTORY))) {
                selectedTower = new MineFactory(1555, 15555, 0, MINES_FACTORY,towerManager, road);
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_T) {
            if (playingBar.isEnoughGold(Constants.TowerType.getCost(POISON_TOWER))) {
                selectedTower = new PoisonTower(1555, 15555, 0, POISON_TOWER,towerManager, road);
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_Y) {
            if (playingBar.isEnoughGold(Constants.TowerType.getCost(BOOM_VOLCANO))) {
                selectedTower = new BoomTower(1555, 15555, 0, BOOM_VOLCANO,towerManager, road);
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_A) {
            if (playingBar.isEnoughGold(Constants.TowerType.getCost(CROSSBOW))) {
                selectedTower = new Crossbow(1555, 15555, 0, CROSSBOW,towerManager, road);
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_S) {
            if (playingBar.isEnoughGold(Constants.TowerType.getCost(MOUSE_FOLLOWS_TOWER))) {
                selectedTower = new MauseFollowsTower(1555, 15555, 0, MOUSE_FOLLOWS_TOWER,towerManager, road);
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_D) {
            if (playingBar.isEnoughGold(Constants.TowerType.getCost(SNIPER))) {
                selectedTower = new Sniper(1555, 15555, 0, SNIPER,towerManager, road);
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_F) {
            if (playingBar.isEnoughGold(Constants.TowerType.getCost(LASER_TOWER))) {
                selectedTower = new LaserTower(1555, 15555, 0, LASER_TOWER,towerManager, road);
            }
        }
    }

    public EnemyManager getEnemyManager() {
        return enemyManager;
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
        enemyManager.reset();
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
        for (Card card : cards) {
            card.setActive(false);
        }
    }

    public void setMine(Tower t, PathPoint e) {
        projectileManager.newMine(t, e);
    }

    public ProjectileManager getProjectileManager() {
        return projectileManager;
    }

    public int[][] getRoadDirArr() {
        road = enemyManager.getRoadDirArr();
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
