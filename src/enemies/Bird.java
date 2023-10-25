package enemies;

import managers.EnemyManager;
import managers.WaveManager;

import static helpz.Constants.EnemyType.*;

public class Bird extends Enemy{
    public Bird(float x, float y, int ID, EnemyManager em, WaveManager wm) {
        super(x, y, ID, BIRD, em, wm);

    }

}
