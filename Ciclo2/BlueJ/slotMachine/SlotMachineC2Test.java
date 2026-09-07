package slotMachine;



import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;

/**
 * The test class SlotMachineC2.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class SlotMachineC2Test {
    
    private SlotMachine sltmchn;
    private ArrayList<Wheel> wheels;
    private int numWheels;
    
    @BeforeEach
    void setUp() {
        sltmchn = new SlotMachine();
        wheels = sltmchn.getWheels();
        numWheels = wheels.size();
    }
    
    @Test
    public void shouldSwapTwoWheels() {
        sltmchn.addWheel(1);
        sltmchn.addWheel(2);
        sltmchn.addWheel(3);
        sltmchn.addWheel(4);
        Wheel firstWheel = wheels.get(0), secondWheel = wheels.get(1), thirdWheel = wheels.get(2), fourthWheel = wheels.get(3);
        sltmchn.swap(4, 2);
        sltmchn.swap(1, 3);
        Wheel proofWheel1 = sltmchn.findWheel(1), proofWheel2 = sltmchn.findWheel(2), proofWheel3 = sltmchn.findWheel(3), proofWheel4 = sltmchn.findWheel(4);
        assertEquals(proofWheel1, thirdWheel);
        assertEquals(proofWheel2, fourthWheel);
        assertEquals(proofWheel3, firstWheel);
        assertEquals(proofWheel4, secondWheel);
    }
}