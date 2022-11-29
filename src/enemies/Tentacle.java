package enemies;

import managers.EnemyMenager;

import static helpz.Constants.EnemyType.TENTACLE;

public class Tentacle extends Enemy{
    public Tentacle(float x, float y, int ID, EnemyMenager em) {
        super(x, y, ID, TENTACLE,em);

    }


}
