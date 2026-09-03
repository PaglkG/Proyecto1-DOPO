

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * The test class SlotMachineAceptanceTest.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class SlotMachineAceptanceTest {
    
    private SlotMachine slmch;
    
    @BeforeEach
    public void setUp() {
        slmch = new SlotMachine();
    }
    
    @Test
    public void shouldAddWheelsCorrectly() {
        int NUMBER_WHEELS_TO_ADD = 3;
        for (int i = 0; i < NUMBER_WHEELS_TO_ADD; i++) {
            slmch.addWheel(i);
        }
    }
    
    @Test
    public void shouldDeleteWheelsCorrectly() {
        int NUMBER_WHEELS_TO_ADD = 5;
        for (int i = 0; i < NUMBER_WHEELS_TO_ADD; i++) {
            slmch.addWheel(i);
        }
        
        slmch.delWheel(3);
        slmch.delWheel(2);
    }
}