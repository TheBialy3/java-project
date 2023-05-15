package enemies;


import managers.EnemyMenager;
import managers.WaveManager;

import static helpz.Constants.EnemyType.*;

public class Slime extends Enemy {
    public Slime(float x, float y, int ID, EnemyMenager em, WaveManager wm) {
        super(x, y, ID, SLIME, em, wm);

    }


}
