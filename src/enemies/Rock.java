package enemies;

import managers.EnemyManager;
import managers.WaveManager;

import static helpz.Constants.EnemyType.ROCK;

public class Rock extends Enemy{
    public Rock(float x, float y, int ID, EnemyManager em, WaveManager wm) {
        super(x, y, ID, ROCK, em, wm);

    }

}
