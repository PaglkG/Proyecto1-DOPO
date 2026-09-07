package slotMachine;

import java.util.List;
import java.util.*;

/**
 * This is slot machine game, this is a variant of I problem - SlotMachine ICPC competition
 *
 * @author Steveen-Gualdron
 * @version 0.1
 */
public class SlotMachine {
    private boolean isOk;
    private ArrayList<Wheel> wheels;

    /**Cronstructor, nyadic method class, of SlotMachine.
     */
    public SlotMachine() {
        isOk = true;
        wheels = new ArrayList<>();
    }

    /**
     * Add a wheel to this slot machine, place the roulette wheel on the left or right side.
     * @param pos pos is the position of wheel that is added to this object.
     */
    public void addWheel(int pos) {
        Wheel newWheelToAdd = new Wheel();
        wheels.add(newWheelToAdd);
        newWheelToAdd.setPositionWheel(pos);
        newWheelToAdd.moveHorizontal(pos);
    }

    /**To remove a wheel, pass its left or right position.
     * If a wheel with a wheel to its right is removed, all wheels on the right move one position to the left
     * @param pos pos is the position of wheel
     */
    public void delWheel(int pos) {
        Wheel wheelToDelete = findWheel(pos);
        if (wheelToDelete != null) {
            wheels.remove(wheelToDelete);
            wheelToDelete.makeInvisible();
            
        }
        //Aqui va la funcionalidad de que se modifican las wheels, por ahora lo básico
        
    }

    /**The symbol object is created at a specific position and color.
     * @param pos pos is the position of wheel going to add symbol.
     * @param color color is the color of symbol that going to be created.
     */
    public void addSymbol(int pos, String color) {
        Wheel wheelToAddSymbol = findWheel(pos);
        Symbol symbolToAdd = new Symbol(color);
        wheelToAddSymbol.addSymbol(symbolToAdd);
    }

    /** The symbol; on each wheel is removed, object is deleted.
     * @param symbol symbol is the type of symbol that going to be deleted.
     */
    public void delSymbol(String symbol) {
        for (Wheel wheel : wheels) {
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
        if (wheelToSpin != null) wheelToSpin.spin();
    }

    /**Moves each of the wheels.
     */
    public void spin() {
        for (Wheel wheel : wheels) {
            wheel.spin();
        }
    }

    /**Displays all existing symbol colors in order.
     * @return A string array with exiting symbols colors of this slot machine.
     */
    public String[] symbols() {
        int sizeWheels = wheels.size(), totalSizeColors = 0;
        ArrayList<String[]> colorSymbolWheels = new ArrayList<>();
        Wheel currentWheel = null;        
        for (int i = 0; i < sizeWheels; i++) {
            currentWheel = wheels.get(i);
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
    public int distincSymbols() {
        return 0;
    }

    /**Gives all colors selected by the wheels from left to right.
     * @return return an array of string with the symbols selected at the wheels.
     */
    public String[] configuration() {
        int totalSizeColors = 0;
        boolean isWheelVisible;
        ArrayList<String[]> colorSymbolWheels = new ArrayList<>();
        Wheel currentWheel = null; 
        for (Wheel wheel : wheels) {
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
        return false;
    }

    /**Makes the slot machine visible.
     */
    public void makeVisible() {
        for (Wheel wheel : wheels) {
            wheel.makeVisible();
        }
    }

    /**Makes the slot machine invisible.
     */
    public void makeInvisible() {
        for (Wheel wheel : wheels) {
            wheel.makeInvisible();
        }
    }

    /**Deletes all objects.
     */
    public void exit() {
    }
    
    /**Indicates whether the last operation was successful.
     * @return True if the operation was successful,
     * False otherwise.
     */
    public boolean isOk() {
        return isOk;
    }

    public void setOk(boolean isOk) {
        this.isOk = isOk;
    }

    public ArrayList<Wheel> getWheels() {
        return wheels;
    }

    public void setWheels(ArrayList<Wheel> wheels) {
        this.wheels = wheels;
    }
    
    public Wheel findWheel(int pos) {
        Wheel wheelFinded = null;
        for (Wheel wheel : wheels) {
            if (wheel.getPositionWheel() == pos) {
                wheelFinded = wheel;
                break;
            }
        }
        return wheelFinded;
    }
    
    public void swap(int wheel1, int wheel2) {
        Wheel findedWheel1 = findWheel(wheel1);
        Wheel findedWheel2 = findWheel(wheel2);
        findedWheel1.moveHorizontal(wheel2);
        findedWheel2.moveHorizontal(wheel1);
        findedWheel1.repositionSymbols();   
        findedWheel2.repositionSymbols();
        if (findedWheel1.isVisible() && findedWheel2.isVisible()) {
            findedWheel1.frameFlickering();
            findedWheel2.frameFlickering();
        }
        int initialPosWheel1 = findedWheel1.getPositionWheel(), initialPosWheel2 = findedWheel2.getPositionWheel();
        findedWheel1.setPositionWheel(initialPosWheel2);
        findedWheel2.setPositionWheel(initialPosWheel1);
    }
    
    
    
    private void organicePositionWheels() {
        
    }
}
