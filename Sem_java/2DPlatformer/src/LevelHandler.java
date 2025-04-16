import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import utilz.Const;
import utilz.LoadSave;

public class LevelHandler {
    Game game;
    BufferedImage[] lvlSprite;
    private Level lvlOne;
    private int lvlLength = GetLvlData()[0].length;
    

    public LevelHandler(Game game) {
        this.game = game;
        //lvlSprite = LoadSave.LoadImg(LoadSave.LEVEL_ATLAS);
        importOutsideSprites();
        lvlOne = new Level(GetLvlData());
    }
    private void importOutsideSprites() {
        BufferedImage img = LoadSave.LoadImg(LoadSave.LEVEL_ATLAS);
        lvlSprite = new BufferedImage[85];

        for(int i=0; i < 5; i++) {
            for(int j = 0; j < 17; j++) {
                lvlSprite[i*17 + j] = img.getSubimage(j*32, i*32, 32,32);
            }
        }

    }
    public void draw(Graphics g, int xLvlOffset) {
        for(int i = 0; i < Const.GameStats.TILES_HEIGHT; i++) {
            for(int j = 0; j < lvlLength; j++) {
                g.drawImage(lvlSprite[lvlOne.lvlData[i][j]], j*Const.GameStats.TILE_SIZE - xLvlOffset,  i*Const.GameStats.TILE_SIZE,  Const.GameStats.TILE_SIZE,  Const.GameStats.TILE_SIZE, null);
            }
        }
    }
    public void update() {

    }
    public static int[][] GetLvlData() {
        BufferedImage img = LoadSave.LoadImg(LoadSave.LEVEL_1_DATA); 
        int[][] lvlData = new int[img.getHeight()][img.getWidth()];

        for(int i = 0; i < img.getHeight(); i++) {
            for(int j = 0; j < img.getWidth(); j++) {
                Color color = new Color(img.getRGB(j, i));
                if(color.getRed() < 85) lvlData[i][j] = color.getRed();
                else lvlData[i][j] = 0;
            }
        }
        for(int i = 0; i < img.getHeight(); i++) {
            for(int j = 0; j < img.getWidth(); j++) {
                //System.out.print(lvlData[i][j]);
            }
            //System.out.println();
        }
        return lvlData;
    }
    public Level GetLevel() {
        return lvlOne;
    }
}
