package managers;

import events.Wave;
import scenes.Playing;

import java.util.ArrayList;
import java.util.Random;

public class WaveManager {

    private Playing playing;
    private ArrayList<Wave> waves = new ArrayList<>();
    private ProjectileManager projectileManager;
    private int enemySpawnTickLimit = 60 * 1;
    private int enemySpawnTick = enemySpawnTickLimit;
    private int enemyIndex, waveIndex;
    private boolean waveStartTimer, waveTickTimerOver;
    private int waveTickLimit = 60 * 3, waveTick = 0;
    private Random random = new Random();

    public WaveManager(Playing playing) {
        createWaves();
        this.playing = playing;

        this.projectileManager = playing.getProjectileManager();
    }

    public void update() {
        if (enemySpawnTick < enemySpawnTickLimit) {
            enemySpawnTick++;
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
        enemySpawnTickLimit = 600 / (waveIndex + 9);
        waveIndex++;
        waveTick = 0;
        waveStartTimer = false;
        waveTickTimerOver = false;
        playing.rewardPlayerAfterWave();
        projectileManager.endOfWave();
        playing.beamReset();
    }

    public boolean isWaveTimerOver() {
        return waveTickTimerOver;
    }


    public void startWaveTimer() {
        waveStartTimer = true;
    }

    public int getNextEnemy() {
        enemySpawnTick = 0;
        return waves.get(waveIndex).getEnemyList().get(enemyIndex++);
    }

    private void createWaves() {
        int waveNumberMax=100;
        int enemyTypesNumber=8;
        //enemyTypesNumber=6;
        int ran;
        for(int waveNumber=0;waveNumber<waveNumberMax;waveNumber++){
            ArrayList<Integer> randList=new ArrayList<>();
            for(int waveEnemyNumber=0;waveEnemyNumber<waveNumber*2;waveEnemyNumber++){
                ran = random.nextInt(enemyTypesNumber);
                randList.add(ran);
            }
            waves.add(new Wave(randList));
        }
    }

    public int getWaveIndex() {
        return waveIndex + 1;
    }

    public int getWaveMax() {
        return waves.size();
    }

    public boolean isTimeForNewEnemy() {
        return enemySpawnTick >= enemySpawnTickLimit;
    }

    public boolean isTheirMoreEnemyInWave() {
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
        enemySpawnTick = enemySpawnTickLimit;
        waveTick = 0;
    }
}
