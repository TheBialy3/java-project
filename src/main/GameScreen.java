package main;

import inputs.KeyboardListener;
import inputs.MyMouseListener;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;


public class GameScreen extends JPanel {


    private Game game;
    private Dimension size,sizeMax,sizeMin;


    private MyMouseListener myMouseListener;
    private KeyboardListener keyboardListener;

    public GameScreen(Game game) {
        this.game = game;
        setPanelSize();
    }

    public void initInputs(){
        myMouseListener= new MyMouseListener(game);
        keyboardListener= new KeyboardListener(game);

        addMouseListener(myMouseListener);
        addMouseMotionListener(myMouseListener);
        addKeyListener(keyboardListener);

        requestFocus();
    }

    private void setPanelSize() {
        size=new Dimension(1536,1280);//1296, 1320
        sizeMax=new Dimension(2560,1440);
        sizeMin=new Dimension(536,480);
        setMaximumSize(sizeMax);
        setPreferredSize(size);
        setMinimumSize(sizeMin);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        game.getRender().render(g);
        game.drawsFps(g);
        game.drawBirds(g);
    }

}
