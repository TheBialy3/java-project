package towers;

import helpz.Constants;
import managers.TowerManager;

import java.util.ArrayList;

public abstract class Tower {

    private int tickSlow=0, tickStun=0, cdTick, standardCD;
    private int x, y, id, towerType, dmg, worthGold, duration;
    private float range, coolDown, slow;
    private ArrayList<Integer> arr = new ArrayList<>();
    private int[][] road;
    private boolean stun= false,attackSlow= false;
    private boolean Upgrade1 = false, Upgrade2 = false, Upgrade3 = false;
    private TowerManager towerManager;


    public Tower(int x, int y, int id, int towerType, TowerManager towerManager, int[][] road) {
        this.x = x;
        this.y = y;
        this.id = id;
        this.towerType = towerType;
        this.towerManager = towerManager;
        setDefaultDmg();
        setDefaultRange();
        setDefaultCoolDown();
        this.worthGold = Constants.TowerType.getCost(towerType);
        this.road = road;
        setDefaultSlow();
        setDefaultDuration();
        if (this.towerManager.isCard3()) {
            Upgrade1Activate();
            Upgrade2Activate();
            Upgrade3Activate();
        }
    }

    public void update() {
        if(!stun){
        cdTick++;
        }
        if(tickStun>0){
            tickStun--;
        }else if(tickStun<=0){
           stun=false;
        }

        if(tickSlow>0){
            tickSlow--;
        }else if(tickSlow<=0){
            attackSlow=false;
            setStandardCD();
        }
    }



    private void setStandardCD() {
        coolDown=standardCD;
    }

    public void setStun(float time){
        if(tickStun<(int)time*60){
        tickStun=(int)time*60;
        stun=true;}
    }
//    public void setSlow(float time,int percent){
//
//            tickSlow = (int) time * 60;
//            setBiggerCD(percent);
//            attackSlow = true;
//
//    }
    public void setBiggerCD(int percent){

    }
    public int getWorthGold() {
        return worthGold;
    }

    public float getSlow() {
        return slow;
    }

    public void updateTowerWorthGold(int worthGold) {
        this.worthGold += worthGold;
    }

    public boolean isCoolDownOver() {
        return cdTick >= coolDown;
    }

    public int getCdTick() {
        return cdTick;
    }

    public boolean isSomeCoolDownOver() {
        return cdTick == 7;
    }

    public boolean isStun() {
        return stun;
    }

    public void resetCoolDown() {
        cdTick = 0;
    }

    private void setDefaultSlow() {
        if (Constants.TowerType.isSlow(towerType)) {
            slow = Constants.TowerType.getPowerOfSlow(towerType);
            if (towerManager.isCard19()) {
                if (this instanceof FrostMage) {
                    slow = slow * 160 / 100;
                }
            }
            if (towerManager.isCard21()) {
                if (this instanceof FrostMage) {
                    slow = slow * 63 / 100;
                }
            }
            slowSet(slow);
        }
    }

    public void slowSet(float slowSet) {
        slow = slowSet;
    }


    private void setDefaultDuration() {
        if (Constants.TowerType.isDOT(towerType)) {
            duration = Constants.TowerType.getDuration(towerType);
            if (towerManager.isCard6()) {
                DurationUp(10);
            }
            if (towerManager.isCard27()) {
                if (this instanceof PoisonTower) {
                    DurationUp(30);
                }
            }
        }
    }

    public void DurationUp(int percent) {
        duration = duration + duration * percent / 100;
    }


    private void setDefaultCoolDown() {
        coolDown = Constants.TowerType.getDefaultCoolDown(towerType);
        if (towerManager.isCard1()) {
            CoolDown(10);
        }
        if (towerManager.isCard4()) {
            CoolDown(10);
        }
        if (towerManager.isCard14()) {
            if (this instanceof Archer) {
                CoolDown(30);
            }
        }
        if (towerManager.isCard17()) {
            if (this instanceof Cannon) {
                CoolDown(30);
            }
        }
        if (towerManager.isCard29()) {
            if (this instanceof BoomTower) {
                CoolDown(30);
            }
        }
        if (towerManager.isCard32()) {
            if (this instanceof Crossbow) {
                CoolDown(30);
            }
        }
        if (towerManager.isCard34()) {
            if (this instanceof MauseFollowsTower) {
                CoolDown(30);
            }
        }
        if (towerManager.isCard38()) {
            if (this instanceof Sniper) {
                CoolDown(30);
            }
        }
        standardCD=(int) coolDown;
    }

    private void CoolDown(int percent) {
        if (coolDown < 10) {
            dmg = (dmg + percent / 10);
        } else {
            coolDown = coolDown - coolDown * percent / 100;
        }
    }

    private void setDefaultRange() {
        range = Constants.TowerType.getDefaultRange(towerType);
        if (towerManager.isCard2()) {
            RangeUp(10);
        }
        if (towerManager.isCard19()) {
            if (this instanceof FrostMage) {
                RangeUp(3000);
            }
        }
        if (towerManager.isCard41()) {
            if (this instanceof LaserTower) {
                RangeUp(20);
            }
        }
    }

    private void RangeUp(int percent) {
        range = range + range * percent / 100;
    }

    private void setDefaultDmg() {
        dmg = Constants.TowerType.getDefaultDmg(towerType);
        if (towerManager.isCard20()) {//to add more dmg it go 1st
            if (this instanceof FrostMage) {
                dmg = 1;
            }
        }
        if (towerManager.isCard0()) {
            damageUp(10);
        }
        if (towerManager.isCard5()) {
            damageUp(10);
        }
        if (towerManager.isCard15()) {
            if (this instanceof Archer) {
                damageUp(30);
            }
        }
        if (towerManager.isCard18()) {
            if (this instanceof Cannon) {
                damageUp(30);
            }
        }
        if (towerManager.isCard24()) {
            if (this instanceof MineFactory) {
                damageUp(30);
            }
        }
        if (towerManager.isCard26()) {
            if (this instanceof PoisonTower) {
                damageUp(100);
            }
        }
        if (towerManager.isCard30()) {
            if (this instanceof BoomTower) {
                damageUp(30);
            }
        }
        if (towerManager.isCard31()) {
            if (this instanceof Crossbow) {
                damageUp(30);
            }
        }
        if (towerManager.isCard35()) {
            if (this instanceof MauseFollowsTower) {
                damageUp(30);
            }
        }
        if (towerManager.isCard37()) {
            if (this instanceof Sniper) {
                damageUp(30);
            }
        }
        if (towerManager.isCard42()) {
            if (this instanceof LaserTower) {
                damageUp(30);
            }
        }
    }

    public void damageUp(int percent) {
        if (dmg < 10) {
            dmg = dmg + percent / 10;
        } else {
            dmg = dmg + dmg * percent / 100;
        }
    }


    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTowerType() {
        return towerType;
    }

    public void isRoadNextTot() {
        try {
            if (road[y / 64 - 1][x / 64 - 1] != 17) {
                arr.add(1);
            }
        } catch (Exception e) {
            System.out.println("Out of bound 1");
        }
        try {
            if (road[y / 64 - 1][x / 64] != 17) {
                arr.add(2);
            }
        } catch (Exception e) {
            System.out.println("Out of bound 2");
        }
        try {
            if (road[y / 64 - 1][x / 64 + 1] != 17) {
                arr.add(3);
            }
        } catch (Exception e) {
            System.out.println("Out of bound 3");
        }
        try {
            if (road[y / 64][x / 64 - 1] != 17) {
                arr.add(4);
            }
        } catch (Exception e) {
            System.out.println("Out of bound 4");
        }
        try {
            if (road[y / 64][x / 64 + 1] != 17) {
                arr.add(5);
            }
        } catch (Exception e) {
            System.out.println("Out of bound 5");
        }
        try {
            if (road[y / 64 + 1][x / 64 - 1] != 17) {
                arr.add(6);
            }
        } catch (Exception e) {
            System.out.println("Out of bound 6");
        }
        try {
            if (road[y / 64 + 1][x / 64] != 17) {
                arr.add(7);
            }
        } catch (Exception e) {
            System.out.println("Out of bound 7");
        }
        try {
            if (road[y / 64 + 1][x / 64 + 1] != 17) {
                arr.add(8);
            }
        } catch (Exception e) {
            System.out.println("Out of bound 8");
        }

    }

    public ArrayList<Integer> getArr() {
        return arr;
    }

    public int arrSize() {
        return arr.size();
    }

    public int getDmg() {
        return dmg;
    }

    public void setDmg(int dmg) {
        this.dmg = dmg;
    }

    public float getRange() {
        return range;
    }

    public void setRange(float range) {
        this.range = range;
    }

    public float getCoolDown() {
        return coolDown;
    }

    public int getDuration() {
        return duration;
    }

    public void addDuration(int duration) {
        this.duration += duration;
    }

    public void reduceCoolDown(float cooldown) {
        this.coolDown -= cooldown;
    }

    public void addRange(float range) {
        this.range += range;
    }

    public void addDmg(int dmg) {
        this.dmg += dmg;
    }

    public boolean isUpgrade1Active() {
        return Upgrade1;
    }

    public boolean isUpgrade2Active() {
        return Upgrade2;
    }

    public boolean isUpgrade3Active() {
        return Upgrade3;
    }

    public void Upgrade1Activate() {
        if (!Upgrade1) {
            if (this instanceof Archer) {
                ((Archer) this).upgrade(1);
            } else if (this instanceof Cannon) {
                ((Cannon) this).upgrade(1);
            } else if (this instanceof FrostMage) {
                ((FrostMage) this).upgrade(1);
            } else if (this instanceof MineFactory) {
                ((MineFactory) this).upgrade(1);
            } else if (this instanceof PoisonTower) {
                ((PoisonTower) this).upgrade(1);
            } else if (this instanceof BoomTower) {
                ((BoomTower) this).upgrade(1);
            } else if (this instanceof Crossbow) {
                ((Crossbow) this).upgrade(1);
            } else if (this instanceof MauseFollowsTower) {
                ((MauseFollowsTower) this).upgrade(1);
            } else if (this instanceof Sniper) {
                ((Sniper) this).upgrade(1);
            } else if (this instanceof LaserTower) {
                ((LaserTower) this).upgrade(1);
//            }else if (this instanceof BoomTower) {
//                ((BoomTower) this).upgrade(1);
            }

        }
        Upgrade1 = true;
    }

    public void Upgrade2Activate() {
        if (!Upgrade2) {

            if (this instanceof Archer) {
                ((Archer) this).upgrade(2);
            } else if (this instanceof Cannon) {
                ((Cannon) this).upgrade(2);
            } else if (this instanceof FrostMage) {
                ((FrostMage) this).upgrade(2);
            } else if (this instanceof MineFactory) {
                ((MineFactory) this).upgrade(2);
            } else if (this instanceof PoisonTower) {
                ((PoisonTower) this).upgrade(2);
            } else if (this instanceof BoomTower) {
                ((BoomTower) this).upgrade(2);
            } else if (this instanceof Crossbow) {
                ((Crossbow) this).upgrade(2);
            } else if (this instanceof MauseFollowsTower) {
                ((MauseFollowsTower) this).upgrade(2);
            } else if (this instanceof Sniper) {
                ((Sniper) this).upgrade(2);
            } else if (this instanceof LaserTower) {
                ((LaserTower) this).upgrade(2);
//            }else if (this instanceof BoomTower) {
//                ((BoomTower) this).upgrade(1);
            }
        }
        Upgrade2 = true;
    }

    public void Upgrade3Activate() {
        if (!Upgrade3) {

            if (this instanceof Archer) {
                ((Archer) this).upgrade(3);
            } else if (this instanceof Cannon) {
                ((Cannon) this).upgrade(3);
            } else if (this instanceof FrostMage) {
                ((FrostMage) this).upgrade(3);
            } else if (this instanceof MineFactory) {
                ((MineFactory) this).upgrade(3);
            } else if (this instanceof PoisonTower) {
                ((PoisonTower) this).upgrade(3);
            } else if (this instanceof BoomTower) {
                ((BoomTower) this).upgrade(3);
            } else if (this instanceof Crossbow) {
                ((Crossbow) this).upgrade(3);
            } else if (this instanceof MauseFollowsTower) {
                ((MauseFollowsTower) this).upgrade(3);
            } else if (this instanceof Sniper) {
                ((Sniper) this).upgrade(3);
            } else if (this instanceof LaserTower) {
                ((LaserTower) this).upgrade(3);
//            }else if (this instanceof BoomTower) {
//                ((BoomTower) this).upgrade(1);
            }
        }
        Upgrade3 = true;
    }


}
