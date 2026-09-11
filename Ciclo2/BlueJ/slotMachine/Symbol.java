package slotMachine;

import shapes.Triangle;

import java.util.Objects;
import java.awt.*;
/**
 * this class is the Symbol in the slotMachine
 * this class is the Symbol in the Wheel
 *
 * @author Steveen-Gualdron
 * @version 0.1
 */
public class Symbol {
    private int positionAtTheWheel;
    private Wheel wheel;
    private Triangle symbolShape;
    
    /**Constructor symbol, dyadic method class
     * @param color color is the color of this symbol.
     * @param pos pos is the position of this symbol at the wheel.
     */
    public Symbol(String color) {
        symbolShape = new Triangle();
        symbolShape.changeColor(color);
        symbolShape.changeSize(30, 30);
    }
    
    /**Constructor symbol, dyadic method class
     * @param color color is the color of this symbol.
     * @param pos pos is the position of this symbol at the wheel.
     */
    public Symbol(String color, int pos) {
        symbolShape = new Triangle();
        symbolShape.changeColor(color);
        positionAtTheWheel = pos;
    }
    
    /**Sets a new X and Y position to this symbol.
     * @param newPosX newPosX that going to set like x position of this symbol.
     * @param newPosY newPosY that going to set like y position of this symbol. 
     */
    public void changePosition(int newPosX, int newPosY) {
        this.symbolShape.setXPosition(newPosX);
        this.symbolShape.setYPosition(newPosY);
        if (symbolShape.isVisible()) {
            this.symbolShape.frameFlickering();
        }
    }
    
    public void changeSize(int newHeight, int newWidth) {
        this.symbolShape.changeSize(newHeight, newWidth);
    }

    /**Makes this symbol visible.
     */
    public void makeVisible() {
        this.symbolShape.makeVisible();
    }

    /**Makes this symbol invisible.
     */
    public void makeInvisible() {
        this.symbolShape.makeInvisible();
    }

    public String getColor() {
        return this.symbolShape.getColor();
    }

    public void setColor(String color) {
        this.symbolShape.setColor(color);
    }


    public boolean isVisible() {
        return this.symbolShape.isVisible();
    }

    public void setVisible(boolean isVisible) {
        this.symbolShape.setVisible(isVisible);;
    }

    public Triangle getSymbolShape() {
        return (Triangle)symbolShape;
    }

    public void setSymbolShape(Triangle symbolShape) {
        this.symbolShape = symbolShape;
    }
    
    public void setPositionAtTheWheel(int newPositionAtTheWheel) {
        this.positionAtTheWheel = newPositionAtTheWheel;
    }
    
    public int getPositionAtTheWheel() {
        return positionAtTheWheel;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Symbol symbol = (Symbol) obj;
        return this.equals(symbol);
    }
    
    public boolean equals(Symbol symbol) {
        boolean hasSamePosition = positionAtTheWheel == symbol.getPositionAtTheWheel();
        boolean hasSameWheel = Objects.equals(wheel, symbol.getWheel());
        boolean hasSameColor = Objects.equals(getColor(), symbol.getColor());
        return hasSamePosition && hasSameWheel && hasSameColor;
    }

    
    public void moveSlowly(int howMany) {
        symbolShape.slowMoveVertical(howMany);
    }
    
    public void frameFlickering() {
        symbolShape.frameFlickering();
    }
    
    public Wheel getWheel() {
        return wheel;
    }
}
