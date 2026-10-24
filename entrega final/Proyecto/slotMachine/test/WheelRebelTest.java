package slotMachine.test;


import slotMachine.*;
import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.Before;
import org.junit.Test;

/**
 * Write a description of class WheelRebelTest here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class WheelRebelTest
{
    Wheel wheel;
    @Before
    public void start() {
        wheel = new Rebel();
    }
    
    @Test
    public void shouldStart() {
        Symbol symbol1 = new Symbol("red");
        Symbol symbol2 = new Symbol("blue");
        Symbol symbol3 = new Symbol("black");
        wheel.addSymbol(symbol1);
        wheel.addSymbol(symbol2);
        wheel.addSymbol(symbol3);
        assertArrayEquals(new String[]{"red", "blue", "black"}, wheel.symbols());
    }
    
    @Test
    public void shouldNotBlock() {
        wheel.lock();
        assertFalse(wheel.isLocked());
    }
    
    @Test
    public void shouldNotExchange() {
        Wheel wheelCange = new Rebel();
        Exception miError = assertThrows(Exception.class, () -> {
            wheel.swap(wheelCange);
        });
    }
    
    @Test
    public void shouldNotDelete() {
        SlotMachine machine = new SlotMachine();
        machine.addWheel("Rebel",1);
        assertThrows(UnsupportedOperationException.class, () -> {
            machine.delWheel(1);
        });
    }
}
