package ally;

public abstract class Ally {
    protected enum AllyStatus {
        WALK,
        STAY,
        STUN,
        FIGHT,
        DEAD
    }
    AllyStatus allyStatus=AllyStatus.WALK;

    public Ally() {
    }

    public void update(int dir) {
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

    public void  move(){

    }
}
