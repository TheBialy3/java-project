package ui;

import java.awt.*;

public class MyButton {

    private int x, y, width, height;
    private String text;

    private Rectangle bounds;

    public MyButton(String text, int x, int y, int width, int height) {
        this.text = text;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        initBounds();
    }

    private void initBounds(){
        this.bounds=new Rectangle(x,y,width,height);
    }

    public void draw(Graphics g){

        //Body
        g.setColor(Color.WHITE);
        g.fillRect(x,y,width,height);
        //Border
        g.setColor(Color.black);
        g.drawRect(x,y,width,height);
        //Text
        drawText(g);

    }

    private void drawText(Graphics g) {
        int w = g.getFontMetrics().stringWidth(text);
        int h = g.getFontMetrics().getHeight();
        g.drawString(text,x-w+width/2,y-h/2+height);
    }

    public Rectangle getBounds() {
        return bounds;
    }
}
