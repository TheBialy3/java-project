package enemies;


import managers.EnemyMenager;

import static helpz.Constants.EnemyType.*;

public class Slime extends Enemy{
    public Slime(float x, float y, int ID, EnemyMenager em) {
        super(x, y, ID, SLIME,em);

    }


}
