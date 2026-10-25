package slotMachine.test;

import slotMachine.*;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * The test class SlotMachineTest.
 *
 * @author  Steveen-Gualdron
 * @version 0.1
 */
public class SlotMachineC1Test {
    
    private SlotMachine sltmchn;
    
    @Before
    public void setUp() {
        sltmchn = new SlotMachine();
    }
    
    
    @Test
    public void shouldAddWheel() {
        sltmchn.addWheel(1);
        sltmchn.addWheel(2);
        assertTrue(sltmchn.ok());
    }
    
    
    @Test
    public void shouldGiveSymbols() throws SlotMachineException {
        int newAddWheel= 5;
        for (int i = 1; i <= newAddWheel; i++) {
            sltmchn.addWheel(i);
        }
        sltmchn.addSymbol(1, "magenta");
        sltmchn.addSymbol(2, "red");
        sltmchn.addSymbol(3, "yellow");
        sltmchn.addSymbol(4, "blue");
        sltmchn.addSymbol(5, "green");
        String[] proof = sltmchn.symbols();
        String[] result = new String[]{"magenta", "red", "yellow", "blue", "green"};
        assertArrayEquals(result, proof);
    }
    
    @Test
    public void shouldGiveConfiguration() throws SlotMachineException{
        int newAddWheel = 5;
        for (int i = 1; i <= newAddWheel; i++) {
            sltmchn.addWheel(i);
        }
        sltmchn.addSymbol(1, "magenta");
        sltmchn.addSymbol(2, "red");
        sltmchn.addSymbol(3, "yellow");
        sltmchn.addSymbol(4, "blue");
        sltmchn.addSymbol(5, "green");
        String[] proof = sltmchn.configuration();
        String[] result = new String[]{"magenta","red", "yellow","blue", "green"};
        assertArrayEquals(result, proof);
    }
    
    @Test
    public void shouldGiveNumberDistingSymbols() throws SlotMachineException {
        int newAddWheel = 5;
        for (int i = 1; i <= newAddWheel; i++) {
            sltmchn.addWheel(i);
        }
        sltmchn.addSymbol(1, "magenta");
        sltmchn.addSymbol(2, "red");
        sltmchn.addSymbol(3, "yellow");
        sltmchn.addSymbol(4, "blue");
        sltmchn.addSymbol(5, "green");
        int numDistinctSymbols = sltmchn.distinctSymbols();
        assertEquals(5, numDistinctSymbols);
    }
    
    
    @Test
    public void shouldBeJackpot() throws SlotMachineException {
        int newAddWheel = 5;
        for (int i = 1; i <= newAddWheel; i++) {
            sltmchn.addWheel(i);
            sltmchn.addSymbol(i, "magenta");
            sltmchn.addSymbol(i, "red");
            sltmchn.addSymbol(i, "yellow");
        }
        boolean isJackpot = sltmchn.isJackpot();
        assertTrue(isJackpot);
    }
    
        
    @Test
    public void shouldExitTheProgram() {
        int newAddWheel = 5;
        for (int i = 1; i <= newAddWheel; i++) {
            sltmchn.addWheel(i);
        }
        sltmchn.exit();
        assertTrue(sltmchn.ok());
    }
    
    @Test
    public void shouldFailWhenAddingSymbolToNonexistentWheel() throws SlotMachineException { 
        sltmchn.addWheel(1);
        sltmchn.addSymbol(5, "red"); 
        assertFalse(sltmchn.ok());
    }
    
    @Test
    public void shouldPlaceSymbolCorrectly() throws SlotMachineException {
        sltmchn.addWheel(1);
        sltmchn.addSymbol(1, "red");
        sltmchn.addSymbol(1, "blue");
        sltmchn.addSymbol(1, "green");
        
        sltmchn.placeSymbol(1, "green");
        
        String[] currentConfig = sltmchn.configuration();
        assertEquals("green", currentConfig[0]);
        assertTrue(sltmchn.ok());
    }

    @Test
    public void shouldNotPlaceSymbolWhenWheelIsLocked() throws SlotMachineException {
        sltmchn.addWheel(1);
        sltmchn.addSymbol(1, "magenta");
        sltmchn.addSymbol(1, "yellow");
        
        sltmchn.placeSymbol(1, "magenta");
        sltmchn.lock(1);
        
        sltmchn.placeSymbol(1, "yellow");
        
        String[] currentConfig = sltmchn.configuration();
        assertEquals("magenta", currentConfig[0]);
    }
    
    @After
    public void tearDown() {
        sltmchn.exit();
    }
}