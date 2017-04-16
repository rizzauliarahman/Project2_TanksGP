/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;

/**
 *
 * @author rizzauliarahman
 */
public class Bounds {
    private Path path; // Line that will be the arena boundary
    
    
    // Constructor to create new rectangular line
    public Bounds() {
        path = new Path(); // Create new path object
        
        // Initialize the initial point of the line
        MoveTo moveTo = new MoveTo(0, 0);
        
        // Create the first line
        LineTo line1 = new LineTo(0, 600);
        
        // Create the second line
        LineTo line2 = new LineTo(1200, 600);
        
        // Create the third line
        LineTo line3 = new LineTo(1200, 0);
        
        // Create the fourth line
        LineTo line4 = new LineTo(0, 0);
        
        // Add the path initial point
        path.getElements().add(moveTo);
        
        // Add all the line that has been created to
        // the path
        path.getElements().addAll(line1, line2, line3, line4);
    }
    
    // Return the path that has been created
    public Path getBounds() {
        return path;
    }
    
}
