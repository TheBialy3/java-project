package scenes;

import helpz.LevelBuild;
import main.Game;
import managers.TileManager;
import ui.SideBar;
import ui.MyButton;

import java.awt.*;

import static main.GameStates.*;

public class Playing extends GameScene implements SceneMethods {

    private int[][] lvl;
    private TileManager tileManager;

    private SideBar sideBar;

    public Playing(Game game) {
        super(game);

        lvl = LevelBuild.getLevelData();
        tileManager = new TileManager();
        sideBar = new SideBar(1280,0,200,1280, this);


        //The lvl
        //tileManager

    }

    public TileManager getTileManager(){
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
    }



    @Override
    public void mouseClicked(int x, int y) {
        if (x >= 1280){
            sideBar.mouseClicked(x,y);
        }
    }

    @Override
    public void mouseMoved(int x, int y) {
        if (x >= 1280){
            sideBar.mouseMoved(x,y);
        }
    }

    @Override
    public void mouseReleased(int x, int y) {
        if (x >= 1280){
            sideBar.mouseReleased(x,y);
        }
    }


    @Override
    public void mousePressed(int x, int y) {
        if (x >= 1280){
            sideBar.mousePressed(x,y);
        }
    }
}
