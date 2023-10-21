package xp;

import objects.Card;
import scenes.Upgrade;

import java.util.ArrayList;

public class UpgradesItemsCard {
    private ArrayList<Card> cards = new ArrayList<>();
    private Upgrade upgrade;

    public UpgradesItemsCard(Upgrade upgrade) {
        this.upgrade = upgrade;
        createUpgrades();
    }

    public void createUpgrades() {
        int id = 0;
        //for all
        cards.add(new Card(id++, "More Damage", "All your towers do more Damage", true, -1));
        cards.add(new Card(id++, "More Speed", "All your towers gets more Attack Speed", true, -1));
        cards.add(new Card(id++, "More Range", "All your towers gets more Range", true, -1));
        cards.add(new Card(id++, "Towers Upgrade", "All your towers gets all 3 upgrades", true, -1));
        cards.add(new Card(id++, "Attack Speed Boost", "All your towers have Attack Speed Boosted", false, -1));
        cards.add(new Card(id++, "Longer duration", "All your towers that do Damage over time (DOT) do it longer", false, -1));
        cards.add(new Card(id++, "Damage Boost", "All your towers do more Damage", false, -1));
        //ENEMY
        cards.add(new Card(id++, "Greed Is Good", "Killing enemies grands more Gold, but they have More HP", false, -2));
        cards.add(new Card(id++, "Greed curse", "Killing enemies grands more Gold, but they are Faster", false, -2));
        cards.add(new Card(id++, "false POWER", "All towers deal false damage, but they have More HP", false, -2));
        cards.add(new Card(id++, "All target", "All towers can target All type of enemy, but they have More HP", false, -2));
        //ARCHER
        cards.add(new Card(id++, "Pricing shoot", "Archer will now pierce the first target", false, 0));
        cards.add(new Card(id++, "Faster Bow", "Archer will now shoot faster", false, 0));
        cards.add(new Card(id++, "Stronger Bow", "Archer will now do more damage", false, 0));
        //CANNON
        cards.add(new Card(id++, "Big Canon", "CANNON will now exploding in bigger radius", false, 1));
        cards.add(new Card(id++, "Fast Canon", "CANNON will now shoot faster", false, 1));
        cards.add(new Card(id++, "Strong Canon", "CANNON will now exploding 2 times", false, 1));
        //FROST_MAGE
        cards.add(new Card(id++, "Global Frost Mage", "Frost Mage will now slow all enemies, but weaker", false, 2));
        cards.add(new Card(id++, "Frost Damage", "Frost Mage will now deal a little magic damage", false, 2));
        cards.add(new Card(id++, "Frost Arch Mage", "Frost Mage will now have stronger slow", false, 2));
        //MINES_FACTORY
        cards.add(new Card(id++, "Long live the Mine", "Mine will now stay for 2 waves", false, 3));
        cards.add(new Card(id++, "Two for One", "Mine Factory will now sets 2 mines at the time", false, 3));
        cards.add(new Card(id++, "Big Mines", "Mine Factory will now sets mines that du more damage", false, 3));
        //POISON_TOWER
        cards.add(new Card(id++, "Bigger Potions", "Poison Tower will now splash in bigger radius", false, 4));
        cards.add(new Card(id++, "Stronger Potions", "Poison Tower will now do more damage", false, 4));
        cards.add(new Card(id++, "Poison Pool", "Poison Tower will leave a pool in place where it splash", false, 4));
        //BOOM_VOLCANO
        cards.add(new Card(id++, "Volcano Aftershock", "Volcano will now exploding 2 times", false, 5));
        cards.add(new Card(id++, "Faster Volcano Eruptions", "Volcano will now exploding faster", false, 5));
        cards.add(new Card(id++, "Stronger Volcano Eruptions", "Volcano will now do more damage", false, 5));
        //CROSSBOW
        cards.add(new Card(id++, "Cross Damage", "CrossBow will now do more damage", false, 6));
        cards.add(new Card(id++, "Fast Cross", "CrossBow will now shoot faster", false, 6));
        cards.add(new Card(id++, "Shoot 8 Bolts", "CrossBow will now shoot diagonally as well", false, 6));
        //MOUSE_FOLLOWS_TOWER
        cards.add(new Card(id++, "Faster Mouse Follower", "Mouse Follower will now shoot faster", false, 7));
        cards.add(new Card(id++, "Big Gun", "Mouse Follower will now do more damage", false, 7));
        cards.add(new Card(id++, "Pricing Power", "Laser eye will now pierce the first target", false, 7));
        //SNIPER
        cards.add(new Card(id++, "Strong Sniper", "Sniper will now shoot bigger caliber", false, 8));
        cards.add(new Card(id++, "Fast Sniper", "Sniper will now shoot auto rifle", false, 8));
        cards.add(new Card(id++, "Double Sniper", "Sniper will now shoot 2 targets at the time", false, 8));
        //LASER_TOWER
        cards.add(new Card(id++, "Double Laser", "Laser eye will now shoot 2 targets", false, 9));
        cards.add(new Card(id++, "Global Laser", "Laser eye will now have bigger radius", false, 9));
        cards.add(new Card(id++, "Hot Laser", "Laser eye will now do more damage", false, 9));
        System.out.println(id);
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

}
