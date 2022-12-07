package helpz;

import objects.PathPoint;

import java.util.ArrayList;

import static helpz.Constants.Direction.*;
import static helpz.Constants.Tiles.ROAD_TILE;

public class Utilz {

    public static int[][] GetRoadDirArr(int[][] lvlTypeArr, PathPoint start, PathPoint end) {
        int[][] roadDirArr = new int[lvlTypeArr.length][lvlTypeArr[0].length];

        PathPoint currentTile = start;
        int lastdir = -1;

        while (!isCurrAtEnd(currentTile, end)) {
            PathPoint previousTile = currentTile;
            currentTile = GetNextRoadTile(previousTile, lastdir, lvlTypeArr);
            lastdir = GetDirFromPrevToCurr(previousTile,currentTile);
            roadDirArr[previousTile.getyCord()][previousTile.getxCord()]=lastdir;
        }
        roadDirArr[end.getyCord()][end.getxCord()]=lastdir;
        return roadDirArr;
    }

    private static int GetDirFromPrevToCurr(PathPoint previousTile, PathPoint currentTile) {
        if(previousTile.getxCord()== currentTile.getxCord()){
            if(previousTile.getyCord()>currentTile.getyCord()){
                return UP;
            }else{
                return DOWN;
            }
        }
        else{
            if(previousTile.getxCord()>currentTile.getxCord()){
                return LEFT;
            }else{
                return RIGHT;
            }
        }
    }

    private static PathPoint GetNextRoadTile(PathPoint previousTile, int lastdir, int[][] lvlTypeArr) {
        int testDir = lastdir;
        PathPoint testTile = GetTileInDir(previousTile, testDir, lastdir);

        while (!isTileRoad(testTile, lvlTypeArr)) {
            testDir++;
            testDir%=4;
            testTile = GetTileInDir(previousTile, testDir, lastdir);
        }

        return testTile;
    }

    private static boolean isTileRoad(PathPoint testTile, int[][] lvlTypeArr) {
        if (testTile != null) {
            if (testTile.getxCord() >= 0) {
                if (testTile.getxCord() <= lvlTypeArr[0].length) {
                    if (testTile.getyCord() >= 0) {
                        if (testTile.getyCord() <= lvlTypeArr.length) {
                            if (lvlTypeArr[testTile.getyCord()][testTile.getxCord()] == ROAD_TILE) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    private static PathPoint GetTileInDir(PathPoint previousTile, int testDir, int lastdir) {
        switch (testDir) {
            case LEFT:
                if (lastdir != RIGHT) {
                    return new PathPoint(previousTile.getxCord() - 1, previousTile.getyCord());
                }
            case UP:
                if (lastdir != DOWN) {
                    return new PathPoint(previousTile.getxCord(), previousTile.getyCord() - 1);
                }
            case RIGHT:
                if (lastdir != LEFT) {
                    return new PathPoint(previousTile.getxCord() + 1, previousTile.getyCord());
                }
            case DOWN:
                if (lastdir != UP) {
                    return new PathPoint(previousTile.getxCord(), previousTile.getyCord() + 1);
                }
        }
        return null;
    }

    private static boolean isCurrAtEnd(PathPoint currentTile, PathPoint end) {
        if (currentTile.getxCord() == end.getxCord()) {
            if (currentTile.getyCord() == end.getyCord()) {
                return true;
            }
        }
        return false;
    }


    public static int[][] ArrayListTo2Dint(ArrayList<Integer> list, int ySize, int xSize) {
        int[][] newArr = new int[ySize][xSize];

        for (int j = 0; j < newArr.length; j++) {
            for (int i = 0; i < newArr[j].length; i++) {
                int index = j * ySize + i;
                newArr[j][i] = list.get(index);
            }
        }
        return newArr;
    }

    public static int[] TwoDTo1Dint(int[][] twoArr) {
        int[] oneArr = new int[twoArr.length * twoArr[0].length];
        for (int j = 0; j < twoArr.length; j++) {
            for (int i = 0; i < twoArr[j].length; i++) {
                int index = j * twoArr.length + i;
                oneArr[index] = twoArr[j][i];
            }
        }
        return oneArr;
    }

    public static int GetHypoDistance(float x1, float y1, float x2, float y2) {
        float xDiff = Math.abs(x1 - x2);
        float yDiff = Math.abs(y1 - y2);

        return (int) Math.hypot(xDiff, yDiff);
    }
}
