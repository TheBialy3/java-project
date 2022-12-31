package towers;

import managers.TowerManager;
import objects.PathPoint;

import java.util.ArrayList;

public class MineFactory extends Tower {



    public MineFactory(int x, int y, int id, int towerType , int[][] road) {
        super(x, y, id, towerType,road);

    }

//    public boolean isRoadInRange(Tower t, PathPoint p) {
//        int range = helpz.Utilz.GetHypoDistance(t.getX(), t.getY(), p.getxCord(), p.getyCord());
//        return range < t.getRange();
//    }


}
