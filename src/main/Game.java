package main;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;


public class Game extends JFrame implements Runnable {

    private BufferedImage img;
    private GameScreen gameScreen;
    private Thread threadGame;

    private final double FPS_SET=120;
    private final double UPS_SET=60;

    public Game() {

        importImg();

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        gameScreen = new GameScreen(img);
        add(gameScreen);

        pack();
        setVisible(true);
    }



    private void updateGame() {
        // System.out.println("UPS: ");
    }

    private void importImg() {
        InputStream is = getClass().getResourceAsStream("/tak.png");
        try {
            img = ImageIO.read(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void start() {
        threadGame = new Thread(this) {
        };
        threadGame.start();
    }

    public static void main(String[] args) {

        Game game = new Game();
        game.start();
    }

    @Override
    public void run() {

        double timePerFrame = 1000000000.0 / FPS_SET;
        double timePerUpdate = 1000000000.0 / UPS_SET;

        long lastFrame= System.nanoTime();
        long lastUpdate= System.nanoTime();
        long lastTimeCheck=System.currentTimeMillis();

        int frames=0;
        int updates=0;

        long now;

        while (true) {
            now=System.nanoTime();
            //Render
            if (now - lastFrame >= timePerFrame) {
                repaint();
                lastFrame = now;
                frames++;
            }
            //Update
            if (now - lastUpdate >= timePerUpdate) {
                updateGame();
                lastUpdate= now;
                updates++;
            }
            if(System.currentTimeMillis()-lastTimeCheck>=1000){

                if(System.currentTimeMillis()-lastTimeCheck>=1000){
                    System.out.println("FPS: "+frames+"| UPS:"+ updates);
                    frames=0;
                    updates=0;
                    lastTimeCheck=System.currentTimeMillis();
                }
            }
        }
    }
}
