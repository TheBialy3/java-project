package towers;

import managers.TowerManager;

public class Bank extends Tower{
    public Bank(int x, int y, int id, int towerType, TowerManager towerManager) {
        super(x, y, id, towerType, towerManager);
    }
    public void upgrade(int upgrade) {
        switch (upgrade) {
            case 1:
                break;
            case 2:
                break;
            case 3:
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
                return "Invest";
            case 2:
                return "Invest";
            case 3:
                return "Invest";
        }
        return "";
    }

}
