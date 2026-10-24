package slotMachine;

import org.junit.Test;
import static org.junit.Assert.*;

public class SymbolEphemeralTest {

    @Test
    public void shouldDecreaseSizeOnSpin() {
        Ephemeral ephemeral = new Ephemeral("red");
        
        ephemeral.efect();
        ephemeral.reduce();
        
        assertNotNull(ephemeral.getSymbolShape());
    }

    @Test
    public void shouldStopDecreasingWhenBecomesAPoint() {
        Ephemeral ephemeral = new Ephemeral("red");
        
        for (int i = 0; i < 50; i++) {
            ephemeral.efect();
        }
        
        assertNotNull(ephemeral.getSymbolShape());
    }
}
