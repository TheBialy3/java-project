package towers;

import helpz.Constants;

import java.util.ArrayList;

public abstract class Tower {

    private int x, y, id, towerType, cdTick, dmg, worthGold;
    private float range, cooldown;
    private ArrayList<Integer> arr = new ArrayList<>();
    private int[][] road;

    public Tower(int x, int y, int id, int towerType, int[][] road) {
        this.x = x;
        this.y = y;
        this.id = id;
        this.towerType = towerType;
        setDefaultDmg();
        setDefaultRange();
        setDefaultCooldown();
        this.worthGold = Constants.TowerType.GetCost(towerType);
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
        cooldown = Constants.TowerType.GetDefaultCooldown(towerType);
    }

    private void setDefaultRange() {
        range = Constants.TowerType.GetDefaultRange(towerType);
    }

    private void setDefaultDmg() {
        dmg = Constants.TowerType.GetDefaultDmg(towerType);
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

    public void setDmg(int dmg) {
        this.dmg = dmg;
    }

    public float getRange() {
        return range;
    }

    public float getCooldown() {
        return cooldown;
    }

}
