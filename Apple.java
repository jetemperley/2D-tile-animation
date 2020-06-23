

public class Apple extends WorldObject{

    public Apple(){
        this(0, 0);
    }

    public Apple(int x, int y){
        super(x, y);
        sprite = AssetManager.getAssetManager().apple;
    }

    public void update(){

    }

    public void draw(TileGraphics tg){
        super.draw(tg, tg.assets.apple);
    }

}