package towers;

import helpz.Constants;
import managers.TowerManager;

import java.util.ArrayList;

public abstract class Tower {

    private int x, y, id, towerType, cdTick, dmg, worthGold, duration;
    private float range, cooldown;
    private ArrayList<Integer> arr = new ArrayList<>();
    private int[][] road;
    private boolean Upgrade1 = false, Upgrade2 = false, Upgrade3 = false;
    private TowerManager towerManager;

    //upgrades
    public boolean AFTERSHOCK = false;
    public boolean ATTACK_SPEED_BOOST = false;
    public boolean ATTACK_DAMAGE_BOOST = false;
    public boolean RANGE_BOOST = false;


    public Tower(int x, int y, int id, int towerType,TowerManager towerManager, int[][] road) {
        this.x = x;
        this.y = y;
        this.id = id;
        this.towerType = towerType;
        setDefaultDmg();
        setDefaultRange();
        setDefaultCooldown();
        this.worthGold = Constants.TowerType.getCost(towerType);
        this.towerManager=towerManager;
        this.road = road;
        if (Constants.TowerType.isDOT(towerType)) {
            duration = Constants.TowerType.getDefaulDuration(towerType);
        }
    }



    public void update() {
        cdTick++;
    }

    public int getWorthGold() {
        return worthGold;
    }



    public void updateTowerWorthGold(int worthGold) {
        this.worthGold += worthGold;
    }

    public boolean isCooldownOver() {
        return cdTick >= cooldown;
    }

    public void resetCooldown() {
        cdTick = 0;
    }

    private void setDefaultCooldown() {
        cooldown = Constants.TowerType.getDefaultCooldown(towerType);
    }

    private void setDefaultRange() {
        range = Constants.TowerType.getDefaultRange(towerType);
    }

    private void setDefaultDmg() {
        dmg = Constants.TowerType.getDefaultDmg(towerType);
        if(towerManager.isCard0()){
            dmg = Constants.TowerType.getDefaultDmg(towerType);
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

    public float getCooldown() {
        return cooldown;
    }

    public int getDuration() {
        return duration;
    }

    public void addDuration(int duration) {
        this.duration += duration;
    }

    public void reduceCooldown(float cooldown) {
        this.cooldown -= cooldown;
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

    public boolean isAFTERSHOCK() {
        return AFTERSHOCK;
    }

    public void setAFTERSHOCK(boolean AFTERSHOCK) {
        this.AFTERSHOCK = AFTERSHOCK;
    }

    public boolean isATTACK_SPEED_BOOST() {
        return ATTACK_SPEED_BOOST;
    }

    public void setATTACK_SPEED_BOOST(boolean ATTACK_SPEED_BOOST) {
        this.ATTACK_SPEED_BOOST = ATTACK_SPEED_BOOST;
    }

    public boolean isATTACK_DAMAGE_BOOST() {
        return ATTACK_DAMAGE_BOOST;
    }

    public void setATTACK_DAMAGE_BOOST(boolean ATTACK_DAMAGE_BOOST) {
        this.ATTACK_DAMAGE_BOOST = ATTACK_DAMAGE_BOOST;
    }

    public boolean isRANGE_BOOST() {
        return RANGE_BOOST;
    }

    public void setRANGE_BOOST(boolean RANGE_BOOST) {
        this.RANGE_BOOST = RANGE_BOOST;
    }
}
