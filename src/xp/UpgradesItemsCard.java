package xp;

import objects.Card;
import scenes.Upgrade;

import java.util.ArrayList;

public class UpgradesItemsCard {
    private ArrayList<Card> cards = new ArrayList<>();
    private Upgrade upgrade;

    public UpgradesItemsCard(Upgrade upgrade) {
        this.upgrade=upgrade;
        createUpgrades();
    }

    public void createUpgrades() {
        int id = 0;
        //starting cards
        cards.add(new Card(id++, "More Damage", "All your towers gets more Damage +1", true));
        cards.add(new Card(id++, "More Speed", "All your towers gets more Attack Speed", true));
        cards.add(new Card(id++, "More Range", "All your towers gets more Range", true));
        //for all
        cards.add(new Card(id++, "Attack Speed Boost", "All your towers have Attack Speed Boosted", false));
        cards.add(new Card(id++, "Longer duration", "All your towers that do Damage over time (DOT) do it longer", false));
        cards.add(new Card(id++, "Damage Boost", "All your towers gets more Damage", false));
        //ARCHER
        cards.add(new Card(id++, "Pricing shoot", "Sniper will now shoot auto rifle", false));
        //CANNON
        cards.add(new Card(id++, "Big Canon", "Volcano will now exploding 2 times", false));
        cards.add(new Card(id++, "Fast Canon", "Volcano will now exploding 2 times", false));
        cards.add(new Card(id++, "Strong Canon", "Volcano will now exploding 2 times", false));
        //FROST_MAGE
        cards.add(new Card(id++, "Global Frost Mage", "Volcano will now exploding 2 times", false));
        cards.add(new Card(id++, "Frost Arch Mage", "Volcano will now exploding 2 times", false));
        //MINES_FACTORY
        cards.add(new Card(id++, "Long live the Mine", "Volcano will now exploding 2 times", false));
        cards.add(new Card(id++, "Two Mine for One", "Volcano will now exploding 2 times", false));
        cards.add(new Card(id++, "Big Mines", "Volcano will now exploding 2 times", false));
        //POISON_TOWER
        cards.add(new Card(id++, "Bigger Potions", "Sniper will now shoot auto rifle", false));
        cards.add(new Card(id++, "Stronger Potions", "Sniper will now shoot auto rifle", false));
        //BOOM_VOLCANO
        cards.add(new Card(id++, "Volcano Aftershock", "Volcano will now exploding 2 times", false));
        //CROSSBOW
        cards.add(new Card(id++, "Shoot Twice", "Sniper will now shoot auto rifle", false));
        cards.add(new Card(id++, "Shoot 8 Bolts", "Sniper will now shoot auto rifle", false));
        //MOUSE_FOLLOWS_TOWER
        cards.add(new Card(id++, "Faster Gun", "Volcano will now exploding 2 times", false));
        cards.add(new Card(id++, "Another One", "Volcano will now exploding 2 times", false));
       // cards.add(new Card(id++, "Laser Beem", "Volcano will now exploding 2 times", false));
        //SNIPER
        cards.add(new Card(id++, "Strong Sniper", "Sniper will now shoot bigger caliber", false));
        cards.add(new Card(id++, "Fast Sniper", "Sniper will now shoot auto rifle", false));
        //LASER_TOWER
        cards.add(new Card(id++, "Double Laser", "Sniper will now shoot auto rifle", false));
        cards.add(new Card(id++, "Global Laser", "Sniper will now shoot auto rifle", false));
    }

    public ArrayList<Card> getCards() {
        return cards;
    }
}
