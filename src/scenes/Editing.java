package scenes;

import helpz.LoadSave;
import main.Game;
import objects.PathPoint;
import objects.Tile;
import ui.ToolBar;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import static helpz.Constants.Tiles.*;

public class Editing extends GameScene implements SceneMethods {


    private static int[][] lvl;

    private ArrayList<PathPoint> enemyPathRoad = new ArrayList<>();
    private ArrayList<PathPoint> tower = new ArrayList<>();
    private Tile selectedTile;

    private int mouseX, mouseY;
    private boolean drawSelect, PathRoad = false;
    private int tileXLast, tileYLast, lastTileId;
    private static int chosenLvl = 1;

    private ToolBar toolBar;
    private PathPoint start, end;

    public Editing(Game game) {
        super(game);
        LoadDefoultLevel();
        toolBar = new ToolBar(1280, 0, 256, 1280, this);
    }

    public void LoadDefoultLevel() {
        lvl = LoadSave.GetLevelData();
        enemyPathRoad = LoadSave.GetLevelDir();
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
        if (selectedTile != null && drawSelect) {
            g.drawImage(selectedTile.getSprite(), mouseX, mouseY, 64, 64, null);
            g.setColor(Color.BLACK);
            g.drawRect(mouseX, mouseY, 64, 64);
        }
    }

    public void saveLevel() {
        LoadSave.SaveLevel("level" + chosenLvl, lvl, enemyPathRoad);
        game.initClasses();
    }

    public void setSelectedTile(Tile tile) {
        this.selectedTile = tile;
        drawSelect = true;
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
            } else if (PathRoad) {
                enemyPathRoad.add(new PathPoint(x, y));
            }
        }
    }

    @Override
    public void mouseMoved(int x, int y) {
        if (x >= 1280) {
            toolBar.mouseMoved(x, y);
            drawSelect = false;
        } else {
            drawSelect = true;
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
                setPathRoad(false);
                break;
        }
    }

    public void setPathRoad(Boolean b) {
        PathRoad = b;
    }

    public Boolean getPathRoad() {
        return PathRoad;
    }

    public void resetEnemyPathRoad() {
        enemyPathRoad.clear();
    }


}
