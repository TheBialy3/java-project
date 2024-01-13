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
import looks.CardLook;
import objects.PathPoint;
import towers.*;
import ui.MyButton;
import ui.PlayingBar;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

import static helpz.Constants.Tiles.GRASS_TILE;
import static helpz.Constants.TowerType.*;
import static helpz.LoadSave.getCardChooseSprite;
import static helpz.LoadSave.getCardSprite;

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
    private Bestiary bestiary;
    private MyButton bReturn;
    private MyButton bReplay;
    private int shuffles = 2;
    private MyButton reshuffle;
    private MyButton bCard1, bCard2, bCard3;
    private BufferedImage card = getCardSprite(), cardChoose = getCardChooseSprite(), logos[] = loadLogos();
    private PathPoint start, end;
    private ArrayList<Beam> beams = new ArrayList<>();
    private ArrayList<Card> cards = new ArrayList<>();
    private ArrayList<Card> winCards = new ArrayList<>();
    private ArrayList<Card> threeCards = new ArrayList<>();
    private Random random = new Random();
    private Tower selectedTower;
    private int goldTick = 0, goldTickLimit = 60 * 13, passiveIncomeGold = 5, afterEveryThisNumberOfWaveIsCardSelect = 5;
    PlayGameState playState = PlayGameState.PLAY_PLAY;

    public Playing(Game game) {
        super(game);
        LoadDefaultLevel();
        projectileManager = new ProjectileManager(this);
        waveManager = new WaveManager(this);
        enemyManager = new EnemyManager(this, start, end, waveManager);
        towerManager = new TowerManager(this);
        playingBar = new PlayingBar(1280, 0, 256, 1280, this, game, towerManager);
        bestiary = game.getBestiary();
        initCardButtons();
        initBestiaryButton();
    }

    private void initBestiaryButton() {
        int w = 200;
        int h = w / 4;
        int x = 1536 / 2 - w / 2;
        int y = 1150;

        bReturn = new MyButton("Return", x, y, w, h);
    }

    private void initCardButtons() {
        int cardX = 140;
        int cardY = 100;
        int cardW = 1000;
        int cardH = 300;
        int diffCard = 400;
        int shuffleX = 640 - 75;
        int shuffleY = 20;
        int shuffleW = 150;
        int shuffleH = 50;

        bCard1 = new MyButton("Card1", cardX, cardY, cardW, cardH);
        bCard2 = new MyButton("Card2", cardX, cardY + diffCard, cardW, cardH);
        bCard3 = new MyButton("Card3", cardX, cardY + diffCard + diffCard, cardW, cardH);
        reshuffle = new MyButton("Shuffle ("+shuffles+")", shuffleX, shuffleY, shuffleW, shuffleH);
    }

    public enum PlayGameState {
        PLAY_PLAY,
        PLAY_PAUSED,
        PLAY_GAME_OVER,
        PLAY_START_OF_GAME,
        PLAY_WIN,
        PLAY_CARD_SELECT,
        PLAY_BESTIARY;
    }


    public void update() {
        switch (playState) {
            case PLAY_PLAY:
                updateTick();
                getWaveManager().update();
                passiveIncome();
                if (isAllEnemyDead()) {
                    if (isThereMoreWaves()) {
                        waveManager.startWaveTimer();
                        if (isWaveTimerOver()) {
                            if (waveManager.getWaveIndex() % afterEveryThisNumberOfWaveIsCardSelect == 1) {
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
                if (playingBar.getLives() <= 0) {
                    game.saveGame();
                    playState = PlayGameState.PLAY_GAME_OVER;
                }
                break;
            case PLAY_PAUSED:

                break;
            case PLAY_GAME_OVER:

                break;
            case PLAY_START_OF_GAME:

                break;
            case PLAY_WIN:

                break;
            case PLAY_CARD_SELECT:

                break;
            case PLAY_BESTIARY:

                break;

        }
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

        if (playState.equals(PlayGameState.PLAY_CARD_SELECT)) {
            drawCardsToSelect(g);
        }
        if (playState.equals(PlayGameState.PLAY_WIN)) {
            drawWinScreen(g);
        }
        if (playState.equals(PlayGameState.PLAY_PAUSED)) {
            drawPause(g);
        }
        if (playState.equals(PlayGameState.PLAY_BESTIARY)) {
            bestiary.drawIconsImg(g);
            bReturn.draw(g);
        }

    }

    private void cardSelectStart() {
        threeCards.clear();
        getCards();
        choose3RandomCards();
        createLookOfCards();
      playState = PlayGameState.PLAY_CARD_SELECT;
    }

    private void createLookOfCards() {
        int cardX = 140;
        int cardY = 100;
        int diffCard = 400;
        logos = loadLogos();
        cardLook1 = new CardLook(threeCards.get(0).getName(), threeCards.get(0).getDescription(), threeCards.get(0).getTowertype(), logos[threeCards.get(0).getId()], card, cardChoose, cardX, cardY);
        cardLook2 = new CardLook(threeCards.get(1).getName(), threeCards.get(1).getDescription(), threeCards.get(1).getTowertype(), logos[threeCards.get(1).getId()], card, cardChoose, cardX, cardY + diffCard);
        cardLook3 = new CardLook(threeCards.get(2).getName(), threeCards.get(2).getDescription(), threeCards.get(2).getTowertype(), logos[threeCards.get(2).getId()], card, cardChoose, cardX, cardY + diffCard + diffCard);
    }

    private void cardSelected(int cardChosen) {
        System.out.println(threeCards.get(cardChosen).getId());
        switch (threeCards.get(cardChosen).getId()) {
            case 0: //for all
                towerManager.damageUp(10);
                towerManager.setCard0(true);
                break;
            case 1:
                towerManager.speedUp(10);
                towerManager.setCard1(true);
                break;
            case 2:
                towerManager.rangeUp(10);
                towerManager.setCard2(true);
                break;
            case 3:
                towerManager.upgradesTrue();
                towerManager.setCard3(true);
                break;
            case 4:
                towerManager.speedUp(10);
                towerManager.setCard4(true);
                break;
            case 5:
                towerManager.damageUp(10);
                towerManager.setCard5(true);
                break;
            case 6:
                towerManager.durationUp(10);
                towerManager.setCard6(true);
                break;
            case 7:   //ENEMY
                enemyManager.setCard7(true);
                break;
            case 8:
                enemyManager.setCard8(true);
                break;
            case 9:
                enemyManager.setCard9(true);
                break;
            case 10:
                enemyManager.setCard10(true);
                break;
            case 11:
                enemyManager.setCard11(true);
                break;
            case 12:
                enemyManager.setCard12(true);
                towerManager.setCard12(true);
                projectileManager.setCard12(true);
                break;
            case 13: //ARCHER
                projectileManager.setCard13(true);
                break;
            case 14:
                towerManager.setCard14(true);
                towerManager.speedUp(30, ARCHER);
                break;
            case 15:
                towerManager.setCard15(true);
                towerManager.damageUp(30, ARCHER);
                break;
            case 16:    //CANNON
                projectileManager.setCard16(true);
                break;
            case 17:
                towerManager.setCard17(true);
                towerManager.speedUp(30, CANNON);
                break;
            case 18:
                towerManager.setCard18(true);
                towerManager.damageUp(30, CANNON);
                break;
            case 19:  //FROST_MAGE
                towerManager.setCard19(true);
                towerManager.rangeUp(3000, FROST_MAGE);
                towerManager.slowChange(160, FROST_MAGE);//weaker slow
                break;
            case 20:
                towerManager.setCard20(true);
                towerManager.setDamage(5, FROST_MAGE);
                break;
            case 21:
                towerManager.setCard21(true);
                towerManager.slowChange(63, FROST_MAGE);//stronger slow
                break;
            case 22:        //MINES_FACTORY
                projectileManager.setCard22(true);
                break;
            case 23:
                towerManager.setCard23(true);
                break;
            case 24:
                towerManager.setCard24(true);
                towerManager.damageUp(30, MINES_FACTORY);
                break;
            case 25:        //POISON_TOWER
                projectileManager.setCard25(true);
                break;
            case 26:
                towerManager.setCard26(true);
                towerManager.damageUp(100, POISON_TOWER);
                break;
            case 27:
                towerManager.durationUp(30, POISON_TOWER);
                towerManager.setCard27(true);
                break;
            case 28://BOOM_VOLCANO
                towerManager.setCard28(true);
                break;
            case 29:
                towerManager.setCard29(true);
                towerManager.speedUp(30, BOOM_VOLCANO);
                break;
            case 30:
                towerManager.damageUp(30, BOOM_VOLCANO);
                towerManager.setCard30(true);
                break;
            case 31://CROSSBOW
                towerManager.damageUp(30, CROSSBOW);
                towerManager.setCard31(true);
                break;
            case 32:
                towerManager.setCard32(true);
                towerManager.speedUp(30, CROSSBOW);
                break;
            case 33:
                projectileManager.setCard33(true);
                break;
            case 34://MOUSE_FOLLOWS_TOWER
                towerManager.setCard34(true);
                towerManager.speedUp(30, MOUSE_FOLLOWS_TOWER);
                break;
            case 35:
                towerManager.damageUp(30, MOUSE_FOLLOWS_TOWER);
                towerManager.setCard35(true);
                break;
            case 36:
                projectileManager.setCard36(true);
                break;
            case 37:  //SNIPER
                towerManager.damageUp(30, SNIPER);
                towerManager.setCard37(true);
                break;
            case 38:
                towerManager.setCard38(true);
                towerManager.speedUp(30, SNIPER);
                break;
            case 39:
                towerManager.setCard39(true);
                break;
            case 40: //LASER_TOWER
                towerManager.setCard40(true);
                break;
            case 41:
                towerManager.rangeUp(20, LASER_TOWER);
                towerManager.setCard41(true);
                break;
            case 42:
                towerManager.damageUp(30, LASER_TOWER);
                towerManager.setCard42(true);
                break;
            case 43:
                break;
            case 44:
                break;
            case 45:
                break;
            case 46:
                break;
            case 47:
                break;
            case 48:
                break;
            case 49:
                break;
            case 50:
                break;
            case 51:
                break;
            case 52:
                break;
            case 53:
                break;
            case 54:
                break;
            case 55:
                break;
            case 56:
                break;
            case 57:
                break;
            case 58:
                break;
            case 59:
                break;
            case 60:
                break;
            case 61:
                break;
            case 62:
                break;
            case 63:
                break;
            case 64:
                break;
            case 65:
                break;
            case 66:
                break;
            case 67:
                break;
            case 68:
                break;
            case 69:
                break;
            default:
                break;
        }
        cards.get(threeCards.get(cardChosen).getId()).setActive(true);
        playState = PlayGameState.PLAY_PLAY;
    }


    private void choose3RandomCards() {
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

    private void shuffleCards() {
        shuffles--;
        threeCards.clear();
        choose3RandomCards();
        createLookOfCards();
        reshuffle.setText("Shuffle ("+shuffles+")");
    }

    public void Finish() {
        game.checkLocked(cards);
        game.saveGame();
        playState = PlayGameState.PLAY_WIN;
    }

    private BufferedImage[] loadLogos() {
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
            if (enemyManager.isCard9()) {
                passiveIncomeGold *= 2;
            }
            if (enemyManager.isCard10()) {
                passiveIncomeGold *= 2;
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


    private void drawCardsToSelect(Graphics g) {
        bCard1.draw(g);
        bCard2.draw(g);
        bCard3.draw(g);

        cardLook1.draw(g, bCard1.isMouseOver());
        cardLook2.draw(g, bCard2.isMouseOver());
        cardLook3.draw(g, bCard3.isMouseOver());

        if(shuffles>0){
            reshuffle.draw(g);
        }else {
            reshuffle.drawUnActive(g);
        }
    }

    private void drawBeam(Graphics g) {
        try{
        for (Beam beam : beams) {
            if (beam.getActive()) {
                g.setColor(new Color(255, 0, 0));
                g.drawLine(beam.getxStart(), beam.getyStart(), beam.getxEnd(), beam.getyEnd());
                beam.timerDown();
            }
        }}catch (Exception e){
            System.out.println("ConcurrentModificationException beam");
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
    public void mouseClicked(int x, int y) {
        if (x >= 1280) {
            playingBar.mouseClicked(x, y);
        } else if (playState.equals(PlayGameState.PLAY_WIN)) {
            if (bReplay.getBounds().contains(x, y)) {
                resetEverything();
            }
        } else if (playState.equals(PlayGameState.PLAY_CARD_SELECT)) {
            if (bCard1.getBounds().contains(x, y)) {
                cardSelected(0);
            } else if (bCard2.getBounds().contains(x, y)) {
                cardSelected(1);
            } else if (bCard3.getBounds().contains(x, y)) {
                cardSelected(2);
            } else if (reshuffle.getBounds().contains(x, y)) {
                if(shuffles>0) {
                    shuffleCards();
                }
            }
        } else if (playState.equals(PlayGameState.PLAY_PAUSED)) {
            playState = PlayGameState.PLAY_PLAY;
        } else if (playState.equals(PlayGameState.PLAY_BESTIARY)) {
            if (bReturn.getBounds().contains(x, y)) {
                playState = PlayGameState.PLAY_PLAY;
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

    }



    @Override
    public void mouseMoved(int x, int y) {
        mX = x;
        mY = y;
        mouseX = (x / tilePixelNumber) * tilePixelNumber;
        mouseY = (y / tilePixelNumber) * tilePixelNumber;
        if (playState.equals(PlayGameState.PLAY_CARD_SELECT)) {
            bCard1.setMouseOver(false);
            bCard2.setMouseOver(false);
            bCard3.setMouseOver(false);
            reshuffle.setMouseOver(false);
            if (bCard1.getBounds().contains(x, y)) {
                bCard1.setMouseOver(true);
            } else if (bCard2.getBounds().contains(x, y)) {
                bCard2.setMouseOver(true);
            } else if (bCard3.getBounds().contains(x, y)) {
                bCard3.setMouseOver(true);
            } else if (reshuffle.getBounds().contains(x, y)) {
                reshuffle.setMouseOver(true);
            }
        }
        if (playState.equals(PlayGameState.PLAY_WIN)) {
            bReplay.setMouseOver(false);
        }
        if (x >= 1280) {
            playingBar.mouseMoved(x, y);

        } else if (playState.equals(PlayGameState.PLAY_WIN)) {
            if (bReplay.getBounds().contains(x, y)) {
                bReplay.setMouseOver(true);
            }
        }
        if (playState.equals(PlayGameState.PLAY_BESTIARY)) {
            bReturn.setMouseOver(false);
            bestiary.mouseMoved(x, y);
            if (bReturn.getBounds().contains(x, y)) {
                bReturn.setMouseOver(true);
            }
        }
    }

    @Override
    public void mouseReleased(int x, int y) {
        if (playState.equals(PlayGameState.PLAY_CARD_SELECT)) {
            bCard1.resetBooleans();
            bCard2.resetBooleans();
            bCard3.resetBooleans();
            reshuffle.resetBooleans();
        }else if (playState.equals(PlayGameState.PLAY_WIN)) {
            bReplay.resetBooleans();
        }else if (playState.equals(PlayGameState.PLAY_BESTIARY)) {
            bReturn.resetBooleans();
        }
        if (x >= 1280) {
            playingBar.mouseReleased(x, y);
        }
    }


    @Override
    public void mousePressed(int x, int y) {
        if (x >= 1280) {
            playingBar.mousePressed(x, y);
        }
        if (playState.equals(PlayGameState.PLAY_CARD_SELECT)) {
            if (bCard1.getBounds().contains(x, y)) {
                bCard1.setMousePressed(true);
            } else if (bCard2.getBounds().contains(x, y)) {
                bCard2.setMousePressed(true);
            } else if (bCard3.getBounds().contains(x, y)) {
                bCard3.setMousePressed(true);
            } else if (reshuffle.getBounds().contains(x, y)) {
                reshuffle.setMousePressed(true);
            }
        } else if (playState.equals(PlayGameState.PLAY_WIN)) {
            if (bReplay.getBounds().contains(x, y)) {
                bReplay.setMousePressed(true);
            }
        }else if (playState.equals(PlayGameState.PLAY_BESTIARY)) {
            if (bReturn.getBounds().contains(x, y)) {
                bReturn.setMousePressed(true);
            }
        }
    }

    @Override
    public void mouseDragged(int x, int y) {
        mouseClicked(x, y);
    }

    public void keyPressed(KeyEvent e) {
        if (playState.equals(PlayGameState.PLAY_PAUSED)) {
            playState = PlayGameState.PLAY_PLAY;
        }
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            if (playState.equals(PlayGameState.PLAY_PLAY)) {
                playState = PlayGameState.PLAY_PAUSED;
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_Q) {
            if (playingBar.isEnoughGold(Constants.TowerType.getCost(ARCHER))) {
                selectedTower = new Archer(1555, 15555, 0, ARCHER, towerManager, road);
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_W) {
            if (playingBar.isEnoughGold(Constants.TowerType.getCost(CANNON))) {
                selectedTower = new Cannon(1555, 15555, 0, CANNON, towerManager, road);
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_E) {
            if (playingBar.isEnoughGold(Constants.TowerType.getCost(FROST_MAGE))) {
                selectedTower = new FrostMage(1555, 15555, 0, FROST_MAGE, towerManager, road);
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_R) {
            if (playingBar.isEnoughGold(Constants.TowerType.getCost(MINES_FACTORY))) {
                selectedTower = new MineFactory(1555, 15555, 0, MINES_FACTORY, towerManager, road);
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_T) {
            if (playingBar.isEnoughGold(Constants.TowerType.getCost(POISON_TOWER))) {
                selectedTower = new PoisonTower(1555, 15555, 0, POISON_TOWER, towerManager, road);
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_Y) {
            if (playingBar.isEnoughGold(Constants.TowerType.getCost(BOOM_VOLCANO))) {
                selectedTower = new BoomTower(1555, 15555, 0, BOOM_VOLCANO, towerManager, road);
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_A) {
            if (playingBar.isEnoughGold(Constants.TowerType.getCost(CROSSBOW))) {
                selectedTower = new Crossbow(1555, 15555, 0, CROSSBOW, towerManager, road);
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_S) {
            if (playingBar.isEnoughGold(Constants.TowerType.getCost(MOUSE_FOLLOWS_TOWER))) {
                selectedTower = new MauseFollowsTower(1555, 15555, 0, MOUSE_FOLLOWS_TOWER, towerManager, road);
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_D) {
            if (playingBar.isEnoughGold(Constants.TowerType.getCost(SNIPER))) {
                selectedTower = new Sniper(1555, 15555, 0, SNIPER, towerManager, road);
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_F) {
            if (playingBar.isEnoughGold(Constants.TowerType.getCost(LASER_TOWER))) {
                selectedTower = new LaserTower(1555, 15555, 0, LASER_TOWER, towerManager, road);
            }
        }
    }


    public void mouseClickedR() {
        setSelectedTower(null);
        if (playState.equals(PlayGameState.PLAY_PAUSED)) {
            playState = PlayGameState.PLAY_PLAY;
        }
    }

    public void rewardPlayerAfterWave() {
        int endWaveGold = 150;
        playingBar.earnGold(endWaveGold);
    }

    public void rewardPlayer(int earnGold) {
        playingBar.earnGold(earnGold);
    }

    public void setSelectedTower(Tower selectedTower) {
        this.selectedTower = selectedTower;
    }

    public void removeOneLive() {
        playingBar.removeOneLive();
    }



    public void setMine(Tower t, PathPoint e) {
        projectileManager.newMine(t, e);
    }

    public void resetEverything() {
        playingBar.resetEvrything();
        enemyManager.reset();
        projectileManager.reset();
        towerManager.reset();
        waveManager.reset();
        mouseX = 0;
        mouseY = 0;
        selectedTower = null;
        goldTick = 0;
        shuffles = 2;
        beamReset();
        playState = PlayGameState.PLAY_PLAY;
        for (Card card : cards) {
            card.setActive(false);
        }
        winCards.clear();
    }
    public ProjectileManager getProjectileManager() {
        return projectileManager;
    }

    public void beamEnemy(Tower t, Enemy e) {
        beams.add(new Beam(t.getX() + 45, t.getY() + 20, (int) e.getX() + 32, (int) e.getY() + 32));
    }

    public void beamReset() {
        beams.clear();
    }

    public EnemyManager getEnemyManager() {
        return enemyManager;
    }

    public WaveManager getWaveManager() {
        return waveManager;
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

    private void getCards() {
        cards = game.getCards();
    }

    private Tower getTowerAt(int x, int y) {
        return towerManager.getTowerAt(x, y);
    }

    public TowerManager getTowerManager() {
        return towerManager;
    }

}
