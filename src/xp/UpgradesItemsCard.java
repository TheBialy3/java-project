package xp;

import objects.Card;

import java.util.ArrayList;

public class UpgradesItemsCard {
    private ArrayList<Card> cards=new ArrayList<>();
    public UpgradesItemsCard() {
        createUpgraces();
    }
    public void createUpgraces(){
        int id=0;
        cards.add(new Card(id++, "More damage", "All your tawers gets damage +1",  true));
        cards.add(new Card(id++, "More damage", "All your tawers gets damage +1",  true));
        cards.add(new Card(id++, "More damage", "All your tawers gets damage +1",  true));
        cards.add(new Card(id++, "More damage", "All your tawers gets damage +1",  true));

    }



}
