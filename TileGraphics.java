import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class TileGraphics {

    public Graphics g;
    public AssetManager assets;
    int screenWidth, screenHeight;
    // tilewidth&height of one tile in pixels
    int tileWidth, tileHeight;
    // number of tiles visible on screen
    int xTiles, yTiles;
    // the position of the screens top left corner
    int xPos = 0, yPos = 0;
    // uiSize is a utility value holding 1/uin of the screen height, for UI purposes
    int uiSize, uiN = 10;

    public TileGraphics(Graphics g_, int screenX, int screenY, int tileW, int tileH) {
        g = g_;
        assets = AssetManager.getAssetManager();

        screenWidth = screenX;
        screenHeight = screenY;

        tileWidth = tileW;
        tileHeight = tileH;

        xTiles = (screenWidth / tileWidth) + 1;
        //System.out.println("xTiles = " + xTiles);
        yTiles = (screenHeight / tileHeight) + 1;
        //System.out.println("yTiles = " + yTiles);

        uiSize = screenHeight/uiN;

    }

    public TileGraphics(int screenX, int screenY, int tileW, int tileH) {
        this(null, screenX, screenY, tileW, tileH);
    }

    // draws the image at TILE x,y based on internal tlesize with offsets in
    // percentage of a tile
    public void drawImage(BufferedImage img, int x, int y, int xOffPercent, int yOffPercent) {
        g.drawImage(img, x * tileWidth + tileWidth * xOffPercent / 100 - xPos,
                y * tileHeight + tileHeight * yOffPercent / 100 - yPos, tileWidth, tileHeight, null);
    }

    public void drawImage(BufferedImage img, int x, int y) {
        // g.drawImage(img, x*tileWidth + xPos, y*tileHeight + yPos, tileWidth,
        // tileHeight, null);
        drawImage(img, x, y, 0, 0);
    }

    public void setGraphics(Graphics g_) {
        g = g_;
    }

    public void zoom(int n) {
        tileWidth += n;
        tileHeight = tileWidth;

        xTiles = (tileWidth / screenWidth) + 1;
        yTiles = (tileHeight / screenHeight) + 1;
    }

    public void orientateBorderAround(WorldObject obj, int border) {
        if (obj.xPos * tileWidth - (obj.xOff * tileWidth) / 100 < xPos + border * tileWidth) {
            xPos = obj.xPos * tileWidth - (obj.xOff * tileWidth) / 100 - border * tileWidth;
        }

        if (obj.xPos * tileWidth - (obj.xOff * tileWidth) / 100 > xPos - (border+2) * tileWidth + xTiles * tileWidth) {
            xPos = (obj.xPos * tileWidth) - ((obj.xOff * tileWidth) / 100) + (border+2) * tileWidth - (xTiles * tileWidth);
        }

        if (obj.yPos * tileHeight - (obj.yOff * tileHeight) / 100 < yPos + border * tileHeight) {
            yPos = obj.yPos * tileHeight - (obj.yOff * tileHeight) / 100 - border * tileHeight;
        }

        if (obj.yPos * tileHeight - (obj.yOff * tileHeight) / 100 > yPos + yTiles * tileHeight - (border+2) * tileHeight) {
            yPos = (obj.yPos * tileHeight) - ((obj.yOff * tileHeight) / 100) + (border+2) * tileHeight - (yTiles * tileHeight);
        }

    }

}