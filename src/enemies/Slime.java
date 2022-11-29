package enemies;


import managers.EnemyMenager;

import static helpz.Constants.EnemyType.ANIMATED_ORK;

public class Slime extends Enemy{
    public Slime(float x, float y, int ID, EnemyMenager em) {
        super(x, y, ID, ANIMATED_ORK,em);

    }


}
