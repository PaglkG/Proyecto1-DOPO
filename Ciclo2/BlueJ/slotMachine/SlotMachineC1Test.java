package slotMachine;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.Map;

/**
 * The test class SlotMachineTest.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class SlotMachineC1Test {
    
    private SlotMachine sltmchn;
    private ArrayList<Wheel> wheels;
    private int numWheels;
    
    @BeforeEach
    void setUp() {
        sltmchn = new SlotMachine();
        wheels = sltmchn.getWheels();
        numWheels = wheels.size();
    }
    
    @Test
    public void shouldCreateSlotMachine() {
        assertTrue(wheels != null); // Comprueba que existe
        sltmchn.addWheel(1);
        TreeMap<Integer, Symbol> symbolsCreated = wheels.get(0).getSymbols();
        assertTrue(symbolsCreated != null); // Se deben crear tambien la existencia de los simbolos
    }
    
    @Test
    public void shouldAddWheel() {
        sltmchn.addWheel(1);
        sltmchn.addWheel(2);
        Wheel firstWheel = wheels.get(0);
        Wheel secondWheel = wheels.get(1);
        int xPosWheelFirst = firstWheel.getXPosition(), xPosWheelSecond = secondWheel.getXPosition(); // Moves 25*2
        assertEquals(50, xPosWheelFirst); // Se verifica que se ubica en la posicion correcta
        assertEquals(100, xPosWheelSecond);
    }
    
    @Test
    public void shouldDelWheel() {
        sltmchn.addWheel(1);
        Wheel wheelWillBeDeleted = wheels.get(0);
        sltmchn.delWheel(1);
        assertFalse(wheels.contains(wheelWillBeDeleted)); // Se verifica su ausencia en la lista
        assertEquals(0, wheels.size());
    }
    
    @Test
    public void shouldGiveSymbols() {
        int NUMBER_WHEELS_TO_ADD = 5;
        for (int i = 0; i < NUMBER_WHEELS_TO_ADD; i++) {
            sltmchn.addWheel(i);
        }
        sltmchn.addSymbol(0, "magenta");
        sltmchn.addSymbol(1, "red");
        sltmchn.addSymbol(2, "yellow");
        sltmchn.addSymbol(3, "blue");
        sltmchn.addSymbol(4, "green");
        String[] proof = sltmchn.symbols();
        String[] result = new String[]{"magenta", "red", "yellow", "blue", "green"};
        for (int i = 0; i < result.length;i++) {
            assertEquals(proof[i], result[i]);
        }
    }
    
    @Test
    public void shouldGiveConfiguration() {
        int NUMBER_WHEELS_TO_ADD = 5;
        for (int i = 0; i < NUMBER_WHEELS_TO_ADD; i++) {
            sltmchn.addWheel(i);
        }
        sltmchn.addSymbol(0, "magenta");
        sltmchn.addSymbol(1, "red");
        sltmchn.addSymbol(2, "yellow");
        sltmchn.addSymbol(3, "blue");
        sltmchn.addSymbol(4, "green");
        ArrayList<Wheel> wheels = sltmchn.getWheels();
        Wheel secondWheel = wheels.get(1);
        Wheel fourthWheel = wheels.get(3);
        
        sltmchn.makeVisible();
        
        Symbol symbolSecWheel = secondWheel.getSymbols().get(1);   //Indice según como se guarden
        Symbol symbolFourthWheel = fourthWheel.getSymbols().get(1); 
        symbolSecWheel.setVisible(false);
        symbolFourthWheel.setVisible(false);
        
        String[] proof = sltmchn.configuration();
        
        String[] result = new String[]{"magenta", "yellow", "green"};
        for (int i = 0; i < result.length;i++) {
            assertEquals(proof[i], result[i]);
        }
    }
    
    @Test
    public void shouldGiveNumberDistingSymbols() {
        int NUMBER_WHEELS_TO_ADD = 5;
        for (int i = 0; i < NUMBER_WHEELS_TO_ADD; i++) {
            sltmchn.addWheel(i);
        }
        sltmchn.addSymbol(0, "magenta");
        sltmchn.addSymbol(1, "red");
        sltmchn.addSymbol(2, "yellow");
        sltmchn.addSymbol(3, "blue");
        sltmchn.addSymbol(4, "green");
        int numDistinctSymbols = sltmchn.distinctSymbols();
        assertEquals(5, numDistinctSymbols);
    }
    
    @Test
    public void shouldGiveOtherNumberDistingSymbols() {
        int NUMBER_WHEELS_TO_ADD = 5;
        for (int i = 0; i < NUMBER_WHEELS_TO_ADD; i++) {
            sltmchn.addWheel(i);
            sltmchn.addSymbol(i, "magenta");
            sltmchn.addSymbol(i, "red");
            sltmchn.addSymbol(i, "yellow");
        }
        sltmchn.addSymbol(1, "write");
        sltmchn.addSymbol(2, "black");
        sltmchn.addSymbol(3, "blue");
        sltmchn.addSymbol(4, "green"); // Solamente colocamos 4 simbolos distintos en distintas ruedas
        
        int numDistinctSymbols = sltmchn.distinctSymbols();
        assertEquals(4, numDistinctSymbols);
    }
    
    @Test
    public void shouldExitTheProgram() {
        int NUMBER_WHEELS_TO_ADD = 5;
        for (int i = 0; i < NUMBER_WHEELS_TO_ADD; i++) {
            sltmchn.addWheel(i);
        }
        sltmchn.exit();
        assertTrue(wheels.isEmpty());
    }
    
    @AfterEach
    void tearDown() {
        wheels.clear();
    }
}
