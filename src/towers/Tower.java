package towers;

import helpz.Constants;

import java.util.ArrayList;

public abstract class Tower {

    private int x, y, id, towerType, cdTick, dmg, worthGold;
    private float range, cooldown;
    private ArrayList<Integer> arr = new ArrayList<>();
    private int[][] road;
    private boolean U1 = false, U2 = false, U3 = false;

    public Tower(int x, int y, int id, int towerType, int[][] road) {
        this.x = x;
        this.y = y;
        this.id = id;
        this.towerType = towerType;
        setDefaultDmg();
        setDefaultRange();
        setDefaultCooldown();
        this.worthGold = Constants.TowerType.getCost(towerType);
        this.road = road;
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
        if (y / 64 > 20) {
            return;
        }
        if (x / 64 > 20) {
            return;
        }
        if (road[y / 64 - 1][x / 64 - 1] != -1) {
            arr.add(1);
        }
        if (road[y / 64 - 1][x / 64] != -1) {
            arr.add(2);
        }
        if (road[y / 64 - 1][x / 64 + 1] != -1) {
            arr.add(3);
        }
        if (road[y / 64][x / 64 - 1] != -1) {
            arr.add(4);
        }
        if (road[y / 64][x / 64 + 1] != -1) {
            arr.add(5);
        }
        if (road[y / 64 + 1][x / 64 - 1] != -1) {
            arr.add(6);
        }
        if (road[y / 64 + 1][x / 64] != -1) {
            arr.add(7);
        }
        if (road[y / 64 + 1][x / 64 + 1] != -1) {
            arr.add(8);
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

    public float getRange() {
        return range;
    }

    public float getCooldown() {
        return cooldown;
    }

    public void reduceCooldown(float cooldown) {
        this.cooldown -= cooldown;
        System.out.println(this.cooldown);
    }

    public void addRange(float range) {
        this.range += range;
    }

    public void addDmg(int dmg) {
        this.dmg += dmg;
    }

    public boolean isUpgrade1Active() {
        return U1;
    }

    public boolean isUpgrade2Active() {
        return U2;
    }

    public boolean isUpgrade3Active() {
        return U3;
    }

    public void Upgrade1Activate() {
        U1 = true;
    }

    public void Upgrade2Activate() {
        U2 = true;
    }

    public void Upgrade3Activate() {
        U3 = true;
    }
}