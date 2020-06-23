

public class Inventory extends WorldObject{

    public WorldObject[][] inv;

    public Inventory(int x, int y){
        super(x, y);
        inv = new WorldObject[5][5];
        sprite = AssetManager.getAssetManager().bag;
    }

    public void draw(TileGraphics tg){
        super.draw(tg, tg.assets.bag);
    }

    public void update(){

    }

    // public void add(WorldObject obj) {

    //     int n = inv.length + 1;
    //     WorldObject[] objs2 = new WorldObject[n];

    //     for (int i = 0; i < inv.length; i++) {
    //         objs2[i] = inv[i];
    //     }

    //     objs2[n - 1] = obj;

    //     inv = objs2;

    // }

    // public void remove(int n) {
    //     if (n < inv.length && inv.length > 0) {
    //         // currentVolume -= inv[n].volume;
    //         WorldObject[] objs2 = new WorldObject[inv.length - 1];

    //         int c = 0;
    //         for (int i = 0; i < objs2.length; i++) {
    //             if (i == n) {
    //                 c++;
    //             }
    //             objs2[i] = inv[c];
    //             c++;
    //         }

    //         inv = objs2;
    //     }
    // }

    // public void remove(WorldObject obj) {
    //     for (int i = 0; i < inv.length; i++) {
    //         if (inv[i].equals(obj)) {
    //             remove(i);
    //             break;
    //         }
    //     }

    // }
}