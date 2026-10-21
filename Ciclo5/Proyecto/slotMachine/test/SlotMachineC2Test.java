package slotMachine.test;

import slotMachine.*;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * The test class SlotMachineC2Test.
 *
 * @author  Steveen-Gualdron
 * @version 0.2
 */
public class SlotMachineC2Test {
    
    private SlotMachine sltmchn;
    
    @Before
    void setUp() {

        sltmchn = new SlotMachine();
    }
    
    @Test
    public void shouldSwapTwoWheels() {
        for (int i = 1; i <= 4; i++) {
            sltmchn.addWheel(i);
        }
        sltmchn.addSymbol(1, "red");
        sltmchn.addSymbol(2, "blue");
        sltmchn.addSymbol(3, "green");
        sltmchn.addSymbol(4, "yellow");
        
        sltmchn.swap(4, 2);
        sltmchn.swap(1, 3);
        
        String[] expectedConfig = {"green", "yellow", "red", "blue"};
        assertArrayEquals(expectedConfig, sltmchn.configuration());
        assertTrue(sltmchn.ok());
    }
    
    
    
    @Test
    public void shouldUnlockSpecificWheel() {
        int NUMBER_WHEELS_TO_ADD = 5;
        for (int i = 1; i <= NUMBER_WHEELS_TO_ADD; i++) {
            sltmchn.addWheel(i);
            sltmchn.addSymbol(i, "magenta");
            sltmchn.addSymbol(i, "blue");
        }
        
        sltmchn.lock(2);
        sltmchn.lock(4);
        
        sltmchn.unlock(2);
        sltmchn.unlock(4);
        
        sltmchn.spin(2);
        assertTrue(sltmchn.ok());
        
        sltmchn.spin(4);
        assertTrue(sltmchn.ok());
    }
    
    @Test
    public void shouldntDeleteWheelWhenIsLocked() {
        int NUMBER_WHEELS_TO_ADD = 5;
        for (int i = 1; i <= NUMBER_WHEELS_TO_ADD; i++) {
            sltmchn.addWheel(i);
        }
        
        sltmchn.lock(5);
        sltmchn.delWheel(5);
        
        assertFalse(sltmchn.ok());
    }
    
    @Test
    public void shouldntSwapWheelWhenIsLocked() {
        int NUMBER_WHEELS_TO_ADD = 5;
        for (int i = 1; i <= NUMBER_WHEELS_TO_ADD; i++) {
            sltmchn.addWheel(i);
            sltmchn.addSymbol(i, "color" + i);
        }
        
        sltmchn.lock(2);
        sltmchn.lock(4);
        
        sltmchn.swap(2, 4);
        assertFalse(sltmchn.ok());
        
        sltmchn.swap(1, 5);
        assertTrue(sltmchn.ok());
        
        sltmchn.swap(2, 5);
        assertFalse(sltmchn.ok());
    }
    
    
    
    @Test
    public void shouldSpinToObtainASpecificConfigurationOfSymbols() {
        int NUMBER_WHEELS_TO_ADD = 5;
        for (int i = 1; i <= NUMBER_WHEELS_TO_ADD; i++) {
            sltmchn.addWheel(i);
            sltmchn.addSymbol(i, "magenta");
            sltmchn.addSymbol(i, "blue");
            sltmchn.addSymbol(i, "yellow");
            sltmchn.addSymbol(i, "red");
            sltmchn.addSymbol(i, "green");
            sltmchn.addSymbol(i, "gray");
            sltmchn.addSymbol(i, "white");
            sltmchn.addSymbol(i, "cyan");
            sltmchn.addSymbol(i, "pink");
        }
        
        String[] specificConfiguration = {"blue", "cyan", "yellow", "green", "pink"};
        sltmchn.spin(specificConfiguration);
        
        assertArrayEquals(specificConfiguration, sltmchn.configuration());
        assertTrue(sltmchn.ok());
    }
    
    @After
    void tearDown() {
        sltmchn.exit();
    }
}