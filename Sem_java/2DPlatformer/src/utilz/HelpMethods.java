package utilz;

import java.awt.geom.Rectangle2D;

public class HelpMethods {
    public static boolean canMoveHere(float x , float y, float width, float height, int[][] lvlData) {
        if(!isSolid(x,y,lvlData))
            if(!isSolid(x+width,y+height,lvlData))
                if(!isSolid(x+width,y,lvlData))
                    if(!isSolid(x,y+height,lvlData))
                        return true;
        return false;
    }
    private static boolean isSolid(float x , float y, int[][] lvlData) {
        int maxWidth = lvlData[0].length * Const.GameStats.TILE_SIZE;
        if(x < 0 || x >= maxWidth) return true;
        if(y < 0 || y >= Const.GameStats.PANEL_HEIGHT) return true;

        float xIndex = x/Const.GameStats.TILE_SIZE;
        float yIndex = y/Const.GameStats.TILE_SIZE;

        int value = lvlData[(int)yIndex][(int)xIndex];
        if(value >= 85 || value < 0 || value != 3) return true; // only use sprite 5 for air
        return false;
    }
    public static float MoveNextToWall(Rectangle2D.Float hitBox, float xSpeed) {
        int currentTile = (int)hitBox.x / Const.GameStats.TILE_SIZE;
        if(xSpeed > 0) {
            //right
            int tileXPos = currentTile * Const.GameStats.TILE_SIZE;
            int xOffset = (int)(Const.GameStats.TILE_SIZE - hitBox.width);
            return tileXPos + xOffset - 1; //-1 to not touch wall
        }
        else {
            return currentTile * Const.GameStats.TILE_SIZE;
        }
    }
    public static float MovePlayerToFloor(Rectangle2D.Float hitBox, float airSpeed) {
        int currentTile = (int)hitBox.y / Const.GameStats.TILE_SIZE;
        if(airSpeed >0) {
            //fall
            int tileYPos = currentTile * Const.GameStats.TILE_SIZE;
            int yOffset = (int)(Const.GameStats.TILE_SIZE - hitBox.height);
            return tileYPos + yOffset - 1;
        }
        else {
            //jump
            return currentTile * Const.GameStats.TILE_SIZE;
        }
    }
    public static boolean PlayerOnFloor(Rectangle2D.Float hitBox, int[][] lvlData) {
        if(!isSolid(hitBox.x, hitBox.y + hitBox.height+1, lvlData))
            if(!isSolid(hitBox.x + hitBox.width, hitBox.y + hitBox.height+1, lvlData))
                return false;
        return true;
    }
}
