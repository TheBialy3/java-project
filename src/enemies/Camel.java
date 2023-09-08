package enemies;

import managers.EnemyMenager;
import managers.WaveManager;

import static helpz.Constants.EnemyType.*;

public class Camel extends Enemy{

    public Camel(float x, float y, int ID, EnemyMenager enemyMenager, WaveManager waveManager) {
        super(x, y, ID, CAMEL, enemyMenager, waveManager);
    }



}