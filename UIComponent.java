import java.awt.Color;

public class UIComponent {

    int xPos = 0, yPos = 0;
    WorldObject[][] content;

    public UIComponent(WorldObject[] parts, int x, int y) {
        setPos(x, y);
        content = new WorldObject[1][0];
        content[0] = parts;
    }

    public UIComponent(WorldObject[][] parts, int x, int y) {
        setPos(x, y);
        content = parts;
    }

    public void setPos(int x, int y) {
        xPos = x;
        yPos = y;
    }

    public void draw(TileGraphics tg) {

        if (content != null) {
            for (int y = 0; y < content.length; y++) {
                for (int x = 0; x < content[y].length; x++) {
                    tg.g.setColor(Color.GRAY);
                    tg.g.fillRect(tg.uiSize * (xPos + x), tg.uiSize * (yPos + y), tg.uiSize, tg.uiSize);
                    tg.g.setColor(Color.BLACK);
                    tg.g.drawRect(tg.uiSize * (xPos + x), tg.uiSize * (yPos + y), tg.uiSize, tg.uiSize);
                    if (content[y][x] != null) {
                        content[y][x].drawAbs(tg, tg.uiSize * (xPos + x), tg.uiSize * (yPos + y), tg.uiSize, tg.uiSize);
                    }
                }
            }
        }

    }

    public void setContent(WorldObject[] wo) {
        if (content == null || content.length > 1) {
            content = new WorldObject[1][0];
        }
        content[0] = wo;
    }

    public void setContent(WorldObject[][] wo) {
        content = wo;
    }

    public WorldObject getRelative(int x, int y) {
        return content[y - yPos][x - xPos];
    }

    public void setRelative(WorldObject wo, int x, int y) {
        content[y - yPos][x - xPos] = wo;
    }

}