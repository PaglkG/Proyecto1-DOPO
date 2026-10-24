package slotMachine.test;

import slotMachine.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.lang.reflect.Method;

/**
 * The test class SlotMachineC4.
 * 
 * @author pablo andres gualdron lindo
 */
// Esta clase contiene todas las pruebas unitarias para validar el comportamiento de la máquina tragamonedas y sus piezas especiales.
public class SlotMachineC4 {


    /**
     * Tests that a Rebel wheel can be unlocked without throwing an exception.
     */
    // Prueba que la rueda rebelde se pueda desbloquear correctamente sin fallar.
    @Test
    public void testRebelUnlock() {
        Rebel rebel = new Rebel();
        assertDoesNotThrow(() -> {
            rebel.unlock(); // Debe ejecutarse y setear isLocked a false sin fallar
        });
    }

    /**
     * Tests that a Rebel wheel throws an exception when attempting to swap with another wheel.
     */
    // Prueba que la rueda rebelde lance una excepción al intentar intercambiarse con otra.
    @Test
    public void testRebelSwap() {
        Rebel rebel = new Rebel();
        Wheel dummyWheel = new Wheel();
        Exception exception = assertThrows(UnsupportedOperationException.class, () -> {
            rebel.swap(dummyWheel);
        });
        assertEquals("A rebel wheel cannot be swap.", exception.getMessage());
    }

    /**
     * Tests that a Rebel wheel throws an exception when attempting to delete it.
     */
    // Prueba que la rueda rebelde lance una excepción al intentar eliminarse.
    @Test
    public void testRebelValidateDeletion() {
        Rebel rebel = new Rebel();
        Exception exception = assertThrows(UnsupportedOperationException.class, () -> {
            rebel.validateDeletion();
        });
        assertEquals("A rebel wheel cannot be deleted.", exception.getMessage());
    }

    /**
     * Tests that checking if a Rebel wheel is a swap throws an exception.
     */
    // Prueba que la verificación de estado de intercambio en la rueda rebelde lance una excepción.
    @Test
    public void testRebelIsSwap() {
        Rebel rebel = new Rebel();
        Exception exception = assertThrows(UnsupportedOperationException.class, () -> {
            rebel.isSwap();
        });
        assertEquals("A rebel wheel cannot be swap.", exception.getMessage());
    }

    /**
     * Tests that a Lefty wheel always returns true for the canSpin validation.
     */
    // Prueba que la rueda zurda (Lefty) siempre permita girar, retornando verdadero.
    @Test
    public void testLeftyCanSpin() {
        Lefty lefty = new Lefty();
        assertTrue(lefty.canSpin()); // Verifica el retorno true
    }

    /**
     * Tests that a Lefty wheel spins and executes its copy mechanism correctly when a left wheel is provided.
     */
    // Prueba que la rueda zurda ejecute correctamente su mecanismo de copia al girar cuando tiene una rueda a su izquierda.
    @Test
    public void testLeftySpinWithLeftWheel() {
        Lefty lefty = new Lefty();
        Wheel leftWheel = new Wheel();
        assertDoesNotThrow(() -> {
            lefty.spin(leftWheel); // Activa el flujo de copia
        });
    }

    /**
     * Tests that a Lefty wheel spins correctly using the default spin method when no left wheel is provided.
     */
    // Prueba que la rueda zurda gire de forma normal (girando sus propios símbolos) cuando no tiene una rueda a la izquierda.
    @Test
    public void testLeftySpinWithoutLeftWheel() {
        Lefty lefty = new Lefty();
        // Add a symbol so the wheel is not empty when spin() calculates the random bound
        lefty.addSymbol(new Symbol("red"));
        assertDoesNotThrow(() -> {
            lefty.spin(null); // Activa el flujo del super.spin()
        });
    }

    /**
     * Tests the private copy method of the Lefty wheel using reflection.
     */
    // Prueba el método privado 'copy' de la rueda zurda mediante reflexión para asegurar que la clonación de símbolos no arroje errores.
    @Test
    public void testLeftyPrivateCopyMethod() throws Exception {
        Lefty lefty = new Lefty();
        Wheel leftWheel = new Wheel();
        
        // Uso de Reflection para probar el método private "copy" directamente
        Method copyMethod = Lefty.class.getDeclaredMethod("copy", Wheel.class);
        copyMethod.setAccessible(true);
        assertDoesNotThrow(() -> {
            copyMethod.invoke(lefty, leftWheel);
        });
    }

    /**
     * Tests the visibility toggle functionality of the Shy symbol.
     */
    // Prueba que el símbolo tímido (Shy) pueda alternar su estado entre visible e invisible correctamente.
    @Test
    public void testShyVisible() {
        Shy shy = new Shy("black");
        assertDoesNotThrow(() -> {
            shy.visible(); // Toggle 1
            shy.visible(); // Toggle 2
        });
    }

    /**
     * Tests the protected efect method of the Shy symbol using reflection.
     */
    // Prueba el método protegido 'efect' del símbolo tímido usando reflexión para simular que es seleccionado por la máquina.
    @Test
    public void testShyEfectMethod() throws Exception {
        Shy shy = new Shy("black");
        
        // Uso de Reflection para probar el método protected "efect"
        Method efectMethod = Shy.class.getDeclaredMethod("efect");
        efectMethod.setAccessible(true);
        assertDoesNotThrow(() -> {
            efectMethod.invoke(shy);
        });
    }

    /**
     * Tests the overridden changePosition method of the Rotation symbol.
     */
    // Prueba que el símbolo de rotación pueda cambiar y actualizar su posición base en la pantalla sin fallar.
    @Test
    public void testRotationChangePosition() {
        Rotation rotation = new Rotation("pink");
        assertDoesNotThrow(() -> {
            rotation.changePosition(50, 50); // Prueba la sobreescritura de changePosition
        });
    }

    /**
     * Tests the rotating movement logic (up and down) of the Rotation symbol.
     */
    // Prueba la lógica del símbolo de rotación verificando que pueda moverse hacia arriba y volver a su lugar original.
    @Test
    public void testRotationMovementMethods() {
        Rotation rotation = new Rotation("pink");
        rotation.changePosition(100, 100);
        
        assertDoesNotThrow(() -> {
            rotation.rotating(); // Flujo sube (isUp = true)
            rotation.rotation(); // Flujo baja (Llama a rotating, isUp = false)
        });
    }

    /**
     * Tests the protected efect method of the Rotation symbol using reflection.
     */
    // Prueba el método protegido 'efect' del símbolo de rotación simulando su activación por la máquina.
    @Test
    public void testRotationEfectMethod() throws Exception {
        Rotation rotation = new Rotation("pink");
        Method efectMethod = Rotation.class.getDeclaredMethod("efect");
        efectMethod.setAccessible(true);
        assertDoesNotThrow(() -> {
            efectMethod.invoke(rotation);
        });
    }

    /**
     * Tests the size reduction logic of the Ephemeral symbol across multiple iterations.
     */
    // Prueba que el símbolo efímero reduzca su tamaño gradualmente múltiples veces hasta alcanzar su tamaño mínimo sin desbordamientos.
    @Test
    public void testEphemeralReduce() {
        Ephemeral ephemeral = new Ephemeral("green");
        assertDoesNotThrow(() -> {
            // Ciclo para asegurar que el tamaño baja a 2x2 y active los validadores del IF
            for(int i = 0; i < 15; i++) {
                ephemeral.reduce();
            }
        });
    }

    /**
     * Tests the protected efect method of the Ephemeral symbol using reflection.
     */
    // Prueba el método protegido 'efect' del símbolo efímero simulando su activación en un giro de la máquina.
    @Test
    public void testEphemeralEfectMethod() throws Exception {
        Ephemeral ephemeral = new Ephemeral("green");
        Method efectMethod = Ephemeral.class.getDeclaredMethod("efect");
        efectMethod.setAccessible(true);
        assertDoesNotThrow(() -> {
            efectMethod.invoke(ephemeral);
        });
    }

    /**
     * Tests the SlotMachine's overall ability to copy symbols using a Lefty wheel.
     */
    // Prueba general de la máquina tragamonedas para verificar que la rueda zurda efectúa la copia de símbolos y estados correctamente en un giro completo.
    @Test
    public void shouldCopy() {
        SlotMachine machine = new SlotMachine();
        machine.addWheel(1);
        machine.addWheel("Lefty", 2);
        machine.addSymbol("Ephemeral", 1, "blue");
        machine.addSymbol("Ephemeral", 2, "red");
        machine.spin();
        assertArrayEquals(new String[]{"green", "green"}, machine.symbols());
    }

    
}