package main;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

public class GameScreen extends JPanel {

    private Random random;
    private BufferedImage img;
    private ArrayList<BufferedImage> sprites = new ArrayList<>();
    private int frames;
    private long lastTime;


    public GameScreen(BufferedImage img) {
        random = new Random();
        this.img = img;
        loadSprites();

    }

    private void loadSprites() {
        for (int y = 0; y < 10; y++) {
            for (int x = 0; x < 10; x++) {
                sprites.add(img.getSubimage(64 * x, 64*y, 64, 64));
            }
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
//        g.drawImage(sprites.get(1), 0, 0, null);
//        g.drawImage(img.getSubimage(0,0,200,200),64,64,null);


        for (int y = 0; y < 20; y++) {
            for (int x = 0; x < 20; x++) {

                g.drawImage(sprites.get(getRandInt()),x * 64, y * 64, null);
            }
        }

        callFps();

    }

    private void callFps() {
        frames++;
        if(System.currentTimeMillis()-lastTime>=1000){
            System.out.println("FPS: "+frames);
            frames=0;
            lastTime=System.currentTimeMillis();
        }
    }

    private int getRandInt() {

        return random.nextInt(10);
    }

    private Color getRandColor() {
        int r = random.nextInt(255);
        int g = random.nextInt(255);
        int b = random.nextInt(255);
        return new Color(r, g, b);
    }
}
