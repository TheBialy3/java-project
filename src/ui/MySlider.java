package ui;

import java.awt.*;
import java.awt.image.BufferedImage;

public class MySlider {

    public int x, y, width, height, id, percent;
    private String text;
    private Rectangle bounds;

    private BufferedImage img;

    //Slider
    public MySlider(String text, int x, int y, int width, int height,int percent) {
        this.text = text;
        this.x = x;
        this.percent=percent;
        this.y = y;
        this.width = width;
        this.height = height;
        this.id = -1;
        initBounds();
    }

    private void initBounds() {
        this.bounds = new Rectangle(x, y, width, height);
    }


}
