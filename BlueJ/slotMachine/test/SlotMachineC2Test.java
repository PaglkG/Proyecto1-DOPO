package slotMachine.test;

import slotMachine.*;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * The test class SlotMachineC2Test.
 *
 * @author  Steveen-Gualdron
 * @version 0.2
 */
public class SlotMachineC2Test {
    
    private SlotMachine sltmchn;
    
    @BeforeEach
    void setUp() {
        // La máquina inicia en modo invisible por defecto para las pruebas de unidad[cite: 5].
        sltmchn = new SlotMachine();
    }
    
    @Test
    public void shouldSwapTwoWheels() {
        for (int i = 1; i <= 4; i++) {
            sltmchn.addWheel(i);
        }
        // Asignamos colores únicos para rastrear su posición tras el intercambio
        sltmchn.addSymbol(1, "red");
        sltmchn.addSymbol(2, "blue");
        sltmchn.addSymbol(3, "green");
        sltmchn.addSymbol(4, "yellow");
        
        sltmchn.swap(4, 2);
        sltmchn.swap(1, 3);
        
        String[] expectedConfig = {"green", "yellow", "red", "blue"};
        assertArrayEquals(expectedConfig, sltmchn.configuration(), "La configuración debe reflejar el intercambio de las ruedas");
        assertTrue(sltmchn.ok(), "El intercambio debió ser exitoso");
    }
    
    @Test
    public void shouldLockSpecificWheel() {
        int NUMBER_WHEELS_TO_ADD = 5;
        for (int i = 1; i <= NUMBER_WHEELS_TO_ADD; i++) {
            sltmchn.addWheel(i);
            sltmchn.addSymbol(i, "magenta");
            sltmchn.addSymbol(i, "blue");
        }
        
        String[] initialConfig = sltmchn.configuration();
        
        sltmchn.lock(2);
        sltmchn.lock(4);
        
        sltmchn.spin(2);
        assertFalse(sltmchn.ok(), "Girar una rueda bloqueada debe retornar ok() = false");
        
        sltmchn.spin(4);
        assertFalse(sltmchn.ok(), "Girar una rueda bloqueada debe retornar ok() = false");
        
        String[] currentConfig = sltmchn.configuration();
        assertEquals(initialConfig[1], currentConfig[1], "El símbolo visible de la rueda 2 no debió cambiar");
        assertEquals(initialConfig[3], currentConfig[3], "El símbolo visible de la rueda 4 no debió cambiar");
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
        assertTrue(sltmchn.ok(), "La rueda 2 debe poder girar tras ser desbloqueada");
        
        sltmchn.spin(4);
        assertTrue(sltmchn.ok(), "La rueda 4 debe poder girar tras ser desbloqueada");
    }
    
    @Test
    public void shouldntDeleteWheelWhenIsLocked() {
        int NUMBER_WHEELS_TO_ADD = 5;
        for (int i = 1; i <= NUMBER_WHEELS_TO_ADD; i++) {
            sltmchn.addWheel(i);
        }
        
        sltmchn.lock(5);
        sltmchn.delWheel(5);
        
        assertFalse(sltmchn.ok(), "No se debe permitir eliminar una rueda bloqueada");
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
        assertFalse(sltmchn.ok(), "No se debe poder intercambiar si ambas ruedas están bloqueadas");
        
        sltmchn.swap(1, 5);
        assertTrue(sltmchn.ok(), "Se debe poder intercambiar ruedas no bloqueadas");
        
        sltmchn.swap(2, 5);
        assertFalse(sltmchn.ok(), "No se debe poder intercambiar si al menos una rueda está bloqueada");
    }
    
    @Test 
    public void shouldntSpinWhenWheelIsLocked() {
        int NUMBER_WHEELS_TO_ADD = 5;
        for (int i = 1; i <= NUMBER_WHEELS_TO_ADD; i++) {
            sltmchn.addWheel(i);
            sltmchn.addSymbol(i, "magenta");
            sltmchn.addSymbol(i, "blue");
        }
        
        sltmchn.lock(5);
        sltmchn.spin(); // El giro global falla si al menos una rueda no puede girar
        assertFalse(sltmchn.ok(), "spin() global debe fallar si alguna rueda está bloqueada");
        
        sltmchn.unlock(5);
        sltmchn.spin(); 
        assertTrue(sltmchn.ok(), "spin() global debe funcionar si todas están desbloqueadas");
        
        sltmchn.lock(1);
        sltmchn.lock(3);
        
        sltmchn.spin(1); 
        assertFalse(sltmchn.ok());
        
        sltmchn.spin(2);
        assertTrue(sltmchn.ok());
        
        sltmchn.spin(3);
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
        
        // Acción de girar las ruedas con la firma spin(String[] setSymbols)
        sltmchn.spin(specificConfiguration);
        
        assertArrayEquals(specificConfiguration, sltmchn.configuration(), "La configuración resultante no coincide con la esperada");
        assertTrue(sltmchn.ok());
    }
    
    @AfterEach
    void tearDown() {
        sltmchn.exit();
    }
}