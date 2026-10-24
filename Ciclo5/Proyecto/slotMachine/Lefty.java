package slotMachine;

/**
 * Write a description of class Lefty here.
 * This class represents a wheel that copies the symbols and state of the wheel to its left.
 * @author Pablo Andres Gualdron Lindo
 * @version 1.0
 */
public class Lefty extends Wheel {

    /**
     * Constructor for objects of class Lefty
     */
    public Lefty() {
        super();
        this.changeColor("blue");
    }

 
    /**
     * Clears its current symbols and copies the symbols and selected state from the left wheel.
     */
    private void copy(Wheel leftWheel) {
    if (!isLocked() && leftWheel != null) {
        this.symbols.clear();
        this.symbols.putAll(leftWheel.getSymbols());
        this.selectedSymbol = leftWheel.getSelectedSymbol(); 
        this.selectedSymbolInteger = leftWheel.getSelectedSymbolInteger();
        
        if (isVisible() && this.selectedSymbol != null) {
            this.selectedSymbol.frameFlickering();
            }
        }
    }

    /**
     * Checks if it can spin.
     * @return true if it can spin, false otherwise.
     */
    @Override
    public boolean canSpin() {
        return true;
    }   

    /**
     * Overrides the spin method to perform a copy if a left wheel exists, 
     * otherwise it spins normally.
     */
    public void spin(Wheel leftWheel) {
        if (!isLocked() && leftWheel != null) {
            copy(leftWheel);
        } else {
            super.spin();
        }
    }
    
    /**
     * Checks if it is a swap.
     * @return true if it is a swap, false otherwise.
     */
    @Override
    public boolean IsSwap() {
        return false;
    }
}