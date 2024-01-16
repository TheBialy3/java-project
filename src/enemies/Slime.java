package enemies;


import managers.EnemyManager;
import managers.TowerManager;
import managers.WaveManager;

import static helpz.Constants.EnemyType.*;

public class Slime extends Enemy {
    public Slime(float x, float y, int ID, EnemyManager em, WaveManager wm, TowerManager tm) {
        super(x, y, ID, SLIME, em, wm,tm);

    }


}
