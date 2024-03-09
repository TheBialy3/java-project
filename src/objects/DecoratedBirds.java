package objects;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

import static helpz.LoadSave.getImg;

public class DecoratedBirds {


    ArrayList<BufferedImage> birds = new ArrayList<>();
    int x = -555, y = 540;
    Random random = new Random();
    int indexOfAnimation = 1, key=2;

    public DecoratedBirds() {

        getBirdsImg();
    }

    private void getBirdsImg() {
        birds.add(getImg("bird_fly_animation1"));
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
        if (key == 3) {
            key = 0;
        }
        y = random.nextInt(1000) + 100;
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
                g.drawImage(birds.get((indexOfAnimation + 2) % 9), x - 40, y - 50, 18, 36, null);
                g.drawImage(birds.get((indexOfAnimation + 4) % 9), x - 23, y - 28, 18, 36, null);
                g.drawImage(birds.get(indexOfAnimation), x, y, 18, 36, null);
                g.drawImage(birds.get((indexOfAnimation + 5) % 9), x - 20, y + 33, 18, 36, null);
                g.drawImage(birds.get((indexOfAnimation + 6) % 9), x - 55, y + 53, 18, 36, null);
                g.drawImage(birds.get((indexOfAnimation + 8) % 9), x - 70, y + 67, 18, 36, null);
                break;
            case 1:
                g.drawImage(birds.get((indexOfAnimation + 1) % 9), x - 53, y - 48, 18, 36, null);
                g.drawImage(birds.get((indexOfAnimation + 5) % 9), x - 43, y - 28, 18, 36, null);
                g.drawImage(birds.get((indexOfAnimation + 2) % 9), x - 28, y - 10, 18, 36, null);
                g.drawImage(birds.get(indexOfAnimation), x, y, 18, 36, null);
                g.drawImage(birds.get((indexOfAnimation + 4) % 9), x - 20, y + 13, 18, 36, null);
                g.drawImage(birds.get((indexOfAnimation + 6) % 9), x - 45, y + 23, 18, 36, null);
                g.drawImage(birds.get((indexOfAnimation + 8) % 9), x - 60, y + 32, 18, 36, null);
                break;
            case 2:
                g.drawImage(birds.get((indexOfAnimation + 1) % 9), x - 73, y - 68, 18, 36, null);
                g.drawImage(birds.get((indexOfAnimation + 5) % 9), x -33, y - 28, 18, 36, null);
                g.drawImage(birds.get(indexOfAnimation), x, y, 18, 36, null);
                g.drawImage(birds.get((indexOfAnimation + 4) % 9), x - 20, y + 13, 18, 36, null);
                g.drawImage(birds.get((indexOfAnimation + 6) % 9), x - 45, y + 33, 18, 36, null);
                g.drawImage(birds.get((indexOfAnimation + 1) % 9), x - 70, y + 57, 18, 36, null);
                g.drawImage(birds.get((indexOfAnimation + 5) % 9), x - 100, y + 78, 18, 36, null);
                g.drawImage(birds.get((indexOfAnimation + 2) % 9), x - 126, y + 93, 18, 36, null);
                g.drawImage(birds.get((indexOfAnimation + 7) % 9), x - 191, y + 112, 18, 36, null);
                break;
        }
    }

}
