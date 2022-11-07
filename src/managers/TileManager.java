package managers;

import helpz.ImgFix;
import helpz.LoadSave;
import objects.Tile;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class TileManager {

    public Tile GRASS,WATER,ROAD,ROAD_TB,GRASS_DARK,GRASS_ORANGE;
    public Tile TL_WATER_CORNER,TR_WATER_CORNER,BR_WATER_CORNER,BL_WATER_CORNER;
    public Tile TL_ROAD,TR_ROAD,BR_ROAD,BL_ROAD;
    public Tile TL_WATER_ISLE,TR_WATER_ISLE,BR_WATER_ISLE,BL_WATER_ISLE;
    public Tile L_WATER,R_WATER,B_WATER,T_WATER;
    private BufferedImage atlas;
    public ArrayList<Tile>  grassT= new ArrayList<>();
    public ArrayList<Tile>  tiles= new ArrayList<>();
    public ArrayList<Tile>  roadS= new ArrayList<>();
    public ArrayList<Tile>  roadC= new ArrayList<>();
    public ArrayList<Tile>  waterC= new ArrayList<>();
    public ArrayList<Tile>  waterB= new ArrayList<>();
    public ArrayList<Tile>  waterI= new ArrayList<>();

    public TileManager() {

        loadAtlas();
        createTiles();

    }


    private void createTiles() {
        int id=0;


        tiles.add(WATER=new Tile(getAniSprite(1,0),id++,"Water"));
        grassT.add(GRASS=new Tile(getSprite(1,2),id++,"Grass"));
        grassT.add(GRASS_DARK=new Tile(getSprite(2,3),id++,"GrassDark"));
        grassT.add(GRASS_ORANGE=new Tile(getSprite(0,3),id++,"GrassOrange"));

        waterC.add(TL_WATER_CORNER=new Tile(ImgFix.getBuildRotImg(getAniSprite(1,0),getSprite( 5, 2),0),id++,"Water corner TL"));
        waterC.add(TR_WATER_CORNER=new Tile(ImgFix.getBuildRotImg(getAniSprite(1,0),getSprite( 5, 2),90),id++,"Water corner TR"));
        waterC.add(BR_WATER_CORNER=new Tile(ImgFix.getBuildRotImg(getAniSprite(1,0),getSprite( 5, 2),180),id++,"Water corner BR"));
        waterC.add(BL_WATER_CORNER=new Tile(ImgFix.getBuildRotImg(getAniSprite(1,0),getSprite( 5, 2),270),id++,"Water corner BL"));

        waterI.add(TL_WATER_ISLE=new Tile(ImgFix.getBuildRotImg(getAniSprite(1,0),getSprite( 6, 1),0),id++,"Water island TL"));
        waterI.add(TR_WATER_ISLE=new Tile(ImgFix.getBuildRotImg(getAniSprite(1,0),getSprite( 6, 1),90),id++,"Water island TR"));
        waterI.add(BR_WATER_ISLE=new Tile(ImgFix.getBuildRotImg(getAniSprite(1,0),getSprite( 6, 1),180),id++,"Water island BR"));
        waterI.add(BL_WATER_ISLE=new Tile(ImgFix.getBuildRotImg(getAniSprite(1,0),getSprite( 6, 1),270),id++,"Water island BL"));

        waterB.add(T_WATER=new Tile(ImgFix.getBuildRotImg(getAniSprite(1,0),getSprite( 5, 4),0),id++,"Water T"));
        waterB.add(R_WATER=new Tile(ImgFix.getBuildRotImg(getAniSprite(1,0),getSprite( 5, 4),90),id++,"Water R"));
        waterB.add(B_WATER=new Tile(ImgFix.getBuildRotImg(getAniSprite(1,0),getSprite( 5, 4),180),id++,"Water B"));
        waterB.add(L_WATER=new Tile(ImgFix.getBuildRotImg(getAniSprite(1,0),getSprite( 5, 4),270),id++,"Water L"));

        roadS.add(ROAD=new Tile(getSprite(4,3),id++,"Road"));
        roadS.add(ROAD_TB=new Tile(ImgFix.getRotImg(getSprite(4,3),90),id++,"Road TB"));

        roadC.add(TL_ROAD=new Tile(getSprite(0,1),id++,"Road TL"));
        roadC.add(TR_ROAD=new Tile(ImgFix.getRotImg(getSprite(0,1),90),id++,"Road TR"));
        roadC.add(BR_ROAD=new Tile(ImgFix.getRotImg(getSprite(0,1),180),id++,"Road BR"));
        roadC.add(BL_ROAD=new Tile(ImgFix.getRotImg(getSprite(0,1),270),id++,"Road BL"));

        tiles.addAll(grassT);
        tiles.addAll(waterC);
        tiles.addAll(waterI);
        tiles.addAll(waterB);
        tiles.addAll(roadS);

        tiles.addAll(roadC);

    }

    private BufferedImage[] getImgs(int fX,int fY,int sX,int sY){
        return new BufferedImage[]{getSprite(fX,fY),getSprite(sX,sY)};
    }

    public Tile getTile(int id) {
        return tiles.get(id);
    }

    private void loadAtlas() {
        atlas= LoadSave.getSpriteAtlas();
    }

    public BufferedImage getSprite(int id){
        return tiles.get(id).getSprite();
    }

    public BufferedImage getAnimSprite(int id, int animationIndex){
        return tiles.get(id).getSprite(animationIndex);
    }

    private BufferedImage[] getAniSprite(int xCord,int yCord){
        BufferedImage[] arr = new BufferedImage[4];
        for (int i=0;i<4;i++){
            arr[i]=getSprite(xCord +i*2,yCord);
        }
        return arr;
    }

    private BufferedImage getSprite(int xCord,int yCord){
        return atlas.getSubimage(xCord * 64,yCord * 64,64,64);
    }

    public boolean isSpriteAnimated(int spryteID){
        return tiles.get(spryteID).isAnimated();
    }

    public ArrayList<Tile> getRoadS() {
        return roadS;
    }

    public ArrayList<Tile> getRoadC() {
        return roadC;
    }

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
