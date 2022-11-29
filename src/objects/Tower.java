package objects;

import helpz.Constants;

public class Tower {

    private int x, y, id, towerType, cdTick, dmg, worthGold;
    private float range, cooldown;

    public Tower(int x, int y, int id, int towerType) {
        this.x = x;
        this.y = y;
        this.id = id;
        this.towerType = towerType;
        setDefaultDmg();
        setDefaultRange();
        setDefaultCooldown();
        this.worthGold = Constants.TowerType.GetCost(towerType);
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

    public void setTowerType(int towerType) {
        this.towerType = towerType;
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
