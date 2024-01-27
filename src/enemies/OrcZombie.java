package enemies;

import managers.EnemyManager;

import static helpz.Constants.EnemyType.*;

import managers.TowerManager;
import managers.WaveManager;


public class OrcZombie extends Enemy{

    public OrcZombie(float x, float y, int ID, EnemyManager em, WaveManager wm, TowerManager tm) {
        super(x, y, ID, ORK_ZOMBIE, em, wm,tm);
    }


    public void powerUse(){

    }


}
