package managers;

import helpz.LoadSave;
import objects.Tower;
import scenes.Playing;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static helpz.Constants.TowerType.*;

public class TowerManager {

    private Playing playing;
    private BufferedImage[] towerImgs;
    private ArrayList<Tower> towers = new ArrayList<>();
    private int towerAmount = 0;

    public TowerManager(Playing playing) {
        this.playing = playing;
        loadTowerImages();

    }


    private void loadTowerImages() {
        BufferedImage atlas = LoadSave.getSpriteAtlas();
        towerImgs = new BufferedImage[4];
        towerImgs[0] = atlas.getSubimage(1 * 64, 3 * 64, 64, 64);
        towerImgs[1] = atlas.getSubimage(5 * 64, 1 * 64, 64, 64);
        towerImgs[2] = atlas.getSubimage(3 * 64, 3 * 64, 64, 64);
        towerImgs[3] = atlas.getSubimage(4 * 64, 0 * 64, 64, 64);
    }

    public void addTower(Tower selectedTower, int xPos, int yPos) {
        towers.add(new Tower(xPos, yPos, towerAmount++, selectedTower.getTowerType()));
    }

    public void update() {
    }

    public void draw(Graphics g) {
        for (Tower t : towers) {
            g.drawImage(towerImgs[t.getTowerType()], t.getX(), t.getY(), null);
        }

    }

    public BufferedImage[] getTowerImgs() {
        return towerImgs;
    }

    public Tower getTowerAt(int x, int y) {
        for (Tower t : towers) {
            if (t.getX() == x) {
                if (t.getY() == y) {
                    return t;
                }
            }
        }
        return null;
    }
}
