package main;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;

public class Render {

    private GameScreen gameScreen;
    private BufferedImage img;

    private Random random;
    private ArrayList<BufferedImage> sprites = new ArrayList<>();

    public Render(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
        random = new Random();
        importImg();
        loadSprites();



    }

    public void render(Graphics g) {

        switch (GameStates.gameStates) {
            case MENU:
                for (int y = 0; y < 20; y++) {
                    for (int x = 0; x < 20; x++) {

                        g.drawImage(sprites.get(getRandInt()), x * 64, y * 64, null);
                    }
                }
                break;
            case PLAYING:

                break;
            case SETTINGS:

                break;
        }
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
