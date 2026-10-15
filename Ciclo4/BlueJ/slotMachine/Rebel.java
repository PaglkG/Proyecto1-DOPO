package slotMachine;

/**
 * Write a description of class Rebel here.
 * This class represents a rebel wheel that cannot be locked, swapped, or deleted.
 * 
 * @version 1.0
 */
public class Rebel extends Wheel {

    /**
     * Constructor for objects of class Rebel
     */
    public Rebel() {
        super();
        this.isLocked = false;
    }

    /**
     * A rebel wheel cannot be locked. This method overrides the parent 
     * method to do nothing.
     */
    @Override
    public void lock() {
    }

    /**
     * A rebel wheel is always unlocked.
     */
    @Override
    public void unlock() {
        this.isLocked = false;
    }

    /**
     * A rebel wheel cannot be swapped. This method overrides the parent
     * method to prevent the swap.
     * @param wheelToSwap The wheel to attempt to swap with.
     */
    @Override
    public void swap(Wheel wheelToSwap) {
    }
    
    /**
     * Prevents the wheel from being deleted by throwing an exception.
     * @throws UnsupportedOperationException always, because a Rebel cannot be deleted.
     */
    @Override
    public void validateDeletion() {
        throw new UnsupportedOperationException("A rebel wheel cannot be deleted.");
    }
}