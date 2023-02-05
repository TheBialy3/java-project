package enemies;

import managers.EnemyMenager;

import static helpz.Constants.EnemyType.ORK_ZOMBI;

import managers.WaveManager;


public class OrcZombi extends Enemy{

    public OrcZombi(float x, float y, int ID, EnemyMenager enemyMenager, WaveManager waveManager) {
        super(x, y, ID, ORK_ZOMBI, enemyMenager, waveManager);
    }





}
