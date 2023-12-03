package objects;

import helpz.Constants;

public class TowerBestiaryLook {
    private int towerType;
    private String name, isAOE,isSlow,isDot, targetMoveTypeName, rangeName,damageTypeName,coolDownName;
    private int targetMoveType,damageType, cost,dmg;
    private float range,coolDown;
    private  boolean aoe,slow,dot;

    public TowerBestiaryLook(int towerType) {
        this.towerType = towerType;
        getNumbers();
        getStrings();
    }

    private void getNumbers() {
        targetMoveType =  Constants.TowerType.getTargetMoveType(towerType);
        range = Constants.TowerType.getDefaultRange(towerType);
        aoe = Constants.TowerType.isAoe(towerType);
        slow = Constants.TowerType.isSlow(towerType);
        dot= Constants.TowerType.isDOT(towerType);
        damageType= Constants.TowerType.getDmgType(towerType);
        cost= Constants.TowerType.getCost(towerType);
        coolDown= Constants.TowerType.getDefaultCoolDown(towerType);
        dmg= Constants.TowerType.getDefaultDmg(towerType);
    }

    private void getStrings() {
        name = Constants.TowerType.getName(towerType);
        isAOE= getAoeString();
        isSlow=getSlowString();
        isDot=getDotString();
        targetMoveTypeName=getTargetMoveTypeString();
        damageTypeName=getDamageTypeString();
        rangeName=getRangeString();
        coolDownName=getCoolDownString();
    }

    private String getAoeString() {
    }

    private String getSlowString() {
    }

    private String getDotString() {
    }

    private String getTargetMoveTypeString() {
    }

    private String getDamageTypeString() {
    }

    private String getRangeString() {
    }

    private String getCoolDownString() {
    }


    private String getSpeedName() {
        if (speed<= 0.3f){
            return "Slow";
        }
        if (speed> 0.5f){
            return "Fast";
        }
        return "Average";
    }
    
    
}
