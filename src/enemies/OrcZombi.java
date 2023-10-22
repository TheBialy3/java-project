package enemies;

import managers.EnemyManager;

import static helpz.Constants.EnemyType.*;

import managers.WaveManager;


public class OrcZombi extends Enemy{

    public OrcZombi(float x, float y, int ID, EnemyManager enemyManager, WaveManager waveManager) {
        super(x, y, ID, ORK_ZOMBIE, enemyManager, waveManager);
    }





}
