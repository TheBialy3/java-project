package enemies;

import managers.EnemyMenager;

import static helpz.Constants.EnemyType.*;

public class Orc extends Enemy{
    public Orc(float x, float y, int ID, EnemyMenager em) {
        super(x, y, ID, ORC,em);

    }


}
