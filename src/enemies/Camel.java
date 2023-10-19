package enemies;

import managers.EnemyManager;
import managers.WaveManager;

import static helpz.Constants.EnemyType.*;

public class Camel extends Enemy{

    public Camel(float x, float y, int ID, EnemyManager enemyManager, WaveManager waveManager) {
        super(x, y, ID, CAMEL, enemyManager, waveManager);
    }



}