import java.awt.*;

/**
 * A triangle that can be manipulated and that draws itself on a canvas.
 * 
 * @author  Michael Kolling and David J. Barnes
 * @version 1.0  (15 July 2000)
 */

public class Triangle extends StraightSided {
    
    public static final int VERTICES=3;

    private double rotationAngle; // La implementacion de la rotación fue hecha con Gemini IA
    
    
    public void makeInvisible(){
        erase();
        isVisible = false;
    }
    
    /**
     * Create a new triangle at default position with default color.
     */
    public Triangle() {
        super(140, 15, "green");
        height = 30;
        width = 40;
        rotationAngle = 0.0;
    }
    
    /**
     * Create a new triangle given a color, width and height.
     */
    public Triangle(String color, int height, int width) {
        super(140, 15, color);
        this.height = height;
        this.width = width;
        rotationAngle = 0.0;
    }
    
    /**
     * Flips triangle vertically. Inspiración: Gemini IA.
     */
    public void flip() {
        erase();
        height = -height;
        draw();
    }
    
    /**
     * This able to rotate the triangle a determinated angle.
     * @param angle angle is a double that defines the rotation angle
     * of triangle by grades °.
     */
    public void rotate(double angle) { // Este método fue ayudado a hacer por Gemini IA
        erase();
        rotationAngle = Math.toRadians(angle);
        draw();
    }
    
    /**
     * Make to move triangle # times,
     * triangle moves 10 units depending on if times is positive this triangle
     * shifts to the rigth, but if times is negative figure shifts to the left.
     * @param times times are the number of iterations (10 units per iteration) 
     * that this figure moves based on if this number is negative or positive.
     */
    public void walk(int times) {
        slowMoveHorizontal(times*10);
    }
    
    /**
     * Convert this triangle into an equilateral triangle.
     */
    public void equilateral() {
        erase();
        /* Según area = s^2*(sqrt(3)/4) <- Formula de area del triangulo equilatero
         * s = sqrt(4*area/sqrt(3))
         */
        double s = Math.sqrt((4 * area()) / Math.sqrt(3)); // Lado de triang. equilatero
    
        width = (int) s;
        height = (int) (s * (Math.sqrt(3) / 2)); /* Propiedad de la altura 
            de una triangulo equilatero: s * sqrt(3)/2*/
        
        draw();
    }

    /**
     * Give area of triangle.
     */
    public int area() {
        int areaTriangle = (height*width)/2;
        
        if (areaTriangle > 0) {
            return areaTriangle;
        }
        width = 0; // Si al calcular el area da negativo o 0 entonces el area es 0
        height = 0;
        return 0;
    }

    /*
     * Draw the triangle with current specifications on screen.
     */
    @Override
    protected void draw(){// Este método fue ayudado a modificar por Gemini IA para que funcione rotate()
        if(isVisible) {
        Canvas canvas = Canvas.getCanvas();
        
        // Coordenadas originales de los vértices (sin rotar)
        int x1 = xPosition;
        int y1 = yPosition;
        int x2 = xPosition + (width/2);
        int y2 = yPosition + height;
        int x3 = xPosition - (width/2);
        int y3 = yPosition + height;
        
        // Centro de rotación (ejemplo: la punta superior)
        int cx = xPosition;
        int cy = yPosition;
        
        // Aplica la rotación a cada vértice
        // Nota: Las fórmulas de rotación necesitan coordenadas 'double'
        double newX1 = cx + (x1 - cx) * Math.cos(rotationAngle) - (y1 - cy) * Math.sin(rotationAngle);
        double newY1 = cy + (x1 - cx) * Math.sin(rotationAngle) + (y1 - cy) * Math.cos(rotationAngle);
        
        // Repite el cálculo para x2, y2, x3, y3...
        
        double newX2 = cx + (x2 - cx) * Math.cos(rotationAngle) - (y2 - cy) * Math.sin(rotationAngle);
        double newY2 = cy + (x2 - cx) * Math.sin(rotationAngle) + (y2 - cy) * Math.cos(rotationAngle);
        
        double newX3 = cx + (x3 - cx) * Math.cos(rotationAngle) - (y3 - cy) * Math.sin(rotationAngle);
        double newY3 = cy + (x3 - cx) * Math.sin(rotationAngle) + (y3 - cy) * Math.cos(rotationAngle);
        
        // Crea el nuevo polígono con las coordenadas rotadas
        int[] xpoints = { (int) newX1, (int) newX2, (int) newX3 };
        int[] ypoints = { (int) newY1, (int) newY2, (int) newY3 };
        
        canvas.draw(this, color, new Polygon(xpoints, ypoints, VERTICES));
        canvas.wait(10);
        }
    }   
}