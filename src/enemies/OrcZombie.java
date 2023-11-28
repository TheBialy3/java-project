package enemies;

import managers.EnemyManager;

import static helpz.Constants.EnemyType.*;

import managers.WaveManager;


public class OrcZombie extends Enemy{

    public OrcZombie(float x, float y, int ID, EnemyManager enemyManager, WaveManager waveManager) {
        super(x, y, ID, ORK_ZOMBIE, enemyManager, waveManager);
    }





}
