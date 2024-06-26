package scenes;

import helpz.LoadSave;
import main.Game;
import objects.PathPoint;
import objects.Tile;
import objects.TowerPlace;
import ui.ToolBar;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Editing extends GameScene implements SceneMethods {


    private static int[][] lvl;

    private ArrayList<PathPoint> enemyPathRoad = new ArrayList<>();
    private ArrayList<PathPoint> enemyPathRoad2 = new ArrayList<>();
    private ArrayList<TowerPlace> towerPlaces = new ArrayList<>();
    private Tile selectedTile;

    private BufferedImage TPimg=helpz.LoadSave.getImg("tower_place");

    private int mouseX, mouseY;



    private enum DrawState{
        NON,
        PATH_POINT,
        PATH_POINT_2,
        TOWER_PLACE,
        TILE,

    }
    DrawState drawState=DrawState.NON;
    private int tileXLast, tileYLast, lastTileId;
    private static int chosenLvl = 1;

    private ToolBar toolBar;
    private PathPoint start, end;

    public Editing(Game game) {
        super(game);
        LoadDefaultLevel();
        toolBar = new ToolBar(1280, 0, 256, 1280, this);
    }

    public void LoadDefaultLevel() {
        lvl = LoadSave.GetLevelData();
        enemyPathRoad = LoadSave.GetLevelDir();
        enemyPathRoad2 = LoadSave.GetLevelDir();
        towerPlaces= LoadSave.GetLevelTowerPlaces();
    }


    public void update() {
        updateTick();
    }

    @Override
    public void render(Graphics g) {
        drawLevel(g);
        toolBar.draw(g);
        drawSelectedTile(g);
        drawPathPoint(g);
        drawTowerPlaces(g);

    }


    private void drawPathPoint(Graphics g) {
        int i = 0;
        if (!enemyPathRoad.isEmpty()) {
            for (PathPoint point : enemyPathRoad) {
                g.setColor(new Color(6, 51, 143));
                g.drawOval(point.getxCord()-20, point.getyCord()-20, 40, 40);
                g.drawString(i + "", point.getxCord()-10, point.getyCord()+5);
                i++;
            }
        }
        i = 0;
        if (!enemyPathRoad2.isEmpty()) {
            for (PathPoint point : enemyPathRoad2) {
                g.setColor(new Color(36, 143, 6));
                g.drawOval(point.getxCord()-20, point.getyCord()-20, 40, 40);
                g.drawString(i + "", point.getxCord()-10, point.getyCord()+5);
                i++;
            }
        }
    }
    private void drawTowerPlaces(Graphics g) {
        int i = 0;
        if (!towerPlaces.isEmpty()) {
            for (TowerPlace point : towerPlaces) {
                g.setColor(new Color(6, 51, 143));
                g.drawImage(TPimg,point.getX()-TPimg.getWidth()*2, point.getY()-TPimg.getHeight()*2, TPimg.getWidth()*4,TPimg.getHeight()*4, null);
                g.drawString(i + "", point.getX()-10, point.getY()+5);
                i++;
            }
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

    private void drawSelectedTile(Graphics g) {
        if (selectedTile != null && drawState==DrawState.TILE) {
            g.drawImage(selectedTile.getSprite(), mouseX, mouseY, 64, 64, null);
            g.setColor(Color.BLACK);
            g.drawRect(mouseX, mouseY, 64, 64);
        }
    }

    public void saveLevel() {
        if(!enemyPathRoad2.isEmpty()){
      //      LoadSave.SaveLevel("level" + chosenLvl, lvl, enemyPathRoad, towerPlaces,enemyPathRoad2);
        }
        LoadSave.SaveLevel("level" + chosenLvl, lvl, enemyPathRoad,towerPlaces);
        game.initClasses();
    }

    public void setSelectedTile(Tile tile) {
        this.selectedTile = tile;
        drawState = DrawState.TILE;
    }

    private void changedTile(int x, int y) {

        int tileX = x / 64;
        int tileY = y / 64;
        if (selectedTile.getId() >= 0) {
            if (tileXLast == tileX && tileYLast == tileY && lastTileId == selectedTile.getId()) {
                return;
            }
            tileXLast = tileX;
            tileYLast = tileY;
            lastTileId = selectedTile.getId();
            lvl[tileY][tileX] = selectedTile.getId();
        }
    }


    @Override
    public void mouseClicked(int x, int y) {
        if (x >= 1280) {
            toolBar.mouseClicked(x, y);
        } else {
            if (selectedTile != null) {
                changedTile(mouseX, mouseY);
            } else if (drawState==DrawState.PATH_POINT) {
                enemyPathRoad.add(new PathPoint(x, y));
            } else if (drawState==DrawState.PATH_POINT_2) {
                     enemyPathRoad2.add(new PathPoint(x, y));
            } else if (drawState==DrawState.TOWER_PLACE) {
                towerPlaces.add(new TowerPlace(x, y));
            }
        }
    }

    @Override
    public void mouseMoved(int x, int y) {
        if (x >= 1280) {
            toolBar.mouseMoved(x, y);
        } else {
            mouseX = (x / 64) * 64;
            mouseY = (y / 64) * 64;
        }
    }

    @Override
    public void mouseReleased(int x, int y) {
        if (x >= 1280) {
            toolBar.mouseReleased(x, y);
        }
    }

    @Override
    public void mousePressed(int x, int y) {
        if (x >= 1280) {
            toolBar.mousePressed(x, y);
        }
    }

    @Override
    public void mouseDragged(int x, int y) {
        mouseClicked(x, y);
    }

    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_R:
                toolBar.rotateSprite();
                break;
            case KeyEvent.VK_1:
                break;
            case KeyEvent.VK_ESCAPE:
                setDrawStateNon();
                break;
        }
    }

    public boolean isDrawPath() {
        return drawState==DrawState.PATH_POINT;
    }
    public boolean isDrawPath2() {
        return drawState==DrawState.PATH_POINT_2;
    }

    public void setDrawStateNon() {
        drawState = DrawState.NON;
    }
    public void setDrawStatePathPoint() {
        drawState = DrawState.PATH_POINT;
    }
    public void setDrawStatePathPoint2() {
        drawState = DrawState.PATH_POINT_2;
    }
    public void setDrawStateTowerPlace() {
        drawState = DrawState.TOWER_PLACE;
    }



    public void resetEnemyPathRoad() {
        enemyPathRoad.clear();
    }
    public void resetEnemyPathRoad2() {
        enemyPathRoad2.clear();
    }
    public void resetTowerPlaces() {
        towerPlaces.clear();
    }



}
