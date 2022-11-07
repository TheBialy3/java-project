package managers;

import enemies.Enemy;
import scenes.Playing;

import java.awt.*;
import java.awt.image.BufferedImage;

public class EnemyMenager {

    private Playing playing;
    private BufferedImage[] enemyImages;
    private Enemy testEnemy;

    public EnemyMenager(Playing playing){
        this.playing = playing;
        enemyImages = new BufferedImage[4];
        testEnemy = new Enemy(64*1, 64*1, 0, 0);
    }

    public void update(){

    }

    public void draw(Graphics g){

    }

}
