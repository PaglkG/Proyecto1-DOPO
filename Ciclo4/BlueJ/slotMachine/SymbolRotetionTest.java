package slotMachine;

import org.junit.Test;
import static org.junit.Assert.*;
import slotMachine.Rotation;

public class SymbolRotetionTest {

    @Test
    public void shouldMoveUpAndDownOnAction() {
        Rotation rotation = new Rotation("blue");
        
        rotation.efect();
        assertNotNull(rotation.getSymbolShape());
        
        rotation.rotating();
        assertNotNull(rotation.getSymbolShape());
    }
}
