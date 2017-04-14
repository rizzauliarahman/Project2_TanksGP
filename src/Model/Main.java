/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.RotateTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Path;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author rizzauliarahman
 */
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Population pop = new Population();
        pop.createPopulation();
        GeneticAlgorithm GA = new GeneticAlgorithm();
        int i = 0;
        
        boolean terminate = false;    
        List<Double> fittestList = new ArrayList<>();
        
        while (!terminate) {
            
            Path bound = new Bounds().getBounds();
            RotateTransition boundColl = new RotateTransition();
            Tank player = new Tank(".\\sprite\\Tank-GTAA-1.png", "player");
            Tank enemy = new Tank(".\\sprite\\Tank-GTAA-1.png", "enemy");
            Stage stage = new Stage();
            List<Ellipse> listBullet = new ArrayList<>();
            
            int j = i+1;
            
            double evol = new Random().nextDouble();
            
            if (evol < GA.getCrossoverRate()) {
                GA.survivorSelection(GA.crossover(pop), pop);
            } else {
                int idx = new Random().nextInt(pop.getPopulation().length);
                Chromosome mutant = pop.getPopulation()[idx];
                GA.mutation(mutant);
                pop.getPopulation()[idx] = mutant;
            }
            
            Chromosome c = pop.getFittest();
            
            fittestList.add(c.getFitness());
            Double[] tmp = new Double[fittestList.size()];
            tmp = fittestList.toArray(tmp);
            
            terminate = GA.termination(tmp);

            List<Animation> listAnim = new ArrayList<>();
            int factor = 0;

//            do {

                factor++;
                for (String s : c.getGenes()) {
                    if (s.equals("walk")) {
                        TranslateTransition translate = player.walk();
                        listAnim.add(translate);
                    } else if (s.equals("turnRight")) {
                        RotateTransition rotate = player.turnRight();
                        listAnim.add(rotate);
                    } else if (s.equals("turnLeft")) {
                        RotateTransition rotate = player.turnLeft();
                        listAnim.add(rotate);
                    } else if (s.equals("fire")) {
                        TranslateTransition translate = player.fire(enemy);
                        
                        Ellipse bullet = player.getBullet().getBullet();
                        listBullet.add(bullet);

                        FadeTransition fade1 = new FadeTransition(Duration.millis(10), player.getBullet().getBullet());
                        fade1.setFromValue(0);
                        fade1.setToValue(1);
                        fade1.setCycleCount(1);

                        FadeTransition fade2 = new FadeTransition(Duration.millis(10), player.getBullet().getBullet());
                        fade2.setFromValue(1);
                        fade2.setToValue(0);
                        fade2.setCycleCount(1);

                        listAnim.add(fade1);
                        listAnim.add(translate);
                        listAnim.add(fade2);
                    }
                    System.out.println(s);
                }

//            } while ((player.getHealth() != 0) && (enemy.getHealth() != 0));
            
            Text text = new Text(10, 580, "Generation - " + j + ", Best Fitness : " + c.getFitness() * factor);
            text.setFont(Font.font("Verdana", 20));
            
            PauseTransition delay = new PauseTransition(Duration.millis(2000));
            delay.setOnFinished(event -> stage.close());
            listAnim.add(delay);
            
            Animation[] anim = new Animation[listAnim.size()];
            anim = listAnim.toArray(anim);
            
            SequentialTransition seq = new SequentialTransition(anim);
            seq.play();
            
            i++;
            
            terminate = true;
            
            ImageView playerObj = player.getTank();
            ImageView enemyObj = enemy.getTank();

            Group root = new Group(playerObj, enemyObj, bound, text);
            for (Ellipse e : listBullet) {
                root.getChildren().add(e);
            }

            Scene scene = new Scene(root, 1200, 600);

            stage.setTitle("Load Tank");
            

            stage.setScene(scene);

            stage.showAndWait();
            
        }
        
        
    }
    
    public static void main(String[] args) {
       launch(args);
    }
    
}
