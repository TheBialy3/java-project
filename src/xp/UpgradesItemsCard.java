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
        cards.add(new Card(id++, "More Damage", "All your towers do more Damage", true, -1));
        cards.add(new Card(id++, "More Speed", "All your towers gets more Attack Speed", true, -1));
        cards.add(new Card(id++, "More Range", "All your towers gets more Range", true, -1));
        //for all
        cards.add(new Card(id++, "Attack Speed Boost", "All your towers have Attack Speed Boosted", false, -1));
        cards.add(new Card(id++, "Longer duration", "All your towers that do Damage over time (DOT) do it longer", false, -1));
        cards.add(new Card(id++, "Damage Boost", "All your towers do more Damage", false, -1));
        //ENEMY
        cards.add(new Card(id++, "Greed Is Good", "Killing enemies grands more Gold, but they have More HP", false, -2));
        cards.add(new Card(id++, "Greed curse", "Killing enemies grands more Gold, but they have More HP", false, -2));
        cards.add(new Card(id++, "True POWER", "All towers deal TRUE damage, but they have More HP", false, -2));
        cards.add(new Card(id++, "All target", "All towers can target All type of enemy, but they have More HP", false, -2));
        //ARCHER
        cards.add(new Card(id++, "Pricing shoot", "Archer will now pierce the first target", false, 0));
        cards.add(new Card(id++, "Faster Bow", "Archer will now shoot faster", false, 0));
        cards.add(new Card(id++, "Stronger Bow", "Archer will now do more damage", false, 0));
        //CANNON
        cards.add(new Card(id++, "Big Canon", "Volcano will now exploding 2 times", false, 1));
        cards.add(new Card(id++, "Fast Canon", "Volcano will now exploding 2 times", false, 1));
        cards.add(new Card(id++, "Strong Canon", "Volcano will now exploding 2 times", false, 1));
        //FROST_MAGE
        cards.add(new Card(id++, "Global Frost Mage", "Volcano will now exploding 2 times", false, 2));
        cards.add(new Card(id++, "Frost Damage", "Volcano will now exploding 2 times", false, 2));
        cards.add(new Card(id++, "Frost Arch Mage", "Volcano will now exploding 2 times", false, 2));
        //MINES_FACTORY
        cards.add(new Card(id++, "Long live the Mine", "Volcano will now exploding 2 times", false, 3));
        cards.add(new Card(id++, "Two Mine for One", "Volcano will now exploding 2 times", false, 3));
        cards.add(new Card(id++, "Big Mines", "Volcano will now exploding 2 times", false, 3));
        //POISON_TOWER
        cards.add(new Card(id++, "Bigger Potions", "Sniper will now shoot auto rifle", false, 4));
        cards.add(new Card(id++, "Stronger Potions", "Sniper will now shoot auto rifle", false, 4));
        //BOOM_VOLCANO
        cards.add(new Card(id++, "Volcano Aftershock", "Volcano will now exploding 2 times", false, 5));
        cards.add(new Card(id++, "Faster Volcano Eruptions", "Volcano will now exploding 2 times", false, 5));
        cards.add(new Card(id++, "Stronger Volcano Eruptions", "Volcano will now exploding 2 times", false, 5));
        //CROSSBOW
        cards.add(new Card(id++, "Cross Damage", "Sniper will now shoot auto rifle", false, 6));
        cards.add(new Card(id++, "Fast Cross", "Sniper will now shoot auto rifle", false, 6));
        cards.add(new Card(id++, "Shoot 8 Bolts", "Sniper will now shoot auto rifle", false, 6));
        //MOUSE_FOLLOWS_TOWER
        cards.add(new Card(id++, "Faster Gun", "Volcano will now exploding 2 times", false, 7));
        cards.add(new Card(id++, "Big Gun", "Volcano will now exploding 2 times", false, 7));
       // cards.add(new Card(id++, "Laser Beem", "Volcano will now exploding 2 times", false, 7));
        //SNIPER
        cards.add(new Card(id++, "Strong Sniper", "Sniper will now shoot bigger caliber", false, 8));
        cards.add(new Card(id++, "Fast Sniper", "Sniper will now shoot auto rifle", false, 8));
        //LASER_TOWER
        cards.add(new Card(id++, "Double Laser", "Sniper will now shoot auto rifle", false, 9));
        cards.add(new Card(id++, "Global Laser", "Sniper will now shoot auto rifle", false, 9));
    }

    public ArrayList<Card> getCards() {
        return cards;
    }
}
