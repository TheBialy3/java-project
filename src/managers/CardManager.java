package managers;

import main.Game;
import objects.Card;
import scenes.Playing;

import java.awt.image.BufferedImage;
import java.util.ArrayList;


//can be used in future for unlocking a new cards

public class CardManager {
    private Playing playing;
    private Game game;
    ArrayList<Card> allCards=new ArrayList<>();
    private BufferedImage cardImg[];
    public CardManager(Playing playing) {
        this.playing = playing;
        this.allCards= game.getCards();
    }



}
