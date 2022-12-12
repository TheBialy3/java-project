package scenes;

import helpz.LevelBuild;
import main.Game;
import managers.EnemyMenager;
import managers.TileManager;
import objects.PathPoint;
import ui.MyButton;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import static main.GameStates.*;

public class Menu extends GameScene implements SceneMethods{

    private BufferedImage img;
    private PathPoint start, end;
    private int[][] lvl;
    private TileManager tileManager;
    private EnemyMenager enemyMenager;

    private ArrayList<BufferedImage> sprites = new ArrayList<>();

    private MyButton bPlaing,bEditing,bSettings,bQuit;

    public Menu(Game game) {
        super(game);
        lvl = LevelBuild.getLevelData();
        tileManager = new TileManager();
         start =new PathPoint(1,10);
         end = new PathPoint(19,10);

        importImg();
        loadSprites();
        initButtons();
    }

    private void initButtons() {
        int w=400;
        int h=w/4;
        int x=1536/2-w/2;
        int y=300;
        int yOffset=200;

        bPlaing = new MyButton("Play",x,y,w,h);
        bEditing = new MyButton("Edit",x,y+(yOffset*1),w,h);
        bSettings = new MyButton("Settings",x,y+(yOffset*2),w,h);
        bQuit = new MyButton("Quit",x,y+(yOffset*3),w,h);
    }

    protected void updateTick() {
        tick++;
        if(tick>=ANIMATION_SPEED){
            tick=0;
            animationIndex++;
            if(animationIndex>=30){
                animationIndex=0;
            }
        }
    }

    public void update() {
        updateTick();
    }

    @Override
    public void render(Graphics g) {
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

        drawButtons(g);
        //enemyMenager.update();
    }

    @Override
    public void mouseClicked(int x, int y) {
        if (bPlaing.getBounds().contains(x,y)){
            SetGameState(PLAYING);
        }else if (bEditing.getBounds().contains(x,y)){
            SetGameState(EDITING);
        }else if (bSettings.getBounds().contains(x,y)){
            SetGameState(SETTINGS);
        }else if (bQuit.getBounds().contains(x,y)){
            System.exit(0);
        }
    }

    @Override
    public void mouseMoved(int x, int y) {
        bPlaing.setMouseOver(false);
        if (bPlaing.getBounds().contains(x,y)){
            bPlaing.setMouseOver(true);
        }
        bEditing.setMouseOver(false);
        if (bEditing.getBounds().contains(x,y)){
            bEditing.setMouseOver(true);
        }
        bSettings.setMouseOver(false);
        if (bSettings.getBounds().contains(x,y)){
            bSettings.setMouseOver(true);
        }
        bQuit.setMouseOver(false);
        if (bQuit.getBounds().contains(x,y)){
            bQuit.setMouseOver(true);
        }
    }

    @Override
    public void mouseReleased(int x, int y) {
        resetButtons();
    }

    private void resetButtons() {
        bPlaing.resetBooleans();
        bEditing.resetBooleans();
        bSettings.resetBooleans();
        bQuit.resetBooleans();
    }

    @Override
    public void mousePressed(int x, int y) {
        if (bPlaing.getBounds().contains(x,y)){
            bPlaing.setMousePressed(true);
        }
        else if (bEditing.getBounds().contains(x,y)){
            bEditing.setMousePressed(true);
        }
        else if (bSettings.getBounds().contains(x,y)){
            bSettings.setMousePressed(true);
        }
        else if (bQuit.getBounds().contains(x,y)){
            bQuit.setMousePressed(true);
        }
    }

    private void drawButtons(Graphics g) {
        bPlaing.draw(g);
        bEditing.draw(g);
        bSettings.draw(g);
        bQuit.draw(g);
    }

    private void loadSprites() {
        for (int y = 0; y < 10; y++) {
            for (int x = 0; x < 10; x++) {
                sprites.add(img.getSubimage(64 * x, 64 * y, 64, 64));
            }
        }
    }

    private void importImg() {
        InputStream is = getClass().getResourceAsStream("/tak.png");
        try {
            img = ImageIO.read(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void mouseDragged(int x, int y) {

    }
}
