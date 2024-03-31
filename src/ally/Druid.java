package ally;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static helpz.LoadSave.getImg;

public class Druid extends Ally{
    public ArrayList<BufferedImage> animationOfWalk, animationOfStay, animationOfStun, animationOfFight, animationOfDead;

    public Druid(int x, int y, int allyType, int lvl, float passiveX, float passiveY) {
        super(x, y, allyType, lvl, passiveX, passiveY);
        getImgAnimationOf();
    }
    public void draw(Graphics g) {
        switch (allyStatus) {
            case STAY:
                g.drawImage(animationOfStay.get(0),x,y,null);
                break;
            case WALK:
                g.drawImage(animationOfWalk.get(0),x,y,null);
                break;
            case STUN:
                g.drawImage(animationOfStun.get(0),x,y,null);
                break;
            case FIGHT:
                g.drawImage(animationOfFight.get(0),x,y,null);
                break;
            case DEAD:
                g.drawImage(animationOfDead.get(0),x,y,null);
                break;

        }
    }

    public void getImgAnimationOf() {
//  Constructors
        animationOfWalk = new ArrayList<>();
        animationOfStay = new ArrayList<>();
        animationOfStun = new ArrayList<>();
        animationOfFight = new ArrayList<>();
        animationOfDead = new ArrayList<>();
//  WALK
        animationOfWalk.add(getImg(""));
//  STAY
        animationOfStay.add(getImg(""));
//  STUN
        animationOfStun.add(getImg(""));
//  FIGHT
        animationOfFight.add(getImg(""));
//  DEAD
        animationOfDead.add(getImg(""));
    }
    public void power1() {

    }
    public void power2() {

    } public void power3() {

    }
}
