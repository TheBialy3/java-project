package main;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class GameScreen extends JPanel {

    private Random random;
    private BufferedImage img;

    public GameScreen(BufferedImage img) {
        random = new Random();
        this.img = img;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(img.getSubimage(64*1,64,64,64),0,0,null);










//        for (int y = 0; y < 20; y++) {
//            for (int x = 0; x < 20; x++) {
//                g.setColor(getRandColor());
//                g.fillRect(x * 64, y * 64, 64, 64);
//            }
//        }

    }

    private Color getRandColor() {
        int r = random.nextInt(255);
        int g = random.nextInt(255);
        int b = random.nextInt(255);
        return new Color(r, g, b);
    }
}
