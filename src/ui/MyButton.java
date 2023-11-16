package ui;

import java.awt.*;

public class MyButton {

    public int x, y, width, height, id;
    private String text;
    private boolean mouseOver, mousePressed, mouseReleased;
    private Rectangle bounds;


    //NormalButtons
    public MyButton(String text, int x, int y, int width, int height) {
        this.text = text;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.id = -1;
        initBounds();
    }


    //TileButtons
    public MyButton(String text, int x, int y, int width, int height, int id) {
        this.text = text;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.id = id;

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
        if (mouseOver) {
            g.setColor(new Color(9, 255, 255));
        }
        if (mousePressed) {
            g.setColor(new Color(9, 255, 255));
            g.drawRect(x + 5, y + 5, width - 10, height - 10);
            g.drawRect(x + 6, y + 6, width - 12, height - 12);
        }
    }


    private void drawBody(Graphics g) {
        if (mouseOver) {
            g.setColor(new Color(0, 52, 52));
        } else {
            g.setColor(new Color(0, 102, 102));
        }
        g.fillRect(x, y, width, height);
    }

    private void drawText(Graphics g) {
        Font stringFont = new Font("SansSerif", Font.PLAIN, 24);
        g.setFont(stringFont);
        int w = g.getFontMetrics().stringWidth(text);
        int h = g.getFontMetrics().getHeight();
        g.drawString(text, x - (w / 2) + width / 2, y + (h / 4) + height / (2));
    }


    public void setMouseOver(boolean mouseOver) {
        this.mouseOver = mouseOver;
    }

    public void setMousePressed(boolean mousePressed) {
        this.mousePressed = mousePressed;
    }

    public void setMouseReleased(boolean mouseReleased) {
        this.mouseReleased = mouseReleased;
    }




    public void resetBooleans() {
        this.mousePressed = false;
        this.mouseOver = false;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public int getId() {
        return id;
    }

    public boolean isMouseOver() {
        return mouseOver;
    }

    public boolean isMousePressed() {
        return mousePressed;
    }

    public void drawQuit(Graphics g) {

        //Body
        drawBodyQuit(g);
        //Border
        drawBorderQuit(g);
        //Text
        drawText(g);

    }

    private void drawBorderQuit(Graphics g) {
        g.setColor(Color.black);
        g.drawRect(x + width / 4, y + height / 4, width / 2, height / 2);
        if (mouseOver) {
            g.setColor(new Color(9, 255, 255));
        }
        if (mousePressed) {
            g.setColor(new Color(9, 255, 255));
            g.drawRect(x + 5 + width / 4, y + 5 + height / 4, width / 2 - 10, height / 2 - 10);
            g.drawRect(x + 6 + width / 4, y + 6 + height / 4, width / 2 - 12, height / 2 - 12);
        }
    }

    private void drawBodyQuit(Graphics g) {
        if (mouseOver) {
            g.setColor(new Color(255, 15, 15));
        } else {

            g.setColor(new Color(169, 0, 0));
        }
        g.fillRect(x + width / 4, y + height / 4, width / 2, height / 2);
    }
}
