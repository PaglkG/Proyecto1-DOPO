package slotMachine;

import shapes.Rectangle;

import java.util.List;
import java.util.Random;
import java.util.TreeMap;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;
import java.util.Objects;

/**
 * Wheel class, this is the wheel that will be installed on slot machine.
 *
 * @author Gualdron-Villagran
 * @version 1.0
 */
public class Wheel {
    private int positionWheel;
    private Rectangle wheelShape; 
    private boolean isStoped;
    private boolean isLocked;
    private Random random;
    private Symbol selectedSymbol;
    private TreeMap<Integer,Symbol> symbols;

    /**Constructor class of wheel, niladic method class.
     */
    public Wheel() {
        wheelShape = new Rectangle();
        wheelShape.changeSize(100,30);
        wheelShape.changeColor("grey");
        wheelShape.makeInvisible();
        isLocked = false;
        random = new Random();
        symbols = new TreeMap<>();
    }
    
    /**To set the wheel color.
     * @param newColor newColor is the color that will be set on this wheel.
     */
    public void changeColor(String newColor) {
        wheelShape.changeColor(newColor);
    }

    /**Sets a new X position to this wheel.
     * @param newPosX newPosX that going to set like x position of this wheel. 
     */
    public void changePositionX(int newPosX) {
        wheelShape.setXPosition(newPosX);
        if (isVisible()) {
            wheelShape.makeInvisible();
            wheelShape.makeVisible();
        }
    }

    /**Sets a new Y position to this wheel.
     * @param newPosY newPosY that going to set like y position of this wheel. 
     */
    public void changePositionY(int newPosY) {
        wheelShape.setYPosition(newPosY);
        if (isVisible()) {
            wheelShape.makeInvisible();
            wheelShape.makeVisible();
        }
    }
    
    /**Add a specific symbol with its color.
     * @param color color is the color of symbol to add. ----------------------
     */
    public void addSymbol(String color) {
        int sizeSymbols = symbols.size();
        Symbol symbolToAdd = new Symbol(color);
        addSymbol(symbolToAdd);
    }

    /** Add a specific symbol with its color.
     * @param symbol symbol is the symbol to add to the wheel. ----------------------
     */
    public void addSymbol(Symbol symbol) {
        boolean isSymbolsEmpty = symbols.isEmpty();
        if (isSymbolsEmpty) {
            selectedSymbol = symbol;
        }
        symbol.setPositionAtTheWheel(symbols.size()+1);
        positionSymbol(symbol);
        symbols.put(symbol.getPositionAtTheWheel(), symbol);
        if (isVisible()) {
            if (symbol == selectedSymbol) {
                symbol.makeVisible();
            } else {
                symbol.makeInvisible();
            }
        }
    }

    /**Remove a specific symbol with its color.
     * @param symbol symbol is the symbol that will be removed. ---------------
     */
    public void delSymbol(Symbol triangle) {
        if (!isLocked) {
            symbols.remove(triangle.getPositionAtTheWheel());
            triangle.makeInvisible();
            boolean isSymbolsEmpty = this.symbols.isEmpty();
            if (!isSymbolsEmpty) { 
                spin();
            }
        }
    }
    
    /**Remove a specific symbol with its color.
     * @param color color is the color of symbol that will be removed of symbols. ---------------
     */
    public void delSymbol(String color) {
        String currentColorSymbol = null; 
        Set<Symbol> symbolsV = new HashSet<>(symbols.values());
        if (!isLocked) {
            for (Symbol symbol : symbolsV) {
                currentColorSymbol = symbol.getColor();
                if (currentColorSymbol.equals(color)) {
                    symbol.makeInvisible();
                    symbols.values().remove(symbol); // Elimina de los simbolos el color encontrado
                    if (symbol == selectedSymbol) {
                        selectedSymbol = null;    // Si llega a estar adelante se elimina
                    }
                }
            }
            boolean isSymbolsEmpty = symbols.isEmpty(), isSelectedSymbolNull = selectedSymbol == null;
            if (isSymbolsEmpty && isSelectedSymbolNull) {
                spin(); // Si se permite que gire otra vez la ruleta hagalo
            }
        }
        
    }
    
    /**Gives the symbol that was selected when spinning the wheel.
     * @return Returns the symbol that was selected when spinning the wheel. ----------
     */
    public Symbol selecSymbol() {
        return selectedSymbol;
    }
    
    /**Choose a symbol randomly.
     */
    public void spin() {
        boolean isSymbolsEmpty = this.symbols.isEmpty();
        if (!isSymbolsEmpty && !isLocked) {
            List<Integer> keys = new ArrayList<>(this.symbols.keySet());
            int randomIndex = random.nextInt(keys.size());
            int randomKey = keys.get(randomIndex);
            selectedSymbol = symbols.get(randomKey);
            if (isVisible()) {
                selectedSymbol.frameFlickering();
            }
        }
        return;
    }
    
    /**Makes this wheel invisible.
     */
    public void makeInvisible() {
        wheelShape.makeInvisible();
        for (Symbol symbol : symbols.values()) {
            symbol.makeInvisible();
        }
    }
    
    /** Gets the color of 
     *
     */
    public String getColor() {
        return wheelShape.getColor();
    }

    /**Makes this wheel visible.
     */
    public void makeVisible() {
        if (!isVisible()) {
            wheelShape.makeVisible();    
        }
        boolean isSymbolsEmpty = symbols.isEmpty();
        Set<Symbol> symbolsV = new HashSet<>(symbols.values());
        for (Symbol symbol : symbolsV) {
            if (isSymbolsEmpty) break;
            if (!isVisible()) symbol.makeVisible();
        }
        if (selectedSymbol != null) selectedSymbol.makeVisible();
    }

    /**Displays all existing symbol colors in order.
     * @return A string array with exiting symbols colors of this slot machine.
     */
    public ArrayList<String> symbols() {
        ArrayList<Symbol> list = new ArrayList<>(this.symbols.values());
        ArrayList<String> listString = new ArrayList();
        for (Symbol symbol : list) {
            listString.add(symbol.getColor());
        }
        return listString;
    }

    /**
     * Compare two wheels, if have the same data at their attributes are equals
     */
    public boolean equals(Wheel wheel) {
        boolean hasSamePosition = this.positionWheel == wheel.getPositionWheel();
        boolean hasSameBoolean = isStoped == wheel.isStoped() && isLocked == wheel.isLocked();
        boolean hasSameSelectedSymbol = Objects.equals(selectedSymbol, wheel.getSelectedSymbol());
        boolean hasSameSymbols = symbols.equals(wheel.getSymbols());
        return hasSamePosition && hasSameBoolean && hasSameSelectedSymbol && hasSameSymbols;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Wheel wheel = (Wheel) obj;
        return this.equals(wheel);
    }
    
    public boolean isLocked() {
        return isLocked;
    }

    /**
     * Gets the rectangle object.
     */
    public Rectangle getRectangle() {
        return (Rectangle)wheelShape;
    }

    public void setRectangle(Rectangle rectangle) {
        this.wheelShape = rectangle;
    }

    /**
     * Checks if the wheel is stopped.
     */
    public boolean isStoped() {
        return isStoped;
    }

    public void setStoped(boolean isStoped) {
        this.isStoped = isStoped;
    }

    /**
     * Checks if the element is visible.
     */
    public boolean isVisible() {
        return wheelShape.isVisible();
    }

    public void setVisible(boolean isVisible) {
        wheelShape.setVisible(isVisible);
    }

    public Random getRandom() {
        return random;
    }

    public void setRandom(Random random) {
        this.random = random;
    }

    /**
     * Gets the selected symbol.
     */
    public Symbol getSelectedSymbol() {
        return selectedSymbol;
    }

    public void setSelectedSymbol(Symbol selectedSymbol) {
        this.selectedSymbol = selectedSymbol;
    }

    /**Gets the list of symbols.
     */   
    public TreeMap<Integer,Symbol> getSymbols() {
        return symbols;
    }

    public void setSymbols(TreeMap<Integer, Symbol> symbols) {
        this.symbols = symbols;
    }

    public void setPositionWheel(int positionWheel) {
        this.positionWheel = positionWheel;
    }
    
    public int getPositionWheel() {
        return this.positionWheel;
    }
    
    public int getXPosition() {
        return wheelShape.getXPosition();
    }
    
    public int getYPosition() {
        return wheelShape.getYPosition();
    }
    
    /**Swap two specific wheel of position 
     * @param wheel1 wheel1 is the number of first wheel at the slotmachine that will be swaped by second wheel.
     * @param wheel2 wheel2 is the nunmber of second wheel at the slotmachine that will be swaped by first wheel.
     */
    public void swap(Wheel wheelToSwap) {
        boolean isWheelToSwapLocked = wheelToSwap.isLocked();
        if (!isLocked && !isWheelToSwapLocked) {
            int thisPosition = this.getPositionWheel();
            int otherPosition = wheelToSwap.getPositionWheel();
            this.moveHorizontal(otherPosition);
            wheelToSwap.moveHorizontal(thisPosition);
            this.repositionSymbols();
            wheelToSwap.repositionSymbols();
            if (this.isVisible() && wheelToSwap.isVisible()) {
                this.frameFlickering();
                wheelToSwap.frameFlickering();
            }
            this.setPositionWheel(otherPosition);
            wheelToSwap.setPositionWheel(thisPosition);
        }
    }
    
    
    public void moveHorizontal(int times) {
        int SPACEAMONGWHEEL = 20, LONGITUDEWHEEL = wheelShape.getWidth(); 
        wheelShape.moveHorizontal(0);
        changePositionX((SPACEAMONGWHEEL+LONGITUDEWHEEL)*times);
    }
    
    public int getNumberOfSymbols() {
        return symbols.size();
    }
    
    public String[] getColorSymbols() {
        int sizeSymbols = symbols.size(), indexColorSymbol = 0;
        String[] colorSymbols = new String[sizeSymbols];
        String colorSymbol; 
        for (Symbol currentSymbol : symbols.values()) {
            colorSymbol =  currentSymbol.getColor();
            if (colorSymbol != null) {
                colorSymbols[indexColorSymbol] = colorSymbol;
                indexColorSymbol++;
            }
        }
        return colorSymbols;
    }
    
    public String[] getColorSymbolsConfiguration() {
        int visibleCount = 0;
        for (Symbol s : symbols.values()) {
            if (s.getColor() != null && s.isVisible()) visibleCount++;
        }
        int indexColorSymbol = 0;
        String[] colorSymbols = new String[visibleCount];
        String colorSymbol; 
        for (Symbol currentSymbol : symbols.values()) {
            colorSymbol =  currentSymbol.getColor();
            if (colorSymbol != null && currentSymbol.isVisible()) {
                colorSymbols[indexColorSymbol] = colorSymbol;
                indexColorSymbol++;
            }
        }
        return colorSymbols;
    }
    
    public int getSizeColors() {
        return symbols.size();
    }
    
    public void frameFlickering() {
        if (isVisible()) {
            wheelShape.frameFlickering();
            selectedSymbol.frameFlickering();
        }
    }
    
    public void repositionSymbols() {
        for (Symbol symbol : symbols.values()) {
            positionSymbol(symbol);
        }
    }
    
    /**This locked this wheel to can´t spin it.
     */
    public void lock() {
        isLocked = true;
    }
    
    /**This locked this wheel to can spin it.
     */
    public void unlock(){
        isLocked = false;
    }
    
    /**Gets the color and number that repeat that color 
     * @param info info is a collection with the number that repeats a specific color. 
     */
    public void getInformationSymbols(Map<String, Integer> info) {
        String colorSymbol;
        Integer numTimesColor = 0;
        for (Symbol symbol : symbols.values()) {
            colorSymbol = symbol.getColor();
            if (info.containsKey(colorSymbol)) {
                numTimesColor = info.get(colorSymbol);
                info.put(colorSymbol, numTimesColor+1); // Se le adiciona la vez que se repite
            } else {
                info.put(colorSymbol, 1); // Si es la primera vez se agrega a la info 
            }
        }
    }
    
    public void spinSlowly(int steps) throws InterruptedException { //Funcionalidad ayudada a corregir por Gemini Flash 3.6
        if (symbols.isEmpty() || isLocked) return;
    
        // Determina la posición actual en el TreeMap
        int currentKey = (selectedSymbol != null) ? selectedSymbol.getPositionAtTheWheel() : symbols.firstKey();

        for (int i = 0; i < steps; i++) {
            Symbol current = symbols.get(currentKey);
    
            // Mueve hacia abajo el símbolo actual y lo oculta
            if (isVisible() && current != null) {
                current.moveSlowly(25);
                current.makeInvisible();
            }
    
            // Avanza cíclicamente al siguiente símbolo (1..size)
            currentKey = (currentKey % symbols.size()) + 1;
            selectedSymbol = symbols.get(currentKey);
    
            // Posiciona y visibiliza el nuevo símbolo seleccionado
            positionSymbol(selectedSymbol);
            if (isVisible()) {
                selectedSymbol.makeVisible();
                selectedSymbol.frameFlickering();
            }
    
            Thread.sleep(700); // Pausa visual para la animación
        }
    }
    
    public void spin(int steps) { //Funcionalidad ayudada a corregir por Gemini Flash 3.6
        if (symbols.isEmpty() || isLocked) return;
    
        int currentKey = (selectedSymbol != null) ? selectedSymbol.getPositionAtTheWheel() : symbols.firstKey();
        int newKey = ((currentKey - 1 + steps) % symbols.size()) + 1;
    
        if (isVisible() && selectedSymbol != null) {
            selectedSymbol.makeInvisible();
        }
    
        selectedSymbol = symbols.get(newKey);
        positionSymbol(selectedSymbol);
    
        if (isVisible()) {
            selectedSymbol.makeVisible();
        }
    }
    
    private void positionSymbol(Symbol symbol) {
        int xPositionWheel = wheelShape.getXPosition();
        int heightWheel = wheelShape.getYPosition();
        symbol.changePosition(xPositionWheel + 15, heightWheel / 2 + 45);
    }
}
