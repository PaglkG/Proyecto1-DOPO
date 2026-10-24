package slotMachine;

import shapes.Rectangle;
import shapes.Canvas;
import java.util.List;
import java.util.*;
import javax.swing.JOptionPane;
import java.util.TreeMap;

/**
 * This is slot machine game, this is a variant of I problem - SlotMachine ICPC competition
 *
 * @author Steveen-Gualdron
 * @version 0.1
 */
public class SlotMachine {
    
    private boolean isOk;
    private NavigableMap<Integer, Wheel> wheels;
    private boolean isVisible;
    private Rectangle bodySlotMachine;
    
    /**
     * Constructor, niladic method class, of SlotMachine.
     */
    public SlotMachine() {
        isOk = false;
        wheels = new TreeMap<>();
        isVisible = false;
        bodySlotMachine = new Rectangle(10, 10, 270, 270, "pink");
        isOk = true;
    }
    
    /**
     * Constructor, dyadic method class, of SlotMachine.
     * This constructor generates n wheels and n symbols.
     * @param n the number of wheels and symbols to generate.
     */
    public SlotMachine(int n) {
        isOk = false;
        wheels = new TreeMap<>();
        isVisible = false;
        bodySlotMachine = new Rectangle(10, 10, 270, 273*(n/4), "pink");
        addSlotMachine(n);
        isOk = true;
    }
    
    /**
     * Add a wheel to this slot machine, place the roulette wheel on the left or right side.
     * @param pos pos is the position of wheel that is added to this object.
     */
    public void addWheel(int pos) {
        isOk = false;
        int newPos = adjustPosition(pos, true);
        NavigableMap<Integer, Wheel> cutMapWheel = wheels.tailMap(pos, true);
        List<Integer> keysToShift = new ArrayList<>(cutMapWheel.keySet());
        Collections.reverse(keysToShift);
        for (Integer key : keysToShift) {
            Wheel wheelToMove = wheels.remove(key);
            wheelToMove.setPositionWheel(key + 1);
            wheels.put(key + 1, wheelToMove);
        }
        Wheel newWheelToAdd = new Wheel();
        newWheelToAdd.setPositionWheel(newPos);
        wheels.put(newPos, newWheelToAdd);
        adjustWheels();
        if (isVisible) {
            newWheelToAdd.makeVisible();
        }
        isOk = true;
    }
    
    /**
     * Add a wheel to this slot machine, place the roulette wheel on the left or right side,
     * specify its type, and change its color.
     * @param type type is the specific kind of wheel to add (e.g., "rebel").
     * @param pos pos is the position of wheel that is added to this object.
     */
    public void addWheel(String type, int pos) {
        isOk = false;
        int newPos = adjustPosition(pos, true);
        NavigableMap<Integer, Wheel> tailWheels = wheels.tailMap(newPos, true);
        List<Integer> keysToShift = new ArrayList<>(tailWheels.keySet());
        Collections.reverse(keysToShift);
        for (Integer key : keysToShift) {
            Wheel wheelToMove = wheels.remove(key);
            wheelToMove.setPositionWheel(key + 1);
            wheels.put(key + 1, wheelToMove);
        }
        try {
            String packageName = this.getClass().getPackage().getName();
            Class<?> wheelClass = Class.forName(packageName + "." + type);
            Wheel newWheelToAdd = (Wheel) wheelClass.getDeclaredConstructor().newInstance();
            newWheelToAdd.setPositionWheel(newPos);
            wheels.put(newPos, newWheelToAdd);
            adjustWheels();
            if (isVisible) {
                newWheelToAdd.makeVisible();
            }
            isOk = true;
        } catch (ClassNotFoundException e) {
            System.out.println("Error: The class '" + type + "' does not exist in the project.");
        } catch (Exception e) {
            System.out.println("Error while attempting to create the wheel instance:" + e.getMessage());
        }
    }
    
    /**
     * To remove a wheel, pass its left or right position.
     * If a wheel with a wheel to its right is removed, all wheels on the right move one position to the left.
     * @param pos pos is the position of wheel.
     */
    public void delWheel(int pos) {
        isOk = false;
        boolean isWheelsEmpty = wheels.isEmpty();
        if (isWheelsEmpty) {
            errorMessage("Esa acción no se puede realizar.");
            return;
        }
        pos = adjustPosition(pos, false);
        Wheel wheelToDelete = wheels.get(pos);
        if (wheelToDelete != null) {
            wheelToDelete.validateDeletion();
        }
        boolean isWheelToDeleteLocked = wheelToDelete.isLocked();
        if (isWheelToDeleteLocked) {
            errorMessage("Esa acción no se puede realizar.");
            return;
        }
        wheelToDelete.makeInvisible();
        wheels.remove(pos);
        NavigableMap<Integer, Wheel> cutMapWheel = wheels.tailMap(pos, false);
        List<Integer> keysToShift = new ArrayList<>(cutMapWheel.keySet());
        for (Integer key : keysToShift) {
            Wheel wheelToMove = wheels.remove(key);
            wheelToMove.setPositionWheel(key - 1);
            wheels.put(key - 1, wheelToMove);
        }
        adjustWheels();
        isOk = true;
    }
    
    /**
     * Swap two specific wheel of position.
     * @param pos1 pos1 is the number of first wheel at the slotmachine that will be swapped by second wheel.
     * @param pos2 pos2 is the number of second wheel at the slotmachine that will be swapped by first wheel.
     */
    public void swap(int pos1, int pos2) {
        isOk = false;
        if (!proofInvariant(pos1, false)) return;
        if (!proofInvariant(pos2, false)) return;
        Wheel findedWheel1 = wheels.get(pos1);
        Wheel findedWheel2 = wheels.get(pos2);
        boolean canSwapWheels = !findedWheel1.isLocked() && !findedWheel2.isLocked();
        if (canSwapWheels && findedWheel2.isSwap()) {
            findedWheel1.swap(findedWheel2);
            wheels.put(pos1, findedWheel2);
            wheels.put(pos2, findedWheel1);
            adjustWheels();
            isOk = true;
        } else {
            isOk = false;
        }
    }
    
    /**
     * This locked a specific wheel to this wheel can't spin.
     * @param wheel wheel is an integer that means the number of this slotmachine; That wheel will be locked.
     */
    public void lock(int wheel) {
        isOk = false;
        if (!proofInvariant(wheel, false)) return;
        Wheel wheelToLock = wheels.get(wheel);
        wheelToLock.lock();
        isOk = true;
    }
    
    /**
     * Make a wheel unlock, this able to the wheel spin corectly.
     * @param wheel wheel is an integer that means the number of this slotmachine; That wheel will be unlocked.
     */
    public void unlock(int wheel) {
        isOk = false;
        if (!proofInvariant(wheel, false)) return;
        Wheel wheelToUnlock = wheels.get(wheel);
        wheelToUnlock.unlock();
        isOk = true;
    }
    
    /**
     * The symbol object is created at a specific position and color.
     * @param pos pos is the position of wheel going to add symbol.
     * @param color color is the color of symbol that going to be created.
     */
    public void addSymbol(int pos, String color) {
        isOk = false;
        boolean isAprovedInvariant = proofInvariant(pos, false);
        if (!isAprovedInvariant) return;
        Wheel wheelToAddSymbol = wheels.get(pos);
        Symbol symbolToAdd = new Symbol(color);
        wheelToAddSymbol.addSymbol(symbolToAdd);
        isOk = true;
    }
    
    /**
     * The symbol object is created at a specific position, specifying its type and color.
     * @param type type is the specific kind of symbol to add (e.g., "Wildcard", "Fruit").
     * @param pos pos is the position of wheel going to add symbol.
     * @param color color is the color of symbol that going to be created.
     */
    public void addSymbol(String type, int pos, String color) {
        isOk = false;
        boolean isAprovedInvariant = proofInvariant(pos, false);
        if (!isAprovedInvariant) return;
        Wheel wheelToAddSymbol = wheels.get(pos);
        try {
            String packageName = this.getClass().getPackage().getName();
            Class<?> symbolClass = Class.forName(packageName + "." + type);
            Symbol newSymbolToAdd = (Symbol) symbolClass.getDeclaredConstructor(String.class).newInstance(color);
            wheelToAddSymbol.addSymbol(newSymbolToAdd);
            isOk = true;
        } catch (ClassNotFoundException e) {
            System.out.println("Error: The class '" + type + "' does not exist in the project.");
        } catch (NoSuchMethodException e) {
            System.out.println("Error: The class '" + type + "' does not have a constructor that takes a String (color).");
        } catch (Exception e) {
            System.out.println("Error while attempting to create the symbol instance: " + e.getMessage());
        }
    }
    
    /**
     * The symbol; on each wheel is removed, object is deleted.
     * @param symbol symbol is the type of symbol that going to be deleted.
     */
    public void delSymbol(String symbol) {
        isOk = false;
        for (Wheel wheel : wheels.values()) {
            wheel.delSymbol(symbol);
        }
        isJackpot();
        isOk = true;
    }
    
    /**
     * The symbol is added to each wheel; shape and wheel number are requested.
     * @param wheel wheel is the number (integer) of wheel that will add the symbol.
     * @param symbol symbol is the type of symbol that will be added at the specific number wheel.
     */
    public void placeSymbol(int wheel, String symbol) {
        isOk = false;
        boolean isAprovedInvariant = proofInvariant(wheel, false);
        if (!isAprovedInvariant) return;
        Wheel wheelToPlaceSymbol = wheels.get(wheel);
        wheelToPlaceSymbol.placeSymbol(symbol);
        isJackpot();
        if (isVisible) {
            makeVisible();
        }
        isOk = true;
    }
    
    /**
     * Moves a specific number of wheel.
     * @param wheel wheel indicates the number (integer) of wheel that going to be moved.
     */
    public void spin(int wheel) {
        isOk = false;
        boolean isAprovedInvariant = proofInvariant(wheel, false);
        if (!isAprovedInvariant) {
            isOk = false;
            return;
        }
        int newWheel = adjustPosition(wheel, false);
        Wheel wheelToSpin = wheels.get(wheel);
        boolean canWheelSpin = wheelToSpin.canSpin();
        if (wheelToSpin instanceof Lefty && canWheelSpin) {
            Lefty leftyWheel = (Lefty) wheelToSpin;
            leftyWheel.spin(wheels.get(newWheel - 1));
        } else if (canWheelSpin) {
            wheelToSpin.spin();
        }
        isOk = canWheelSpin;
        ok();
        isJackpot();
        isOk = true;
    }
    
    /**
     * Spin a specific wheel a specific steps.
     * @param wheel wheel is the number of wheel will be to spined.
     * @param steps steps is the number of symbols the specific wheel will be moved.
     * @throws InterruptedException if the thread is interrupted during the spin delay.
     */
    public void spin(int wheel, int steps) throws InterruptedException {
        isOk = false;
        if (!proofInvariant(wheel, false)){
            isOk = false;
            return;
        }
        Wheel wheelToSpin = wheels.get(wheel);
        boolean canSpin = wheelToSpin.canSpin();
        if (wheelToSpin instanceof Lefty && canSpin) {
            Lefty leftyWheel = (Lefty) wheelToSpin;
            leftyWheel.spin(wheels.get(wheel - 1));
        } else if (canSpin && !(wheelToSpin instanceof Lefty)) {
            if (isVisible) {
                wheelToSpin.spinSlowly(steps);
            } else {
                wheelToSpin.spin(steps);
            }
        }
        isJackpot();
        isOk = true;
    }
    
    /**
     * Spin the wheels to place an specific configuration given.
     * @param setSymbols setSymbols is the specific configuration that if it's possible can set, this will be to setted.
     */
    public void spin(String[] setSymbols) {
        isOk = false;
        int lengthArray = setSymbols.length;
        boolean specificInvariant = lengthArray == wheels.size();
        if (!specificInvariant) {
            ok();
            isOk = false;
            return;
        }
        String specificSymbolToPut = null;
        for (int i = 0; i < lengthArray; i++) {
            specificSymbolToPut = setSymbols[i];
            placeSymbol(i+1, specificSymbolToPut);
        }
        isJackpot();
        isOk = true;
    }
    
    /**
     * Moves each of the wheels.
     */
    public void spin() {
        isOk = false;
        boolean allCanSpin = true;
        for (Integer pos : wheels.keySet()) {
            Wheel wheelObj = wheels.get(pos);
            boolean canWheelSpin = wheelObj.canSpin();
            if (canWheelSpin) {
                if (wheelObj instanceof Lefty) {
                    Lefty leftyWheel = (Lefty) wheelObj;
                    leftyWheel.spin(wheels.get(pos - 1));
                } else {
                    wheelObj.spin();
                }
            } else {
                allCanSpin = false;
            }
        }
        isJackpot();
        isOk = allCanSpin;
    }
    
    /**
     * Displays all existing symbol colors in order.
     * @return a string array with the existing symbol colors of this slot machine.
     */
    public String[] symbols() {
        isOk = false;
        List<String> colorSymbols = new ArrayList<>();
        for (Wheel wheel : wheels.values()) {
            String[] symbolsWheel = wheel.symbols();
            for (String color : symbolsWheel) {
                colorSymbols.add(color);
            }
        }
        isOk = true;
        return colorSymbols.toArray(new String[0]);
    }
    
    /**
     * Displays the number of distinct colors among the symbols on the wheel that are flipped.
     * @return Number of distinct colors of the flipped symbols.
     */
    public int distinctSymbols() {
        isOk = false;
        List<String> visibleColors = getColorSymbolWheels();
        Set<String> uniqueVisibleColors = new HashSet<>(visibleColors);
        isOk = true;
        return uniqueVisibleColors.size();
    }
    
    /**
     * Gives all colors selected by the wheels from left to right.
     * @return return an array of string with the symbols selected at the wheels.
     */
    public String[] configuration() {
        isOk = false;
        List<String> colorSymbols = getColorSymbolWheels();
        String[] symbols = colorSymbols.toArray(new String[0]);
        isOk = true;
        return symbols;
    }
    
    /**
     * Tells whether all symbols currently selected by the wheels are identical in color.
     * @return true if all visible symbols have the same color, false otherwise.
     */
    public boolean isJackpot() {
        isOk = false;
        List<String> colorSymbols = getColorSymbolWheels();
        if (colorSymbols.isEmpty()) {
            return false;
        }
        String firstColor = colorSymbols.get(0);
        for (String color : colorSymbols) {
            if (!firstColor.equals(color)) {
                bodySlotMachine.changeColor("pink");
                if (isVisible) {
                    makeVisible();
                }
                isOk = true;
                return false;
            }
        }
        bodySlotMachine.changeColor("orange");
        if (isVisible) {
            makeVisible();
        }
        isOk = true;
        return true;
    }
    
    /**
     * Makes the slot machine visible.
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
    
    /**
     * Makes the slot machine invisible.
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
    
    /**
     * Deletes all objects and close the windows.
     */
    public void exit() {
        if (wheels != null) {
            wheels.clear();
        }
        Canvas.getCanvas().close();
        isOk = true;
    }
    
    /**
     * Indicates whether the last operation was successful.
     * @return True if the operation was successful, False otherwise.
     */
    public boolean ok() {
        if (!isOk && isVisible) {
            errorMessage("Esa acción no se puede realizar");
        }
        return isOk;
    }
    
    /**
     * Gets a wheel from a specific position.
     * @param i the position of the wheel.
     * @return the Wheel object at the specified position.
     */
    protected Wheel wheelsGet(int i) {
        return wheels.get(i);
    }
    
    /**
     * Gets the colors of the currently selected symbols of all wheels.
     * @return a list of colors as strings.
     */
    private List<String> getColorSymbolWheels() {
        ArrayList<String> colorSymbols = new ArrayList<>();
        Symbol selectedSymbolWheel = null;
        boolean existsSelectedSymbol = false;
        String colorSelectedSymbol = null;
        for (Wheel wheel : wheels.values()) {
            selectedSymbolWheel = wheel.getSelectedSymbol();
            existsSelectedSymbol = selectedSymbolWheel != null;
            if (existsSelectedSymbol) {
                colorSelectedSymbol = selectedSymbolWheel.getColor();
                colorSymbols.add(colorSelectedSymbol);
            }
        }
        return colorSymbols;
    }
    
    /**
     * Displays an error message on the screen if the simulator is visible.
     * @param messageError the specific error message to be displayed.
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
    
    /**
     * Validates if a given position is valid within the current state of the slot machine.
     * @param pos the position to validate.
     * @param isAdding true if the operation is adding a wheel, false otherwise.
     * @return true if the invariant proofs, false otherwise.
     */
    private boolean proofInvariant(int pos, boolean isAdding) {
        // Ayudado a perfeccionar con Gemini Pro Avanzado IA
        isOk = false;
        int sizeWheels = wheels.size(), plusSize = isAdding ? 1 : 0;
        boolean wheelExists = wheels.containsKey(pos), isOutBoundPos = pos <= 0 || pos > sizeWheels+plusSize;
        
        // Verifica posiciones negativas o saltos inválidos
        if (isOutBoundPos) {
            errorMessage("Posición inválida o no consecutiva.");
            return false;
        }
        
        boolean ifIsAddingIsExistsPosition = isAdding ? wheelExists : !wheelExists;
        
        // Valida la existencia según la acción (agregar vs modificar)
        if (ifIsAddingIsExistsPosition) {
            String messageToShow = isAdding ? "La posición ya está repetida." : "La rueda no existe.";
            errorMessage(messageToShow);
            return false;
        }
        return true; // Retorna true si pasó todas las pruebas
    }
    
    /**
     * Adjusts the visual positions of all wheels in the slot machine.
     */
    private void adjustWheels() {
        int currentX = 20;
        for (Wheel wheel : wheels.values()) {
            wheel.changePosition(currentX, 50);
            currentX += 50;
        }
    }
    
    /**
     * Adjusts the position index to comply with the limits.
     * @param pos the initial position to evaluate.
     * @param isAdding indicates if the operation is adding a new element.
     * @return the adjusted position within valid bounds.
     */
    private int adjustPosition(int pos, boolean isAdding) {
        // Ayudado a hacer por Gemini IA
        int max = wheels.size() + (isAdding ? 1 : 0);
        if (max == 0) return 1;
        
        if (pos < 1) {
            return 1;
        } else if (pos > max) {
            return max;
        }
        return pos;
    }
    
    //metodo con ayuda de geminis
    private static final String[] COLORS_HEX = {
        "#FF0000", "#8B0000", "#DC143C", "#FF6347", "#FFC0CB", 
        "#FF1493", "#C71585", "#FF4500", "#FF8C00", "#FFA500", 
        "#FFD700", "#FFFF00", "#F0E68C", "#B8860B", "#008000", 
        "#00FF00", "#32CD32", "#228B22", "#8FBC8F", "#00FA9A", 
        "#6B8E23", "#0000FF", "#000080", "#1E90FF", "#4682B4", 
        "#00BFFF", "#00FFFF", "#48D1CC", "#5F9EA0", "#800080", 
        "#4B0082", "#8A2BE2", "#DA70D6", "#EE82EE", "#FF00FF", 
        "#9370DB", "#A52A2A", "#8B4513", "#D2691E", "#F4A460", 
        "#DEB887", "#FFDEAD", "#000000", "#2F4F4F", "#696969", 
        "#808080", "#A9A9A9", "#C0C0C0", "#DCDCDC", "#F5F5DC"
    };
    
    /**
     * Adds n wheels and symbols based on the predefined hex colors array to this slot machine.
     * @param n the number of wheels and symbols to generate.
     */
    private void addSlotMachine(int n) {
        Canvas.getCanvas().resizeAndRefresh(70*n, 283);
        for (int i = 0; i < n; i++) {
            int requestedPos = i;
            addWheel(requestedPos);
            int correctedPos = adjustPosition(requestedPos, true);
            for (int u = 0; u < n; u++) {
                addSymbol(correctedPos, COLORS_HEX[u]);
            }
        }
        spin();
    }
    
    
}