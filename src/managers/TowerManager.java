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

import static helpz.Constants.EnemyType.*;
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
    private boolean Card14 = false, Card15 = false, Card17 = false, Card18 = false, Card19 = false, Card20 = false, Card21 = false;
    private boolean Card23 = false, Card24 = false, Card26 = false, Card27 = false, Card28 = false, Card29 = false, Card30 = false;
    private boolean Card31 = false, Card32 = false, Card34 = false, Card35 = false, Card37 = false, Card38 = false;
    private boolean Card39 = false, Card40 = false, Card41 = false, Card42 = false;


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
                if (Card20) {
                    hurtAllEnemyIfClose(t, 100);
                }
            } else if (t.getTowerType() == BOOM_VOLCANO) {
                boomTowerAtack(t);
            } else {
                attackEnemyIfClose(t);
            }
        }
    }

    private void boomTowerAtack(Tower t) {
        if (t.isCoolDownOver()) {
            hurtAllEnemyIfClose(t, 100);
            t.resetCoolDown();
        }
        if (Card28) {
            if (t.isSomeCoolDownOver()) {
                hurtAllEnemyIfClose(t, 50);
            }
        }
    }


    private void hurtAllEnemyIfClose(Tower t, int percent) {
        try {
            for (Enemy e : playing.getEnemyManager().getEnemies()) {
                if (e.isAlive()) {
                    if (isEnemyInRange(t, e)) {
                        e.hurt(t.getDmg() * percent / 100, getDmgType(t.getTowerType()));
                    } else {
                        //nothing
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("ConcurrentModificationException hurtAllEnemyIfClose");
        }
    }


    private void slowEnemyIfClose(Tower t) {
        try {
            for (Enemy e : playing.getEnemyManager().getEnemies()) {
                if (e.isAlive()) {
                    if (isEnemyInRange(t, e)) {
                        float slow = t.getSlow();
                        if (isCard19()) {
                            slow = 0.7f;
                        }
                        e.slow(slow);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("ConcurrentModificationException slowEnemyIfClose");
        }

    }


    private void attackEnemyIfClose(Tower t) {
        try {
            for (Enemy e : playing.getEnemyManager().getEnemies()) {
                if (t.isCoolDownOver()) {
                    if (e.isAlive()) {
                        if (t.getTowerType() == SNIPER) {
                            e.hurt(t.getDmg(), getDmgType(t.getTowerType()));
                            if (Card39) {
                                attackEnemyIfClose(t, e);
                            }
                            t.resetCoolDown();
                        } else if (isTowerTargetingEnemy(t, e)) {
                            if (isEnemyInRange(t, e)) {
                                if (t.getTowerType() == LASER_TOWER) {
                                    e.hurt(t.getDmg(), getDmgType(t.getTowerType()));
                                    if (Card40) {
                                        attackEnemyIfClose(t, e);
                                    }
                                    playing.beamEnemy(t, e);
                                } else {
                                    playing.shootEnemy(t, e);
                                }
                                if (Constants.TowerType.isSlow(t.getTowerType())) {
                                    float slow = t.getSlow();
                                    e.slow(slow);
                                }
                                t.resetCoolDown();
                            }
                        }
                    }
                }
            }

        } catch (Exception e) {
            System.out.println("ConcurrentModificationException attackEnemyIfClose");
        }
    }


    private void attackEnemyIfClose(Tower t, Enemy hurted) {
        for (Enemy e : playing.getEnemyManager().getEnemies()) {
            if (e.isAlive()) {
                if (t.getTowerType() == SNIPER) {
                    if (hurted != e) {
                        e.hurt(t.getDmg(), getDmgType(t.getTowerType()));
                        return;
                    }
                }
                if (isEnemyInRange(t, e)) {
                    if (t.getTowerType() == LASER_TOWER) {
                        if (hurted != e) {
                            e.hurt(t.getDmg(), getDmgType(t.getTowerType()));
                            playing.beamEnemy(t, e);
                            return;
                        }
                    }
                }

            }
        }
    }


    private boolean isTowerTargetingEnemy(Tower t, Enemy e) {
        if (Card12) {
            return true;
        }
        if (getTargetMoveType(t.getTowerType()) == BOTH) {
            return true;
        }
        if (getTargetMoveType(t.getTowerType()) == getMoveType(e.getEnemyType())) {
            return true;
        }
        return false;
    }

    private void setMine(Tower t) {
        int a = t.arrSize();
        if (a != 0) {
            if (!playing.isAllEnemyDead()) {
                if (t.isCoolDownOver()) {
                    int mineNumber = 3;
                    if (Card23) {
                        mineNumber *= 2;
                    }
                    for (int i = mineNumber; i > 0; i--) {
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
            if (t.getTowerType() == BOOM_VOLCANO) {
                if (t.getCdTick() <= 4) {
                    g.setColor(new Color(255, 0, 0, 38));
                    g.fillOval((int) (t.getX() - t.getRange()) + 32, (int) (t.getY() - t.getRange()) + 32, (int) (t.getRange() * 2), (int) (t.getRange()) * 2);
                }
                if (Card28) {
                    if (t.getCdTick() <= 11 && t.getCdTick() > 7) {
                        g.setColor(new Color(255, 0, 0, 38));
                        g.fillOval((int) (t.getX() - t.getRange()) + 32, (int) (t.getY() - t.getRange()) + 32, (int) (t.getRange() * 2), (int) (t.getRange()) * 2);
                    }
                }
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

    public void setCard14(boolean card14) {
        Card14 = card14;
    }

    public void setCard15(boolean card15) {
        Card15 = card15;
    }

    public void setCard17(boolean card17) {
        Card17 = card17;
    }

    public void setCard18(boolean card18) {
        Card18 = card18;
    }

    public void setCard19(boolean card19) {
        Card19 = card19;
    }

    public void setCard20(boolean card20) {
        Card20 = card20;
    }

    public void setCard21(boolean card21) {
        Card21 = card21;
    }

    public void setCard23(boolean card23) {
        Card23 = card23;
    }

    public void setCard24(boolean card24) {
        Card24 = card24;
    }

    public void setCard26(boolean card26) {
        Card26 = card26;
    }

    public void setCard27(boolean card27) {
        Card27 = card27;
    }

    public void setCard28(boolean card28) {
        Card28 = card28;
    }

    public void setCard29(boolean card29) {
        Card29 = card29;
    }

    public void setCard30(boolean card30) {
        Card30 = card30;
    }

    public void setCard31(boolean card31) {
        Card31 = card31;
    }

    public void setCard32(boolean card32) {
        Card32 = card32;
    }

    public void setCard34(boolean card34) {
        Card34 = card34;
    }

    public void setCard35(boolean card35) {
        Card35 = card35;
    }

    public void setCard37(boolean card37) {
        Card37 = card37;
    }

    public void setCard38(boolean card38) {
        Card38 = card38;
    }

    public void setCard39(boolean card39) {
        Card39 = card39;
    }

    public void setCard40(boolean card40) {
        Card40 = card40;
    }

    public void setCard41(boolean card41) {
        Card41 = card41;
    }

    public void setCard42(boolean card42) {
        Card42 = card42;
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

    public boolean isCard14() {
        return Card14;
    }

    public boolean isCard15() {
        return Card15;
    }

    public boolean isCard17() {
        return Card17;
    }

    public boolean isCard18() {
        return Card18;
    }

    public boolean isCard19() {
        return Card19;
    }

    public boolean isCard20() {
        return Card20;
    }

    public boolean isCard21() {
        return Card21;
    }

    public boolean isCard24() {
        return Card24;
    }

    public boolean isCard26() {
        return Card26;
    }

    public boolean isCard27() {
        return Card27;
    }

    public boolean isCard29() {
        return Card29;
    }

    public boolean isCard30() {
        return Card30;
    }

    public boolean isCard31() {
        return Card31;
    }

    public boolean isCard32() {
        return Card32;
    }

    public boolean isCard34() {
        return Card34;
    }

    public boolean isCard35() {
        return Card35;
    }

    public boolean isCard37() {
        return Card37;
    }

    public boolean isCard38() {
        return Card38;
    }

    public boolean isCard41() {
        return Card41;
    }

    public boolean isCard42() {
        return Card42;
    }

    public void slowChange(int percent, int towerType) {
        float slow = 0;
        for (Tower t : towers) {
            if (t.getTowerType() == towerType) {
                slow = t.getSlow();
                t.slowSet(slow * percent / 100);
            }
        }
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

    public void damageUp(int percent, int towerType) {
        int dmg = 0;
        for (Tower t : towers) {
            if (t.getTowerType() == towerType) {
                dmg = t.getDmg();
                if (dmg < 10) {
                    t.setDmg(dmg + percent / 10);
                } else {
                    dmg = dmg + dmg * percent / 100;
                    t.setDmg(dmg);
                }
            }
        }
    }

    public void setDamage(int dmg, int towerType) {
        for (Tower t : towers) {
            if (t.getTowerType() == towerType) {
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
                    t.setDmg(dmg + percent / 10);
                } else {
                    t.setDmg(dmg + dmg * percent / 100);
                }
            } else {
                coolDown = coolDown * percent / 100;
                t.reduceCoolDown(coolDown);
            }
        }
    }

    public void speedUp(int percent, int towerType) {
        float coolDown = 0;
        for (Tower t : towers) {
            coolDown = t.getCoolDown();
            if (t.getTowerType() == towerType) {
                if (coolDown < 10) {
                    int dmg = t.getDmg();
                    if (dmg < 10) {
                        t.setDmg(dmg + percent / 10);
                    } else {
                        t.setDmg(dmg + dmg * percent / 100);
                    }
                } else {
                    coolDown = coolDown * percent / 100;
                    t.reduceCoolDown(coolDown);
                }
            }
        }
    }

    public void rangeUp(int percent) {
        float range = 0;
        for (Tower t : towers) {
            range = t.getRange();
            range = range + range * percent / 100;
            t.setRange(range);

        }
    }

    public void rangeUp(int percent, int towerType) {
        float range = 0;
        for (Tower t : towers) {
            if (t.getTowerType() == towerType) {
                range = t.getRange();
                range = range + range * percent / 100;
                t.setRange(range);
            }
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
            if (Constants.TowerType.isDOT(t.getTowerType())) {
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

    public void durationUp(int percent, int towerType) {
        int duration = 0;
        for (Tower t : towers) {
            if (Constants.TowerType.isDOT(t.getTowerType())) {
                if (t.getTowerType() == towerType) {
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
}
