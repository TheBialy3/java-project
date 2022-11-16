package enemies;

import static helpz.Constants.EnemyType.BALL;


public class Ball extends Enemy{
    public Ball(float x, float y, int ID) {
        super(x, y, ID, BALL);
        health=10;
    }


}
