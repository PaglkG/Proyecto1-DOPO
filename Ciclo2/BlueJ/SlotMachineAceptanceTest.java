

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
        slmch.makeVisible();
    }
    
    @Test
    public void shouldDeleteWheelsCorrectly() {
        int NUMBER_WHEELS_TO_ADD = 5;
        for (int i = 0; i < NUMBER_WHEELS_TO_ADD; i++) {
            slmch.addWheel(i);
        }
        slmch.makeVisible();
        slmch.delWheel(3);
        slmch.delWheel(2);
    }
    
    @Test
    public void shouldAddSymbolEveryWheel() {
        int NUMBER_WHEELS_TO_ADD = 5;
        for (int i = 0; i < NUMBER_WHEELS_TO_ADD; i++) {
            slmch.addWheel(i);
        }
        slmch.addSymbol(0, "magenta");
        slmch.addSymbol(1, "red");
        slmch.addSymbol(2, "yellow");
        slmch.addSymbol(3, "blue");
        slmch.addSymbol(4, "green");
        slmch.makeVisible();
    }
    
    @Test
    public void shouldDelSpecificSymbolWheel() {
        int NUMBER_WHEELS_TO_ADD = 5;
        for (int i = 0; i < NUMBER_WHEELS_TO_ADD; i++) {
            slmch.addWheel(i);
            slmch.addSymbol(i, "magenta");
        }
        slmch.makeVisible();
        slmch.delSymbol("magenta");
    }
}