package objects;

public class TowerBuildPlace {
    protected int x,y;
    protected boolean free=true;

    public TowerBuildPlace(int x,int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setFree(boolean free) {
        this.free = free;
    }

    public boolean isFree() {
        return free;
    }
}
