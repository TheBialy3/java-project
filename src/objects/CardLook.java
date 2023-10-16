package objects;

import java.awt.*;
import java.awt.image.BufferedImage;

public class CardLook {
    private BufferedImage  logo,card, cardChoose;
    private String name, description;
    private int x, y;

    public CardLook(String name, String description, BufferedImage logo, BufferedImage card, BufferedImage cardChoose, int x, int y) {
        this.name = name;
        this.description = description;
        this.logo = logo;
        this.card = card;
        this.cardChoose = cardChoose;
        this.x=x;
        this.y=y;
    }

    public void draw(Graphics g, boolean isMouseOver) {
        int nextLineH = 50;
        int centerH = 100;
        int centerW = 100;
        int logoX = 820;
        if (!isMouseOver) {
            g.drawImage(card, x, y, null);
        } else {
            g.drawImage(cardChoose, x, y, null);
        }
        g.setColor(new Color(28, 28, 28));
        g.setFont(new Font("Serif", Font.BOLD, 30));
        g.drawString(name, x + centerW, y + centerH);
        g.drawString(description, x + centerW, y + centerH + nextLineH);
        g.drawImage(logo, x +logoX, y + nextLineH , null);
    }
}