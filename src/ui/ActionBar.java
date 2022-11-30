package ui;

import helpz.Constants;
import objects.Tower;
import scenes.GameOver;
import scenes.Playing;

import java.awt.*;
import java.text.DecimalFormat;

import static main.GameStates.MENU;
import static main.GameStates.SetGameState;

public class ActionBar extends Bar {

    private Playing playing;
    private MyButton bMenu, bBestiary;
    private MyButton bUpgrate1, bUpgrate2, bUpgrate3, bSell;
    private MyButton[] towerButtons;
    private Tower selectedTower;
    private Tower displayedTower;

    private int gold = 300;
    private boolean showTowerCost;
    private static boolean paused, gameOver;
    private int towerCostId;


    private static int lives = 25;

    private DecimalFormat formatter;

    public ActionBar(int x, int y, int width, int height, Playing playing) {
        super(x, y, width, height);
        this.playing = playing;
        formatter = new DecimalFormat("0.0");

        initButtons();
    }


    public void draw(Graphics g) {
        //backGround
        g.setColor(new Color(0, 102, 102));
        g.fillRect(x, y, width, height);
        //buttons
        drawButtons(g);
        //displayTower
        drawDispalyTower(g);
        //waveInfo
        drawWaveInfo(g);
        //GoldInfo
        drawGoldAmount(g);
        //towerInfo
        if (showTowerCost) {
            drawTowerCost(g);
        }

        if (bSell.isMouseOver()) {
            drawTowerSellCost(g);
        }

        if (gameOver) {
            GameOver.drawGameOver(g);
        } else if (paused) {
            drawPause(g);
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

    public static void Pause() {
        paused = true;
    }

    public static void unpouse() {
        paused = false;
    }

    public void drawPause(Graphics g) {
        g.setColor(new Color(171, 0, 0));
        g.setFont(new Font("Serif", Font.BOLD, 50));
        String text = "PAUSED";
        int h = g.getFontMetrics().getHeight();
        int w = g.getFontMetrics().stringWidth(text);
        g.drawString(text, 640 - w / 2, 15 + 640 - h / 2);
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
        return Constants.TowerType.GetCost(towerCostId);
    }

    private int getTowerCost() {
        return displayedTower.getWorthGold();
    }

    private String getTowerName(int towerCostId) {
        return Constants.TowerType.GetName(towerCostId);
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
        int currentWave = playing.getEnemyMenager().getAmountOfAliveEnemies();
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
            g.drawString("Time Left:" + lives, 1285, 1230);

    }

    //256, 1280,
    private void drawDispalyTower(Graphics g) {
        if (displayedTower != null) {
            g.setColor(new Color(100, 45, 15));
            g.fillRect(1290, 990, 236, 180);
            g.setColor(Color.black);
            g.drawRect(1301, 1000, 64, 64);
            g.drawRect(1290, 990, 236, 180);
            g.setColor(new Color(45, 45, 45, 109));
            g.fillRect(1301, 1000, 64, 64);
            g.drawImage(playing.getTowerManager().getTowerImgs()[displayedTower.getTowerType()], 1301, 1000, 64, 64, null);
            g.setFont(new Font("Serif", Font.BOLD, 20));
            g.setColor(new Color(15, 15, 15));
            g.drawString("" + Constants.TowerType.GetName(displayedTower.getTowerType()), 1375, 1018);
            g.drawString("ID:" + displayedTower.getId(), 1375, 1038);

            bSell.draw(g);
            bUpgrate1.draw(g);
            // bUpgrate2.draw(g);
            // bUpgrate3.draw(g);

            drawDisplayedTower(g);
            drawDisplayedTowerRange(g);
            upgrate1Descryption(g);
        }
    }

    public static void removeOneLive() {
        lives--;
        if (lives <= 0) {
            gameOver=true;
        }
    }

    private void drawDisplayedTowerRange(Graphics g) {
        g.setColor(Color.BLACK);
        g.drawOval((int) (displayedTower.getX() - displayedTower.getRange()) + 32, (int) (displayedTower.getY() - displayedTower.getRange()) + 32, (int) (displayedTower.getRange() * 2), (int) (displayedTower.getRange()) * 2);
    }

    private void drawDisplayedTower(Graphics g) {
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
        bBestiary = new MyButton("Bestiary", 1417, 10, 108, 40);

        towerButtons = new MyButton[4];

        for (int i = 0; i < towerButtons.length; i++) {
            int row = i / 3;
            int reszta = i % 3;
            towerButtons[i] = new MyButton("", x + diff * reszta, y + diff * row, w, h, i);
        }

        bSell = new MyButton("Sell ", 1401, 1180, 125, 27);
//+ Constants.TowerType.GetCost(displayedTower.getTowerType())
        bUpgrate1 = new MyButton("", 1301, 1095, 64, 64);

        // bUpgrate2 = new MyButton("", 1376, 1095, 64, 64);
        // bUpgrate3 = new MyButton("", 1451, 1095, 64, 64);

    }

    public void upgrate1Descryption(Graphics g) {
        if (bUpgrate1.isMouseOver()) {
            g.setColor(new Color(100, 45, 15));
            g.fillRect(700, 990, 566, 280);
            g.setColor(Color.black);
            g.drawRect(700, 990, 566, 280);
            g.fillRect(710, 1000, 64, 64);
            g.setColor(new Color(15, 15, 15));
            g.drawRect(710, 1000, 64, 64);
            g.drawImage(playing.getTowerManager().getTowerImgs()[displayedTower.getTowerType()], 710, 1000, 64, 64, null);
            g.setFont(new Font("Serif", Font.BOLD, 30));
            String text = "Cost: " + getTowerCost() + "g";
            g.drawString(text, 794, 1022);
            g.setFont(new Font("Serif", Font.BOLD, 28));
            text = getTowerName(towerCostId);
            g.drawString(text, 794, 1064);
        }
    }

//    private void drawUpgrade(Graphics g) {
//        // if (getTowerCost(towerCostId) > gold) {
//        //            g.setColor(new Color(171, 0, 0));
//        //        } else {
//        //            g.setColor(new Color(3, 132, 0));
//        //        }
//        g.fillRect(1301, 1095, 64, 64);
//        g.setColor(new Color(15, 15, 15));
//        g.drawRect(1301, 1095, 64, 64);
//        g.drawImage(playing.getTowerManager().getTowerImgs()[displayedTower.getTowerType()], 1301, 1095, 64, 64, null);
//
//        //if (getTowerCost(towerCostId) > gold) {
//        //            g.setColor(new Color(171, 0, 0));
//        //        } else {
//        //            g.setColor(new Color(3, 132, 0));
//        //        }
//        g.fillRect(1376, 1095, 64, 64);
//        g.setColor(new Color(15, 15, 15));
//        g.drawRect(1376, 1095, 64, 64);
//        g.drawImage(playing.getTowerManager().getTowerImgs()[displayedTower.getTowerType()], 1376, 1095, 64, 64, null);
//
//        //if (getTowerCost(towerCostId) > gold) {
//        //            g.setColor(new Color(171, 0, 0));
//        //        } else {
//        //            g.setColor(new Color(3, 132, 0));
//        //        }
//        g.fillRect(1451, 1095, 64, 64);
//        g.setColor(new Color(15, 15, 15));
//        g.drawRect(1451, 1095, 64, 64);
//        g.drawImage(playing.getTowerManager().getTowerImgs()[displayedTower.getTowerType()], 1451, 1095, 64, 64, null);
//
//    }

    public void earnGold(int getCost) {
        gold += getCost;
    }

    public void goldSpend(int getCost) {
        gold -= getCost;
    }

    private void drawButtons(Graphics g) {
        bMenu.draw(g);
        bBestiary.draw(g);
        for (MyButton b : towerButtons) {
            g.setColor(new Color(200, 200, 200));
            g.fillRect(b.x, b.y, b.width, b.height);
            g.drawImage(playing.getTowerManager().getTowerImgs()[b.getId()], b.x, b.y, b.width, b.height, null);
            drawButtonFeedback(g, b);
        }
    }

    private void sellTowerClicked() {
        playing.removeTower(displayedTower);
        gold += getTowerCost() * 0.8;
        displayedTower = null;
    }

    private void upgrade1TowerClicked() {
        // playing.upgrade1Tower(displayedTower);
        //  updateTowerWorthGold(Constants.TowerType.upgrade1cost(displayedTower.getTowerType()));
        // gold -= Constants.TowerType.upgrade1cost(displayedTower.getTowerType());
    }

    public void mouseClicked(int x, int y) {
        if (bMenu.getBounds().contains(x, y)) {
            SetGameState(MENU);
        } else if (bBestiary.getBounds().contains(x, y)) {
            SetGameState(MENU);
        } else {
            if (displayedTower != null) {
                if (bSell.getBounds().contains(x, y)) {
                    sellTowerClicked();
                } else if (bUpgrate1.getBounds().contains(x, y)) {
                    upgrade1TowerClicked();
                }
            }
            for (MyButton b : towerButtons) {
                if (b.getBounds().contains(x, y)) {
                    if (isEnoughGold(Constants.TowerType.GetCost(b.getId()))) {
                        selectedTower = new Tower(0, 0, -1, b.getId());
                        playing.setSelectedTower(selectedTower);
                        return;
                    }
                }
            }
        }

    }

    public void mouseMoved(int x, int y) {
        bMenu.setMouseOver(false);
        bBestiary.setMouseOver(false);
        showTowerCost = false;
        bSell.setMouseOver(false);
        bUpgrate1.setMouseOver(false);
        for (MyButton b : towerButtons) {
            b.setMouseOver(false);
        }
        if (bMenu.getBounds().contains(x, y)) {
            bMenu.setMouseOver(true);
        } else if (bBestiary.getBounds().contains(x, y)) {
            bBestiary.setMouseOver(true);
        } else {
            if (displayedTower != null) {
                if (bSell.getBounds().contains(x, y)) {
                    bSell.setMouseOver(true);
                } else if (bUpgrate1.getBounds().contains(x, y)) {
                    bUpgrate1.setMouseOver(true);
                    //
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
            bMenu.setMousePressed(true);
        } else if (bBestiary.getBounds().contains(x, y)) {
            bBestiary.setMousePressed(true);
        } else {
            if (displayedTower != null) {
                if (bSell.getBounds().contains(x, y)) {
                    bSell.setMousePressed(true);
                } else if (bUpgrate1.getBounds().contains(x, y)) {
                    bUpgrate1.setMousePressed(true);
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
        bBestiary.resetBooleans();
        bSell.resetBooleans();
        bUpgrate1.resetBooleans();
        for (MyButton b : towerButtons) {
            b.resetBooleans();
        }
    }

    private boolean isEnoughGold(int cost) {
        return gold >= cost;
    }

    public static int getLives() {
        return lives;
    }

    public void displayTower(Tower t) {
        displayedTower = t;
    }

}
