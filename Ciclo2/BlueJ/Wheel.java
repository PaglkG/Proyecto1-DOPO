import java.util.List;
import java.util.Random;
import java.util.TreeMap;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;

/**
 * Wheel class, this is the wheel that will be installed on slot machine.
 *
 * @author Gualdron-Villagran
 * @version 0.1
 */
public class Wheel {
    private int positionWheel;
    
    private Rectangle wheelShape; //fatal agregar a astah
    private boolean isStoped;
    private boolean isVisible;
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
        wheelShape.makeInvisible();
        wheelShape.makeVisible();
    }

    /**Sets a new Y position to this wheel.
     * @param newPosY newPosY that going to set like y position of this wheel. 
     */
    public void changePositionY(int newPosY) {
        wheelShape.setYPosition(newPosY);
        wheelShape.makeInvisible();
        wheelShape.makeVisible();
    }
    
    /**Add a specific symbol with its color.
     * @param color color is the color of symbol to add. ----------------------
     */
    public void addSymbol(String color) {
        int sizeSymbols = symbols.size();
        Symbol symbolToAdd = new Symbol(color);
        addSymbol(symbolToAdd);
    }

    /**Add a specific symbol with its color.
     * @param symbol symbol is the symbol to add to the wheel. ----------------------
     */
    public void addSymbol(Symbol symbol) {
        boolean isSymbolsEmpty = symbols.isEmpty();
        if (isSymbolsEmpty) {
            selectedSymbol = symbol;
        }
        symbol.setPositionAtTheWheel(symbols.size()+1);
        
        int xPositionWheel = wheelShape.getXPosition(), widthWheel = wheelShape.getWidth(), heightWheel =  wheelShape.getYPosition();
        symbol.changePositionX(xPositionWheel+15);
        symbol.changePositionY(heightWheel/2+45);
        symbol.changeSize(30, widthWheel);
        
        symbols.put(symbol.getPositionAtTheWheel(), symbol);
    }

    /**Remove a specific symbol with its color.
     * @param symbol symbol is the symbol that will be removed. ---------------
     */
    public void delSymbol(Symbol triangle) {
        symbols.remove(triangle.getPositionAtTheWheel());
        triangle.makeInvisible();
        boolean isSymbolsEmpty = this.symbols.isEmpty();
        if (!isSymbolsEmpty) { 
            spin();
        }
    }
    
    /**Remove a specific symbol with its color.
     * @param color color is the color of symbol that will be removed of symbols. ---------------
     */
    public void delSymbol(String color) {
        String currentColorSymbol = null; 
        Set<Symbol> symbolsV = new HashSet<>(symbols.values());
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
        if (!isSymbolsEmpty) {
            List<Integer> keys = new ArrayList<>(this.symbols.keySet());
            int randomIndex = random.nextInt(keys.size());
            int randomKey = keys.get(randomIndex);
            selectedSymbol = symbols.get(randomKey);
        }
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
        return getRectangle().getColor();
    }

    /**Makes this wheel visible.
     */
    public void makeVisible() {
        wheelShape.makeVisible();
        boolean isSymbolsEmpty = symbols.isEmpty();
        Set<Symbol> symbolsV = new HashSet<>(symbols.values());
        for (Symbol symbol : symbolsV) {
            if (isSymbolsEmpty) break;
            symbol.makeVisible();
        }
        if (selectedSymbol != null) selectedSymbol.makeVisible();
    }

    /**Displays all existing symbol colors in order.
     * @return A string array with exiting symbols colors of this slot machine.
     */
    public ArrayList<String>  symbols() {
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
        return false;
    }

    /**
     * Gets the rectangle object.
     */
    public Rectangle getRectangle() {
        return wheelShape;
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
        return isVisible;
    }

    public void setVisible(boolean isVisible) {
        this.isVisible = isVisible;
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
    
    
    public void moveHorizontal(int times) {
        int SPACEAMONGWHEEL = 20, LONGITUDEWHEEL = wheelShape.getWidth(); 
        wheelShape.moveHorizontal(0);
        changePositionX((SPACEAMONGWHEEL+LONGITUDEWHEEL)*times);
    }
    
    public int getNumberOfSymbols() {
        return symbols.size();
    }
    
    /*Show the symbol making visible
     */ 
    private void viewSymbol() {
        int positionX = wheelShape.getXPosition(), positionY = wheelShape.getYPosition();
        Symbol triangle = selecSymbol();
        triangle.changePositionX(positionX/2);
        triangle.changePositionY(positionX/2);
        triangle.makeVisible();
    }
}
