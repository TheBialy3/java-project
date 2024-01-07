package looks;

import helpz.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

public class BestiaryPopUpLook {
    private BufferedImage icon, background;
    private EnemyBestiaryLook enemy=null;
    private TowerBestiaryLook tower=null;
    private int x, y;

    public BestiaryPopUpLook(EnemyBestiaryLook enemy, int x, int y) {
        this.x = x;
        this.y = y;
        this.enemy=enemy;
        getEnemyBackground();
    }



    public BestiaryPopUpLook(TowerBestiaryLook tower, int x, int y) {
        this.x = x;
        this.y = y;
        this.tower=tower;
        getTowerBackground();
    }

    private void getEnemyBackground() {
        LoadSave.getImg("");
    }

    private void getTowerBackground() {
        LoadSave.getImg("");
    }

    public void draw(Graphics g) {
        int nextLineH = 50;
        int centerH = 100;
        int centerW = 100;
        int logoX = 820;
        g.drawImage(background, x, y, null);
        g.setColor(new Color(28, 28, 28));
        g.setFont(new Font("Serif", Font.BOLD, 30));
        if(tower==null){
            g.drawString(enemy.getName(), x + centerW, y + centerH);
            g.drawString(enemy.getHitboxName(), x + centerW * 5, y + centerH);
            g.drawString(enemy.getMoveTypeName(), x + centerW, y + centerH + nextLineH);
        } else {
            g.drawString(tower.getName(), x + centerW, y + centerH);
            g.drawString(tower.getCoolDownName(), x + centerW * 5, y + centerH);
            g.drawString(tower.getDamageTypeName(), x + centerW, y + centerH + nextLineH);
        }
        g.drawImage(icon, x + logoX, y + nextLineH, null);
    }
}

