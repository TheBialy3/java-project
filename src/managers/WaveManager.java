package managers;

import events.Wave;
import scenes.Playing;

import java.util.ArrayList;
import java.util.Arrays;

public class WaveManager {

    private Playing playing;
    private ArrayList<Wave> waves = new ArrayList<>();

    private int enemySpowntickLimit = 60 * 1;
    private int enemySpowntick = enemySpowntickLimit;
    private int enemyIndex, waveIndex;

    public WaveManager(Playing playing) {
        this.playing = playing;
        createWaves();
    }

    public void update() {
        if (enemySpowntick < enemySpowntickLimit) {
            enemySpowntick++;
        } else {
            enemySpowntick = 0;
        }
    }

    public int getNextEnemy() {
        return waves.get(waveIndex).getEnemyList().get(enemyIndex++);
    }

    private void createWaves() {
        waves.add(new Wave(new ArrayList<Integer>(Arrays.asList(1, 0, 0, 0, 0, 0, 1))));
    }

    public ArrayList<Wave> getWaves() {
        return waves;
    }

    public boolean isTimeForNewWave() {
        return enemySpowntick >= enemySpowntickLimit;
    }

    public boolean isTherMoreEnemysInWave() {
        return waves.get(waveIndex).getEnemyList().size() >enemyIndex ;
    }
}
