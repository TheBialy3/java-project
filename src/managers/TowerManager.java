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
    private BufferedImage[] towerImg, upgradeImg;

    private ArrayList<Tower> towers = new ArrayList<>();
    private ArrayList<Integer> arr = new ArrayList<>();
    private int towerAmount = 0, ran, tilePixelNumber = 64;
    private Random random = new Random();
    private PathPoint e;
    private int[][] road;

    private boolean Card0 = false, Card1 = false, Card2 = false, Card3 = false, Card4 = false, Card5 = false, Card6 = false, Card12 = false;


    public TowerManager(Playing playing) {
        this.playing = playing;
        loadTowerImages();
        loadUpgradeImg();
    }

    private void loadUpgradeImg() {
        BufferedImage atlas = LoadSave.getSpriteAtlas();
        upgradeImg = new BufferedImage[4];
        for (int i = 0; i < 4; i++) {
            upgradeImg[i] = atlas.getSubimage((0 + i) * tilePixelNumber, 29 * tilePixelNumber, tilePixelNumber, tilePixelNumber);
        }
    }

    private void loadTowerImages() {
        BufferedImage atlas = LoadSave.getSpriteAtlas();
        int towerNumber = 10;
        int imageInRow = 9;
        towerImg = new BufferedImage[towerNumber];
        for (int i = 0; i < towerNumber; i++) {
            towerImg[i] = atlas.getSubimage((0 + i / imageInRow) * tilePixelNumber, (12 + i % imageInRow) * tilePixelNumber, tilePixelNumber, tilePixelNumber);
        }
        road = playing.getRoadDirArr();
    }

    public void addTower(Tower selectedTower, int x, int y) {
        switch (selectedTower.getTowerType()) {
            case ARCHER:
                towers.add(new Archer(x, y, towerAmount++, ARCHER, this, road));
                break;
            case CANNON:
                towers.add(new Cannon(x, y, towerAmount++, CANNON, this, road));
                break;
            case FROST_MAGE:
                towers.add(new FrostMage(x, y, towerAmount++, FROST_MAGE, this, road));
                break;
            case MINES_FACTORY:
                towers.add(new MineFactory(x, y, towerAmount++, MINES_FACTORY, this, road));
                towers.get(towers.size() - 1).isRoadNextTot();
                break;
            case POISON_TOWER:
                towers.add(new PoisonTower(x, y, towerAmount++, POISON_TOWER, this, road));
                break;
            case BOOM_VOLCANO:
                towers.add(new BoomTower(x, y, towerAmount++, BOOM_VOLCANO, this, road));
                break;
            case CROSSBOW:
                towers.add(new Crossbow(x, y, towerAmount++, CROSSBOW, this, road));
                break;
            case MOUSE_FOLLOWS_TOWER:
                towers.add(new MauseFollowsTower(x, y, towerAmount++, MOUSE_FOLLOWS_TOWER, this, road));
                break;
            case SNIPER:
                towers.add(new Sniper(x, y, towerAmount++, SNIPER, this, road));
                break;
            case LASER_TOWER:
                towers.add(new LaserTower(x, y, towerAmount++, LASER_TOWER, this, road));
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
                if (t.isCoolDownOver()) {
                    hurtAllEnemyIfClose(t);
                    t.resetCoolDown();
                }
            } else {
                attackEnemyIfClose(t);
            }
        }
    }


    private void hurtAllEnemyIfClose(Tower t) {
        for (Enemy e : playing.getEnemyManager().getEnemies()) {
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
        for (Enemy e : playing.getEnemyManager().getEnemies()) {
            if (e.isAlive()) {
                if (isEnemyInRange(t, e)) {
                    e.slow(Constants.TowerType.getPowerOfSlow(t.getTowerType()));
                }
            }
        }

    }


    private void attackEnemyIfClose(Tower t) {
        try {
            for (Enemy e : playing.getEnemyManager().getEnemies()) {
                if (e.isAlive()) {
                    if (isEnemyInRange(t, e)) {
                        if (t.isCoolDownOver()) {
                            if (t.getTowerType() == SNIPER) {
                                e.hurt(t.getDmg(), getDmgType(t.getTowerType()));
                            } else if (t.getTowerType() == LASER_TOWER) {
                                e.hurt(t.getDmg(), getDmgType(t.getTowerType()));
                                playing.beamEnemy(t, e);
                            } else {
                                playing.shootEnemy(t, e);
                            }
                            t.resetCoolDown();
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("ConcurrentModificationException");
        }
    }

    private void setMine(Tower t) {
        int a = t.arrSize();
        if (a != 0) {
            if (!playing.isAllEnemyDead()) {
                if (t.isCoolDownOver()) {
                    for (int mineNumber = 2; mineNumber >= 0; mineNumber--) {
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
                    }
                    t.resetCoolDown();
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
            g.drawImage(towerImg[t.getTowerType()], t.getX(), t.getY(), null);
            if (t.getTowerType() == FROST_MAGE) {
                g.setColor(new Color(0, 254, 255, 38));
                g.fillOval((int) (t.getX() - t.getRange()) + 32, (int) (t.getY() - t.getRange()) + 32, (int) (t.getRange() * 2), (int) (t.getRange()) * 2);
            }
        }
    }

    public BufferedImage[] getTowerImg() {
        return towerImg;
    }

    public BufferedImage[] getUpgradeImg() {
        return upgradeImg;
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

    public void setCard0(boolean card0) {
        Card0 = card0;
    }

    public void setCard1(boolean card1) {
        Card1 = card1;
    }

    public void setCard2(boolean card2) {
        Card2 = card2;
    }

    public void setCard3(boolean card3) {
        Card3 = card3;
    }

    public void setCard4(boolean card4) {
        Card4 = card4;
    }

    public void setCard5(boolean card5) {
        Card5 = card5;
    }

    public void setCard6(boolean card6) {
        Card6 = card6;
    }

    public void setCard12(boolean card12) {
        Card12 = card12;
    }

    public boolean isCard0() {
        return Card0;
    }

    public boolean isCard1() {
        return Card1;
    }

    public boolean isCard2() {
        return Card2;
    }

    public boolean isCard3() {
        return Card3;
    }

    public boolean isCard4() {
        return Card4;
    }

    public boolean isCard5() {
        return Card5;
    }

    public boolean isCard6() {
        return Card6;
    }

    public boolean isCard12() {
        return Card12;
    }



    public void damageUp(int percent) {
        int dmg = 0;
        for (Tower t : towers) {
            dmg = t.getDmg();
            if (dmg < 10) {
                t.setDmg(dmg + percent / 10);
            } else {
                dmg = dmg + dmg * percent / 100;
                t.setDmg(dmg);
            }
        }
    }

    public void speedUp(int percent) {
        float coolDown = 0;
        for (Tower t : towers) {
            coolDown = t.getCoolDown();
            if (coolDown < 10) {
                int dmg = t.getDmg();
                if (dmg < 10) {
                    t.setDmg(dmg+  percent / 10);
                } else {
                    t.setDmg(dmg + dmg * percent / 100);
                }
            } else {
                coolDown = coolDown * percent / 100;
                t.reduceCoolDown(coolDown);
            }
        }
    }

    public void rangeUp(int percent) {
        float range = 0;
        for (Tower t : towers) {
            range = t.getRange();
            range =range+ range * percent / 100;
                t.setRange(range);

        }
    }

    public void upgradesTrue() {
        for (Tower t : towers) {
            t.isUpgrade1Active();
            t.isUpgrade2Active();
            t.isUpgrade3Active();
        }
    }

    public void durationUp(int percent) {
        int duration = 0;
        for (Tower t : towers) {
            if(Constants.TowerType.isDOT(t.getTowerType())){
                duration = t.getDuration();
                if (duration < 10) {
                    t.setDmg(duration + percent / 10);
                } else {
                    duration = duration + duration * percent / 100;
                    t.setDmg(duration);
                }
            }
        }
    }
}
