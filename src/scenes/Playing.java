package scenes;

import helpz.LoadSave;
import main.Game;
import ui.ActionBar;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Playing extends GameScene implements SceneMethods {

    private static int[][] lvl;
    private ActionBar actionBar;
    private int mouseX, mouseY;

    public Playing(Game game) {
        super(game);
        LoadDefoultLevel();
        actionBar = new ActionBar(1280, 0, 256, 1280, this);
    }

    public void setLevel(int[][] lvl){
        this.lvl=lvl;
    }

    private void LoadDefoultLevel() {
        lvl = LoadSave.GetLevelData("newlevel");
    }

    @Override
    public void render(Graphics g) {
        drawLevel(g);
        actionBar.draw(g);
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

    @Override
    public void mouseClicked(int x, int y) {
        if (x >= 1280) {
            actionBar.mouseClicked(x, y);
        }
    }


    @Override
    public void mouseMoved(int x, int y) {
        if (x >= 1280) {
            actionBar.mouseMoved(x, y);
        } else {
            mouseX = (x / 64) * 64;
            mouseY = (y / 64) * 64;
        }
    }

    @Override
    public void mouseReleased(int x, int y) {
        if (x >= 1280) {
            actionBar.mouseReleased(x, y);
        }
    }


    @Override
    public void mousePressed(int x, int y) {
        if (x >= 1280) {
            actionBar.mousePressed(x, y);
        }
    }

    @Override
    public void mouseDragged(int x, int y) {
        if (x >= 1280) {

        } else {
            mouseX = (x / 64) * 64;
            mouseY = (y / 64) * 64;
        }
    }


}
