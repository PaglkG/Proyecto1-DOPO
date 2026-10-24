package slotMachine;

/**
 * Exception class for handling errors in the slot machine.
 *
 * @author pablo andres gualdron lindo
 * @version 1.0
 */
public class SlotMachineException extends Exception {
    
    private static final String ERROR = "error";

    /**
     * Constructor for the SlotMachineException.
     * @param message the detail message of the exception.
     */
    public SlotMachineException(String message) {
        super(message);
    }
}