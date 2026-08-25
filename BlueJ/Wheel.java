package BlueJ;
import java.util.List;
import java.util.Random;
import java.util.TreeMap;
import java.util.ArrayList;
/**
 * Wheel class, this is the wheel that will be installed on slot machine.
 *
 * @author Steveen-Gualdron
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
    
    
    /**
     * Gets the current color.
     */
    public String getColor() {
        return color;
    }
    
    /**
     * Gets the current X position.
     */
    public int getPositionX() {
        return positionX;
    }

    /**
     * Gets the current Y position.
     */
    public int getPositionY() {
        return positionY;
    }


    /**
     * Gets the rectangle object.
     */
    public Rectangle getRectangle() {
        return rectangle;
    }

    /**
     * Checks if the wheel is stopped.
     */
    public boolean isStoped() {
        return isStoped;
    }

    /**
     * Checks if the element is visible.
     */
    public boolean isVisible() {
        return isVisible;
    }

    /**
     * Gets the selected symbol.
     */
    public Symbol getSelectedSymbol() {
        return selectedSymbol;
    }

    /**
     * Gets the list of symbols.
     */   
    public TreeMap<Integer,Symbol> getSymbols() {
        return symbols;
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
        rectangle.setxPosition(newPosX);
        rectangle.makeVisible();
    }

    /**Sets a new Y position to this wheel.
     * @param newPosY newPosY that going to set like y position of this wheel. 
     */
    public void changePositionY(int newPosY) {
        positionY = newPosY;
        rectangle.setxPosition(newPosY);
        rectangle.makeVisible();
    }

    /**Add a specific symbol with its color.
     * @param symbol symbol is the color of symbol. ----------------------
     */
    public void addSymbol(Symbol triangle) {

        if (symbols.isEmpty()) {
            selectedSymbol = triangle;
        }
        symbols.put(triangle.getPositionWheel(), triangle);

    }

    /**Remove a specific symbol with its color.
     * @param symbol symbol is the color of symbol that will be removed. ---------------
     */
    public void delSymbol(Symbol triangle) {
        symbols.remove(triangle.getPositionWheel());

        if (!this.symbols.isEmpty()) { 
            spin();
        }

    }
    // ----------------
    private void viewSymbol() {
            Symbol triangle = selecSymbol();
            triangle.changePositionX(positionX/2);
            triangle.changePositionY(positionX/2);
            triangle.makeVisible();
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
        if (!this.symbols.isEmpty()) {
        List<Integer> keys = new ArrayList<>(this.symbols.keySet());
        int randomIndex = random.nextInt(keys.size());
        int randomKey = keys.get(randomIndex);
        selectedSymbol = symbols.get(randomKey);
        }
    }
    /**Makes this wheel invisible.
     */
    public void makeInvisible() {
        rectangle.changeColor("white");
        selectedSymbol.makeInvisible();
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

    
}
