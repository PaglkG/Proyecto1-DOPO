package slotMachine;

/**
 * Write a description of class Lefty here.
 * This class represents a wheel that copies the symbols and state of the wheel to its left.
 * 
 * @version 1.0
 */
public class Lefty extends Wheel {

    private Wheel leftWheel;

    /**
     * Constructor for objects of class Lefty
     */
    public Lefty() {
        super();
    }

    /**
     * Sets the wheel located to the left of this one.
     * @param leftWheel The wheel that will be copied.
     */
    public void getWheelToCopy(Wheel leftWheel) {
        this.leftWheel = leftWheel;
    }

    /**
     * Clears its current symbols and copies the symbols and selected state from the left wheel.
     */
    public void copy() {
        if (!isLocked() && this.leftWheel != null) {
            this.symbols.clear();
            this.symbols.putAll(this.leftWheel.getSymbols());
            this.selectedSymbol = this.leftWheel.selectedSymbol;
            this.selectedSymbolInteger = this.leftWheel.selectedSymbolInteger;
            
            if (isVisible() && this.selectedSymbol != null) {
                this.selectedSymbol.frameFlickering();
            }
        }
    }

    /**
     * Overrides the spin method to perform a copy if a left wheel exists, 
     * otherwise it spins normally.
     */
    @Override
    public void spin() {
        if (!isLocked() && this.leftWheel != null) {
            copy();
        } else {
            super.spin();
        }
    }
}