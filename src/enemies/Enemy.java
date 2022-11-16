package enemies;

import helpz.Constants;

import java.awt.*;

import static helpz.Constants.Direction.*;

public abstract class Enemy {

    protected float x, y;
    protected Rectangle bounds;
    protected int health;
    protected int maxHealth;
    protected int ID;
    protected int enemyType;
    protected int lastDir;
    protected boolean alive = true;


    public Enemy(float x, float y, int ID, int enemyType) {
        this.x = x;
        this.y = y;
        this.ID = ID;
        this.enemyType = enemyType;
        bounds = new Rectangle((int) x, (int) y, 64, 64);
        lastDir = -1;
        setStartHealth();
    }

    public void move(float speed, int dir) {
        lastDir = dir;
        switch (dir) {
            case LEFT:
                this.x -= speed;
                break;
            case UP:
                this.y -= speed;
                break;
            case RIGHT:
                this.x += speed;
                break;
            case DOWN:
                this.y += speed;
                break;
        }
    }

    public float getHealthBar() {
        return (float) health / maxHealth;
    }

    protected void setStartHealth() {
        health = Constants.EnemyType.GetStartHealth(enemyType);
        maxHealth = health;
    }

    public void hurt(int dmg) {
        this.health -= dmg;
        if (health <= 0) {
            alive = false;
        }
    }

    ;

    public void setPos(int x, int y) {
        //posfix
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public int getHealth() {
        return health;
    }

    public int getID() {
        return ID;
    }

    public int getEnemyType() {
        return enemyType;
    }

    public int getLastDir() {
        return lastDir;
    }

    public boolean isAlive() {
        return alive;
    }
}
