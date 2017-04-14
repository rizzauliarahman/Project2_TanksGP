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
    Image image;
    ImageView object;
    double init_dir, current_dir;
    double init_x, init_y, dx = 0, dy = 0, x_pos, y_pos;
    double gun_x, gun_y;
    int health;
    Bullet bulletObj;
    
    public Tank(String filename, String type) throws Exception {
        image = new Image(new FileInputStream(filename));
        object = new ImageView(image);
        object.setFitHeight(100);
        object.setFitWidth(50);
        
        if (type.toUpperCase().equals("PLAYER")) {
            object.setRotate(180);
            object.setX(50);
            object.setY(50);
            gun_x = object.getX() + 50;
            gun_y = object.getY() + 15;
        } else if (type.toUpperCase().equals("ENEMY")) {
            object.setX(1100);
            object.setY(50);
            gun_x = object.getX() - 50;
            gun_y = object.getY() - 15;
        }
        System.out.println(object.getX() + ", " + object.getY() + ", " + object.getRotate());
        
        init_dir = current_dir = object.getRotate();
        init_x = x_pos = object.getX();
        init_y = y_pos = object.getY();
        health = 100;
        
        object.setPreserveRatio(true);
        
    }
    
    public ImageView getTank() {
        return object;
    }
    
    public TranslateTransition walk() {
        TranslateTransition translate = new TranslateTransition(Duration.millis(1000));
        translate.setNode(object);
        
        dx = dy = 0;
        
        if ((this.x_pos >= 25 && this.x_pos <= 1150) &&
                (this.y_pos >= 25 && this.y_pos <= 550)) {
            
            if (current_dir == 90) {
                translate.setByY(-50);
                dy -= 50;
            } else if (current_dir == 180) {
                translate.setByX(50);
                dx += 50;
            } else if (current_dir == 270) {
                translate.setByY(50);
                dy += 50;
            } else if (current_dir == 0) {
                translate.setByX(-50);
                dx -= 50;
            }
            
            this.x_pos += dx;
            this.y_pos += dy;
            this.gun_x += dx;
            this.gun_y += dy;
            
            System.out.println(this.x_pos + ", " + this.y_pos + ", " + this.current_dir);
            
        } else {
            if (this.x_pos < 25) {
                if (this.current_dir == 0) {
                    return back();
                }                    
            } else if (this.x_pos > 1150) {
                if (this.current_dir == 180) {
                    return back();
                }
            } else if (this.y_pos < 25) {
                if (this.current_dir == 90) {
                    return back();
                }
            } else if (this.y_pos > 550) {
                if (this.current_dir == 270) {
                    return back();
                }
            }
            
        }
        
        return translate;
    }
    
    public RotateTransition turnRight() {
        RotateTransition rotate = new RotateTransition(Duration.millis(1000));
        rotate.setNode(object);
        
        rotate.setByAngle(90);
        current_dir += 90;
        
        if (current_dir >= 360) {
            current_dir -= 360;
        }
        
        if (current_dir == 270) {
            gun_x -= 25;
            gun_y += 25;
        } else if (current_dir == 0) {
            gun_x -= 25;
            gun_y -= 25;
        } else if (current_dir == 90) {
            gun_x += 25;
            gun_y -= 25;
        } else if (current_dir == 180) {
            gun_x += 25;
            gun_y += 25;
        }
        
        return rotate;
    }
    
    public RotateTransition turnLeft() {
        RotateTransition rotate = new RotateTransition(Duration.millis(1000));
        rotate.setNode(object);
        
        rotate.setByAngle(-90);
        current_dir -= 90;
        
        if (current_dir < 0) {
            current_dir += 360;
        }
        
        if (current_dir == 270) {
            gun_x += 25;
            gun_y += 25;
        } else if (current_dir == 0) {
            gun_x -= 25;
            gun_y += 25;
        } else if (current_dir == 90) {
            gun_x -= 25;
            gun_y -= 25;
        } else if (current_dir == 180) {
            gun_x += 25;
            gun_y -= 25;
        }
        
        return rotate;
    }
    
    public TranslateTransition back() {
        TranslateTransition translate = new TranslateTransition(Duration.millis(1000));
        translate.setNode(object);
        
        dx = dy = 0;
        
        if (current_dir == 90) {
            translate.setByY(50);
            dy += 50;
        } else if (current_dir == 180) {
            translate.setByX(-50);
            dx -= 50;
        } else if (current_dir == 270) {
            translate.setByY(-50);
            dy -= 50;
        } else if (current_dir == 0) {
            translate.setByX(50);
            dx += 50;
        }

        this.x_pos += dx;
        this.y_pos += dy;
        this.gun_x += dx;
        this.gun_y += dy;

        System.out.println(this.x_pos + ", " + this.y_pos + ", " + this.current_dir);
        
        return translate;
        
    }
    
    public void getHit(int power) {
        health -= power + new Random().nextInt(11);
        if (health < 0) {
            health = 0;
        }
    }
    
    public TranslateTransition fire(Tank tank) {
        bulletObj = new Bullet(gun_x, gun_y, current_dir);
        TranslateTransition translate = bulletObj.walk(current_dir, tank);
        
        return translate;
    }
    
    public Bullet getBullet() {
        return bulletObj;
    }
    
    public int getHealth() {
        return health;
    }
    
}
