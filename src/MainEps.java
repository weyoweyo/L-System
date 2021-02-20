/*
 * author: Weiyue Cai
 * date: feb 20, 2021
 * */


import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import org.json.*;
import turtle.*;
import system.*;
import ui.*;


public class MainEps {
    public static void main(String[] args) throws IOException {
        TurtleEps turtleEps = new TurtleEps();
        VirtualPen virtualPen = new VirtualPen();

        LSystem system = new LSystem();

              
        readJSON.readJSONFile(args[0], system, virtualPen, turtleEps);
        
        int n = Integer.parseInt(args[1]); 
            

        // boundary function: calculate the estimated bounding box       
        Rectangle2D boundingBox = system.boundary(system.getAxiom(), n, virtualPen);

        int minX = (int)boundingBox.getMinX();
        int minY =(int)boundingBox.getMinY();
        int width =(int)boundingBox.getWidth()+1;
        int height = (int)boundingBox.getHeight()+1;
        String box = ""+ minX + " " + minY + " " + width + " " + height;
        
        
        // generate postscript code into eps file    
        PrintWriter out = new PrintWriter(args[2]);
        turtleEps.setFileName(args[2]);
        out.println("%!PS-Adobe-3.0 EPSF-3.0");
        out.println("%%BoundingBox:" + box);
        out.close();
        system.tell(turtleEps, system.getAxiom(), n);
        // to append new text in an existing file without rewritting
        PrintWriter out1 = new PrintWriter(new BufferedWriter(new FileWriter(args[2], true)));
        out1.println("%%Trailer");
        out1.println("%%EOF");
        out1.close();       
    }
}