package towers;

import managers.TowerManager;

public class LaserTower extends Tower {
    public LaserTower(int x, int y, int id, int towerType , TowerManager towerManager, int[][] road) {
        super(x, y, id, towerType,towerManager,road);

    }

    public void upgrade(int upgrade) {
        switch (upgrade) {
            case 1:
                this.addRange(100);
                break;
            case 2:
                this.addDmg(1);
                break;
            case 3:
                this.addDmg(2);
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
                return "Range +100";
            case 2:
                return "Attack damage +1";
            case 3:
                return "Attack damage +1";
        }
        return "";
    }
}
