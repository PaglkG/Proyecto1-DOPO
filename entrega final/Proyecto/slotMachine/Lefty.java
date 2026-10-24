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
    /**
     * Clears its current symbols and copies the symbols and selected state from the left wheel.
     */
    private void copy(Wheel leftWheel) {
        if (!isLocked() && leftWheel != null) {
            
            for (Symbol s : this.symbols.values()) {
                if (s != null) s.makeInvisible();
            }
            this.symbols.clear();
            this.selectedSymbol = null;
            this.selectedSymbolInteger = null;
            for (Symbol leftSymbol : leftWheel.getSymbols().values()) {
                try {
                    Class<?> symbolClass = leftSymbol.getClass();
                    Symbol newSymbol = (Symbol) symbolClass.getDeclaredConstructor(String.class).newInstance(leftSymbol.getColor());
                    this.addSymbol(newSymbol); 
                }  catch (Exception e) {
                    this.addSymbol(new Symbol(leftSymbol.getColor()));
                }
            }

            if (!this.symbols.isEmpty() && leftWheel.getSelectedSymbol() != null) {   
                if (this.isVisible() && this.selectedSymbol != null) {
                    this.selectedSymbol.makeInvisible();
                }
                int targetSelectedIndex = leftWheel.getSelectedSymbolInteger();
                this.selectedSymbol = this.symbols.get(targetSelectedIndex);
                this.selectedSymbolInteger = targetSelectedIndex;
                if (this.selectedSymbol != null) {
                    this.positionSymbol(this.selectedSymbol);
                    
                    if (this.isVisible()) {
                        this.selectedSymbol.makeVisible();
                        this.selectedSymbol.frameFlickering();
                    }
                }
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
    
    
}