package towers;


import managers.TowerManager;

public class PoisonTower extends Tower {

    public PoisonTower(int x, int y, int id, int towerType, TowerManager towerManager, int[][] road) {
        super(x, y, id, towerType, towerManager, road);
    }

    public void upgrade(int upgrade) {
        switch (upgrade) {
            case 1:
                this.addDmg(1);
                return;
            case 2:
                this.addDuration(50);
                return;
            case 3:
                this.addRange(100);
                return;
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
                return "Doubl damage";
            case 2:
                return "Poison duration";
            case 3:
                return "Range +100";
        }
        return "";
    }

}

