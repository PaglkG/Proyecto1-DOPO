package slotMachine;


import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import shapes.Canvas;

import java.util.Map;

/**
 * The test class SlotMachineAceptanceTest.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class SlotMachineAceptanceTest {
    
    private SlotMachine slmch;
    private Map<Integer, Wheel> wheels;
    private static int numWheels;
    
    @BeforeEach
    public void setUp() {
        slmch = new SlotMachine();
        wheels = slmch.getWheels();
        numWheels = wheels.size();
        slmch.frameFlickering();
    }
    
    @Test
    public void shouldAddWheelsCorrectly() throws InterruptedException {
        int NUMBER_WHEELS_TO_ADD = 3;
        for (int i = 1; i <= NUMBER_WHEELS_TO_ADD; i++) {
            slmch.addWheel(i);
            Thread.sleep(500);
        }
    }
    
    @Test
    public void shouldDeleteWheelsCorrectly() throws InterruptedException {
        int NUMBER_WHEELS_TO_ADD = 5;
        for (int i = 1; i <= NUMBER_WHEELS_TO_ADD; i++) {
            slmch.addWheel(i);
        }
        Thread.sleep(1000);
        slmch.delWheel(3);
        Thread.sleep(1000);
        slmch.delWheel(1);
        Thread.sleep(1000);
    }
    
    @Test
    public void shouldAddSymbolEveryWheel() throws InterruptedException {
        int NUMBER_WHEELS_TO_ADD = 5;
        for (int i = 1; i <= NUMBER_WHEELS_TO_ADD; i++) {
            slmch.addWheel(i);
        }
        slmch.addSymbol(1, "magenta");
        Thread.sleep(500);
        slmch.addSymbol(2, "red");
        Thread.sleep(500);
        slmch.addSymbol(3, "yellow");
        Thread.sleep(500);
        slmch.addSymbol(4, "blue");
        Thread.sleep(500);
        slmch.addSymbol(5, "green");
        Thread.sleep(500);
    }
    
    @Test
    public void shouldDelSpecificSymbolWheel() throws InterruptedException {
        int NUMBER_WHEELS_TO_ADD = 5;
        for (int i = 0; i < NUMBER_WHEELS_TO_ADD; i++) {
            slmch.addWheel(i);
            slmch.addSymbol(i, "magenta");
        }
        Thread.sleep(1000);
        slmch.delSymbol("magenta");
        Thread.sleep(1000);
    }
    
    @Test
    public void shouldDelSpecificSymbolWheelAndSpin() throws InterruptedException {
        int NUMBER_WHEELS_TO_ADD = 5;
        for (int i = 0; i < NUMBER_WHEELS_TO_ADD; i++) {
            slmch.addWheel(i);
            slmch.addSymbol(i, "magenta");
            slmch.addSymbol(i, "blue");
        }
        Thread.sleep(1000);
        slmch.delSymbol("magenta");
        Thread.sleep(1000);
    }
    
    @Test
    public void shouldPlaceSymbols() throws InterruptedException {
        int NUMBER_WHEELS_TO_PLACE = 5;
        for (int i = 0; i < NUMBER_WHEELS_TO_PLACE; i++) {
            slmch.addWheel(i);
            slmch.placeSymbol(i, "magenta");
            Thread.sleep(500);
        }
        Thread.sleep(1500);
        for (int i = 0; i < NUMBER_WHEELS_TO_PLACE; i++) {
            slmch.addSymbol(i, "blue");
            slmch.spin();
            Thread.sleep(500);
        }
        Thread.sleep(1500);
    }
    
    @Test
    public void shouldSpinSpecificWheel() throws InterruptedException {
        int NUMBER_WHEELS_TO_ADD = 5;
        for (int i = 1; i <= NUMBER_WHEELS_TO_ADD; i++) {
            slmch.addWheel(i);
            slmch.addSymbol(i, "magenta");
            slmch.addSymbol(i, "blue");
            slmch.addSymbol(i, "yellow");
            slmch.addSymbol(i, "red");
            slmch.addSymbol(i, "green");
            Thread.sleep(500);
        }
        Thread.sleep(1000);
        slmch.spin(2); //Solo debería de cambiar la 2da y última rueda
        slmch.spin(5);
        Thread.sleep(1500);
    }
    
    @Test 
    public void shouldSpinAllWheels() throws InterruptedException {
        int NUMBER_WHEELS_TO_ADD = 5;
        for (int i = 1; i <= NUMBER_WHEELS_TO_ADD; i++) {
            slmch.addWheel(i);
            slmch.addSymbol(i, "magenta");
            slmch.addSymbol(i, "blue");
            slmch.addSymbol(i, "yellow");
            slmch.addSymbol(i, "red");
            slmch.addSymbol(i, "green");
            slmch.addSymbol(i, "blue");
            slmch.addSymbol(i, "white");
            slmch.addSymbol(i, "blue");
            Thread.sleep(500);
        }
        slmch.spin();
        Thread.sleep(1500);
    }
    
    @Test
    public void shouldSwapTwoWheels() throws InterruptedException {
        int NUMBER_WHEELS_TO_ADD = 4;
        for (int i = 1; i <= NUMBER_WHEELS_TO_ADD; i++) {
            slmch.addWheel(i);
        }
        slmch.addSymbol(1, "yellow");
        slmch.addSymbol(2, "blue");
        slmch.addSymbol(3, "red");
        slmch.addSymbol(4, "magenta"); // Se agregan simbolos para identificar las ruedas
        
        Thread.sleep(1500);
        slmch.swap(4, 2);
        Thread.sleep(1500);
        slmch.swap(1, 3);
        Thread.sleep(1500);
    }
    
    @Test 
    public void shouldLockAndUnlockSomeWheelsSpin() throws InterruptedException {
        int NUMBER_WHEELS_TO_ADD = 5;
        for (int i = 1; i <= NUMBER_WHEELS_TO_ADD; i++) {
            slmch.addWheel(i);
            slmch.addSymbol(i, "magenta");
            slmch.addSymbol(i, "blue");
            slmch.addSymbol(i, "yellow");
            slmch.addSymbol(i, "red");
            slmch.addSymbol(i, "green");
            slmch.addSymbol(i, "blue");
            slmch.addSymbol(i, "white");
            slmch.addSymbol(i, "blue");
            Thread.sleep(500);
        }
        slmch.lock(1);
        slmch.lock(3);
        slmch.spin(); // Solamente deberian de girar la rueda 2, 4, 5 (Cambian de selectedSymbol o el simbolo principal)
        Thread.sleep(2000);
        slmch.unlock(1);
        slmch.unlock(3);
        slmch.spin(); // Giran todas
        Thread.sleep(2500);
        slmch.lock(1);
        slmch.lock(3);
        slmch.spin(); // Solamente deberian de girar la rueda 2, 4, 5 (Cambian de selectedSymbol o el simbolo principal)
        Thread.sleep(2000);
    }
    
    @Test
    public void shouldntDeleteWheelWhenIsLocked() throws InterruptedException{
        int NUMBER_WHEELS_TO_ADD = 5;
        for (int i = 1; i <= NUMBER_WHEELS_TO_ADD; i++) {
            slmch.addWheel(i);
            Thread.sleep(500);
        }
        slmch.lock(2);
        slmch.lock(4);
        for (int i = 1; i <= NUMBER_WHEELS_TO_ADD; i++) {
            slmch.delWheel(i);
            Thread.sleep(500);
        }
    }
    
    @Test
    public void shouldntSwapWheelWhenIsLocked() throws InterruptedException{
        int NUMBER_WHEELS_TO_ADD = 5;
        for (int i = 1; i <= NUMBER_WHEELS_TO_ADD; i++) {
            slmch.addWheel(i);
            Thread.sleep(500);
        }
        Wheel wheel1 = wheels.get(0), wheel2 = wheels.get(1), wheel4 = wheels.get(3), wheel5 = wheels.get(4);
        wheel2.addSymbol("yellow");
        Thread.sleep(500);
        wheel4.addSymbol("magenta");
        slmch.lock(2);
        slmch.lock(4);
        Thread.sleep(500);
        slmch.swap(2, 4);
        Thread.sleep(1500);
        wheel5.addSymbol("blue");
        Thread.sleep(500);
        wheel1.addSymbol("cyan");
        Thread.sleep(500);
        slmch.swap(1, 5);
        Thread.sleep(1500);
        slmch.swap(2, 5);
        Thread.sleep(2500);
    }
    
    @AfterEach
    void tearsDown() {
        slmch.makeInvisible();
        slmch.exit();
        slmch = null;
        wheels.clear();
    }
    
    @AfterAll
    static void tearDownClass() {
        Canvas.getCanvas().close();
    }
}