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
    public void shouldGiveSymbols() {
        int NUMBER_WHEELS_TO_ADD = 5;
        for (int i = 0; i < NUMBER_WHEELS_TO_ADD; i++) {
            sltmchn.addWheel(i);
        }
        sltmchn.addSymbol(0, "magenta");
        sltmchn.addSymbol(1, "red");
        sltmchn.addSymbol(2, "yellow");
        sltmchn.addSymbol(3, "blue");
        sltmchn.addSymbol(4, "green");
        String[] proof = sltmchn.symbols();
        String[] result = new String[]{"magenta", "red", "yellow", "blue", "green"};
        for (int i = 0; i < result.length;i++) {
            assertEquals(proof[i], result[i]);
        }
    }
}
