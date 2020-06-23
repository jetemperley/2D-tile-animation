import java.awt.Color;
import java.awt.event.*;

public class InvUI implements Controllable {

    UIComponent i, p;
    Controllable owner;
    public boolean drawFlag = false;
    public Color bgCol, selCol;
    int x = 1, y = 1, sx = -1, sy = -1;
    WorldObject selectedObject;
    UIComponent[][] map;
    WorldObject temp;

    public InvUI(Controllable owner_, WorldObject[][] inv, WorldObject[] pockets) {

        p = new UIComponent(pockets, 1, 1);
        i = new UIComponent(inv, 1, 3);
        owner = owner_;
        bgCol = new Color(100, 100, 100, 175);
        selCol = new Color(200, 200, 200, 100);
        map = new UIComponent[10][10];

    }

    public void drawUI(TileGraphics tg) {

        tg.g.setColor(bgCol);
        tg.g.fillRect(0, 0, tg.screenWidth, tg.screenHeight);

        // tg.g.setColor(Color.GRAY);
        // tg.g.fillRect(tg.uiSize, tg.uiSize, tg.uiSize, tg.uiSize);
        // tg.g.fillRect(tg.uiSize * 5, tg.uiSize, tg.uiSize, tg.uiSize);
        // tg.g.setColor(Color.BLACK);
        // tg.g.drawRect(tg.uiSize, tg.uiSize, tg.uiSize, tg.uiSize);
        // tg.g.drawRect(tg.uiSize * 5, tg.uiSize, tg.uiSize, tg.uiSize);
        // tg.g.drawString("bag", tg.uiSize * 5, tg.uiSize);

        // tg.g.drawString("held", tg.uiSize, tg.uiSize);

        // if (p != null) {
        // for (int i = 0; i < p.length; i++) {
        // tg.g.setColor(Color.GRAY);
        // tg.g.fillRect(tg.uiSize * (i + 1), tg.uiSize, tg.uiSize, tg.uiSize);
        // tg.g.setColor(Color.BLACK);
        // tg.g.drawString("p" + i, tg.uiSize * (i + 1), tg.uiSize);
        // tg.g.drawRect(tg.uiSize * (i + 1), tg.uiSize, tg.uiSize, tg.uiSize);
        // if (p[i] != null) {
        // p[i].drawAbs(tg, tg.uiSize * (i + 1), tg.uiSize, tg.uiSize, tg.uiSize);
        // }
        // }
        // }

        // if (i != null) {

        // tg.g.drawImage(i.sprite, tg.uiSize * 4, tg.uiSize, tg.uiSize, tg.uiSize,
        // null);

        // for (int y = 0; y < i.inv.length; y++) {
        // for (int x = 0; x < i.inv[y].length; x++) {
        // tg.g.setColor(Color.GRAY);
        // tg.g.fillRect(tg.uiSize * (x + 4), tg.uiSize * (y + 4), tg.uiSize,
        // tg.uiSize);
        // tg.g.setColor(Color.BLACK);
        // tg.g.drawRect(tg.uiSize * (x + 4), tg.uiSize * (y + 4), tg.uiSize,
        // tg.uiSize);
        // if (i.inv[y][x] != null) {
        // i.inv[y][x].drawAbs(tg, tg.uiSize * (x + 4), tg.uiSize * (y + 4), tg.uiSize,
        // tg.uiSize);
        // }
        // }
        // }
        // }

        if (x < 0) {
            x = 0;
        } else if (x >= tg.uiN) {
            x = tg.uiN - 1;
        }
        if (y < 0) {
            y = 0;
        } else if (y >= tg.uiN) {
            y = tg.uiN - 1;
        }
        
        if (i != null) {
            i.draw(tg);
        }
        p.draw(tg);
        tg.g.setColor(selCol);
        tg.g.fillRect(sx * tg.uiSize, sy * tg.uiSize, tg.uiSize, tg.uiSize);
        tg.g.drawImage(tg.assets.select, x * tg.uiSize, y * tg.uiSize, tg.uiSize, tg.uiSize, null);

    }

    public void setUIContents(Controllable owner_, WorldObject[][] inv, WorldObject[] pockets) {
        i.setContent(inv);
        p.setContent(pockets);
        owner = owner_;
        x = 1;
        y = 1;
        updateMap();
        sx = -1;
        sy = -1;
        x = 1;
        y = 1;

    }

    public void updateMap() {
        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[y].length; x++) {
                map[y][x] = null;
            }
        }
        if (i.content != null) {
            for (int y = 0; y < i.content.length; y++) {
                for (int x = 0; x < i.content[y].length; x++) {
                    map[y + i.yPos][x + i.xPos] = i;
                }
            }
        }
        for (int y = 0; y < p.content.length; y++) {
            for (int x = 0; x < p.content[y].length; x++) {
                map[y + p.yPos][x + p.xPos] = p;
            }
        }
    }

    public void applyKeyTyped(int code) {
        switch (code) {

        case KeyEvent.VK_I:
            drawFlag = false;
            GamePanel.getGamePanel().inputFocus = owner;
            break;
        case KeyEvent.VK_UP:
            y--;
            break;
        case KeyEvent.VK_DOWN:
            y++;
            break;
        case KeyEvent.VK_LEFT:
            x--;
            break;
        case KeyEvent.VK_RIGHT:
            x++;
            break;
        case KeyEvent.VK_ENTER:
            if (sx == -1) {
                sx = x;
                sy = y;
            } else if (map[y][x] != null && map[sy][sx] != null) {
                temp = map[y][x].getRelative(x, y);
                map[y][x].setRelative(map[sy][sx].getRelative(sx, sy), x, y);
                map[sy][sx].setRelative(temp, sx, sy);
                temp = null;
                sx = -1;
                sy = -1;
            } else {
                sx = -1;
                sy = -1;
            }
            break;
        case KeyEvent.VK_ESCAPE:
            drawFlag = false;
            GamePanel.getGamePanel().inputFocus = owner;
            break;
        }
    }

    public void applyMouseClicked(MouseEvent e) {

    }

    public void applyMouseExited(MouseEvent e) {
    }

    public void applyMousePressed(MouseEvent e) {
    }

    public void applyMouseReleased(MouseEvent e) {
    }

    public void applyKeyReleased(int code) {
    }

    public void applyKeyPressed(int code) {
    }

}