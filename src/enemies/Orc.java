package enemies;

import managers.EnemyManager;
import static helpz.Constants.EnemyType.*;

import managers.TowerManager;
import managers.WaveManager;
import objects.PathPoint;

import java.util.ArrayList;

public class Orc extends Enemy{
    public Orc(float x, float y, int ID, int enemyType, ArrayList<PathPoint> wayForEnemies, EnemyManager enemyManager, WaveManager waveManager, TowerManager towerManager) {
        super(x, y, ID, enemyType, wayForEnemies, enemyManager, waveManager, towerManager);
    }

    //healOnUpdate * 60
    public void heal(int heal) {
        this.healThis(heal);
    }
}
