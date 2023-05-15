package enemies;

import managers.EnemyMenager;

import static helpz.Constants.EnemyType.TENTACLE;
import managers.WaveManager;

public class Tentacle extends Enemy{
    public Tentacle(float x, float y, int ID, EnemyMenager em, WaveManager wm) {
        super(x, y, ID, TENTACLE, em, wm);

    }


}
