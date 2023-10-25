package enemies;

import managers.EnemyManager;
import managers.WaveManager;


import static helpz.Constants.EnemyType.GHOST;

public class Ghost extends Enemy{
    public Ghost(float x, float y, int ID, EnemyManager em, WaveManager wm) {
        super(x, y, ID, GHOST, em, wm);

    }

}
