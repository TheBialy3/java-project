package enemies;

import managers.EnemyMenager;

import static helpz.Constants.EnemyType.SLIME;


public class Ball extends Enemy{
    public Ball(float x, float y, int ID, EnemyMenager em) {
        super(x, y, ID, SLIME,em);
        health=10;
    }


}
