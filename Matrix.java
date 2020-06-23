import java.awt.image.*;
import java.io.*;
import javax.imageio.ImageIO;

public class Matrix {

    public Cell[][] cells;
    private WorldObject[] updates;
    private int updatesLength = 0;
    public TileGraphics tg;
    public Player focus = null;
    public InvUI ui;

    public Matrix(int xS, int yS) {
        cells = new Cell[xS][yS];

        for (int y = 0; y < cells.length; y++) {
            for (int x = 0; x < cells[y].length; x++) {
                cells[y][x] = new Cell(x, y);
            }
        }

    }

    public Matrix(int[][] data) {

        BufferedImage grass = null, tree = null;

        try {
            grass = ImageIO.read(new File("grass24x24.png"));
            grass = grass.getSubimage(0, 0, 24, 24);
            tree = ImageIO.read(new File("stree24x24.png"));
        } catch (IOException ioe) {
            System.out.println("grass load error");
        }

        cells = new Cell[data.length][data[0].length];

        for (int y = 0; y < cells.length; y++) {
            for (int x = 0; x < cells[y].length; x++) {
                switch (data[y][x]) {
                case 0:
                    cells[y][x] = new Cell(grass, x, y);
                    break;

                case 1:
                    cells[y][x] = new Cell(tree, x, y);
                    cells[y][x].locked = true;
                    break;
                }
            }
        }

        updates = new WorldObject[50];

        ui = new InvUI(null, null, null);
    }

    public boolean add(WorldObject obj) {

        if (cells[obj.getY()][obj.getX()].add(obj)) {
            obj.setParentGrid(this);
            updates[updatesLength] = obj;
            updatesLength++;
            //System.out.println("" + updatesLength);
            return true;
        } else {
            return false;
        }
    }

    public boolean add(WorldObject obj, int x, int y){
        //System.out.println("trying set location " + cells[y][x].hasObject() + " " + cells[y][x].locked);
        if (!cells[y][x].hasObject() && !cells[y][x].locked){
            obj.setLocation(x, y);
            //System.out.println("set location");
            return add(obj);
        } else {
            return false;
        }
    }

    public void updateAndDraw() {
        try {
            
            if (focus != null) {
                tg.orientateBorderAround(focus, 3);
            }
        } catch (NullPointerException npe) {

        }
        // calc the starting position for onsceen tiles and end (limit)
        int ystart = (tg.yPos / tg.tileHeight) - 1;
        int xstart = (tg.xPos / tg.tileWidth) - 1;
        int ylim = ystart + tg.yTiles + 1;
        int xlim = xstart + tg.xTiles + 1;

        if (ystart < 0)
            ystart = 0;
        if (xstart < 0)
            xstart = 0;

        for (int i = 0; i < updatesLength; i++){
            updates[i].update();
        }

        for (int y = ystart; y < ylim; y++) {
            for (int x = xstart; x < xlim; x++) {
                if (y < cells.length && x < cells[y].length) {
                    cells[y][x].drawTerrain(tg);
                }
            }
        }

        for (int y = ystart; y < ylim; y++) {
            for (int x = xstart; x < xlim; x++) {
                if (y < cells.length && x < cells[y].length) {
                    cells[y][x].drawObject(tg);
                }
            }
        }

        if (ui.drawFlag){
            ui.drawUI(tg);
        }

    }

    public void setDimensions(TileGraphics tg_) {
        tg = tg_;
    }

    public void setFocus(Player p) {
        focus = p;
        /*
         * if (focus != null) { translateTo(focus.xPos * dimensions.tileWidth -
         * (dimensions.tileWidth * focus.xOff) / 100 - 200, focus.yPos *
         * dimensions.tileHeight - (dimensions.tileHeight * focus.yOff) / 100 - 200); }
         */
    }

    public void translateTo(int x, int y) {
        tg.xPos = x;
        tg.yPos = y;
    }

    public void removeObject(int x, int y){
        removeUpdate(cells[y][x].object);
        cells[y][x].object = null;

    }

    public Cell getCell(int x, int y){
        return cells[y][x];
    }

    public Cell getCellAt(int x, int y) {
        try {
            return cells[(y + tg.yPos) / tg.tileHeight][(x + tg.xPos) / tg.tileWidth];
        } catch (ArrayIndexOutOfBoundsException npe) {
            return null;
        }
    }

    public void removeUpdate(WorldObject obj){
        int n = 0;
        for (int i = 0; n < updatesLength; i++){
            if (updates[i].equals(obj)){
                n++;
            }
            updates[i] = updates[n];
            n++;
        }
        updatesLength--;
        //System.out.println("" + updatesLength);
    }

    public boolean move(int x1, int y1, int x2, int y2){
        if(!cells[y2][x2].locked && !cells[y2][x2].hasObject()){
            cells[y2][x2].add(cells[y1][x1].object);
            cells[y1][x1].object = null;
            cells[y2][x2].object.setLocation(x2, y2);
            return true;
        } else {
            return false;
        }
    }

    

}