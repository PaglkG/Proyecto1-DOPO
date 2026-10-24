package slotMachine;

import shapes.Rectangle;
import java.util.NavigableMap;
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
 * @version 15.8.2
 */
public class Wheel {
    
    protected int positionWheel;
    protected Rectangle wheelShape;
    protected boolean isStoped;
    protected boolean isLocked;
    private Random random;
    protected Symbol selectedSymbol;
    protected Integer selectedSymbolInteger;
    protected Map<Integer, Symbol> symbols;
    
    /**
     * Constructor class of wheel, niladic method class.
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
    
    /**
     * Add a specific symbol with its color.
     * @param color color is the color of symbol to add.
     */
    public void addSymbol(String color) {
        Symbol symbolToAdd = new Symbol(color);
        addSymbol(symbolToAdd);
    }
    
    /**
     * Add a specific symbol to the wheel.
     * @param symbol symbol is the symbol to add to the wheel.
     */
    public void addSymbol(Symbol symbol) {
        boolean isSymbolsEmpty = symbols.isEmpty();
        if (isSymbolsEmpty) {
            selectedSymbol = symbol;
            selectedSymbolInteger = symbols.size() + 1;
        }
        symbol.setPositionAtTheWheel(symbols.size()+1);
        positionSymbol(symbol);
        symbols.put(symbol.getPositionAtTheWheel(), symbol);
        
        if (isVisible()) {
            if (symbol == selectedSymbol) {
                symbol.frameFlickering();
            } else {
                symbol.makeInvisible();
            }
        }
    }
    
    /**
     * The symbol is added to the wheel; shape and wheel number are requested.
     * @param symbol symbol is the type of symbol that will be added at the wheel.
     */
    public void placeSymbol(String symbol) {
        Symbol symbolToPlace = findJustColorSymbol(symbol);
        boolean existsSymbol = symbolToPlace != null;
        
        // Se toma ayuda de Gemini IA Pro Avanzado para hacer parte de este método
        // Añadimos !isLocked para respetar las reglas de bloqueo
        if (existsSymbol && !isLocked) {
            // 1. Ocultar el símbolo que está actualmente al frente
            if (isVisible() && selectedSymbol != null) {
                selectedSymbol.makeInvisible();
            }
            
            // 2. Cambiar el puntero al nuevo símbolo
            selectedSymbol = symbolToPlace;
            selectedSymbol.efect();
            selectedSymbolInteger = symbolToPlace.getPositionAtTheWheel();
            
            // 3. Acomodar sus coordenadas X y Y en el centro de la rueda
            positionSymbol(selectedSymbol);
            
            // 4. Mostrar el nuevo símbolo si la máquina está visible
            if (isVisible()) {
                selectedSymbol.frameFlickering(); // Lo repinta para que quede encima del fondo negro
            }
        }
    }
    
    /**
     * Remove a specific symbol.
     * @param triangle symbol that will be removed.
     */
    public void delSymbol(Symbol triangle) {
        if (!isLocked) {
            int positionAtWheelSymbol = triangle.getPositionAtTheWheel();
            symbols.remove(positionAtWheelSymbol);
            triangle.makeInvisible();
            boolean canSpinWheel = canSpin();
            if (canSpinWheel) {
                spin();
            }
        }
    }
    
    /**
     * Remove a specific symbol with its color.
     * @param color color is the color of symbol that will be removed of symbols.
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
            if (!isSymbolsEmpty && isSelectedSymbolNull) {
                spin(); // Si se permite que gire otra vez la ruleta hagalo
            }
        }
    }
    
    /**
     * Choose a symbol randomly.
     */
    public void spin() {
        boolean canSpin = canSpin();
        if (canSpin) {
            List<Integer> keys = new ArrayList<>(this.symbols.keySet());
            int randomIndex = random.nextInt(keys.size());
            int randomKey = keys.get(randomIndex);
            selectedSymbol = symbols.get(randomKey);
            selectedSymbolInteger = randomKey;
            boolean isVisible = isVisible();
            if (isVisible) {
                selectedSymbol.frameFlickering();
            }
        }
        selectedSymbol.efect();
        return;
    }
    
    /**
     * Spin a specific number of steps.
     * @param steps the number of symbols to move.
     */
    public void spin(int steps) {
        // Funcionalidad ayudada a corregir por Gemini Flash 3.6
        if (symbols.isEmpty() || isLocked) return;
        
        NavigableMap<Integer, Symbol> navSymbols = (NavigableMap<Integer, Symbol>) symbols;
        int currentKey = (selectedSymbol != null) ? selectedSymbol.getPositionAtTheWheel() : navSymbols.firstKey();
        int newKey = ((currentKey - 1 + steps) % symbols.size()) + 1;
        
        if (isVisible() && selectedSymbol != null) {
            selectedSymbol.makeInvisible();
        }
        
        selectedSymbol = symbols.get(newKey);
        positionSymbol(selectedSymbol);
        
        if (isVisible()) {
            selectedSymbol.makeVisible();
        }
        selectedSymbol.efect();
    }
    
    /**
     * Spin slowly a specific number of steps.
     * @param steps the number of symbols to move.
     * @throws InterruptedException if the thread is interrupted during animation delay.
     */
    public void spinSlowly(int steps) throws InterruptedException {
        // Funcionalidad ayudada a corregir por Gemini Flash 3.6
        if (symbols.isEmpty() || isLocked) return;
        
        // Determina la posición actual en el TreeMap
        NavigableMap<Integer, Symbol> navSymbols = (NavigableMap<Integer, Symbol>) symbols;
        int currentKey = (selectedSymbol != null) ? selectedSymbol.getPositionAtTheWheel() : navSymbols.firstKey();
        
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
            selectedSymbol.efect();
            // Posiciona y visibiliza el nuevo símbolo seleccionado
            positionSymbol(selectedSymbol);
            if (isVisible()) {
                selectedSymbol.makeVisible();
                selectedSymbol.frameFlickering();
            }
            Thread.sleep(700); // Pausa visual para la animación
        }
        selectedSymbol.efect();
    }
    
    /**
     * Swap this wheel with another wheel.
     * @param wheelToSwap wheelToSwap is the wheel that will be swaped by this wheel.
     */
    public void swap(Wheel wheelToSwap) {
        boolean isWheelToSwapLocked = wheelToSwap.isLocked();
        if (!isLocked && !isWheelToSwapLocked) {
            int thisPosition = this.getPositionWheel();
            int otherPosition = wheelToSwap.getPositionWheel();
            
            this.setPositionWheel(otherPosition);
            wheelToSwap.setPositionWheel(thisPosition); // Cambian el atributo de posición
            
            if (this.isVisible() && wheelToSwap.isVisible()) {
                this.frameFlickering();
                wheelToSwap.frameFlickering(); // Si son visibles entonces reponganse en el canvas
            }
        }
        return;
    }
    
    /**
     * This locks this wheel so it can´t spin, delete and swap.
     */
    public void lock() {
        isLocked = true;
    }
    
    /**
     * This unlocks this wheel so it can spin, delete and swap.
     */
    public void unlock(){
        isLocked = false;
    }
    
    /**
     * Makes this wheel visible.
     */
    public void makeVisible() {
        wheelShape.makeVisible();
        for (Symbol symbol : symbols.values()) {
            symbol.makeVisible();
        }
        boolean existsSelectedSymbol = selectedSymbol != null;
        if (existsSelectedSymbol) selectedSymbol.frameFlickering();
    }
    
    /**
     * Makes this wheel invisible.
     */
    public void makeInvisible() {
        
        wheelShape.makeInvisible();
        for (Symbol symbol : symbols.values()) {
            symbol.makeInvisible();
        }
    }
    
    /**
     * Sets a new X and Y position to this wheel.
     * @param newPosX newPosX that going to set like x position of this wheel.
     * @param newPosY newPosY that going to set like y position of this wheel.
     */
    public void changePosition(int newPosX, int newPosY) {
        wheelShape.setPosition(newPosX, newPosY);
        repositionSymbols();
        if (isVisible()) {
            wheelShape.frameFlickering();
            boolean existsSelectedSymbol = selectedSymbol != null;
            if (existsSelectedSymbol) {
                selectedSymbol.frameFlickering();
            }
        }
    }
    
    /**
     * Flickers the frame of the wheel and its selected symbol.
     */
    public void frameFlickering() {
        if (isVisible()) {
            wheelShape.frameFlickering();
            selectedSymbol.frameFlickering();
        }
    }
    
    /**
     * Displays all existing symbol colors in order.
     * @return A string array with exiting symbols colors of this slot machine.
     */
    public String[] symbols() {
        ArrayList<Symbol> list = new ArrayList<>(this.symbols.values());
        String[] listString = new String[list.size()];
        int indexListString = 0;
        for (Symbol symbol : list) {
            listString[indexListString] = symbol.getColor();
            indexListString++;
        }
        return listString;
    }
    
    /**
     * Gets the color and number that repeat that color.
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
    
    /**
     * Checks if the wheel can spin.
     * @return true if the wheel has symbols and is not locked, false otherwise.
     */
    public boolean canSpin() {
        return !symbols.isEmpty() && !isLocked;
    }
    
    /**
     * Checks if the wheel is locked.
     * @return true if the wheel is locked, false otherwise.
     */
    public boolean isLocked() {
        return isLocked;
    }
    
    /**
     * Checks if the wheel is stopped.
     * @return true if stopped, false otherwise.
     */
    public boolean isStoped() {
        return isStoped;
    }
    
    /**
     * Checks if the element is visible.
     * @return true if visible, false otherwise.
     */
    public boolean isVisible() {
        return wheelShape.isVisible();
    }
    
    /**
     * Gives the symbol that was selected when spinning the wheel.
     * @return Returns the symbol that was selected when spinning the wheel.
     */
    public Symbol selectedSymbol() {
        return selectedSymbol;
    }
    
    /**
     * Gets the selected symbol.
     * @return the selected symbol.
     */
    public Symbol getSelectedSymbol() {
        return selectedSymbol;
    }
    
    /**
     * Gets the selected symbol's position.
     * @return the integer position of the selected symbol.
     */
    public int getSelectedSymbolInteger() {
        return (int) selectedSymbolInteger;
    }
    
    /**
     * Sets the position of the wheel.
     * @param positionWheel the new position of the wheel.
     */
    public void setPositionWheel(int positionWheel) {
        this.positionWheel = positionWheel;
    }
    
    /**
     * Gets the position of the wheel.
     * @return the position of the wheel.
     */
    public int getPositionWheel() {
        return this.positionWheel;
    }
    
    /**
     * Gets the X position of the wheel shape.
     * @return the X position.
     */
    public int getPositionX() {
        return wheelShape.getXPosition();
    }
    
    /**
     * Gets the Y position of the wheel shape.
     * @return the Y position.
     */
    public int getPositionY() {
        return wheelShape.getYPosition();
    }
    
    /**
     * Gets the X position of the wheel shape.
     * @return the X position.
     */
    public int getXPosition() {
        return wheelShape.getXPosition();
    }
    
    /**
     * Gets the Y position of the wheel shape.
     * @return the Y position.
     */
    public int getYPosition() {
        return wheelShape.getYPosition();
    }
    
    /**
     * Gets the color of the wheel.
     * @return the color of the wheel.
     */
    public String getColor() {
        return wheelShape.getColor();
    }
    
    /**
     * Gets the number of symbols on the wheel.
     * @return the number of symbols.
     */
    public int getNumberOfSymbols() {
        return symbols.size();
    }
    
    /**
     * Gets the size of colors on the wheel.
     * @return the size of symbols map.
     */
    public int getSizeColors() {
        return symbols.size();
    }
    
    /**
     * Gets an array of color configurations.
     * @return a string array of symbol colors.
     */
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
    
    /**
     * Compare two wheels, if have the same data at their attributes are equals.
     * @param wheel the wheel to compare to.
     * @return true if equal, false otherwise.
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
    
    /**
     * To set the wheel color.
     * @param newColor newColor is the color that will be set on this wheel.
     */
    public void changeColor(String newColor) {
        wheelShape.changeColor(newColor);
    }
    
    /**
     * Changes the selected symbol by a given amount of positions.
     * @param post the amount of positions to shift.
     */
    protected void changePositionSymbol(int post) {
        if (symbols.isEmpty() || isLocked) {
            return;
        }
        
        int currentPosition = selectedSymbolInteger != null ? selectedSymbolInteger : 1;
        int totalSymbols = symbols.size();
        
        int newPosition = (currentPosition - 1 + post) % totalSymbols;
        if (newPosition < 0) {
            newPosition += totalSymbols;
        }
        newPosition += 1;
        
        Symbol newSymbol = symbols.get(newPosition);
        if (newSymbol != null) {
            if (isVisible() && selectedSymbol != null) {
                selectedSymbol.makeInvisible();
            }
            selectedSymbol = newSymbol;
            selectedSymbolInteger = newPosition;
            positionSymbol(selectedSymbol);
            if (isVisible()) {
                selectedSymbol.frameFlickering();
            }
        }
    }
    
    /**
     * Positions a symbol on the wheel.
     * @param symbol the symbol to position.
     */
    protected void positionSymbol(Symbol symbol) {
        int xPositionWheel = wheelShape.getXPosition();
        int heightWheel = wheelShape.getYPosition();
        symbol.changePosition(xPositionWheel + 15, heightWheel / 2 + 45);
    }
    
    /**
     * Repositions all symbols on the wheel.
     */
    protected void repositionSymbols() {
        for (Symbol symbol : symbols.values()) {
            positionSymbol(symbol);
        }
    }
    
    /**
     * Finds a symbol based only on its color.
     * @param colorToFind the color to find.
     * @return the symbol if found, null otherwise.
     */
    protected Symbol findJustColorSymbol(String colorToFind) {
        String currentColorSymbol = null;
        Symbol symbolFinded = null;
        boolean isColorFinded = false;
        for (Symbol symbol : symbols.values()) {
            currentColorSymbol = symbol.getColor();
            isColorFinded = colorToFind.equals(currentColorSymbol);
            if (isColorFinded) {
                symbolFinded = symbol;
                break;
            }
        }
        return symbolFinded;
    }
    
    /**
     * Gets the random generator object.
     * @return the random object.
     */
    protected Random getRandom() {
        return random;
    }
    
    /**
     * Gets the list of symbols.
     * @return the map of symbols.
     */
    protected Map<Integer,Symbol> getSymbols() {
        return symbols;
    }
    
    /**
     * Sets the selected symbol.
     * @param newSelectedSymbol the new selected symbol.
     */
    protected void setSelectedSymbol(Symbol newSelectedSymbol) {
        this.selectedSymbol = newSelectedSymbol;
    }
    
    /**
     * Gets the position of the selected symbol.
     * @return the integer position.
     */
    protected Integer getSelectedSymbolPosition() {
        return selectedSymbolInteger;
    }
    
    /**
     * Validates deletion of the wheel.
     */
    protected void validateDeletion() {
    }
    
    /**
     * Checks if this wheel can swap.
     * @return true if it can swap, false otherwise.
     */
    protected boolean isSwap() {
        return true;
    }
}