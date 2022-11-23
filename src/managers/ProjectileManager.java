package managers;

import enemies.Enemy;
import helpz.Constants;
import helpz.LoadSave;
import objects.Projectile;
import objects.Tower;
import scenes.Playing;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static helpz.Constants.ProjectileType.*;
import static helpz.Constants.TowerType.*;

public class ProjectileManager {

    private Playing playing;
    private ArrayList<Projectile> projectiles = new ArrayList<>();
    private BufferedImage[] proj_img;
    private int projectilesID = 0;

    public ProjectileManager(Playing playing) {
        this.playing = playing;
        importImgs();
    }

    private void importImgs() {
        //0,4
        BufferedImage atlas = LoadSave.getSpriteAtlas();
        proj_img = new BufferedImage[4];
        proj_img[0] = atlas.getSubimage(3 * 64, 4 * 64, 64, 64);
        proj_img[1] = atlas.getSubimage(1 * 64, 1 * 64, 64, 64);
        proj_img[2] = atlas.getSubimage(1 * 64, 4 * 64, 64, 64);
        proj_img[3] = atlas.getSubimage(2 * 64, 4 * 64, 64, 64);

    }

    public void newProjectile(Tower t, Enemy e) {
        int type = getProjType(t);

        int xdis = (int) Math.abs(t.getX() - e.getX());
        int ydis = (int) Math.abs(t.getY() - e.getY());
        int todis = Math.abs(xdis) + Math.abs(ydis);

        float xper = (float) Math.abs(xdis) / todis;

        float speedx = xper * Constants.ProjectileType.GetSpeed(type);
        float speedy = Constants.ProjectileType.GetSpeed(type) - speedx;

        if (t.getX() > e.getX()) {
            speedx *= -1;
        }
        if (t.getY() > e.getY()) {
            speedy *= -1;
        }
        float rotate = 0;
        if (type == ARROW) {
            float arcValue = (float) Math.atan(ydis / xdis);
            rotate = (float) Math.toDegrees(arcValue);

            if (xdis < 0) {
                rotate += 180;
            }

        }
        projectiles.add(new Projectile(t.getX() + 32, t.getY() + 32, speedx, speedy, t.getDmg(), rotate, projectilesID++, type));
    }

    public void update() {
        for (Projectile p : projectiles) {
            p.move();
            if (isProjHitingEnemy(p)) {
                p.setActive(false);
            } else {
                // do nothing
            }
        }
    }

    private boolean isProjHitingEnemy(Projectile p) {
        if (p.isActive()) {
            for (Enemy e : playing.getEnemyMenager().getEnemies()) {
                if (e.getBounds().contains(p.getPos())) {
                    e.hurt(p.getDmg());
                    return true;
                }
            }
        }
        return false;
    }

    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;


        for (Projectile p : projectiles) {
            if (p.isActive()) {
                if (p.getProjectileType() == ARROW) {
                    g2d.translate(p.getPos().x, p.getPos().y);
                    g2d.rotate(Math.toRadians(90));
                    g2d.drawImage(proj_img[p.getProjectileType()],
                            -32, -32, null);
                    g2d.rotate(-Math.toRadians(90));
                    g2d.translate(-p.getPos().x, -p.getPos().y);
                } else {
                    g2d.drawImage(proj_img[p.getProjectileType()],
                            (int)p.getPos().x-32, (int)p.getPos().y-32, null);
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
            case MAGE:
                return BEEM;
            case YES:
                return ELSE;
        }
        return 0;
    }

}
