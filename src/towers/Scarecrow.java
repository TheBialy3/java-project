package towers;

import managers.TowerManager;

public class Scarecrow extends Tower {

    public Scarecrow(int x, int y, int id, int towerType, TowerManager towerManager ){
        super(x, y, id, towerType, towerManager);
    }

    public void upgrade(int upgrade) {
        switch (upgrade) {
            case 1:
                this.addDmg(5);
                break;
            case 2:
                this.addRange(100);
                break;
            case 3:
                this.addRange(100);
                break;
        }
        return;
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
                return "Add damage";
            case 2:
                return "Increase range";
            case 3:
                return "Increase range more";
        }
        return "";
    }

}
