/*
 * author: Weiyue Cai
 * date: feb 20, 2021
 * */

package turtle;


/* TurtleEps: generate postscript codes into eps file */
import java.awt.geom.Point2D;
import java.io.*;
import java.util.Stack;


public class TurtleEps implements Turtle {
    double step;
    double delta;   
    Point2D pos;
    double theta;   
    Stack states = new Stack();
    String fileName;

    /*setter*/
    public void init(Point2D pos, double angle_deg) {
        this.theta = angle_deg;
        this.pos = pos;
    }

    /*getter*/
    public Point2D getPosition() {
        return this.pos;
    }

    /*getter : get the current angle, which is theta*/
    public double getAngle() {
        return this.theta;
    }

    /*setter*/
    public void setUnits(double step, double delta) {
        this.step = step;
        this.delta = delta;
    }


    public void setFileName(String path) {
        this.fileName = path;
    }


    /* draw function follows the basic logic of postscript
     * to draw a line from A to B
     * do the following code:
     * newpath
     * X_a Y_a moveto
     * X_b Y_b lineto
     * stroke
     * */
    public void draw() {
        try {
            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(fileName, true)));
            out.println("newpath");
            out.print(getPosition().getX());
            out.print(" ");
            out.print(getPosition().getY());
            out.print(" ");
            out.println("moveto");
            double endx = getPosition().getX() + step*Math.cos(theta*Math.PI/180);
            double endy = getPosition().getY() + step*Math.sin(theta*Math.PI/180);
            Point2D newPoint = new Point2D.Double(endx, endy);
            this.pos = newPoint;
            out.print(getPosition().getX());
            out.print(" ");
            out.print(getPosition().getY());
            out.print(" ");
            out.println("lineto");
            out.println("stroke");
            out.close();
        } catch (IOException e) {
            System.out.println("cannot find file");
        }
    }

    @Override
    public void move() {
        try {
            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(fileName, true)));
            out.println("newpath");
            out.print(getPosition().getX());
            out.print(" ");
            out.print(getPosition().getY());
            out.print(" ");
            out.println("moveto");
            double endx = getPosition().getX() + step*Math.cos(theta*Math.PI/180);
            double endy = getPosition().getY() + step*Math.sin(theta*Math.PI/180);
            Point2D newPoint = new Point2D.Double(endx, endy);
            this.pos = newPoint;
            out.print(getPosition().getX());
            out.print(" ");
            out.print(getPosition().getY());
            out.print(" ");
            out.println("stroke");
            out.close();
        } catch (IOException e) {
            System.out.println("cannot find file");
        }
    }

    public void turnR() {
        this.theta -= delta;
    }

    public void turnL() {
        this.theta += delta;
    }

    @Override
    public void push() {
        double[] state = new double[3];
        state[0] = getPosition().getX();
        state[1] = getPosition().getY();
        state[2] = this.theta;
        states.push(state);
    }

    @Override
    public void pop() {
        double[] lastState = (double[]) states.pop();
        this.theta = lastState[2];
        Point2D lastPoint = new Point2D.Double(lastState[0], lastState[1]);
        this.pos = lastPoint;
    }

    @Override
    public void stay() {
        return;
    }
}

