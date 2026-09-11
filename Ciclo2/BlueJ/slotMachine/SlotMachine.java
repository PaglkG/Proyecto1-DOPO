package slotMachine;

import shapes.Canvas;

import java.util.List;
import java.util.*;
import javax.swing.JOptionPane;

/**
 * This is slot machine game, this is a variant of I problem - SlotMachine ICPC competition
 *
 * @author Steveen-Gualdron
 * @version 0.1
 */
public class SlotMachine {
    private boolean isOk;
    private Map<Integer, Wheel> wheels; //Key represents the position of wheel at the slotmachine
    private boolean isVisible;

    /**Cronstructor, nyadic method class, of SlotMachine.
     */
    public SlotMachine() {
        isOk = true;
        wheels = new TreeMap<>();
        isVisible = false;
    }

    /**
     * Add a wheel to this slot machine, place the roulette wheel on the left or right side.
     * @param pos pos is the position of wheel that is added to this object.
     */
    public void addWheel(int pos) {
        Wheel newWheelToAdd = new Wheel();
        wheels.put(pos, newWheelToAdd);
        newWheelToAdd.setPositionWheel(pos);
        newWheelToAdd.moveHorizontal(pos);
        if (isVisible) {
            newWheelToAdd.makeVisible();
        }
    }

    /**To remove a wheel, pass its left or right position.
     * If a wheel with a wheel to its right is removed, all wheels on the right move one position to the left
     * @param pos pos is the position of wheel
     */
    public void delWheel(int pos) {
        Wheel wheelToDelete = wheels.get(pos);
        boolean isWheelToDeleteLocked = wheelToDelete.isLocked();
        if (wheelToDelete != null && !isWheelToDeleteLocked) {
            wheels.remove(pos);
            if (isVisible) wheelToDelete.makeInvisible();
        }
        isOk = !isWheelToDeleteLocked;
        isOk();
        //Aqui va la funcionalidad de que se modifican las wheels, por ahora lo básico
        
    }

    /**The symbol object is created at a specific position and color.
     * @param pos pos is the position of wheel going to add symbol.
     * @param color color is the color of symbol that going to be created.
     */
    public void addSymbol(int pos, String color) {
        Wheel wheelToAddSymbol = wheels.get(pos);
        Symbol symbolToAdd = new Symbol(color);
        wheelToAddSymbol.addSymbol(symbolToAdd);
    }

    /** The symbol; on each wheel is removed, object is deleted.
     * @param symbol symbol is the type of symbol that going to be deleted.
     */
    public void delSymbol(String symbol) {
        for (Wheel wheel : wheels.values()) {
            wheel.delSymbol(symbol);
        }
    }

    /** The symbol is added to each wheel; shape and wheel number are requested.
     * @param wheel wheel is the number (integer) of wheel that will add the symbol. 
     * @param symbol symbol is the type of symbol that will be added at the specific number wheel.
     */
    public void placeSymbol(int wheel, String symbol) {
        Wheel wheelToPlaceSymbol = wheels.get(wheel);
        wheelToPlaceSymbol.addSymbol(symbol);
    }

    /**Moves a specific number of wheel.
     * @param wheel wheel indicates the number (integer) of wheel that going to be moved.
     */
    public void spin(int wheel) {
        Wheel wheelToSpin = wheels.get(wheel);
        boolean isWheelToSpinLocked = wheelToSpin.isLocked();
        if (wheelToSpin != null && !isWheelToSpinLocked) {
            wheelToSpin.spin();
        }
        isOk = !isWheelToSpinLocked;
        isOk();
    }

    /**Moves each of the wheels.
     */
    public void spin() {
        boolean isWheelToSpinLocked;
        for (Wheel wheel : wheels.values()) {
            isWheelToSpinLocked = wheel.isLocked();
            if (!isWheelToSpinLocked) {
                wheel.spin();
            }
            isOk = !isWheelToSpinLocked;
            isOk();
        }
    }

    /**Displays all existing symbol colors in order.
     * @return A string array with exiting symbols colors of this slot machine.
     */
    public String[] symbols() {
        int sizeWheels = wheels.size(), totalSizeColors = 0;
        ArrayList<String[]> colorSymbolWheels = new ArrayList<>();
        for (Wheel currentWheel : wheels.values()) {
            colorSymbolWheels.add(currentWheel.getColorSymbols());
            totalSizeColors += currentWheel.getSizeColors();
        }
        int currentSizeColor, indexColorSymbols = 0;
        String[] colorSymbols = new String[totalSizeColors];
        for (String[] colorWheel : colorSymbolWheels) {
            currentSizeColor = colorWheel.length;
            for (int j = 0; j < currentSizeColor; j++) {
                colorSymbols[indexColorSymbols] = colorWheel[j];
                indexColorSymbols++;
            }
        }
        return colorSymbols;
    }

    /**Displays the number of distinct colors among the symbols on the wheel that are flipped.
     * @return Number of distinct colors of the flipped symbols.
     */
    public int distinctSymbols() {
        int countDistincSymbols = 0;
        Map<String, Integer> infoAllSymbols = getAllSymbolsAtSlotMachine();
        for (Integer numTimesColorRepeat : infoAllSymbols.values()) {
            if (numTimesColorRepeat.equals(1)) {
                countDistincSymbols++;
            }
        }
        return countDistincSymbols;
    }

    /**Gives all colors selected by the wheels from left to right.
     * @return return an array of string with the symbols selected at the wheels.
     */
    public String[] configuration() {
        int totalSizeColors = 0;
        boolean isWheelVisible;
        ArrayList<String[]> colorSymbolWheels = new ArrayList<>();
        Wheel currentWheel = null; 
        for (Wheel wheel : wheels.values()) {
            isWheelVisible = wheel.isVisible();
            if (wheel != null && isWheelVisible) {
                colorSymbolWheels.add(wheel.getColorSymbolsConfiguration());
                totalSizeColors += wheel.getSizeColors();
            }
        }
        int currentSizeColor, indexColorSymbols = 0;
        String[] configuration = new String[totalSizeColors];
        for (String[] colorWheel : colorSymbolWheels) {
            for (String color : colorWheel) {
                if (color != null) {
                    configuration[indexColorSymbols] = color;
                    indexColorSymbols++;
                }
            }
        }
        return configuration;
    }

    /**Tells whether all shapes selected by the wheels are identical.
     * @return true if all shapes selected by the wheels are identical,
     * false otherwise
     */
    public boolean isJackpot() {
        boolean isSymbolIdentical;
        int indexNextWheel, sizeWheels = wheels.size();
        Wheel wheelToCompare, wheelNext;
        for (int  i = 0; i < sizeWheels-1; i++) {
            indexNextWheel = i+1;
            wheelToCompare = wheels.get(i);
            wheelNext = wheels.get(indexNextWheel);
            isSymbolIdentical = (wheelToCompare.getSelectedSymbol()).equals(wheelNext.getSelectedSymbol());
            if (!isSymbolIdentical) return false;
        }
        return true;
    }

    /**Makes the slot machine visible.
     */
    public void makeVisible() {
        if (!isVisible) isVisible = true;
        for (Wheel wheel : wheels.values()) {
            wheel.makeVisible();
        }
    }

    /**Makes the slot machine invisible.
     */
    public void makeInvisible() {
        if (isVisible) isVisible = false;
        for (Wheel wheel : wheels.values()) {
            wheel.makeInvisible();
        }
    }

    /**Deletes all objects and close the windows.
     */
    public void exit() {
        if (wheels != null) {
            wheels.clear();
        }
        Canvas.getCanvas().close();
    }
    
    /**Indicates whether the last operation was successful.
     * @return True if the operation was successful,
     * False otherwise.
     */
    public boolean isOk() {
        if (!isOk) {
            errorMessage("Esa acción no se puede realizar");
        }
        return isOk;
    }

    public void setOk(boolean isOk) {
        this.isOk = isOk;
    }

    public Map<Integer, Wheel> getWheels() {
        return wheels;
    }

    public void setWheels(Map<Integer, Wheel> wheels) {
        this.wheels = wheels;
    }
    
    /**Swap two specific wheel of position 
     * @param wheel1 wheel1 is the number of first wheel at the slotmachine that will be swaped by second wheel.
     * @param wheel2 wheel2 is the nunmber of second wheel at the slotmachine that will be swaped by first wheel.
     */
    public void swap(int wheel1, int wheel2) {
        Wheel findedWheel1 = wheels.get(wheel1);
        Wheel findedWheel2 = wheels.get(wheel2);
        findedWheel1.swap(findedWheel2);
        boolean canSwapedWheels = !findedWheel1.isLocked() && !findedWheel2.isLocked();
        if (canSwapedWheels) {
            wheels.put(wheel1, findedWheel2);
            wheels.put(wheel2, findedWheel1);
        } 
        isOk = canSwapedWheels;
        isOk();
    }
    
    /**This locked a specific wheel to this wheel can't spin
     * @param wheel wheel is an integer that means the number of this slotmachine; That wheel will be locked. 
     */
    public void lock(int wheel) {
        Wheel wheelToLock = wheels.get(wheel);
        wheelToLock.lock();
    }
    
    /**Make a wheel unlock, this able to the wheel spin corectly 
     * @param wheel wheel is an integer that means the number of this slotmachine; That wheel will be locked. 
     */
    public void unlock(int wheel) {
        Wheel wheelToUnlock = wheels.get(wheel);
        wheelToUnlock.unlock();
    }
    
    /**Makes a update visually of SlotMachine
     * 
     */
    public void frameFlickering() {
        if (!isVisible) {
            isVisible = true;
        }    
        for (Wheel wheel : wheels.values()) {
            wheel.frameFlickering();
        }
    }
    
    public void spin(int wheel, int steps) throws InterruptedException {
        Wheel wheelToSpin = wheels.get(wheel);
        if (isVisible) {
            wheelToSpin.spinSlowly(steps);
        } else if (!isVisible) {
            wheelToSpin.spin(steps);
        }
        Map<Integer, Symbol> symbolsWheelToSpin = wheelToSpin.getSymbols();
        boolean canSpin = !symbolsWheelToSpin.isEmpty() && !wheelToSpin.isLocked();
        isOk = canSpin;
        isOk();
    }
    
    /* Obtains information of all symbol of all wheel
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
    
    private void organicePositionWheels() {
        
    }
}
