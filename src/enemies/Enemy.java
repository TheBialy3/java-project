package enemies;

import helpz.Constants;
import main.Game;
import managers.EnemyManager;
import managers.TowerManager;
import managers.WaveManager;


import java.awt.*;

import static helpz.Constants.Direction.*;
import static helpz.Constants.EnemyType.*;
import static helpz.Constants.EnemyType.MAGIC;
import static helpz.Constants.EnemyType.PHYSICAL;
import static helpz.Constants.TowerType.*;

public abstract class Enemy {


    protected EnemyManager enemyManager;
    protected TowerManager towerManager;
    protected WaveManager waveManager;
    protected float x, y;
    protected Rectangle bounds;
    protected int dmg, duration = 0;
    protected int slowTickLimit = 10, slowTick = slowTickLimit;
    protected int countdown = 0;
    protected int maxHealth, health, mr, armor,attack;
    protected int ID;
    protected int enemyType, damageType;
    protected int lastDir;
    protected boolean alive = false, poisoned = false;
    public float slowPower = 1f, distancePast = 0;
    protected boolean power = false;
    protected boolean targetable = true;
    EnemyStatus enemyStatus = EnemyStatus.WALK;

    protected enum EnemyStatus {
        WALK,
        STAY,
        STUN,
        FIGHT,
        DEAD
    }


    public Enemy(float x, float y, int ID, int enemyType, EnemyManager enemyManager, WaveManager waveManager, TowerManager towerManager) {
        this.x = x;
        this.y = y;
        this.ID = ID;
        this.enemyType = enemyType;
        this.enemyManager = enemyManager;
        this.waveManager = waveManager;
        this.towerManager = towerManager;
        if (enemyType != CAMEL_JUNIOR) {
            alive = true;
        }
        setBounds(enemyType);
        lastDir = -1;
        setStartHealth();
        setStartingAttack();
        setPower();
        setResists(enemyType);
    }




    public void update(int dir) {
        switch (enemyStatus) {
            case STAY:

                break;
            case WALK:
                move(dir);
                break;
            case STUN:

                break;
            case FIGHT:

                break;

        }
    }

    public void move(int dir) {
        lastDir = dir;
        float speed = getSpeed(enemyType);
        if (enemyManager.isCard8()) {
            speed = speed + 0.1f;
        }
        if (enemyManager.isCard9()) {
            speed = speed + 0.1f;
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
        distancePast += speed;
        updateHitbox();
        if (poisoned) {
            poisonDamage();
        }
        if (isRegainHP(enemyType)) {
            regenHp();
        }
    }

    private void regenHp() {
        if (this instanceof Orc) {
            ((Orc) this).heal(2);
        } else if (this instanceof Tentacle) {
            ((Tentacle) this).heal(3);
        } else if (this instanceof Slime) {
            ((Slime) this).heal(2);
        } else if (this instanceof Bird) {
            ((Bird) this).heal(1);
        }

    }
    private void setStartingAttack() {
        attack=getAttackEnemy(enemyType);
    }

    public void healThis(int heal) {
        if (health + heal > maxHealth) {
            health = maxHealth;
        } else {
            health += heal;
        }
    }

    private void setBounds(int enemyType) {
        int w = getHeightOfHitBox(enemyType);
        int h = getWightOfHitBox(enemyType);
        bounds = new Rectangle((int) (x + (64 - w) / 2), (int) (y + (64 - h) / 2), w, h);
    }

    private void setResists(int enemyType) {
        this.mr = getMR(enemyType);
        this.armor = getArmor(enemyType);
    }

    private void setPower() {
        if (this.enemyType == ORK_ZOMBIE) {
            power = true;
        } else if (this.enemyType == CREEPY_CAT) {
            power = true;
        } else if (this.enemyType == BANSHEE) {
            power = true;
        }

    }

    public void reuse(float x, float y, float distancePast) {
        this.x = x;
        this.y = y;
        this.alive = true;
        setStartHealth();
        this.distancePast = distancePast;
    }


    public void setPoisonOn(int dmg, int duration, int damageType) {
        this.dmg = dmg;
        this.duration = duration;
        this.damageType = damageType;
        poisoned = true;
    }

    public void poisonDamage() {
        if (0 < duration) {
            duration--;
            if (0 == duration % 10) {
                hurt(dmg, damageType);
            }
        }
        if (duration == 0) {
            poisoned = false;
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
        health = Constants.EnemyType.getStartHealth(enemyType) * (5 + waveIndex) / 2;//(5 + waveIndex) / 10
        if (enemyManager.isCard7()) {
            health = health + health * 10 / 100;
        }
        if (enemyManager.isCard11()) {
            health = health + health * 20 / 100;
        }
        if (enemyManager.isCard12()) {
            health = health + health * 10 / 100;
        }
        if (enemyManager.isCard10()) {
            health = health + health * 10 / 100;
        }
        maxHealth = health;
    }

    public void hurt(int dmg, int DMGType) {
        int calDMG = calculateDMG(dmg, DMGType);
        this.health -= calDMG;//calculateDMG(dmg, DMGType) ;
        if (enemyType == CREEPY_CAT) {
            if (this.power) {
                if (health < maxHealth / 2) {
                    setTargetable(false);
                    startCountdown(240);
                    power = false;
                }
            }
        }
        if (health <= 0) {
            alive = false;
            if (enemyType == ORK_ZOMBIE) {
                if (this.power) {
                    power = false;
                    reuse(this.x, this.y, distancePast);
                }
            } else if (enemyType == CAMEL) {
                enemyManager.spawnJuniors(this.x, this.y, (this.enemyType + 1), distancePast);
            } else if (enemyType == BANSHEE) {
                towerManager.stunCloseTower(x, y);
            }
            enemyManager.rewardPlayer(enemyType);
            Game.addXp();
        }
    }

    public void updateEnemyCountdown() {
        if (countdown > 0) {
            countdown--;
        }
        fixPower();
    }

    private void fixPower() {
        if (!targetable) {
            if (isCountdownOver()) {
                targetable = true;
            }
        }
    }

    public boolean isCountdownOver() {
        return countdown == 0;
    }


    private void startCountdown(int countdownTime) {
        countdown = countdownTime;
    }

    private int calculateDMG(int dmg, int dmgType) {
        if (enemyManager.isCard11()) {
            return dmg;
        }
        if (dmgType == PHYSICAL) {
            return ((100 - armor) * dmg / 100);
        } else if (dmgType == MAGIC) {
            return ((100 - mr) * dmg / 100);
        } else {
            return dmg;
        }
    }


    public void kill() {
        health = 0;
        alive = false;
    }

    public boolean doesRevive() {
        if (enemyType == ORK_ZOMBIE) {
            return power;
        }
        return false;
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

    public void setLastDir(int newDir) {
        this.lastDir = newDir;
    }

    public boolean isTargetable() {
        return targetable;
    }

    public void setTargetable(boolean targetable) {
        this.targetable = targetable;
    }


}
