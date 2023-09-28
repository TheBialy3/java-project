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

    private BufferedImage img;

    private ArrayList<BufferedImage> sprites = new ArrayList<>();
    private ArrayList<Card> cards=new ArrayList<>();

    private int[][] lvl;
    private TileManager tileManager;

    private MyButton bMusic, bSound, bMenu;
    private MyButton[] upgradeIcons;

    public Upgrade(Game game) {
        super(game);

        initCards();
        initButtons();
    }

    private void initCards() {
       // cards.add(new Card());
    }

    public void update() {
        updateTick();
    }

    private void initButtons() {
        int w = 200;
        int h = w / 4;
        int x = 1536 / 2 - w / 2;
        int y = 1000;
        int yOffset = 200;

//        bMusic = new MyButton("Music", x, y, w, h);
//        bSound = new MyButton("Sound", x, y + yOffset, w, h);
        bMenu = new MyButton("Menu", x, y, w, h);
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(helpz.LoadSave.getBackgroundImg(), 0, 0, 1536, 1280, null);

        //  drawButtons(g);
        drawUpgradeImg(g);
    }

    private void drawUpgradeImg(Graphics g) {
        int diff=100;

        int tilePixelNumber=64;
        for (Card card:cards) {
            if (card.isUnlocked()) {///////////////////////////
                g.setColor(new Color(161, 0, 0));
            } else {
                g.setColor(new Color(200, 200, 200));
            }
            g.fillRect(50+(diff*(card.getId()%10)),50+(diff*(card.getId()/10)), tilePixelNumber,tilePixelNumber);
            //g.drawImage(playing.getTowerManager().getTowerImgs()[b.getId()], b.x, b.y, b.width, b.height, null);

        }
    }

    @Override
    public void mouseClicked(int x, int y) {
//        if (bMusic.getBounds().contains(x, y)) {
//
//        } else if (bSound.getBounds().contains(x, y)) {
//
//        } else
        if (bMenu.getBounds().contains(x, y)) {
            SetGameState(MENU);
        }
    }

    @Override
    public void mouseMoved(int x, int y) {
//        bMusic.setMouseOver(false);
//        if (bMusic.getBounds().contains(x, y)) {
//            bMusic.setMouseOver(true);
//        }
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
//        bMusic.resetBooleans();
//        bSound.resetBooleans();
        bMenu.resetBooleans();
    }

    @Override
    public void mousePressed(int x, int y) {
//        if (bMusic.getBounds().contains(x, y)) {
//            bMusic.setMousePressed(true);
//        } else if (bSound.getBounds().contains(x, y)) {
//            bSound.setMousePressed(true);
//        } else
        if (bMenu.getBounds().contains(x, y)) {
            bMenu.setMousePressed(true);
        }
    }

    private void drawButtons(Graphics g) {
//        bMusic.draw(g);
//        bSound.draw(g);
        bMenu.draw(g);
    }

    @Override
    public void mouseDragged(int x, int y) {
        mouseClicked(x, y);
    }

}