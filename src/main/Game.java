package main;

import managers.TileManager;
import objects.Card;
import scenes.*;
import scenes.Menu;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import static helpz.LoadSave.*;


public class Game extends JFrame implements Runnable {

    public static int xp = 0;
    private ArrayList<Integer> save;
    ArrayList<Boolean> cardSave=new ArrayList<>();
    private GameScreen gameScreen;
    private Thread threadGame;

    private final double FPS_SET = 60;
    private final double UPS_SET = 60;
    public int fps = 0;
    public ImageIcon img;

    //Classes
    private Render render;
    private Menu menu;
    private Playing playing;
    private Settings settings;
    private Editing editing;
    private Upgrade upgrade;
    private Bestiary bestiary;
    private TileManager tileManager;
    private GameOver gameOver;
    private ArrayList<Card> cards = new ArrayList<>();

    public static void main(String[] args) {
        Game game = new Game();
        game.gameScreen.initInputs();
        game.start();

    }

    public Game() {
        initClasses();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Biały Precel TD");
        img = new ImageIcon("res/rest/logo.png");
        setIconImage(img.getImage());
        //setUndecorated(true);
        setResizable(false);
        add(gameScreen);
        pack();
        setVisible(true);

        getCardSave();
    }

    private void getCardSave() {
        try{
            save = GetXpData();
            this.xp = save.get(0);
            cardSave = GetCardSave();
            this.cards = upgrade.getCards();
            for (Card card : cards) {
                card.setUnlocked(cardSave.get(card.getId()));
            }
        }catch (Exception e){
            System.out.println("hmmm");
            CreateSaveFile();
            saveGame();
        }
    }


    public static void addXp() {
        xp++;
    }
    //can be used in future
    public static void costXp(int cost) {
        xp-=cost;
    }

    public  void saveGame(){
        cards=upgrade.getCards();
        SaveXpToFile(xp, cards);
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
        bestiary = new Bestiary(this);
    }

    //for lvl chose, not added yet
    public void initClasses(int lvl) {
        tileManager = new TileManager();
        gameScreen = new GameScreen(this);
        render = new Render(this);
        menu = new Menu(this);
        playing = new Playing(this);
        settings = new Settings(this);
        editing = new Editing(this);
        upgrade = new Upgrade(this);
        gameOver = new GameOver(this);
        bestiary = new Bestiary(this);
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
            case BESTIARY:
                bestiary.update();
                break;
            case UPGRADE:
                upgrade.update();
                break;
        }

    }

    public void drawsFps(Graphics g) {
        g.setFont(new Font("Monospaced", Font.BOLD, 17));
        g.setColor(new Color(0, 0, 0));
        g.drawString("FPS:" + fps, 2, 16);
       // g.drawString("xp:" + xp, 2, 32);
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
                    fps = frames;
                    frames = 0;
                    updates = 0;
                    lastTimeCheck = System.currentTimeMillis();
                }
            }
        }
    }

    //Getters and Setters:
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

    public Upgrade getUpgrade() {
        return upgrade;
    }

    public Bestiary getBestiary() {
        return bestiary;
    }

    public TileManager getTileManager() {
        return tileManager;
    }

    public ArrayList<Card> getCards() {
        return cards;
    }
}
