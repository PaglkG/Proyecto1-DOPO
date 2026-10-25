package slotMachine;
import java.awt.Rectangle;
/**
 * Write a description of class Ephemeral here.
 * This symbol decreases its size with each spin until it becomes a dot.
 * 
 * @version 1.0
 */
public class Ephemeral extends Symbol {

    private int currentHeight;
    private int currentWidth;

    /**
     * Constructor for objects of class Ephemeral
     * @param color The color of the ephemeral symbol.
     */
    public Ephemeral(String color) throws SlotMachineException {
        super("green");
        super.changeToSquare();
        this.currentHeight = 30;
        this.currentWidth = 30;
    }

    /**
     * Reduces the size of the symbol gradually.
     */
    public void reduce() {
        if (currentHeight > 2 && currentWidth > 2) {
            currentHeight -= 4;
            currentWidth -= 4;
            changeSize(currentHeight, currentWidth);
        }
    }

    /**
     * Applies the specific effect of the ephemeral symbol.
     */
    @Override
    protected void efect() {
        reduce();
    }
}