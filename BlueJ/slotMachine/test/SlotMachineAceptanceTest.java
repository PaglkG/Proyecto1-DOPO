package slotMachine.test;

import slotMachine.*;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import shapes.Canvas;

/**
 * The test class SlotMachineAceptanceTest.
 *
 * @author  Steveen-Gualdron
 * @version 0.3
 */
public class SlotMachineAceptanceTest {
    
    private SlotMachine slmch;
    
    @BeforeEach
    public void setUp() {
        slmch = new SlotMachine();
        // Se usa el método oficial de la interfaz pública para hacer visible el simulador en las pruebas de aceptación preparadas para presentación[cite: 5].
        slmch.makeVisible(); 
    }
    
    @Test
    public void shouldAddWheelsCorrectly() throws InterruptedException {
        int NUMBER_WHEELS_TO_ADD = 3;
        for (int i = 1; i <= NUMBER_WHEELS_TO_ADD; i++) {
            slmch.addWheel(i);
            assertTrue(slmch.ok());
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
        assertTrue(slmch.ok());
        slmch.delWheel(5);
        assertTrue(slmch.ok());
        Thread.sleep(1000);
        
        slmch.delWheel(1);
        assertTrue(slmch.ok());
        Thread.sleep(1000);
    }
    
    @Test
    public void shouldAddSymbolEveryWheel() throws InterruptedException {
        int NUMBER_WHEELS_TO_ADD = 5;
        for (int i = 1; i <= NUMBER_WHEELS_TO_ADD; i++) {
            slmch.addWheel(i);
        }
        slmch.addSymbol(1, "magenta");
        assertTrue(slmch.ok());
        Thread.sleep(500);
        
        slmch.addSymbol(2, "red");
        assertTrue(slmch.ok());
        Thread.sleep(500);
        
        slmch.addSymbol(3, "yellow");
        assertTrue(slmch.ok());
        Thread.sleep(500);
        
        slmch.addSymbol(4, "blue");
        assertTrue(slmch.ok());
        Thread.sleep(500);
        
        slmch.addSymbol(5, "green");
        assertTrue(slmch.ok());
        Thread.sleep(500);
    }
    
    @Test
    public void shouldSymbolsEveryWheel() {
        int NUMBER_WHEELS_TO_ADD = 5;
        for (int i = 1; i <= NUMBER_WHEELS_TO_ADD; i++) {
            slmch.addWheel(i);
        }
        slmch.addSymbol(1, "magenta");
        slmch.addSymbol(1, "red");
        slmch.addSymbol(2, "red");
        slmch.addSymbol(2, "green");
        slmch.addSymbol(3, "yellow");
        slmch.addSymbol(4, "blue");
        slmch.addSymbol(5, "green");
        
        String[] symbols = slmch.symbols();
        String[] symbolsIdeal ={"magenta","red","red","green","yellow","blue","green"};
        assertArrayEquals(symbolsIdeal, symbols);
        assertTrue(slmch.ok());
    }
    
    @Test
    public void shouldDistinctSymbolsEveryWheel() {
        int NUMBER_WHEELS_TO_ADD = 5;
        for (int i = 1; i <= NUMBER_WHEELS_TO_ADD; i++) {
            slmch.addWheel(i);
        }
        slmch.addSymbol(1, "magenta");
        slmch.addSymbol(1, "red");
        slmch.addSymbol(2, "red");
        slmch.addSymbol(2, "green");
        slmch.addSymbol(3, "yellow"); 
        slmch.addSymbol(4, "blue");   
        slmch.addSymbol(5, "green");
        
        int symbols = slmch.distinctSymbols();
        int symbolsIdeal = 3;
        assertEquals(symbolsIdeal, symbols);
        assertTrue(slmch.ok());
    }
    
    @Test
    public void shouldDelSpecificSymbolWheel() throws InterruptedException {
        int NUMBER_WHEELS_TO_ADD = 5;
        for (int i = 1; i <= NUMBER_WHEELS_TO_ADD; i++) {
            slmch.addWheel(i);
            slmch.addSymbol(i, "magenta");
        }
        Thread.sleep(1000);
        slmch.delSymbol("magenta");
        assertTrue(slmch.ok());
        Thread.sleep(1000);
    }
    
    @Test
    public void shouldDelSpecificSymbolWheelAndSpin() throws InterruptedException {
        int NUMBER_WHEELS_TO_ADD = 5;
        for (int i = 1; i <= NUMBER_WHEELS_TO_ADD; i++) {
            slmch.addWheel(i);
            slmch.addSymbol(i, "magenta");
            slmch.addSymbol(i, "blue");
        }
        Thread.sleep(1000);
        slmch.delSymbol("magenta");
        assertTrue(slmch.ok());
        
        slmch.spin();
        assertTrue(slmch.ok());
        Thread.sleep(1000);
    }
    
    @Test
    public void shouldPlaceSymbols() throws InterruptedException {
        int NUMBER_WHEELS_TO_PLACE = 5;
        for (int i = 1; i <= NUMBER_WHEELS_TO_PLACE; i++) {
            slmch.addWheel(i);
            slmch.addSymbol(i, "magenta");
            slmch.placeSymbol(i, "magenta");
            assertTrue(slmch.ok());
            Thread.sleep(500);
        }
        Thread.sleep(1500);
        for (int i = 1; i <= NUMBER_WHEELS_TO_PLACE; i++) {
            slmch.addSymbol(i, "blue");
            slmch.spin();
            assertTrue(slmch.ok());
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
        }
        Thread.sleep(1000);
        
        slmch.spin(2); //Solo debería de cambiar la 2da rueda
        assertTrue(slmch.ok());
        
        slmch.spin(5); //Solo debería de cambiar la última rueda
        assertTrue(slmch.ok());
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
            slmch.addSymbol(i, "white");
        }
        Thread.sleep(500);
        slmch.spin();
        assertTrue(slmch.ok());
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
        slmch.addSymbol(4, "magenta"); 
        
        Thread.sleep(1500);
        slmch.swap(4, 2);
        assertTrue(slmch.ok());
        Thread.sleep(1500);
        
        slmch.swap(1, 3);
        assertTrue(slmch.ok());
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
            slmch.addSymbol(i, "white");
        }
        Thread.sleep(500);
        slmch.lock(1);
        slmch.lock(3);
        
        slmch.spin(); // El giro global retorna false si alguna no puede girar
        assertFalse(slmch.ok()); 
        Thread.sleep(2000);
        
        slmch.unlock(1);
        slmch.unlock(3);
        slmch.spin(); // Giran todas
        assertTrue(slmch.ok());
        Thread.sleep(2500);
    }
    
    @Test
    public void shouldntDeleteWheelWhenIsLocked() throws InterruptedException{
        int NUMBER_WHEELS_TO_ADD = 5;
        for (int i = 1; i <= NUMBER_WHEELS_TO_ADD; i++) {
            slmch.addWheel(i);
        }
        Thread.sleep(500);
        slmch.lock(2);
        slmch.lock(4);
        
        for (int i = 1; i <= NUMBER_WHEELS_TO_ADD; i++) {
            slmch.delWheel(i);
            // Si la rueda es la 2 o 4, ok() será false
            if (i == 2 || i == 4) {
                assertFalse(slmch.ok());
            } else {
                assertTrue(slmch.ok());
            }
            Thread.sleep(500);
        }
    }
    
    @Test
    public void shouldntSwapWheelWhenIsLocked() throws InterruptedException{
        int NUMBER_WHEELS_TO_ADD = 5;
        for (int i = 1; i <= NUMBER_WHEELS_TO_ADD; i++) {
            slmch.addWheel(i);
        }
        Thread.sleep(500);
        
        // Se corrige el acceso directo a los símbolos para usar la interfaz de la máquina
        slmch.addSymbol(2, "yellow");
        slmch.addSymbol(4, "magenta");
        slmch.addSymbol(5, "blue");
        slmch.addSymbol(1, "cyan");
        Thread.sleep(500);
        
        slmch.lock(2);
        slmch.lock(4);
        Thread.sleep(500);
        
        slmch.swap(2, 4);
        assertFalse(slmch.ok(), "No debe intercambiar ruedas bloqueadas");
        Thread.sleep(1500);
        
        slmch.swap(1, 5);
        assertTrue(slmch.ok(), "Debe intercambiar ruedas libres");
        Thread.sleep(1500);
        
        slmch.swap(2, 5);
        assertFalse(slmch.ok(), "No debe intercambiar si una está bloqueada");
        Thread.sleep(2500);
    }
    
    @Test
    public void shouldMoveSlowlyWhenSpinWithSteps() throws InterruptedException {
        int NUMBER_WHEELS_TO_ADD = 5;
        for (int i = 1; i <= NUMBER_WHEELS_TO_ADD; i++) {
            slmch.addWheel(i);
            slmch.addSymbol(i, "yellow");
            slmch.addSymbol(i, "magenta");
            slmch.addSymbol(i, "blue");
            slmch.addSymbol(i, "cyan");
        }
        Thread.sleep(500);
        // Si el simulador está visible, este método internamente visualizará paso a paso[cite: 5].
        slmch.spin(1, 2); 
        assertTrue(slmch.ok());
        Thread.sleep(2000);
        
        slmch.spin(2, 3); 
        assertTrue(slmch.ok());
        Thread.sleep(2000);
        
        slmch.spin(4, 4); 
        assertTrue(slmch.ok());
        Thread.sleep(2000);
    }
    
    @AfterEach
    void tearDown() {
        slmch.makeInvisible();
        slmch.exit();
        slmch = null;
    }
    
    @AfterAll
    static void tearDownClass() {
        Canvas.getCanvas().close();
    }
}