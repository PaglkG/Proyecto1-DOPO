package slotMachine.test;

import slotMachine.*;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * The test class SlotMachineTest.
 *
 * @author  Steveen-Gualdron
 * @version 0.1
 */
public class SlotMachineC1Test {
    
    private SlotMachine sltmchn;
    
    @BeforeEach
    void setUp() {
        // La máquina inicia en modo invisible por defecto, cumpliendo con los requisitos de las pruebas[cite: 5].
        sltmchn = new SlotMachine();
    }
    
    
    @Test
    public void shouldAddWheel() {
        sltmchn.addWheel(1);
        sltmchn.addWheel(2);
        assertTrue(sltmchn.ok(), "Debe permitir agregar ruedas consecutivas");
    }
    
    
    @Test
    public void shouldGiveSymbols() {
        int NUMBER_WHEELS_TO_ADD = 5;
        for (int i = 1; i <= NUMBER_WHEELS_TO_ADD; i++) {
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
    public void shouldGiveConfiguration() {
        int NUMBER_WHEELS_TO_ADD = 5;
        for (int i = 1; i <= NUMBER_WHEELS_TO_ADD; i++) {
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
    public void shouldGiveNumberDistingSymbols() {
        int NUMBER_WHEELS_TO_ADD = 5;
        for (int i = 1; i <= NUMBER_WHEELS_TO_ADD; i++) {
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
    public void shouldGiveOtherNumberDistingSymbols() {
        int NUMBER_WHEELS_TO_ADD = 5;
        for (int i = 1; i <= NUMBER_WHEELS_TO_ADD; i++) {
            sltmchn.addWheel(i);
            sltmchn.addSymbol(i, "magenta");
            sltmchn.addSymbol(i, "red");
            sltmchn.addSymbol(i, "yellow");
        }
        sltmchn.addSymbol(1, "write");
        sltmchn.addSymbol(2, "black");
        sltmchn.addSymbol(3, "blue");
        sltmchn.addSymbol(4, "green"); 
        
        int numDistinctSymbols = sltmchn.distinctSymbols();
        assertEquals(4, numDistinctSymbols);
    }
    
    @Test
    public void shouldBeJackpot() {
        int NUMBER_WHEELS_TO_ADD = 5;
        // Los índices de las posiciones se enumeran a partir de 1 según los requisitos[cite: 2].
        for (int i = 1; i <= NUMBER_WHEELS_TO_ADD; i++) {
            sltmchn.addWheel(i);
            sltmchn.addSymbol(i, "magenta");
            sltmchn.addSymbol(i, "red");
            sltmchn.addSymbol(i, "yellow");
        }
        boolean isJackpot = sltmchn.isJackpot();
        assertTrue(isJackpot);
    }
    
    @Test
    public void shouldntBeJackpot() {
        int NUMBER_WHEELS_TO_ADD = 5;
        for (int i = 1; i <= NUMBER_WHEELS_TO_ADD; i++) {
            sltmchn.addWheel(i);
            sltmchn.addSymbol(i, "magenta");
            sltmchn.addSymbol(i, "red");
            sltmchn.addSymbol(i, "yellow");
        }
        // Se reemplaza la inyección directa por la interfaz pública placeSymbol
        sltmchn.placeSymbol(2, "blue");
        boolean isJackpot = sltmchn.isJackpot();
        assertFalse(isJackpot);
    }
    
    @Test
    public void shouldExitTheProgram() {
        int NUMBER_WHEELS_TO_ADD = 5;
        for (int i = 1; i <= NUMBER_WHEELS_TO_ADD; i++) {
            sltmchn.addWheel(i);
        }
        sltmchn.exit();
        assertTrue(sltmchn.ok(), "Exit debe terminar en un estado válido");
    }
    
    @Test
    public void shouldFailWhenAddingSymbolToNonexistentWheel() { 
        sltmchn.addWheel(1);
        sltmchn.addSymbol(5, "red"); 
        assertFalse(sltmchn.ok(), "No debe permitir agregar un símbolo a una rueda inexistente");
    }
    
    @Test
    public void shouldPlaceSymbolCorrectly() {
        sltmchn.addWheel(1);
        sltmchn.addSymbol(1, "red");
        sltmchn.addSymbol(1, "blue");
        sltmchn.addSymbol(1, "green");
        
        sltmchn.placeSymbol(1, "green");
        
        String[] currentConfig = sltmchn.configuration();
        assertEquals("green", currentConfig[0], "El símbolo al frente de la rueda 1 debe ser 'green'");
        assertTrue(sltmchn.ok(), "La operación de ubicar el símbolo debió marcar ok() como true");
    }

    @Test
    public void shouldNotPlaceSymbolWhenWheelIsLocked() {
        sltmchn.addWheel(1);
        sltmchn.addSymbol(1, "magenta");
        sltmchn.addSymbol(1, "yellow");
        
        sltmchn.placeSymbol(1, "magenta");
        sltmchn.lock(1);
        
        sltmchn.placeSymbol(1, "yellow");
        
        String[] currentConfig = sltmchn.configuration();
        assertEquals("magenta", currentConfig[0], "El símbolo no debió cambiar porque la rueda está bloqueada");
    }
    
    @AfterEach
    void tearDown() {
        sltmchn.exit();
    }
}