package ui;

import objects.Tile;
import scenes.Playing;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static main.GameStates.MENU;
import static main.GameStates.SetGameState;

public class SideBar {

    private int x, y, width, height;
    private Playing playing;
    private MyButton bMenu;
    private Tile selectedTile;

    private ArrayList<MyButton> tileButtons = new ArrayList<>();

    public SideBar(int x, int y, int width, int height, Playing playing) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.playing = playing;


        initButtons();
    }

    public void draw(Graphics g) {
        //backGround
        g.setColor(new Color(0, 102, 102));
        g.fillRect(x, y, width, height);
        //buttons
        drawButtons(g);
    }

    private void initButtons() {
        int w = 64;
        int h = 64;
        int x = 1300;
        int y = 64;
        int diff = 100;
        int i = 0;
        int ii = 0;
        bMenu = new MyButton("Menu", 1300, 10, 160, 40);

        for (Tile tile : playing.getTileManager().tiles) {
            if (tile.getId() % 2 == 1 ) {
                tileButtons.add(new MyButton(tile.getName(), x+ diff, y + diff * ii, w, h, i));
                i++;
                ii++;
            }else{
                tileButtons.add(new MyButton(tile.getName(), x, y + diff * ii, w, h, i));
                i++;
            }
        }
    }

    private void drawButtons(Graphics g) {
        bMenu.draw(g);
        drawTileButtons(g);
        drawSelectedTile(g);
    }

    private void drawSelectedTile(Graphics g) {
        if(selectedTile != null){
            g.drawImage(selectedTile.getSprite(),1300,1200,64,64,null);
            g.setColor(Color.black);
            g.drawRect(1300,1200,64,64);
        }
    }

    private void drawTileButtons(Graphics g) {
        for (MyButton b : tileButtons) {
            //Sprite
            g.drawImage(getButtImg(b.getId()), b.x, b.y, b.width, b.height, null);
            //MouseOver
            if (b.isMouseOver()) {
                g.setColor(Color.WHITE);
            } else {
                g.setColor(Color.BLACK);
            }
            //Border
            g.drawRect(b.x, b.y, b.width, b.height);
            //MousePressed
            if(b.isMousePressed()){
                g.drawRect(b.x+1, b.y+1, b.width-2, b.height-2);
                g.drawRect(b.x+2, b.y+2, b.width-4, b.height-4);
            }
        }
    }

    public BufferedImage getButtImg(int id) {
        return playing.getTileManager().getSprite(id);
    }

    public void mouseClicked(int x, int y) {
        if (bMenu.getBounds().contains(x, y)) {
            SetGameState(MENU);
        }else{
            for(MyButton b : tileButtons){
                if(b.getBounds().contains(x,y)){
                    selectedTile = playing.getTileManager().getTile(b.id);
                    playing.setSelectedTile(selectedTile);
                    return;
                }
            }
        }
    }

    public void mouseMoved(int x, int y) {
        bMenu.setMouseOver(false);
        for (MyButton b : tileButtons) {
            b.setMouseOver(false);
        }
        if (bMenu.getBounds().contains(x, y)) {
            bMenu.setMouseOver(true);
        } else {
            for (MyButton b : tileButtons) {
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
        }else{
            for (MyButton b : tileButtons) {
                if (b.getBounds().contains(x, y)) {
                    b.setMousePressed(true);
                    return;
                }
            }
        }
    }

    private void resetButtons() {
        bMenu.resetBooleans();
        for (MyButton b : tileButtons) {
            b.resetBooleans();
        }
    }

}
