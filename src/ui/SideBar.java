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

    private ArrayList<MyButton> tileButtons = new ArrayList<>();

    public SideBar(int x, int y, int width, int height, Playing playing){
        this.x=x;
        this.y=y;
        this.width=width;
        this.height=height;
        this.playing=playing;


        initButtons();
    }

    public void draw(Graphics g){
        //backGround
        g.setColor(new Color(0,102,102));
        g.fillRect(x, y, width, height);
        //buttons
        drawButtons(g);
    }

    private void initButtons() {
        int w=64;
        int h=64;
        int x=1300;
        int y=60;
        int diff=80;
        int i=0;
        bMenu = new MyButton("Menu",1300,10,160,40);

        for(Tile tile:playing.getTileManager().tiles){
            tileButtons.add(new MyButton(tile.getName(),x,y+diff*i,w,h,i));
            i++;
        }
    }

    private void drawButtons(Graphics g) {
        bMenu.draw(g);
        drawTileButtons(g);

    }

    private void drawTileButtons(Graphics g) {
        for(MyButton b:tileButtons){
            g.drawImage(getButtImg(b.getId()),b.x,b.y,b.width,b.height,null);
        }
    }

    public BufferedImage getButtImg(int id) {
        return playing.getTileManager().getSprite(id);
    }

    public void mouseClicked(int x, int y) {
        if (bMenu.getBounds().contains(x,y)) {
            SetGameState(MENU);
        }
    }

    public void mouseMoved(int x, int y) {
        bMenu.setMouseOver(false);
        if (bMenu.getBounds().contains(x,y)){
            bMenu.setMouseOver(true);
        }
    }

    public void mouseReleased(int x, int y) {
        resetButtons();
    }

    public void mousePressed(int x, int y) {
        if (bMenu.getBounds().contains(x,y)) {
            bMenu.setMousePressed(true);
        }
    }

    private void resetButtons() {
        bMenu.resetBooleans();
    }

}
