package slotMachine.test;

import slotMachine.*;


import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
/**
 * Write a description of class WheelLeftyTest here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class WheelLeftyTest
{
    Lefty wheel;
    @BeforeEach
    public void start() {
        wheel = new Lefty();
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
    public void shouldCopy() {
        Wheel wheelToCopy = new Wheel();
        Symbol symbol1 = new Symbol("red");
        Symbol symbol2 = new Symbol("blue");
        Symbol symbol3 = new Symbol("black");
        wheel.addSymbol(symbol1);
        wheel.addSymbol(symbol2);
        wheel.addSymbol(symbol3);
        wheel.getWheelToCopy(wheelToCopy);
        wheel.copy();
        assertArrayEquals(new String[]{}, wheel.symbols());
    }
}