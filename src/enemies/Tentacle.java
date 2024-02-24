package enemies;

import managers.EnemyManager;

import static helpz.Constants.EnemyType.*;

import managers.TowerManager;
import managers.WaveManager;

public class Tentacle extends Enemy{
    public Tentacle(float x, float y, int ID, EnemyManager em, WaveManager wm, TowerManager tm) {
        super(x, y, ID, TENTACLE, em, wm,tm);
    }


    public void heal(int heal) {
        this.healThis(heal);
    }

}
