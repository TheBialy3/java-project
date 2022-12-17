package managers;

import enemies.Enemy;
import helpz.LoadSave;
import objects.PathPoint;
import towers.*;
import scenes.Playing;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

import static helpz.Constants.TowerType.*;
import static helpz.Constants.TowerType.MINE_FACTORY;

public class TowerManager {

    private Playing playing;
    private BufferedImage[] towerImgs;
    private ArrayList<Tower> towers = new ArrayList<>();
    private int towerAmount = 0;
    private Random rand;
    private int[][] road;

    public TowerManager(Playing playing) {
        this.playing = playing;
        loadTowerImages();

    }

    private void loadTowerImages() {
        BufferedImage atlas = LoadSave.getSpriteAtlas();
        towerImgs = new BufferedImage[4];
        towerImgs[0] = atlas.getSubimage(7 * 64, 2 * 64, 64, 64);
        towerImgs[1] = atlas.getSubimage(8 * 64, 2 * 64, 64, 64);
        towerImgs[2] = atlas.getSubimage(0 * 64, 7 * 64, 64, 64);
        towerImgs[3] = atlas.getSubimage(4 * 64, 0 * 64, 64, 64);
        road=playing.getRoadDirArr();
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                System.out.print(road[i][j]);
            }System.out.println();
        }
    }

    public void addTower(Tower selectedTower, int x, int y) {
        switch (selectedTower.getTowerType()) {
            case ARCHER:
                towers.add(new Archer(x, y, towerAmount++, ARCHER));
                break;
            case CANNON:
                towers.add(new Cannon(x, y, towerAmount++,CANNON));
                break;
            case FROST_MAGE:
                towers.add(new FrostMage(x, y, towerAmount++, FROST_MAGE));
                break;
            case MINE_FACTORY:
                towers.add(new MineFactory(x, y, towerAmount++, MINE_FACTORY));
                break;
        }
    }

    public void removeTower(Tower displayedTower) {
        for (int i = 0; i < towers.size(); i++) {
            if (towers.get(i).getId() == displayedTower.getId()) {
                towers.remove(i);
            }
        }
    }

    public void update() {
        for (Tower t : towers) {
            t.update();
            if (t.getTowerType() == 3) {
                setMine(t);
            } else {
                attackEnemyIfClose(t);
            }
        }
    }

    private void attackEnemyIfClose(Tower t) {
        for (Enemy e : playing.getEnemyMenager().getEnemies()) {
            if (e.isAlive()) {
                if (isEnemyInRange(t, e)) {
                    if (t.isCooldownOver()) {
                        playing.shootEnemy(t, e);
                        t.resetCooldown();
                    }
                } else {
                    //nothing
                }
            }
        }
    }

    private void setMine(Tower t) {
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
             //   if(road[i][j])
               // PathPoint e = new PathPoint(j, i);
           //     if (isRoadInRange(t, e)) {
                    if (t.isCooldownOver()) {
                        int l = rand.nextInt(1000);
             //           playing.setMine(t, e);
                        t.resetCooldown();
                    }
         //       } else {
                    //nothing
                }
            }
        }
  //  }

    private boolean isRoadInRange(Tower t, PathPoint p) {
        int range = helpz.Utilz.GetHypoDistance(t.getX(), t.getY(), p.getxCord(), p.getyCord());
        return range < t.getRange();
    }

    private boolean isEnemyInRange(Tower t, Enemy e) {
        int range = helpz.Utilz.GetHypoDistance(t.getX(), t.getY(), e.getX(), e.getY());
        return range < t.getRange();
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

    public void reset() {
        towers.clear();
        towerAmount = 0;
    }

}
