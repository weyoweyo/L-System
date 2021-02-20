
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
import system.*;
import turtle.*;


/*
 * param: @ turtle: initialize the virtual pen to calculate estimated boundary box
 * 		  @ turtleEps: initialize the eps turtle 
 * 
 * */
public class readJSON {
	public static void readJSONFile(String fileName, LSystem system, Turtle turtle, Turtle turtleEps) {
        try {
        	String filePath = "src/" + fileName; 
        	
        	File directory = new File(filePath);
        	String abPath = directory.getAbsolutePath(); 
        	
        	
            JSONObject input = new JSONObject(new JSONTokener(new java.io.FileInputStream(abPath)));

		    // getting alphabets
            JSONArray alphabet = (JSONArray) input.get("alphabet");
            for (int i = 0; i < alphabet.length(); i++) {
		 		String letter = alphabet.getString(i);
		 		Character c = letter.charAt(0);
		 		system.addSymbol(c);
		 	}
            

            // getting rules
            JSONObject rules = input.getJSONObject("rules");
            Map<String, Object> ruleObjMap = rules.toMap();
            Iterator<Map.Entry<String, Object>> itr1 = ruleObjMap.entrySet().iterator();
            while (itr1.hasNext()) {
                Map.Entry pair = itr1.next();
                String key = (String) pair.getKey();
                Symbol sym = system.alphabet.get(key.charAt(0));

                ArrayList<String> rulesArray = (ArrayList<String>) pair.getValue();
                for (int i=0; i<rulesArray.size(); i++) {
                    system.addRule(sym, rulesArray.get(i));
                }
            }

            
            // getting axiom
			String axiom = (String) input.get("axiom");
            system.setAxiom(axiom);
			

            // getting actions
            JSONObject actions = input.getJSONObject("actions");
            Map<String, Object> actionObjMap = actions.toMap();
            Iterator<Map.Entry<String, Object>> itr2 = actionObjMap.entrySet().iterator();
            while (itr2.hasNext()) {
                Map.Entry pair = itr2.next();
                String key = (String) pair.getKey();
                Symbol sym = system.alphabet.get(key.charAt(0));
                String action = (String) pair.getValue();
                system.setAction(sym, action);
            }


            // getting parameters
            JSONObject parameters = input.getJSONObject("parameters");
            Map<String, Object> parameterObjMap = parameters.toMap();
            Iterator<Map.Entry<String, Object>> itr3 = parameterObjMap.entrySet().iterator();
            double unitAngle = 0.0;
            double unitStep = 0.0;

            while (itr3.hasNext()) {
                Map.Entry pair = itr3.next();
                String key = (String) pair.getKey();

                if (key.equals("start")) {
                    ArrayList startState = (ArrayList) pair.getValue();
                    Point2D initPoint = new Point2D.Double((Integer) startState.get(0), (Integer) startState.get(1));
                    double initAngle = (Integer) startState.get(2);

                    turtle.init(initPoint, initAngle);
                    turtleEps.init(initPoint, initAngle);
                   
                } else if (key.equals("angle")) {
                    boolean convert = false;
                    try {
                        BigDecimal angle = (BigDecimal) pair.getValue();
                        unitAngle = angle.doubleValue();
                        convert = true;
                    } catch (ClassCastException e) {
                        System.out.println("cannot be cast to class BigDecimal");
                    }

                    if (!convert) {
                        try {
                            unitAngle = (double) pair.getValue();
                            convert = true;
                        } catch (ClassCastException e) {
                            System.out.println("cannot be cast to class double");
                        }
                    }

                    if (!convert) {
                        try {
                            unitAngle = (int) pair.getValue();
                        } catch (ClassCastException e) {
                            e.printStackTrace();
                        }
                    }

                } else if (key.equals("step")) {
                    unitStep = (Integer) pair.getValue();
                }
            }

            turtle.setUnits(unitStep, unitAngle);
            turtleEps.setUnits(unitStep, unitAngle);
  
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            System.out.println("Cannot find file.");
        }
    }
}



