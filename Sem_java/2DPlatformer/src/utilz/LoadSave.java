package utilz;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class LoadSave {
    public static final String PLAYER_ATLAS = "assets/Rogue.png";
    public static final String LEVEL_ATLAS = "assets/Terrain (32x32).png";
    public static final String LEVEL_1_DATA = "assets/LvlOne.png";
    public static final String PLAYING_BACKGROUND = "assets/Background.png";

    public static BufferedImage LoadImg(String File) {
        BufferedImage img = null;
        InputStream is =LoadSave.class.getResourceAsStream("/" + File);
        try {
            img = ImageIO.read(is);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return img;
    }
}
