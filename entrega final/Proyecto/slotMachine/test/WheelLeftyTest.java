package slotMachine.test;

import slotMachine.*;


import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Write a description of class WheelLeftyTest here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class WheelLeftyTest
{
    
    @Test
    public void shouldCopy() {
        SlotMachine machine = new SlotMachine();
        machine.addWheel(1);
        machine.addWheel("Lefty",2);
        machine.addSymbol(1,"blue");
        machine.spin(2);
        assertArrayEquals(new String[] {"blue", "blue"}, machine.configuration());
        machine.addSymbol(2,"red");
        try {machine.spin(1,3);
        assertArrayEquals(new String[] {"blue", "blue"}, machine.configuration());
        machine.spin();
        assertArrayEquals(new String[] {"blue", "blue"}, machine.configuration());
        }
        catch(Exception e) {};
    }
    
    
    
}