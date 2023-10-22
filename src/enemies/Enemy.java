package enemies;

import helpz.Constants;
import main.Game;
import managers.EnemyManager;
import managers.WaveManager;


import java.awt.*;

import static helpz.Constants.Direction.*;
import static helpz.Constants.EnemyType.*;
import static helpz.Constants.TowerType.*;

public abstract class Enemy {

    private int tick = 0;

    protected EnemyManager enemyManager;
    protected WaveManager waveManager;
    protected float x, y;
    protected Rectangle bounds;
    protected int dmg, duration = 0;
    protected int slowTickLimit = 3, slowTick = slowTickLimit;
    protected int maxHealth, health, mr, armor;
    protected int ID;
    protected int enemyType, damageType;
    protected int lastDir;
    protected boolean alive=false, poisoned = false;
    public float slowPower = 1f, distancePast = 0;
    protected boolean revive;


    public Enemy(float x, float y, int ID, int enemyType, EnemyManager enemyManager, WaveManager waveManager) {
        this.x = x;
        this.y = y;
        this.ID = ID;
        this.enemyType = enemyType;
        this.enemyManager = enemyManager;
        this.waveManager = waveManager;
        if (enemyType != CAMEL_JUNIOR) {
            alive = true;
        }
        bounds = new Rectangle((int) x, (int) y, 64, 64);
        lastDir = -1;
        setStartHealth();
        setRevive();
        setResists(enemyType);
    }

    private void setResists(int enemyType) {
        this.mr=getMR(enemyType);
        this.armor=getArmor(enemyType);
    }

    private void setRevive() {
        if (this.enemyType == ORK_ZOMBIE) {
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

    public void reuse(float x, float y,float distancePast) {
        this.x = x;
        this.y = y;
        this.alive = true;
        setStartHealth();
        this.distancePast=distancePast;
    }

    public void killed() {
        revive = false;
    }

    public void setPoisonOn(int dmg, int duration, int damageType) {
        this.dmg = dmg;
        this.duration = duration;
        this.damageType=damageType;
        poisoned = true;
    }

    public void poisonDamage() {
        if (0 < duration) {
            duration--;
            if (0 == duration % 10) {
                hurt(dmg,damageType);
            }

        }
        if (duration == 0) {
            poisoned = false;
        }
    }

    public void move(float speed, int dir) {
        lastDir = dir;
        if (enemyManager.isCard8()){
            speed=speed+0.1f;
        }
        if (enemyManager.isCard9()){
            speed=speed+0.1f;
        }
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
        distancePast+=speed;
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
        if(enemyManager.isCard7()){
            health=health+health*10/100;
        }
        if(enemyManager.isCard10()){
            health=health+health*10/100;
        }
        maxHealth = health;
    }

    public void hurt(int dmg, int DMGType) {
        int calDMG=calculateDMG(dmg, DMGType) ;
        this.health -= calDMG;//calculateDMG(dmg, DMGType) ;
        if (health <= 0) {
            alive = false;
            if (enemyType == ORK_ZOMBIE) {
                if (this.revive) {
                    killed();
                    reuse(this.x, this.y,distancePast);
                }
            } else if (enemyType == CAMEL) {
                enemyManager.spawnJuniors(this.x, this.y, (this.enemyType + 1),distancePast);
            }
            enemyManager.rewardPlayer(enemyType);
            Game.addXp();
        }
    }

    private int calculateDMG(int dmg, int dmgType) {
        if (dmgType==PHYSICAL){
            return  ((100-armor)*dmg/100);
        } else if (dmgType==MAGIC){
            return  ((100-mr)*dmg/100);
        }else {return dmg;}
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

    public void setID(int ID) {
        this.ID = ID;
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

    public float getDistancePast() {
        return distancePast;
    }

    public void setDistancePast(float distancePast) {
        this.distancePast = distancePast;
    }

    public void setLastDir(int newDir) {
        this.lastDir = newDir;
    }
}
