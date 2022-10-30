package scenes;

import helpz.LoadSave;
import main.Game;
import objects.Tile;
import ui.ActionBar;
import ui.ToolBar;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Editing extends GameScene implements SceneMethods {


    private static int[][] lvl;


    private Tile selectedTile;
    private int mouseX, mouseY;
    private boolean drawSelect;
    private int tileXLast, tileYLast, lastTileId;

    private ToolBar toolBar;

    public Editing(Game game) {
        super(game);
        LoadDefoultLevel();
        toolBar = new ToolBar(1280, 0, 256, 1280, this);
    }

    private void LoadDefoultLevel() {
        lvl = LoadSave.GetLevelData("newlevel");
    }

    @Override
    public void render(Graphics g) {
        drawLevel(g);
        toolBar.draw(g);
        drawSelectedTile(g);
    }

    private void drawLevel(Graphics g) {
        for (int y = 0; y < lvl.length; y++) {
            for (int x = 0; x < lvl[y].length; x++) {
                int id = lvl[y][x];
                g.drawImage(getSprite(id), x * 64, y * 64, null);
            }
        }
    }

    private BufferedImage getSprite(int spriteId) {
        return game.getTileManager().getSprite(spriteId);
    }


    private void drawSelectedTile(Graphics g) {
        if (selectedTile != null && drawSelect) {
            g.drawImage(selectedTile.getSprite(), mouseX, mouseY, 64, 64, null);
            g.setColor(Color.BLACK);
            g.drawRect(mouseX, mouseY, 64, 64);
        }
    }

    public void saveLevel() {
        LoadSave.SaveLevel("newlevel", lvl);
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

        } else {
            changedTile(x, y);
            mouseX = (x / 64) * 64;
            mouseY = (y / 64) * 64;
        }
    }


}
