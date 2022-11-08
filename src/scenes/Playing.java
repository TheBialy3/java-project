package scenes;

import helpz.LoadSave;
import main.Game;
import managers.EnemyMenager;
import ui.ActionBar;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Playing extends GameScene implements SceneMethods {

    private static int[][] lvl;
    private ActionBar actionBar;
    private int mouseX, mouseY;
    private EnemyMenager enemyMenager;

    public Playing(Game game) {
        super(game);
        LoadDefoultLevel();
        actionBar = new ActionBar(1280, 0, 256, 1280, this);

        enemyMenager = new EnemyMenager(this);
    }

    public void setLevel(int[][] lvl){
        this.lvl=lvl;
    }

    public void update(){
        updateTick();
        enemyMenager.update();

    }

    private void LoadDefoultLevel() {
        lvl = LoadSave.GetLevelData("newlevel");
    }

    @Override
    public void render(Graphics g) {
        drawLevel(g);
        actionBar.draw(g);
        enemyMenager.draw(g);
    }

    private void drawLevel(Graphics g) {
        for (int y = 0; y < lvl.length; y++) {
            for (int x = 0; x < lvl[y].length; x++) {
                int id = lvl[y][x];
                if (isAnimation(id)) {
                    g.drawImage(getSprite(id,animationIndex), x * 64, y * 64, null);
                } else {
                    g.drawImage(getSprite(id), x * 64, y * 64, null);
                }
            }
        }
    }

    public int getTileType(int x, int y) {
        int xCord = x/64;
        int yCord = y/64;

        if(xCord >19||xCord <0){
            return 0;
        }else if(yCord >19||xCord <0){
            return 0;}

        int id = lvl[y/64][x/64];
        return game.getTileManager().getTile(id).getTileType();
    }


    @Override
    public void mouseClicked(int x, int y) {
        if (x >= 1280) {
            actionBar.mouseClicked(x, y);
        }else{
//            enemyMenager.addEnemy(x,y);
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
