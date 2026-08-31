package BlueJ;
//import BlueJ.shapes.Triangle; 
import java.awt.*;
/**
 * this class is the Symbol in the slotMachine
 * this class is the Symbol in the Wheel
 *
 * @author Steveen-Gualdron
 * @version 0.1
 */
public class Symbol {
    private int positionX;
    private int positionY;
    private int positionWheel;
    private String color;
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
        positionWheel = pos;
    }

    /**Sets a new X position to this symbol.
     * @param newPosX newPosX that going to set like x position of this symbol. 
     */
    public void changePositionX(int newPosX) {
        positionX = newPosX;
        if (symbolShape.isVisible()) {
            symbolShape.makeVisible();
        }
    }

    /**Sets a new Y position  to this symbol.
     * @param newPosY newPosY that going to set like y position of this symbol. 
     */
    public void changePositionY(int newPosY) {
        positionY = newPosY;
        if (symbolShape.isVisible()) {
            symbolShape.makeVisible();
        }
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
    
    public int getPositionX() {
        return positionX;
    }

    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getPositionWheel() {
        return positionWheel;
    }

    public void setPositionWheel(int positionWheel) {
        this.positionWheel = positionWheel;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean isVisible) {
        this.isVisible = isVisible;
    }

    public Triangle getSymbolShape() {
        return symbolShape;
    }

    public void setSymbolShape(Triangle symbolShape) {
        this.symbolShape = symbolShape;
    }
}
