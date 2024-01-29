package enemies;

import managers.EnemyManager;
import managers.TowerManager;
import managers.WaveManager;

import static helpz.Constants.EnemyType.ROCK;

public class Rock extends Enemy{
    public Rock(float x, float y, int ID, EnemyManager em, WaveManager wm, TowerManager tm) {
        super(x, y, ID, ROCK, em, wm,tm);

    }
    public void heal(int heal) {
        this.healThis(heal);
    }

}
