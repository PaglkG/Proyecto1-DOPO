package slotMachine;

import org.junit.Test;
import static org.junit.Assert.*;

public class SymbolShyTest {

    @Test
    public void shouldToggleVisibilityWhenSelected() throws SlotMachineException {
        Shy shy = new Shy("green");
        boolean initialVisibility = shy.isVisible();
        
        shy.efect();
        assertNotEquals(initialVisibility, shy.isVisible());
        
        shy.visible();
        assertEquals(initialVisibility, shy.isVisible());
    }

    @Test
    public void shouldToggleShySymbolVisibilityWheneverSelected() {
        SlotMachine machine = new SlotMachine();
        try {
            machine.addWheel(1);
            machine.addSymbol("Shy", 1, "black");
            Symbol selected = wheelAt(machine, 1).getSelectedSymbol();
            assertTrue("The selected symbol should be Shy", selected instanceof Shy);
            assertFalse("Shy begins invisible", selected.isVisible());
            machine.placeSymbol(1, "orange");
            assertTrue("The first selection should make Shy visible", selected.isVisible());
            machine.placeSymbol(1, "orange");
            assertFalse("The next selection should hide Shy", selected.isVisible());

        } finally {
            machine.exit();
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
}