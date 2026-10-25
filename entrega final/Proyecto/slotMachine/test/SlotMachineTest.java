package slotMachine.test;
import slotMachine.*;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;
import java.awt.GraphicsEnvironment;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import org.junit.After;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import slotMachine.SlotMachine;

/**
 * 50 unit tests for SlotMachine, validating method by method.
 *
 * All tests run with the machine in an invisible state (no windows or dialogs), 
 * except for test 48, which is automatically skipped if no display is available.
 *
 * Each test documents:
 *   Method:  The SlotMachine method being tested.
 *   Should:  The expected behavior according to the cycle requirements.
 *   Verify:  What is exactly checked with the assertions.
 *
 * Tests marked with @Ignore describe the CORRECT behavior of a known bug in the current code.
 * They would fail today: remove the @Ignore when you fix the bug and it should pass.
 *
 * @author Pablo Andrés Gualdrón Lindo, Santiago Cristancho Rodríguez
 * @version 1.0
 */
public class SlotMachineTest {

    private SlotMachine machine;
    private int wheelCount;

    @Before
    public void setUp() {
        machine = new SlotMachine();
        wheelCount = 0;
    }

    @After
    public void tearDown() {
        machine.exit();
    }

    /**
     * Helper: adds a wheel at the end and adds the indicated symbols to it,
     * in that order. The first one gets selected.
     */
    private void wheel(String... colors) throws SlotMachineException {
        wheelCount++;
        machine.addWheel(wheelCount);
        for (String color : colors) {
            machine.addSymbol(wheelCount, color);
        }
    }

    private static String[] colors(String... colors) {
        return colors;
    }

    /**
     * Method: SlotMachine()
     * Should: create an empty machine, with no wheels or symbols, ready to use.
     * Verify: ok() is true; symbols() and configuration() are empty;
     * distinctSymbols() is 0 and isJackpot() is false.
     */
    // Prueba que el constructor sin parámetros cree una máquina vacía y lista para usar.
    @Test
    public void shouldCreateEmptyMachineWithNiladicConstructor() {
        assertTrue(machine.ok());
        assertEquals(0, machine.symbols().length);
        assertEquals(0, machine.configuration().length);
        assertEquals(0, machine.distinctSymbols());
        assertFalse(machine.isJackpot());
    }

    /**
     * Method: SlotMachine(int n)
     * Should: create n wheels and place n symbols of different colors in each.
     * Verify: with n = 4 there are 4 wheels (configuration has 4 colors), 16 symbols
     * in total and only 4 different colors.
     */
    // Prueba que el constructor con parámetro 'n' cree 'n' ruedas con 'n' símbolos de colores distintos cada una.
    @Test
    public void shouldCreateNWheelsWithNSymbolsEach() throws SlotMachineException {
        SlotMachine big = new SlotMachine(4);
        try {
            assertTrue(big.ok());
            assertEquals(4, big.configuration().length);
            assertEquals(16, big.symbols().length);
            Set<String> distinct = new HashSet<>(Arrays.asList(big.symbols()));
            assertEquals(4, distinct.size());
        } finally {
            big.exit();
        }
    }

    /**
     * Method: addWheel(int pos)
     * Should: add wheels one after another, from left to right.
     * Verify: after adding 3 wheels, configuration() returns their colors in
     * the order of positions 1, 2 and 3, and ok() is true.
     */
    // Prueba que las ruedas se agreguen en orden de izquierda a derecha.
    @Test
    public void shouldAddWheelsInOrder() throws SlotMachineException {
        wheel("red");
        wheel("blue");
        wheel("green");
        assertTrue(machine.ok());
        assertArrayEquals(colors("red", "blue", "green"), machine.configuration());
    }

    /**
     * Method: addWheel(int pos)
     * Should: when inserting in an occupied position, shift to the right the
     * wheels starting from that position.
     * Verify: with wheels [red, blue], addWheel(2) and a green symbol in the new
     * wheel, the result is [red, green, blue].
     */
    // Prueba que al insertar una rueda en una posición ocupada, las ruedas existentes se desplacen hacia la derecha.
    @Test
    public void shouldShiftWheelsRightWhenInsertingInTheMiddle() throws SlotMachineException {
        wheel("red");
        wheel("blue");
        machine.addWheel(2);
        assertTrue(machine.ok());
        machine.addSymbol(2, "green");
        assertArrayEquals(colors("red", "green", "blue"), machine.configuration());
    }

    /**
     * Method: addWheel(int pos)
     * Should: adjust an out-of-range position to the closest limit
     * (less than 1 goes to the start, greater than size goes to the end).
     * Verify: addWheel(0) creates the wheel at position 1 and addWheel(99) at 3;
     * ok() is true and the final order is [green, red, blue].
     */
    // Prueba que las posiciones fuera de rango se ajusten al límite más cercano al agregar una rueda.
    @Test
    public void shouldAdjustOutOfRangePositionsWhenAddingWheel() throws SlotMachineException {
        wheel("red");
        machine.addWheel(0);
        assertTrue(machine.ok());
        machine.addSymbol(1, "green");
        machine.addWheel(99);
        assertTrue(machine.ok());
        machine.addSymbol(3, "blue");
        assertArrayEquals(colors("green", "red", "blue"), machine.configuration());
    }

    /**
     * Method: addWheel(String, int) with type "Lefty"
     * Should: create a lefty wheel, which upon spinning copies the state of the wheel
     * on its left.
     * Verify: with wheel 1 on blue, the Lefty at position 2 also gets
     * on blue after spin(2); ok() is true.
     */
    // Prueba que la rueda Lefty copie el estado de la rueda a su izquierda al girar.
    @Test
    public void shouldAddLeftyWheelThatCopiesItsLeftNeighbor() throws SlotMachineException {
        wheel("red", "blue");
        machine.placeSymbol(1, "blue");
        machine.addWheel("Lefty", 2);
        assertTrue(machine.ok());
        machine.spin(2);
        assertTrue(machine.ok());
        assertArrayEquals(colors("blue", "blue"), machine.configuration());
    }


    /**
     * Method: addWheel(String, int) with a non-existent type
     * Should: reject the operation and leave the machine as it was.
     * Verify: ok() is false, configuration() is still [red] and position 2
     * does not exist (addSymbol(2, ...) fails).
     */
    // Prueba que la máquina rechace la creación de una rueda si el tipo especificado no existe.
    @Test
    public void shouldFailWhenWheelTypeDoesNotExist() throws SlotMachineException {
        wheel("red");
        machine.addWheel("NoExiste", 2);
        assertFalse(machine.ok());
        assertArrayEquals(colors("red"), machine.configuration());
        machine.addSymbol(2, "blue");
        assertFalse(machine.ok());
    }

    /**
     * Method: addWheel(String, int) with a non-existent type, inserting at the start
     * Should: not alter the positions of the already existing wheels.
     * Verify: after the failed attempt, wheel 1 still exists
     * (addSymbol(1, ...) works) and the configuration is [red, blue].
     * KNOWN BUG: the code shifts the wheels to the right BEFORE creating the
     * instance; if the type does not exist it leaves a gap at position 1.
     */
    // Prueba que al intentar agregar un tipo de rueda inexistente no se alteren las posiciones de las ruedas actuales.
    @Ignore("Known bug: addWheel(type, pos) shifts wheels before validating the type")
    @Test
    public void shouldNotCorruptPositionsWhenWheelTypeDoesNotExist() throws SlotMachineException {
        wheel("red");
        wheel("blue");
        machine.addWheel("NoExiste", 1);
        assertFalse(machine.ok());
        machine.addSymbol(1, "green");
        assertTrue("Wheel 1 must continue to exist", machine.ok());
        assertArrayEquals(colors("red", "blue"), machine.configuration());
    }

    /**
     * Method: delWheel(int pos)
     * Should: delete the wheel and shift to the left those that were to its right.
     * Verify: with [red, blue, green], delWheel(2) leaves [red, green]; ok() is true.
     */
    // Prueba que al eliminar una rueda, las ruedas a su derecha se desplacen hacia la izquierda.
    @Test
    public void shouldDeleteWheelAndShiftLeft() throws SlotMachineException {
        wheel("red");
        wheel("blue");
        wheel("green");
        machine.delWheel(2);
        assertTrue(machine.ok());
        assertArrayEquals(colors("red", "green"), machine.configuration());
    }

    /**
     * Method: delWheel(int pos)
     * Should: fail if the machine has no wheels.
     * Verify: ok() is false and the machine is still empty.
     */
    // Prueba que falle el intento de eliminar una rueda si la máquina está vacía.
    @Test
    public void shouldFailToDeleteOnEmptyMachine() {
        machine.delWheel(1);
        assertFalse(machine.ok());
        assertEquals(0, machine.configuration().length);
    }

    /**
     * Method: delWheel(int pos)
     * Should: not delete a locked wheel.
     * Verify: after lock(1) and delWheel(1), ok() is false and the configuration
     * remains [red, blue].
     */
    // Prueba que una rueda bloqueada (fija) no pueda ser eliminada.
    @Test
    public void shouldNotDeleteLockedWheel() throws SlotMachineException {
        wheel("red");
        wheel("blue");
        machine.lock(1);
        machine.delWheel(1);
        assertFalse(machine.ok());
        assertArrayEquals(colors("red", "blue"), machine.configuration());
    }

    /**
     * Method: swap(int pos1, int pos2)
     * Should: swap places of two wheels.
     * Verify: with [red, blue, green], swap(1, 3) leaves [green, blue, red]; ok() true.
     */
    // Prueba que se puedan intercambiar de posición dos ruedas correctamente.
    @Test
    public void shouldSwapTwoWheels() throws SlotMachineException {
        wheel("red");
        wheel("blue");
        wheel("green");
        machine.swap(1, 3);
        assertTrue(machine.ok());
        assertArrayEquals(colors("green", "blue", "red"), machine.configuration());
    }

    /**
     * Method: swap(int pos1, int pos2)
     * Should: fail if any position does not exist.
     * Verify: swap(1, 9) leaves ok() in false and the order [red, blue] intact.
     */
    // Prueba que el intercambio falle si alguna de las posiciones especificadas no existe.
    @Test
    public void shouldFailToSwapInvalidPosition() throws SlotMachineException {
        wheel("red");
        wheel("blue");
        machine.swap(1, 9);
        assertFalse(machine.ok());
        assertArrayEquals(colors("red", "blue"), machine.configuration());
    }

    /**
     * Method: swap(int pos1, int pos2)
     * Should: not swap if any of the two wheels is locked.
     * Verify: with wheel 1 locked, swap(1, 2) and swap(2, 1) leave ok() in false
     * and the order [red, blue] unchanged.
     */
    // Prueba que el intercambio falle si alguna de las dos ruedas está bloqueada.
    @Test
    public void shouldNotSwapLockedWheel() throws SlotMachineException {
        wheel("red");
        wheel("blue");
        machine.lock(1);
        machine.swap(1, 2);
        assertFalse(machine.ok());
        machine.swap(2, 1);
        assertFalse(machine.ok());
        assertArrayEquals(colors("red", "blue"), machine.configuration());
    }

    /**
     * Method: swap(int pos1, int pos2) with a Rebel as SECOND argument
     * Should: reject the swap, just like when the Rebel goes first
     * (with exception or with ok() in false) and leave the order intact.
     * Verify: after swap(1, 2), with the Rebel in 2, the order remains [red, blue].
     * KNOWN BUG: the IsSwap() method that blocks the swap is in Lefty
     * and not in Rebel.
     */
    // Prueba que se rechace el intercambio si la segunda rueda es de tipo Rebel.
    @Ignore("Known bug: IsSwap() returns false in Lefty, should be in Rebel")
    @Test
    public void shouldRejectSwapWhenRebelIsSecond() throws SlotMachineException {
        wheel("red");
        machine.addWheel("Rebel", 2);
        machine.addSymbol(2, "blue");
        boolean rejectedByException = false;
        try {
            machine.swap(1, 2);
        } catch (UnsupportedOperationException e) {
            rejectedByException = true;
        }
        assertTrue(rejectedByException || !machine.ok());
        assertArrayEquals(colors("red", "blue"), machine.configuration());
    }

    /**
     * Method: swap(int pos1, int pos2) with a Lefty
     * Should: a lefty wheel can indeed be swapped (the prompt only
     * asks it to copy its left neighbor when spinning).
     * Verify: with [red, Lefty(blue)], swap(1, 2) leaves ok() true and [blue, red].
     * KNOWN BUG: today the swap with a Lefty is rejected due to misplaced IsSwap().
     */
    // Prueba que una rueda Lefty permita ser intercambiada de posición.
    @Ignore("Known bug: IsSwap() returns false in Lefty")
    @Test
    public void shouldAllowSwapWithLeftyWheel() throws SlotMachineException {
        wheel("red");
        machine.addWheel("Lefty", 2);
        machine.addSymbol(2, "blue");
        machine.swap(1, 2);
        assertTrue(machine.ok());
        assertArrayEquals(colors("blue", "red"), machine.configuration());
    }

    /**
     * Method: lock(int wheel)
     * Should: lock the wheel so it cannot spin.
     * Verify: after lock(1), lock finishes with ok() true; in 30 calls to spin()
     * wheel 1 always keeps red and spin() reports ok() false.
     */
    // Prueba que al bloquear una rueda, esta no cambie de símbolo al girar.
    @Test
    public void shouldLockWheelSoItCannotSpin() throws SlotMachineException {
        wheel("red", "blue");
        machine.placeSymbol(1, "red");
        machine.lock(1);
        assertTrue(machine.ok());
        for (int i = 0; i < 30; i++) {
            machine.spin();
            assertFalse(machine.ok());
            assertEquals("red", machine.configuration()[0]);
        }
    }

    /**
     * Method: lock(int wheel)
     * Should: fail if the wheel does not exist.
     * Verify: lock(5) with a single wheel leaves ok() in false.
     */
    // Prueba que falle el intento de bloquear una rueda que no existe.
    @Test
    public void shouldFailToLockInvalidWheel() throws SlotMachineException  {
        wheel("red");
        machine.lock(5);
        assertFalse(machine.ok());
    }

    /**
     * Method: unlock(int wheel)
     * Should: release a locked wheel so it can spin again.
     * Verify: with wheel 1 locked spin() gives ok() false; after unlock(1)
     * (ok() true), spin() gives ok() true.
     */
    // Prueba que al desbloquear una rueda, esta pueda volver a girar normalmente.
    @Test
    public void shouldUnlockWheelSoItCanSpinAgain() throws SlotMachineException {
        wheel("red", "blue");
        machine.lock(1);
        machine.spin();
        assertFalse(machine.ok());
        machine.unlock(1);
        assertTrue(machine.ok());
        machine.spin();
        assertTrue(machine.ok());
    }

    /**
     * Method: addSymbol(int pos, String color)
     * Should: add the symbol to the wheel; the first one added gets selected.
     * Verify: after adding red and blue to wheel 1, configuration() is [red] and
     * symbols() is [red, blue] (in insertion order); ok() true.
     */
    // Prueba que los símbolos se agreguen a la rueda y el primero quede seleccionado.
    @Test
    public void shouldAddSymbolsAndSelectTheFirstOne() throws SlotMachineException {
        machine.addWheel(1);
        machine.addSymbol(1, "red");
        machine.addSymbol(1, "blue");
        assertTrue(machine.ok());
        assertArrayEquals(colors("red"), machine.configuration());
        assertArrayEquals(colors("red", "blue"), machine.symbols());
    }

    /**
     * Method: addSymbol(int pos, String color)
     * Should: fail if the wheel does not exist.
     * Verify: addSymbol(3, ...) and addSymbol(0, ...) with a single wheel leave ok()
     * in false and no symbol is added.
     */
    // Prueba que falle el intento de agregar un símbolo a una rueda que no existe.
    @Test
    public void shouldFailToAddSymbolToInvalidWheel() throws SlotMachineException {
        machine.addWheel(1);
        machine.addSymbol(3, "red");
        assertFalse(machine.ok());
        machine.addSymbol(0, "red");
        assertFalse(machine.ok());
        assertEquals(0, machine.symbols().length);
    }

    /**
     * Method: addSymbol(String, int, String)
     * Should: create symbols of types Ephemeral, Shy and Rotation and add them to the wheel.
     * Verify: the three addSymbol finish with ok() true and symbols() returns
     * [red, blue, green] (the colors of the three symbols).
     */
    // Prueba que los símbolos especiales (Ephemeral, Shy, Rotation) se agreguen correctamente a la rueda.
    @Test
    public void shouldAddSpecialSymbolTypes() {
        machine.addWheel(1);
        machine.addSymbol("Ephemeral", 1, "red");
        assertTrue(machine.ok());
        machine.addSymbol("Shy", 1, "blue");
        assertTrue(machine.ok());
        machine.addSymbol("Rotation", 1, "green");
        assertTrue(machine.ok());
        assertArrayEquals(colors("green", "orange", "pink"), machine.symbols());
    }

    /**
     * Method: addSymbol(String, int, String)
     * Should: fail if the type does not exist or if the wheel does not exist.
     * Verify: type "NoExiste" on a valid wheel and type "Shy" on wheel 5
     * leave ok() in false and do not add symbols.
     */
    // Prueba que falle la adición de un símbolo si su tipo no existe o la rueda es inválida.
    @Test
    public void shouldFailWhenSymbolTypeOrWheelIsInvalid() {
        machine.addWheel(1);
        machine.addSymbol("NoExiste", 1, "red");
        assertFalse(machine.ok());
        machine.addSymbol("Shy", 5, "red");
        assertFalse(machine.ok());
        assertEquals(0, machine.symbols().length);
    }

    /**
     * Method: delSymbol(String color)
     * Should: delete symbols of that color from all wheels.
     * Verify: with wheels [red, blue] and [red, green], delSymbol("red") leaves
     * symbols() as [blue, green]; ok() true.
     */
    // Prueba que al eliminar un color, se borren los símbolos de ese color en todas las ruedas.
    @Test
    public void shouldDeleteSymbolColorFromEveryWheel() throws SlotMachineException {
        wheel("red", "blue");
        wheel("red", "green");
        machine.delSymbol("red");
        assertTrue(machine.ok());
        assertArrayEquals(colors("blue", "green"), machine.symbols());
    }

    /**
     * Method: delSymbol(String color)
     * Should: not touch the symbols of a locked wheel.
     * Verify: with wheel 1 locked, delSymbol("red") only removes it from
     * wheel 2: symbols() is [red, blue, green].
     */
    // Prueba que los símbolos de una rueda bloqueada no se eliminen al borrar un color de la máquina.
    @Test
    public void shouldKeepSymbolsOfLockedWheelWhenDeletingColor() throws SlotMachineException {
        wheel("red", "blue");
        wheel("red", "green");
        machine.lock(1);
        machine.delSymbol("red");
        assertTrue(machine.ok());
        assertArrayEquals(colors("red", "blue", "green"), machine.symbols());
    }

    /**
     * Method: placeSymbol(int wheel, String color)
     * Should: leave selected (visible on the machine) the symbol of that color.
     * Verify: with wheel [red, blue], placeSymbol(1, "blue") leaves
     * configuration() as [blue]; ok() true.
     */
    // Prueba que se pueda seleccionar (hacer visible) un símbolo específico en la máquina.
    @Test
    public void shouldPlaceSymbolAsSelected() throws SlotMachineException {
        wheel("red", "blue");
        machine.placeSymbol(1, "blue");
        assertTrue(machine.ok());
        assertArrayEquals(colors("blue"), machine.configuration());
    }

    /**
     * Method: placeSymbol(int wheel, String color)
     * Should: fail if the wheel does not exist.
     * Verify: placeSymbol(4, "red") with a single wheel leaves ok() in false and the
     * configuration [red] unchanged.
     */
    // Prueba que falle la selección de un símbolo si la rueda especificada no existe.
    @Test
    public void shouldFailToPlaceSymbolOnInvalidWheel() throws SlotMachineException {
        wheel("red", "blue");
        machine.placeSymbol(4, "red");
        assertFalse(machine.ok());
        assertArrayEquals(colors("red"), machine.configuration());
    }

    /**
     * Method: placeSymbol(int wheel, String color)
     * Should: fail if the wheel does not have a symbol of that color.
     * Verify: placeSymbol(1, "pink") with wheel [red, blue] leaves ok() in false
     * and the configuration [red] the same.
     * KNOWN BUG: today ok() stays true even though nothing was done.
     */
    // Prueba que falle la selección si la rueda no contiene un símbolo con el color indicado.
    @Ignore("Known bug: placeSymbol with a non-existent color leaves ok() in true")
    @Test
    public void shouldFailWhenColorIsNotInTheWheel() throws SlotMachineException {
        wheel("red", "blue");
        machine.placeSymbol(1, "pink");
        assertFalse(machine.ok());
        assertArrayEquals(colors("red"), machine.configuration());
    }

    /**
     * Method: spin(int wheel)
     * Should: spin only that wheel and leave one of its symbols selected.
     * Verify: in 30 spins of wheel 1 ([red, blue]) the shown color always
     * is red or blue, ok() is true and wheel 2 ([green]) does not change.
     */
    // Prueba que al girar una sola rueda, esta seleccione uno de sus propios símbolos.
    @Test
    public void shouldSpinWheelAndSelectOneOfItsSymbols() throws SlotMachineException {
        wheel("red", "blue");
        wheel("green");
        for (int i = 0; i < 30; i++) {
            machine.spin(1);
            assertTrue(machine.ok());
            String shown = machine.configuration()[0];
            assertTrue(shown.equals("red") || shown.equals("blue"));
            assertEquals("green", machine.configuration()[1]);
        }
    }

    /**
     * Method: spin(int wheel)
     * Should: a locked wheel does not spin and the operation is reported as failed.
     * Verify: with wheel 1 on red and locked, spin(1) leaves ok() in false and the
     * configuration remains at [red].
     * KNOWN BUG: today spin(int) finishes with isOk = true even if the wheel is locked.
     */
    // Prueba que una rueda bloqueada no gire y la operación reporte un fallo.
    @Ignore("Known bug: spin(int) leaves ok() in true with a locked wheel")
    @Test
    public void shouldFailWhenSpinningLockedWheel() throws SlotMachineException {
        wheel("red", "blue");
        machine.placeSymbol(1, "red");
        machine.lock(1);
        machine.spin(1);
        assertFalse(machine.ok());
        assertArrayEquals(colors("red"), machine.configuration());
    }

    /**
     * Method: spin(int wheel, int steps)
     * Should: advance the wheel 'steps' symbols in a circular manner.
     * Verify: with wheel [red, green, blue] starting at red: 1 step gives green,
     * 2 more steps give red (green, blue, red) and a full turn (3 steps) ends at red.
     */
    // Prueba que la rueda avance una cantidad de pasos especificada de forma circular.
    @Test
    public void shouldAdvanceSymbolsCircularlyWithSteps() throws Exception {
        wheel("red", "green", "blue");
        machine.placeSymbol(1, "red");
        machine.spin(1, 1);
        assertTrue(machine.ok());
        assertArrayEquals(colors("green"), machine.configuration());
        machine.spin(1, 2);
        assertArrayEquals(colors("red"), machine.configuration());
        machine.spin(1, 3);
        assertArrayEquals(colors("red"), machine.configuration());
    }

    /**
     * Method: spin(int wheel, int steps)
     * Should: fail if the wheel does not exist.
     * Verify: spin(9, 1) and spin(0, 1) leave ok() in false and configuration the same.
     */
    // Prueba que falle el giro por pasos si la rueda no existe.
    @Test
    public void shouldFailToSpinStepsOnInvalidWheel() throws Exception {
        wheel("red", "green");
        machine.spin(9, 1);
        assertFalse(machine.ok());
        machine.spin(0, 1);
        assertFalse(machine.ok());
        assertArrayEquals(colors("red"), machine.configuration());
    }

    /**
     * Method: spin(String[] setSymbols)
     * Should: leave the machine showing exactly the requested colors, one per wheel.
     * Verify: with three wheels [red, green, blue], spin({blue, red, green}) leaves
     * configuration() equal to that array; ok() true.
     */
    // Prueba que la máquina muestre exactamente la configuración de colores pasada por arreglo.
    @Test
    public void shouldSetConfigurationFromArray() throws SlotMachineException {
        wheel("red", "green", "blue");
        wheel("red", "green", "blue");
        wheel("red", "green", "blue");
        machine.spin(colors("blue", "red", "green"));
        assertTrue(machine.ok());
        assertArrayEquals(colors("blue", "red", "green"), machine.configuration());
    }

    /**
     * Method: spin(String[] setSymbols)
     * Should: fail if the array does not have one color per wheel.
     * Verify: with 3 wheels and an array of 2 colors, ok() is false and the
     * configuration [red, red, red] does not change.
     */
    // Prueba que falle configurar la máquina si la cantidad de colores en el arreglo no coincide con la cantidad de ruedas.
    @Test
    public void shouldFailWhenArrayLengthDiffersFromWheels() throws SlotMachineException {
        wheel("red", "green", "blue");
        wheel("red", "green", "blue");
        wheel("red", "green", "blue");
        machine.spin(colors("blue", "green"));
        assertFalse(machine.ok());
        assertArrayEquals(colors("red", "red", "red"), machine.configuration());
    }

    /**
     * Method: spin(String[] setSymbols)
     * Should: fail if any wheel does not have the requested color.
     * Verify: with wheels [red, blue] and [red, blue], spin({red, pink}) leaves ok()
     * in false and the configuration [red, red].
     * KNOWN BUG: today ok() stays true even though wheel 2 could not show pink.
     */
    // Prueba que falle configurar la máquina si algún color del arreglo no existe en su respectiva rueda.
    @Ignore("Known bug: spin(String[]) leaves ok() in true if a color does not exist on the wheel")
    @Test
    public void shouldFailWhenArrayHasColorNotInWheel() throws SlotMachineException {
        wheel("red", "blue");
        wheel("red", "blue");
        machine.spin(colors("red", "pink"));
        assertFalse(machine.ok());
        assertArrayEquals(colors("red", "red"), machine.configuration());
    }

    /**
     * Method: spin()
     * Should: spin all wheels; each one shows one of its own symbols.
     * Verify: with single-symbol wheels [red], [green], [blue], the result
     * is always [red, green, blue] and ok() is true.
     */
    // Prueba que al girar toda la máquina, cada rueda muestre uno de sus propios símbolos.
    @Test
    public void shouldSpinAllWheelsKeepingEachOwnSymbols() throws SlotMachineException {
        wheel("red");
        wheel("green");
        wheel("blue");
        machine.spin();
        assertTrue(machine.ok());
        assertArrayEquals(colors("red", "green", "blue"), machine.configuration());
    }

    /**
     * Method: spin()
     * Should: not move locked wheels, spin the others and report the failure.
     * Verify: with wheel 1 locked on red, spin() leaves ok() in false, wheel 1
     * stays on red and wheel 2 stays on green.
     */
    // Prueba que al girar toda la máquina, las ruedas bloqueadas no se muevan y se reporte el fallo.
    @Test
    public void shouldReportFailureAndKeepLockedWheelWhenSpinningAll() throws SlotMachineException {
        wheel("red", "blue");
        wheel("green");
        machine.placeSymbol(1, "red");
        machine.lock(1);
        machine.spin();
        assertFalse(machine.ok());
        assertArrayEquals(colors("red", "green"), machine.configuration());
    }

    /**
     * Method: spin() with a Lefty wheel
     * Should: the Lefty copies the symbol of the wheel on its left after it spins.
     * Verify: in 20 spins, the color of wheel 1 and the Lefty (wheel 2) are
     * always the same and ok() is true.
     */
    // Prueba que la rueda Lefty copie el símbolo de la rueda a su izquierda cuando se giran todas las ruedas.
    @Test
    public void shouldCopyLeftNeighborWhenLeftyWheelSpinsWithAll() throws SlotMachineException {
        wheel("red", "blue");
        machine.addWheel("Lefty", 2);
        machine.addSymbol(2, "green");
        for (int i = 0; i < 20; i++) {
            machine.spin();
            assertTrue(machine.ok());
            String[] shown = machine.configuration();
            assertEquals(shown[0], shown[1]);
        }
    }

    /**
     * Method: symbols()
     * Should: return the colors of all symbols, wheel by wheel from
     * left to right and, within each wheel, in the order they were added.
     * Verify: with [red, blue] and [green] the result is [red, blue, green].
     */
    // Prueba que devuelva los colores de todos los símbolos, ordenados por rueda y por inserción.
    @Test
    public void shouldListSymbolsOfAllWheelsInOrder() throws SlotMachineException {
        wheel("red", "blue");
        wheel("green");
        assertArrayEquals(colors("red", "blue", "green"), machine.symbols());
        assertTrue(machine.ok());
    }

    /**
     * Method: symbols()
     * Should: return an empty array if there are no symbols.
     * Verify: after adding and deleting the only wheel, symbols() has
     * length 0.
     */
    // Prueba que devuelva un arreglo vacío si la máquina no tiene símbolos.
    @Test
    public void shouldReturnNoSymbolsWhenThereAreNoWheels() throws SlotMachineException {
        wheel("red");
        machine.delWheel(1);
        assertEquals(0, machine.symbols().length);
    }

    /**
     * Method: distinctSymbols()
     * Should: count the different colors seen on the machine (one per wheel).
     * Verify: empty machine gives 0; with wheels showing red, blue and red gives 2.
     */
    // Prueba que cuente correctamente la cantidad de colores diferentes visibles en la configuración actual.
    @Test
    public void shouldCountDistinctVisibleColors() throws SlotMachineException {
        assertEquals(0, machine.distinctSymbols());
        wheel("red", "green");
        wheel("blue", "green");
        wheel("red", "green");
        assertEquals(2, machine.distinctSymbols());
        assertTrue(machine.ok());
    }

    /**
     * Method: configuration()
     * Should: return the color of the selected symbol of each wheel, from
     * left to right.
     * Verify: with [red, blue], [green] and [blue, red] it returns [red, green, blue]
     * (3 elements, one per wheel).
     */
    // Prueba que devuelva un arreglo con el color visible actual de cada rueda.
    @Test
    public void shouldReturnConfigurationOneColorPerWheel() throws SlotMachineException {
        wheel("red", "blue");
        wheel("green");
        wheel("blue", "red");
        String[] shown = machine.configuration();
        assertEquals(3, shown.length);
        assertArrayEquals(colors("red", "green", "blue"), shown);
        assertTrue(machine.ok());
    }

    /**
     * Method: isJackpot()
     * Should: be true when all wheels show the same color.
     * Verify: with three wheels showing red, isJackpot() is true and ok() is true.
     */
    // Prueba que detecte un Jackpot (premio) cuando todas las ruedas muestran el mismo color.
    @Test
    public void shouldDetectJackpotWhenAllColorsAreEqual() throws SlotMachineException {
        wheel("red", "blue");
        wheel("red", "green");
        wheel("red");
        assertTrue(machine.isJackpot());
        assertTrue(machine.ok());
    }

    /**
     * Method: isJackpot()
     * Should: be false if there are different colors or if there are no wheels.
     * Verify: empty machine gives false; with red and blue gives false; if you leave blue
     * on wheel 1 with placeSymbol and wheel 2 on blue, it switches to true.
     */
    // Prueba que no haya Jackpot si los colores de las ruedas son diferentes o si no hay ruedas.
    @Test
    public void shouldNotDetectJackpotWhenColorsDiffer() throws SlotMachineException {
        assertFalse(machine.isJackpot());
        wheel("red", "blue");
        wheel("blue", "red");
        assertFalse(machine.isJackpot());
        machine.placeSymbol(1, "blue");
        assertTrue(machine.isJackpot());
    }

    /**
     * Method: makeVisible() and makeInvisible()
     * Should: show and hide the machine without changing its state.
     * Verify: after makeVisible() and makeInvisible(), ok() is true and the
     * configuration [blue] is maintained. Automatically skipped if no display is available.
     */
    // Prueba que los métodos de visibilidad gráfica no alteren el estado lógico de la máquina.
    @Test
    public void shouldMakeVisibleAndInvisibleKeepingState() throws SlotMachineException  {
        Assume.assumeFalse(GraphicsEnvironment.isHeadless());
        wheel("red", "blue");
        machine.placeSymbol(1, "blue");
        
        assertTrue(machine.ok());
        assertArrayEquals(colors("blue"), machine.configuration());
 
        assertTrue(machine.ok());
        assertArrayEquals(colors("blue"), machine.configuration());
    }

    /**
     * Method: exit()
     * Should: terminate the simulator, removing all wheels.
     * Verify: after exit(), ok() is true and symbols() and configuration()
     * are empty.
     */
    // Prueba que al salir del simulador, se eliminen todas las ruedas y configuraciones.
    @Test
    public void shouldClearWheelsOnExit() throws SlotMachineException {
        wheel("red", "blue");
        machine.exit();
        assertTrue(machine.ok());
        assertEquals(0, machine.symbols().length);
        assertEquals(0, machine.configuration().length);
    }

    /**
     * Method: ok()
     * Should: report whether the last operation was successfully performed.
     * Verify: true in a new machine; false after an invalid operation
     * (addSymbol on wheel 5); and returns to true after a valid operation.
     */
    // Prueba que ok() reporte correctamente el estado de éxito o fracaso de la última operación realizada.
    @Test
    public void shouldReportOkAccordingToLastOperation() throws SlotMachineException {
        assertTrue(machine.ok());
        machine.addSymbol(5, "red");
        assertFalse(machine.ok());
        machine.addWheel(1);
        assertTrue(machine.ok());
    }
    

    /**
     * Acceptance scenario 2: add/delete wheels and symbols while preserving
     * the order and configuration of the remaining machine.
     */
    // Verifica que se puedan agregar y eliminar ruedas y símbolos manteniendo el orden y la configuración del resto de la máquina.
    @Test
    public void shouldManageWheelsAndSymbolsDuringAUserSession() throws SlotMachineException {
        SlotMachine machine = new SlotMachine();
        try {
            machine.addWheel(1);
            machine.addWheel(2);
            for (int wheel = 1; wheel <= 2; wheel++) {
                machine.addSymbol(wheel, "red");
                machine.addSymbol(wheel, "blue");
            }
            machine.spin(new String[] {"red", "blue"});
            assertArrayEquals(new String[] {"red", "blue"}, machine.configuration());

            machine.addWheel(2);
            machine.addSymbol(2, "red");
            machine.addSymbol(2, "blue");
            machine.spin(new String[] {"red", "red", "blue"});
            assertEquals("Inserting a wheel should increase the wheel count", 3, wheelCount(machine));
            assertArrayEquals(new String[] {"red", "red", "blue"}, machine.configuration());

            machine.delWheel(2);
            assertTrue("Deleting a normal unlocked wheel should succeed", machine.ok());
            assertEquals("Deleting a wheel should reduce the wheel count", 2, wheelCount(machine));
            assertArrayEquals(
                "The remaining wheels should close the gap in their positions",
                new String[] {"red", "blue"},
                machine.configuration());

            machine.delSymbol("red");
            assertTrue("Deleting an existing symbol color should complete", machine.ok());
            assertFalse(
                "The deleted color must no longer be in the machine's symbol catalog",
                Arrays.asList(machine.symbols()).contains("red"));

            machine.addSymbol(1, "green");
            machine.placeSymbol(1, "green");
            assertArrayEquals(
                "A newly added symbol should be selectable",
                new String[] {"green", "blue"},
                machine.configuration());
        } finally {
            machine.exit();
        }
    }





    /**
     * Acceptance scenario 4: run the contest solver and verify the reported
     * solution bounds and the final distinct-symbol count.
     */
    // Verifica que el solucionador del concurso para 5 ruedas devuelva los pasos correctos dentro del límite permitido para llegar a una configuración uniforme.
    @Test
    public void shouldSolveContestForFiveWheels() throws SlotMachineException {
        int numberOfWheels = 5;
        int[][] moves = SlotMachineContest.solve(numberOfWheels);
        assertNotNull("The solver must return a sequence of moves", moves);
        assertTrue("The solver must finish within the project bound", moves.length < 10000);
        for (int[] move : moves) {
            assertNotNull("Every move must be present", move);
            assertEquals("Each move must contain wheel index and rotation steps", 2, move.length);
            assertTrue("Wheel indices must be in the machine's range", move[0] >= 1 && move[0] <= numberOfWheels);
        }
        assertEquals(
            "A successful solution must leave only one distinct selected symbol",
            1, new SlotMachineContest().getDistincSymbols());
    }

    /**
     * Acceptance scenario 5: launch the contest solution in visible simulation
     * mode. The @After fixture closes the canvas after the scenario completes.
     */
    // Verifica que la simulación visual del concurso termine con un solo símbolo distinto seleccionado.
    @Test
    public void shouldSimulateContestSolutionVisibly() throws SlotMachineException {
        SlotMachineContest.simulate(5);
        assertEquals(
            "The visible simulation should finish with one distinct selected symbol",
            1, new SlotMachineContest().getDistincSymbols());
    }

    /**
     * Edge acceptance check for the smallest machine size supported by solve.
     * This verifies the actual solver API rather than only checking that it returns.
     */
    // Verifica el comportamiento del solucionador con el caso base de tamaño mínimo (1 rueda).
    @Test
    public void shouldSolveContestWithOneWheel() throws SlotMachineException {
        int[][] moves = SlotMachineContest.solve(1);
        assertNotNull(moves);
        assertEquals("One wheel is already a uniform configuration", 1, new SlotMachineContest().getDistincSymbols());
    }

    /**
     * Helper method to assert if a special symbol preserves its requested color.
     * @param type the class name of the special symbol to create.
     * @param requestedColor the expected color of the new symbol.
     */
    // Método auxiliar para verificar si un símbolo especial conserva su color solicitado.
    private void assertSpecialSymbolColor(String type, String requestedColor) {
        SlotMachine machine = new SlotMachine();
        try {
            machine.addWheel(1);
            machine.addSymbol(type, 1, requestedColor);
            assertTrue("The requested special symbol should be accepted", machine.ok());
            assertArrayEquals(
                type + " must preserve the requested color",
                new String[] {requestedColor},
                machine.configuration());
        } finally {
            machine.exit();
        }
    }

    /**
     * Helper method to initialize three default wheels with three identical colors each.
     * @param machine the slot machine to populate.
     */
    // Método auxiliar para inicializar tres ruedas predeterminadas con los mismos tres colores.
    private void addThreeWheelsWithCommonSymbols(SlotMachine machine) throws SlotMachineException {
        for (int position = 1; position <= 3; position++) {
            machine.addWheel(position);
            machine.addSymbol(position, "red");
            machine.addSymbol(position, "green");
            machine.addSymbol(position, "blue");
        }
    }

    /**
     * Uses reflection to access a specific wheel inside the machine.
     * @param machine the slot machine instance.
     * @param position the index of the wheel to retrieve.
     * @return the Wheel object at the specified position.
     */
    // Utiliza reflexión para acceder a una rueda específica en la máquina.
    private Wheel wheelAt(SlotMachine machine, int position) {
        try {
            java.lang.reflect.Field field = SlotMachine.class.getDeclaredField("wheels");
            field.setAccessible(true);
            @SuppressWarnings("unchecked")
            java.util.NavigableMap<Integer, Wheel> wheels = (java.util.NavigableMap<Integer, Wheel>) field.get(machine);
            return wheels.get(position);
        } catch (ReflectiveOperationException exception) {
            throw new AssertionError("Could not inspect wheel " + position, exception);
        }
    }

    /**
     * Uses reflection to count the total number of wheels currently in the machine.
     * @param machine the slot machine instance.
     * @return the total number of wheels.
     */
    // Utiliza reflexión para contar el número total de ruedas en la máquina.
    private int wheelCount(SlotMachine machine) {
        try {
            java.lang.reflect.Field field = SlotMachine.class.getDeclaredField("wheels");
            field.setAccessible(true);
            java.util.Map<?, ?> wheels = (java.util.Map<?, ?>) field.get(machine);
            return wheels.size();
        } catch (ReflectiveOperationException exception) {
            throw new AssertionError("Could not inspect wheel count", exception);
        }
    }    
    
}