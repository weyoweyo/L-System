
/*
 * author: Weiyue Cai
 * date: feb 20, 2021
 * */

package turtle;

/* VirtualPen: a turtle follows every coordinate of image 
 * without leaving any trace */

import java.awt.geom.Point2D;
import java.util.Stack;

public class VirtualPen implements Turtle {
    double step;
    double delta;   
    Point2D pos;
    double theta;   
    Stack states = new Stack();


    public void init(Point2D pos, double angle_deg) {
        this.theta = angle_deg;
        this.pos = pos;
    }

    public Point2D getPosition() {
        return this.pos;
    }

    public double getAngle() {
        return this.theta;
    }

    public void setUnits(double step, double delta) {
        this.step = step;
        this.delta = delta;
    }


    public void draw() {
        double endx = getPosition().getX() + step*Math.cos(theta*Math.PI/180);
        double endy = getPosition().getY() + step*Math.sin(theta*Math.PI/180);
        Point2D newPoint = new Point2D.Double(endx, endy);
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
        double[] state = new double[3];
        state[0] = getPosition().getX();
        state[1] = getPosition().getY();
        state[2] = this.theta;
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

