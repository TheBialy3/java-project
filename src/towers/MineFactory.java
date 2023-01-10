package towers;

import managers.TowerManager;
import objects.PathPoint;

import java.util.ArrayList;

public class MineFactory extends Tower {



    public MineFactory(int x, int y, int id, int towerType , int[][] road) {
        super(x, y, id, towerType,road);

    }

    public void upgrate1() {
        this.setCooldown(50);
    }

    public void upgrate2() {
        this.addDmg(10);
    }

    public void upgrate3() {
        //   this.addDmg(10);
    }
}
