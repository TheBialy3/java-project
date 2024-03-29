package managers;

import helpz.ImgFix;
import helpz.LoadSave;
import objects.Tile;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static helpz.Constants.Tiles.*;

public class TileManager {
    //commented can be used in future ass well as not used but i need to add mos sprites
    public Tile GRASS, WATER, ROAD, ROAD_TB, GRASS_DARK, GRASS_ORANGE;
    public Tile TL_WATER_CORNER, TR_WATER_CORNER, BR_WATER_CORNER, BL_WATER_CORNER;
    public Tile TL_ROAD, TR_ROAD, BR_ROAD, BL_ROAD;
    public Tile B_ROAD, L_ROAD, R_ROAD, T_ROAD, null_ROAD;
    public Tile TL_WATER_ISLE, TR_WATER_ISLE, BR_WATER_ISLE, BL_WATER_ISLE;
    public Tile L_WATER, R_WATER, B_WATER, T_WATER;
    private BufferedImage atlas;
    public ArrayList<Tile> grassT = new ArrayList<>();
    public ArrayList<Tile> tiles = new ArrayList<>();
    public ArrayList<Tile> roadS = new ArrayList<>();
//    public ArrayList<Tile> roadC = new ArrayList<>();
    public ArrayList<Tile> roadDir = new ArrayList<>();
    public ArrayList<Tile> roadDirNull = new ArrayList<>();
    public ArrayList<Tile> waterC = new ArrayList<>();
    public ArrayList<Tile> waterB = new ArrayList<>();
    public ArrayList<Tile> waterI = new ArrayList<>();

    public TileManager() {
        loadAtlas();
        createTiles();

    }


    private void createTiles() {
        int id = 0;
        int waterx = 0;
        int watery = 27;

        tiles.add(WATER = new Tile(getAnimateSprite(waterx, watery), id++, WATER_TILE));
        grassT.add(GRASS = new Tile(getSprite(2, 0), id++, GRASS_TILE));
        grassT.add(GRASS_DARK = new Tile(getSprite(3, 0), id++, GRASS_TILE));
       // grassT.add(GRASS_ORANGE = new Tile(getSprite(0, 0), id++, GRASS_TILE));

        waterC.add(TL_WATER_CORNER = new Tile(ImgFix.getBuildRotImg(getAnimateSprite(waterx, watery), getSprite(4, 0), 0), id++, WATER_TILE));
        waterC.add(TR_WATER_CORNER = new Tile(ImgFix.getBuildRotImg(getAnimateSprite(waterx, watery), getSprite(4, 0), 90), id++, WATER_TILE));
        waterC.add(BR_WATER_CORNER = new Tile(ImgFix.getBuildRotImg(getAnimateSprite(waterx, watery), getSprite(4, 0), 180), id++, WATER_TILE));
        waterC.add(BL_WATER_CORNER = new Tile(ImgFix.getBuildRotImg(getAnimateSprite(waterx, watery), getSprite(4, 0), 270), id++, WATER_TILE));

        waterI.add(TL_WATER_ISLE = new Tile(ImgFix.getBuildRotImg(getAnimateSprite(waterx, watery), getSprite(3, 1), 0), id++, WATER_TILE));
        waterI.add(TR_WATER_ISLE = new Tile(ImgFix.getBuildRotImg(getAnimateSprite(waterx, watery), getSprite(3, 1), 90), id++, WATER_TILE));
        waterI.add(BR_WATER_ISLE = new Tile(ImgFix.getBuildRotImg(getAnimateSprite(waterx, watery), getSprite(3, 1), 180), id++, WATER_TILE));
        waterI.add(BL_WATER_ISLE = new Tile(ImgFix.getBuildRotImg(getAnimateSprite(waterx, watery), getSprite(3, 1), 270), id++, WATER_TILE));

        waterB.add(T_WATER = new Tile(ImgFix.getBuildRotImg(getAnimateSprite(waterx, watery), getSprite(4, 1), 0), id++, WATER_TILE));
        waterB.add(R_WATER = new Tile(ImgFix.getBuildRotImg(getAnimateSprite(waterx, watery), getSprite(4, 1), 90), id++, WATER_TILE));
        waterB.add(B_WATER = new Tile(ImgFix.getBuildRotImg(getAnimateSprite(waterx, watery), getSprite(4, 1), 180), id++, WATER_TILE));
        waterB.add(L_WATER = new Tile(ImgFix.getBuildRotImg(getAnimateSprite(waterx, watery), getSprite(4, 1), 270), id++, WATER_TILE));

        roadS.add(ROAD = new Tile(getSprite(2, 1), id++, ROAD_TILE));
        roadS.add(ROAD_TB = new Tile(ImgFix.getRotImg(getSprite(2, 1), 90), id++, ROAD_TILE));

//can be used in future
//        roadC.add(TL_ROAD = new Tile(getSprite(2, 1), id++, ROAD_TILE));
//        roadC.add(TR_ROAD = new Tile(ImgFix.getRotImg(getSprite(2, 1), 90), id++, ROAD_TILE));
//        roadC.add(BR_ROAD = new Tile(ImgFix.getRotImg(getSprite(2, 1), 180), id++, ROAD_TILE));
//        roadC.add(BL_ROAD = new Tile(ImgFix.getRotImg(getSprite(2, 1), 270), id++, ROAD_TILE));


        roadDirNull.add(null_ROAD = new Tile(getSprite(0, 1), id++, ROAD_DIR));
        roadDir.add(B_ROAD = new Tile(getSprite(1, 1), id++, ROAD_DIR));
        roadDir.add(L_ROAD = new Tile(ImgFix.getRotImg(getSprite(1, 1), 90), id++, ROAD_DIR));
        roadDir.add(T_ROAD = new Tile(ImgFix.getRotImg(getSprite(1, 1), 180), id++, ROAD_DIR));
        roadDir.add(R_ROAD = new Tile(ImgFix.getRotImg(getSprite(1, 1), 270), id++, ROAD_DIR));

        tiles.addAll(grassT);
        tiles.addAll(waterC);
        tiles.addAll(waterI);
        tiles.addAll(waterB);
        tiles.addAll(roadS);
//        tiles.addAll(roadC);
        tiles.addAll(roadDirNull);
        tiles.addAll(roadDir);
    }

//can be used in future
//    private BufferedImage[] getImgs(int fX, int fY, int sX, int sY) {
//        return new BufferedImage[]{getSprite(fX, fY), getSprite(sX, sY)};
//    }

    public int[][] getTypeArr() {
        int[][] idArr = LoadSave.GetLevelData();
        int[][] typeArr = new int[idArr.length][idArr[0].length];

        for (int j = 0; j < idArr.length; j++) {
            for (int i = 0; i < idArr[j].length; i++) {
                int id = idArr[j][i];
                typeArr[j][i] = tiles.get(id).getTileType();
            }
        }

        return typeArr;

    }

    public Tile getTile(int id) {
        return tiles.get(id);
    }

    private void loadAtlas() {
        atlas = LoadSave.getSpriteAtlas();
    }

    public BufferedImage getSprite(int id) {
        return tiles.get(id).getSprite();
    }

    public BufferedImage getAnimSprite(int id, int animationIndex) {
        return tiles.get(id).getSprite(animationIndex);
    }

    private BufferedImage[] getAnimateSprite(int xCord, int yCord) {
        int jumps =704;
        BufferedImage[] arr = new BufferedImage[jumps];
        for (int i = 0; i < jumps; i++) {
            arr[i] = getWaterSprite(xCord*64 +  i , yCord * (64));
        }
        return arr;
    }

    private BufferedImage getWaterSprite(int xCord, int yCord) {
        return atlas.getSubimage(xCord, yCord, 64, 64);
    }

    private BufferedImage getSprite(int xCord, int yCord) {
        return atlas.getSubimage(xCord * 64, yCord * 64, 64, 64);
    }

    public boolean isSpriteAnimated(int spryteID) {
        return tiles.get(spryteID).isAnimated();
    }

    public ArrayList<Tile> getRoadS() {
        return roadS;
    }

    public ArrayList<Tile> getRoadDir() {
        return roadDir;
    }
    public ArrayList<Tile> getRoadDirNull() {
        return roadDirNull;
    }

//    public ArrayList<Tile> getRoadC() {
//        return roadC;
//    }

    public ArrayList<Tile> getWaterC() {
        return waterC;
    }

    public ArrayList<Tile> getWaterB() {
        return waterB;
    }

    public ArrayList<Tile> getWaterI() {
        return waterI;
    }

    public ArrayList<Tile> getGrassT() {
        return grassT;
    }
}
