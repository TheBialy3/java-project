package objects;

import java.awt.geom.Point2D;

public class Projectile {

    private Point2D.Float pos;
    private int id, projectileType, dmg;
    private boolean active = true;
    private float speedx, speedy , rotation;


    public Projectile(float x, float y, float speedx, float speedy, int dmg,float rotation, int id, int projectileType) {
        pos = new Point2D.Float(x, y);
        this.speedx = speedx;
        this.speedy = speedy;
        this.dmg = dmg;
        this.rotation = rotation;
        this.id = id;
        this.projectileType = projectileType;
    }

    public void reuse(float x, float y, float speedx, float speedy, int dmg,float rotation) {
        pos = new Point2D.Float(x, y);
        this.speedx = speedx;
        this.speedy = speedy;
        this.dmg = dmg;
        this.rotation = rotation;

    }

    public void move() {
        pos.x += speedx;
        pos.y += speedy;
    }

    public Point2D.Float getPos() {
        return pos;
    }

    public void setPos(Point2D.Float pos) {
        this.pos = pos;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProjectileType() {
        return projectileType;
    }

    public void setProjectileType(int projectileType) {
        this.projectileType = projectileType;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getDmg(){
        return dmg;
    }

    public float getRotation(){
        return rotation;
    }



}
