package enemies;

import managers.EnemyMenager;

import static helpz.Constants.EnemyType.ANIMATED_ORK;
import static helpz.Constants.EnemyType.ORC;
import managers.WaveManager;


public class Ball extends Enemy{
    public Ball(float x, float y, int ID, EnemyMenager enemyMenager, WaveManager waveManager) {
        super(x, y, ID, ANIMATED_ORK, enemyMenager, waveManager);
    }




}
