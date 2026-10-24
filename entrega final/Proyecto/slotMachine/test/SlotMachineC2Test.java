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
    public void setUp() {

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
    public void shouldntDeleteWheelWhenIsLocked() {
        int newAddWheel = 5;
        for (int i = 1; i <= newAddWheel; i++) {
            sltmchn.addWheel(i);
        }
        
        sltmchn.lock(5);
        sltmchn.delWheel(5);
        
        assertFalse(sltmchn.ok());
    }
    
     
    
    
    @Test
    public void shouldSpinToObtainASpecificConfigurationOfSymbols() {
        int newAddWheel = 5;
        for (int i = 1; i <= newAddWheel; i++) {
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
    public void tearDown() {
        sltmchn.exit();
    }
}