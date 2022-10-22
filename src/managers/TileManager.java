package managers;

import helpz.LoadSave;
import objects.Tile;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class TileManager {

    public Tile GRASS,WATER,ROAD,GRASS_Dark;
    private BufferedImage atlas;
    private ArrayList<Tile>  tiles= new ArrayList<>();

    public TileManager() {

        loadAtlas();
        createTiles();

    }


    private void createTiles() {
        tiles.add(GRASS=new Tile(getSprite(1,2)));
        tiles.add(WATER=new Tile(getSprite(1,0)));
        tiles.add(ROAD=new Tile(getSprite(4,3)));
        tiles.add(GRASS_Dark=new Tile(getSprite(2,3)));
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
