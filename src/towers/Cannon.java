package towers;

import helpz.Constants;

import static helpz.Constants.TowerType.CANNON;

public class Cannon extends Tower {
    public Cannon(int x, int y, int id, int towerType, int[][] road) {
        super(x, y, id, towerType, road);
    }

    public void upgrate1() {
        this.setCooldown(90);
    }

    public void upgrate2() {
        this.addDmg(10);
    }

    public void upgrate3() {
        //   this.addDmg(10);
    }

}


