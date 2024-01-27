package enemies;

import managers.EnemyManager;
import managers.TowerManager;
import managers.WaveManager;

import static helpz.Constants.EnemyType.CREEPY_CAT;

public class CreepyCat  extends Enemy{

    public CreepyCat(float x, float y, int ID, EnemyManager em, WaveManager wm, TowerManager tm) {
        super(x, y, ID, CREEPY_CAT, em, wm,tm);
    }

    public void powerUse(){

    }

}