package looks;

import helpz.Constants;

import java.awt.*;

import static helpz.Constants.TowerType.*;

public class TowerBestiaryLook {
    private int towerType;
    private String name, isAOE, isSlow, isDot, targetMoveTypeName, rangeName, damageTypeName, coolDownName;
    private int targetMoveType, damageType, cost, dmg;
    private float range, coolDown;
    private boolean aoe, slow, dot;
    private boolean unlocked = true, mouseOver;

    private Rectangle bounds;

    public int x, y, tilePixelNumber = 64;

    public TowerBestiaryLook(int towerType) {
        this.towerType = towerType;
        getNumbers();
        getStrings();
        initBounds();
    }

    private void initBounds() {
        int halfOfScreenHight = 640;
        int diff = 99;
        this.bounds = new Rectangle(40 + (diff * (towerType % 15)), halfOfScreenHight + 50 + (diff * (towerType / 15)), tilePixelNumber, tilePixelNumber);
    }

    private void getNumbers() {
        targetMoveType = Constants.TowerType.getTargetMoveTypeTower(towerType);
        range = Constants.TowerType.getDefaultRangeTower(towerType);
        aoe = Constants.TowerType.isAoeTower(towerType);
        slow = Constants.TowerType.isSlowTower(towerType);
        dot = Constants.TowerType.isDOTTower(towerType);
        damageType = Constants.TowerType.getDmgTypeTower(towerType);
        cost = Constants.TowerType.getCost(towerType);
        coolDown = Constants.TowerType.getDefaultCoolDownTower(towerType);
        dmg = Constants.TowerType.getDefaultDmg(towerType);
    }

    private void getStrings() {
        name = Constants.TowerType.getNameTower(towerType);
        isAOE = getAoeString();
        isSlow = getSlowString();
        isDot = getDotString();
        targetMoveTypeName = getTargetMoveTypeString();
        damageTypeName = getDamageTypeString();
        rangeName = getRangeString();
        coolDownName = getCoolDownString();
    }

    private String getAoeString() {
        if (aoe) {
            return "Area of effect";
        } else {
            return "Single target";
        }
    }

    private String getSlowString() {
        if (slow) {
            return "Can slowFor " + slow + "%";
        } else {
            return null;
        }
    }

    private String getDotString() {
        if (dot) {
            return "Do Damage over time";
        } else {
            return null;
        }
    }

    private String getTargetMoveTypeString() {
        if (targetMoveType == BOTH) {
            return "Attacks: Ground and Flying Units";
        } else if (targetMoveType == FLYING) {
            return "Attacks: Flying Units";
        } else if (damageType == NONE) {
            return null;
        } else {
            return "Attacks: Ground Units";
        }
    }

    public String getSpecialTowerString() {
            return getSpecialStringTower(towerType);
    }

    private String getDamageTypeString() {
        if (damageType == TRUE) {
            return "True damage";
        } else if (damageType == PHYSICAL) {
            return "Physical damage";
        } else if (damageType == NONE) {
            return "Dont do damage";
        } else {
            return "Magic damage";
        }
    }

    private String getRangeString() {
        if (range > 1200) {
            return "Range: Infinite";
        } else if (range > 250) {
            return "Range: Big";
        } else if (range < 129) {
            return "Range: Small";
        } else if (range < 1) {
            return "";
        } else {
            return "Range: Average";
        }
    }

    private String getCoolDownString() {
        if (coolDown > 60) {
            return "Slow";
        } else if (coolDown < 10) {
            return "Constant damage";
        } else if (coolDown < 45) {
            return "Fast";
        } else {
            return "Average";
        }
    }

    public void setUnlocked(boolean unlocked) {
        this.unlocked = unlocked;
    }

    public String getName() {
        return name;
    }

    public String getIsAOE() {
        return isAOE;
    }

    public String getIsSlow() {
        return isSlow;
    }

    public String getIsDot() {
        return isDot;
    }

    public String getTargetMoveTypeName() {
        return targetMoveTypeName;
    }


    public String getRangeName() {
        return rangeName;
    }

    public String getDamageTypeName() {
        return damageTypeName;
    }

    public String getCoolDownName() {
        return coolDownName;
    }

    public String getCost() {
        return "Cost: " + cost;
    }

    public String getDmg() {
        return "DMG: " + dmg;
    }

    public int getTowerType() {
        return towerType;
    }

    public boolean isUnlocked() {
        return unlocked;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void setMouseOver(boolean mouseOver) {
        this.mouseOver = mouseOver;
    }

    public boolean isMouseOver() {
        return mouseOver;
    }

}
