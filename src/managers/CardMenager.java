package managers;

import helpz.LoadSave;
import scenes.Playing;

import java.awt.image.BufferedImage;

public class CardMenager {
    private Playing playing;
    private BufferedImage cardImg[];
    public CardMenager(Playing playing) {
        this.playing = playing;
        importCadrImgs();
    }
    private void importCadrImgs() {
        BufferedImage atlas = LoadSave.getCadrSprite();
        cardImg = new BufferedImage[1];
        for (int i = 0; i < 1; i++) {
            cardImg[i] = atlas.getSubimage(0, 0 , 300, 420);
        }
    }
}
