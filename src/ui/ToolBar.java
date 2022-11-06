package ui;

import objects.Tile;
import scenes.Editing;
import scenes.Playing;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static main.GameStates.MENU;
import static main.GameStates.SetGameState;

public class ToolBar extends Bar {

    private MyButton bMenu, bSave;
    private MyButton currentButton;
    private MyButton bGrass, bWater, bRoadC, bWaterC, bWaterI, bWaterB, bRoad;
    private Tile selectedTile;
    private Editing editing;
    private int currentIndex=0;

    private Map<MyButton, ArrayList<Tile>> map = new HashMap<MyButton, ArrayList<Tile>>();

    public ToolBar(int x, int y, int width, int height, Editing editing) {
        super(x, y, width, height);
        this.editing = editing;
        initButtons();
    }

    private void initButtons() {
        int w = 64;
        int h = 64;
        int x = 1293;
        int y = 64;
        int diff = 84;
        int id = 0;

        bMenu = new MyButton("Menu", 1293, 10, 108, 40);
        bSave = new MyButton("Save", 1417, 10, 108, 40);

        bWater = new MyButton("Water", x, y, w, h, id++);
        initMapButtons(bGrass, editing.getGame().getTileManager().getGrassT(), x, y, diff, w, h, id++);
        initMapButtons(bRoad, editing.getGame().getTileManager().getRoadS(), x, y, diff, w, h, id++);
        initMapButtons(bRoadC, editing.getGame().getTileManager().getRoadC(), x, y, diff, w, h, id++);
        initMapButtons(bWaterC, editing.getGame().getTileManager().getWaterC(), x, y, diff, w, h, id++);
        initMapButtons(bWaterB, editing.getGame().getTileManager().getWaterB(), x, y, diff, w, h, id++);
        initMapButtons(bWaterI, editing.getGame().getTileManager().getWaterI(), x, y, diff, w, h, id++);
    }

    private void initMapButtons(MyButton b, ArrayList<Tile> list, int x, int y, int diff, int w, int h, int id) {
        int ii = id/3;
        int reszta = id % 3;


        b = new MyButton("", x + diff * reszta, y + diff * ii, w, h, id);

        map.put(b, list);
    }

    public void rotateSprite() {
        currentIndex++;
        if(currentIndex>=map.get(currentButton).size()){
            currentIndex=0;
        }
        selectedTile=map.get(currentButton).get(currentIndex);
        editing.setSelectedTile(selectedTile);
    }

    public void draw(Graphics g) {
        //backGround
        g.setColor(new Color(0, 102, 102));
        g.fillRect(x, y, width, height);
        //buttons
        drawButtons(g);
    }

    private void drawButtons(Graphics g) {
        bMenu.draw(g);
        bSave.draw(g);
        drawNormalButtons(g, bWater);
        drawSelectedTile(g);
        drawMapButtons(g);
    }

    private void drawNormalButtons(Graphics g, MyButton b) {
        g.drawImage(getButtImg(b.getId()), b.x, b.y, b.width, b.height, null);
        drawButtonFeedback(g, b);
    }

    private void drawMapButtons(Graphics g) {
        for (Map.Entry<MyButton, ArrayList<Tile>> entry : map.entrySet()) {
            MyButton b = entry.getKey();
            BufferedImage img = entry.getValue().get(0).getSprite();
            g.drawImage(img, b.x, b.y, b.width, b.height, null);
            drawButtonFeedback(g, b);
        }
    }

    private void drawSelectedTile(Graphics g) {
        if (selectedTile != null) {
            g.drawImage(selectedTile.getSprite(), 1300, 1200, 64, 64, null);
            g.setColor(Color.black);
            g.drawRect(1300, 1200, 64, 64);
        }
    }

    public BufferedImage getButtImg(int id) {
        return editing.getGame().getTileManager().getSprite(id);
    }

    private void drawButtonFeedback(Graphics g, MyButton b) {
        //MouseOver
        if (b.isMouseOver()) {
            g.setColor(Color.WHITE);
        } else {
            g.setColor(Color.BLACK);
        }
        //Border
        g.drawRect(b.x, b.y, b.width, b.height);
        //MousePressed
        if (b.isMousePressed()) {
            g.drawRect(b.x + 1, b.y + 1, b.width - 2, b.height - 2);
            g.drawRect(b.x + 2, b.y + 2, b.width - 4, b.height - 4);
        }
    }

    public void mouseClicked(int x, int y) {
        if (bMenu.getBounds().contains(x, y)) {
            SetGameState(MENU);
        } else if (bSave.getBounds().contains(x, y)) {
            saveLevel();
        } else if (bWater.getBounds().contains(x, y)) {
            selectedTile = editing.getGame().getTileManager().getTile(bWater.getId());
            editing.setSelectedTile(selectedTile);
            return;

        } else {
            for (MyButton b : map.keySet()) {
                if (b.getBounds().contains(x, y)) {
                    selectedTile = map.get(b).get(0);
                    editing.setSelectedTile(selectedTile);
                    return;
                }
            }
        }
    }

    private void saveLevel() {
        editing.saveLevel();
    }

    public void mouseMoved(int x, int y) {
        bMenu.setMouseOver(false);
        bSave.setMouseOver(false);
        bWater.setMouseOver(false);
        for (MyButton b : map.keySet()) {
            b.setMouseOver(false);
        }
        if (bMenu.getBounds().contains(x, y)) {
            bMenu.setMouseOver(true);
        } else if (bSave.getBounds().contains(x, y)) {
            bSave.setMouseOver(true);
        } else if (bWater.getBounds().contains(x, y)) {
            bWater.setMouseOver(true);
        } else {
            for (MyButton b : map.keySet()) {
                if (b.getBounds().contains(x, y)) {
                    b.setMouseOver(true);
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
        } else if (bSave.getBounds().contains(x, y)) {
            bSave.setMousePressed(true);}
        else if (bWater.getBounds().contains(x, y)) {
            bWater.setMousePressed(true);
        } else {
            for (MyButton b : map.keySet()) {
                if (b.getBounds().contains(x, y)) {
                    b.setMousePressed(true);
                    currentButton=b;
                    currentIndex=0;
                    return;
                }
            }
        }
    }

    private void resetButtons() {
        bMenu.resetBooleans();
        bSave.resetBooleans();
        bWater.resetBooleans();
        for (MyButton b : map.keySet()) {
            b.resetBooleans();
        }
    }


}
