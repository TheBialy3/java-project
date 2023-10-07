package managers;

import enemies.Enemy;
import helpz.Constants;
import helpz.LoadSave;
import objects.PathPoint;
import towers.*;
import scenes.Playing;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

import static helpz.Constants.TowerType.*;


public class TowerManager {

    private Playing playing;
    private BufferedImage[] towerImgs, upgradeImgs;

    private ArrayList<Tower> towers = new ArrayList<>();
    private ArrayList<Integer> arr = new ArrayList<>();
    private int towerAmount = 0, ran, tilePixelNumber = 64;
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
        upgradeImgs = new BufferedImage[4];
        for (int i = 0; i < 4; i++) {
            upgradeImgs[i] = atlas.getSubimage((0 + i) * tilePixelNumber, 29  * tilePixelNumber, tilePixelNumber, tilePixelNumber);
        }
    }

    private void loadTowerImages() {
        BufferedImage atlas = LoadSave.getSpriteAtlas();
        int towerNumber = 10;
        int imageInRow = 9;
        towerImgs = new BufferedImage[towerNumber];
        for (int i = 0; i < towerNumber; i++) {
            towerImgs[i] = atlas.getSubimage((0+i/9) * tilePixelNumber, (12 + i%9) * tilePixelNumber, tilePixelNumber, tilePixelNumber);
        }
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
            case MINES_FACTORY:
                towers.add(new MineFactory(x, y, towerAmount++, MINES_FACTORY, road));
                towers.get(towers.size() - 1).isRoadNextTot();
                break;
            case POISON_TOWER:
                towers.add(new PoisonTower(x, y, towerAmount++, POISON_TOWER, road));
                break;
            case BOOM_VOLCANO:
                towers.add(new BoomTower(x, y, towerAmount++, BOOM_VOLCANO, road));
                break;
            case CROSSBOW:
                towers.add(new Crossbow(x, y, towerAmount++, CROSSBOW, road));
                break;
            case MOUSE_FOLLOWS_TOWER:
                towers.add(new MauseFollowsTower(x, y, towerAmount++, MOUSE_FOLLOWS_TOWER, road));
                break;
            case SNIPER:
                towers.add(new Sniper(x, y, towerAmount++, SNIPER, road));
                break;
            case LASER_TOWER:
                towers.add(new LaserTower(x, y, towerAmount++, LASER_TOWER, road));
                break;
            default:
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
            if (t.getTowerType() == MINES_FACTORY) {
                setMine(t);
            } else if (t.getTowerType() == FROST_MAGE) {
                slowEnemyIfClose(t);
            } else if (t.getTowerType() == BOOM_VOLCANO) {
                if (t.isCooldownOver()) {
                    hurtAllEnemysIfClose(t);
                    t.resetCooldown();
                }
            } else {
                attackEnemyIfClose(t);
            }
        }
    }


    private void hurtAllEnemysIfClose(Tower t) {
        for (Enemy e : playing.getEnemyMenager().getEnemies()) {
            if (e.isAlive()) {
                if (isEnemyInRange(t, e)) {
                    e.hurt(getDefaultDmg(t.getTowerType()), getDmgType(t.getTowerType()));
                } else {
                    //nothing
                }
            }
        }
    }


    private void slowEnemyIfClose(Tower t) {
        for (Enemy e : playing.getEnemyMenager().getEnemies()) {
            if (e.isAlive()) {
                if (isEnemyInRange(t, e)) {
                    e.slow(Constants.TowerType.getPowerOfSlow(t.getTowerType()));
                } else {
                    //nothing
                }
            }
        }

    }


    private void attackEnemyIfClose(Tower t) {
        for (Enemy e : playing.getEnemyMenager().getEnemies()) {
            if (e.isAlive()) {
                if (isEnemyInRange(t, e)) {
                    if (t.isCooldownOver()) {
                        if (t.getTowerType() == SNIPER) {
                            e.hurt(t.getDmg(), getDmgType(t.getTowerType()));
                        } else if (t.getTowerType() == LASER_TOWER) {
                            e.hurt(t.getDmg(), getDmgType(t.getTowerType()));
                            playing.beemEnemy(t, e);
                        } else {
                            playing.shootEnemy(t, e);
                        }
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
                            PathPoint a2 = new PathPoint(t.getX(), t.getY() - 64);
                            e = a2;
                            break;
                        case 3:
                            PathPoint a3 = new PathPoint(t.getX() + 64, t.getY() - 64);
                            e = a3;
                            break;
                        case 4:
                            PathPoint a4 = new PathPoint(t.getX() - 64, t.getY());
                            e = a4;
                            break;
                        case 5:
                            PathPoint a5 = new PathPoint(t.getX() + 64, t.getY());
                            e = a5;
                            break;
                        case 6:
                            PathPoint a6 = new PathPoint(t.getX() - 64, t.getY() + 64);
                            e = a6;
                            break;
                        case 7:
                            PathPoint a7 = new PathPoint(t.getX(), t.getY() + 64);
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
            if (t.getTowerType() == FROST_MAGE) {
                g.setColor(new Color(0, 254, 255, 38));
                g.fillOval((int) (t.getX() - t.getRange()) + 32, (int) (t.getY() - t.getRange()) + 32, (int) (t.getRange() * 2), (int) (t.getRange()) * 2);
            }
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
