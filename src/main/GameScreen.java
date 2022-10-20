package main;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;


public class GameScreen extends JPanel {


    private Game game;
    private ArrayList<BufferedImage> sprites = new ArrayList<>();
    private Dimension size;

    public GameScreen(Game game) {
        this.game = game;


        setPanelSize();
    }

    private void setPanelSize() {
        size=new Dimension(1280,1280);//1296, 1320
        setMaximumSize(size);
        setPreferredSize(size);
        setMaximumSize(size);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        game.getRender().render(g);
    }
}
