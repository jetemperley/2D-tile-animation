import java.awt.event.*;


public interface Controllable{
    public abstract void applyMouseClicked(MouseEvent e);
    public abstract void applyMouseExited(MouseEvent e);
    public abstract void applyMousePressed(MouseEvent e);
    public abstract void applyMouseReleased(MouseEvent e);
    public abstract void applyKeyPressed(int code);
    public abstract void applyKeyReleased(int code);
    public abstract void applyKeyTyped(int code);

}