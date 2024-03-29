package looks;

import helpz.Constants;


import java.awt.*;

import static helpz.Constants.EnemyType.*;


public class EnemyBestiaryLook {
    private int enemyType;
    private String name, speedName, moveTypeName, hitboxName,power;
    private int heightOfHitbox, wightOfHitbox, hitbox, mr, armor, goldWorth,hp;
    private float speed;
    private boolean unlocked = true, mouseOver;

    private Rectangle bounds;
    public int x, y, tilePixelNumber = 64;

    public EnemyBestiaryLook(int enemyType) {
        this.enemyType = enemyType;
        getNumbers();
        getStrings();
        initBounds();
    }

    private void initBounds() {
        int diff = 99;
        this.bounds = new Rectangle(40 + (diff * (enemyType % 15)), 50 + (diff * (enemyType / 15)), tilePixelNumber, tilePixelNumber);
    }

    private void getNumbers() {
        getHitbox();
        hp=Constants.EnemyType.getStartHealth(enemyType);
        mr = Constants.EnemyType.getMR(enemyType);
        armor = Constants.EnemyType.getArmor(enemyType);
        goldWorth = Constants.EnemyType.getGoldWorth(enemyType);
        speed = Constants.EnemyType.getSpeed(enemyType);
    }

    private void getHitbox() {
        heightOfHitbox = Constants.EnemyType.getHeightOfHitBox(enemyType);
        wightOfHitbox = Constants.EnemyType.getWightOfHitBox(enemyType);
        hitbox = heightOfHitbox * wightOfHitbox;
    }

    private void getStrings() {
        name = Constants.EnemyType.getName(enemyType);
        speedName = getSpeedName(speed);
        moveTypeName = getMoveTypeNameInClass();
        hitboxName = getHitboxNameInClass();
        power=powerStringGenerator();
    }

    private String powerStringGenerator() {
        if(enemyType==ORK_ZOMBIE){
            return "Will rise one time";
        } else if(enemyType==CAMEL){
            return "After death spawn all Camel Spawns that died this game(+2)";
        } else if(enemyType==CREEPY_CAT){
            return "Become undetectable when his Hp drop below half";
        } else{
            return null;
        }
    }

    private String getSpeedName(float speed) {
        if (speed <= 0.3f) {
            return "Slow";
        }
        if (speed > 0.5f) {
            return "Fast";
        }
        return "Average";
    }

    private String getMoveTypeNameInClass() {
        if (Constants.EnemyType.getMoveType(enemyType) == 1) {
            return "Flying";
        } else {
            return "Walking";
        }

    }

    private String getHitboxNameInClass() {
        if (hitbox > 2500) {
            return "Big";
        }
        if (hitbox < 500) {
            return "Small";
        }
        return "Average";
    }


    public void setUnlocked(boolean unlocked) {
        this.unlocked = unlocked;
    }

    public String getPower() {
        return power;
    }

    public String getHP(){
       return "Starting HP: "+hp;
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

    public String getMr() {
        return "Magic Resist: "+mr;
    }

    public String getArmor() {
        return "Armor: "+armor;
    }

    public String getGoldWorth() {
        return "Gold for kill: "+goldWorth;
    }

    public int getEnemyType() {
        return enemyType;
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
