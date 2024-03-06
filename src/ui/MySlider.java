package ui;

import java.awt.*;
import java.awt.image.BufferedImage;

public class MySlider {

    public int x, y, width, height, percent;
    private String text;
    private Rectangle bounds;

    private boolean mouseOver, mousePressed, mouseReleased;

    private BufferedImage img;

    //Slider
    public MySlider(String text, int x, int y, int width, int height,int percent) {
        this.text = text;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.percent=percent;
        initBounds();
    }

    public void draw(Graphics g) {

        //Body
        drawLine(g);
        //Border
        drawSlider(g);
        //Text
        drawText(g);

    }

    private void drawLine(Graphics g) {
        g.setColor(new Color(49, 49, 49));
        g.fillRect(x,y+(height/2)-10,width,20);
        g.setColor(new Color(33, 33, 33));
        g.drawRect(x,y+(height/2)-10,width,20);
    }

    private void drawSlider(Graphics g) {
        g.setColor(new Color(49, 49, 49));
        g.fillRect(x+(width/100*percent),y,30,height);
        g.setColor(new Color(33, 33, 33));
        g.drawRect(x+(width/100*percent),y,30,height);
    }

    private void drawText(Graphics g) {
        Font stringFont = new Font("SansSerif", Font.PLAIN, 24);
        g.setFont(stringFont);
        int w = g.getFontMetrics().stringWidth(text);
        int h = g.getFontMetrics().getHeight();
        g.drawString(text, x - (w / 2) + width / 2, y );
    }
    
    private void initBounds() {
        this.bounds = new Rectangle(x, y, width, height);
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void setMouseOver(boolean mouseOver) {
        this.mouseOver = mouseOver;
    }

    public void resetBooleans() {
        this.mousePressed = false;
        this.mouseOver = false;
    }

    public void setMousePressed(boolean mousePressed) {
        this.mousePressed = mousePressed;
    }
}
