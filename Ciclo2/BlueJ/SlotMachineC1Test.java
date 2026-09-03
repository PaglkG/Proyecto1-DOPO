 



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
public class SlotMachineC1Test {
    
    private SlotMachine sltmchn;
    private ArrayList<Wheel> wheels;
    
    @BeforeEach
    public void setUp() {
        sltmchn = new SlotMachine();
        wheels = sltmchn.getWheels();
    }
    
    @Test
    public void shouldCreateSlotMachine() {
        Wheel wheelCreated = wheels.get(0);
        assertTrue(wheelCreated != null);
        TreeMap<Integer, Symbol> symbolsCreated = wheelCreated.getSymbols();
        assertTrue(symbolsCreated != null);
    }
    
    @Test
    public void shouldAddWheel() {
        sltmchn.addWheel(2);
        Wheel firstWheel = wheels.get(0);
        Wheel wheelAdded = wheels.get(1);
        int xPosWheel = firstWheel.getXPosition(); // Moves 25*2
        assertEquals(50, xPosWheel);
    }
    
    @Test
    public void shouldDelWheel() {
        Wheel wheelWillBeDeleted = wheels.get(0);
        sltmchn.delWheel(1);
        assertNull(wheelWillBeDeleted);
    }
    
    @Test
    public void should() {
        
    }
}
