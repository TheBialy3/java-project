package main;

import inputs.KeyboardListener;
import inputs.MyMouseListener;
import scenes.Menu;
import scenes.Playing;
import scenes.Settings;

import javax.swing.*;



public class Game extends JFrame implements Runnable {


    private GameScreen gameScreen;
    private Thread threadGame;



    private final double FPS_SET=120;
    private final double UPS_SET=60;



    //Classes
    private Render render;
    private Menu menu;
    private Playing playing;
    private Settings settings;

    public Game() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        initClasses();

        add(gameScreen);
        pack();

        setVisible(true);
    }

    private void initClasses(){
        gameScreen = new GameScreen(this);
        render = new Render(this);
        menu = new Menu(this);
        playing = new Playing(this);
        settings = new Settings(this);
    }



    private void updateGame() {
        // System.out.println("UPS: ");
    }



    private void start() {
        threadGame = new Thread(this) {
        };
        threadGame.start();
    }

    public static void main(String[] args) {

        Game game = new Game();
        game.gameScreen.initInputs();
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
    //Geters and Setters:
    public Render getRender(){
        return render;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public Playing getPlaying() {
        return playing;
    }

    public void setPlaying(Playing playing) {
        this.playing = playing;
    }

    public Settings getSettings() {
        return settings;
    }

    public void setSettings(Settings settings) {
        this.settings = settings;
    }
}
