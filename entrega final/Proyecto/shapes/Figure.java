package shapes;

/**
 * Class Figure, each figure of shapes is a Figure.
 *
 * @author Gualdron - Villagran
 * @version 1.0
 */
public abstract class Figure {
    
    protected String color;
    protected boolean isVisible;
    protected int xPosition;
    protected int yPosition;

    /**
     * Constructor for the Figure class.
     * @param x the initial X position of the figure.
     * @param y the initial Y position of the figure.
     * @param color the initial color of the figure.
     */
    protected Figure(int x, int y, String color) {
        this.xPosition = x;
        this.yPosition = y;
        this.color = color;
        this.isVisible = false;
    }

    /**
     * Make this shape visible. If it was already visible, do nothing.
     */
    public void makeVisible() {
        isVisible = true;
        draw();
    }

    /**
     * Make this shape invisible. If it was already invisible, do nothing.
     */
    public void makeInvisible() {
        erase();
        isVisible = false;
    }

    /**
     * Move the shape horizontally to the right by a specific distance.
     * @param distance the desired distance in pixels.
     */
    public void moveRigth(int distance) {
        erase();
        xPosition += distance;
        draw();
    }

    /**
     * Move the shape a few pixels to the left.
     */
    public void moveLeft() {
        moveHorizontal(-20);
    }

    /**
     * Move the shape a few pixels up.
     */
    public void moveUp() {
        moveVertical(-20);
    }

    /**
     * Move the shape a few pixels down.
     */
    public void moveDown() {
        moveVertical(20);
    }

    /**
     * Move the shape vertically.
     * @param distance the desired distance in pixels.
     */
    public void moveVertical(int distance) {
        erase();
        yPosition += distance;
        draw();
    }

    /**
     * Move the shape horizontally.
     * @param distance the desired distance in pixels.
     */
    public void moveHorizontal(int distance) {
        erase();
        xPosition += distance;
        draw();
    }

    /**
     * Slowly move the shape horizontally.
     * @param distance the desired distance in pixels.
     */
    public void slowMoveHorizontal(int distance) {
        int delta;
        if(distance < 0) {
            delta = -1;
            distance = -distance;
        } else {
            delta = 1;
        }
        for(int i = 0; i < distance; i++){
            xPosition += delta;
            draw();
        }
    }

    /**
     * Slowly move the shape vertically.
     * @param distance the desired distance in pixels.
     */
    public void slowMoveVertical(int distance) {
        int delta;
        if(distance < 0) {
            delta = -1;
            distance = -distance;
        } else {
            delta = 1;
        }
        for(int i = 0; i < distance; i++){
            yPosition += delta;
            draw();
        }
    }

    /**
     * Change the shape's color.
     * @param newColor the new color. Valid colors are "red", "yellow", "blue", "green",
     * "black", "white", "magenta", "orange", "pink", "cyan", "gray", "lightGray",
     * "darkGray", "brown" and "maroon".
     */
    public void changeColor(String newColor) {
        color = newColor;
        draw();
    }

    /**
     * Get position X of the shape.
     * @return the position X of the shape.
     */
    public int getXPosition(){
        return xPosition;
    }

    /**
     * Get position Y of the shape.
     * @return the position Y of the shape.
     */
    public int getYPosition(){
        return yPosition;
    }

    /**
     * Set new X and Y position.
     * @param x x is the new xPosition of the figure.
     * @param y y is the new yPosition of the figure.
     */
    public void setPosition(int x, int y) {
        this.xPosition = x;
        this.yPosition = y;
    }

    /**
     * Erasing shape on canvas.
     */
    public void erase() {
        if(isVisible) {
            Canvas canvas = Canvas.getCanvas();
            canvas.erase(this);
        }
    }

    /**
     * Get the figure color.
     * @return the current color of the figure.
     */
    public String getColor() {
        return color;
    }

    /**
     * Give the attribute visible.
     * @return true if the figure is visible, false otherwise.
     */
    public boolean isVisible() {
        return isVisible;
    }

    /**
     * Flickers the frame of this figure by making it invisible then visible.
     */
    public void frameFlickering() {
        makeInvisible();
        makeVisible();
    }

    /**
     * Sets the visibility state of this figure without drawing it immediately.
     * @param isVisible true to make it visible, false to make it invisible.
     */
    public void setVisible(boolean isVisible) {
        this.isVisible = isVisible;
    }

    /**
     * Sets the color attribute of this figure without drawing it immediately.
     * @param color the new color.
     */
    public void setColor(String color) {
        this.color = color;
    }

    /**
     * Draw the shape on canvas.
     */
    protected abstract void draw();
}