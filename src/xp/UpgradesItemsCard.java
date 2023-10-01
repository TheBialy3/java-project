package xp;

import objects.Card;

import java.util.ArrayList;

public class UpgradesItemsCard {
    private ArrayList<Card> cards=new ArrayList<>();
    public UpgradesItemsCard() {
        createUpgrades();
    }
    public void createUpgrades(){
        int id=0;
        cards.add(new Card(id++, "More Damage", "All your towers gets more Damage +1",  true));
        cards.add(new Card(id++, "More Speed", "All your towers gets more Attack Speed",  true));
        cards.add(new Card(id++, "More Range", "All your towers gets more Range",  true));

        cards.add(new Card(id++, "Attack Speed Boost", "All your towers have Attack Speed Boosted",  false));
        cards.add(new Card(id++, "Longer duration", "All your towers that do Damage over time (DOT) do it longer",  false));

        cards.add(new Card(id++, "Volcano aftershock", "Volcano will now exploding 2 times",  false));

        cards.add(new Card(id++, "Strong Sniper", "Sniper will now shoot bigger caliber",  false));
        cards.add(new Card(id++, "Fast Sniper", "Sniper will now shoot auto rifle",  false));

        cards.add(new Card(id++, "Global Frost Mage", "Volcano will now exploding 2 times",  false));
        cards.add(new Card(id++, "Frost Arch Mage", "Volcano will now exploding 2 times",  false));

        cards.add(new Card(id++, "Big Canon", "Volcano will now exploding 2 times",  false));
        cards.add(new Card(id++, "Fast Canon", "Volcano will now exploding 2 times",  false));
        cards.add(new Card(id++, "Strong Canon", "Volcano will now exploding 2 times",  false));

        cards.add(new Card(id++, "Long live the Mine", "Volcano will now exploding 2 times",  false));
        cards.add(new Card(id++, "Two Mine for One", "Volcano will now exploding 2 times",  false));

        cards.add(new Card(id++, "Two Mine for One", "Volcano will now exploding 2 times",  false));
        cards.add(new Card(id++, "Two Mine for One", "Volcano will now exploding 2 times",  false));
        cards.add(new Card(id++, "Two Mine for One", "Volcano will now exploding 2 times",  false));
    }
}
