package enemies;

import managers.EnemyManager;
import static helpz.Constants.EnemyType.*;
import managers.WaveManager;

public class Orc extends Enemy{
    public Orc(float x, float y, int ID, EnemyManager em, WaveManager wm) {
        super(x, y, ID, ORC, em, wm);

    }


}
