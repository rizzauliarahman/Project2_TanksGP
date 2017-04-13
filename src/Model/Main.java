/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import javafx.animation.Animation;
import javafx.animation.PauseTransition;
import javafx.animation.RotateTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Path;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author rizzauliarahman
 */
public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        
        List<Animation> listAnim = new ArrayList<>();
        Path bound = new Bounds().getBounds();
        Tank player = new Tank(".\\sprite\\Tank-GTAA-1.png", "player", bound);
        Tank enemy = new Tank(".\\sprite\\Tank-GTAA-1.png", "enemy", bound);
        
        RotateTransition rotate1 = player.turnLeft();
        RotateTransition rotate2 = player.turnLeft();
        RotateTransition rotate3 = player.turnLeft();
        RotateTransition rotate4 = player.turnLeft();
        TranslateTransition translate1 = player.walk();
        TranslateTransition translate2 = player.walk();
        listAnim.add(rotate1);
        listAnim.add(rotate2);
        listAnim.add(rotate3);
        listAnim.add(rotate4);
        listAnim.add(translate1);
        listAnim.add(translate2);
        
        Animation[] anim = new Animation[listAnim.size()];
        anim = listAnim.toArray(anim);
        
        SequentialTransition seq = new SequentialTransition(anim);
        seq.play();
        
        ImageView playerObj = player.getTank();
        ImageView enemyObj = enemy.getTank();
        
        Group root = new Group(playerObj, enemyObj, bound);
        
        Scene scene = new Scene(root, 1200, 600);
        
        stage.setTitle("Load Tank");
        
        stage.setScene(scene);
        
        stage.show();
        
    }
    
    public synchronized void waitAnim() throws InterruptedException {
        wait(1000);
    }
    
    public static void main(String[] args) {
        launch(args);
    }
    
}
