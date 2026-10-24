package slotMachine;

/**
 * This symbol toggles its visibility every time it is selected in the wheel.
 *
 * @author Pablo Andres Gualdron Lindo
 * @version 1.0
 */
public class Shy extends Symbol {
    
    private boolean isCurrentlyVisible;

    /**
     * Constructor for objects of class Shy.
     * @param color The color of the shy symbol.
     */
    public Shy(String color) {
        super("orange"); 
        super.changeToSquare();
        this.isCurrentlyVisible = false;
        super.makeInvisible(); 
    }

    /**
     * Toggles the visibility of the symbol.
     */
    public void visible() {
        if (this.isCurrentlyVisible) {
            super.makeInvisible();
            this.isCurrentlyVisible = false;
        } else {
            super.makeVisible();
            this.isCurrentlyVisible = true;
        }
    }

    /**
     * Applies the specific effect of the shy symbol.
     */
    @Override
    protected void efect() {
        visible();
    }

    /**
     * Returns the logical visibility state.
     * Overrides the parent method to prevent headless environment failures.
     * @return true if logically visible, false otherwise.
     */
    @Override
    public boolean isVisible() {
        return this.isCurrentlyVisible;
    }
}