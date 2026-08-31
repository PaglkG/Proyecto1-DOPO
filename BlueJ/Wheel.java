package BlueJ;
import java.util.List;
import java.util.Random;
import java.util.TreeMap;
import java.util.ArrayList;
/**
 * Wheel class, this is the wheel that will be installed on slot machine.
 *
 * @author Gualdron-Villagran
 * @version 0.1
 */
public class Wheel {
    private int positionX;
    private int positionY;

    private int wheelShape;
    private Rectangle rectangle; //fatal agregar a astah
    private boolean isStoped;
    private boolean isVisible;
    private Random random;
    private String color;
    private Symbol selectedSymbol;
    private TreeMap<Integer,Symbol> symbols;


    /**Constructor class of wheel, niladic method class.
     */
    public Wheel() {
        rectangle = new Rectangle();
        rectangle.changeSize(100,30);
        rectangle.changeColor("grey");
        rectangle.makeVisible();

        random = new Random();
        symbols = new TreeMap<>();
        color = "grey";
        rectangle = new Rectangle();
        rectangle.changeSize(100,30);
        rectangle.changeColor(color);
        rectangle.makeVisible();
    }
    
    /**To set the wheel color.
     * @param newColor newColor is the color that will be set on this wheel.
     */
    public void changeColor(String newColor) {

        color = newColor;
        rectangle.changeColor(newColor);
    }

    /**Sets a new X position to this wheel.
     * @param newPosX newPosX that going to set like x position of this wheel. 
     */
    public void changePositionX(int newPosX) {
        positionX = newPosX;
        rectangle.setXPosition(newPosX);
        rectangle.makeVisible();
    }

    /**Sets a new Y position to this wheel.
     * @param newPosY newPosY that going to set like y position of this wheel. 
     */
    public void changePositionY(int newPosY) {
        positionY = newPosY;
        rectangle.setYPosition(newPosY);
        rectangle.makeVisible();
    }
    
    /**
     * 
     */
    public void addSymbol(String color, int pos) {
        int sizeSymbols = symbols.size();
        Symbol symbolToAdd = new Symbol(color, pos);
        addSymbol(symbolToAdd);
    }

    /**Add a specific symbol with its color.
     * @param symbol symbol is the color of symbol. ----------------------
     */
    public void addSymbol(Symbol triangle) {
        boolean isSymbolsEmpty = symbols.isEmpty();
        if (isSymbolsEmpty) {
            selectedSymbol = triangle;
        }
        symbols.put(triangle.getPositionWheel(), triangle);
    }

    /**Remove a specific symbol with its color.
     * @param symbol symbol is the color of symbol that will be removed. ---------------
     */
    public void delSymbol(Symbol triangle) {
        symbols.remove(triangle.getPositionWheel());
        triangle.makeInvisible();
        boolean isSymbolsEmpty = this.symbols.isEmpty();
        if (!isSymbolsEmpty) { 
            spin();
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
        rectangle.makeInvisible();
        selectedSymbol.makeInvisible();
        for (Symbol symbol : symbols.values()) {
            symbol.makeInvisible();
        }
        symbols.clear();
    }


    /**Makes this wheel visible.
     */
    public void makeVisible() {
        rectangle.changeColor(color);
        selectedSymbol.makeVisible();
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
     * Gets the current color.
     */
    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
    
    /**
     * Gets the current X position.
     */
    public int getPositionX() {
        return positionX;
    }

    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    /**
     * Gets the current Y position.
     */
    public int getPositionY() {
        return positionY;
    }

    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }

    public int getWheelShape() {
        return wheelShape;
    }

    public void setWheelShape(int wheelShape) {
        this.wheelShape = wheelShape;
    }

    /**
     * Gets the rectangle object.
     */
    public Rectangle getRectangle() {
        return rectangle;
    }

    public void setRectangle(Rectangle rectangle) {
        this.rectangle = rectangle;
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

    /*Show the symbol making visible
     */ 
    private void viewSymbol() {
            Symbol triangle = selecSymbol();
            triangle.changePositionX(positionX/2);
            triangle.changePositionY(positionX/2);
            triangle.makeVisible();
    }
}
