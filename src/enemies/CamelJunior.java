package enemies;

import managers.EnemyMenager;
import managers.WaveManager;

import static helpz.Constants.EnemyType.*;

public class CamelJunior extends Enemy{

    public CamelJunior(float x, float y, int ID, EnemyMenager enemyMenager, WaveManager waveManager) {
        super(x, y, ID, CAMEL_JUNIOR, enemyMenager, waveManager);
    }





}
