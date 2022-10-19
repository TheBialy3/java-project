package main;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class GameScreen extends JPanel {

    private Random random;

    public GameScreen() {
        random = new Random();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int y = 0; y < 20; y++) {
            for (int x = 0; x < 20; x++) {
                g.setColor(getRandColor());
                g.fillRect(x * 64, y * 64, 64, 64);
            }
        }

    }

    private Color getRandColor() {
        int r = random.nextInt(255);
        int g = random.nextInt(255);
        int b = random.nextInt(255);
        return new Color(r, g, b);
    }
}
