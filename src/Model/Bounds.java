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
    Path path;
    
    public Bounds() {
        path = new Path();
        
        MoveTo moveTo = new MoveTo(0, 0);
        
        LineTo line1 = new LineTo(0, 600);
        
        LineTo line2 = new LineTo(1200, 600);
        
        LineTo line3 = new LineTo(1200, 0);
        
        LineTo line4 = new LineTo(0, 0);
        
        path.getElements().add(moveTo);
        path.getElements().addAll(line1, line2, line3, line4);
    }
    
    public Path getBounds() {
        return path;
    }
    
}
