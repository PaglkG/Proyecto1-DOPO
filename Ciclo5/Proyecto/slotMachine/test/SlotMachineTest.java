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

public class SlotMachineTest {
    
    private SlotMachine slmch;
    
    @Before
    public void setUp() {
        slmch = new SlotMachine();
    }

    @Test
    public void shouldInitializeEmptyCorrectly() {
        assertFalse(slmch.isJackpot());
        assertEquals(0, slmch.distinctSymbols());
        assertEquals(0, slmch.configuration().length);
    }

    @Test
    public void shouldInitializeWithNWheelsConstructor() {
        SlotMachine slmchN = new SlotMachine(5);
        assertTrue(slmchN.ok());
        assertEquals(5, slmchN.configuration().length);
        slmchN.exit();
    }
    
    @Test
    public void shouldAddWheelsCorrectly() {
        for (int i = 1; i <= 3; i++) {
            slmch.addWheel(i);
            assertTrue(slmch.ok());
        }
    }
    
    @Test
    public void shouldDeleteWheelsCorrectly() {
        for (int i = 1; i <= 5; i++) {
            slmch.addWheel(i);
        }
        slmch.delWheel(3);
        assertTrue(slmch.ok());
        slmch.delWheel(4); 
        assertTrue(slmch.ok());
    }

    @Test
    public void shouldFailToDeleteWhenMachineIsEmpty() {
        slmch.delWheel(1);
        assertFalse(slmch.ok());
    }

    @Test
    public void shouldAdjustInvalidWheelPositionOnDelete() {
        slmch.addWheel(1);
        slmch.addWheel(2);
        slmch.delWheel(5);
        assertTrue(slmch.ok());
        
        slmch.delWheel(1);
        assertTrue(slmch.ok());
    }
    
    @Test
    public void shouldAdjustInvalidWheelPositionOnAdd() {
        slmch.addWheel(5);
        assertTrue(slmch.ok());
        slmch.addSymbol(1, "red");
        assertTrue(slmch.ok());
    }
    
    @Test
    public void shouldAddSymbolEveryWheel() {
        for (int i = 1; i <= 5; i++) {
            slmch.addWheel(i);
            slmch.addSymbol(i, "color" + i);
            assertTrue(slmch.ok());
        }
    }

    @Test
    public void shouldFailToAddSymbolToNonExistentWheel() {
        slmch.addWheel(1);
        slmch.addSymbol(2, "red");
        assertFalse(slmch.ok());
    }
    
    @Test
    public void shouldReturnAllSymbolsCorrectly() {
        for (int i = 1; i <= 3; i++) {
            slmch.addWheel(i);
        }
        slmch.addSymbol(1, "red");
        slmch.addSymbol(2, "blue");
        slmch.addSymbol(3, "green");
        
        String[] symbolsIdeal = {"red", "blue", "green"};
        assertArrayEquals(symbolsIdeal, slmch.symbols());
    }
    
    @Test
    public void shouldDelSpecificSymbolWheel() {
        for (int i = 1; i <= 3; i++) {
            slmch.addWheel(i);
            slmch.addSymbol(i, "magenta");
            slmch.addSymbol(i, "blue");
        }
        slmch.delSymbol("magenta");
        assertTrue(slmch.ok());
        
        String[] remainingSymbols = slmch.symbols();
        for (String color : remainingSymbols) {
            assertNotEquals("magenta", color);
        }
    }

    @Test
    public void shouldPlaceSymbolsCorrectly() {
        for (int i = 1; i <= 2; i++) {
            slmch.addWheel(i);
            slmch.addSymbol(i, "magenta");
            slmch.addSymbol(i, "cyan");
            slmch.placeSymbol(i, "cyan");
            assertTrue(slmch.ok());
        }
        String[] config = slmch.configuration();
        assertEquals("cyan", config[0]);
        assertEquals("cyan", config[1]);
    }

    @Test
    public void shouldFailToPlaceSymbolOnNonExistentWheel() {
        slmch.addWheel(1);
        slmch.placeSymbol(2, "red");
        assertFalse(slmch.ok());
    }

    @Test
    public void shouldDetectJackpotCorrectly() {
        for (int i = 1; i <= 3; i++) {
            slmch.addWheel(i);
            slmch.addSymbol(i, "green");
            slmch.placeSymbol(i, "green");
        }
        assertTrue(slmch.isJackpot());
    }

    @Test
    public void shouldNotDetectJackpotWhenColorsDiffer() {
        for (int i = 1; i <= 3; i++) {
            slmch.addWheel(i);
            slmch.addSymbol(i, "green");
        }
        slmch.addSymbol(2, "red");
        slmch.placeSymbol(2, "red");
        assertFalse(slmch.isJackpot());
    }

    @Test
    public void shouldCountDistinctSymbolsCorrectly() {
        for (int i = 1; i <= 4; i++) {
            slmch.addWheel(i);
            slmch.addSymbol(i, "cyan");
            slmch.addSymbol(i, "magenta");
            slmch.addSymbol(i, "yellow");
        }
        slmch.placeSymbol(1, "cyan");
        slmch.placeSymbol(2, "magenta");
        slmch.placeSymbol(3, "cyan");
        slmch.placeSymbol(4, "yellow");
        
        assertEquals(3, slmch.distinctSymbols());
    }
    
    @Test
    public void shouldSpinWithArrayConfiguration() {
        for (int i = 1; i <= 3; i++) {
            slmch.addWheel(i);
            slmch.addSymbol(i, "white");
            slmch.addSymbol(i, "black");
        }
        String[] specificSpin = {"black", "white", "black"};
        slmch.spin(specificSpin);
        
        assertTrue(slmch.ok());
        assertArrayEquals(specificSpin, slmch.configuration());
    }

    @Test
    public void shouldFailToSpinWithIncorrectArraySize() {
        for (int i = 1; i <= 3; i++) {
            slmch.addWheel(i);
        }
        String[] specificSpin = {"black", "white"};
        slmch.spin(specificSpin);
        assertFalse(slmch.ok());
    }
    
    @Test
    public void shouldSpinSpecificWheel() {
        for (int i = 1; i <= 2; i++) {
            slmch.addWheel(i);
            slmch.addSymbol(i, "magenta");
            slmch.addSymbol(i, "blue");
        }
        slmch.spin(2);
        assertTrue(slmch.ok());
    }

    @Test
    public void shouldFailToSpinNonExistentWheel() {
        slmch.addWheel(1);
        slmch.spin(2); 
        assertFalse(slmch.ok());
    }
    
    @Test 
    public void shouldSpinAllWheels() {
        for (int i = 1; i <= 5; i++) {
            slmch.addWheel(i);
            slmch.addSymbol(i, "magenta");
        }
        slmch.spin();
        assertTrue(slmch.ok());
    }
    
    @Test
    public void shouldMoveWhenSpinWithSteps() {
        for (int i = 1; i <= 3; i++) {
            slmch.addWheel(i);
            slmch.addSymbol(i, "yellow");
            slmch.addSymbol(i, "magenta");
        }
        try {
            slmch.spin(1, 2); 
            assertTrue(slmch.ok());
        } catch (InterruptedException e) {
            fail("Excepción inesperada.");
        }
    }
    
    @Test
    public void shouldSwapTwoWheels() {
        for (int i = 1; i <= 3; i++) {
            slmch.addWheel(i);
        }
        slmch.addSymbol(1, "yellow");
        slmch.addSymbol(2, "blue");
        slmch.addSymbol(3, "red");
        
        slmch.swap(1, 3);
        assertTrue(slmch.ok());
        
        String[] config = slmch.configuration();
        assertEquals("red", config[0]);
        assertEquals("yellow", config[2]);
    }

    @Test
    public void shouldFailToSwapSameWheel() {
        slmch.addWheel(1);
        slmch.swap(1, 1);
        assertTrue(slmch.ok());
    }

    @Test
    public void shouldFailToSwapNonExistentWheels() {
        slmch.addWheel(1);
        slmch.swap(1, 2); 
        assertFalse(slmch.ok());
    }
    
    @Test 
    public void shouldLockAndUnlockWheelsProperly() {
        for (int i = 1; i <= 3; i++) {
            slmch.addWheel(i);
            slmch.addSymbol(i, "magenta");
        }
        slmch.lock(1);
        slmch.spin();
        assertFalse(slmch.ok());
        
        slmch.unlock(1);
        slmch.spin();
        assertTrue(slmch.ok());
    }
    
    @Test
    public void shouldNotSwapWheelWhenIsLocked(){
        for (int i = 1; i <= 3; i++) {
            slmch.addWheel(i);
        }
        slmch.lock(2);
        
        slmch.swap(1, 2);
        assertFalse(slmch.ok()); 
        
        slmch.swap(1, 3);
        assertTrue(slmch.ok()); 
    }

    @Test
    public void shouldFailToDeleteLockedWheel() {
        slmch.addWheel(1);
        slmch.lock(1);
        slmch.delWheel(1);
        assertFalse(slmch.ok());
    }

    @Test
    public void shouldFailToLockNonExistentWheel() {
        slmch.addWheel(1);
        slmch.lock(2);
        assertFalse(slmch.ok());
    }

    @Test
    public void shouldFailToUnlockNonExistentWheel() {
        slmch.addWheel(1);
        slmch.unlock(2);
        assertFalse(slmch.ok());
    }

    @Test
    public void shouldExecuteVisibilityMethodsWithoutCrashing() {
        slmch.addWheel(1);
        slmch.makeVisible();
        slmch.makeInvisible();
        assertTrue(slmch.ok());
    }

    @After
    public void tearDown() {
        if (slmch != null) {
            slmch.exit();
            slmch = null;
        }
    }
}