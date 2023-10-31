package ui;


import helpz.Constants;
import main.Game;
import managers.TowerManager;
import scenes.Playing;
import towers.*;

import java.awt.*;
import java.text.DecimalFormat;

import static helpz.Constants.TowerType.*;
import static main.GameStates.*;

public class PlayingBar extends Bar {

    private Playing playing;
    private Game game;
    private TowerManager towerManager;
    private MyButton bMenu, bReset;
    private MyButton bUpgrade1, bUpgrade2, bUpgrade3, bSell;
    private MyButton[] towerButtons;
    private Tower selectedTower;
    private Tower displayedTower;
    private String textUp1, textUp2, textUp3;
    private int startingGold = 4000;
    private int gold = startingGold, costUp1, costUp2, costUp3;

    private boolean showTowerCost;

    private int towerCostId;

    private int lives = 5;

    private int[][] road;

    private static final DecimalFormat decfor = new DecimalFormat("0.00");

    public PlayingBar(int x, int y, int width, int height, Playing playing, Game game,TowerManager towerManager) {
        super(x, y, width, height);
        this.playing = playing;
        this.game = game;
        initButtons();
        this.towerManager=towerManager;
    }

    public void resetEvrything() {
        lives = 5;
        towerCostId = 0;
        showTowerCost = false;
        gold = startingGold;
        selectedTower = null;
        displayedTower = null;
    }

    public void draw(Graphics g) {
        //backGround
        g.setColor(new Color(0, 102, 102));
        g.fillRect(x, y, width, height);

        //buttons
        drawButtons(g);

        //displayTower
        drawDisplayTower(g);

        //waveInfo
        drawWaveInfo(g);

        //GoldInfo
        drawGoldAmount(g);

        //towerInfo
        if (showTowerCost) {
            drawTowerCost(g);
        }
        //towerSellInfo
        if (bSell.isMouseOver()) {
            drawTowerSellCost(g);
        }


    }

    private void drawTowerSellCost(Graphics g) {
        g.setColor(new Color(7, 43, 186));
        g.fillRect(1290, 890, 236, 90);
        g.setColor(Color.black);
        g.drawRect(1290, 890, 236, 90);
        g.setFont(new Font("Serif", Font.BOLD, 30));
        g.setColor(new Color(15, 15, 15));
        String text = "Gold Worth: " + (int) (getTowerCost() * 0.8) + "g";
        int w = g.getFontMetrics().stringWidth(text);
        g.drawString(text, 1408 - (w / 2), 960);
        g.setFont(new Font("Serif", Font.BOLD, 28));
        text = getTowerName(towerCostId);
        w = g.getFontMetrics().stringWidth(text);
        g.drawString(text, 1408 - (w / 2), 923);
    }


    private void drawTowerCost(Graphics g) {
        if (getTowerCost(towerCostId) > gold) {
            g.setColor(new Color(171, 0, 0));
        } else {
            g.setColor(new Color(3, 132, 0));
        }
        g.fillRect(1290, 890, 236, 90);
        g.setColor(Color.black);
        g.drawRect(1290, 890, 236, 90);
        g.setFont(new Font("Serif", Font.BOLD, 30));
        g.setColor(new Color(15, 15, 15));
        String text = "Cost: " + getTowerCost(towerCostId) + "g";
        int w = g.getFontMetrics().stringWidth(text);
        g.drawString(text, 1408 - (w / 2), 960);
        g.setFont(new Font("Serif", Font.BOLD, 28));
        text = getTowerName(towerCostId);
        w = g.getFontMetrics().stringWidth(text);
        g.drawString(text, 1408 - (w / 2), 923);
    }

    private int getTowerCost(int towerCostId) {
        return Constants.TowerType.getCost(towerCostId);
    }

    private int getTowerCost() {
        return displayedTower.getWorthGold();
    }

    private String getTowerName(int towerCostId) {
        return Constants.TowerType.getName(towerCostId);
    }

    private void drawWaveInfo(Graphics g) {
        g.setFont(new Font("Monospaced", Font.BOLD, 20));
        drawLives(g);
        drawEnemisLeftInfo(g);
        drawWaveLeftInfo(g);
    }

    private void drawGoldAmount(Graphics g) {
        g.setFont(new Font("Serif", Font.BOLD, 20));
        g.setColor(new Color(196, 174, 0));
        g.drawString("Gold:" + gold, 1285, 1210);
    }

    private void drawEnemisLeftInfo(Graphics g) {
        g.setColor(new Color(9, 255, 255));
        int currentWave = playing.getEnemyManager().getAmountOfAliveEnemies();
        g.drawString("Enemies alive:" + currentWave, 1285, 1250);
    }

    private void drawWaveLeftInfo(Graphics g) {
        g.setColor(new Color(9, 255, 255));
        int currentWave = playing.getWaveManager().getWaveIndex();
        int allWaves = playing.getWaveManager().getWaveMax();
        g.drawString("Waves:" + currentWave + "/" + allWaves, 1285, 1270);
    }

    private void drawLives(Graphics g) {

        g.setColor(new Color(171, 0, 0));
        g.drawString("Lives Left:" + lives, 1285, 1230);

    }

    //256, 1280,
    private void drawDisplayTower(Graphics g) {
        if (displayedTower != null || showTowerCost) {
            g.setColor(new Color(100, 45, 15));
            g.fillRect(1290, 990, 236, 180);
            g.setColor(Color.black);
//            if (displayedTower != null){g.drawString("" + displayedTower.getId(), 111, 111);}
            g.drawRect(1301, 1000, 64, 64);
            g.drawRect(1290, 990, 236, 180);
            g.setColor(new Color(45, 45, 45, 109));
            g.fillRect(1301, 1000, 64, 64);
            if (displayedTower == null) {
                g.drawImage(playing.getTowerManager().getTowerImg()[towerCostId], 1301, 1000, 64, 64, null);
            } else {
                g.drawImage(playing.getTowerManager().getTowerImg()[displayedTower.getTowerType()], 1301, 1000, 64, 64, null);
            }
            g.setFont(new Font("Serif", Font.BOLD, 20));
            g.setColor(new Color(15, 15, 15));
            if (displayedTower == null) {
                g.drawString("" + Constants.TowerType.getName(towerCostId), 1375, 1018);
                g.drawString("Range:" + Constants.TowerType.getDefaultRange(towerCostId), 1375, 1038);
                if (towerCostId == FROST_MAGE) {
                    g.drawString("Slow:" + 100 * Constants.TowerType.getPowerOfSlow(towerCostId) + "%", 1375, 1058);
                } else {
                    g.drawString("DMG:" + Constants.TowerType.getDefaultDmg(towerCostId), 1375, 1058);

                    g.drawString("AS:" + decfor.format(60 / Constants.TowerType.getDefaultCoolDown(towerCostId)), 1375, 1078);
                }
            } else {
                g.drawString("" + Constants.TowerType.getName(displayedTower.getTowerType()), 1375, 1018);
                g.drawString("Range:" + displayedTower.getRange(), 1375, 1038);
                if (displayedTower.getTowerType() == FROST_MAGE) {
                    g.drawString("Slow:" + 100 * Constants.TowerType.getPowerOfSlow(displayedTower.getTowerType()) + "%", 1375, 1058);
                } else {
                    g.drawString("DMG:" + displayedTower.getDmg(), 1375, 1058);
                    g.drawString("AS:" +  decfor.format(60 / displayedTower.getCoolDown()), 1375, 1078);
                }
            }
            if (displayedTower != null) {
                bSell.draw(g);
                if (!displayedTower.isUpgrade1Active()) {
                    bUpgrade1.draw(g);
                }
                if (!displayedTower.isUpgrade2Active()) {
                    bUpgrade2.draw(g);
                }
                if (!displayedTower.isUpgrade3Active()) {
                    bUpgrade3.draw(g);
                }
                drawUpgrade(g);
                drawDisplayedTowerRect(g);
                drawDisplayedTowerRange(g);
                upgradeDescription(g);
            }
        }
    }

    public void removeOneLive() {
        lives--;
        if (lives <= 0) {
            SetGameState(GAME_OVER);
        }
    }

    private void drawDisplayedTowerRange(Graphics g) {
        if (displayedTower.getTowerType() == MINES_FACTORY) {
            g.setColor(Color.BLACK);
            g.drawRect((int) (displayedTower.getX() - displayedTower.getRange()) + 32, (int) (displayedTower.getY() - displayedTower.getRange()) + 32, (int) (displayedTower.getRange() * 2), (int) (displayedTower.getRange()) * 2);
        } else {
            g.setColor(Color.BLACK);
            g.drawOval((int) (displayedTower.getX() - displayedTower.getRange()) + 32, (int) (displayedTower.getY() - displayedTower.getRange()) + 32, (int) (displayedTower.getRange() * 2), (int) (displayedTower.getRange()) * 2);
        }
    }

    private void drawDisplayedTowerRect(Graphics g) {
        g.setColor(Color.BLACK);
        g.drawRect(displayedTower.getX(), displayedTower.getY(), 64, 64);
    }


    private void initButtons() {
        int w = 64;
        int h = 64;
        int x = 1293;
        int y = 64;
        int diff = 84;

        bMenu = new MyButton("Menu", 1293, 10, 108, 40);
        bReset = new MyButton("Reset", 1417, 10, 108, 40);

        towerButtons = new MyButton[10];

        for (int i = 0; i < towerButtons.length; i++) {
            int row = i / 3;
            int rest = i % 3;
            towerButtons[i] = new MyButton("", x + diff * rest, y + diff * row, w, h, i);
        }

        bSell = new MyButton("Sell ", 1401, 1180, 125, 27);
        bUpgrade1 = new MyButton("", 1301, 1095, 64, 64);
        bUpgrade2 = new MyButton("", 1376, 1095, 64, 64);
        bUpgrade3 = new MyButton("", 1451, 1095, 64, 64);

    }

    public void upgradeDescription(Graphics g) {
        if (bUpgrade1.isMouseOver() || bUpgrade2.isMouseOver() || bUpgrade3.isMouseOver()) {
            g.setColor(new Color(100, 45, 15));
            g.fillRect(700, 990, 566, 84);
            g.setColor(Color.black);
            g.drawRect(700, 990, 566, 84);
            g.fillRect(710, 1000, 64, 64);
            g.setColor(new Color(15, 15, 15));
            g.drawRect(710, 1000, 64, 64);
            g.drawImage(playing.getTowerManager().getTowerImg()[displayedTower.getTowerType()], 710, 1000, 64, 64, null);
            g.setFont(new Font("Serif", Font.BOLD, 30));
            String text;
            if (bUpgrade1.isMouseOver()) {
                text = "Cost: " + costUp1 + "g";
            } else if (bUpgrade2.isMouseOver()) {
                text = "Cost: " + costUp2 + "g";
            } else {
                text = "Cost: " + costUp3 + "g";
            }
            g.drawString(text, 794, 1022);
            g.setFont(new Font("Serif", Font.BOLD, 28));
            if (bUpgrade1.isMouseOver()) {
                g.drawString(textUp1, 794, 1059);
            } else if (bUpgrade2.isMouseOver()) {
                g.drawString(textUp2, 794, 1059);
            } else {
                g.drawString(textUp3, 794, 1059);
            }

        }
    }


    private void drawUpgrade(Graphics g) {
        if (!displayedTower.isUpgrade1Active()) {
            if (!isEnoughGold(costUp1)) {
                g.setColor(new Color(171, 0, 0));
            } else {
                g.setColor(new Color(3, 132, 0));
            }
            g.fillRect(1301, 1095, 64, 64);
            g.drawImage(playing.getTowerManager().getUpgradeImg()[Constants.TowerType.getUpgradeTowerImage(displayedTower.getTowerType(), 1)], 1301, 1095, 64, 64, null);
        }
        if (!displayedTower.isUpgrade2Active()) {
            if (!isEnoughGold(costUp2)) {
                g.setColor(new Color(171, 0, 0));
            } else {
                g.setColor(new Color(3, 132, 0));
            }
            g.fillRect(1376, 1095, 64, 64);
            g.drawImage(playing.getTowerManager().getUpgradeImg()[Constants.TowerType.getUpgradeTowerImage(displayedTower.getTowerType(), 2)], 1376, 1095, 64, 64, null);
        }
        if (!displayedTower.isUpgrade3Active()) {
            if (!isEnoughGold(costUp3)) {
                g.setColor(new Color(171, 0, 0));
            } else {
                g.setColor(new Color(3, 132, 0));
            }
            g.fillRect(1451, 1095, 64, 64);
            g.drawImage(playing.getTowerManager().getUpgradeImg()[Constants.TowerType.getUpgradeTowerImage(displayedTower.getTowerType(), 3)], 1451, 1095, 64, 64, null);
        }
    }


    public void earnGold(int getCost) {
        gold += getCost;
    }

    public void goldSpend(int getCost) {
        gold -= getCost;
    }

    private void drawButtons(Graphics g) {
        bMenu.draw(g);
        bReset.draw(g);
        for (MyButton b : towerButtons) {
            if (getTowerCost(b.getId()) < gold) {
                g.setColor(new Color(3, 132, 0));
            } else {
                g.setColor(new Color(171, 0, 0));
            }
            g.fillRect(b.x, b.y, b.width, b.height);
            g.drawImage(playing.getTowerManager().getTowerImg()[b.getId()], b.x, b.y, b.width, b.height, null);
            drawButtonFeedback(g, b);
        }
    }

    private void sellTowerClicked() {
        playing.removeTower(displayedTower);
        gold += getTowerCost() * 0.8;
        displayedTower = null;
    }


    public void mouseClicked(int x, int y) {
        if (bMenu.getBounds().contains(x, y)) {
            SetGameState(MENU);
        } else if (bReset.getBounds().contains(x, y)) {
            playing.resetEverything();
        } else {
            if (displayedTower != null) {
                if (bSell.getBounds().contains(x, y)) {
                    sellTowerClicked();
                }
                if (bUpgrade1.getBounds().contains(x, y)) {
                    if (isEnoughGold(costUp1)) {
                        if (!displayedTower.isUpgrade1Active()) {
                            displayedTower.Upgrade1Activate();
                            goldSpend(costUp1);
                            displayedTower.updateTowerWorthGold(costUp1);
                        }
                    }
                }
                if (bUpgrade2.getBounds().contains(x, y)) {
                    if (isEnoughGold(costUp2)) {
                        if (!displayedTower.isUpgrade2Active()) {
                            displayedTower.Upgrade2Activate();
                            goldSpend(costUp2);
                            displayedTower.updateTowerWorthGold(costUp2);
                        }
                    }
                }
                if (bUpgrade3.getBounds().contains(x, y)) {
                    if (isEnoughGold(costUp3)) {
                        if (!displayedTower.isUpgrade3Active()) {
                            displayedTower.Upgrade3Activate();
                            goldSpend(costUp3);
                            displayedTower.updateTowerWorthGold(costUp3);
                        }
                    }
                }
            }
        }
        for (MyButton b : towerButtons) {
            if (b.getBounds().contains(x, y)) {
                if (isEnoughGold(Constants.TowerType.getCost(b.getId()))) {
                    switch (b.getId()) {
                        case ARCHER:
                            selectedTower = new Archer(x, y, 0, b.getId(),towerManager, road);
                            break;
                        case CANNON:
                            selectedTower = new Cannon(x, y, 0, b.getId(),towerManager, road);
                            break;
                        case FROST_MAGE:
                            selectedTower = new FrostMage(x, y, 0, b.getId(),towerManager, road);
                            break;
                        case MINES_FACTORY:
                            road = playing.getRoadDirArr();
                            selectedTower = new MineFactory(x, y, 0, b.getId(),towerManager, road);
                            break;
                        case POISON_TOWER:
                            selectedTower = new PoisonTower(x, y, 0, b.getId(),towerManager, road);
                            break;
                        case BOOM_VOLCANO:
                            selectedTower = new BoomTower(x, y, 0, b.getId(),towerManager, road);
                            break;
                        case CROSSBOW:
                            selectedTower = new Crossbow(x, y, 0, b.getId(),towerManager, road);
                            break;
                        case MOUSE_FOLLOWS_TOWER:
                            selectedTower = new MauseFollowsTower(x, y, 0, b.getId(),towerManager, road);
                            break;
                        case SNIPER:
                            selectedTower = new Sniper(x, y, 0, b.getId(),towerManager, road);
                            break;
                        case LASER_TOWER:
                            selectedTower = new LaserTower(x, y, 0, b.getId(),towerManager, road);
                            break;
                    }
                    playing.setSelectedTower(selectedTower);
                    return;
                }
            }
        }
    }


    public void mouseDragged(int x, int y) {
        mouseClicked(x, y);
    }

    public void mouseMoved(int x, int y) {
        bMenu.setMouseOver(false);
        bReset.setMouseOver(false);
        showTowerCost = false;
        bSell.setMouseOver(false);
        bUpgrade1.setMouseOver(false);
        bUpgrade2.setMouseOver(false);
        bUpgrade3.setMouseOver(false);
        for (MyButton b : towerButtons) {
            b.setMouseOver(false);
        }
        if (bMenu.getBounds().contains(x, y)) {
            bMenu.setMouseOver(true);
        } else if (bReset.getBounds().contains(x, y)) {
            bReset.setMouseOver(true);
        } else {
            if (displayedTower != null) {
                if (bSell.getBounds().contains(x, y)) {
                    bSell.setMouseOver(true);
                } else if (bUpgrade1.getBounds().contains(x, y)) {
                    if (!displayedTower.isUpgrade1Active()) {
                        bUpgrade1.setMouseOver(true);
                    }
                } else if (bUpgrade2.getBounds().contains(x, y)) {
                    if (!displayedTower.isUpgrade1Active()) {
                        bUpgrade2.setMouseOver(true);
                    }
                } else if (bUpgrade3.getBounds().contains(x, y)) {
                    if (!displayedTower.isUpgrade1Active()) {
                        bUpgrade3.setMouseOver(true);
                    }
                }
            }
            for (MyButton b : towerButtons) {
                if (b.getBounds().contains(x, y)) {
                    b.setMouseOver(true);
                    showTowerCost = true;
                    towerCostId = b.getId();
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
            game.saveGame();
            bMenu.setMousePressed(true);
        } else if (bReset.getBounds().contains(x, y)) {
            game.saveGame();
            bReset.setMousePressed(true);
        } else {
            if (displayedTower != null) {
                if (bSell.getBounds().contains(x, y)) {
                    bSell.setMousePressed(true);
                } else if (bUpgrade1.getBounds().contains(x, y)) {
                    bUpgrade1.setMousePressed(true);
                } else if (bUpgrade2.getBounds().contains(x, y)) {
                    bUpgrade2.setMousePressed(true);
                } else if (bUpgrade3.getBounds().contains(x, y)) {
                    bUpgrade3.setMousePressed(true);
                }
            }
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
        bReset.resetBooleans();
        bSell.resetBooleans();
        bUpgrade1.resetBooleans();
        bUpgrade2.resetBooleans();
        bUpgrade3.resetBooleans();
        for (MyButton b : towerButtons) {
            b.resetBooleans();
        }
    }

    public boolean isEnoughGold(int cost) {
        return gold >= cost;
    }

    public int getLives() {
        return lives;
    }

    public void displayTower(Tower t) {
        displayedTower = t;
        if (displayedTower instanceof Archer) {
            costUp1 = ((Archer) displayedTower).getCost(1);
            textUp1 = ((Archer) displayedTower).getName(1);
        } else if (displayedTower instanceof Cannon) {
            costUp1 = ((Cannon) displayedTower).getCost(1);
            textUp1 = ((Cannon) displayedTower).getName(1);
        } else if (displayedTower instanceof FrostMage) {
            costUp1 = ((FrostMage) displayedTower).getCost(1);
            textUp1 = ((FrostMage) displayedTower).getName(1);
        } else if (displayedTower instanceof MineFactory) {
            costUp1 = ((MineFactory) displayedTower).getCost(1);
            textUp1 = ((MineFactory) displayedTower).getName(1);
        } else if (displayedTower instanceof PoisonTower) {
            costUp1 = ((PoisonTower) displayedTower).getCost(1);
            textUp1 = ((PoisonTower) displayedTower).getName(1);
        } else if (displayedTower instanceof BoomTower) {
            costUp1 = ((BoomTower) displayedTower).getCost(1);
            textUp1 = ((BoomTower) displayedTower).getName(1);
        } else if (displayedTower instanceof Crossbow) {
            costUp1 = ((Crossbow) displayedTower).getCost(1);
            textUp1 = ((Crossbow) displayedTower).getName(1);
        } else if (displayedTower instanceof MauseFollowsTower) {
            costUp1 = ((MauseFollowsTower) displayedTower).getCost(1);
            textUp1 = ((MauseFollowsTower) displayedTower).getName(1);
        } else if (displayedTower instanceof Sniper) {
            costUp1 = ((Sniper) displayedTower).getCost(1);
            textUp1 = ((Sniper) displayedTower).getName(1);
        } else if (displayedTower instanceof LaserTower) {
            costUp1 = ((LaserTower) displayedTower).getCost(1);
            textUp1 = ((LaserTower) displayedTower).getName(1);
        } else {
            System.out.println("cost error 1");
        }
        if (displayedTower instanceof Archer) {
            costUp2 = ((Archer) displayedTower).getCost(2);
            textUp2 = ((Archer) displayedTower).getName(2);
        } else if (displayedTower instanceof Cannon) {
            costUp2 = ((Cannon) displayedTower).getCost(2);
            textUp2 = ((Cannon) displayedTower).getName(2);
        } else if (displayedTower instanceof FrostMage) {
            costUp2 = ((FrostMage) displayedTower).getCost(2);
            textUp2 = ((FrostMage) displayedTower).getName(2);
        } else if (displayedTower instanceof MineFactory) {
            costUp2 = ((MineFactory) displayedTower).getCost(2);
            textUp2 = ((MineFactory) displayedTower).getName(2);
        } else if (displayedTower instanceof PoisonTower) {
            costUp2 = ((PoisonTower) displayedTower).getCost(2);
            textUp2 = ((PoisonTower) displayedTower).getName(2);
        } else if (displayedTower instanceof BoomTower) {
            costUp2 = ((BoomTower) displayedTower).getCost(2);
            textUp2 = ((BoomTower) displayedTower).getName(2);
        } else if (displayedTower instanceof Crossbow) {
            costUp2 = ((Crossbow) displayedTower).getCost(2);
            textUp2 = ((Crossbow) displayedTower).getName(2);
        } else if (displayedTower instanceof MauseFollowsTower) {
            costUp2 = ((MauseFollowsTower) displayedTower).getCost(2);
            textUp2 = ((MauseFollowsTower) displayedTower).getName(2);
        } else if (displayedTower instanceof Sniper) {
            costUp2 = ((Sniper) displayedTower).getCost(2);
            textUp2 = ((Sniper) displayedTower).getName(2);
        } else if (displayedTower instanceof LaserTower) {
            costUp2 = ((LaserTower) displayedTower).getCost(2);
            textUp2 = ((LaserTower) displayedTower).getName(2);
        } else {
            System.out.println("cost error 2");
        }
        if (displayedTower instanceof Archer) {
            costUp3 = ((Archer) displayedTower).getCost(3);
            textUp3 = ((Archer) displayedTower).getName(3);
        } else if (displayedTower instanceof Cannon) {
            costUp3 = ((Cannon) displayedTower).getCost(3);
            textUp3 = ((Cannon) displayedTower).getName(3);
        } else if (displayedTower instanceof FrostMage) {
            costUp3 = ((FrostMage) displayedTower).getCost(3);
            textUp3 = ((FrostMage) displayedTower).getName(3);
        } else if (displayedTower instanceof MineFactory) {
            costUp3 = ((MineFactory) displayedTower).getCost(3);
            textUp3 = ((MineFactory) displayedTower).getName(3);
        } else if (displayedTower instanceof PoisonTower) {
            costUp3 = ((PoisonTower) displayedTower).getCost(3);
            textUp3 = ((PoisonTower) displayedTower).getName(3);
        } else if (displayedTower instanceof BoomTower) {
            costUp3 = ((BoomTower) displayedTower).getCost(3);
            textUp3 = ((BoomTower) displayedTower).getName(3);
        } else if (displayedTower instanceof Crossbow) {
            costUp3 = ((Crossbow) displayedTower).getCost(3);
            textUp3 = ((Crossbow) displayedTower).getName(3);
        } else if (displayedTower instanceof MauseFollowsTower) {
            costUp3 = ((MauseFollowsTower) displayedTower).getCost(3);
            textUp3 = ((MauseFollowsTower) displayedTower).getName(3);
        } else if (displayedTower instanceof Sniper) {
            costUp3 = ((Sniper) displayedTower).getCost(3);
            textUp3 = ((Sniper) displayedTower).getName(3);
        } else if (displayedTower instanceof LaserTower) {
            costUp3 = ((LaserTower) displayedTower).getCost(3);
            textUp3 = ((LaserTower) displayedTower).getName(3);
        } else {
            System.out.println("cost error 3");
        }

    }


}