package objects;

import java.awt.*;

public class TowerPlace {
    private  int x,y ;
    private  boolean isOccupied=false;
    private Rectangle hitBox;

    public TowerPlace(int x, int y) {
        this.x = x;
        this.y = y;
        setRectangle();
    }

    private void setRectangle() {
        hitBox=new Rectangle(x,y,34*4,+19*4);
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

    public Rectangle getHitBox() {
        return hitBox;
    }
}
