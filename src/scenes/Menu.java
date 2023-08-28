package scenes;

import helpz.LevelBuild;
import helpz.LoadSave;
import main.Game;
import managers.TileManager;
import objects.PathPoint;
import ui.MyButton;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;

import static main.GameStates.*;

public class Menu extends GameScene implements SceneMethods {

    private BufferedImage img;
    private PathPoint start, end;
    private int[][] lvl;
    private int xlevelSprite=-64,tilePixelNumber= 64 ,ran;
    private TileManager tileManager;
    private BufferedImage[] moveImages;
    private Random random = new Random();
    private ArrayList<BufferedImage> sprites = new ArrayList<>();

    private MyButton bPlaing, bEditing, bSettings, bQuit, bUpgrade;

    public Menu(Game game) {
        super(game);
        lvl = LevelBuild.getLevelData();
        tileManager = new TileManager();
        importImg();
        loadSprites();
        initButtons();
    }

    private void initButtons() {
        int w = 400;
        int h = w / 4;
        int x = 1536 / 2 - w / 2;
        int y = 200;
        int yOffset = 200;

        bPlaing = new MyButton("Play", x, y, w, h);
        bEditing = new MyButton("Edit", x, y + (yOffset * 1), w, h);
        bSettings = new MyButton("Settings", x, y + (yOffset * 2), w, h);
        bUpgrade = new MyButton("Upgrade", x, y + (yOffset * 3), w, h);
        bQuit = new MyButton("Quit", x, y + (yOffset * 4), w, h);
    }

    protected void updateTick() {
        tick++;
        if (tick >= ANIMATION_SPEED) {
            tick = 0;
            animationIndex++;
            if (animationIndex >= 64) {
                animationIndex = 0;
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
                    g.drawImage(getSprite(id, animationIndex), x * tilePixelNumber, y * tilePixelNumber, null);
                } else {
                    g.drawImage(getSprite(id), x * tilePixelNumber, y * tilePixelNumber, null);
                }
            }
        }
        drawIt(g);
        drawButtons(g);
    }

    private void drawIt(Graphics g) {
        xlevelSprite++;
        if(xlevelSprite>1600){
            resetDrawIt(g);
        }
        g.drawImage(moveImages[ran],xlevelSprite,507 ,tilePixelNumber,tilePixelNumber,null);
    }
    private void resetDrawIt(Graphics g) {
        xlevelSprite=-64;
        ran = random.nextInt(10);

    }

    @Override
    public void mouseClicked(int x, int y) {
        if (bPlaing.getBounds().contains(x, y)) {
            SetGameState(PLAYING);
        } else if (bEditing.getBounds().contains(x, y)) {
            SetGameState(EDITING);
        }else if (bSettings.getBounds().contains(x,y)){
            SetGameState(SETTINGS);
        } else if (bQuit.getBounds().contains(x, y)) {
            System.exit(0);
        }else if (bUpgrade.getBounds().contains(x, y)) {
            SetGameState(UPGRADE);
        }
    }

    @Override
    public void mouseMoved(int x, int y) {
        bPlaing.setMouseOver(false);
        if (bPlaing.getBounds().contains(x, y)) {
            bPlaing.setMouseOver(true);
        }
        bEditing.setMouseOver(false);
        if (bEditing.getBounds().contains(x, y)) {
            bEditing.setMouseOver(true);
        }
        bSettings.setMouseOver(false);
        if (bSettings.getBounds().contains(x,y)){
            bSettings.setMouseOver(true);
        }
        bUpgrade.setMouseOver(false);
        if (bUpgrade.getBounds().contains(x,y)){
            bUpgrade.setMouseOver(true);
        }
        bQuit.setMouseOver(false);
        if (bQuit.getBounds().contains(x, y)) {
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
        bUpgrade.resetBooleans();
        bQuit.resetBooleans();
    }

    @Override
    public void mousePressed(int x, int y) {
        if (bPlaing.getBounds().contains(x, y)) {
            bPlaing.setMousePressed(true);
        } else if (bEditing.getBounds().contains(x, y)) {
            bEditing.setMousePressed(true);
        }
        else if (bSettings.getBounds().contains(x,y)){
            bSettings.setMousePressed(true);
        }
        else if (bUpgrade.getBounds().contains(x,y)){
            bUpgrade.setMousePressed(true);
        }
        else if (bQuit.getBounds().contains(x, y)) {
            bQuit.setMousePressed(true);
        }
    }

    private void drawButtons(Graphics g) {
        bPlaing.draw(g);
        bEditing.draw(g);
        bSettings.draw(g);
        bUpgrade.draw(g);
        bQuit.drawQuit(g);
    }

    private void loadSprites() {
        for (int y = 0; y < 10; y++) {
            for (int x = 0; x < 10; x++) {
                sprites.add(img.getSubimage(tilePixelNumber * x, tilePixelNumber * y, tilePixelNumber, tilePixelNumber));
            }
        }
    }

    private void importImg() {
        InputStream is = getClass().getResourceAsStream("/pngFile/tak.png");
        try {
            img = ImageIO.read(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        BufferedImage atlas = LoadSave.getSpriteAtlas();
        moveImages = new BufferedImage[10];
        moveImages[0]= atlas.getSubimage(3 * tilePixelNumber, 1 * tilePixelNumber, tilePixelNumber, tilePixelNumber);
        moveImages[1] = atlas.getSubimage(0 * tilePixelNumber, 6 * tilePixelNumber, tilePixelNumber, tilePixelNumber);
        moveImages[2] = atlas.getSubimage(4 * tilePixelNumber, 2 * tilePixelNumber, tilePixelNumber, tilePixelNumber);
        moveImages[3] = atlas.getSubimage(2 * tilePixelNumber, 2 * tilePixelNumber, tilePixelNumber, tilePixelNumber);
        moveImages[4] = atlas.getSubimage(1 * tilePixelNumber, 6 * tilePixelNumber, tilePixelNumber, tilePixelNumber);
        moveImages[5] = atlas.getSubimage(0 * tilePixelNumber, 8 * tilePixelNumber, tilePixelNumber, tilePixelNumber);
        moveImages[6] = atlas.getSubimage(8 * tilePixelNumber, 2 * tilePixelNumber, tilePixelNumber, tilePixelNumber);
        moveImages[7] = atlas.getSubimage(0 * tilePixelNumber, 7 * tilePixelNumber, tilePixelNumber, tilePixelNumber);
        moveImages[8] = atlas.getSubimage(4 * tilePixelNumber, 0 * tilePixelNumber, tilePixelNumber, tilePixelNumber);
        moveImages[9] = atlas.getSubimage(7 * tilePixelNumber, 2 * tilePixelNumber, tilePixelNumber, tilePixelNumber);
    }

    @Override
    public void mouseDragged(int x, int y) {
        if (bPlaing.getBounds().contains(x, y)) {
            SetGameState(PLAYING);
        } else if (bEditing.getBounds().contains(x, y)) {
            SetGameState(EDITING);
        }else if (bSettings.getBounds().contains(x,y)){
            SetGameState(SETTINGS);
        }else if (bUpgrade.getBounds().contains(x,y)){
            SetGameState(UPGRADE);
        } else if (bQuit.getBounds().contains(x, y)) {
            System.exit(0);
        }
    }
}
