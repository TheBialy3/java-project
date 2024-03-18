package towers;

import ally.Ally;
import ally.Knight;
import managers.TowerManager;

import java.util.ArrayList;

import static helpz.Constants.AllyType.*;

public class Barracks extends Tower {
    ArrayList<Ally> allys = new ArrayList<>();

    public Barracks(int x, int y, int id, int towerType, TowerManager towerManager, int[][] road) {
        super(x, y, id, towerType, towerManager, road);
    }

    public void upgrade(int upgrade) {
        switch (upgrade) {
            case 1:
                allys.add(new Knight(this.getX() * 64, this.getY() * 64, KNIGHT, 1));
                break;
            case 2:
                allys.add(new Knight(this.getX() * 64, this.getY() * 64, MAGE, 1));
                break;
            case 3:
                allys.add(new Knight(this.getX() * 64, this.getY() * 64, HUNTER, 1));
                break;
        }
        return;
    }

    public void cooldownOnAlly() {

    }

    public int getCost(int upgrade) {
        switch (upgrade) {
            case 1:
                return 400;
            case 2:
                return 350;
            case 3:
                return 400;
        }
        return 0;
    }

    public String getName(int upgrade) {
        switch (upgrade) {
            case 1:
                return "Knight";
            case 2:
                return "Mage";
            case 3:
                return "Hunter";
        }
        return "";
    }
}
