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
        //ALL id0↓
        cards.add(new Card(id++, "More Damage", "All your towers do more Damage", true, -1));
        cards.add(new Card(id++, "More Speed", "All your towers gets more Attack Speed", true, -1));
        cards.add(new Card(id++, "More Range", "All your towers gets more Range", true, -1));
        cards.add(new Card(id++, "Towers Upgrade", "All your towers gets all 3 upgrades", true, -1));
        cards.add(new Card(id++, "Attack Speed Boost", "All your towers have Attack Speed Boosted", true, -1));
        cards.add(new Card(id++, "Damage Boost", "All your towers do more Damage", true, -1));
        cards.add(new Card(id++, "Longer duration", "All your towers that do Damage over time (DOT) do it longer", true, -1));
        //ENEMY id7↓
        cards.add(new Card(id++, "Greed Is Good", "Killing enemies grands more Gold, but they have More HP", true, -2));
        cards.add(new Card(id++, "Greed curse", "Killing enemies grands more Gold, but they are Faster", true, -2));
        cards.add(new Card(id++, "Greed Fast Passive", "Passive income grands 2 times more Gold, but enemies are Faster", true, -2));
        cards.add(new Card(id++, "Greed BIG Passive", "Passive income grands 2 times more more Gold, but enemies have More HP", true, -2));
        cards.add(new Card(id++, "True POWER", "All towers deal true damage, but enemies have More HP", true, -2));
        cards.add(new Card(id++, "All target", "All towers can target All type of enemy, but they have More HP", true, -2));
        //ARCHER id13↓
        cards.add(new Card(id++, "Pricing shoot", "Archer will now pierce the first target", true, 0));
        cards.add(new Card(id++, "Faster Bow", "Archer will now shoot faster", true, 0));
        cards.add(new Card(id++, "Stronger Bow", "Archer will now do more damage", true, 0));
        //CANNON id16↓
        cards.add(new Card(id++, "Big Canon", "CANNON will now exploding in bigger radius", true, 1));
        cards.add(new Card(id++, "Fast Canon", "CANNON will now shoot faster", true, 1));
        cards.add(new Card(id++, "Strong Canon", "CANNON will now do more damage", true, 1));
        //FROST_MAGE id19↓
        cards.add(new Card(id++, "Global Frost Mage", "Frost Mage will now slow all enemies, but weaker", true, 2));
        cards.add(new Card(id++, "Frost Damage", "Frost Mage will now deal a little magic damage", true, 2));
        cards.add(new Card(id++, "Frost Arch Mage", "Frost Mage will now have stronger slow", true, 2));
        //MINES_FACTORY id22↓
        cards.add(new Card(id++, "Long live the Mine", "Mines will now stay for 2 waves", true, 3));
        cards.add(new Card(id++, "Two for One", "Mines Factory will now sets 2 times more mines at the time", true, 3));
        cards.add(new Card(id++, "Big Mines", "Mines Factory will now sets mines that du more damage", true, 3));
        //POISON_TOWER id25↓
        cards.add(new Card(id++, "Bigger Potions", "Poison Tower will now splash in bigger radius", true, 4));
        cards.add(new Card(id++, "Stronger Potions", "Poison Tower will now do more damage", true, 4));
        cards.add(new Card(id++, "Longer Poison Effect", "Poison Tower will now least longer", true, 4));
        //BOOM_VOLCANO id28↓
        cards.add(new Card(id++, "Volcano Aftershock", "Volcano will now exploding 2 times", true, 5));
        cards.add(new Card(id++, "Faster Volcano Eruptions", "Volcano will now exploding faster", true, 5));
        cards.add(new Card(id++, "Stronger Volcano Eruptions", "Volcano will now do more damage", true, 5));
        //CROSSBOW id31↓
        cards.add(new Card(id++, "Cross Damage", "CrossBow will now do more damage", true, 6));
        cards.add(new Card(id++, "Fast Cross", "CrossBow will now shoot faster", true, 6));
        cards.add(new Card(id++, "Shoot 8 Bolts", "CrossBow will now shoot diagonally as well", true, 6));
        //MOUSE_FOLLOWS_TOWER id34↓
        cards.add(new Card(id++, "Faster Mouse Follower", "Mouse Follower will now shoot faster", true, 7));
        cards.add(new Card(id++, "Big Gun", "Mouse Follower will now do more damage", true, 7));
        cards.add(new Card(id++, "Pricing Power", "Laser eye will now pierce the first target", true, 7));
        //SNIPER id37↓
        cards.add(new Card(id++, "Strong Sniper", "Sniper will now shoot bigger caliber", true, 8));
        cards.add(new Card(id++, "Fast Sniper", "Sniper will now shoot auto rifle", true, 8));
        cards.add(new Card(id++, "Double Rifle", "Sniper will now shoot 2 targets at the time", true, 8));
        //LASER_TOWER id40↓
        cards.add(new Card(id++, "Double Laser", "Laser eye will now shoot 2 targets", true, 9));
        cards.add(new Card(id++, "Global Laser", "Laser eye will now have bigger radius", true, 9));
        cards.add(new Card(id++, "Hot Laser", "Laser eye will now do more damage", true, 9));
        //SCARECROW   id 43↓
        cards.add(new Card(id++, "Bigger crows", "SCARECROW will now do more damage", true, 10));
        cards.add(new Card(id++, "Ground crows", "SCARECROW will now damage ground enemy as well", true, 10));
        cards.add(new Card(id++, "Faster crows", "SCARECROW will now have more attack speed", true, 10));

        //BANK   id 46↓
        cards.add(new Card(id++, "High percent", "Bank will gain bigger interest", true, 11));
        cards.add(new Card(id++, "Fast action", "Bank will gain interest faster", true, 11));
        cards.add(new Card(id++, "Smaller Banks", "Bank will now have lower cost", true, 11));

        System.out.println(id + "unlocked cards");
        //unlocked
        //locked 49↓
        //ALL
        cards.add(new Card(id++, "Fast Projectile", "Projectile travel faster", false, -1));
        cards.add(new Card(id++, "Merchant", "There will be e chance to merchant appearance", false, -1));
        cards.add(new Card(id++, "Tenacity", "Reduce all debuffs from your towers by 50%", false, -1));
        cards.add(new Card(id++, "Unbreakable", "Reduce all debuffs from your towers by 100%", false, -1));


        //ENEMY
        cards.add(new Card(id++, "Big enemy", "Enemies are bigger so it is easy to shoot", false, -2));
        cards.add(new Card(id++, "Weak and Fast", "Enemies have less HP but cant be slowed", false, -2));
        cards.add(new Card(id++, "Weak and Fast", "Enemies have less HP but cant be slowed", false, -2));

        //ARCHER
        cards.add(new Card(id++, "Flame Archer", "Archer will now set enemies on fire", false, 0));
        cards.add(new Card(id++, "Arrow split", "Arrows will split after hitting enemy", false, 0));
        cards.add(new Card(id++, "Quiver", "Archer will now stash arrows in quiver and spray them faster if enemy is near", false, 0));

        //CANNON
        cards.add(new Card(id++, "Bombs Everywhere", "Bombs will now explode in neighboring squares", false, 1));
        cards.add(new Card(id++, "Slow Bombs", "Bombs will now Slow enemy", false, 1));
        cards.add(new Card(id++, "Molotov cocktail", "Bombs will now set enemies on fire", false, 1));

        //FROST_MAGE
        cards.add(new Card(id++, "Frost Nova", "Frost Mage will now stun enemies in low range", false, 2));

        //MINES_FACTORY
        cards.add(new Card(id++, "Bomb Mines", "Mines will now explode", false, 3));
        cards.add(new Card(id++, "Triple Mines", "Mines will now be triggered tree timer", false, 3));
        cards.add(new Card(id++, "Minefield", "Set mines everywhere", false, 3));

        //POISON_TOWER
        cards.add(new Card(id++, "Poison Pool", "Poison Tower will set a pool of poison in near place", false, 4));
        cards.add(new Card(id++, "Stinky Poison", "Poison Tower will spawn flies that will attack enemy", false, 4));

        //BOOM_VOLCANO
        cards.add(new Card(id++, "Volcano Power", "Volcano will now bust random stats of near towers", false, 5));
        cards.add(new Card(id++, "", "", false, 5));


        //CROSSBOW
        cards.add(new Card(id++, "Pricing Cross", "CrossBow will now Price first enemy", false, 6));

        //MOUSE_FOLLOWS_TOWER
        cards.add(new Card(id++, "Flame Archer", "Archer will now set enemies on fire", false, 7));


        //SNIPER


        //LASER_TOWER

        System.out.println(id + "locked cards");

    }

    public ArrayList<Card> getCards() {
        return cards;
    }

}
