package slotMachine.test;

import slotMachine.*;


import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;


/**
 * The test class TestWheel.
 *
 * @author  Steveen-Gualdron
 * @version 0.1
 */
public class WheelTest
{
    private Wheel wheel;
    @Before
    public void setUp() {
        wheel = new Wheel();
    }

    @Test
    public void changeColorAndPositionTest(){
        wheel.changeColor("black");
        wheel.changeColor("blue");
        wheel.changeColor("white");
        wheel.changePosition(120, 40);
        assertEquals("white", wheel.getColor());
        assertEquals(120, wheel.getXPosition());
        assertEquals(40, wheel.getYPosition());
    
    }   
    @Test
    public void addSymbols() throws SlotMachineException{
        Symbol triangle1 = new Symbol("black");
        Symbol triangle2 = new Symbol("blue");
        Symbol triangle3 = new Symbol("white");
        wheel.addSymbol(triangle1);
        wheel.addSymbol(triangle2);
        wheel.addSymbol(triangle3);
        assertEquals(triangle1, wheel.selectedSymbol());
        wheel.delSymbol(triangle3);
        
    }
    
    @Test
    public void deleteSymbols() throws SlotMachineException {
        Symbol triangle1 = new Symbol("black");
        Symbol triangle2 = new Symbol("blue");
        Symbol triangle3 = new Symbol("white");
        wheel.addSymbol(triangle1);
        wheel.addSymbol(triangle2);
        wheel.addSymbol(triangle3);
        wheel.delSymbol(triangle1);
        wheel.delSymbol(triangle2);
        assertEquals(triangle3, wheel.selectedSymbol());
    }
}
