package slotMachine;

import shapes.Triangle;
import shapes.Rectangle; 
import java.util.Objects;

/**
 * this class is the Symbol in the slotMachine
 * this class is the Symbol in the Wheel
 *
 * @author Steveen-Gualdron
 * @version 0.3
 */
public class Symbol {
    
    private int positionAtTheWheel;
    private Triangle symbolShape;
    private Rectangle squareShape;
    private boolean isTriangleActive;
    
    /**Constructor symbol, dyadic method class
     * @param color color is the color of this symbol.
     */
    public Symbol(String color) throws SlotMachineException {
        if (color == null || color.trim().isEmpty()) {
            throw new SlotMachineException(SlotMachineException.INVALID_COLOR);
        }
        symbolShape = new Triangle();
        symbolShape.changeColor(color);
        symbolShape.changeSize(30, 30);
        
        squareShape = new Rectangle();
        squareShape.changeColor(color);
        squareShape.changeSize(10, 10);
        
        isTriangleActive = true; 
    }
    
    /**Constructor symbol, dyadic method class
     * @param color color is the color of this symbol.
     * @param pos pos is the position of this symbol at the wheel.
     */
    public Symbol(String color, int pos) throws SlotMachineException {
        this(color);
        positionAtTheWheel = pos;
    }

    /**Changes the visible shape of this symbol to a square.
     */
    public void changeToSquare() {
        if (isTriangleActive) {
            boolean wasVisible = symbolShape.isVisible();
            if (wasVisible) {
                symbolShape.makeInvisible();
            }
            
            isTriangleActive = false;
            
            if (wasVisible) {
                squareShape.makeVisible();
            }
        }
    }
    
    /**Changes the visible shape of this symbol back to a triangle.
     */
    public void changeToTriangle() {
        if (!isTriangleActive) {
            boolean wasVisible = squareShape.isVisible();
            if (wasVisible) {
                squareShape.makeInvisible();
            }
            
            isTriangleActive = true;
            
            if (wasVisible) {
                symbolShape.makeVisible();
            }
        }
    }

    /**Sets a new X and Y position to this symbol.
     * @param newPosX newPosX that going to set like x position of this symbol.
     * @param newPosY newPosY that going to set like y position of this symbol. 
     */
    public void changePosition(int newPosX, int newPosY) {
        if (isTriangleActive) {
            this.symbolShape.setPosition(newPosX, newPosY);
            if (symbolShape.isVisible()) {
                this.symbolShape.frameFlickering();
            }
        } else {
            this.squareShape.setPosition(newPosX, newPosY);
            if (squareShape.isVisible()) {
                this.squareShape.frameFlickering();
            }
        }
    }
    
    /**Moves this symbol slowly vertically.
     * @param howMany howMany is the distance to move this symbol.
     */
    public void moveSlowly(int howMany) {
        if (isTriangleActive) {
            symbolShape.slowMoveVertical(howMany);
        } else {
            squareShape.slowMoveVertical(howMany);
        }
    }

    /**Makes this symbol visible.
     */
    public void makeVisible() {
        this.symbolShape.makeVisible();
        
    }

    /**Makes this symbol invisible.
     */
    public void makeInvisible() {
        
        this.squareShape.makeInvisible();

    }
    
    /**Flickers the frame of this symbol.
     */
    public void frameFlickering() {
        if (isTriangleActive) {
            symbolShape.frameFlickering();
        } else {
            squareShape.frameFlickering();
        }
    }

    /**Gets the color of this symbol.
     * @return the color of this symbol.
     */
    public String getColor() {
        return isTriangleActive ? this.symbolShape.getColor() : this.squareShape.getColor();
    }

    /**Checks if this symbol is visible.
     * @return true if it is visible, false otherwise.
     */
    public boolean isVisible() {
        return isTriangleActive ? this.symbolShape.isVisible() : this.squareShape.isVisible();
    }

    /**Sets a new position at the wheel to this symbol.
     * @param newPositionAtTheWheel newPositionAtTheWheel is the new position at the wheel.
     */
    public void setPositionAtTheWheel(int newPositionAtTheWheel) {
        this.positionAtTheWheel = newPositionAtTheWheel;
    }
    
    /**Gets the position at the wheel of this symbol.
     * @return the position at the wheel.
     */
    public int getPositionAtTheWheel() {
        return positionAtTheWheel;
    }
    
    /**Compares this symbol with another symbol.
     * @param symbol symbol is the symbol to compare.
     * @return true if both symbols have the same position and color.
     */
    public boolean equals(Symbol symbol) {
        boolean hasSamePosition = positionAtTheWheel == symbol.getPositionAtTheWheel();
        boolean hasSameColor = Objects.equals(getColor(), symbol.getColor());
        return hasSamePosition && hasSameColor;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Symbol symbol = (Symbol) obj;
        return this.equals(symbol);
    }

    /**
     * Permite a las clases hijas (como Ephemeral) modificar el tamaño.
     * @param newHeight newHeight is the new height of this symbol.
     * @param newWidth newWidth is the new width of this symbol.
     */
    protected void changeSize(int newHeight, int newWidth) {
        if (isTriangleActive) {
            this.symbolShape.changeSize(newHeight, newWidth);
        } else {
            this.squareShape.changeSize(newHeight, newWidth);
        }
    }

    /**
     * Permite a las clases hijas acceder a la figura sin exponerla públicamente.
     * @return the triangle shape of this symbol.
     */
    protected Triangle getSymbolShape() {
        return this.symbolShape;
    }
    
    /**
     * Permite a las clases hijas acceder a la figura cuadrada sin exponerla públicamente.
     * @return the square shape of this symbol.
     */
    protected Rectangle getSquareShape() {
        return this.squareShape;
    }
    
    /**
     * efect specials symbols 
     */
    protected void efect() {
        
    }
}