package scenes;

import main.Game;
import ui.MyButton;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;

public class Menu extends GameScene implements SceneMethods{

    private BufferedImage img;

    private Random random;
    private ArrayList<BufferedImage> sprites = new ArrayList<>();

    private MyButton bPlaing,bSettings,bQuit;

    public Menu(Game game) {
        super(game);
        random = new Random();
        importImg();
        loadSprites();
        initButtons();
    }

    private void initButtons() {
        bPlaing = new MyButton("Play",300,300,300,30);
    }

    @Override
    public void render(Graphics g) {
        drawButtons(g);
    }

    @Override
    public void mouseClicked(int x, int y) {

    }

    private void drawButtons(Graphics g) {
        bPlaing.draw(g);
    }

    private void loadSprites() {
        for (int y = 0; y < 10; y++) {
            for (int x = 0; x < 10; x++) {
                sprites.add(img.getSubimage(64 * x, 64 * y, 64, 64));
            }
        }
    }

    private int getRandInt() {
        return random.nextInt(10);
    }

    private void importImg() {
        InputStream is = getClass().getResourceAsStream("/tak.png");
        try {
            img = ImageIO.read(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
