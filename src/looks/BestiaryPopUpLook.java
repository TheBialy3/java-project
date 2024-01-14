package looks;

import helpz.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

public class BestiaryPopUpLook {
    private BufferedImage icon, background;
    private EnemyBestiaryLook enemy = null;
    private TowerBestiaryLook tower = null;
    private int x, y;
    private boolean mouseOver;

    private Rectangle bounds;

    public BestiaryPopUpLook(EnemyBestiaryLook enemy, int x, int y) {
        this.x = x;
        this.y = y;
        this.enemy = enemy;
        getEnemyBackground();
        getRectangleEnemy();
    }


    public BestiaryPopUpLook(TowerBestiaryLook tower, int x, int y) {
        this.x = x;
        this.y = y;
        this.tower = tower;
        getTowerBackground();
        getRectangleTower();
    }

    private void getRectangleEnemy() {
        bounds = enemy.getBounds();
    }

    private void getRectangleTower() {
        bounds = tower.getBounds();
    }

    private void getEnemyBackground() {
        background = LoadSave.getImg("enemyBackground");
    }

    private void getTowerBackground() {
        background = LoadSave.getImg("towerBackground");
    }

    public void draw(Graphics g) {
        int nextLineH = 50;
        int centerH = 200;
        int centerW = 200;
        int logoX = 820;
        int width = 1500;
        int height = 660;

        g.setColor(new Color(28, 28, 28));
        g.setFont(new Font("Osaka", Font.BOLD, 40));
        if (tower == null) {
            g.setColor(new Color(124, 6, 6));
            g.drawImage(background, x, y, width, height, null);
            g.drawString(enemy.getName(), x + centerW, y + centerH);
            g.drawString(enemy.getHP(), x + centerW * 3, y + centerH+ nextLineH);
            g.drawString(enemy.getArmor(), x + centerW * 3, y + centerH + nextLineH * 2);
            g.drawString(enemy.getMr(), x + centerW * 3, y + centerH + nextLineH * 3);
            g.drawString(enemy.getGoldWorth(), x + centerW, y + centerH + nextLineH * 2);
            g.drawString(enemy.getSpeedName(), x + centerW, y + centerH + nextLineH * 3);
            g.drawString(enemy.getHitboxName(), x + centerW, y + centerH + nextLineH * 4);
            g.drawString(enemy.getMoveTypeName(), x + centerW, y + centerH + nextLineH);
            if (enemy.getPower() != null) {
                g.drawString(enemy.getPower(), x + centerW, y + centerH + nextLineH * 5);
            }
        } else {
            g.setColor(new Color(0, 0, 0));
            g.drawImage(background, x, y, width, height, null);
            g.drawString(tower.getName(), x + centerW, y + centerH);
            g.drawString(tower.getCost(), x + centerW * 3, y + centerH);
            g.drawString(tower.getCoolDownName(), x + centerW * 5, y + centerH);
            g.drawString(tower.getDmg(), x + centerW, y + centerH + nextLineH);
            g.drawString(tower.getDamageTypeName(), x + centerW * 3, y + centerH + nextLineH);
            g.drawString(tower.getRangeName(), x + centerW, y + centerH + nextLineH * 5);
            g.drawString(tower.getTargetMoveTypeName(), x + centerW * 3, y + centerH + nextLineH * 2);
            if (tower.getIsAOE() != null) {
                g.drawString(tower.getIsAOE(), x + centerW, y + centerH + nextLineH * 2);
            }
            if (tower.getIsDot() != null) {
                g.drawString(tower.getIsDot(), x + centerW, y + centerH + nextLineH * 3);
            }
            if (tower.getIsSlow() != null) {
                g.drawString(tower.getIsSlow(), x + centerW, y + centerH + nextLineH * 4);
            }
        }
        g.drawImage(icon, x + logoX, y + nextLineH, null);
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void setMouseOver(boolean mouseOver) {
        this.mouseOver = mouseOver;
        if (tower == null) {
            enemy.setMouseOver(mouseOver);
        } else {
            tower.setMouseOver(mouseOver);
        }
    }

    public boolean isMouseOver() {
        return mouseOver;
    }
}

