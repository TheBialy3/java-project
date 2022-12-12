package scenes;

import helpz.LoadSave;
import main.Game;
import objects.PathPoint;
import objects.Tile;
import ui.ActionBar;
import ui.ToolBar;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static helpz.Constants.Tiles.*;

public class Editing extends GameScene implements SceneMethods {


    private static int[][] lvl;


    private Tile selectedTile;
    private int mouseX, mouseY;
    private boolean drawSelect;
    private int tileXLast, tileYLast, lastTileId;

    private ToolBar toolBar;
    private PathPoint start, end;

    public Editing(Game game) {
        super(game);
        LoadDefoultLevel();
        toolBar = new ToolBar(1280, 0, 256, 1280, this);
    }

    private void LoadDefoultLevel() {
        lvl = LoadSave.GetLevelData();
        ArrayList<PathPoint> points = LoadSave.getPathPoints();
        start=points.get(0);
        end=points.get(1);
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
        if(start!=null){
            g.drawImage(toolBar.getPathStartImg(),start.getxCord()*64, start.getyCord()*64,64,64, null);
        }
        if(end!=null){
            g.drawImage(toolBar.getPathEndImg(),end.getxCord()*64, end.getyCord()*64,64,64, null);
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
        LoadSave.SaveLevel("newlevel", lvl, start, end);
        game.getPlaying().setLevel(lvl);
    }

    public void setSelectedTile(Tile tile) {
        this.selectedTile = tile;
        drawSelect = true;
    }

    private void changedTile(int x, int y) {
        if (selectedTile != null) {
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
            } else {
                int id = lvl[tileY][tileX];
                if (game.getTileManager().getTile(id).getTileType() == ROAD_TILE) {
                    if (selectedTile.getId() == -1) {
                        start = new PathPoint(tileX, tileY);
                    } else {
                        end = new PathPoint(tileX, tileY);
                    }
                }
            }
        }
    }

    @Override
    public void mouseClicked(int x, int y) {
        if (x >= 1280) {
            toolBar.mouseClicked(x, y);
        } else {
            changedTile(mouseX, mouseY);
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
        if (x >= 1280) {
            toolBar.mouseDragged(x, y);
        } else {
            changedTile(x, y);
            mouseX = (x / 64) * 64;
            mouseY = (y / 64) * 64;
        }
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_R) {
            toolBar.rotateSprite();
        }
    }


}
