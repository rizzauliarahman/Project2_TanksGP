/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Path;
import javafx.scene.shape.Shape;
import javafx.util.Duration;

/**
 *
 * @author rizzauliarahman
 */
public class Tank {
    Image image;
    ImageView object;
    double init_dir, current_dir;
    double init_x, init_y, dx = 0, dy = 0, x_pos, y_pos;
    
    public Tank(String filename, String type) throws Exception {
        image = new Image(new FileInputStream(filename));
        object = new ImageView(image);
        object.setFitHeight(100);
        object.setFitWidth(50);
        
        if (type.toUpperCase().equals("PLAYER")) {
            object.setRotate(180);
            object.setX(50);
            object.setY(50);
        } else if (type.toUpperCase().equals("ENEMY")) {
            object.setX(1100);
            object.setY(500);
        }
        System.out.println(object.getX() + ", " + object.getY() + ", " + object.getRotate());
        
        init_dir = current_dir = object.getRotate();
        init_x = x_pos = object.getX();
        init_y = y_pos = object.getY();
        
        object.setPreserveRatio(true);
        
    }
    
    public ImageView getTank() {
        return object;
    }
    
    public TranslateTransition walk() {
        TranslateTransition translate = new TranslateTransition(Duration.millis(1000));
        translate.setNode(object);
        
        if ((this.x_pos >= 25 && this.x_pos <= 1150) &&
                (this.y_pos >= 25 && this.y_pos <= 550)) {
            
            if (current_dir == 45) {
                translate.setByX(-30);
                translate.setByY(-30);
                dx -= 30;
                dy -= 30;
            } else if (current_dir == 90) {
                translate.setByY(-50);
                dy -= 50;
            } else if (current_dir == 135) {
                translate.setByX(30);
                translate.setByY(-30);
                dx += 30;
                dy -= 30;
            } else if (current_dir == 180) {
                translate.setByX(-50);
                dx -= 50;
            } else if (current_dir == 225) {
                translate.setByX(30);
                translate.setByY(30);
                dx += 30;
                dy += 30;
            } else if (current_dir == 270) {
                translate.setByY(50);
                dy += 50;
            } else if (current_dir == 315) {
                translate.setByX(-30);
                translate.setByY(30);
                dx -= 30;
                dx += 30;
            } else if (current_dir == 0) {
                translate.setByX(50);
                dx += 50;
            }
            
            this.x_pos += dx;
            this.y_pos += dy;
            
            
            System.out.println(this.x_pos + ", " + this.y_pos + ", " + this.current_dir);
            
        } else {
            translate = this.back();
            
        }
        
        return translate;
    }
    
    public RotateTransition turnRight() {
        RotateTransition rotate = new RotateTransition(Duration.millis(1000));
        rotate.setNode(object);
        
        rotate.setByAngle(45);
        current_dir += 45;
        
        return rotate;
    }
    
    public RotateTransition turnLeft() {
        RotateTransition rotate = new RotateTransition(Duration.millis(1000));
        rotate.setNode(object);
        
        rotate.setByAngle(-45);
        current_dir -= 45;
        
        return rotate;
    }
    
    public TranslateTransition back() {
        TranslateTransition translate = new TranslateTransition(Duration.millis(1000));
        translate.setNode(object);
        
        if (current_dir == 45) {
            translate.setByX(30);
            translate.setByY(30);
            dx += 30;
            dy += 30;
        } else if (current_dir == 90) {
            translate.setByY(50);
            dy += 50;
        } else if (current_dir == 135) {
            translate.setByX(-30);
            translate.setByY(30);
            dx -= 30;
            dy += 30;
        } else if (current_dir == 180) {
            translate.setByX(50);
            dx += 50;
        } else if (current_dir == 225) {
            translate.setByX(-30);
            translate.setByY(-30);
            dx -= 30;
            dy -= 30;
        } else if (current_dir == 270) {
            translate.setByY(-50);
            dy -= 50;
        } else if (current_dir == 315) {
            translate.setByX(30);
            translate.setByY(-30);
            dx += 30;
            dx -= 30;
        } else if (current_dir == 0) {
            translate.setByX(-50);
            dx -= 50;
        }

        this.x_pos += dx;
        this.y_pos += dy;


        System.out.println(this.x_pos + ", " + this.y_pos + ", " + this.current_dir);
        
        return translate;
        
    }
    
    
}
