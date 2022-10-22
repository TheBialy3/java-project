package ui;

import java.awt.*;

public class MyButton {

    private int x, y, width, height;
    private String text;
    private boolean mouseover;

    private Rectangle bounds;

    public MyButton(String text, int x, int y, int width, int height) {
        this.text = text;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        initBounds();
    }

    private void initBounds() {
        this.bounds = new Rectangle(x, y, width, height);
    }

    public void draw(Graphics g) {

        //Body
        drawBody(g);
        //Border
        drawBorder(g);
        //Text
        drawText(g);

    }

    private void drawBorder(Graphics g) {
        g.setColor(Color.black);
        g.drawRect(x, y, width, height);
    }

    private void drawBody(Graphics g) {
        if (mouseover) {
            g.setColor(Color.green);
        } else {
            g.setColor(Color.WHITE);
        }
        g.fillRect(x, y, width, height);
    }

    public void setMouseOver(boolean mouseover) {
        this.mouseover = mouseover;
    }

    private void drawText(Graphics g) {
        int w = g.getFontMetrics().stringWidth(text);
        int h = g.getFontMetrics().getHeight();
        g.drawString(text, x - w + width / 2, y - h / 2 + height);
    }

    public Rectangle getBounds() {
        return bounds;
    }
}
