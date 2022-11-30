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

    private int enemySpownTickLimit = 60 * 1;
    private int enemySpownTick = enemySpownTickLimit;
    private int enemyIndex, waveIndex;
    private boolean waveStartTimer, waveTickTimerOver;
    private int waveTickLimit = 60 * 13;
    private int waveTick = 0;

    public WaveManager(Playing playing) {
        this.playing = playing;
        createWaves();
    }

    public void update() {
        if (enemySpownTick < enemySpownTickLimit) {
            enemySpownTick++;
        }
        if (waveStartTimer) {
            waveTick++;
            if (waveStartTimer) {

                if(waveTick>=waveTickLimit){
                    waveTickTimerOver = true;
                }
            }
        }
    }

    public void resetEnemyIndex() {
        enemyIndex = 0;
    }

//    public void reset() {
//        waves.clear();
//        createWaves();
//        enemyIndex = 0;
//        waveIndex = 0;
//        waveStartTimer = false;
//        waveTickTimerOver = false;
//        waveTick = 0;
//        enemySpawnTick = enemySpawnTickLimit;
//    }

    public void increaseWaveIndex() {
        waveIndex++;
        waveTick =0;
        waveStartTimer = false;
        waveTickTimerOver = false;
        playing.rewardPlayerAfterWave();
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
        waves.add(new Wave(new ArrayList<Integer>(Arrays.asList(1, 0, 0, 0, 0,3, 1,3, 1, 0, 1))));
        waves.add(new Wave(new ArrayList<Integer>(Arrays.asList(2, 1, 3, 1, 3, 1, 2))));
        waves.add(new Wave(new ArrayList<Integer>(Arrays.asList(1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1))));
        waves.add(new Wave(new ArrayList<Integer>(Arrays.asList(2, 1, 3, 1, 3, 1, 2, 0, 0, 0, 0, 0, 1))));
        waves.add(new Wave(new ArrayList<Integer>(Arrays.asList(1, 0, 0,3, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1))));
        waves.add(new Wave(new ArrayList<Integer>(Arrays.asList(2, 1, 3,3, 1, 1, 3, 1, 2, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1))));
    }

    public int getWaveIndex() {
        return waveIndex+1;
    }
    public int getWaveMax() {
        return waves.size();
    }

    public ArrayList<Wave> getWaves() {
        return waves;
    }

    public boolean isTimeForNewWave() {
        return enemySpownTick >= enemySpownTickLimit;
    }

    public boolean isTherMoreEnemysInWave() {
        return waves.get(waveIndex).getEnemyList().size() > enemyIndex;
    }

    public boolean isThereMoreWaves() {
        return waveIndex + 1 < waves.size();
    }

    public float getTimeLeft() {
        float TimeLeft = waveTickLimit - waveTick;
        return TimeLeft / 60;
    }

    public boolean isWaveTimerStarted() {
        return waveStartTimer;
    }


}
