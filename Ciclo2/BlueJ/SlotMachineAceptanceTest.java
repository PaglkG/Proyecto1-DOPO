

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
        slmch.addWheel(0);
        slmch.addWheel(1);
    }
    
}