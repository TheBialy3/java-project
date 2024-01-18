package enemies;

import managers.EnemyManager;
import managers.TowerManager;
import managers.WaveManager;

import static helpz.Constants.EnemyType.BIRD_SKELETON;

public class BirdSkeleton extends Enemy{
    public BirdSkeleton(float x, float y, int ID, EnemyManager em, WaveManager wm, TowerManager tm) {
        super(x, y, ID, BIRD_SKELETON, em, wm,tm);

    }

}
