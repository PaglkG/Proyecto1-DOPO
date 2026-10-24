package slotMachine;

/**
 * This symbol toggles its visibility every time it is selected in the wheel.
 *
 * @author Pablo Andres Gualdron Lindo
 * @version 1.0
 */
public class Shy extends Symbol {
    
    private boolean isVisible;

    /**
     * Constructor for objects of class Shy.
     * @param color The color of the shy symbol.
     */
    public Shy(String color) {
        super("black");
        super.changeToSquare();
        this.isVisible = super.isVisible();
    }

    /**
     * Toggles the visibility of the symbol.
     */
    public void visible() {
        if (this.isVisible) {
            makeInvisible();
            this.isVisible = false;
        } else {
            makeVisible();
            this.isVisible = true;
        }
    }

    /**
     * Applies the specific effect of the shy symbol.
     */
    @Override
    protected void efect() {
        visible();
    }
}