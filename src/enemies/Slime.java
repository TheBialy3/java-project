package enemies;


import managers.EnemyManager;
import managers.WaveManager;

import static helpz.Constants.EnemyType.*;

public class Slime extends Enemy {
    public Slime(float x, float y, int ID, EnemyManager em, WaveManager wm) {
        super(x, y, ID, SLIME, em, wm);

    }


}
