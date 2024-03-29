package turtle;

import java.awt.geom.Point2D;
import java.io.FileNotFoundException;

/**
 * Turtle graphics interface. The turtle state is defined as its
 * location on the plane and the orientation of its nose.
 * Implementing classes are expected to initialize
 * the turtle with position (0,0) and angle 0 by default.
 * The turtle moves and draws by unit-length steps, and turns
 * left or right by a unit angle
 * (e.g., 30), which are set in {@link #setUnits(double, double) }.
 */

public interface Turtle {
    /**
     * Draws a line of unit length
     */
    public void draw() throws FileNotFoundException;
    /**
     * Moves by unit length, no drawing.
     */
    public void move();
    /**
     * Turn right (clockwise) by unit angle.
     */
    public void turnR();
    /**
     * Turn left (counter-clockwise) by unit angle.
     */
    public void turnL();
    /**
     * Saves turtle state
     */
    public void push();
    /**
     * Recovers turtle state
     */
    public void pop();
    /**
     * Lets the turtle relax.
     */
    public void stay();
    /**
     * initializes the turtle state (and clears the state stack)
     * @param pos turtle position
     * @param angle_deg angle in degrees (90=up, 0=right)
     */
    public void init(Point2D pos, double angle_deg);
    /**
     * Turtle position
     *
     * @return location of the turtle on the plane
     */
    public Point2D getPosition();
    /**
     * angle of the turtle's nose
     * @return angle in degrees
     */
    public double getAngle();
    /**
     * sets the unit step and unit angle
     *
     * @param step length of an advance (move or draw)
     * @param delta unit angle change in degrees (for turnR and turnL)
     */
    public void setUnits(double step, double delta);

}
