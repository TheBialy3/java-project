package managers;

import helpz.LoadSave;
import main.Game;
import objects.Card;
import scenes.Playing;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class CardMenager {
    private Playing playing;
    private Game game;
    ArrayList<Card> allCards=new ArrayList<>();
    private BufferedImage cardImg[];
    public CardMenager(Playing playing) {
        this.playing = playing;
        this.allCards= game.getCards();
    }


    public ArrayList<Card> CardMenager() {

        ArrayList<Card> list=new ArrayList<>();
        for(Card card:allCards){
            if (card.isUnlocked()){
                if(!card.isActive()){
                    list.add(card);
                }
            }
        }
        return list;
    }

}
