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
import javafx.animation.ParallelTransition;
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
        double minEnemyHealth = 9999;
        int minStepsTaken = 9999;
        
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

            List<Animation> listAnim = new ArrayList<>();
            
            Chromosome enemyMoves = GA.enemyMoves(c.getGenes().length);
            
            List<Animation> listEnemy = new ArrayList<>();
            
            for (int m = 0; m < c.getGenes().length; m++) {
                String s = c.getGenes()[m];
                String e = enemyMoves.getGenes()[m];
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
//                    System.out.print("fire ");
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
                
                if (e.equals("walk")) {
                    TranslateTransition translate = enemy.walk();
                    listEnemy.add(translate);
                } else if (e.equals("turnRight")) {
                    RotateTransition rotate = enemy.turnRight();
                    listEnemy.add(rotate);
                } else if (e.equals("turnLeft")) {
                    RotateTransition rotate = enemy.turnLeft();
                    listEnemy.add(rotate);
                } else if (e.equals("fire")) {
//                    System.out.print("fire ");
                    TranslateTransition translate = enemy.fire(player);

                    Ellipse bullet = enemy.getBullet().getBullet();
                    listBullet.add(bullet);

                    FadeTransition fade1 = new FadeTransition(Duration.millis(10), enemy.getBullet().getBullet());
                    fade1.setFromValue(0);
                    fade1.setToValue(1);
                    fade1.setCycleCount(1);

                    FadeTransition fade2 = new FadeTransition(Duration.millis(10), enemy.getBullet().getBullet());
                    fade2.setFromValue(1);
                    fade2.setToValue(0);
                    fade2.setCycleCount(1);

                    listEnemy.add(fade1);
                    listEnemy.add(translate);
                    listEnemy.add(fade2);
                }
            }
            
            if (enemy.getHealth() == minEnemyHealth) {
                if (c.getGenes().length < minStepsTaken) {
                    minStepsTaken = c.getGenes().length;
                }
            } else if (enemy.getHealth() < minEnemyHealth) {
                minEnemyHealth = enemy.getHealth();
                minStepsTaken = c.getGenes().length;
            }
            
            Text text1 = new Text(10, 560, "Generation - " + j + ", Enemy Health Remaining : " + 
                    enemy.getHealth() + ", Player Health Remaining : " + player.getHealth());
            Text text2 = new Text(10, 580, "Best Record : " + minEnemyHealth + " health remaining with " +
                    minStepsTaken + " steps taken");
            text1.setFont(Font.font("Verdana", 12));
            text2.setFont(Font.font("Verdana", 12));
            
            PauseTransition delay = new PauseTransition(Duration.millis(2000));
            delay.setOnFinished(event -> stage.close());
            listAnim.add(delay);
            listEnemy.add(delay);
            
            Animation[] anim = new Animation[listAnim.size()];
            anim = listAnim.toArray(anim);
            
//            ParallelTransition par = new ParallelTransition(anim);
//            par.getChildren().addAll(enemyMovement);
//            
//            par.play();
            
            Animation[] enemyMovement = new Animation[listEnemy.size()];
            enemyMovement = listEnemy.toArray(enemyMovement);
            
            SequentialTransition seq1 = new SequentialTransition(anim);
            seq1.play();
            
            SequentialTransition seq2 = new SequentialTransition(enemyMovement);
            seq2.play();
            
            i++;
            
            terminate = GA.termination(tmp, pop);
            
//            terminate = true;
            
            ImageView playerObj = player.getTank();
            ImageView enemyObj = enemy.getTank();

            Group root = new Group(playerObj, enemyObj, bound, text1, text2);
            for (Ellipse e : listBullet) {
                root.getChildren().add(e);
            }

            Scene scene = new Scene(root, 1200, 600);
            scene.setFill(Color.LIGHTYELLOW);

            stage.setTitle("Load Tank");

            stage.setScene(scene);

            stage.showAndWait();
            
        }
        
        System.out.println("Generations count : " + i);
        System.out.println("Minimum Steps count : " + minStepsTaken);
        System.out.println("Maximum Damage Dealt : " + (100 - minEnemyHealth));
        
    }
    
    public static void main(String[] args) {
       launch(args);
    }
    
}
