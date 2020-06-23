import java.awt.image.BufferedImage;

public abstract class Monster extends WorldObject {

    int spriteFrame = 0, spriteDirection = 0;
    int xdir = 0, ydir = 1;
    int animTimer = 0;
    int mood = 0, moodTimer = 0;
    boolean moodFlag = false;
    BufferedImage[][] spriteSheet = null;
    WorldObject[] pockets;

    public Monster(int x, int y) {
        super(x, y);
        // volume = 50;
        pockets = new WorldObject[4];
        System.out.println("pockets");

    }

    public void draw(TileGraphics tg, BufferedImage img) {
        tg.drawImage(img, xPos, yPos, -xOff, -yOff);
        if (pockets[0] != null){
            tg.drawImage(pockets[0].sprite, xPos, yPos, 
            -xOff + xdir*50, -yOff+ydir*50);
        }
        if (moodFlag) {
            if (moodTimer < 40) {
                tg.drawImage(tg.assets.moods[0][0], getX(), yPos, -xOff, -yOff);
                moodTimer++;
            } else {
                moodFlag = false;
                moodTimer = 0;
            }
        }
    }

    public void update() {

        if (xOff != 0 || yOff != 0) {
            animTimer++;
            if (animTimer >= 5) {
                animTimer = 0;
                spriteFrame++;
                if (spriteFrame >= 4) {
                    spriteFrame = 0;
                }
            }
            xOff += -10 * Integer.signum(xOff);
            yOff += -10 * Integer.signum(yOff);
        } else {
            animTimer = 0;
            spriteFrame = 0;
            // System.out.println("reset sprite frame");
        }

    }

    public void move(int x, int y) {
        if (yOff == 0 && xOff == 0) {
            // if (matrix.cells[yPos + y][getX() + x].object == null && !matrix.cells[yPos +
            // y][getX() + x].locked) {
            // matrix.cells[yPos][getX()].remove();
            // xPos += x;
            // yPos += y;

            changeDirection(x, y);
            // matrix.add(this);
            if (matrix.move(xPos, yPos, xPos + x, yPos + y)) {
                xOff += 100 * x;
                yOff += 100 * y;
            }
            // }
        }
    }

    public int getHeldX(){
        if (spriteDirection == 1){
            return -50;
        } else if (spriteDirection == 3){
            return 50;
        } else {
            return 0;
        }
    }

    public int getHeldY(){
        if (spriteDirection == 0){
            return 50;
        } else if (spriteDirection == 2){
            return -50;
        } else {
            return 0;
        }
    }

    public void makeRandomMove() {
        int n = Math.round((float) Math.random() * 100);

        if (n <= 25) {
            move(0, 1);

        } else if (n <= 50) {
            move(0, -1);

        } else if (n <= 75) {
            move(1, 0);

        } else {
            move(-1, 0);

        }
    }

    public void changeDirection(int x, int y) {
        xdir = x;
        ydir = y;
        if (x == 1) {
            spriteDirection = 3;
        } else if (x == -1) {
            spriteDirection = 1;
        } else if (y == 1) {
            spriteDirection = 0;
        } else if (y == -1) {
            spriteDirection = 2;
        }

    }

    public void use() {

        if (spriteDirection == 0) {
            matrix.getCell(getX(), getY() + 1).triggerObject(this);

        } else if (spriteDirection == 1) {
            matrix.getCell(getX() - 1, getY()).triggerObject(this);

        } else if (spriteDirection == 2) {
            matrix.getCell(getX(), getY() - 1).triggerObject(this);

        } else if (spriteDirection == 3) {
            matrix.getCell(getX() + 1, getY()).triggerObject(this);
        }
    }

    public void onTrigger(Monster mon) {
        moodFlag = true;
    }

    public void drop() {
        if (pockets[0] != null) {
            if (spriteDirection == 0) {
                // matrix.cells[yPos + 1][xPos].object = (this);
                dropHeldObject(0, 1);
            } else if (spriteDirection == 1) {
                dropHeldObject(-1, 0);

            } else if (spriteDirection == 2) {
                dropHeldObject(0, -1);

            } else if (spriteDirection == 3) {
                dropHeldObject(1, 0);
            }
        }
    }

    public void dropHeldObject(int x, int y) {

        if (matrix.add(pockets[0], xPos + x, yPos + y)) {
            pockets[0] = null;
        }
    }

    public WorldObject swap(WorldObject itm) {
        WorldObject ret = pockets[0];
        pockets[0] = itm;
        return ret;
    }

}