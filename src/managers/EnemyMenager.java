package managers;

import enemies.*;
import helpz.LoadSave;

import objects.PathPoint;
import scenes.Playing;

import static helpz.Constants.EnemyType.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

import static helpz.Constants.Direction.*;
import static helpz.Constants.EnemyType.ORC;

public class EnemyMenager {

    private Random random = new Random();
    private Playing playing;
    private BufferedImage[] enemyImages;
    private ArrayList<Enemy> enemies = new ArrayList<>();
    private PathPoint start, end;
    private int HPbarWidth = 50, i = 0, tilePixelNumber= 64;
    private int  ranl = random.nextInt(25),ranr = random.nextInt(25);
    private BufferedImage[] enemyEfects;
    private int[][] roadDirArr;
    protected WaveManager waveManager;

    public EnemyMenager(Playing playing, PathPoint start, PathPoint end, WaveManager waveManager) {
        this.waveManager = waveManager;
        this.playing = playing;
        this.start = start;
        this.end = end;
        loadRoadDirArr();
        loadEnemyImages();
        loadEfectsImages();

    }

    private void loadRoadDirArr() {
        roadDirArr = LoadSave.GetLevelDir();
    }

    private void loadEfectsImages() {
        BufferedImage atlas = LoadSave.getSpriteAtlas();
        enemyEfects = new BufferedImage[3];
        for (int i = 0; i < 3; i++) {
            enemyEfects[i] = atlas.getSubimage(0+i*tilePixelNumber, 22 * tilePixelNumber, tilePixelNumber, tilePixelNumber);
        }}

    private void loadEnemyImages() {
        BufferedImage atlas = LoadSave.getSpriteAtlas();
        enemyImages = new BufferedImage[9];
        for (int i = 0; i < 9; i++) {
            enemyImages[i] = atlas.getSubimage(0, (2+i) * tilePixelNumber, tilePixelNumber, tilePixelNumber);
        } }

    public void spawnEnemy(int nextEnemy) {
        addEnemy(nextEnemy);
    }

    public void addEnemy(int enemyType) {
        int x = start.getxCord() * tilePixelNumber;
        int y = start.getyCord() * tilePixelNumber;
        switch (enemyType) {
            case ORC:
                enemies.add(new Orc(x, y, 0, this, waveManager));
                break;
            case SLIME:
                enemies.add(new Slime(x, y, 0, this, waveManager));
                break;
            case TENTACLE:
                enemies.add(new Tentacle(x, y, 0, this, waveManager));
                break;
            case ORK_ZOMBI:
                enemies.add(new OrcZombi(x, y, 0, this, waveManager));
                break;
            case CAMEL:
                enemies.add(new Camel(x, y, 0, this, waveManager));
                enemies.add(new CamelJunior(x, y, 0, this, waveManager));
                break;
        }
    }

    public void update() {
        i++;
        for (Enemy e : enemies) {
            if (e.isAlive()) {
                updateEnemyMoveNew(e);
            }
        }
    }

    public void resetRand() {
        ranl = random.nextInt(25);
        ranr = random.nextInt(25);
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
            g.drawImage(enemyEfects[1], (int) e.getX(), (int) e.getY(), null);
        }
        if (e.doesRevive()) {
            g.drawImage(enemyEfects[0], (int) e.getX() - 2, (int) e.getY() - 53, null);
        }
        if (e.isPoisoned()) {
            if((i/8)%4==4) {
                g.drawImage(enemyEfects[2], (int) e.getX() - 26 + ranr + ((i/5) % 2), (int) e.getY() + 25 - (i / 3), null);
                g.drawImage(enemyEfects[2], (int) e.getX() + ranl + ((i/5) % 2), (int) e.getY() + 25 - (((i+75)%150) / 3), null);
            }else {
            g.drawImage(enemyEfects[2], (int) e.getX() - 26 + ranr+((i/5)%4), (int) e.getY() + 25 - (i / 3), null);
            g.drawImage(enemyEfects[2], (int) e.getX() + ranl+((i/5)%4), (int) e.getY() + 25 - (((i+75)%150) / 3), null);}
            if (i > 150) {
                i = 0;
                resetRand();
            }


        }
    }

    private void updateEnemyMoveNew(Enemy e) {
        PathPoint currTile = getEnemyTile(e);
        int dir = roadDirArr[currTile.getyCord()][currTile.getxCord()];

        e.move(getSpeed(e.getEnemyType()), dir);

        PathPoint newTile = getEnemyTile(e);

        if (!isTilesTheSame(currTile, newTile)) {
            if (isTilesTheSame(newTile, end)) {
                e.kill();
                playing.removeOneLive();
                return;
            }
            int newDir = roadDirArr[newTile.getyCord()][newTile.getxCord()];
            if (newDir != dir) {
                e.setPos(newTile.getxCord() * tilePixelNumber, newTile.getyCord() * tilePixelNumber);
                e.setLastDir(newDir);
            }
        }

    }

    private boolean isTilesTheSame(PathPoint currTile, PathPoint newTile) {
        if (currTile.getxCord() == newTile.getxCord())
            if (currTile.getyCord() == newTile.getyCord())
                return true;
        return false;
    }

    private PathPoint getEnemyTile(Enemy e) {

        switch (e.getLastDir()) {
            case LEFT:
                return new PathPoint((int) ((e.getX() + 63) / tilePixelNumber), (int) (e.getY() / tilePixelNumber));
            case UP:
                return new PathPoint((int) (e.getX() / tilePixelNumber), (int) ((e.getY() + 63) / tilePixelNumber));
            case RIGHT:
            case DOWN:
                return new PathPoint((int) (e.getX() / tilePixelNumber), (int) (e.getY() / tilePixelNumber));
        }
        return new PathPoint((int) (e.getX() / tilePixelNumber), (int) (e.getY() / tilePixelNumber));
    }
    public void spawnJuniors(float x, float y, int type) {
        for(Enemy e:enemies){
            if(e.getEnemyType()==type) {
                if (!e.isAlive()) {
                    e.reuse(x,y);
                }
            }
        }
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

        if (e.getEnemyType() == ORK_ZOMBI) {
            e.tickUp();
            if (e.getTick() < 50) {
                g.drawImage(enemyImages[1], (int) e.getX(), (int) e.getY(), null);
            } else {
                g.drawImage(enemyImages[4], (int) e.getX(), (int) e.getY(), null);
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

    public void reset() {
        enemies.clear();
    }

    public int[][] getRoadDirArr() {
        return roadDirArr;
    }


}
