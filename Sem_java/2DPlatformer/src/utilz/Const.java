package utilz;
public class Const {

    public static class Directions {  
        public static final int LEFT = 0;
        public static final int UP = 1;
        public static final int RIGHT = 2;
        public static final int DOWN = 3;

    }
    public static class GameStats {
        public static final int TILE_SIZE_DEF = 32;
        public static final float SCALE = 2.0f;
        public static final int TILES_WIDTH = 26;
        public static final int TILES_HEIGHT = 14;
        public static final int TILE_SIZE = (int)(TILE_SIZE_DEF * SCALE);
        //panel size
        public static final int PANEL_WIDTH = TILE_SIZE * TILES_WIDTH;
        public static final int PANEL_HEIGHT = TILE_SIZE * TILES_HEIGHT;
    }
    public static class PlayerConst {
        public static final int IDLE = 0;
        public static final int RUNNING = 1;
        public static final int JUMP = 2;
        public static final int FALL = 3;
        public static final int ATTACK = 4;
        public static final int HIT = 5;

        public static int GetSpriteNum(int action) {
            switch (action) {
                case IDLE: return 2;
                case RUNNING: return 8;
                case JUMP: return 3;
                case FALL: return 5;
                case ATTACK: return 8;
                case HIT: return 2;
                default: return 2;
            }
        }
    }
}
