package managers;

import enemies.*;
import helpz.LoadSave;
import helpz.Utilz;
import objects.PathPoint;
import scenes.Playing;

import static helpz.Constants.EnemyType.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static helpz.Constants.Direction.*;
import static helpz.Constants.EnemyType.ORC;

public class EnemyMenager {

    private Playing playing;
    private BufferedImage[] enemyImages;
    private ArrayList<Enemy> enemies = new ArrayList<>();
    private PathPoint start, end;
    private int HPbarWidth = 50;
    private BufferedImage[] enemyEfects;
    private int[][] roadDirArr;
    protected WaveManager waveManager;

    public EnemyMenager(Playing playing, PathPoint start, PathPoint end, WaveManager waveManager) {
        this.waveManager=waveManager;
        this.playing = playing;
        this.start = start;
        this.end = end;
        loadRoadDirArr();
        loadEnemyImages();
        loadEfectsImages();

    }

    private void loadRoadDirArr() {
         roadDirArr = Utilz.GetRoadDirArr(playing.getGame().getTileManager().getTypeArr(), start, end);
    }

    private void loadEfectsImages() {
        BufferedImage atlas = LoadSave.getSpriteAtlas();
        enemyEfects = new BufferedImage[2];
        enemyEfects[0] = atlas.getSubimage(8 * 64, 0 * 64, 64, 64);
        enemyEfects[1] = atlas.getSubimage(7 * 64, 0 * 64, 64, 64);
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
                enemies.add(new Orc(x, y, 0, this,waveManager));
                break;
            case SLIME:
                enemies.add(new Slime(x, y, 0, this,waveManager));
                break;
            case TENTACLE:
                enemies.add(new Tentacle(x, y, 0, this,waveManager));
                break;
            case ORK_ZOMBI:
                enemies.add(new OrcZombi(x, y, 0, this,waveManager));
                break;
        }
    }

    public void update() {
        for (Enemy e : enemies) {
            if (e.isAlive()) {
                updateEnemyMoveNew(e);
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
        if(e.doesRevive()){
            g.drawImage(enemyEfects[1], (int) e.getX()-2, (int) e.getY()-53, null);
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
                e.setPos(newTile.getxCord() * 64, newTile.getyCord() * 64);
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
                return new PathPoint((int) ((e.getX() + 63) / 64), (int) (e.getY() / 64));
            case UP:
                return new PathPoint((int) (e.getX() / 64), (int) ((e.getY() + 63) / 64));
            case RIGHT:
            case DOWN:
                return new PathPoint((int) (e.getX() / 64), (int) (e.getY() / 64));

        }

        return new PathPoint((int) (e.getX() / 64), (int) (e.getY() / 64));
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
                if (e.getTick() > 100) {
                    e.tickZero();
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

    public void reset(){
        enemies.clear();
    }

    public int[][] getRoadDirArr(){
        return roadDirArr;
    }
}
