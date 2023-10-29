package objects;

import managers.ProjectileManager;

import java.awt.geom.Point2D;

import static helpz.Constants.ProjectileType.ARROW;

public class Projectile {

    private Point2D.Float pos;
    private int id, projectileType, dmg, duration, damageType;
    private boolean active = true;
    private float speedx, speedy, rotation;
    private int projectilePricing = 1;
    private ProjectileManager projectileManager;

    public Projectile(float x, float y, float speedx, float speedy, int dmg, float rotation, int id, int projectileType, int damageType, ProjectileManager projectileManager) {
        pos = new Point2D.Float(x, y);
        this.speedx = speedx;
        this.speedy = speedy;
        this.dmg = dmg;
        this.rotation = rotation;
        this.id = id;
        this.projectileType = projectileType;
        this.damageType = damageType;
        this.projectileManager = projectileManager;
        setProjectilePricing();
    }


    public void reuse(float x, float y, float speedx, float speedy, int dmg, float rotation, int duration) {
        pos = new Point2D.Float(x, y);
        this.speedx = speedx;
        this.speedy = speedy;
        this.dmg = dmg;
        this.rotation = rotation;
        this.active = true;
        this.duration = duration;
        setProjectilePricing();
    }

    public void reuse(float x, float y, float speedx, float speedy, int dmg, float rotation) {
        pos = new Point2D.Float(x, y);
        this.speedx = speedx;
        this.speedy = speedy;
        this.dmg = dmg;
        this.rotation = rotation;
        this.active = true;
        setProjectilePricing();
    }

    public void move() {
        pos.x += speedx;
        pos.y += speedy;
    }

    public Point2D.Float getPos() {
        return pos;
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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getDmg() {
        return dmg;
    }

    public float getRotation() {
        return rotation;
    }


    public int getDuration() {
        return duration;
    }

    public int getDamageType() {
        return damageType;
    }

    public void setProjectilePricing() {
        if (projectileManager.isCard13()) {
            if (projectileType == ARROW) {
                projectilePricing++;
            }
        }
    }

    public boolean isProjectilePricing() {
        if (projectilePricing > 1) {
            projectilePricing--;
            return true;
        }
        return false;
    }
}
