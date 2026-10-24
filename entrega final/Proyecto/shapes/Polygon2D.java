package shapes;

/**
 * Polygon2D are the figures that have a height and a width.
 * This is the class StrightSided.
 *
 * @author Gualdron - Villagran
 * @version 1.0
 */
public abstract class Polygon2D extends Figure {
    
    protected int height;
    protected int width;

    /**
     * Constructor for the Polygon2D class.
     * @param x the initial X position of the polygon.
     * @param y the initial Y position of the polygon.
     * @param color the initial color of the polygon.
     */
    protected Polygon2D(int x, int y, String color) {
        super(x, y, color);
    }

    /**
     * Change the size of a specified polygon.
     * @param newHeight the new height of the polygon.
     * @param newWidth the new width of the polygon.
     */
    public void changeSize(int newHeight, int newWidth) {
        erase();
        if (newHeight >= 0 && newWidth >= 0){
            height = newHeight;
            width = newWidth;
        }
        draw();
    }

    /**
     * Gets the width of the polygon.
     * @return the current width of the polygon.
     */
    public int getWidth() {
        return width;
    }

    /**
     * Gets the height of the polygon.
     * @return the current height of the polygon.
     */
    public int getHeight() {
        return height;
    }

    /**
     * Get the figure color.
     * @return the current color of the figure.
     */
    public String getColor() {
        return color;
    }
}