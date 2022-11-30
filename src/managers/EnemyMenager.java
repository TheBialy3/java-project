package managers;

import enemies.*;
import helpz.LoadSave;
import objects.PathPoint;
import scenes.Playing;

import static helpz.Constants.EnemyType.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static helpz.Constants.Direction.*;
import static helpz.Constants.EnemyType.ORC;
import static helpz.Constants.Tiles.ROAD_TILE;

public class EnemyMenager {

    private Playing playing;
    private BufferedImage[] enemyImages;
    private ArrayList<Enemy> enemies = new ArrayList<>();
    private PathPoint start, end;
    private int HPbarWidth = 50, tick = 0;
    private BufferedImage[] enemyEfects;

    public EnemyMenager(Playing playing, PathPoint start, PathPoint end) {
        this.playing = playing;
        this.start = start;
        this.end = end;

//        addEnemy(ANIMATED_ORK);
//        addEnemy(ORC);
//        addEnemy(BALL);
//        addEnemy(TENTACLE);

        loadEnemyImages();
        loadEfectsImages();
    }

    private void loadEfectsImages() {
        BufferedImage atlas = LoadSave.getSpriteAtlas();
        enemyEfects = new BufferedImage[1];
        enemyEfects[0] = atlas.getSubimage(8 * 64, 0 * 64, 64, 64);
    }

    private void loadEnemyImages() {
        BufferedImage atlas = LoadSave.getSpriteAtlas();
        enemyImages = new BufferedImage[5];
        enemyImages[0] = atlas.getSubimage(3 * 64, 1 * 64, 64, 64);
        enemyImages[1] = atlas.getSubimage(0 * 64, 6 * 64, 64, 64);
        enemyImages[2] = atlas.getSubimage(4 * 64, 2 * 64, 64, 64);
        enemyImages[3] = atlas.getSubimage(2 * 64, 2 * 64, 64, 64);
        enemyImages[4] = atlas.getSubimage(1 * 64, 6 * 64, 64, 64);
    }

    public void spawnEnemy(int nextEnemy) {
        addEnemy(nextEnemy);
    }

    public void addEnemy(int enemyType) {
        int x = start.getxCord() * 64;
        int y = start.getyCord() * 64;
        switch (enemyType) {
            case ORC:
                enemies.add(new Orc(x, y, 0, this));
                break;
            case ANIMATED_ORK:
                enemies.add(new Slime(x, y, 0, this));
                break;
            case TENTACLE:
                enemies.add(new Tentacle(x, y, 0, this));
                break;
            case SLIME:
                enemies.add(new Ball(x, y, 0, this));
                break;
        }
    }

    public void update() {
        for (Enemy e : enemies) {
            if (e.isAlive()) {
                updateEnemyMove(e);
            }
        }
    }

    public void draw(Graphics g) {
        for (Enemy e : enemies) {
            if (e.isAlive()) {
                drawEnemy(e, g);
                drawHealthBar(e, g);
                drawEfects(e, g);
            }
        }
    }

    private void drawEfects(Enemy e, Graphics g) {
        if (e.isSlowd()) {
            g.drawImage(enemyEfects[0], (int) e.getX(), (int) e.getY(), null);
        }
    }

    private void updateEnemyMove(Enemy e) {
        if (e.getLastDir() == -1) {
            setNewDirectionAndMove(e);
        }
        int newX = (int) (e.getX() + getSpeedAndWidth(e.getLastDir(), e.getEnemyType()));
        int newY = (int) (e.getY() + getSpeedAndHeight(e.getLastDir(), e.getEnemyType()));

        if (getTileType(newX, newY) == ROAD_TILE) {
            //keep moving
            e.move(GetSpeed(e.getEnemyType()), e.getLastDir());
        } else if (isAtEnd(e)) {
            e.kill();
            playing.removeOneLive();
        } else {
            setNewDirectionAndMove(e);
        }
    }

    private void setNewDirectionAndMove(Enemy e) {
        int dir = e.getLastDir();

        //move in tile in 100%
        int xCord = (int) (e.getX() / 64);
        int yCord = (int) (e.getY() / 64);

        fixEnemyOffsetTile(e, dir, xCord, yCord);

        if (isAtEnd(e)) {
            return;
        }

        if (dir == LEFT || dir == RIGHT) {
            int newY = (int) (e.getY() + getSpeedAndHeight(UP, e.getEnemyType()));
            if (getTileType((int) e.getX(), newY) == ROAD_TILE) {
                e.move(GetSpeed(e.getEnemyType()), UP);
            } else {
                e.move(GetSpeed(e.getEnemyType()), DOWN);
            }
        } else {
            int newX = (int) (e.getX() + getSpeedAndWidth(RIGHT, e.getEnemyType()));
            if (getTileType(newX, (int) e.getY()) == ROAD_TILE) {
                e.move(GetSpeed(e.getEnemyType()), RIGHT);
            } else {
                e.move(GetSpeed(e.getEnemyType()), LEFT);
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
        e.setPos(xCord * 64, yCord * 64);
    }


    private boolean isAtEnd(Enemy e) {
        if (e.getX() == end.getxCord() * 64) {
            if (e.getY() == end.getyCord() * 64) {
                return true;
            }
        }
        return false;
    }

    public int getTileType(int newX, int newY) {
        return playing.getTileType(newX, newY);
    }


    private float getSpeedAndWidth(int dir, int enemyType) {
        if (dir == LEFT) {
            return -GetSpeed(enemyType);
        } else if (dir == RIGHT) {
            return GetSpeed(enemyType) + 64;
        }

        return 0;
    }

    private float getSpeedAndHeight(int dir, int enemyType) {
        if (dir == UP) {
            return -GetSpeed(enemyType);
        } else if (dir == DOWN) {
            return GetSpeed(enemyType) + 64;
        }
        return 0;
    }


    private void drawHealthBar(Enemy e, Graphics g) {
        g.setColor(Color.RED);
        g.fillRect((int) e.getX() + 32 - (getNewHealthBar(e) / 2), (int) e.getY() - 10, getNewHealthBar(e), 6);
        getNewHealthBar(e);
    }

    private int getNewHealthBar(Enemy e) {
        return (int) (HPbarWidth * e.getHealthBar());
    }

    public void drawEnemy(Enemy e, Graphics g) {

        if (e.getEnemyType() == ANIMATED_ORK) {
            tick++;
            if (tick < 50) {
                g.drawImage(enemyImages[1], (int) e.getX(), (int) e.getY(), null);
            } else {
                g.drawImage(enemyImages[4], (int) e.getX(), (int) e.getY(), null);
                if (tick > 100) {
                    tick = 0;
                }
            }
        } else {
            g.drawImage(enemyImages[e.getEnemyType()], (int) e.getX(), (int) e.getY(), null);
        }
    }

    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }

    public int getAmountOfAliveEnemies() {
        int size = 0;
        for (Enemy e : enemies) {
            if (e.isAlive()) {
                size++;
            }
        }
        return size;
    }

    public void rewardPlayer(int enemyType) {
        playing.rewardPlayer(enemyType);
    }
}
