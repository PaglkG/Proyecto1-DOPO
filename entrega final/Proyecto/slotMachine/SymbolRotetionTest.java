package slotMachine;

import org.junit.Test;
import static org.junit.Assert.*;

public class SymbolRotetionTest {

    @Test
    public void shouldMoveUpAndDownOnAction() throws SlotMachineException {
        Rotation rotation = new Rotation("blue");
        
        rotation.efect();
        assertNotNull(rotation.getSymbolShape());
        
        rotation.rotating();
        assertNotNull(rotation.getSymbolShape());
    }
}
