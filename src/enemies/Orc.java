package enemies;

import managers.EnemyMenager;
import static helpz.Constants.EnemyType.*;
import managers.WaveManager;

public class Orc extends Enemy{
    public Orc(float x, float y, int ID, EnemyMenager em, WaveManager wm) {
        super(x, y, ID, ORC, em, wm);

    }


}
