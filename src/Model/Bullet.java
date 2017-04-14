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
    Ellipse ellipse;
    double init_x, init_y, dx = 0, dy = 0, x_pos, y_pos;
    int power;
    
    public Bullet(double x_pos, double y_pos, double direction) {
        ellipse = new Ellipse(x_pos, y_pos, 5, 2.5);
        ellipse.setFill(Color.ORANGERED);
        init_x = this.x_pos = x_pos;
        init_y = this.y_pos = y_pos;
        
        power = 12;
        
        ellipse.setRotate(direction);
        ellipse.setVisible(true);
        
    }
    
    public Ellipse getBullet() {
        return ellipse;
    }
    
    public void dispose() {
        ellipse.setVisible(false);
    }
    
    public TranslateTransition walk(double direction, Tank tank) {
        TranslateTransition translate = new TranslateTransition();
        double ms = 0;
        dx = dy = 0;
        translate.setNode(ellipse);
        
        while ((!checkHit(tank)) && 
                ((x_pos >= 0 && x_pos <= 1200) && 
                (y_pos >= 0 && y_pos <= 600))) {
            
            ms += 0.5;
            translate.setDuration(Duration.millis(ms));
            
            double tmp_x = 0, tmp_y = 0;
            
            if (direction == 45) {
                tmp_x -= 1;
                tmp_y -= 1;
            } else if (direction == 90) {
                tmp_y -= 1;
            } else if (direction == 135) {
                tmp_x += 1;
                tmp_y -= 1;
            } else if (direction == 180) {
                tmp_x += 1;
            } else if (direction == 225) {
                tmp_x += 1;
                tmp_y += 1;
            } else if (direction == 270) {
                tmp_y += 1;
            } else if (direction == 315) {
                tmp_x -= 1;
                tmp_x += 1;
            } else if (direction == 0) {
                tmp_x -= 1;
            }
            
            dx += tmp_x;
            dy += tmp_y;
            
            this.x_pos += tmp_x;
            this.y_pos += tmp_y;
        }
        
        translate.setByX(dx);
        translate.setByY(dy);        
        
        if (checkHit(tank)) {
//            System.out.print("Hit ");
            tank.getHit(power);
        }
        
        return translate;
    }
    
    public boolean checkHit(Tank tank) {
        if (tank.getBoundary().intersects(this.getBoundary())) {
                return true;    
        }
        return false;
    }
    
    public Rectangle2D getBoundary() {
        return new Rectangle2D(x_pos, y_pos, 5, 5);
    }
    
}
