package slotMachine;
import shapes.Rectangle;
import shapes.Canvas;
import shapes.Rectangle;

import java.util.List;
import java.util.*;
import javax.swing.JOptionPane;
import java.util.Map;
import java.util.TreeMap;

/**
 * This is slot machine game, this is a variant of I problem - SlotMachine ICPC competition
 *
 * @author Steveen-Gualdron
 * @version 0.1
 */
public class SlotMachine {
    private boolean isOk;
    private NavigableMap<Integer, Wheel> wheels; //Key represents the position of wheel at the slotmachine
    private boolean isVisible;
    private Rectangle bodySlotMachine;

    /**Cronstructor, nyadic method class, of SlotMachine.
     */
    public SlotMachine() {
        isOk = false;
        wheels = new TreeMap<>();
        isVisible = false;
        bodySlotMachine = new Rectangle(10, 10, 270, 270, "pink");
        isOk = true;
    }

    /**
     * Add a wheel to this slot machine, place the roulette wheel on the left or right side.
     * @param pos pos is the position of wheel that is added to this object.
     */
    public void addWheel(int pos) { //Ayudado a perfeccionar con Gemini Pro Avanzado IA
        pos = adjustPosition(pos, true); 
        // Desplazar las llaves del mapa hacia la derecha para hacer espacio
        List<Integer> keysToShift = new ArrayList<>(wheels.tailMap(pos, true).keySet());
        Collections.reverse(keysToShift); // Importante: recorrer de mayor a menor para no sobrescribir
        for (Integer key : keysToShift) {
            Wheel w = wheels.remove(key);
            w.setPositionWheel(key + 1);
            wheels.put(key + 1, w);
        }
        Wheel newWheelToAdd = new Wheel();
        newWheelToAdd.setPositionWheel(pos);
        wheels.put(pos, newWheelToAdd);
        
        adjustWheels(); 
        if (isVisible) {
            newWheelToAdd.makeVisible();
        }
        isOk = true;
    }
    
    /**To remove a wheel, pass its left or right position.
     * If a wheel with a wheel to its right is removed, all wheels on the right move one position to the left
     * @param pos pos is the position of wheel
     */
    public void delWheel(int pos) {
        if (wheels.isEmpty()) {
            isOk = false;
            errorMessage("Esa acción no se puede realizar");
            return;
        }
        // Auto-ajustar la posición sin lanzar error
        pos = adjustPosition(pos, false); 
        
        Wheel wheelToDelete = wheels.get(pos);
        if (wheelToDelete.isLocked()) {
            isOk = false;
            errorMessage("Esa acción no se puede realizar");
            return;
        }
        
        wheelToDelete.makeInvisible();
        wheels.remove(pos);
        
        // Desplazar las llaves del mapa hacia la izquierda
        List<Integer> keysToShift = new ArrayList<>(wheels.tailMap(pos, false).keySet());
        for (Integer key : keysToShift) {
            Wheel w = wheels.remove(key);
            w.setPositionWheel(key - 1); // Actualiza internamente el atributo
            wheels.put(key - 1, w);      // Lo guarda en la nueva posición
        }
        adjustWheels(); 
        isOk = true;
    }

    /**The symbol object is created at a specific position and color.
     * @param pos pos is the position of wheel going to add symbol.
     * @param color color is the color of symbol that going to be created.
     */
    public void addSymbol(int pos, String color) {
        if (!proofInvariant(pos, false)) return;
        Wheel wheelToAddSymbol = wheels.get(pos);
        if (wheelToAddSymbol != null) {
            Symbol symbolToAdd = new Symbol(color);
            wheelToAddSymbol.addSymbol(symbolToAdd);
            isOk = true;
        } else {
            isOk = false; // Falla porque no existe la rueda
            ok();
        }
    }

    /** The symbol; on each wheel is removed, object is deleted.
     * @param symbol symbol is the type of symbol that going to be deleted.
     */
    public void delSymbol(String symbol) {
        isOk = false;
        for (Wheel wheel : wheels.values()) {
            wheel.delSymbol(symbol);
        }
        isOk = true;
    }

    /** The symbol is added to each wheel; shape and wheel number are requested.
     * @param wheel wheel is the number (integer) of wheel that will add the symbol. 
     * @param symbol symbol is the type of symbol that will be added at the specific number wheel.
     */
    public void placeSymbol(int wheel, String symbol) {
        if (!proofInvariant(wheel, false)) return;
        Wheel wheelToPlaceSymbol = wheels.get(wheel);
        wheelToPlaceSymbol.addSymbol(symbol);   
        isOk = true;
    }

    /**Moves a specific number of wheel.
     * @param wheel wheel indicates the number (integer) of wheel that going to be moved.
     */
    public void spin(int wheel) {
        if (!proofInvariant(wheel, false)) return;
        Wheel wheelToSpin = wheels.get(wheel);
        boolean isWheelToSpinLocked = wheelToSpin.isLocked();
        if (!isWheelToSpinLocked) {
            wheelToSpin.spin();
        }
        isOk = !isWheelToSpinLocked;
        ok();
    }

    /**Moves each of the wheels.
     */
    public void spin() {
        boolean isWheelToSpinLocked=false;
        for (Wheel wheel : wheels.values()) {
            isWheelToSpinLocked = wheel.isLocked();
            if (!isWheelToSpinLocked) {
                wheel.spin();
            }
            isOk = !isWheelToSpinLocked;
            ok();
            return;
        }
        boolean noExistsSomeWheel = wheels.isEmpty();
        if (noExistsSomeWheel) {
            isOk = false;
        } else {
            for (Wheel wheel : wheels.values()) {
                isWheelToSpinLocked = wheel.isLocked();
                if (!isWheelToSpinLocked) {
                    wheel.spin();
                }
            }
            isOk = !isWheelToSpinLocked;
        }
        ok();
    }

    /**Displays all existing symbol colors in order.
     * @return A string array with exiting symbols colors of this slot machine.
     */
    public String[] symbols() {
        isOk = false;
        ArrayList<String> colorSymbols = new ArrayList<>();        
        for (Wheel wheel : wheels.values()) {
            for (String color : wheel.symbols()){
                colorSymbols.add(color);
            }
        }
        String[] symbols = colorSymbols.toArray(new String[0]);
        isOk = true;
        return symbols;
    }

    /**Displays the number of distinct colors among the symbols on the wheel that are flipped.
     * @return Number of distinct colors of the flipped symbols.
     */
    public int distinctSymbols() {
        isOk = false;
        int countDistincSymbols = 0;
        Map<String, Integer> infoAllSymbols = getAllSymbolsAtSlotMachine();
        for (Integer numTimesColorRepeat : infoAllSymbols.values()) {
            if (numTimesColorRepeat.equals(1)) {
                countDistincSymbols++;
            }
        }
        Set<String> colorSymbols = new HashSet<>();
        for (Wheel wheel : wheels.values()) {
            for (String color : wheel.symbols()){
                colorSymbols.add(color);
            }
        }
        isOk = true;
        return countDistincSymbols;
    }

    /**Gives all colors selected by the wheels from left to right.
     * @return return an array of string with the symbols selected at the wheels.
     */
    public String[] configuration() {
        isOk = false;
        ArrayList<String> colorSymbols = new ArrayList<>();
        for (Wheel wheel : wheels.values()) {
            if (wheel.getSelectedSymbol() != null) {
                colorSymbols.add(wheel.getSelectedSymbol().getColor());
            }
        }
        String[] symbols = colorSymbols.toArray(new String[0]);
        isOk = true;
        return symbols;
    }

    /**Tells whether all shapes selected by the wheels are identical.
     * @return true if all shapes selected by the wheels are identical,
     * false otherwise
     */
    public boolean isJackpot() {
        isOk = false;
        ArrayList<String> colorSymbols = new ArrayList<>();
        for (Wheel wheel : wheels.values()) {
            if (wheel.getSelectedSymbol() != null) {
                colorSymbols.add(wheel.getSelectedSymbol().getColor());
            }
        }
        boolean existsColorsSymbolsOrWheels = colorSymbols.isEmpty() || wheels.isEmpty();
        if (existsColorsSymbolsOrWheels) {
            isOk = false;
            ok();
            return false;
        }
        String first = colorSymbols.get(0);
        for (int i = 1; i < colorSymbols.size(); i++) {
            if (!first.equals(colorSymbols.get(i))) {
                isOk = true;
                return false;
            }
        }
        isOk = true;
        return true;
    }

    /**Makes the slot machine visible.
     */
    public void makeVisible() {
        isOk = false;
        isVisible = true;
        bodySlotMachine.makeVisible();
        for (Wheel wheel : wheels.values()) {
            wheel.makeVisible();
        }
        isOk = true;
    }

    /**Makes the slot machine invisible.
     */
    public void makeInvisible() {
        isOk = false;
        isVisible = false;
        for (Wheel wheel : wheels.values()) {
            wheel.makeInvisible();
        }
        bodySlotMachine.makeInvisible();
        isOk = true;
    }

    /**Deletes all objects and close the windows.
     */
    public void exit() {
        if (wheels != null) {
            wheels.clear();
        }
        Canvas.getCanvas().close();
        isOk = true;
    }
    
    /**Indicates whether the last operation was successful.
     * @return True if the operation was successful,
     * False otherwise.
     */
    public boolean ok() {
        if (!isOk && isVisible) {
            errorMessage("Esa acción no se puede realizar");
        }
        return isOk;
    }
    
    /**Swap two specific wheel of position 
     * @param wheel1 wheel1 is the number of first wheel at the slotmachine that will be swaped by second wheel.
     * @param wheel2 wheel2 is the nunmber of second wheel at the slotmachine that will be swaped by first wheel.
     */
    public void swap(int wheel1, int wheel2) {
        if (!proofInvariant(wheel1, false)) return;
        if (!proofInvariant(wheel2, false)) return;
        Wheel findedWheel1 = wheels.get(wheel1);
        Wheel findedWheel2 = wheels.get(wheel2);
        boolean canSwapWheels = !findedWheel1.isLocked() && !findedWheel2.isLocked();
        if (canSwapWheels) {
            findedWheel1.swap(findedWheel2);
            wheels.put(wheel1, findedWheel2);
            wheels.put(wheel2, findedWheel1);
            adjustWheels();
            isOk = true;
        } else {
            isOk = false; // Falla porque alguna o las dos estám bloqueada
        }
    }
    
    /**This locked a specific wheel to this wheel can't spin
     * @param wheel wheel is an integer that means the number of this slotmachine; That wheel will be locked. 
     */
    public void lock(int wheel) {
        if (!proofInvariant(wheel, false)) return;
        Wheel wheelToLock = wheels.get(wheel);
        wheelToLock.lock();
        isOk = false;
    }
    
    /**Make a wheel unlock, this able to the wheel spin corectly 
     * @param wheel wheel is an integer that means the number of this slotmachine; That wheel will be locked. 
     */
    public void unlock(int wheel) {
       if (!proofInvariant(wheel, false)) return;
        Wheel wheelToUnlock = wheels.get(wheel);
        wheelToUnlock.unlock();
        isOk = true;
    }
    
    /**Makes a update visually of SlotMachine
     * 
     */
    public void frameFlickering() {
        isOk = false;
        isVisible = true;
        bodySlotMachine.frameFlickering();
        for (Wheel wheel : wheels.values()) {
            wheel.frameFlickering();
        }
        isOk = true;
    }
    
    /**Spin a specific wheel a specific steps
     * @param wheel wheel is the number of wheel will be to spined,
     * @param steps steps is the number of symbols the specific wheel will be moved.
     */
    public void spin(int wheel, int steps) throws InterruptedException {
        if (!proofInvariant(wheel, false)) return;
        Wheel wheelToSpin = wheels.get(wheel);
        if (isVisible) {
            wheelToSpin.spinSlowly(steps);
        } else if (!isVisible) {
            wheelToSpin.spin(steps);
        }
        boolean canSpin = wheelToSpin.canSpin();
        isOk = canSpin;
        ok();
        isOk = true;
    }
    
    public void spin(String[] setSymbols) {
        
    }
    
    public Map<Integer, Wheel> getWheels() {
        return wheels;
    }
    
    /* Obtains information of all symbol of all wheel (Key: Color, Value: time color repeats).
     * @return A map with the information of all symbol of SlotMachine 
     */
    private Map<String, Integer> getAllSymbolsAtSlotMachine() {
        Map<String, Integer> allSymbols = new HashMap<>();
        for (Wheel wheel : wheels.values()) {
            wheel.getInformationSymbols(allSymbols);
        }
        return allSymbols;
    }
    
    /*
     * Error message this going to apperear at the screen
     * This only apper if simulator is visible
     */
    private void errorMessage(String messageError) {
        if (isVisible) {
            JOptionPane.showMessageDialog( 
                null, 
                messageError,  // Mensaje de la ventana
                "Invalid Action",  //Mensaje del titulo de la ventana
                JOptionPane.ERROR_MESSAGE
            );
        }
    }
    
    private void order(NavigableMap<Integer, Wheel> subMap ) {
        Wheel nextWheel = null, minWheel;
        Integer posMin = Integer.MAX_VALUE, positionWheelMin = Integer.MAX_VALUE;
        for (Map.Entry<Integer, slotMachine.Wheel> wheelNext : subMap.entrySet()) {
            posMin = wheels.lowerKey(wheelNext.getKey());
            nextWheel = wheelNext.getValue();
            minWheel = wheels.get(posMin);
            positionWheelMin = minWheel.getPositionX();
            if (posMin != null) {
                nextWheel.changePosition(positionWheelMin + 50, 50);
            } else {
                nextWheel.changePosition(20, 50); 
            }
        }
    }
    
    private boolean proofInvariant(int pos, boolean isAdding) { // Ayudado a perfeccionar con Gemini Pro Avanzado IA
        int sizeWheels = wheels.size(), plusSize = isAdding ? 1 : 0;
        boolean wheelExists = wheels.containsKey(pos), isOutBoundPos = pos <= 0 || pos > sizeWheels+plusSize;
        // Verifica posiciones negativas o saltos inválidos
        if (isOutBoundPos) {
            isOk = false;
            errorMessage("Posición inválida o no consecutiva.");
            return false;
        }
        boolean ifIsAddingIsExistsPosition = isAdding ? wheelExists : !wheelExists;
        // Valida la existencia según la acción (agregar vs modificar)
        if (ifIsAddingIsExistsPosition) {
            isOk = false;
            String messageToShow = isAdding ? "La posición ya está repetida." : "La rueda no existe.";
            errorMessage(messageToShow);
            return false;
        }
        return true; // Retorna true si pasó todas las pruebas
    }
    
    private void adjustWheels() {
        int currentX = 20;
        for (Wheel wheel : wheels.values()) {
            wheel.changePosition(currentX, 50);
            currentX += 50;
        }
    }
    
    // Ajusta la posición para cumplir con los límites descritos en el documento
    private int adjustPosition(int pos, boolean isAdding) { // Ayudado a hacer por Gemini IA
        int max = wheels.size() + (isAdding ? 1 : 0);
        if (max == 0) return 1; 
        
        if (pos < 1) {
            return 1;
        } else if (pos > max) {
            return max;
        }
        return pos;
    }
}