
/**
 * StraightSided are the figures that has a height and a width.
 * This is the class StrightSided.
 * 
 * @author Gualdron - Villagran
 * @version 1.0
 */
public abstract class StraightSided extends Figure {
    
    protected int height;
    protected int width;
    
    protected StraightSided(int x, int y, String color) {
        super(x, y, color);
    }
    
    /**
     * Change size of a specified polygon
     */
    public void changeSize(int newHeight, int newWidth) {
        erase();
        height = newHeight;
        width = newWidth;
        draw();
    }
    
    public int getWidth() {
        return width;
    }
    
    public int getHeight() {
        return height;
    }
    
    /**
     * Get the figure color
     */
    public String getColor() {
        return color;
    }

}