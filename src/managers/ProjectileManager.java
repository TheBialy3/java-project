package managers;

import enemies.Enemy;
import helpz.Constants;
import helpz.LoadSave;
import objects.Projectile;
import objects.Tower;
import scenes.Playing;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static helpz.Constants.ProjectileType.*;
import static helpz.Constants.TowerType.*;

public class ProjectileManager {

    private Playing playing;
    private ArrayList<Projectile> projectiles = new ArrayList<>();
    private ArrayList<Explosion> explosions = new ArrayList<>();
    private BufferedImage[] proj_imgs, explo_imgs;
    private int proj_id = 0;

    public ProjectileManager(Playing playing) {
        this.playing = playing;
        importImgs();

    }

    private void importImgs() {
        //0,4
        BufferedImage atlas = LoadSave.getSpriteAtlas();
        proj_imgs = new BufferedImage[4];
        proj_imgs[0] = atlas.getSubimage(4 * 64, 4 * 64, 64, 64);
        proj_imgs[1] = atlas.getSubimage(6 * 64, 2 * 64, 64, 64);
        proj_imgs[2] = atlas.getSubimage(7 * 64, 1 * 64, 64, 64);
        proj_imgs[3] = atlas.getSubimage(6 * 64, 0 * 64, 64, 64);
        implortExplosion(atlas);
    }

    private void implortExplosion(BufferedImage atlas) {
        explo_imgs = new BufferedImage[10];
        for (int i = 0; i < 10; i++) {
            explo_imgs[i] = atlas.getSubimage(i * 64, 5 * 64, 64, 64);
        }
    }

    public void newProjectile(Tower t, Enemy e) {
        int type = getProjType(t);

        int xDist = (int) (t.getX() - e.getX());
        int yDist = (int) (t.getY() - e.getY());
        int totDist = Math.abs(xDist) + Math.abs(yDist);

        float xPer = (float) Math.abs(xDist) / totDist;

        float xSpeed = xPer * helpz.Constants.ProjectileType.GetSpeed(type);
        float ySpeed = helpz.Constants.ProjectileType.GetSpeed(type) - xSpeed;

        if (t.getX() > e.getX())
            xSpeed *= -1;
        if (t.getY() > e.getY())
            ySpeed *= -1;

        float rotate = 0;

        if (helpz.Constants.ProjectileType.isRorating(type)) {
            float arcValue = (float) Math.atan(yDist / (float) xDist);
            rotate = (float) Math.toDegrees(arcValue);

            if (xDist > 0)
                rotate += 180;
        }

        projectiles.add(new Projectile(t.getX() + 32, t.getY() + 32, xSpeed, ySpeed, t.getDmg(), rotate, proj_id++, type));

    }

    public void update() {
        for (Projectile p : projectiles) {
            if (p.isActive()) {
                p.move();
                if (isProjHitingEnemy(p)) {
                    p.setActive(false);
                    if (helpz.Constants.ProjectileType.isAoe(p.getProjectileType())) {
                        explodeOnEnemys(p);
                    }
                    if (p.getProjectileType() == BOMB) {
                        explosions.add(new Explosion(p.getPos()));

                    }
                }
            }else if (isProjOutOfBounds(p)) {
                p.setActive(false);
            }
        }
        for (Explosion e : explosions) {
            if (e.getIndex() < 10) {
                e.update();
            }
        }
    }

    private boolean isProjOutOfBounds(Projectile p) {
        if (p.getPos().x <=0) {
            if (p.getPos().x >=1280) {
                if (p.getPos().y <=0) {
                    if (p.getPos().y >=1280) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private void explodeOnEnemys(Projectile p) {
        for (Enemy e : playing.getEnemyMenager().getEnemies()) {
            if (e.isAlive()) {
                float xDist = Math.abs(p.getPos().x - e.getX());
                float yDist = Math.abs(p.getPos().y - e.getY());
                float realDist = (float) Math.hypot(xDist, yDist);
                if (realDist <= Constants.ProjectileType.GetRadiusExplosion(p.getProjectileType())) {
                    e.hurt(p.getDmg());
                    if (helpz.Constants.ProjectileType.isSlow(p.getProjectileType())) {
                        e.slow(helpz.Constants.ProjectileType.GetPowerOfSlow(p.getProjectileType()));
                    }
                }
            }
        }
    }

    private boolean isProjHitingEnemy(Projectile p) {
        if (p.isActive()) {
            for (Enemy e : playing.getEnemyMenager().getEnemies()) {
                if (e.isAlive()) {
                    if (e.getBounds().contains(p.getPos())) {
                        e.hurt(p.getDmg());
                        if (helpz.Constants.ProjectileType.isSlow(p.getProjectileType())) {
                            e.slow(helpz.Constants.ProjectileType.GetPowerOfSlow(p.getProjectileType()));
                        }
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        for (Projectile p : projectiles)
            if (p.isActive()) {
                if (helpz.Constants.ProjectileType.isRorating(p.getProjectileType())) {
                    g2d.translate(p.getPos().x, p.getPos().y);
                    g2d.rotate(Math.toRadians(p.getRotation()));
                    g2d.drawImage(proj_imgs[p.getProjectileType()], -32, -32, null);
                    g2d.rotate(-Math.toRadians(p.getRotation()));
                    g2d.translate(-p.getPos().x, -p.getPos().y);
                } else {
                    g2d.drawImage(proj_imgs[p.getProjectileType()], (int) p.getPos().x - 32, (int) p.getPos().y - 32, null);
                }
            }
        drawExplosions(g2d);

    }

    private void drawExplosions(Graphics2D g2d) {
        for (Explosion e : explosions) {
            if (e.getIndex() < 10) {
                g2d.drawImage(explo_imgs[e.getIndex()], (int) e.getPos().x - 32, (int) e.getPos().y - 32, null);
            }//else if (e.getIndex() == 10){ aoeExplosionsdamageSet =false; }
        }
    }

    private int getProjType(Tower t) {
        switch (t.getTowerType()) {
            case ARCHER:
                return ARROW;
            case CANNON:
                return BOMB;
            case FROST_MAGE:
                return FROST_BEEM;
            case MINE_FACTORY:
                return MINE;
        }
        return 0;
    }

    public class Explosion {

        private int exploTick = 0, exploIndex = 0;
        private Point2D.Float pos;

        public Explosion(Point2D.Float pos) {
            this.pos = pos;
        }

        public void update() {
            exploTick++;
            if (exploTick >= 5) {
                exploTick = 0;
                exploIndex++;
            }
        }

        public int getIndex() {
            return exploIndex;
        }

        public Point2D.Float getPos() {
            return pos;
        }
    }

    public void reset(){
        projectiles.clear();
        explosions.clear();
        proj_id=0;
    }
}


