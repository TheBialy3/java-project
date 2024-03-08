package objects;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static helpz.LoadSave.getImg;

public class DecoratedBirds {


    ArrayList<BufferedImage> birds = new ArrayList<>();
    int x = -555, y=540;
    int indexOfAnimation = 1, key;

    public DecoratedBirds(int key) {
        this.key = key;
        getBirdsImg();
    }

    private void getBirdsImg() {
        birds.add(getImg(    "bird_fly_animation1"));
            birds.add(getImg("bird_fly_animation2"));
        birds.add(getImg("bird_fly_animation3"));
        birds.add(getImg("bird_fly_animation4"));
        birds.add(getImg("bird_fly_animation5"));
        birds.add(getImg("bird_fly_animation6"));
        birds.add(getImg("bird_fly_animation7"));
        birds.add(getImg("bird_fly_animation8"));
        birds.add(getImg("bird_fly_animation9"));
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void keyUp() {
        key++;
        if (key == 2) {
            key = 0;
        }
    }
    public void xUp() {
        x++;
    }
    public void animationIndexUp() {
        indexOfAnimation++;
        if (indexOfAnimation == 9) {
            indexOfAnimation = 0;
        }
    }

    public void draw(Graphics g) {
        switch (key) {
            case 0:
                g.drawImage(birds.get((indexOfAnimation + 2) % 9), x-40, y-40, 18, 36, null);
                g.drawImage(birds.get(indexOfAnimation), x, y, 18, 36, null);
                g.drawImage(birds.get((indexOfAnimation + 5) % 9), x-20, y+83, 18, 36, null);
                g.drawImage(birds.get((indexOfAnimation + 4) % 9), x-23, y-88, 18, 36, null);
                g.drawImage(birds.get((indexOfAnimation + 6) % 9), x-55, y+43, 18, 36, null);
                g.drawImage(birds.get((indexOfAnimation + 8) % 9), x-60, y+122, 18, 36, null);
                break;
            case 1:
                g.drawImage(birds.get((indexOfAnimation + 2) % 9), x-40, y-40, 18, 36, null);
                g.drawImage(birds.get(indexOfAnimation), x, y, 18, 36, null);
                g.drawImage(birds.get((indexOfAnimation + 5) % 9), x-20, y+83, 18, 36, null);
                g.drawImage(birds.get((indexOfAnimation + 4) % 9), x-23, y-88, 18, 36, null);
                g.drawImage(birds.get((indexOfAnimation + 6) % 9), x-55, y+43, 18, 36, null);
                g.drawImage(birds.get((indexOfAnimation + 8) % 9), x-60, y+122, 18, 36, null);
                break;
        }
    }

}
