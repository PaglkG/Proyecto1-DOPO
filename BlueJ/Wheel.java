package BlueJ;
import java.util.LinkedList;
import java.util.List;
/**
 * Wheel class, this is the wheel that will be installed on slot machine.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Wheel {
    private int positionX;
    private int positionY;
    private int wheelShape;
    private boolean isStoped;
    private boolean isVisible;
    private LinkedList<Symbol> symbols;

    /**Constructor class of wheel, niladic method class.
     */
    public Wheel() {
    }

    /**To set the wheel color.
     * @param newColor newColor is the color that will be set on this wheel.
     */
    public void changeColor(String newColor) {
    }

    /**Sets a new X position to this wheel.
     * @param newPosX newPosX that going to set like x position of this wheel. 
     */
    public void changePositionX(int newPosX) {
    }

    /**Sets a new Y position to this wheel.
     * @param newPosY newPosY that going to set like y position of this wheel. 
     */
    public void changePositionY(int newPosX) {
    }

    /**Add a specific symbol with its color.
     * @param symbol symbol is the color of symbol.
     */
    public void addSymbol(String symbol) {
    }

    /**Remove a specific symbol with its color.
     * @param symbol symbol is the color of symbol that will be removed.
     */
    public void delSymbol(String symbol) {
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

    /**Gives the symbol that was selected when spinning the wheel.
     * @return Returns the symbol that was selected when spinning the wheel.
     */
    public String selecSymbol() {
        return null;
    }
}
