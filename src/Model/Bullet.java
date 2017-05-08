/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import javafx.animation.TranslateTransition;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.util.Duration;

/**
 *
 * @author asprak101
 */
public class Bullet {
    private Ellipse ellipse; // The shape of the bullet    
    // init_x and init_y = the initial position of the bullet
    // dx and dy = the position change of the bullet
    // x_pos and y_pos = the current position of the bullet
    private double init_x, init_y, dx = 0, dy = 0, x_pos, y_pos;
    private int power; // the power of the bullet
    
    
    // Constructor to create new bullet shape
    public Bullet(double x_pos, double y_pos, double direction) {
        
        // Create new ellipse as the bullet, set
        // the position based on the tank gun position
        // and set its fill color
        ellipse = new Ellipse(x_pos, y_pos, 5, 2.5);
        ellipse.setFill(Color.ORANGERED);
        
        // Initialize the initial position and
        // current position of the bullet by
        // the tank gun position
        init_x = this.x_pos = x_pos;
        init_y = this.y_pos = y_pos;
        
        power = 12; // Set the power of the bullet
        
        // Set the direction of the bullet by the direction
        // of the tank and show it
        ellipse.setRotate(direction);
        ellipse.setVisible(true);
        
    }
    
    // Return the bullet
    public Ellipse getBullet() {
        return ellipse;
    }
    
    // Hide the bullet
    public void dispose() {
        ellipse.setVisible(false);
    }
    
    // @method to generate bullet animation
    public TranslateTransition walk(double direction, Tank tank) {
        
        // Create new TranslateTransition to contain the bullet
        // movement animation
        TranslateTransition translate = new TranslateTransition();
        double ms = 0; // Duration of the movement
        
        // Set the position change of the bullet to 0
        // and set the Node of TranslateTransition object
        // to bullet object
        dx = dy = 0;
        translate.setNode(ellipse);
        
        // While the bullet isn't hit the tank and doesn't
        // exceed the boundary, keep moving
        while ((!checkHit(tank)) && 
                ((x_pos >= 0 && x_pos <= 1200) && 
                (y_pos >= 0 && y_pos <= 600))) {
            
            // Increase the movement duration
            ms += 0.5;
            
            // Set the duration of the movement
            translate.setDuration(Duration.millis(ms));
            
            // tmp_x and tmp_y = to contain the position change
            // on each loop
            double tmp_x = 0, tmp_y = 0;
            
            // If the bullet direction is to the top, move
            // the bullet y position upward by 1
            if (direction == 90) {
                tmp_y -= 1;
            
            // If the bullet direction is to the right, move
            // the bullet x position to the right by 1
            } else if (direction == 180) {
                tmp_x += 1;
                
            // If the bullet direction is to the bottom, move
            // the bullet y position downward by 1
            } else if (direction == 270) {
                tmp_y += 1;
                
            // If the bullet direction is to the left, move
            // the bullet x position to the left by 1
            } else if (direction == 0) {
                tmp_x -= 1;
            }
            
            // Change the position's change of the bullet
            // by tmp_x and tmp_y
            dx += tmp_x;
            dy += tmp_y;
            
            // Change the position of the bullet by
            // tmp_x and tmp_y
            this.x_pos += tmp_x;
            this.y_pos += tmp_y;
        }
        
        // Set the movement of the bullet in the animation
        // by dx and dy
        translate.setByX(dx);
        translate.setByY(dy);        
        
        // Check if bullet hits the tank
        if (checkHit(tank)) {

            // Call @method getHit in tank object
            tank.getHit(power);
            
//            // If the tank health is less than 0 when
//            // hit by the bullet, destroy the tank
//            if (tank.getHealth() <= 0) {
//                tank.destroy();
//            }
        }
        
        // Return the generated animation
        return translate;
    }
    
    // Check if the bullet hits the tank
    public boolean checkHit(Tank tank) {
        
        // Check if the boundary of the tank intersect with
        // the boundary of the bullet
        if (tank.getBoundary().intersects(this.getBoundary())) {
                return true;    
        }
        
        // Return false if those boundary doesn't intersect
        return false;
    }
    
    // Get the rectangle that bound the bullet
    public Rectangle2D getBoundary() {
        return new Rectangle2D(x_pos, y_pos, 5, 5);
    }
    
}
