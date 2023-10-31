package scenes;

import helpz.LoadSave;
import main.Game;
import managers.TileManager;
import objects.Card;
import ui.MyButton;
import xp.UpgradesItemsCard;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static main.GameStates.MENU;
import static main.GameStates.SetGameState;


public class Upgrade extends GameScene implements SceneMethods {

    private UpgradesItemsCard upgradesItemsCard;

    private ArrayList<BufferedImage> sprites = new ArrayList<>();
    private ArrayList<Card> cards=new ArrayList<>();
    private MyButton  bMenu;

    public Upgrade(Game game) {
        super(game);
        this.upgradesItemsCard=new UpgradesItemsCard(this);
        initCards();
        initButtons();
    }

    private void initCards() {
       cards=upgradesItemsCard.getCards();
    }

    public void update() {
        updateTick();
    }

    private void initButtons() {
        int w = 200;
        int h = w / 4;
        int x = 1536 / 2 - w / 2;
        int y = 1150;

        bMenu = new MyButton("Menu", x, y, w, h);
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(helpz.LoadSave.getBackgroundImg(), 0, 0, 1536, 1280, null);

        drawButtons(g);
        drawUpgradeImg(g);
    }

    private void drawUpgradeImg(Graphics g) {
        int diff=99;

        int tilePixelNumber=64;
        for (Card card:cards) {
            if (card.isUnlocked()) {///////////////////////////
                g.setColor(new Color(200, 200, 200));
            } else {
                g.setColor(new Color(161, 0, 0));
            }
            g.fillRect(40+(diff*(card.getId()%15)),50+(diff*(card.getId()/15)), tilePixelNumber,tilePixelNumber);
            g.setColor(new Color(0, 26, 147));
            g.drawString(String.valueOf(card.getId()), 40+(diff*(card.getId()%15))+25, 50+(diff*(card.getId()/15))+39);
            //g.drawImage(playing.getTowerManager().getTowerImgs()[b.getId()], (40+(diff*(card.getId()%15)), 50+(diff*(card.getId()/15)), tilePixelNumber, tilePixelNumber, null);

        }
    }

    @Override
    public void mouseClicked(int x, int y) {
//       if (bSound.getBounds().contains(x, y)) {
//
//        } else
        if (bMenu.getBounds().contains(x, y)) {
            SetGameState(MENU);
        }
    }

    @Override
    public void mouseMoved(int x, int y) {
//        bSound.setMouseOver(false);
//        if (bSound.getBounds().contains(x, y)) {
//            bSound.setMouseOver(true);
//        }
        bMenu.setMouseOver(false);
        if (bMenu.getBounds().contains(x, y)) {
            bMenu.setMouseOver(true);
        }
    }

    @Override
    public void mouseReleased(int x, int y) {
        resetButtons();
    }

    private void resetButtons() {
//        bSound.resetBooleans();
        bMenu.resetBooleans();
    }

    @Override
    public void mousePressed(int x, int y) {
// if (bSound.getBounds().contains(x, y)) {
//            bSound.setMousePressed(true);
//        } else
        if (bMenu.getBounds().contains(x, y)) {
            bMenu.setMousePressed(true);
        }
    }

    private void drawButtons(Graphics g) {
//        bSound.draw(g);
        bMenu.draw(g);
    }

    @Override
    public void mouseDragged(int x, int y) {
        mouseClicked(x, y);
    }

    public ArrayList<Card> getCards() {
        return cards;
    }
}