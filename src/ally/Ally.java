package ally;

import enemies.Enemy;
import helpz.Constants;

import java.awt.*;

import static helpz.Constants.EnemyType.MAGIC;
import static helpz.Constants.EnemyType.PHYSICAL;


public abstract class Ally {
    int x, y, allyType, lvl, xp = 0, xpToLvlUp = 100;
    int attackAnimation = 0, attackAnimationLimit;
    float passiveX, passiveY;
    int coolDown = 120, deadCoolDown = 0;
    int hpMax, hp, armor, mr, attack, attackType;
    float speed;
    Enemy enemyToAttack;
    float xSpeed;
    float ySpeed;

    protected enum AllyStatus {
        WALK,
        STAY,
        STUN,
        FIGHT,
        DEAD
    }

    AllyStatus allyStatus = AllyStatus.WALK;

    public Ally(int x, int y, int allyType, int lvl, float passiveX, float passiveY) {
        this.x = x;
        this.y = y;
        this.allyType = allyType;
        this.lvl = lvl;
        this.passiveX = passiveX;
        this.passiveY = passiveY;
        getStartingHP();
        getResists();
        getStartingAttack();
        getStartingSpeed();
        getAttackAnimationLimit();
    }


    public void update() {
        switch (allyStatus) {
            case STAY:

                break;
            case WALK:
                move();
                break;
            case STUN:

                break;
            case FIGHT:
                fight();
                break;
            case DEAD:
                respownCoolDown();
                break;

        }
    }

    public void draw(Graphics g) {

    }

    private void getResists() {
        mr = Constants.AllyType.getMR(allyType);
        armor = Constants.AllyType.getArmor(allyType);
    }

    private void getAttackAnimationLimit() {
        attackAnimationLimit = Constants.AllyType.getAttackAnimationLimit(allyType);
    }

    private void getStartingHP() {
        hpMax = Constants.AllyType.getStartHealth(allyType);
        hp = hpMax;
    }

    private void getStartingAttack() {
        attack = Constants.AllyType.getAttack(allyType);
        attackType = Constants.AllyType.getDmgType(allyType);
    }

    private void getStartingSpeed() {
        speed = Constants.AllyType.getSpeed(allyType);
    }

    public void lvlUp() {
        hpUp();
        atackUp();
        coolDownUp();
        lvl++;
    }

    private void coolDownUp() {
        coolDown += 60;
    }

    public void setEnemyToAttack(Enemy enemy) {
        enemyToAttack = enemy;
        setMove();
        setAllyStatus(1);
    }

    private void atackUp() {
        attack *= (int) 1.1;
    }

    private void hpUp() {
        hpMax *= (int) 1.1;
    }


    private void respownCoolDown() {
        deadCoolDown++;
        if (isCoolDownOver()) {
            setAllyStatus(2);
            deadCoolDown = 0;
            hp = hpMax;
        }
    }

    private boolean isCoolDownOver() {
        if (coolDown <= deadCoolDown) {
            return true;
        }
        return false;
    }

    private void fight() {
        attackAnimation++;
        if (attackAnimation >= attackAnimationLimit) {
            enemyToAttack.hurt(attack, attackType);
            attackAnimation = 0;
        }
        if (!enemyToAttack.isAlive()) {
            enemyToAttack = null;
            attackAnimation = 0;
            setAllyStatus(1);
        }
    }

    public void setMove() {
        int xDist;
        int yDist;
        if (enemyToAttack != null) {
            xDist = (int) (x - enemyToAttack.getX() + 30);
            yDist = (int) (y - enemyToAttack.getY());
        } else {
            xDist = (int) (x - passiveX);
            yDist = (int) (y - passiveY);
        }

        int totDist = Math.abs(xDist) + Math.abs(yDist);
        float xPer = (float) Math.abs(xDist) / totDist;

        xSpeed = xPer * Constants.AllyType.getSpeed(allyType);
        ySpeed = Constants.AllyType.getSpeed(allyType) - xSpeed;
        if (enemyToAttack != null) {
            if (x > enemyToAttack.getX()) {
                xSpeed *= -1;
            }
            if (y > enemyToAttack.getY()) {
                ySpeed *= -1;
            }
        } else {
            if (x > passiveX) {
                xSpeed *= -1;
            }
            if (y > passiveY) {
                ySpeed *= -1;
            }
        }
    }

    public void move() {
        x += xSpeed;
        y += ySpeed;
        if (enemyToAttack != null) {
            if (didAllyPass(enemyToAttack.getX(), enemyToAttack.getY())) {
                setAllyStatus(3);
            }
        } else {
            if (didAllyPass(passiveX, passiveY)) {
                setAllyStatus(2);
            }
        }

    }

    private boolean didAllyPass(float toX, float toY) {
        int xDist;
        int yDist;
        xDist = (int) (x - toX + 30);
        yDist = (int) (y - toY);
        if (xDist > xDist + xSpeed || yDist > yDist + ySpeed) {
            return true;
        }
        return false;
    }

    public void setAllyStatus(int statusOfAlly) {
        switch (statusOfAlly) {
            case 0:
                this.allyStatus = AllyStatus.DEAD;
                break;
            case 1:
                this.allyStatus = AllyStatus.WALK;
                break;
            case 2:
                this.allyStatus = AllyStatus.STAY;
                break;
            case 3:
                this.allyStatus = AllyStatus.FIGHT;
                break;
            case 4:
                this.allyStatus = AllyStatus.STUN;
                break;

        }
    }

    public void hurt(int dmg, int DMGType) {
        int calDMG = calculateDMG(dmg, DMGType);
        this.hp -= calDMG;
        if (hp <= 0) {
            setAllyStatus(0);
        }
    }

    private int calculateDMG(int dmg, int dmgType) {
        if (dmgType == PHYSICAL) {
            return ((100 - armor) * dmg / 100);
        } else if (dmgType == MAGIC) {
            return ((100 - mr) * dmg / 100);
        } else {
            return dmg;
        }
    }

    public AllyStatus getAllyStatus() {
        return allyStatus;
    }
}
