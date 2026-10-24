package slotMachine;

import org.junit.Test;
import static org.junit.Assert.*;


public class SymbolShyTest {

    @Test
    public void shouldToggleVisibilityWhenSelected() {
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
            // 1. Crea la máquina y agrega una rueda en la posición 1
            machine.addWheel(1);
            
            // 2. Agrega el símbolo especial 'Shy' de color negro usando el método que recibe el tipo
            machine.addSymbol("Shy", 1, "black");
            
            // 3. Captura la referencia del símbolo que acaba de nacer
            Symbol selected = wheelAt(machine, 1).getSelectedSymbol();
            
            // 4. Verifica que la instancia sea correcta y que su estado inicial sea INVISIBLE
            assertTrue("The selected symbol should be Shy", selected instanceof Shy);
            assertFalse("Shy begins invisible", selected.isVisible());

            // 5. Primera selección: el efecto debe volver la figura VISIBLE
            machine.placeSymbol(1, "black");
            assertTrue("The first selection should make Shy visible", selected.isVisible());

            // 6. Segunda selección: el efecto debe volver la figura INVISIBLE nuevamente
            machine.placeSymbol(1, "black");
            assertFalse("The next selection should hide Shy", selected.isVisible());
            
        } finally {
            // 7. Cierra la ventana gráfica y limpia la memoria al terminar el test
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
