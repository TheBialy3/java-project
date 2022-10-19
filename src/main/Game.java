package main;

import javax.swing.*;

public class Game  extends JFrame {

    private GameScreen gameScreen;

    public Game() {
       setSize(1296,1320);
       setVisible(true);
       setDefaultCloseOperation(EXIT_ON_CLOSE);
       setLocationRelativeTo(null);

       gameScreen = new GameScreen();
       add(gameScreen);
    }

    public static void main(String[] args){
        System.out.println("tak");
        Game game = new Game();
    }
}
