import java.awt.image.*;
import java.io.*;

import javax.imageio.*;

// singleton that holds all the sprite references
public class AssetManager {

    private static AssetManager assets = null;
    public BufferedImage[][] player = null, grass = null, tree = null, moods = null, slime = null;
    public BufferedImage apple = null, bag = null, select = null;

    private AssetManager() {

        try {
            player = to2dArray(ImageIO.read(new File("playerwalking24x24.png")), 24);
            slime = to2dArray(ImageIO.read(new File("slimewalk.png")), 24);
            grass = to2dArray(ImageIO.read(new File("grass24x24.png")), 24);
            tree = to2dArray(ImageIO.read(new File("stree24x24.png")), 24);
            moods = to2dArray(ImageIO.read(new File("mood.png")), 24);
            apple = ImageIO.read(new File("apple.png"));
            bag = ImageIO.read(new File("bag.png"));
            select = ImageIO.read(new File("select.png"));
        } catch (IOException ioe) {
            
        }

    }

    public static AssetManager getAssetManager() {

        if (assets == null) {
            assets = new AssetManager();
            return assets;
        } else {
            return assets;
        }

    }

    private BufferedImage[][] to2dArray(BufferedImage img, int spritesize) {
        BufferedImage[][] spriteSheet = null;
        if (img != null) {
            int spriteNX = img.getWidth() / spritesize;
            int spriteNY = img.getHeight() / spritesize;
            spriteSheet = new BufferedImage[spriteNY][spriteNX];

            for (int x = 0; x * spritesize < img.getWidth(); x++) {
                for (int y = 0; y * spritesize < img.getHeight(); y++) {
                    spriteSheet[y][x] = img.getSubimage(x * spritesize, y * spritesize, spritesize, spritesize);
                }
            }
            //System.out.println("xs = " + spriteNX + ", ys = " + spriteNY);
        }

        return spriteSheet;

    }

}