package towers;

import managers.TowerManager;

public class BoomTower extends Tower {
    public BoomTower(int x, int y, int id, int towerType, TowerManager towerManager) {
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
                this.addRange(60);
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
            default:
                return 0;
        }

    }

    public String getName(int upgrade) {
        switch (upgrade) {
            case 1:
                return "Attack speed +";
            case 2:
                return "Attack damage +5";
            case 3:
                return "Range +60";
        }
        return "";
    }
}
