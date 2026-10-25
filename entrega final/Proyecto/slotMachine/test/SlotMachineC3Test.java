package slotMachine.test;

import slotMachine.*;

import static org.junit.Assert.*;

import java.beans.Transient;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


/**
 * solution maraton problem
 * @author Pablo Andres Guladron Lindo
 * @version 2.3
 */
public class SlotMachineC3Test {
    
    @Test
    public void shouldSolutionProblem() throws SlotMachineException {
        SlotMachineContest machine = new SlotMachineContest();
        machine.solve(5);
        assertEquals(1, machine.getDistincSymbols());
        machine.solve(30);
        assertEquals(1, machine.getDistincSymbols());    }
     @Test 
     public void shouldNotMaxSteps()  throws SlotMachineException {
        SlotMachineContest machine = new SlotMachineContest();
        int result = machine.solve(50).length;
        assertTrue(10000 > result);
     }
}