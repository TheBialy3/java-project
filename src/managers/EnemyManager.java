package managers;

import enemies.*;
import helpz.Constants;
import helpz.LoadSave;

import objects.PathPoint;
import scenes.Playing;

import static helpz.Constants.EnemyType.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

import static helpz.Constants.NumbersOf.NUMBER_OF_ENEMIES;

public class EnemyManager {

    private Random random = new Random();
    private Playing playing;
    private BufferedImage[] enemyImages;
    private ArrayList<Enemy> enemies = new ArrayList<>();
    private ArrayList<PathPoint> wayForEnemies = new ArrayList<>();
    private int HPBarWidth = 50, indexOfPoisonAnimation = 0, tilePixelNumber = 64;

    private int ranL = random.nextInt(25), ranR = random.nextInt(25);
    private BufferedImage[] enemyEffects;

    protected WaveManager waveManager;
    protected TowerManager towerManager;

    private boolean Card7 = false, Card8 = false, Card9 = false, Card10 = false, Card11 = false, Card12 = false;

    public EnemyManager(Playing playing, WaveManager waveManager ,TowerManager towerManager) {
        this.waveManager = waveManager;
        this.towerManager=towerManager;
        this.playing = playing;
        loadRoadDirArr();
        loadEnemyImages();
        loadEffectsImages();
    }

    public void update() {
        indexOfPoisonAnimation++;
//        try {
            for (Enemy e : enemies) {
                if (e.isAlive()) {
                    e.update();
                    e.updateEnemyCountdown();
                }
            }
//        } catch (Exception e) {
//            System.out.println("ConcurrentModificationException update enemy");
//        }
        if (indexOfPoisonAnimation > 150) {
            enemyReorder();
            indexOfPoisonAnimation = 0;
            resetRand();
        }
    }
    public void draw(Graphics g) {
        try {
            for (Enemy e : enemies) {
                if (e.isAlive()) {
                    drawEnemy(e, g);
                    drawHealthBar(e, g);
                    drawEffects(e, g);
                }
            }
        } catch (Exception e) {
            System.out.println("ConcurrentModificationException draw EnemyManager");
        }
    }

    private void loadRoadDirArr() {
        wayForEnemies = LoadSave.GetLevelDir();
    }

    private void loadEffectsImages() {
        BufferedImage atlas = LoadSave.getSpriteAtlas();
        enemyEffects = new BufferedImage[3];
        for (int i = 0; i < 3; i++) {
            enemyEffects[i] = atlas.getSubimage(0 + i * tilePixelNumber, 22 * tilePixelNumber, tilePixelNumber, tilePixelNumber);
        }
    }

    private void loadEnemyImages() {
        BufferedImage atlas = LoadSave.getSpriteAtlas();
        enemyImages = new BufferedImage[NUMBER_OF_ENEMIES + 1];
        for (int i = 0; i < NUMBER_OF_ENEMIES; i++) {
            enemyImages[i] = atlas.getSubimage(0+(i/10)*2* tilePixelNumber , (2 + i%10 ) * tilePixelNumber, tilePixelNumber, tilePixelNumber);
        }
        enemyImages[NUMBER_OF_ENEMIES] = atlas.getSubimage(1 * tilePixelNumber, (11)* tilePixelNumber, tilePixelNumber, tilePixelNumber);
    }

    public void spawnEnemy(int nextEnemy) {
        addEnemy(nextEnemy);
    }

    public void addEnemy(int enemyType) {///modify wave manager
        int x = wayForEnemies.get(0).getxCord() ;
        int y = wayForEnemies.get(0).getyCord() ;
        switch (enemyType) {
            case ORC:
                enemies.add(new Orc(x, y, 0, ORC,wayForEnemies,this, waveManager, towerManager));
                break;
            case SLIME:
                enemies.add(new Slime(x, y, 0,SLIME ,wayForEnemies,this, waveManager, towerManager));
                break;
            case TENTACLE:
                enemies.add(new Tentacle(x, y, 0,TENTACLE ,wayForEnemies,this, waveManager, towerManager));
                break;
            case ORK_ZOMBIE:
                enemies.add(new OrcZombie(x, y, 0,ORK_ZOMBIE ,wayForEnemies,this, waveManager, towerManager));
                break;
            case CAMEL:
                enemies.add(new Camel(x, y, 0,CAMEL ,wayForEnemies,this, waveManager, towerManager));
                enemies.add(new CamelJunior(x, y, 0, CAMEL_JUNIOR,wayForEnemies,this, waveManager, towerManager));
                break;
            case CAMEL_JUNIOR:
                enemies.add(new CamelJunior(x, y, 0,CAMEL_JUNIOR ,wayForEnemies,this, waveManager, towerManager));
                break;
            case BIRD:
                enemies.add(new Bird(x, y, 0,BIRD ,wayForEnemies,this, waveManager, towerManager));
                break;
            case GHOST:
                enemies.add(new Ghost(x, y, 0,GHOST ,wayForEnemies,this, waveManager, towerManager));
                break;
            case ROCK:
                enemies.add(new Rock(x, y, 0,ROCK ,wayForEnemies,this, waveManager, towerManager));
                break;
            case CREEPY_CAT:
                enemies.add(new CreepyCat(x, y, 0,CREEPY_CAT ,wayForEnemies,this, waveManager, towerManager));
                break;
            case BIRD_SKELETON:
                enemies.add(new BirdSkeleton(x, y, 0,BIRD_SKELETON ,wayForEnemies,this, waveManager, towerManager));
                break;
            case BANSHEE:
                enemies.add(new Banshee(x, y, 0, BANSHEE,wayForEnemies,this, waveManager, towerManager));
                break;

        }
    }



    private void enemyReorder() {
        Collections.sort(enemies, new Comparator<Enemy>() {
            public int compare(Enemy e1, Enemy e2) {
                return Integer.valueOf((int) e2.getDistancePast()).compareTo((int) e1.getDistancePast());
            }
        });
    }


    public void resetRand() {
        ranL = random.nextInt(25);
        ranR = random.nextInt(25);
    }


    private void drawEffects(Enemy e, Graphics g) {

        if (e.isSlowd()) {
            g.drawImage(enemyEffects[1], (int) e.getX(), (int) e.getY(), null);
        }
        if (e.doesRevive()) {
            g.drawImage(enemyEffects[0], (int) e.getX() - 2, (int) e.getY() - 53, null);
        }
        //effect Poison
        if (e.isPoisoned()) {
            if ((indexOfPoisonAnimation / 8) % 4 == 4) {
                g.drawImage(enemyEffects[2], (int) e.getX() - 26 + ranR + ((indexOfPoisonAnimation / 5) % 2), (int) e.getY() + 25 - (indexOfPoisonAnimation / 3), null);
                g.drawImage(enemyEffects[2], (int) e.getX() + ranL + ((indexOfPoisonAnimation / 5) % 2), (int) e.getY() + 25 - (((indexOfPoisonAnimation + 75) % 150) / 3), null);
            } else {
                g.drawImage(enemyEffects[2], (int) e.getX() - 26 + ranR + ((indexOfPoisonAnimation / 5) % 4), (int) e.getY() + 25 - (indexOfPoisonAnimation / 3), null);
                g.drawImage(enemyEffects[2], (int) e.getX() + ranL + ((indexOfPoisonAnimation / 5) % 4), (int) e.getY() + 25 - (((indexOfPoisonAnimation + 75) % 150) / 3), null);
            }

        }
    }


    public void playingRemoveOneLive(){
        playing.removeOneLive();
    }

    private boolean isTilesTheSame(PathPoint currTile, PathPoint newTile) {
        if (currTile.getxCord() == newTile.getxCord())
            if (currTile.getyCord() == newTile.getyCord())
                return true;
        return false;
    }

    public void spawnJuniors(float x, float y, int type, float distancePast, PathPoint nextPathPoint) {
        int shift = 0;
        for (Enemy e : enemies) {
            if (e.getEnemyType() == type) {
                if (!e.isAlive()) {
                    e.reuse(x + shift, y + shift, distancePast, nextPathPoint);
                    shift++;
                }
            }
        }
        enemyReorder();
    }

    private void drawHealthBar(Enemy e, Graphics g) {
        g.setColor(Color.RED);
        g.fillRect((int) e.getX() + 32 - (getNewHealthBar(e) / 2), (int) e.getY() - 10, getNewHealthBar(e), 6);
        getNewHealthBar(e);
        g.setColor(Color.BLACK);
        g.drawRect((int) e.getX() + 32 - (HPBarWidth / 2), (int) e.getY() - 10, HPBarWidth, 6);

    }

    private int getNewHealthBar(Enemy e) {
        return (int) (HPBarWidth * e.getHealthBar());
    }

    public void drawEnemy(Enemy e, Graphics g) {/////////to fix
        if (e.getEnemyType() == CREEPY_CAT) {
            if (!e.isTargetable()) {
                g.drawImage(enemyImages[NUMBER_OF_ENEMIES], (int) e.getX(), (int) e.getY(), null);
            } else {
                drawBasic(e,g);
            }
        } else {
           drawBasic(e,g);
        }
    }

    private void drawBasic(Enemy e, Graphics g) {
        g.drawImage(enemyImages[e.getEnemyType()], (int) e.getX(), (int) e.getY(), null);
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
        int earnGold = Constants.EnemyType.getGoldWorth(enemyType);
        if (isCard7()) {
            if (Constants.EnemyType.getGoldWorth(enemyType) < 10) {
                earnGold = earnGold * 2;
            } else {
                earnGold = earnGold + 10;
            }
        }
        if (isCard8()) {
            if (Constants.EnemyType.getGoldWorth(enemyType) < 10) {
                earnGold = earnGold * 2;
            } else {
                earnGold = earnGold + 10;
            }
        }
        playing.rewardPlayer(earnGold);
    }

    public void reset() {
        enemies.clear();
        setAllCardsBoolFalse();

    }

    private void setAllCardsBoolFalse() {
        Card7 = false;
        Card8 = false;
        Card9 = false;
        Card10 = false;
        Card11 = false;
        Card12 = false;
    }


    public boolean isCard7() {
        return Card7;
    }

    public void setCard7(boolean card7) {
        Card7 = card7;
    }

    public boolean isCard8() {
        return Card8;
    }

    public void setCard8(boolean card8) {
        Card8 = card8;
    }

    public boolean isCard9() {
        return Card9;
    }

    public void setCard9(boolean card9) {
        Card9 = card9;
    }

    public boolean isCard10() {
        return Card10;
    }

    public void setCard10(boolean card10) {
        Card10 = card10;
    }

    public boolean isCard11() {
        return Card11;
    }

    public void setCard11(boolean card11) {
        Card11 = card11;
    }

    public boolean isCard12() {
        return Card12;
    }

    public void setCard12(boolean card12) {
        Card12 = card12;
    }

    public BufferedImage[] getEnemyImages() {
        return enemyImages;
    }
}
