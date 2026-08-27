package BlueJ.slotMachine;

import java.util.ArrayList;

/**
 * This is slot machine game, this is a variant of I problem - SlotMachine ICPC competition
 *
 * @author 
 * @version (a version number or a date)
 */
public class SlotMachine {
    private boolean isOk;
    private ArrayList<Wheel> wheels;

    /**Constructor, nyadic method class, of SlotMachine.
     */
    public SlotMachine() {
    }

    /**
     * Add a wheel to this slot machine, place the roulette wheel on the left or right side.
     * @param pos pos is the position of wheel that is added to this object.
     */
    public void addWheel(int pos) {
    }

    /**To remove a wheel, pass its left or right position.
     * If a wheel with a wheel to its right is removed, all wheels on the right move one position to the left
     * @param pos pos is the position of wheel
     */
    public void delWheel(int pos) {
    }

    /**The symbol object is created at a specific position and color.
     * @param pos pos is the position of symbol that going to go.
     * @param color color is the color of symbol that going to be created.
     */
    public void addSymbol(int pos, String color) {
    }

    /** The symbol; on each wheel is removed, object is deleted.
     * @param symbol symbol is the type of symbol that going to be created.
     */
    public void delSymbol(String symbol) {
    }

    /** The symbol is added to each wheel; shape and wheel number are requested.
     * @param wheel wheel is the number (integer) of wheel that will add the symbol. 
     * @param symbol symbol is the type of symbol that will be added at the specific number wheel.
     */
    public void placeSymbol(int wheel, String symbol) {
    }

    /**Moves a specific number of wheel.
     * @param wheel wheel indicates the number (integer) of wheel that going to be moved.
     */
    public void spin(int wheel) {
    }

    /**Moves each of the wheels.
     */
    public void spin() {
    }

    /**Displays all existing symbol colors in order.
     * @return A string array with exiting symbols colors of this slot machine.
     */
    public String[] symbols() {
        return null;
    }

    /**Displays the number of distinct colors among the symbols on the wheel that are flipped.
     * @return Number of distinct colors of the flipped symbols.
     */
    public int distincSymbols() {
        return 0;
    }

    /**Gives all colors selected by the wheels from left to right.
     * @return return an array of string with the symbols selected at the wheels.
     */
    public String[] configuration() {
        return null;
    }

    /**Tells whether all shapes selected by the wheels are identical.
     * @return true if all shapes selected by the wheels are identical,
     * false otherwise
     */
    public boolean isJackpot() {
        return false;
    }

    /**Makes the slot machine visible.
     */
    public void makeVisible() {
    }

    /**Makes the slot machine invisible.
     */
    public void makeInvisible() {
    }

    /**Deletes all objects.
     */
    public void exit() {
    }

    /**Indicates whether the last operation was successful.
     * @return True if the operation was successful,
     * False otherwise.
     */
    public boolean ok() {
        return false;
    }
}
