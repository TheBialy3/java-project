package ally;

import enemies.Enemy;

public abstract class Ally {
    int x, y, allyType, lvl;
    int hp, attack;
    float speed;
    Enemy enemyToAttack;

    protected enum AllyStatus {
        WALK,
        STAY,
        STUN,
        FIGHT,
        DEAD
    }

    AllyStatus allyStatus = AllyStatus.STAY;

    public Ally(int x, int y, int allyType, int lvl) {
        this.x = x;
        this.y = y;
        this.allyType = allyType;
        this.lvl = lvl;
        getStartingHP();
        getStartingAttack();
        getStartingSpeed();
    }

    private void getStartingHP() {
        // hp=
    }

    private void getStartingAttack() {
    }

    private void getStartingSpeed() {
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

                break;
            case DEAD:

                break;

        }
    }

    public void move() {

    }

    public void setAllyStatus(int stat) {
        switch (stat) {
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

    public AllyStatus getAllyStatus() {
        return allyStatus;
    }
}
