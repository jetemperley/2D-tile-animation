import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Cell {

    // WorldObject objects[];
    public WorldObject object = null;
    // int maxVolume = 100, currentVolume = 0;
    private int xPos, yPos;
    BufferedImage ground;
    boolean locked = false;

    public Cell(int x, int y) {
        this(null, x, y);
    }

    public Cell(BufferedImage img, int x, int y) {
        xPos = x;
        yPos = y;
        ground = img;
        // objects = new WorldObject[0];
    }

    public boolean add(WorldObject obj) {
        if (object == null && !locked) {
            object = obj;
            return true;
        } else {
            return false;
        }
    }

    public void setObject(WorldObject obj){
        object = obj;
        if (object != null)
            object.setLocation(xPos, yPos);
    }

    public void remove() {
        object = null;
        //System.out.println("set " + xPos + " " + yPos + " to null");
    }

    public boolean hasObject(){
        if (object == null){
            return false;
        } else {
            return true;
        }
    }

    // public boolean add2(WorldObject obj) {

    // if (currentVolume + obj.volume <= maxVolume) {
    // int n = objects.length + 1;
    // WorldObject[] objs2 = new WorldObject[n];

    // for (int i = 0; i < objects.length; i++) {
    // objs2[i] = objects[i];
    // }

    // objs2[n - 1] = obj;

    // objects = objs2;
    // currentVolume += obj.volume;

    // return true;
    // } else {
    // return false;
    // }

    // }

    // public void remove(int n) {
    // if (n < objects.length && objects.length > 0) {
    // currentVolume -= objects[n].volume;
    // WorldObject[] objs2 = new WorldObject[objects.length - 1];

    // int c = 0;
    // for (int i = 0; i < objs2.length; i++) {
    // if (i == n) {
    // c++;
    // }
    // objs2[i] = objects[c];
    // c++;
    // }

    // objects = objs2;
    // }
    // }

    // public void remove(WorldObject obj) {
    // for (int i = 0; i < objects.length; i++) {
    // if (objects[i].equals(obj)) {
    // remove(i);
    // break;
    // }
    // }

    // }

    public void drawTerrain(TileGraphics tg) {
        if (ground != null) {
            tg.drawImage(ground, xPos, yPos);
        }

    }

    public void drawObject(TileGraphics tg) {
        // for (WorldObject wo : objects) {
        // if (wo != null)
        // wo.draw(tg);
        // }
        if (object != null) {
            object.draw(tg);
        }

    }

    public void update() {
        if (object != null) {
            object.update();
        }
    }

    public void triggerObject(Monster mon) {
        if (object != null && !locked) {
            object.onTrigger(mon);
        }
    }


}