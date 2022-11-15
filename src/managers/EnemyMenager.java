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

    public EnemyMenager(Playing playing, PathPoint start, PathPoint end) {
        this.playing = playing;
        this.start = start;
        this.end = end;
        addEnemy(SLIME);
        addEnemy(ORC);
        addEnemy(BALL);
        addEnemy(TENTACLE);
        loadEnemyImages();
    }

    private void loadEnemyImages() {
        BufferedImage atlas = LoadSave.getSpriteAtlas();
        enemyImages = new BufferedImage[4];
        enemyImages[0] = atlas.getSubimage(3 * 64, 1 * 64, 64, 64);
        enemyImages[1] = atlas.getSubimage(2 * 64, 2 * 64, 64, 64);
        enemyImages[2] = atlas.getSubimage(4 * 64, 2 * 64, 64, 64);
        enemyImages[3] = atlas.getSubimage(1 * 64, 4 * 64, 64, 64);
    }

    public void addEnemy(int enemyType) {
        int x = start.getxCord() * 64;
        int y = start.getyCord() * 64;
        switch (enemyType) {
            case ORC:
                enemies.add(new Orc(x, y, 0));
                break;
            case SLIME:
                enemies.add(new Slime(x, y, 0));
                break;
            case TENTACLE:
                enemies.add(new Tentacle(x, y, 0));
                break;
            case BALL:
                enemies.add(new Ball(x, y, 0));
                break;
        }

    }

    public void update() {
        for (Enemy e : enemies) {
            updateEnemyMove(e);
        }
    }

    private void updateEnemyMove(Enemy e) {
        if (e.getLastDir() == -1) {
            setNewDirectionAndMove(e);
        }
        int newX = (int) (e.getX() + getSpeedAndWidth(e.getLastDir(),e.getEnemyType()));
        int newY = (int) (e.getY() + getSpeedAndHeight(e.getLastDir(),e.getEnemyType()));

        if (getTileType(newX, newY) == ROAD_TILE) {
            //keep moving
            e.move(GetSpeed(e.getEnemyType()), e.getLastDir());
        } else if (isAtEnd(e)) {
            System.out.println("over");
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
            int newY = (int) (e.getY() + getSpeedAndHeight(UP,e.getEnemyType()));
            if (getTileType((int) e.getX(), newY) == ROAD_TILE) {
                e.move(GetSpeed(e.getEnemyType()), UP);
            } else {
                e.move(GetSpeed(e.getEnemyType()), DOWN);
            }
        } else {
            int newX = (int) (e.getX() + getSpeedAndWidth(RIGHT,e.getEnemyType()));
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
        if (e.getX() == end.getxCord() * 32) {
            if (e.getY() == end.getyCord() * 32) {
                return true;
            }
        }
        return false;
    }

    public int getTileType(int newX, int newY) {
        return playing.getTileType(newX, newY);
    }


    private float getSpeedAndWidth(int dir,int enemyType) {
        if (dir == LEFT) {
            return -GetSpeed(enemyType);
        } else if (dir == RIGHT) {
            return GetSpeed(enemyType) + 64;
        }

        return 0;
    }

    private float getSpeedAndHeight(int dir,int enemyType) {
        if (dir == UP) {
            return -GetSpeed(enemyType);
        } else if (dir == DOWN) {
            return GetSpeed(enemyType) + 64;
        }
        return 0;
    }

    public void draw(Graphics g) {
        for (Enemy e : enemies) {
            drawEnemy(e, g);
        }
    }

    public void drawEnemy(Enemy e, Graphics g) {
        g.drawImage(enemyImages[e.getEnemyType()], (int) e.getX(), (int) e.getY(), null);
    }

}
