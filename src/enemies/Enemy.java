package enemies;

import helpz.Constants;
import main.Game;
import managers.EnemyMenager;
import managers.WaveManager;


import java.awt.*;

import static helpz.Constants.Direction.*;
import static helpz.Constants.EnemyType.*;

public abstract class Enemy {

    private int tick = 0;

    protected EnemyMenager enemyMenager;
    protected WaveManager waveManager;
    protected float x, y;
    protected Rectangle bounds;
    protected int dmg, duration = 0;
    protected int slowTickLimit = 3, slowTick = slowTickLimit;
    protected int maxHealth, health;
    protected int ID;
    protected int enemyType;
    protected int lastDir;
    protected boolean alive=false, poisoned = false;
    protected float slowPower = 1f;
    protected boolean revive;

    public Enemy(float x, float y, int ID, int enemyType, EnemyMenager enemyMenager, WaveManager waveManager) {
        this.x = x;
        this.y = y;
        this.ID = ID;
        this.enemyType = enemyType;
        this.enemyMenager = enemyMenager;
        this.waveManager = waveManager;
        bounds = new Rectangle((int) x, (int) y, 64, 64);
        lastDir = -1;
        setStartHealth();
        if (enemyType != CAMEL_JUNIOR) {
            alive = true;
        }
        setRevive();
    }

    private void setRevive() {
        if (this.enemyType == ORK_ZOMBI) {
            revive = true;
        }
    }

    public void tickUp() {
        tick++;
        if (tick < 100) {
            tick = 0;
        }
    }


    public int getTick() {
        return tick;
    }

    public void reuse(float x, float y) {
        this.x = x;
        this.y = y;
        this.alive = true;
        setStartHealth();
    }

    public void killed() {
        revive = false;
    }

    public void setPoisonOn(int dmg, int duration) {
        this.dmg = dmg;
        this.duration = duration;
        poisoned = true;
    }

    public void poisonDamage() {
        if (0 < duration) {
            duration--;
            if (0 == duration % 10) {
                hurt(dmg);
            }

        }
        if (duration == 0) {
            poisoned = false;
        }
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
        if (poisoned) {
            poisonDamage();
        }
    }

    private void updateHitbox() {
        bounds.x = (int) x;
        bounds.y = (int) y;
    }

    public float getHealthBar() {
        return (float) health / maxHealth;
    }

    protected void setStartHealth() {
        int waveIndex = waveManager.getWaveIndex();
        health = Constants.EnemyType.getStartHealth(enemyType) * (5 + waveIndex)/2;//(5 + waveIndex) / 10
        maxHealth = health;
        System.out.println(health);
    }

    public void hurt(int dmg) {
        this.health -= dmg;
        if (health <= 0) {
            alive = false;
            if (enemyType == ORK_ZOMBI) {
                if (this.revive) {
                    killed();
                    reuse(this.x, this.y);
                }
            } else if (enemyType == CAMEL) {
                enemyMenager.spawnJuniors(this.x, this.y, (this.enemyType + 1));
            }
            enemyMenager.rewardPlayer(enemyType);
            Game.addXp();
        }
    }


    public void kill() {
        health = 0;
        alive = false;
    }

    public boolean doesRevive() {
        return revive;
    }

    public void slow(float powerOfSlow) {
        slowTick = 0;
        slowPower = powerOfSlow;
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
        return slowTick < slowTickLimit;
    }

    public boolean isPoisoned() {
        return poisoned;
    }

    public void setLastDir(int newDir) {
        this.lastDir = newDir;
    }
}
