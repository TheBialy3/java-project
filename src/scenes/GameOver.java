package scenes;

import helpz.LevelBuild;
import main.Game;
import managers.TileManager;
import ui.MyButton;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import static main.GameStates.*;

public class GameOver extends GameScene implements SceneMethods {

    private BufferedImage img;
    private int[][] lvl;
    private TileManager tileManager;
    private ArrayList<BufferedImage> sprites = new ArrayList<>();
    private MyButton bReplay, bMenu;

    public GameOver(Game game) {
        super(game);
        lvl = LevelBuild.getLevelData();
        tileManager = new TileManager();
        importImg();
        loadSprites();
        initButtons();
    }

    public void update() {
        updateTick();
    }

    protected void updateTick() {
        tick++;
        if(tick>=ANIMATION_SPEED){
            tick=0;
            animationIndex++;
            if(animationIndex>=64){
                animationIndex=0;
            }
        }
    }

    private void initButtons() {
        int w = 400;
        int h = w / 4;
        int x = 1536 / 2 - w / 2;
        int y = 324;
        int yOffset = 200;

        // bPlaing = new MyButton("Play",x,y,w,h);
        bReplay = new MyButton("Replay", x, y + (yOffset * 1), w, h);
        bMenu = new MyButton("Menu", x, y + (yOffset * 2), w, h);
        //bQuit = new MyButton("Quit",x,y+(yOffset*3),w,h);
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



        drawGameOver(g);
        drawButtons(g);
    }

    private void drawButtons(Graphics g) {
        bReplay.draw(g);
        bMenu.draw(g);
    }

    public static void drawGameOver(Graphics g) {
        g.setColor(new Color(171, 0, 0));
        g.setFont(new Font("Serif", Font.BOLD, 70));
        String text = "GAME OVER";
        int w = g.getFontMetrics().stringWidth(text);
        g.drawString(text, 1536 / 2 - w / 2, 340);
    }

    private void loadSprites() {
        for (int y = 0; y < 10; y++) {
            for (int x = 0; x < 10; x++) {
                sprites.add(img.getSubimage(64 * x, 64 * y, 64, 64));
            }
        }
    }

    private void importImg() {
        InputStream is = getClass().getResourceAsStream("/pngFile/final.png");//poczatkowyWyglad
        try {
            img = ImageIO.read(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void mouseClicked(int x, int y) {
        if (bMenu.getBounds().contains(x, y)) {
            SetGameState(MENU);
        } else if (bReplay.getBounds().contains(x, y)) {
             replayGame();
        }
    }

    public void replayGame() {
        game.getPlaying().resetEvrything();
        SetGameState(PLAYING);
    }

    @Override
    public void mouseMoved(int x, int y) {
        bMenu.setMouseOver(false);

        if (bMenu.getBounds().contains(x, y)) {
            bMenu.setMouseOver(true);}

        bReplay.setMouseOver(false);
         if (bReplay.getBounds().contains(x, y)) {
            bReplay.setMouseOver(true);
        }
    }


    public void mouseReleased(int x, int y) {
        resetButtons();
    }

    private void resetButtons() {
        bMenu.resetBooleans();
        bReplay.resetBooleans();
    }

    @Override
    public void mousePressed(int x, int y) {
        if (bMenu.getBounds().contains(x, y)) {
            bMenu.setMousePressed(true);
        }else if (bReplay.getBounds().contains(x, y)) {
            bReplay.setMousePressed(true);
        }
    }

    @Override
    public void mouseDragged(int x, int y) {

    }
}
