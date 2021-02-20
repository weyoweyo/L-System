
/*
 * author: Weiyue Cai
 * date: feb 20, 2021
 * */

package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.util.LinkedList;


public class Panel extends JPanel {
    private final LinkedList<Line2D> lineList = new LinkedList<>();

    public Panel() {
        this.setSize(1000,700);
    }

    public void addLines(Line2D line) {
        lineList.add(line);
    }

    /* override paintComponent function 
     * to draw every line of image on the panel */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        for (int i=0; i<lineList.size(); i++) {
            g2.draw(lineList.get(i));
        }
    }
}
