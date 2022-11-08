package managers;

import enemies.Enemy;
import helpz.LoadSave;
import scenes.Playing;

import static helpz.Constants.Direction.*;
import static helpz.Constants.Tiles.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class EnemyMenager {

    private Playing playing;
    private BufferedImage[] enemyImages;
    private ArrayList<Enemy> enemies = new ArrayList<>();
    private float speed = 1.5f;


    public EnemyMenager(Playing playing) {
        this.playing = playing;
        enemyImages = new BufferedImage[4];
        addEnemy(64 * 3, 64 * 9);
        loadEnemyImages();

    }

    private void loadEnemyImages() {
        BufferedImage atlas = LoadSave.getSpriteAtlas();
        enemyImages[0] = atlas.getSubimage(3 * 64, 1 * 64, 64, 64);
        enemyImages[1] = atlas.getSubimage(2 * 64, 2 * 64, 64, 64);
        enemyImages[2] = atlas.getSubimage(4 * 64, 2 * 64, 64, 64);
        enemyImages[3] = atlas.getSubimage(1 * 64, 4 * 64, 64, 64);
    }

    public void addEnemy(int x, int y) {
        enemies.add(new Enemy(x, y, 0, 0));
    }

    public void update() {
        for (Enemy e : enemies) {
            // is next tile road(pos. dir)
            if (isNextTileRoad(e)) {
                //move enemy
            }
        }
    }

    private boolean isNextTileRoad(Enemy e) {
        //e pos
        //e dir
        //tile at new possible pos
        int newX = (int) (e.getX() + getSpeedAndWidth(e.getLastDir()));
        int newY = (int) (e.getY() + getSpeedAndHeight(e.getLastDir()));

        if (getTileType(newX, newY) == ROAD_TILE) {
            //keep moving
            e.move(speed, e.getLastDir());
        } else if (isAtEnd(e)) {
            //reached the end
        } else {
            setNewDirectionAndMove(e);
        }

        return false;
    }

    private void setNewDirectionAndMove(Enemy e) {
        int dir = e.getLastDir();

        //move in tile in 100%
        int xCord = (int) (e.getX() / 64);
        int yCord = (int) (e.getY() / 64);

        fixEnemyOffsetTile(e, dir, xCord, yCord);

        if (dir == LEFT || dir == RIGHT) {
            int newY = (int) (e.getY() + getSpeedAndHeight(UP));
            if (getTileType((int) e.getX(), newY) == ROAD_TILE) {
                e.move(speed, UP);
            } else {
                e.move(speed, DOWN);
            }
        } else {
            int newX = (int) (e.getX() + getSpeedAndWidth(RIGHT));
            if (getTileType(newX, (int) e.getY()) == ROAD_TILE){
                e.move(speed, RIGHT);
            } else {
                e.move(speed, LEFT);
            }
        }
    }

    private void fixEnemyOffsetTile(Enemy e, int dir, int xCord, int yCord) {

        switch (dir) {
            case RIGHT:
                if (xCord < 19) {
                    xCord++;
                }
                break;
            case DOWN:
                if (yCord < 19) {
                    yCord++;
                }
                break;
        }
        e.setPos(xCord*64,yCord*64);
    }


    private boolean isAtEnd(Enemy e) {
        return false;
    }

    public int getTileType(int newX, int newY) {
        return playing.getTileType(newX, newY);
    }


    private float getSpeedAndWidth(int dir) {
        if (dir == LEFT) {
            return -speed;
        } else if (dir == RIGHT) {
            return speed + 64;
        }

        return 0;
    }

    private float getSpeedAndHeight(int dir) {
        if (dir == UP) {
            return -speed;
        } else if (dir == DOWN) {
            return speed + 64;
        }

        return 0;
    }

    public void draw(Graphics g) {
        for (Enemy e : enemies) {
            drawEnemy(e, g);
        }

    }

    public void drawEnemy(Enemy e, Graphics g) {
        g.drawImage(enemyImages[0], (int) e.getX(), (int) e.getY(), null);
    }

}
