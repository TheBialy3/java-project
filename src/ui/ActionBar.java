package ui;

import helpz.Constants;
import objects.Tower;
import scenes.Playing;

import java.awt.*;
import java.text.DecimalFormat;

import static main.GameStates.MENU;
import static main.GameStates.SetGameState;

public class ActionBar extends Bar {

    private Playing playing;
    private MyButton bMenu, bBestiary;

    private MyButton[] towerButtons;
    private Tower selectedTower;
    private Tower displayedTower;

    private int gold = 100;
    private boolean showTowerCost;
    private int towerCostType;

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

    }

    private void drawTowerCost(Graphics g) {
        if (getTowerCost(towerCostType) > gold) {
            g.setColor(new Color(171, 0, 0));
        } else {
            g.setColor(new Color(3, 132, 0));
        }
        g.fillRect(1290, 890, 236, 90);
        g.setColor(Color.black);
        g.drawRect(1290, 890, 236, 90);
        g.setFont(new Font("Serif", Font.BOLD, 30));
        g.setColor(new Color(15, 15, 15));
        String text = "Cost: " + getTowerCost(towerCostType) + "g";
        int w = g.getFontMetrics().stringWidth(text);
        g.drawString(text, 1408 - (w / 2), 960);
        g.setFont(new Font("Serif", Font.BOLD, 28));
        text = getTowerName(towerCostType);
        w = g.getFontMetrics().stringWidth(text);
        g.drawString(text, 1408 - (w / 2), 923);


        //drawDisplayedTower(g);
        // drawDisplayedTowerRange(g);

    }

    public void goldSpend(int getCost) {
        gold -= getCost;
    }

    private int getTowerCost(int towerCostType) {
        return Constants.TowerType.GetCost(towerCostType);
    }

    private String getTowerName(int towerCostType) {
        return Constants.TowerType.GetName(towerCostType);
    }

    private void drawWaveInfo(Graphics g) {
        g.setFont(new Font("Monospaced", Font.BOLD, 20));
        drawWaveTimerInfo(g);
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

    private void drawWaveTimerInfo(Graphics g) {
        if (playing.getWaveManager().isWaveTimerStarted()) {
            g.setColor(new Color(1, 24, 87));
            float timeLeft = playing.getWaveManager().getTimeLeft();
            String formattedText = formatter.format(timeLeft);
            g.drawString("Time Left:" + formattedText, 1285, 1230);
        }
    }

    //256, 1280,
    private void drawDispalyTower(Graphics g) {
        if (displayedTower != null) {
            g.setColor(new Color(100, 45, 15));
            g.fillRect(1290, 990, 236, 180);
            g.setColor(Color.black);
            g.drawRect(1300, 1000, 64, 64);
            g.drawRect(1290, 990, 236, 180);
            g.drawImage(playing.getTowerManager().getTowerImgs()[displayedTower.getTowerType()], 1300, 1000, 64, 64, null);
            g.setFont(new Font("Serif", Font.BOLD, 20));
            g.setColor(new Color(15, 15, 15));
            g.drawString("" + Constants.TowerType.GetName(displayedTower.getTowerType()), 1375, 1018);
            g.drawString("ID:" + displayedTower.getId(), 1375, 1038);

            drawDisplayedTower(g);
            drawDisplayedTowerRange(g);
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
            towerButtons[i] = new MyButton("", x + diff * reszta, y + diff * row, w, h, i)
            ;
        }
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

    public void mouseClicked(int x, int y) {
        if (bMenu.getBounds().contains(x, y)) {
            SetGameState(MENU);
        } else if (bBestiary.getBounds().contains(x, y)) {
            SetGameState(MENU);
        } else {
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
        for (MyButton b : towerButtons) {
            b.setMouseOver(false);
        }
        if (bMenu.getBounds().contains(x, y)) {
            bMenu.setMouseOver(true);
        } else if (bBestiary.getBounds().contains(x, y)) {
            bBestiary.setMouseOver(true);
        } else {
            for (MyButton b : towerButtons) {
                if (b.getBounds().contains(x, y)) {
                    b.setMouseOver(true);
                    showTowerCost = true;
                    towerCostType = b.getId();
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
        for (MyButton b : towerButtons) {
            b.resetBooleans();
        }
    }

    private boolean isEnoughGold(int cost){
        return gold >= cost;
    }

    public void displayTower(Tower t) {
        displayedTower = t;
    }


}
