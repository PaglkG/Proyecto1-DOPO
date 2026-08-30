package BlueJ;



import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.TreeMap;

/**
 * The test class SlotMachineTest.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class SlotMachineTest {
    
    private SlotMachine sltmchn;
    
    @BeforeEach
    public void setUp() {
        sltmchn = new SlotMachine();
    }
    
    @Test
    public void shouldCreateSlotMachine() {
        ArrayList<Wheel> wheels = sltmchn.getWheels();
        Wheel wheelCreated = wheels.get(0);
        assertTrue(wheelCreated != null);
        TreeMap<Integer, Symbol> symbolsCreated = wheelCreated.getSymbols();
        assertTrue(symbolsCreated != null);
    }
}