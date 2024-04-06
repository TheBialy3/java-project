package towers;

import managers.TowerManager;

public class Sniper extends Tower {
    public Sniper(int x, int y, int id, int towerType, TowerManager towerManager) {
        super(x, y, id, towerType, towerManager);

    }

    public void upgrade(int upgrade) {
        switch (upgrade) {
            case 1:
                this.reduceCoolDown(10);
                break;
            case 2:
                this.addDmg(5);
                break;
            case 3:
                this.addDmg(10);
                break;
            default:
                break;
        }

    }

    public int getCost(int upgrade) {
        switch (upgrade) {
            case 1:
                return 100;
            case 2:
                return 150;
            case 3:
                return 200;
        }
        return 0;
    }

    public String getName(int upgrade) {
        switch (upgrade) {
            case 1:
                return "Attack speed +";
            case 2:
                return "Attack damage +5";
            case 3:
                return "Attack damage +10";
        }
        return "";
    }
}
