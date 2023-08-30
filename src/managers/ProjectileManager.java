package managers;

import enemies.Enemy;
import helpz.Constants;
import helpz.LoadSave;
import objects.PathPoint;
import objects.Projectile;
import towers.Tower;
import scenes.Playing;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

import static helpz.Constants.ProjectileType.*;
import static helpz.Constants.TowerType.*;

public class ProjectileManager {

    private Random random = new Random();

    private Playing playing;
    private ArrayList<Projectile> projectiles = new ArrayList<>();
    private ArrayList<Explosion> explosions = new ArrayList<>();
    private BufferedImage[] proj_imgs, explo_imgs, splash_imgs;
    private int proj_id = 0, ranx, rany, tilePixelNumber = 64, halfTilePixelNumber = 32;

    public ProjectileManager(Playing playing) {
        this.playing = playing;
        importImgs();

    }

    private void importImgs() {
        //place of sprite on spritesheet
        BufferedImage atlas = LoadSave.getSpriteAtlas();
        proj_imgs = new BufferedImage[5];
        proj_imgs[0] = atlas.getSubimage(4 * tilePixelNumber, 4 * tilePixelNumber, tilePixelNumber, tilePixelNumber);
        proj_imgs[1] = atlas.getSubimage(6 * tilePixelNumber, 2 * tilePixelNumber, tilePixelNumber, tilePixelNumber);
        proj_imgs[2] = atlas.getSubimage(6 * tilePixelNumber, 0 * tilePixelNumber, tilePixelNumber, tilePixelNumber);
        proj_imgs[3] = atlas.getSubimage(7 * tilePixelNumber, 1 * tilePixelNumber, tilePixelNumber, tilePixelNumber);
        proj_imgs[4] = atlas.getSubimage(4 * tilePixelNumber, 1 * tilePixelNumber, tilePixelNumber, tilePixelNumber);
        implortExplosion(atlas);
    }

    private void implortExplosion(BufferedImage atlas) {
        explo_imgs = new BufferedImage[10];
        for (int i = 0; i < 10; i++) {
            explo_imgs[i] = atlas.getSubimage(i * tilePixelNumber, 5 * tilePixelNumber, tilePixelNumber, tilePixelNumber);
        }
        splash_imgs = new BufferedImage[1];
        splash_imgs[0] = atlas.getSubimage(5 * tilePixelNumber, 3 * tilePixelNumber, tilePixelNumber, tilePixelNumber);
    }

    public void newProjectile(Tower t, Enemy e) {
        int type = getProjType(t);

        int xDist = (int) (t.getX() - e.getX());
        int yDist = (int) (t.getY() - e.getY());
        int totDist = Math.abs(xDist) + Math.abs(yDist);

        float xPer = (float) Math.abs(xDist) / totDist;

        float xSpeed = xPer * helpz.Constants.ProjectileType.getSpeed(type);
        float ySpeed = helpz.Constants.ProjectileType.getSpeed(type) - xSpeed;

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
        for (Projectile p : projectiles) {
            if (!p.isActive()) {
                if (p.getProjectileType() == type) {
                    if (p.getProjectileType() == POISON_POTION) {
                        p.reuse(t.getX() + halfTilePixelNumber, t.getY() + halfTilePixelNumber, xSpeed, ySpeed, t.getDmg(), rotate, t.getDuration());
                        return;
                    }
                    p.reuse(t.getX() + halfTilePixelNumber, t.getY() + halfTilePixelNumber, xSpeed, ySpeed, t.getDmg(), rotate);
                    return;
                }
            }
        }
        if (t.getTowerType() == POISON_TOWER) {
            projectiles.add(new Projectile(t.getX() + halfTilePixelNumber, t.getY() + halfTilePixelNumber, xSpeed, ySpeed, t.getDmg(), rotate, proj_id++, type, t.getDuration()));
            return;
        }
        projectiles.add(new Projectile(t.getX() + halfTilePixelNumber, t.getY() + halfTilePixelNumber, xSpeed, ySpeed, t.getDmg(), rotate, proj_id++, type));
    }

    public void newMine(Tower t, PathPoint e) {
        int type = getProjType(t);
        ranx = random.nextInt(44);
        rany = random.nextInt(44);
        projectiles.add(new Projectile(e.getxCord() + 10 + ranx, e.getyCord() + 10 + rany, 0, 0, t.getDmg(), 0, proj_id++, type));
    }

    public void update() {
        for (Projectile p : projectiles) {
            if (p.isActive()) {
                p.move();
                if (isProjHitingEnemy(p)) {
                    p.setActive(false);
                    if (helpz.Constants.ProjectileType.isAoe(p.getProjectileType())) {
                        if (p.getProjectileType() == BOMB) {
                            explosions.add(new Explosion(p.getPos(), p.getProjectileType()));
                        }
                        if (p.getProjectileType() == POISON_POTION) {
                            explosions.add(new Explosion(p.getPos(), p.getProjectileType()));
                        }
                    }
                } else if (isProjOutOfBounds(p)) {
                    p.setActive(false);
                }
            }
        }
        for (Explosion e : explosions) {
            if (e.getIndex() < 10) {
                e.update();
            }
        }
    }

    private boolean isProjOutOfBounds(Projectile p) {
        if (p.getPos().x <= 0) {
            return true;
        }
        if (p.getPos().x >= 1280) {
            return true;
        }
        if (p.getPos().y <= 0) {
            return true;
        }
        if (p.getPos().y >= 1280) {
            return true;
        }
        return false;


    }

    private void explodeOnEnemys(Projectile p, ArrayList<Enemy> enemies) {
        for (Enemy e : enemies) {
            if (e.isAlive()) {
                float xDist = Math.abs(p.getPos().x - e.getX());
                float yDist = Math.abs(p.getPos().y - e.getY());
                float realDist = (float) Math.hypot(xDist, yDist);
                if (realDist <= Constants.ProjectileType.getRadiusExplosion(p.getProjectileType())) {
                    if (p.getProjectileType() == POISON_POTION) {
                        e.setPoisonOn(p.getDmg(), p.getDuration());
                    } else {
                        e.hurt(p.getDmg());
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
                        if (helpz.Constants.ProjectileType.isAoe(p.getProjectileType())) {
                            explodeOnEnemys(p, playing.getEnemyMenager().getEnemies());
                        } else {
                            e.hurt(p.getDmg());
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
        for (Projectile p : projectiles) {
            if (p.isActive()) {
                if (helpz.Constants.ProjectileType.isRorating(p.getProjectileType())) {
                    g2d.translate(p.getPos().x, p.getPos().y);
                    g2d.rotate(Math.toRadians(p.getRotation()));
                    g2d.drawImage(proj_imgs[p.getProjectileType()], -halfTilePixelNumber, -halfTilePixelNumber, null);
                    g2d.rotate(-Math.toRadians(p.getRotation()));
                    g2d.translate(-p.getPos().x, -p.getPos().y);
                } else {
                    g2d.drawImage(proj_imgs[p.getProjectileType()], (int) p.getPos().x - halfTilePixelNumber, (int) p.getPos().y - halfTilePixelNumber, null);
                }
            }
            drawExplosions(g2d);
        }
    }

    private void drawExplosions(Graphics2D g2d) {
        for (Explosion e : explosions) {
            if (e.getExploType() == BOMB) {
                if (e.getIndex() < 10) {
                    g2d.drawImage(explo_imgs[e.getIndex()], (int) e.getPos().x - halfTilePixelNumber, (int) e.getPos().y - halfTilePixelNumber, null);
                }//else if (e.getIndex() == 10){ aoeExplosionsdamageSet =false; }
            } else {
                if (e.getIndex() < 10) {
                    g2d.drawImage(splash_imgs[0], (int) e.getPos().x - halfTilePixelNumber, (int) e.getPos().y - halfTilePixelNumber, null);
                }
            }
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
            case MINES_FACTORY:
                return MINES;
            case POISON_TOWER:
                return POISON_POTION;
        }
        return 0;
    }

    public void endOfWave() {
        projectiles.clear();
    }

    public class Explosion {

        private int exploTick = 0, exploIndex = 0, type;
        private Point2D.Float pos;

        public Explosion(Point2D.Float pos, int type) {
            this.pos = pos;
            this.type = type;
        }

        public void update() {
            exploTick++;
            if (exploTick >= 5) {
                exploTick = 0;
                exploIndex++;
            }
        }

        public int getExploType() {
            return type;
        }

        public int getIndex() {
            return exploIndex;
        }

        public Point2D.Float getPos() {
            return pos;
        }
    }

    public void reset() {
        projectiles.clear();
        explosions.clear();
        proj_id = 0;
    }
}


