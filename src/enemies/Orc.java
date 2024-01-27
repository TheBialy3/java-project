package enemies;

import managers.EnemyManager;
import static helpz.Constants.EnemyType.*;

import managers.TowerManager;
import managers.WaveManager;

public class Orc extends Enemy{
    public Orc(float x, float y, int ID, EnemyManager em, WaveManager wm, TowerManager tm) {
        super(x, y, ID, ORC, em, wm,tm);

    }

    //healOnUpdate * 60
    public void heal(int heal) {
        this.heal(heal);
    }
}
