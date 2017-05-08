/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Random;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.geometry.Rectangle2D;
import javafx.scene.effect.SepiaTone;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Path;
import javafx.scene.shape.Shape;
import javafx.util.Duration;

/**
 *
 * @author rizzauliarahman
 */
public class Tank {
    private Image image; // Tank image loaded from the file
    private ImageView object; // Visualization of the image
    // init_dir = the initial direction of the tank
    // current_dir = the current direction of the tank
    private double init_dir, current_dir;
    // init_x and init_y = the initial position of the tank
    // dx and dy = the position change of the tank
    // x_pos and y_pos = the current position of the tank
    private double init_x, init_y, dx = 0, dy = 0, x_pos, y_pos;
    // gun_x and gun_y = position of the tanks gun
    private double gun_x, gun_y;
    private int health; // the tank's health
    private Bullet bulletObj; // the image visualization of the tank's bullet
    private String name; // the name of the tanks
    
    
    // The constructor of tank object
    public Tank(String filename, String type) throws Exception {
        
        // Load the tank's image file into @attribute image
        image = new Image(new FileInputStream(filename));
        
        // Create the visualization of the tank
        // and set its height and width
        object = new ImageView(image);
        object.setFitHeight(100);
        object.setFitWidth(50);
        
        // If the tank type is player, set the direction to
        // the right side and position to (200, 200), and
        // initial gun position to (250, 215)
        if (type.toUpperCase().equals("PLAYER")) {
            object.setRotate(180);
            object.setX(400);
            object.setY(200);
            gun_x = object.getX() + 50;
            gun_y = object.getY() + 15;
            
        // else if the tank type is enemy, set the direction to
        // the left, and the position to (1000, 200), and initial
        // gun position to (1000, 215)
        // Set the Sepia effect to the tank to distinguish it from
        // player's tank
        } else if (type.toUpperCase().equals("ENEMY")) {
            object.setX(800);
            object.setY(300);
            gun_x = object.getX();
            gun_y = object.getY() + 15;
            object.setEffect(new SepiaTone(0.5));
        }
        
        // Initialize init_dir, current_dir to the
        // direction of tank
        init_dir = current_dir = object.getRotate();
        
        // Initialize, init_x, init_y, x_pos, and y_pos
        // with the initial position of the tank
        init_x = x_pos = object.getX();
        init_y = y_pos = object.getY();
        
        // Initialize the amount of tank's health and
        // the tank's name
        health = 100;
        name = type;
        
        // Fix the ratio of tank's image
        object.setPreserveRatio(true);
        
    }
    
    // Return the tank's visualization
    public ImageView getTank() {
        return object;
    }
    
    // @method to generate tank's walking animation
    public TranslateTransition walk() {
        
        // Create new TranslateTransition object to contain
        // the movement of the tank, and set the Node of this object
        // to the tank
        TranslateTransition translate = new TranslateTransition(Duration.millis(250));
        translate.setNode(object);
        
        // Initialize the position change of tank with 0
        dx = dy = 0;
        
        // Check if the tank position is less than the
        // boundary position that has been defined
        if ((this.x_pos >= 100 && this.x_pos <= 1100) &&
                (this.y_pos >= 75 && this.y_pos <= 525)) {
            
            // If the direction is up, move the y coordinate
            // upward by 50
            if (current_dir == 90) {
                translate.setByY(-50);
                dy -= 50;
                
            // If the direction is right, move the x coordinate
            // to the right by 50
            } else if (current_dir == 180) {
                translate.setByX(50);
                dx += 50;
                
            // If the direction is down, move the y coordinate
            // downward by 50
            } else if (current_dir == 270) {
                translate.setByY(50);
                dy += 50;
            
            // If the direction is left, move the x coordinate
            // to the left by 50
            } else if (current_dir == 0) {
                translate.setByX(-50);
                dx -= 50;
            }
            
            // change the x_pos, y_pos by dx and dy
            // change the position of gun by dx and dy
            this.x_pos += dx;
            this.y_pos += dy;
            this.gun_x += dx;
            this.gun_y += dy;
            
        // If the tank position is outside the boundary
        } else {
            
            // If the tank position is outside the
            // left boundary and its direction is to
            // the left, then move the tank backward
            if (this.x_pos < 100) {
                if (this.current_dir == 0) {
                    return back();
                }                    
            
            // If the tank position is outside the
            // right boundary and its direction is to
            // the right, then move the tank backward    
            } else if (this.x_pos > 1100) {
                if (this.current_dir == 180) {
                    return back();
                }
            
            // If the tank position is outside the
            // top boundary and its direction is to
            // the top, then move the tank backward
            } else if (this.y_pos < 75) {
                if (this.current_dir == 90) {
                    return back();
                }
            
            // If the tank position is outside the
            // bottom boundary and its direction is to
            // the bottom, then move the tank backward
            } else if (this.y_pos > 525) {
                if (this.current_dir == 270) {
                    return back();
                }
            }
            
        }
        
        // return the animation generated by the previous
        // condition
        return translate;
    }
    
    // @method to generate the tank's right turning
    // (rotating) animation
    public RotateTransition turnRight() {
        
        // Create new RotateTransition object to contain
        // the rotation of the tank, and set the Node of this object
        // to the tank
        RotateTransition rotate = new RotateTransition(Duration.millis(250));
        rotate.setNode(object);
        
        // Rotate the tank by 90 degrees, and change its
        // direction by 90
        rotate.setByAngle(90);
        current_dir += 90;
        
        // If current_direction is more than 360, set
        // it to 0
        if (current_dir >= 360) {
            current_dir -= 360;
        }
        
        // If the current direction is to the bottom, change the
        // position of the gun by (-25,25)
        if (current_dir == 270) {
            gun_x -= 25;
            gun_y += 25;
            
        // If the current direction is to the left, change the
        // position of the gun by (-25,-25)
        } else if (current_dir == 0) {
            gun_x -= 25;
            gun_y -= 25;
            
        // If the current direction is to the top, change the
        // position of the gun by (25,-25)    
        } else if (current_dir == 90) {
            gun_x += 25;
            gun_y -= 25;
            
        // If the current direction is to the right, change the
        // position of the gun by (25,25)    
        } else if (current_dir == 180) {
            gun_x += 25;
            gun_y += 25;
        }
        
        // return the rotation animation that has been generated
        return rotate;
    }
    
    // @method to generate the tank's right turning
    // (rotating) animation
    public RotateTransition turnLeft() {
        
        // Create new RotateTransition object to contain
        // the rotation of the tank, and set the Node of this object
        // to the tank
        RotateTransition rotate = new RotateTransition(Duration.millis(250));
        rotate.setNode(object);
        
        // Rotate the tank by -90 degrees, and change its
        // direction by -90
        rotate.setByAngle(-90);
        current_dir -= 90;
        
        // If current_direction is less than 0, set
        // it to current_direction + 360
        if (current_dir < 0) {
            current_dir += 360;
        }
        
        // If the current direction is to the bottom, change the
        // position of the gun by (25,25)
        if (current_dir == 270) {
            gun_x += 25;
            gun_y += 25;
        
        // If the current direction is to the left, change the
        // position of the gun by (-25,25)
        } else if (current_dir == 0) {
            gun_x -= 25;
            gun_y += 25;
        
        // If the current direction is to the top, change the
        // position of the gun by (-25,-25)
        } else if (current_dir == 90) {
            gun_x -= 25;
            gun_y -= 25;
        
        // If the current direction is to the right, change the
        // position of the gun by (25,-25)
        } else if (current_dir == 180) {
            gun_x += 25;
            gun_y -= 25;
        }
        
        // return the rotation animation that has been generated
        return rotate;
    }
    
    // @method to generate tank's backward movement animation
    public TranslateTransition back() {
        
        // Create new TranslateTransition object to contain
        // the movement of the tank, and set the Node of this object
        // to the tank
        TranslateTransition translate = new TranslateTransition(Duration.millis(250));
        translate.setNode(object);
        
        // Set the position change of the tank to 0
        dx = dy = 0;
        
        // If tank currently facing upward, move the tank
        // y coordinate downward by 50
        if (current_dir == 90) {
            translate.setByY(50);
            dy += 50;
        
        // If tank currently facing rightward, move the tank
        // x coordinate to the left by 50
        } else if (current_dir == 180) {
            translate.setByX(-50);
            dx -= 50;
        
        // If tank currently facing downward, move the tank
        // y coordinate upward by 50
        } else if (current_dir == 270) {
            translate.setByY(-50);
            dy -= 50;
        
        // If tank currently facing leftward, move the tank
        // x coordinate to the right by 50
        } else if (current_dir == 0) {
            translate.setByX(50);
            dx += 50;
        }

        // change the x_pos and y_pos by dx and dy
        // change the position of tank's gun by dx and dy
        this.x_pos += dx;
        this.y_pos += dy;
        this.gun_x += dx;
        this.gun_y += dy;
        
        // return the animation that has been generation
        return translate;
        
    }
    
    // @method to be called when the tank got hit by bullet
    public void getHit(int power) {
        
        // subtract the tank health by the bullet power added
        // by the random number from 0-10
        health -= power + new Random().nextInt(11);
        
        // If health is less than 0, set it to 0
        if (health < 0) {
            health = 0;
        }
    }
    
    // @method to be called when tank's firing a bullet
    public TranslateTransition fire(Tank tank) {
        
        // Create new bullet object
        bulletObj = new Bullet(gun_x, gun_y, current_dir);
        
        // Get the bullet animation generated by @method walk
        // in bulletObj (bullet object)
        TranslateTransition translate = bulletObj.walk(current_dir, tank);
        
        // return the generated animation
        return translate;
    }
    
    // return the tank's bullet object
    public Bullet getBullet() {
        return bulletObj;
    }
    
    // return the tank's health
    public int getHealth() {
        return health;
    }
    
    // return the tank's name
    public String getName() {
        return name;
    }
    
    // return the x position of the tank
    public double getXPos() {
        return this.x_pos;
    }
    
    // return the y position of the tank
    public double getYPos() {
        return this.y_pos;
    }
    
    // return the rectangle that bound the tank's
    // image
    public Rectangle2D getBoundary() {
        return new Rectangle2D(x_pos, y_pos, 50, 50);
    }
    
    public boolean facingEnemy(Tank tank) {
        Rectangle2D radar;
        
        if (this.current_dir == 0) {
            radar = new Rectangle2D(0, y_pos, x_pos, 50);
        } else if (this.current_dir == 90) {
            radar = new Rectangle2D(x_pos, 0, 50, y_pos);
        } else if (this.current_dir == 180) {
            radar = new Rectangle2D(x_pos, y_pos, (1200-x_pos), 50);
        } else {
            radar = new Rectangle2D(x_pos, y_pos, 50, (600-y_pos));
        }
        
        if ((radar.intersects(tank.getBoundary()))) {
            return true;
        } else {
            return false;
        }
    }
    
    // Hide the tanks
    public void destroy() {
        object.setVisible(false);
    }
    
    public double getDirection() {
        return current_dir;
    }
    
}
