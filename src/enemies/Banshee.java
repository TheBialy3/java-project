package enemies;

import managers.EnemyManager;
import managers.TowerManager;
import managers.WaveManager;

import static helpz.Constants.EnemyType.BANSHEE;


public class Banshee extends Enemy{
    public Banshee(float x, float y, int ID, EnemyManager em, WaveManager wm, TowerManager tm) {
        super(x, y, ID, BANSHEE, em, wm,tm);

    }
    public void powerUse(){

    }
}
