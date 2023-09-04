package managers;

import enemies.Enemy;
import events.Wave;
import scenes.Playing;
import ui.ActionBar;

import java.util.ArrayList;
import java.util.Arrays;

public class WaveManager {

    private Playing playing;
    private ArrayList<Wave> waves = new ArrayList<>();
    private ProjectileManager projectileManager;
    private int enemySpownTickLimit = 60 * 1;
    private int enemySpownTick = enemySpownTickLimit;
    private int enemyIndex, waveIndex;
    private boolean waveStartTimer, waveTickTimerOver;
    private int waveTickLimit = 60 * 3, waveTick = 0;

    public WaveManager(Playing playing) {
        createWaves();
        this.playing = playing;

        this.projectileManager = playing.getProjectileManager();
    }

    public void update() {
        if (enemySpownTick < enemySpownTickLimit) {
            enemySpownTick++;
        }
        if (waveStartTimer) {
            waveTick++;
            if (waveStartTimer) {

                if (waveTick >= waveTickLimit) {
                    waveTickTimerOver = true;
                }
            }
        }
    }

    public void resetEnemyIndex() {
        enemyIndex = 0;
    }

    public void increaseWaveIndex() {
        enemySpownTickLimit = 600 / (waveIndex + 9);
        waveIndex++;
        waveTick = 0;
        waveStartTimer = false;
        waveTickTimerOver = false;
        playing.rewardPlayerAfterWave();
        projectileManager.endOfWave();

    }

    public boolean isWaveTimerOver() {
        return waveTickTimerOver;
    }


    public void startWaveTimer() {
        waveStartTimer = true;
    }

    public int getNextEnemy() {
        enemySpownTick = 0;
        return waves.get(waveIndex).getEnemyList().get(enemyIndex++);
    }

    private void createWaves() {
        waves.add(new Wave(new ArrayList<Integer>(Arrays.asList(1, 0))));
        waves.add(new Wave(new ArrayList<Integer>(Arrays.asList(2, 1, 1, 1))));
        waves.add(new Wave(new ArrayList<Integer>(Arrays.asList(1, 0, 0, 0, 0, 0, 1, 0, 1))));
        waves.add(new Wave(new ArrayList<Integer>(Arrays.asList(2, 1, 3, 1, 3, 1, 2, 0, 0, 1, 1, 1, 2))));
        waves.add(new Wave(new ArrayList<Integer>(Arrays.asList(1, 0, 0, 3, 1, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 1))));
        waves.add(new Wave(new ArrayList<Integer>(Arrays.asList(3, 1, 3, 3, 1, 1, 3, 1, 2, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 2))));
        waves.add(new Wave(new ArrayList<Integer>(Arrays.asList(2, 2, 3, 3, 1, 1, 3, 1, 2, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 1, 2))));
        waves.add(new Wave(new ArrayList<Integer>(Arrays.asList(1, 1, 3, 3, 1, 3, 1, 2, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1, 2))));
        waves.add(new Wave(new ArrayList<Integer>(Arrays.asList(0, 1, 3, 3, 1, 1, 3, 1, 2, 0, 3, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1, 2))));
        waves.add(new Wave(new ArrayList<Integer>(Arrays.asList(2, 3, 3, 3, 1, 1, 3, 1, 2, 0, 0, 0, 0, 0, 1, 2, 0, 0, 0, 0, 1, 1, 1, 2, 1, 1, 2))));
        waves.add(new Wave(new ArrayList<Integer>(Arrays.asList(0, 1, 3, 3, 1, 1, 3, 1, 2, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 1, 1, 1, 2, 1, 1, 2, 1, 1))));
        waves.add(new Wave(new ArrayList<Integer>(Arrays.asList(2, 0, 3, 3, 1, 1, 3, 1, 2, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1, 2, 1, 1, 2, 1, 3, 2))));
        waves.add(new Wave(new ArrayList<Integer>(Arrays.asList(2, 0, 3, 3, 2, 0, 3, 3, 1, 1, 3, 1, 2, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1, 2, 1, 1, 2, 1, 3, 2))));
        waves.add(new Wave(new ArrayList<Integer>(Arrays.asList(2, 3, 3, 3, 1, 1, 3, 1, 2, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 2, 0, 3, 3, 2, 0, 3, 3, 3, 3, 1, 1, 3, 1, 2, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1, 2, 1, 1, 2, 1, 3, 2))));
        waves.add(new Wave(new ArrayList<Integer>(Arrays.asList(2, 0, 2, 0, 3, 3, 3, 3, 1, 1, 3, 1, 2, 0, 1, 2, 0, 3, 3, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1, 2, 1, 1, 2, 1, 3, 2))));
        waves.add(new Wave(new ArrayList<Integer>(Arrays.asList(2, 0, 2, 0, 3, 3, 2, 0, 3, 3, 3, 3, 1, 1, 3, 1, 2, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1, 2, 1, 1, 2, 1, 3, 2))));
        waves.add(new Wave(new ArrayList<Integer>(Arrays.asList(2, 0, 3, 3, 1, 1, 3, 1, 2, 0, 1, 0, 0, 0, 1, 0, 0, 0, 2, 0, 3, 3, 0, 0, 1, 1, 2, 0, 3, 3, 1, 2, 1, 1, 2, 1, 3, 2))));
        waves.add(new Wave(new ArrayList<Integer>(Arrays.asList(2, 0, 3, 3, 1, 1, 3, 1, 2, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1, 2, 1, 1, 2, 1, 3, 2))));
        waves.add(new Wave(new ArrayList<Integer>(Arrays.asList(2, 1, 0, 0, 0, 0, 0, 1, 1, 1, 0, 3, 3, 1, 1, 3, 1, 2, 0, 1, 0, 0, 0, 1, 0, 0, 0, 2, 0, 3, 3, 0, 0, 1, 1, 2, 0, 3, 3, 1, 2, 1, 1, 2, 1, 3, 2))));
        waves.add(new Wave(new ArrayList<Integer>(Arrays.asList(2, 0, 3, 3, 1, 1, 3, 1, 2, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1, 2, 1, 1, 2, 1, 3, 2, 3, 3, 3, 1, 1, 3, 1, 2, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 2))));
        waves.add(new Wave(new ArrayList<Integer>(Arrays.asList(2, 0, 3, 3, 1, 1, 3, 1, 2, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 3, 3, 3, 1, 1, 3, 1, 2, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1, 2, 1, 1, 2, 1, 3, 2))));
        waves.add(new Wave(new ArrayList<Integer>(Arrays.asList(2, 0, 3, 3, 1, 1, 3, 1, 2, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1, 2, 1, 1, 2, 1, 3, 2))));
        waves.add(new Wave(new ArrayList<Integer>(Arrays.asList(2, 1, 0, 0, 0, 0, 0, 1, 1, 1, 0, 3, 3, 1, 1, 3, 1, 2, 0, 1, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1, 2, 1, 1, 2, 1, 3, 2))));
        waves.add(new Wave(new ArrayList<Integer>(Arrays.asList(1, 0, 0, 0, 0, 0, 1, 1, 1, 2, 0, 3, 3, 1, 1, 3, 1, 2, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1, 2, 1, 1, 2, 1, 3, 2))));
        waves.add(new Wave(new ArrayList<Integer>(Arrays.asList(2, 0, 3, 3, 1, 1, 3, 1, 2, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1, 2, 1, 1, 2, 1, 3, 2))));
        waves.add(new Wave(new ArrayList<Integer>(Arrays.asList(2, 0, 3, 3, 1, 1, 3, 1, 2, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 1, 1, 1, 2, 1, 1, 2, 1, 3, 2))));
        waves.add(new Wave(new ArrayList<Integer>(Arrays.asList(2, 0, 3, 3, 1, 1, 3, 1, 2, 0, 1, 1, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 1, 1, 1, 2, 1, 1, 2, 1, 3, 2))));
        waves.add(new Wave(new ArrayList<Integer>(Arrays.asList(2, 0, 3, 3, 1, 1, 3, 1, 2, 0, 1, 1, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1, 2, 1, 1, 2, 1, 3, 2))));
        waves.add(new Wave(new ArrayList<Integer>(Arrays.asList(2, 0, 3, 3, 1, 1, 3, 1, 2, 0, 1, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1, 2, 1, 1, 2, 1, 3, 2))));
        waves.add(new Wave(new ArrayList<Integer>(Arrays.asList(2, 0, 3, 3, 1, 1, 3, 1, 2, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1, 2, 1, 1, 2, 1, 3, 2))));
    }

    public int getWaveIndex() {
        return waveIndex + 1;
    }

    public int getWaveMax() {
        return waves.size();
    }

    public ArrayList<Wave> getWaves() {
        return waves;
    }

    public boolean isTimeForNewEnemy() {
        return enemySpownTick >= enemySpownTickLimit;
    }

    public boolean isTherMoreEnemysInWave() {
        return waves.get(waveIndex).getEnemyList().size() > enemyIndex;
    }

    public boolean isThereMoreWaves() {
        return waveIndex + 1 < waves.size();
    }

    public void reset() {
        waves.clear();
        createWaves();
        enemyIndex = 0;
        waveIndex = 0;
        waveStartTimer = false;
        waveTickTimerOver = false;
        enemySpownTick = enemySpownTickLimit;
        waveTick = 0;
    }
}
