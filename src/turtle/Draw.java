/*
 * author: Weiyue Cai
 * date: feb 20, 2021
 * */


package turtle;

import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.Stack;
import ui.*;


/* Draw: to draw the real image in the Java Swing */
public class Draw implements Turtle{
    double step;
    double delta;   // the unit angle when the turtle turns
    Point2D pos;
    double theta;   // the angle of current state 
    Stack states = new Stack(); // states is a stack of arrays
    public Panel canvas;


    /* setter: initialize the original state of turtle */
    public void init(Point2D pos, double angle_deg) {
        this.theta = angle_deg;
        this.pos = pos;
    }

    /* getter */
    public Point2D getPosition() {
        return this.pos;
    }

    /*getter : get the current angle, which is theta*/
    public double getAngle() {
        return this.theta;
    }

    /* setter */
    public void setUnits(double step, double delta) {
        this.step = step;
        this.delta = delta;
    }

    /* to show the image in the Java Swing, 
     * we need a panel to stock all the lines 
     * 
     * */
    public void setCanvas(Panel canvas) {
        this.canvas = canvas;
    }

    public void draw() {
        double endx = getPosition().getX() + step*Math.cos(theta*Math.PI/180);
        double endy = getPosition().getY() + step*Math.sin(theta*Math.PI/180);
        Point2D newPoint = new Point2D.Double(endx, endy);
        Line2D line = new Line2D.Double(this.pos, newPoint);
        canvas.addLines(line);
        this.pos = newPoint;
    }

    public void move() {
        double endx = getPosition().getX() + step*Math.cos(delta*Math.PI/180);
        double endy = getPosition().getY() + step*Math.sin(delta*Math.PI/180);
        this.pos = new Point2D.Double(endx, endy);
    }

    public void turnR() {
        this.theta -= delta;
    }

    public void turnL() {
        this.theta += delta;
    }

    public void push() {
    	// initialize an array size 3
        double[] state = new double[3];
        
        // stock x, y and current angle in the array
        state[0] = getPosition().getX();
        state[1] = getPosition().getY();
        state[2] = this.theta;
        
        // push this array of current state in the stack of states
        states.push(state);
    }

    public void pop() {
        double[] lastState = (double[]) states.pop();
        this.theta = lastState[2];
        Point2D lastPoint = new Point2D.Double(lastState[0], lastState[1]);
        this.pos = lastPoint;
    }

    public void stay() {
        return;
    }
}

