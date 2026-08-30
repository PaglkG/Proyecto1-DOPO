package BlueJ;



import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * The test class TestWheel.
 *
 * @author  Steveen-Gualdron
 * @version 0.1
 */
public class WheelTest
{
    private Wheel wheel;
    @BeforeEach
    void setUp() {
        wheel = new Wheel();
    }
//deberia crear un rectangulo y cambiar el color junto a sus posiciones 
    @Test
    public void changeColorAndPositionTest(){
        wheel.changeColor("black");
        wheel.changeColor("blue");
        wheel.changeColor("white");
        wheel.changePositionX(30);
        wheel.changePositionX(300);
        wheel.changePositionX(120);
        wheel.changePositionY(200);
        wheel.changePositionY(400);
        wheel.changePositionY(40);
        assertEquals("white", wheel.getColor(), "The color of the wheel should be 'white'");
        assertEquals(120, wheel.getPositionX(), "The X position should be 120");
        assertEquals(40, wheel.getPositionY(), "The Y position should be 40");
    
    }   
    @Test
    public void addSymbols(){
        Symbol triangle1 = new Symbol("black",0);
        Symbol triangle2 = new Symbol("blue",6);
        Symbol triangle3 = new Symbol("white",3);
        wheel.addSymbol(triangle1);
        wheel.addSymbol(triangle2);
        wheel.addSymbol(triangle3);
        assertEquals(triangle1, wheel.selecSymbol());
        wheel.delSymbol(triangle3);
        
    }
    
    @Test
    public void deleteSymbols() {
        Symbol triangle1 = new Symbol("black",0);
        Symbol triangle2 = new Symbol("blue",6);
        Symbol triangle3 = new Symbol("white",3);
        wheel.delSymbol(triangle1);
        wheel.delSymbol(triangle2);
        assertEquals(triangle3, wheel.selecSymbol());
    }
}