package managers;

import enemies.Enemy;
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
    }

    public void update() {

    }

    public void draw(Graphics g) {

    }

    private int getProjType(Tower t) {
        switch (t.getTowerType()){
            case ARCHER:
                return ARROW;
                break;
            case CANNON:
                return BOMB;
                break;
            case MAGE:
                return BEEM;
                break;
            case YES:
                return ELSE;
                break;

            return 0;
        }
    }

}
