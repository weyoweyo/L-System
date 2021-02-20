# L-System

/*
Author: Weiyue Cai
Date: Feb 20, 2021
*/


1. to execute the jar file,  
java -jar lindenmayer.jar test/buisson.json 4 test2.eps

2. run MainEps in the java project,  
args[0]: path of json file, for example, test/buisson.json  
args[1]: the number of iterations  
args[2]: name of output file  
test/sier.json 7 testsier7.eps

3. all output eps files will be under project folder (L-System) directly. I created manually a result folder to put all the results.  

4. We can also run the following code to show image in Java Swing,  
but sometimes we can not get the result because of boundary and scale issues.  
Frame ui = new Frame();  
Panel panel = new Panel();  
ui.add(panel);  
Draw pen = new Draw();  
pen.setCanvas(panel);  
readJSON.readJSONFile(args[0], system, pen, turtleEps);  
system.tell(pen, system.getAxiom(), n);  

5. for LSystem class, my original idea is to use iterator, but my program gives ConcurrentModificationException errors.  
So I decided to use LinkedList to handle all the symbols. 
