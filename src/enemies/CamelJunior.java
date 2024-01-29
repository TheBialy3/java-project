package enemies;

import managers.EnemyManager;
import managers.TowerManager;
import managers.WaveManager;

import static helpz.Constants.EnemyType.*;

public class CamelJunior extends Enemy{

    public CamelJunior(float x, float y, int ID, EnemyManager em, WaveManager wm, TowerManager tm) {
        super(x, y, ID, CAMEL_JUNIOR, em, wm,tm);
    }
    public void heal(int heal) {
        this.healThis(heal);
    }

}
