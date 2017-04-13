/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import javafx.animation.TranslateTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Ellipse;
import javafx.util.Duration;

/**
 *
 * @author asprak101
 */
public class Bullet {
    Ellipse ellipse;
    double init_x, init_y, dx = 0, dy = 0, x_pos, y_pos;
    
    public Bullet(double x_pos, double y_pos, double direction) {
        ellipse = new Ellipse(x_pos, y_pos, 8, 16);
        init_x = this.x_pos = x_pos;
        init_y = this.y_pos = y_pos;
        
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
        TranslateTransition translate = new TranslateTransition(Duration.millis(1000));
        translate.setNode(ellipse);
        
        while (!tank.getTank().contains(x_pos, y_pos)) {
            
        }
        return translate;
    }
    
}
