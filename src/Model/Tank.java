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
    double initDirection;
    double[] initPos = new double[2];
    Path bound;
    double init_x, init_y, dx, dy, x_pos, y_pos;
    
    public Tank(String filename, String type, Path bound) throws Exception {
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
        
        initDirection = object.getRotate();
        initPos[0] = object.getX();
        initPos[1] = object.getY();
        
        this.bound = bound;
        
        object.setPreserveRatio(true);
        
    }
    
    public ImageView getTank() {
        return object;
    }
    
    public TranslateTransition walk() {
        TranslateTransition translate = new TranslateTransition(Duration.millis(1000));
        translate.setNode(object);
        
        if ((object.getX() >= 25 && object.getX() <= 1150) &&
                (object.getY() >= 25 && object.getY() <= 550)) {
            
            if (object.getRotate() == 45) {
                translate.setByX(-30);
                translate.setByY(-30);
                object.setX(object.getX() - 30);
                object.setY(object.getY() - 30);
            } else if (object.getRotate() == 90) {
                translate.setByY(-50);
                object.setY(object.getY() - 50);
            } else if (object.getRotate() == 135) {
                translate.setByX(30);
                translate.setByY(-30);
                object.setX(object.getX() + 30);
                object.setY(object.getY() - 30);
            } else if (object.getRotate() == 180) {
                translate.setByX(-50);
                object.setX(object.getX() - 50);
            } else if (object.getRotate() == 225) {
                translate.setByX(30);
                translate.setByY(30);
                object.setX(object.getX() + 30);
                object.setY(object.getY() + 30);
            } else if (object.getRotate() == 270) {
                translate.setByY(50);
                object.setY(object.getY() + 50);
            } else if (object.getRotate() == 315) {
                translate.setByX(-30);
                translate.setByY(30);
                object.setX(object.getX() - 30);
                object.setY(object.getY() + 30);
            } else if (object.getRotate() == 0) {
                translate.setByX(50);
                object.setX(object.getX() + 50);
            }
            System.out.println(object.getX() + ", " + object.getY() + ", " + object.getRotate());
            
        } else {
            RotateTransition rotate = new RotateTransition(Duration.millis(1000));
            System.out.println("a");
            rotate.setNode(object);
            rotate.setToAngle(initDirection);
            rotate.play();
            
            translate.setToX(initPos[0]);
            translate.setToY(initPos[1]);
        }
        
        return translate;
    }
    
    public RotateTransition turnRight() {
        RotateTransition rotate = new RotateTransition(Duration.millis(1000));
        rotate.setNode(object);
        
        rotate.setByAngle(45);
        
        return rotate;
    }
    
    public RotateTransition turnLeft() {
        RotateTransition rotate = new RotateTransition(Duration.millis(1000));
        rotate.setNode(object);
        
        rotate.setByAngle(-45);
        
        return rotate;
    }
    
}
