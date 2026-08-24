package BlueJ;
import BlueJ.shapes.Triangle; 
import java.awt.*;
/**
 * The class symbol has every logical of figures of wheels.
 * Visually, it is a triangle.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Symbol {
    private int positionX;
    private int positionY;
    private int positionWheel;
    private final String color;
    private boolean isVisible;
    private Triangle symbolShape;
    
    /**Constructor symbol, dyadic method class
     * @param color color is the color of this symbol.
     * @param pos pos is the position of this symbol at the wheel.
     */
    public Symbol(String color, int pos) {
        this.color = color;
        symbolShape = new Triangle();
        symbolShape.changeColor(color);
        symbolShape.makeVisible();
        positionWheel = pos;
    }

    /**Sets a new X position to this symbol.
     * @param newPosX newPosX that going to set like x position of this symbol. 
     */
    public void changePositionX(int newPosX) {
        positionX = newPosX;
    }

    /**Sets a new Y position  to this symbol.
     * @param newPosY newPosY that going to set like y position of this symbol. 
     */
    public void changePositionY(int newPosY) {
        positionX = newPosY;
    }

    /**Makes this symbol visible.
     */
    public void makeVisible() {
        symbolShape.changeColor(color);
    }

    /**Makes this symbol invisible.
     */
    public void makeInvisible() {
        symbolShape.changeColor("white");
    }
}
