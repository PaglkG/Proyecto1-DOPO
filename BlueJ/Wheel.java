package BlueJ;
import java.util.LinkedList;
import java.util.List;
/**
 * Wheel class, this is the wheel that will be installed on slot machine.
 *
 * @author Steveen-Gualdron
 * @version 0.1
 */
public class Wheel {
    private int positionX;
    private int positionY;
    private int wheelShape;
    private Rectangle rectangle;
    private boolean isStoped;
    private boolean isVisible;
    private Symbol selectedSymbol;
    private LinkedList<Symbol> symbols;

    /**Constructor class of wheel, niladic method class.
     */
    public Wheel() {
        rectangle = new Rectangle();
        rectangle.changeSize(100,30);
        rectangle.changeColor("grey");
        rectangle.makeVisible();
    }
    
    /**To set the wheel color.
     * @param newColor newColor is the color that will be set on this wheel.
     */
    public void changeColor(String newColor) {
        rectangle.changeColor(newColor);
    }

    /**Sets a new X position to this wheel.
     * @param newPosX newPosX that going to set like x position of this wheel. 
     */
    public void changePositionX(int newPosX) {
        positionX = newPosX;
        rectangle.setxPosition(newPosX);
        rectangle.makeVisible();
    }

    /**Sets a new Y position to this wheel.
     * @param newPosY newPosY that going to set like y position of this wheel. 
     */
    public void changePositionY(int newPosY) {
        positionY = newPosY;
        rectangle.setxPosition(newPosY);
        rectangle.makeVisible();
    }

    /**Add a specific symbol with its color.
     * @param symbol symbol is the color of symbol. ----------------------
     */
    public void addSymbol(Symbol triangle) {
        symbols.add(triangle.getPositionWheel(), triangle);
    }

    /**Remove a specific symbol with its color.
     * @param symbol symbol is the color of symbol that will be removed. ---------------
     */
    public void delSymbol(Symbol triangle) {
        symbols.remove(triangle.getPositionWheel());
    }
    // ----------------
    private void viewSymbol() {
            Symbol triangle = selecSymbol();
            triangle.changePositionX(positionX/2);
            triangle.changePositionY(positionX/2);
            triangle.makeVisible();
    }
    
    /**Gives the symbol that was selected when spinning the wheel.
     * @return Returns the symbol that was selected when spinning the wheel. ----------
     */
    public Symbol selecSymbol() {
        return null;
    }
    
    /**Choose a symbol randomly.
     */
    public void spin() {
    }

    /**Makes this wheel invisible.
     */
    public void makeInvisible() {
    }

    /**Add a shape for this wheel.
     */
    public void addShape() {
    }

    /**Makes this wheel visible.
     */
    public void makeVisible() {
    }

    /**Displays all existing symbol colors in order.
     * @return A string array with exiting symbols colors of this slot machine.
     */
    public String[] symbols() {
        return null;
    }

    
}
