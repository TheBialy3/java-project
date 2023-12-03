package objects;

import helpz.Constants;



public class EnemyBestiaryLook {
    private int enemyType;
    private String name, speedName, moveTypeName, hitboxName;
    private int enemyMoveType, heightOfHitbox, wightOfHitbox, hitbox, mr, armor, goldWorth;
    private float speed;

    public EnemyBestiaryLook(int enemyType) {
        this.enemyType = enemyType;
        getNumbers();
        getStrings();
    }

    private void getNumbers() {
        enemyMoveType =  Constants.EnemyType.getMoveType(enemyType);
        getHitbox();
        mr = Constants.EnemyType.getMR(enemyType);
        armor = Constants.EnemyType.getArmor(enemyType);
        goldWorth = Constants.EnemyType.getGoldWorth(enemyType);
        speed= Constants.EnemyType.getSpeed(enemyType);
    }

    private void getHitbox() {
        heightOfHitbox = Constants.EnemyType.getHeightOfHitbox(enemyType);
        wightOfHitbox =  Constants.EnemyType.getWightOfHitbox(enemyType);
        hitbox = heightOfHitbox * wightOfHitbox;
    }

    private void getStrings() {
        name = Constants.EnemyType.getName(enemyType);
        speedName= getSpeedName(speed);
        moveTypeName=getMoveTypeNameInClass();
        hitboxName=getHitboxNameInClass();
    }

    private String getSpeedName(float speed) {
        if (speed<= 0.3f){
            return "Slow";
        }
        if (speed> 0.5f){
            return "Fast";
        }
        return "Average";
    }
    private String getMoveTypeNameInClass() {
        if( Constants.EnemyType.getMoveType(enemyType)==1){
            return "Flying";
        }else {
            return "Walking";
        }

    }
    private String getHitboxNameInClass() {
        if(hitbox>2500){
            return "Big";
        }
        if(hitbox<500){
            return "Small";
        }
        return "Average";
    }


    public String getName() {
        return name;
    }

    public String getSpeedName() {
        return speedName;
    }

    public String getMoveTypeName() {
        return moveTypeName;
    }

    public String getHitboxName() {
        return hitboxName;
    }

    public int getMr() {
        return mr;
    }

    public int getArmor() {
        return armor;
    }

    public int getGoldWorth() {
        return goldWorth;
    }


}
