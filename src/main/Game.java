package main;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;


public class Game extends JFrame {

    private GameScreen gameScreen;
    private double timePerFrame;
    private long lastFrame;
    private long lastUpdate;
    private double timePerUpdate;
    private long lastTimeUps;
    private BufferedImage img;
    private int updates;

    public Game() {
        importImg();
        timePerFrame = 1000000000.0 / 120.0;
        timePerUpdate = 1000000000.0 / 60.0;
        setSize(1296, 1320);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        gameScreen = new GameScreen(img);
        add(gameScreen);
        setVisible(true);
    }

    private void loopGame() {
        while (true) {
            if (System.nanoTime() - lastUpdate >= timePerUpdate) {
                updateGame();
                callUps();
            }
            if (System.nanoTime() - lastFrame >= timePerFrame) {
                lastFrame = System.nanoTime();
                repaint();
            } else {
                //hehe
            }
        }
    }

    private void updateGame() {
        updates++;
        lastUpdate=System.nanoTime();
       // System.out.println("UPS: ");
    }
    private void callUps() {

        if(System.currentTimeMillis()-lastTimeUps>=1000){
            System.out.println("UPS: "+ updates);
            updates =0;
            lastTimeUps=System.currentTimeMillis();
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

    public static void main(String[] args) {

        Game game = new Game();
        game.loopGame();
    }
}
