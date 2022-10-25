package managers;

import helpz.LoadSave;
import objects.Tile;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class TileManager {

    public Tile GRASS,WATER,ROAD,GRASS_DARK,GRASS_ORANGE;
    private BufferedImage atlas;
    public ArrayList<Tile>  tiles= new ArrayList<>();

    public TileManager() {

        loadAtlas();
        createTiles();

    }


    private void createTiles() {
        int id=0;
        tiles.add(ROAD=new Tile(getSprite(4,3),id++,"Road"));
        tiles.add(GRASS=new Tile(getSprite(1,2),id++,"Grass"));
        tiles.add(WATER=new Tile(getSprite(1,0),id++,"Water"));
        tiles.add(GRASS_DARK=new Tile(getSprite(2,3),id++,"GrassDark"));
        tiles.add(GRASS_ORANGE=new Tile(getSprite(0,3),id++,"GrassOrange"));
    }

    private void loadAtlas() {
        atlas= LoadSave.getSpriteAtlas();
    }

    public BufferedImage getSprite(int id){
        return tiles.get(id).getSprite();
    }

    private BufferedImage getSprite(int xCord,int yCord){
        return atlas.getSubimage(xCord * 64,yCord * 64,64,64);
    }
}
