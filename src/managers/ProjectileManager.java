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

import static helpz.Constants.EnemyType.getMoveType;
import static helpz.Constants.NumbersOf.NUMBER_OF_PROJECTILE;
import static helpz.Constants.ProjectileType.*;
import static helpz.Constants.ProjectileType.BOTH;
import static helpz.Constants.TowerType.*;

public class ProjectileManager {

    private Random random = new Random();

    private Playing playing;
    private ArrayList<Projectile> projectiles = new ArrayList<>();
    private ArrayList<Explosion> explosions = new ArrayList<>();
    private BufferedImage[] proj_imgs, explo_imgs, splash_imgs;
    private int proj_id = 0, ranx, rany, tilePixelNumber = 64, halfTilePixelNumber = 32;
    private boolean Card12 = false, Card13 = false, Card16 = false, Card22 = false, Card25 = false, Card33 = false;
    private boolean Card36 = false;


    public ProjectileManager(Playing playing) {
        this.playing = playing;
        importImgs();
    }

    private void importImgs() {
        BufferedImage atlas = LoadSave.getSpriteAtlas();
        proj_imgs = new BufferedImage[NUMBER_OF_PROJECTILE];
        for (int i = 0; i < NUMBER_OF_PROJECTILE; i++) {
            proj_imgs[i] = atlas.getSubimage(0 + i * tilePixelNumber, 21 * tilePixelNumber, tilePixelNumber, tilePixelNumber);
        }
        importExplosion(atlas);
    }

    private void importExplosion(BufferedImage atlas) {
        explo_imgs = new BufferedImage[10];
        for (int i = 0; i < 10; i++) {
            explo_imgs[i] = atlas.getSubimage(i * tilePixelNumber, 26 * tilePixelNumber, tilePixelNumber, tilePixelNumber);
        }
        splash_imgs = new BufferedImage[1];
        splash_imgs[0] = atlas.getSubimage(0 * tilePixelNumber, 30 * tilePixelNumber, tilePixelNumber, tilePixelNumber);
    }

    public void newProjectile(Tower t, Enemy e) {
        int type = getProjType(t);
        int xDist;
        int yDist;
        //mouseFollower
        if (t.getTowerType() == MOUSE_FOLLOWS_TOWER) {
            xDist = (int) (t.getX() - playing.getMouseX() + 32);
            yDist = (int) (t.getY() - playing.getMouseY() + 32);
        } else {
            xDist = (int) (t.getX() - e.getX());
            yDist = (int) (t.getY() - e.getY());
        }
        int totDist = Math.abs(xDist) + Math.abs(yDist);
        float xPer = (float) Math.abs(xDist) / totDist;

        float xSpeed = xPer * helpz.Constants.ProjectileType.getSpeed(type);
        float ySpeed = helpz.Constants.ProjectileType.getSpeed(type) - xSpeed;
        //mouseFollower
        if (t.getTowerType() == MOUSE_FOLLOWS_TOWER) {
            if (t.getX() + 32 > playing.getMouseX())
                xSpeed *= -1;
            if (t.getY() + 32 > playing.getMouseY())
                ySpeed *= -1;
        } else {
            //rest
            if (t.getX() > e.getX())
                xSpeed *= -1;
            if (t.getY() > e.getY())
                ySpeed *= -1;
        }
        float rotate = 0;
        try {
            if (helpz.Constants.ProjectileType.isRotating(type)) {
                float arcValue = (float) Math.atan(yDist / (float) xDist);
                rotate = (float) Math.toDegrees(arcValue);

                if (xDist > 0) {
                    rotate += 180;
                }
            }
        } catch (Exception expe) {
            System.out.println("draw projectile error");
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
            projectiles.add(new Projectile(t.getX() + halfTilePixelNumber, t.getY() + halfTilePixelNumber, xSpeed, ySpeed, t.getDmg(), rotate, proj_id++, type, t.getDuration(), this));
            return;
        }
        projectiles.add(new Projectile(t.getX() + halfTilePixelNumber, t.getY() + halfTilePixelNumber, xSpeed, ySpeed, t.getDmg(), rotate, proj_id++, type, getDmgTypeTower(t.getTowerType()), this));
    }

    public void newMine(Tower t, PathPoint e) {
        int type = getProjType(t);
        ranx = random.nextInt(44);
        rany = random.nextInt(44);
        projectiles.add(new Projectile(e.getxCord() + 10 + ranx, e.getyCord() + 10 + rany, 0, 0, t.getDmg(), 0, proj_id++, type, getDmgTypeTower(t.getTowerType()), this));
    }

    public void update() {
        for (Projectile p : projectiles) {
            if (p.isActive()) {
                p.move();
                if (isProjHitingEnemy(p)) {
                    if (!p.isProjectilePricing()) {
                        p.setActive(false);
                    }
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
        try {
            for (Enemy e : enemies) {
                if (e.isAlive()) {
                    float xDist = Math.abs(p.getPos().x - e.getX());
                    float yDist = Math.abs(p.getPos().y - e.getY());
                    float realDist = (float) Math.hypot(xDist, yDist);
                    float radiusExplosion = Constants.ProjectileType.getRadiusExplosion(p.getProjectileType());
                    if (Card16) {
                        if (p.getProjectileType() == BOMB) {
                            radiusExplosion = radiusExplosion * 15 / 10;
                        }
                    }
                    if (Card25) {
                        if (p.getProjectileType() == POISON_POTION) {
                            radiusExplosion = radiusExplosion * 15 / 10;
                        }
                    }
                    if (realDist <= radiusExplosion) {
                        if (p.getProjectileType() == POISON_POTION) {
                            e.setPoisonOn(p.getDmg(), p.getDuration(), p.getDamageType());
                        } else {
                            e.hurt(p.getDmg(), p.getDamageType());
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("ConcurrentModificationException explodeOnEnemys");
        }
    }

    private boolean isProjHitingEnemy(Projectile p) {
        if (p.isActive()) {
            for (Enemy e : playing.getEnemyManager().getEnemies()) {
                if (e.isAlive()) {
                    if (isTowerTargetingEnemy(p, e)) {
                        if (e.getBounds().contains(p.getPos())) {
                            if (helpz.Constants.ProjectileType.isAoe(p.getProjectileType())) {
                                explodeOnEnemys(p, playing.getEnemyManager().getEnemies());
                            } else {
                                e.hurt(p.getDmg(), p.getDamageType());
                            }
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    private boolean isTowerTargetingEnemy(Projectile p, Enemy e) {
        if (Card12) {
            return true;
        }
        if (getProjectileTargetMoveType(p.getProjectileType()) == BOTH) {
            return true;
        }
        if (getProjectileTargetMoveType(p.getProjectileType()) == getMoveType(e.getEnemyType())) {
            return true;
        }
        return false;
    }

    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
       try {
            for (Projectile p : projectiles) {
                if (p.isActive()) {
                    if (helpz.Constants.ProjectileType.isRotating(p.getProjectileType())) {
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
        }catch (Exception e){
           System.out.println("pm Exception in thread draw");
       }
    }

    private void drawExplosions(Graphics2D g2d) {
        for (Explosion e : explosions) {
            if (e.getExploType() == BOMB) {
                if (e.getIndex() < 10) {
                    g2d.drawImage(explo_imgs[e.getIndex()], (int) e.getPos().x - halfTilePixelNumber, (int) e.getPos().y - halfTilePixelNumber, null);
                }//else if (e.getIndex() == 10){ aoeExplosionsDamageSet =false; }
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
            case MINES_FACTORY:
                return MINES;
            case POISON_TOWER:
                return POISON_POTION;
            case CROSSBOW:
                return SHORT_BEM;
            case MOUSE_FOLLOWS_TOWER:
                return BULLET;
            default:
                return 0;
        }
    }

    public void endOfWave() {
        if (Card22) {
            for (Projectile p : projectiles) {
                p.endOfTurn();
            }
        } else {
            projectiles.clear();
        }
    }

    public void crossbowNewProjectile(Tower t) {
        int type = getProjType(t);
        float Speed = helpz.Constants.ProjectileType.getSpeed(type);
        int neg;
        for (int i = 0; i < 4; i++) {
            if (i < 2) {
                neg = -1;
            } else {
                neg = 1;
            }
            projectiles.add(new Projectile(t.getX() + halfTilePixelNumber, t.getY() + halfTilePixelNumber, Speed * ((1 + i) % 2) * neg, Speed * (i % 2) * neg, t.getDmg(), 90 * i, proj_id++, type, getDmgTypeTower(t.getTowerType()), this));

        }
        if (Card33) {
            float negX = 0.85f;
            float negY = 0.85f;
            for (int i = 0; i < 4; i++) {
                if (i == 0) {
                    negX = -1;
                    negY = -1;
                } else if (i == 1) {
                    negX = -1;
                    negY = 1;
                } else if (i == 2) {
                    negX = 1;
                    negY = 1;
                } else {
                    negX = 1;
                    negY = -1;
                }
                projectiles.add(new Projectile(t.getX() + halfTilePixelNumber, t.getY() + halfTilePixelNumber, Speed * negY, Speed * negX, t.getDmg(), 90 * i + 45, proj_id++, type, getDmgTypeTower(t.getTowerType()), this));

            }
        }
    }

    public void setCard12(boolean card12) {
        Card12 = card12;
    }

    public void setCard13(boolean card13) {
        Card13 = card13;
    }

    public void setCard16(boolean card16) {
        Card16 = card16;
    }

    public void setCard22(boolean card22) {
        Card22 = card22;
    }

    public void setCard25(boolean card25) {
        Card25 = card25;
    }

    public void setCard33(boolean card33) {
        Card33 = card33;
    }

    public void setCard36(boolean card36) {
        Card36 = card36;
    }

    public boolean isCard13() {
        return Card13;
    }

    public boolean isCard22() {
        return Card22;
    }

    public boolean isCard36() {
        return Card36;
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
        setAllCardsBoolFalse();
    }

    private void setAllCardsBoolFalse() {
        Card12 = false;
        Card13 = false;
        Card16 = false;
        Card22 = false;
        Card25 = false;
        Card33 = false;
        Card36 = false;
    }


}


