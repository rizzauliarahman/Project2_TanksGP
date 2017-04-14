/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import javafx.animation.TranslateTransition;
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
        ellipse.setFill(Color.BURLYWOOD);
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
        
        while ((!tank.getTank().contains(x_pos, y_pos)) && 
                ((x_pos >= 25 && x_pos <= 1150) && 
                (y_pos >= 25 && y_pos <= 550))) {
            
            ms += 10;
            translate.setDuration(Duration.millis(ms));
            
            double tmp_x = 0, tmp_y = 0;
            
            if (direction == 45) {
                tmp_x -= 5;
                tmp_y -= 5;
            } else if (direction == 90) {
                tmp_y -= 5;
            } else if (direction == 135) {
                tmp_x += 5;
                tmp_y -= 5;
            } else if (direction == 180) {
                tmp_x += 5;
            } else if (direction == 225) {
                tmp_x += 5;
                tmp_y += 5;
            } else if (direction == 270) {
                tmp_y += 5;
            } else if (direction == 315) {
                tmp_x -= 5;
                tmp_x += 5;
            } else if (direction == 0) {
                tmp_x -= 5;
            }
            
            dx += tmp_x;
            dy += tmp_y;
            
            this.x_pos += tmp_x;
            this.y_pos += tmp_y;
        }
        
        translate.setByX(dx);
        translate.setByY(dy);        
        
        if (tank.getTank().contains(x_pos, y_pos)) {
            tank.getHit(power);
        }
        
        return translate;
    }
    
}
