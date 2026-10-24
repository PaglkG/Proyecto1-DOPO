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
}
