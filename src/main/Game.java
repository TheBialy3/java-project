package main;

import managers.TileManager;
import scenes.*;
import scenes.Menu;

import javax.swing.*;
import java.awt.*;


public class Game extends JFrame implements Runnable {

    public int xp=0;
    private GameScreen gameScreen;
    private Thread threadGame;

    private final double FPS_SET = 60;
    private final double UPS_SET = 60;
    public int fps = 0;

    //Classes
    private Render render;
    private Menu menu;
    private Playing playing;
    private Settings settings;
    private Editing editing;
    private Upgrade upgrade;
    private TileManager tileManager;
    private GameOver gameOver;

    public static void main(String[] args) {
        Game game = new Game();
        game.gameScreen.initInputs();
        game.start();
    }

    public Game() {
        initClasses();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("gtd");
        setResizable(false);
        add(gameScreen);
        pack();
        setVisible(true);
    }

    public void initClasses() {
        tileManager = new TileManager();
        gameScreen = new GameScreen(this);
        render = new Render(this);
        menu = new Menu(this);
        playing = new Playing(this);
        settings = new Settings(this);
        editing = new Editing(this);
        upgrade = new Upgrade(this);
        gameOver = new GameOver(this);
    }

    //for lvl chose
    public void initClasses(int lvl) {
        tileManager = new TileManager();
        gameScreen = new GameScreen(this);
        render = new Render(this);
        menu = new Menu(this);
        playing = new Playing(this);
        settings = new Settings(this);
        editing = new Editing(this);
        gameOver = new GameOver(this);
    }

    private void start() {
        threadGame = new Thread(this) {
        };
        threadGame.start();
    }

    private void updateGame() {
        switch (GameStates.gameStates) {
            case PLAYING:
                playing.update();
                break;
            case EDITING:
                editing.update();
                break;
            case MENU:
                menu.update();
                break;
            case SETTINGS:
                settings.update();
                break;
            case GAME_OVER:
                gameOver.update();
                break;
        }

    }

    public void drawfps(Graphics g) {
        g.setFont(new Font("Monospaced", Font.BOLD, 17));
            g.setColor(new Color(0, 0, 0));
            g.drawString("FPS:" + fps, 2, 16);

    }


    @Override
    public void run() {
        double timePerFrame = 1000000000.0 / FPS_SET;
        double timePerUpdate = 1000000000.0 / UPS_SET;

        long lastFrame = System.nanoTime();
        long lastUpdate = System.nanoTime();
        long lastTimeCheck = System.currentTimeMillis();

        int frames = 0;
        int updates = 0;

        long now;

        while (true) {
            now = System.nanoTime();
            //Render
            if (now - lastFrame >= timePerFrame) {
                repaint();
                lastFrame = now;
                frames++;
            }
            //Update
            if (now - lastUpdate >= timePerUpdate) {
                updateGame();
                lastUpdate = now;
                updates++;
            }
            if (System.currentTimeMillis() - lastTimeCheck >= 1000) {
                if (System.currentTimeMillis() - lastTimeCheck >= 1000) {
//                    System.out.println("FPS: " + frames + "| UPS:" + updates);
                    fps=frames;
                    frames = 0;
                    updates = 0;
                    lastTimeCheck = System.currentTimeMillis();
                }
            }
        }
    }

    //Geters and Setters:
    public Render getRender() {
        return render;
    }

    public Menu getMenu() {
        return menu;
    }

    public Playing getPlaying() {
        return playing;
    }

    public Settings getSettings() {
        return settings;
    }

    public Editing getEditing() {
        return editing;
    }

    public GameOver getGameOver() {
        return gameOver;
    }

    public TileManager getTileManager() {
        return tileManager;
    }
}
