package enemies;

import managers.EnemyManager;
import managers.WaveManager;

import static helpz.Constants.EnemyType.*;

public class CamelJunior extends Enemy{

    public CamelJunior(float x, float y, int ID, EnemyManager enemyManager, WaveManager waveManager) {
        super(x, y, ID, CAMEL_JUNIOR, enemyManager, waveManager);
    }


}
