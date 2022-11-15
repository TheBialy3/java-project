package managers;

import helpz.LoadSave;
import objects.Tower;
import scenes.Playing;

import java.awt.*;
import java.awt.image.BufferedImage;

import static helpz.Constants.TowerType.*;

public class TowerManager {

    private Playing playing;
    private BufferedImage[] towerImgs;
    private Tower tower;

    public TowerManager(Playing playing) {
        this.playing = playing;
        loadTowerImages();
        initTower();
    }

    private void initTower() {
        tower = new Tower(0*64,0*64,0,ARCHER);
    }

    private void loadTowerImages() {
        BufferedImage atlas = LoadSave.getSpriteAtlas();
        towerImgs = new BufferedImage[4];
        towerImgs[0] = atlas.getSubimage(1 * 64, 3 * 64, 64, 64);
        towerImgs[1] = atlas.getSubimage(5 * 64, 1 * 64, 64, 64);
        towerImgs[2] = atlas.getSubimage(3 * 64, 3 * 64, 64, 64);
        towerImgs[3] = atlas.getSubimage(4 * 64, 0 * 64, 64, 64);
    }

    public void draw(Graphics g){
        g.drawImage(towerImgs[0],tower.getX(),tower.getY(),null);
    }

    public void update() {
    }
}
