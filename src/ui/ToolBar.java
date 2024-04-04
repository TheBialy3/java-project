package ui;

import helpz.ImgFix;
import helpz.LoadSave;
import objects.Tile;
import scenes.Editing;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static helpz.Constants.Tiles.ROAD_TILE;
import static main.GameStates.MENU;
import static main.GameStates.SetGameState;

public class ToolBar extends Bar {

    private MyButton bMenu, bSave;
    private MyButton currentButton;
    private MyButton bGrass, bWater, bRoadC, bWaterC, bWaterI, bWaterB, bRoad, bRoadDir, bRoadDirNull;
    private MyButton bPoints, bResetPoints;
    private Tile selectedTile;
    private Editing editing;
    private int tilePixelNumber = 64;
    private int currentIndex = 0;
    private int currentType = 0;
    private int reszta = 0;

    private BufferedImage pathStart, pathEnd, pathDir, pathDirNull;


    private Map<MyButton, ArrayList<Tile>> map = new HashMap<MyButton, ArrayList<Tile>>();

    public ToolBar(int x, int y, int width, int height, Editing editing) {
        super(x, y, width, height);
        this.editing = editing;
        initPathImgs();
        initButtons();
    }

    private void initPathImgs() {
        pathStart = LoadSave.getSpriteAtlas().getSubimage(0 * tilePixelNumber, 0 * tilePixelNumber, tilePixelNumber, tilePixelNumber);
        pathEnd = LoadSave.getSpriteAtlas().getSubimage(1 * tilePixelNumber, 0 * tilePixelNumber, tilePixelNumber, tilePixelNumber);

        pathDir = LoadSave.getSpriteAtlas().getSubimage(0 * tilePixelNumber, 1 * tilePixelNumber, tilePixelNumber, tilePixelNumber);
        pathDirNull = LoadSave.getSpriteAtlas().getSubimage(1 * tilePixelNumber, 1 * tilePixelNumber, tilePixelNumber, tilePixelNumber);
    }

    private void initButtons() {
        int w = tilePixelNumber;
        int h = tilePixelNumber;
        int x = 1293;
        int y = tilePixelNumber;
        int diff = 84;
        int id = 0;
        int type = 0;

        bMenu = new MyButton("Menu", 1293, 10, 108, 40);
        bSave = new MyButton("Save", 1417, 10, 108, 40);

        bWater = new MyButton("Water", x, y, w, h, id++);
        initMapButtons(bGrass, editing.getGame().getTileManager().getGrassT(), x, y, diff, w, h, id++, type++);

        initMapButtons(bRoad, editing.getGame().getTileManager().getRoadS(), x, y, diff, w, h, id++, type);
//        initMapButtons(bRoadC, editing.getGame().getTileManager().getRoadC(), x, y, diff, w, h, id++, type++);


        initMapButtons(bWaterC, editing.getGame().getTileManager().getWaterC(), x, y, diff, w, h, id++, type);
        initMapButtons(bWaterB, editing.getGame().getTileManager().getWaterB(), x, y, diff, w, h, id++, type);
        initMapButtons(bWaterI, editing.getGame().getTileManager().getWaterI(), x, y, diff, w, h, id++, type++);
        initMapButtons(bWaterI, editing.getGame().getTileManager().getWaterI(), x, y, diff, w, h, id++, type++);
        initMapButtons(bRoadDirNull, editing.getGame().getTileManager().getRoadDirNull(), x, y, diff, w, h, id++, type++);
        initMapButtons(bRoadDir, editing.getGame().getTileManager().getRoadDir(), x, y, diff, w, h, id++, type++);

        bPoints = new MyButton("Points", x, y + diff * type, w, h, id++);
        bResetPoints = new MyButton("ResetPoints", x + diff, y + diff * type, w, h, id++);
        type++;



    }

    private void initMapButtons(MyButton b, ArrayList<Tile> list, int x, int y, int diff, int w, int h, int id, int type) {
        if (currentType == type) {
            reszta++;
        } else {
            currentType = type;
            reszta = 0;
        }
        b = new MyButton("", x + diff * reszta, y + diff * type, w, h, id);
        map.put(b, list);
    }

    public void rotateSprite() {
        currentIndex++;
        if (currentIndex >= map.get(currentButton).size()) {
            currentIndex = 0;
        }
        selectedTile = map.get(currentButton).get(currentIndex);
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

        drawPathButtons(g, bPoints);
        drawPathButtons(g, bResetPoints);


        drawNormalButtons(g, bWater);
        drawSelectedTile(g);
        drawMapButtons(g);
        drawMesage(g);
    }

    private void drawMesage(Graphics g) {
        if (editing.getPathRoad()) {
            g.drawString("press esc kay to stop creating path for eemies", 1293, 1000);
        }
    }

    private void drawPathButtons(Graphics g, MyButton b, BufferedImage img) {
        g.drawImage(img, b.x, b.y, b.width, b.height, null);
        drawButtonFeedback(g, b);
    }

    private void drawPathButtons(Graphics g, MyButton b) {
        g.drawString(b.getText(), b.x, b.y);
        drawButtonFeedback(g, b);
    }


    private void drawNormalButtons(Graphics g, MyButton b) {
        g.drawImage(getButtImg(b.getId()), b.x, b.y, b.width, b.height, null);
        drawButtonFeedback(g, b);
    }

    private void drawMapButtons(Graphics g) {
        for (Map.Entry<MyButton, ArrayList<Tile>> entry : map.entrySet()) {
            MyButton b = entry.getKey();
            BufferedImage img = entry.getValue().get(0).getSprite();
            if (img != null) {
                g.drawImage(img, b.x, b.y, b.width, b.height, null);
            }
            drawButtonFeedback(g, b);
        }
    }

    private void drawSelectedTile(Graphics g) {
        if (selectedTile != null) {
            g.drawImage(selectedTile.getSprite(), 1300, 1200, tilePixelNumber, tilePixelNumber, null);
            g.setColor(Color.black);
            g.drawRect(1300, 1200, tilePixelNumber, tilePixelNumber);
        }
    }

    public BufferedImage getButtImg(int id) {
        return editing.getGame().getTileManager().getSprite(id);
    }


    public void mouseClicked(int x, int y) {
        if (bMenu.getBounds().contains(x, y)) {
            SetGameState(MENU);
        } else if (bSave.getBounds().contains(x, y)) {
            saveLevel();
        } else if (bWater.getBounds().contains(x, y)) {
            selectedTile = editing.getGame().getTileManager().getTile(bWater.getId());
            editing.setSelectedTile(selectedTile);
        } else if (bPoints.getBounds().contains(x, y)) {
            editing.setPathRoad(true);
        } else if (bResetPoints.getBounds().contains(x, y)) {
            editing.resetEnemyPathRoad();
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
        } else if (bResetPoints.getBounds().contains(x, y)) {
            bResetPoints.setMouseOver(true);
        } else if (bPoints.getBounds().contains(x, y)) {
            bPoints.setMouseOver(true);
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

    public void mouseDragged(int x, int y) {
        mouseClicked(x, y);
    }

    public void mousePressed(int x, int y) {
        if (bMenu.getBounds().contains(x, y)) {
            bMenu.setMousePressed(true);
        } else if (bSave.getBounds().contains(x, y)) {
            bSave.setMousePressed(true);
        } else if (bWater.getBounds().contains(x, y)) {
            bWater.setMousePressed(true);
        } else if (bResetPoints.getBounds().contains(x, y)) {
            bResetPoints.setMousePressed(true);
        } else if (bPoints.getBounds().contains(x, y)) {
            bPoints.setMousePressed(true);
        } else {
            for (MyButton b : map.keySet()) {
                if (b.getBounds().contains(x, y)) {
                    b.setMousePressed(true);
                    currentButton = b;
                    currentIndex = 0;
                    return;
                }
            }
        }
    }

    private void resetButtons() {
        bMenu.resetBooleans();
        bSave.resetBooleans();
        bWater.resetBooleans();
        bPoints.resetBooleans();
        bResetPoints.resetBooleans();
        for (MyButton b : map.keySet()) {
            b.resetBooleans();
        }
    }

    public BufferedImage getPathStartImg() {
        return pathStart;
    }

    public BufferedImage getPathEndImg() {
        return pathEnd;
    }
}
