package slotMachine;
import shapes.Rectangle;
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
    private TreeMap<Integer, Wheel> wheels; 
    private boolean isVisible;
    private Rectangle body;

    /** Constructor, nyadic method class, of SlotMachine. */
    public SlotMachine() {
        body = new Rectangle(10,10,270,270,"pink");
        isOk = true;
        wheels = new TreeMap<>();
        isVisible = false;
    }

    public void addWheel(int pos) {
        if (wheels.containsKey(pos)) {
            isOk = false; // Falla si la rueda ya existe en esa posición
            return;
        }

        Wheel wheel = new Wheel();
        wheels.put(pos,wheel);
        if (wheels.isEmpty()) {
            wheel.changePositionX(20);
            wheel.changePositionY(50);
        } else {
            Integer posMin = wheels.lowerKey(pos);
            if (posMin == null) {
                posMin = pos;
                wheel.changePositionX(20);
                wheel.changePositionY(50);
            } else {
                wheel.changePositionX(wheels.get(posMin).getPositionX() + 50);
                wheel.changePositionY(50);
            }
            NavigableMap<Integer, Wheel> subMap = wheels.tailMap(posMin, false);
            order(subMap);
        }
        
        if (isVisible) {
            wheel.makeVisible();
        }
        isOk = true; // Acción exitosa
    }
    
    private void order( NavigableMap<Integer, Wheel> subMap ) {
        for (Map.Entry<Integer, slotMachine.Wheel> wheelNext : subMap.entrySet()) {
            Integer posMin = wheels.lowerKey(wheelNext.getKey());
            if (posMin != null) {
                wheelNext.getValue().changePositionX(wheels.get(posMin).getPositionX() + 50);
            } else {
                wheelNext.getValue().changePositionX(50); 
            }
        }
    }
    
    public void delWheel(int pos) {
        Wheel wheelToDelete = wheels.get(pos);
        if (wheelToDelete != null) {
            wheelToDelete.makeInvisible();
            NavigableMap<Integer, Wheel> subMap = wheels.tailMap(pos, false);
            wheels.remove(pos);
            order(subMap);
            isOk = true; // Se borró con éxito
        } else {
            isOk = false; // Falla porque no existe la rueda
        }
    }

    public void addSymbol(int pos, String color) {
        Wheel wheelToAddSymbol = wheels.get(pos);
        if (wheelToAddSymbol != null) {
            Symbol symbolToAdd = new Symbol(color);
            wheelToAddSymbol.addSymbol(symbolToAdd);
            isOk = true;
        } else {
            isOk = false; // Falla porque no existe la rueda
        }
    }

    public void delSymbol(String symbol) {
        if (wheels.isEmpty()) {
            isOk = false;
            return;
        }
        for (Wheel wheel : wheels.values()) {
            wheel.delSymbol(symbol);
        }
        isOk = true;
    }

    public void placeSymbol(int wheel, String symbol) {
        Wheel wheelToPlaceSymbol = wheels.get(wheel);
        if (wheelToPlaceSymbol != null) {
             wheelToPlaceSymbol.addSymbol(symbol);   
             isOk = true;
        } else {
             isOk = false; // Falla porque la rueda específica no existe
        }
    }

    public void spin(int wheel) {
        Wheel wheelToSpin = wheels.get(wheel);
        if (wheelToSpin != null) {
            wheelToSpin.spin();
            isOk = true;
        } else {
            isOk = false;
        }
    }

    public void spin() {
        if (wheels.isEmpty()) {
            isOk = false;
        } else {
            for (Wheel wheel : wheels.values()) {
                wheel.spin();
            }
            isOk = true;
        }
    }

    public String[] symbols() {
        ArrayList<String> colorSymbols = new ArrayList<>();
        for (Wheel wheel : wheels.values()) {
            for (String color : wheel.symbols()){
                colorSymbols.add(color);
            }
        }
        isOk = true;
        return colorSymbols.toArray(new String[0]);
    }

    public int distinctSymbols() {
        Set<String> colorSymbols = new HashSet<>();
        for (Wheel wheel : wheels.values()) {
            for (String color : wheel.symbols()){
                colorSymbols.add(color);
            }
        }
        isOk = true;
        return colorSymbols.size();
    }

    public String[] configuration() {
        ArrayList<String> colorSymbols = new ArrayList<>();
        for (Wheel wheel : wheels.values()) {
            if (wheel.getSelectedSymbol() != null) {
                colorSymbols.add(wheel.getSelectedSymbol().getColor());
            }
        }
        isOk = true;
        return colorSymbols.toArray(new String[0]);
    }

    public boolean isJackpot() {
        if (wheels.isEmpty()) {
            isOk = false;
            return false;
        }
        
        ArrayList<String> colorSymbols = new ArrayList<>();
        for (Wheel wheel : wheels.values()) {
            if (wheel.getSelectedSymbol() != null) {
                colorSymbols.add(wheel.getSelectedSymbol().getColor());
            }
        }
        
        if (colorSymbols.isEmpty()) {
            isOk = false;
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

    public void makeVisible() {
        isVisible = true;
        body.makeVisible();
        for (Wheel wheel : wheels.values()) {
            wheel.makeVisible();
        }
        isOk = true;
    }

    public void makeInvisible() {
        isVisible = false;
        for (Wheel wheel : wheels.values()) {
            wheel.makeInvisible();
        }
        body.makeInvisible();
        isOk = true;
    }

    public void exit() {
        if (wheels != null) {
            wheels.clear();
        }
        Canvas.getCanvas().close();
        isOk = true;
    }
    
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

    public void setWheels(TreeMap<Integer, Wheel> wheels) {
        this.wheels = wheels;
    }
    
    public void swap(int wheel1, int wheel2) {
        Wheel findedWheel1 = wheels.get(wheel1);
        Wheel findedWheel2 = wheels.get(wheel2);
        
        if (findedWheel1 != null && findedWheel2 != null) {
            boolean canSwapedWheels = !findedWheel1.isLocked() && !findedWheel2.isLocked();
            if (canSwapedWheels) {
                findedWheel1.swap(findedWheel2);
                wheels.put(wheel1, findedWheel2);
                wheels.put(wheel2, findedWheel1);
                isOk = true;
            } else {
                isOk = false; // Falla porque alguna está bloqueada
            }
        } else {
            isOk = false; // Falla porque alguna de las dos ruedas no existe
        }
    }
    
    public void lock(int wheel) {
        Wheel wheelToLock = wheels.get(wheel);
        if (wheelToLock != null) {
            wheelToLock.lock();
            isOk = true;
        } else {
            isOk = false;
        }
    }
    
    public void unlock(int wheel) {
        Wheel wheelToUnlock = wheels.get(wheel);
        if (wheelToUnlock != null) {
            wheelToUnlock.unlock();
            isOk = true;
        } else {
            isOk = false;
        }
    }
    
    public void frameFlickering() {
        if (!isVisible) {
            isVisible = true;
        }   
        for (Wheel wheel : wheels.values()) {
            wheel.frameFlickering();
        }
        isOk = true;
    }
    
    public void spin(int wheel, int steps) {
        // Falta implementar tu lógica aquí, pero el isOk iría así:
        Wheel wheelToSpin = wheels.get(wheel);
        if (wheelToSpin != null && !wheelToSpin.isLocked()) {
            // Lógica de los steps
            isOk = true;
        } else {
            isOk = false;
        }
    }
    
    private Map<String, Integer> getAllSymbolsAtSlotMachine() {
        Map<String, Integer> allSymbols = new HashMap<>();
        for (Wheel wheel : wheels.values()) {
            wheel.getInformationSymbols(allSymbols);
        }
        return allSymbols;
    }
    
    private void errorMessage(String messageError) {
        if (isVisible) {
            JOptionPane.showMessageDialog( 
            null, 
            messageError,  
            "Invalid Action",  
            JOptionPane.ERROR_MESSAGE
            );
        }
    }
    
    private void organicePositionWheels() {
        // Implementación futura
    }
}