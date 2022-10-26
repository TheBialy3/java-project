package scenes;

import helpz.LevelBuild;
import main.Game;
import managers.TileManager;
import objects.Tile;
import ui.SideBar;
import ui.MyButton;

import java.awt.*;
import java.awt.image.BufferedImage;

import static main.GameStates.*;

public class Playing extends GameScene implements SceneMethods {


    private int[][] lvl;
    public TileManager tileManager;
    private Tile selectedTile;
    private int mouseX, mouseY;
    private boolean drawSelect;
    private int tileXLast, tileYLast, lastTileId;

    private SideBar sideBar;

    public Playing(Game game) {
        super(game);

        lvl = LevelBuild.getLevelData();
        tileManager = new TileManager();
        sideBar = new SideBar(1280, 0, 200, 1280, this);


        //The lvl
        //tileManager

    }

    public TileManager getTileManager() {
        return tileManager;
    }

    @Override
    public void render(Graphics g) {
        for (int y = 0; y < lvl.length; y++) {
            for (int x = 0; x < lvl[y].length; x++) {
                int id = lvl[y][x];
                g.drawImage(tileManager.getSprite(id), x * 64, y * 64, null);
            }
        }

        sideBar.draw(g);
        drawSelectedTile(g);
    }

    private void drawSelectedTile(Graphics g) {
        if (selectedTile != null && drawSelect) {
            g.drawImage(selectedTile.getSprite(), mouseX, mouseY, 64, 64, null);
            g.setColor(Color.BLACK);
            g.drawRect(mouseX, mouseY, 64, 64);
        }
    }

    public void setSelectedTile(Tile tile) {
        this.selectedTile = tile;
        drawSelect = true;
    }

    @Override
    public void mouseClicked(int x, int y) {
        if (x >= 1280) {
            sideBar.mouseClicked(x, y);
        } else {
            changedTile(mouseX, mouseY);
        }
    }

    private void changedTile(int x, int y) {
        if (selectedTile != null) {
            int tileX = x / 64;
            int tileY = y / 64;
            if (tileXLast == tileX && tileYLast == tileY&&lastTileId==selectedTile.getId()) {
                return;
            }
            tileXLast = tileX;
            tileYLast = tileY;
            lastTileId=selectedTile.getId();
            lvl[tileY][tileX] = selectedTile.getId();
        }
    }

    @Override
    public void mouseMoved(int x, int y) {
        if (x >= 1280) {
            sideBar.mouseMoved(x, y);
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
            sideBar.mouseReleased(x, y);
        }
    }


    @Override
    public void mousePressed(int x, int y) {
        if (x >= 1280) {
            sideBar.mousePressed(x, y);
        }
    }

    @Override
    public void mouseDragged(int x, int y) {
        if (x >= 1280) {

        } else {
            changedTile(x, y);
            mouseX = (x / 64) * 64;
            mouseY = (y / 64) * 64;
        }
    }
}
