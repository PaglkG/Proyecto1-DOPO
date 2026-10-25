package slotMachine;

/**
 * Exception class for handling errors in the slot machine.
 *
 * @author pablo andres gualdron lindo
 * @version 1.0
 */
public class SlotMachineException extends Exception {

    public static final String INVALID_ARGUMENT = "An argument is null or invalid.";

    public static final String INVALID_WHEEL_COUNT = "The number of wheels must be between 1 and 50.";

    public static final String INVALID_COLOR = "The symbol color cannot be null or empty.";

    public static final String UNKNOWN_WHEEL_TYPE = "The requested wheel type does not exist.";

    public static final String WHEEL_CREATION_FAILED = "The requested wheel could not be created.";

    public static final String WHEEL_OPERATION_NOT_SUPPORTED = "This wheel does not support the requested operation.";

    public static final String WHEEL_LOCKED = "The operation cannot be performed on a locked wheel.";

    public static final String UNKNOWN_SYMBOL_TYPE = "The requested symbol type does not exist.";

    public static final String SYMBOL_CREATION_FAILED = "The requested symbol could not be created.";

    public static final String SYMBOL_COPY_FAILED = "The symbol could not be copied.";

    public static final String SYMBOL_NOT_FOUND = "The requested symbol does not exist in the wheel.";

    public static final String NO_SELECTED_SYMBOL = "The wheel has no selected symbol.";

    /**
     * Creates a domain exception with a descriptive message.
     */
    public SlotMachineException(String message) {
        super(message);
    }
}