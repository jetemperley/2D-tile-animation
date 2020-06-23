import java.awt.*;
import javax.imageio.*;
import java.awt.image.*;
import java.io.*;
import java.awt.event.*;
import javax.swing.*;

public class Player extends Monster implements Controllable {

    public Inventory inv = null;

    public Player() {
        this(0, 0);
    }

    public Player(int x, int y) {
        super(x, y);
        spriteSheet = AssetManager.getAssetManager().player;
    }

    public void applyKeyTyped(int code) {
        if (yOff == 0 && xOff == 0) {
            switch (code) {
            case KeyEvent.VK_UP:
                if (spriteDirection == 2) {
                    move(0, -1);
                } else {
                    changeDirection(0, -1);
                }
                break;

            case KeyEvent.VK_DOWN:
                if (spriteDirection == 0) {
                    move(0, 1);
                } else {
                    changeDirection(0, 1);
                }
                break;

            case KeyEvent.VK_LEFT:
                if (spriteDirection == 1) {
                    move(-1, 0);
                } else {
                    changeDirection(-1, 0);
                }
                break;

            case KeyEvent.VK_RIGHT:
                if (spriteDirection == 3) {
                    move(1, 0);
                } else {
                    changeDirection(1, 0);
                }
                break;

            case KeyEvent.VK_A:
                use();
                break;
            case KeyEvent.VK_S:
                drop();
                break;
            case KeyEvent.VK_I:
                engageUI();
                break;
            }
        }

    }

    public void applyKeyPressed(int code) {

        switch (code) {
        case KeyEvent.VK_UP:
            move(0, -1);
            break;
        case KeyEvent.VK_DOWN:
            move(0, 1);
            break;
        case KeyEvent.VK_LEFT:
            move(-1, 0);
            break;
        case KeyEvent.VK_RIGHT:
            move(1, 0);
            break;

        case KeyEvent.VK_A:

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

    public void draw(TileGraphics tg) {
        super.draw(tg, spriteSheet[spriteFrame][spriteDirection]);

    }

    public void engageUI() {

        matrix.ui.drawFlag = true;
        if (inv != null) {
            matrix.ui.setUIContents(this, inv.inv, pockets);
        } else {
            matrix.ui.setUIContents(this, null, pockets);
        }

        GamePanel.getGamePanel().inputFocus = matrix.ui;

    }

    // public void use() {

    // if (spriteDirection == 0) {
    // matrix.cells[yPos + 1][xPos].trigger();
    // } else if (spriteDirection == 1) {
    // matrix.cells[yPos][xPos-1].trigger();
    // } else if (spriteDirection == 2) {
    // matrix.cells[yPos - 1][xPos].trigger();
    // } else {
    // matrix.cells[yPos][xPos+1].trigger();
    // }
    // }
}