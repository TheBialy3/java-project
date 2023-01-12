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
    private BufferedImage[] towerImgs,upgradeImgs;

    private ArrayList<Tower> towers = new ArrayList<>();
    private ArrayList<Integer> arr = new ArrayList<>();
    private int towerAmount = 0, ran;
    private Random random = new Random();
    private PathPoint e;
    private int[][] road;


    public TowerManager(Playing playing) {
        this.playing = playing;
        loadTowerImages();
        loadUpgradeImgs();
    }

    private void loadUpgradeImgs() {
        BufferedImage atlas = LoadSave.getSpriteAtlas();
        upgradeImgs = new BufferedImage[3];
        upgradeImgs[0] = atlas.getSubimage(9 * 64, 0 * 64, 64, 64);
        upgradeImgs[1] = atlas.getSubimage(9 * 64, 1 * 64, 64, 64);
        upgradeImgs[2] = atlas.getSubimage(9 * 64, 2 * 64, 64, 64);
        //upgradeImgs[3] = atlas.getSubimage(4 * 64, 0 * 64, 64, 64);
    }

    private void loadTowerImages() {
        BufferedImage atlas = LoadSave.getSpriteAtlas();
        towerImgs = new BufferedImage[4];
        towerImgs[0] = atlas.getSubimage(7 * 64, 2 * 64, 64, 64);
        towerImgs[1] = atlas.getSubimage(8 * 64, 2 * 64, 64, 64);
        towerImgs[2] = atlas.getSubimage(0 * 64, 7 * 64, 64, 64);
        towerImgs[3] = atlas.getSubimage(4 * 64, 0 * 64, 64, 64);
        road = playing.getRoadDirArr();
    }

    public void addTower(Tower selectedTower, int x, int y) {
        switch (selectedTower.getTowerType()) {
            case ARCHER:
                towers.add(new Archer(x, y, towerAmount++, ARCHER, road));
                break;
            case CANNON:
                towers.add(new Cannon(x, y, towerAmount++, CANNON, road));
                break;
            case FROST_MAGE:
                towers.add(new FrostMage(x, y, towerAmount++, FROST_MAGE, road));
                break;
            case MINE_FACTORY:
                towers.add(new MineFactory(x, y, towerAmount++, MINE_FACTORY, road));
                towers.get(towers.size()-1).isRoadNextTot();
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

    public void roadNextTo(Tower displayedTower) {
        for (int i = 0; i < towers.size(); i++) {
            if (towers.get(i).getId() == displayedTower.getId()) {
                towers.get(i).isRoadNextTot();
            }
        }
    }

    public void update() {
        for (Tower t : towers) {
            t.update();
            if (t.getTowerType() == MINE_FACTORY) {
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
        int a = t.arrSize();
        if (a != 0) {
            if (!playing.isAllEnemysDead()) {
                if (t.isCooldownOver()) {
                    arr = t.getArr();
                    ran = random.nextInt(a);
                    switch (arr.get(ran)) {
                        case 1:
                            PathPoint a1 = new PathPoint(t.getX() - 64, t.getY() - 64);
                            e = a1;
                            break;
                        case 2:
                            PathPoint a2 = new PathPoint(t.getX() , t.getY()- 64);
                            e = a2;
                            break;
                        case 3:
                            PathPoint a3 = new PathPoint(t.getX() + 64, t.getY() - 64);
                            e = a3;
                            break;
                        case 4:
                            PathPoint a4 = new PathPoint(t.getX()- 64, t.getY() );
                            e = a4;
                            break;
                        case 5:
                            PathPoint a5 = new PathPoint(t.getX()+ 64, t.getY() );
                            e = a5;
                            break;
                        case 6:
                            PathPoint a6 = new PathPoint(t.getX() - 64, t.getY() + 64);
                            e = a6;
                            break;
                        case 7:
                            PathPoint a7 = new PathPoint(t.getX() , t.getY()+ 64);
                            e = a7;
                            break;
                        case 8:
                            PathPoint a8 = new PathPoint(t.getX() + 64, t.getY() + 64);
                            e = a8;
                            break;
                    }

                    playing.setMine(t, e);
                    t.resetCooldown();
                }
            }
        } else {
            System.out.print(a);
        }
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

    public BufferedImage[] getUpgradeImgs() {
        return upgradeImgs;
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
