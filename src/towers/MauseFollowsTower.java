package towers;

import managers.TowerManager;

public class MauseFollowsTower extends Tower {
    public MauseFollowsTower(int x, int y, int id, int towerType , TowerManager towerManager, int[][] road) {
        super(x, y, id, towerType,  towerManager,road);

    }

    public void upgrade(int upgrade) {
        switch (upgrade) {
            case 1:
                this.reduceCoolDown(10);
                return;
            case 2:
                this.addDmg(5);
                return;
            case 3:
                this.reduceCoolDown(5);
                return;
            default:
                return ;
        }

    }

    public int getCost(int upgrade) {
        switch (upgrade) {
            case 1:
                return 200;
            case 2:
                return 150;
            case 3:
                return 100;
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
                return "Attack speed +";
        }
        return "";
    }
}
