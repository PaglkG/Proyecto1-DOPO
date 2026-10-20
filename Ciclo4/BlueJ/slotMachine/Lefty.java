package slotMachine;

/**
 * Write a description of class Lefty here.
 * This class represents a wheel that copies the symbols and state of the wheel to its left.
 * 
 * @version 1.0
 */
public class Lefty extends Wheel {

    /**
     * Constructor for objects of class Lefty
     */
    public Lefty() {
        super();
    }

 
    /**
     * Clears its current symbols and copies the symbols and selected state from the left wheel.
     */
    private void copy(Wheel leftWheel) {
    if (!isLocked() && leftWheel != null) {
        this.symbols.clear();
        
        // Asumiendo que symbols es un Map (si es un ArrayList usa .addAll en lugar de .putAll)
        this.symbols.putAll(leftWheel.getSymbols());
        
        // Debes usar getters en lugar de acceder directamente a la variable si es privada
        this.selectedSymbol = leftWheel.getSelectedSymbol(); 
        this.selectedSymbolInteger = leftWheel.getSelectedSymbolInteger();
        
        if (isVisible() && this.selectedSymbol != null) {
            this.selectedSymbol.frameFlickering();
            }
        }
    }

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
}