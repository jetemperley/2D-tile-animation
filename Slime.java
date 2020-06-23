import java.awt.*;
import javax.imageio.*;
import java.awt.image.*;
import java.io.*;
import java.awt.event.*;
import javax.swing.*;
import java.lang.*;

public class Slime extends Monster {

    public int fcount = 0;

    public Slime() {
        this(0, 0);

    }

    public Slime(int x, int y) {
        super(x, y);
        spriteSheet = AssetManager.getAssetManager().slime;

    }

    public void update() {
        super.update();
        fcount++;
        if (fcount == 80) {
            makeRandomMove();
            fcount = 0;
        }

    }

    public void draw(TileGraphics tg) {
        super.draw(tg, tg.assets.slime[spriteFrame][spriteDirection]);
    }

}