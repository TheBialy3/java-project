package objects;

public class TowerPlace {
    private  int x,y ;
    private  boolean isOccupied=false;

    public TowerPlace(int x, int y, boolean isOccupied) {
        this.x = x;
        this.y = y;
        this.isOccupied = isOccupied;
    }

    public void setOccupied(boolean occupied) {
        isOccupied = occupied;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isOccupied() {
        return isOccupied;
    }
}
