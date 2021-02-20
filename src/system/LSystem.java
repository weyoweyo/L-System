/*
 * author: Weiyue Cai
 * date: feb 20, 2021
 * */

package system;

import java.awt.geom.Rectangle2D;
import java.io.FileNotFoundException;
import java.util.*;
import turtle.*; 


public class LSystem {
    HashMap<Symbol, LinkedList<LinkedList<Symbol>>> rules = new HashMap<>();
    String axiom = "";
    public HashMap<Character, Symbol> alphabet = new HashMap<>();
    HashMap<Symbol, String> actions = new HashMap<>();

    public Symbol addSymbol(char sym) {
        Symbol symbol = new Symbol(sym);
        alphabet.put(sym, symbol);
        return symbol;
    }

    public void addRule(Symbol sym, String expansion) {
        LinkedList<LinkedList<Symbol>> rulesFor1Sym = new LinkedList<>();
        LinkedList<Symbol> rulesConvert2Sym = new LinkedList<>();
        
        // convert expansion string into a linked list of symbols
        for (char c : expansion.toCharArray()) {
            rulesConvert2Sym.add(alphabet.get(c));
        }

        if (rules.containsKey(sym)) {
        	// if this symbol exists already in the rules 
            // obtain rules.get(sym) --> list of symbol
            rules.get(sym).add(rulesConvert2Sym);
        } else {
        	// if this symbol does not exist,
        	// we have to create key and value 
        	// and add rule to the list 
            rules.put(sym, rulesFor1Sym);
            rulesFor1Sym.add(rulesConvert2Sym);
        }
    }

    public void setAction(Symbol sym, String action) {
        actions.put(sym, action);
    }

    public void setAxiom(String str) {
        this.axiom = str;
    }

    // convert the string of axiom into linked list
    public LinkedList<Symbol> getAxiom() {
        LinkedList<Symbol> axiomToSym = new LinkedList<>();
        for (Character c : this.axiom.toCharArray()) {
            axiomToSym.add(alphabet.get(c));
        }
        return axiomToSym;
    }

    public LinkedList<Symbol> rewrite(Symbol sym) {
        // if there is no rule, return null
        if (!rules.containsKey(sym)) {
            return null;
        }

        // if there is only one rule, return this rule
        if (rules.get(sym).size() == 1) {
            return rules.get(sym).get(0);
        }

        // if there are multiple rules,
        // because of uniform probability of each rule
        // probability for each = 100% / (number of rules)
        int nbRules = rules.get(sym).size();
        // generate random number among 0,1,...,nbRules-1
        // Math.random() generate a number of [0,1)
        int indexOfRule = (int)(Math.random() * nbRules);
        LinkedList<Symbol> ruleChosen = rules.get(sym).get(indexOfRule);
        return ruleChosen;
    }

    // appluRules function: calculate the complete path of a turtle 
    public LinkedList<Symbol> applyRules(LinkedList<Symbol> seq, int n) {
        LinkedList<Symbol> seqAppliedRules;
        if (n == 0) {
            return seq;
        } else {
        	// initialize a new sequence for applied rules 
            seqAppliedRules = new LinkedList<>();
            
            // obtain the sequence from the last round of iteration
            seq = applyRules(seq, n-1);
            
            // based on the sequence rewritten from the last round, 
            // obtain a new sequence for this round 
            // for each symbol of sequence, 
            // if its rewritten expansion returns null, 
            // we can just this symbol into the new sequence
            // if it has a rewritten rule, 
            // we add all the symbols of this rule into the new sequence
            for (int i=0; i<seq.size(); i++) {
                Symbol s = seq.get(i);
                LinkedList<Symbol> re = rewrite(s);
                if (re == null) {
                    seqAppliedRules.add(s);
                } else {
                    seqAppliedRules.addAll(re);
                }
            }
        }
        return seqAppliedRules;
    }


    public void tell(Turtle turtle, Symbol sym) throws FileNotFoundException {
        String action = actions.get(sym);
        if (action.equals("draw")) {
            turtle.draw();
        } else if (action.equals("push")) {
            turtle.push();
        } else if (action.equals("pop")) {
            turtle.pop();
        } else if(action.equals("turnL")) {
            turtle.turnL();
        } else if(action.equals("turnR")) {
            turtle.turnR();
        } else if (action.equals("move")) {
            turtle.move();
        }
    }


    public void tell(Turtle turtle, LinkedList<Symbol> sym, int rounds) throws FileNotFoundException {
        if(rounds == 0) {
            for (Symbol s : sym) {
                tell(turtle, s);
            }
        } else {
            // rewrite every symbol in the sequence
            for (Symbol s : sym) {
                LinkedList<Symbol> re = rewrite(s);
                if (re == null) {
                    // if the rewritten symbol returns null
                    // just call tell function with one symbol
                    tell(turtle, s);
                } else {
                    // if the rewritten symbol returns a sequence
                    // call recursively this tell function
                    tell(turtle, re, rounds-1);
                }
            }
        }
    }


    /* this function is to calculate the estimated boundary box for the eps file 
    * the turtle pass by (x1, y1), … (xm, ym),
    * width = Xmax - Xmin
    * height = Ymax - Ymin
    * */
    public Rectangle2D boundary(LinkedList<Symbol> seq, int n, Turtle turtle) throws FileNotFoundException {
        // get the complete of path the turtle 
    	LinkedList<Symbol> path = applyRules(seq, n);
    	
    	// initialize the min and max of coordinates 
        double minX = 0.0, minY = 0.0;
        double maxX = 0.0, maxY = 0.0;

        for (int i=0; i<path.size(); i++) {
            tell(turtle, path.get(i));
            double curX = turtle.getPosition().getX();
            double curY = turtle.getPosition().getY();

            if (curX > maxX) {
                maxX = curX;
            } else if (curX < minX) {
                minX = curX;
            }

            if (curY > maxY) {
                maxY = curY;
            } else if (curY < minY) {
                minY = curY;
            }
        }

        double w = maxX - minX;
        double h = maxY - minY;
        Rectangle2D boundary = new Rectangle2D.Double(minX, minY, w, h);
        return boundary;
    }
}



