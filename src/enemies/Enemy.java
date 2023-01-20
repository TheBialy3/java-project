package enemies;

import helpz.Constants;
import managers.EnemyMenager;
import managers.WaveManager;


import java.awt.*;

import static helpz.Constants.Direction.*;
import static helpz.Constants.EnemyType.*;

public abstract class Enemy {

    private int tick=0;

    protected EnemyMenager enemyMenager;
    protected WaveManager waveManager;
    protected float x, y;
    protected Rectangle bounds;
    protected int health, slowTickLimit = 3, slowTick = slowTickLimit;
    protected int maxHealth;
    protected int ID;
    protected int enemyType;
    protected int lastDir;
    protected boolean alive = true;
    protected float slowPower=1f;

    public Enemy(float x, float y, int ID, int enemyType,EnemyMenager enemyMenager, WaveManager waveManager) {
        this.x = x;
        this.y = y;
        this.ID = ID;
        this.enemyType = enemyType;
        this.enemyMenager=enemyMenager;
        this.waveManager=waveManager;
        bounds = new Rectangle((int) x, (int) y, 64, 64);
        lastDir = -1;
        setStartHealth();
    }

    public void tickUp(){
        tick++;
    }

    public void tickZero(){
        tick=0;
    }

    public int getTick(){
        return tick;
    }

    public Enemy(float x, float y, int ID, int enemyType,EnemyMenager enemyMenager, WaveManager waveManager,int lastDir) {
        this.x = x;
        this.y = y;
        this.ID = ID;
        this.enemyType = enemyType;
        this.enemyMenager=enemyMenager;
        this.waveManager=waveManager;
        bounds = new Rectangle((int) x, (int) y, 64, 64);
        this.lastDir = lastDir;
        setStartHealth();
    }

    public void move(float speed, int dir) {
        lastDir = dir;
        if (slowTick < slowTickLimit) {
            slowTick++;
            speed *= slowPower;
        }
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
        updateHitbox();
    }

    private void updateHitbox() {
        bounds.x = (int) x;
        bounds.y = (int) y;
    }

    public float getHealthBar() {
        return (float) health / maxHealth;
    }

    protected void setStartHealth() {
        int a=waveManager.getWaveIndex();
        health = Constants.EnemyType.getStartHealth(enemyType)*(5+a)/10;
        maxHealth = health;
        System.out.println(health);
    }

    public void hurt(int dmg) {
        this.health -= dmg;
        if (health <= 0) {
            if(enemyType==ANIMATED_ORK){
                enemyMenager.addEnemy( ORC, (int)x,(int) y, lastDir);
            }
                alive = false;
                enemyMenager.rewardPlayer(enemyType);

        }
    }

    public void kill() {
        health=0;
        alive=false;
    }

    public void slow(float powerOfSlow) {
        slowTick = 0;
        slowPower=powerOfSlow;
    }

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

    public boolean isSlowd() {

        return  slowTick < slowTickLimit;
    }

    public void setLastDir(int newDir) {
        this.lastDir = newDir;
    }
}
