package slotMachine;
import java.util.Map;


/**
 * Write a description of class Lefty here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Lefty extends Wheel implements Updatable {
    private Wheel wheelToCopy;
    
    public Lefty(Wheel wheelToCopy) {
        this.wheelToCopy = wheelToCopy;
    }
    
    public void update() {
        copyWheel();
        boolean isVisible = this.wheelShape.isVisible();
        if (isVisible) {
            this.wheelShape.frameFlickering();
        }
    }
    
    private void copyWheel() {
        int positionWheelCopy = wheelToCopy.getPositionWheel(), selectedSymbolIntToCopy = wheelToCopy.getSelectedSymbolInteger();
        boolean isLockedToCopy = wheelToCopy.isLocked();
        Symbol selectedSymbolToCopy = wheelToCopy.getSelectedSymbol();
        Map<Integer,Symbol> symbolsToCopy = wheelToCopy.getSymbols();
        this.positionWheel = positionWheelCopy;
        this.selectedSymbolInteger = selectedSymbolIntToCopy;
        this.isLocked = isLockedToCopy;
        this.selectedSymbol = selectedSymbolToCopy;
        this.symbols = symbolsToCopy;
    }
}