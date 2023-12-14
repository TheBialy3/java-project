package enemies;

import managers.EnemyManager;
import managers.WaveManager;

import static helpz.Constants.EnemyType.CREEPY_CAT;

public class CreepyCat  extends Enemy{

    public CreepyCat(float x, float y, int ID, EnemyManager enemyManager, WaveManager waveManager) {
        super(x, y, ID, CREEPY_CAT, enemyManager, waveManager);
    }

}