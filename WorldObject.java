import java.awt.Graphics;
import java.awt.image.BufferedImage;

public abstract class WorldObject {
    // 2d position as the tile coords oon a grid
    public int xPos = 0, yPos = 0;
    // the offset as a perentage of a tile 0-100
    public int xOff = 0, yOff = 0;
    // the amout of space this object takes
    // int volume;
    // parent matrix
    Matrix matrix;
    BufferedImage sprite = null;

    public WorldObject() {
        this(0, 0);
    }

    public WorldObject(int x, int y) {
        xPos = x;
        yPos = y;
    }

    public void draw(TileGraphics tg){

    }

    public void draw(TileGraphics tg, BufferedImage img) {
        draw(tg, img, xPos, yPos, -xOff, -yOff);
    }

    public void drawAbs(TileGraphics tg, int x, int y, int width, int height){
        tg.g.drawImage(sprite, x, y, width, height, null);
    }

    public void draw(TileGraphics tg, BufferedImage img, int x, int y, int xo, int yo) {
        tg.drawImage(img, x, y, xo, yo);
    }

    public void setParentGrid(Matrix gd) {
        matrix = gd;
    }

    // public abstract void move(int x, int y);

    public abstract void update();

    // swaps this object (including null) with the mon that triggered it
    public void onTrigger(Monster mon) {
        matrix.removeObject(xPos, yPos);
        if (mon.pockets[0] != null) {
            matrix.add(mon.swap(this), xPos, yPos);
        } else {
            mon.pockets[0] = this;
        }
    }

    public void use() {
    }

    // complete

    public void setLocation(int x, int y) {
        xPos = x;
        yPos = y;
    }

    public int getX() {
        return xPos;
    }

    public int getY() {
        return yPos;
    }

}
