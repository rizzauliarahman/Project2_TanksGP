/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author rizzauliarahman
 */
public class Chromosome {
    
    String[] steps = {"walk", "turnRight", "turnLeft", "fire"};
    double fitness;
    String[] genes;
    
    public String[] getGenes() {
        return genes;
    }
    
    public void setGenes(String[] genes) {
        this.genes = genes;
    }
    
    public void setFitness() {
        this.fitness = 100 / (((new Random().nextFloat()) + 1) * this.genes.length);
    }
    
    public double getFitness() {
        return fitness;
    }
    
    public void createPlayerGenes() {
        List<String> list = new ArrayList<>();
        
        int projHealth = 100;
        
        while (projHealth > 0) {
            String taken = this.steps[new Random().nextInt(steps.length)];
            
            if (taken.equals("fire")) {
                projHealth -= new Random().nextFloat() * 5;
            }
            
            list.add(taken);
            
        }
        
        this.genes = new String[list.size()];
        this.genes = list.toArray(this.genes);
        setFitness();
        
    }
    
    public void createEnemyGenes(int stepsCount) {
        List<String> list = new ArrayList<>();
        
        for (int i = 0; i < stepsCount; i++) {
            String taken = this.steps[new Random().nextInt(steps.length)];
            
            list.add(taken);            
        }
        
        this.genes = new String[list.size()];
        this.genes = list.toArray(this.genes);
        setFitness();
        
    }
    
    public String[] getSteps() {
        return steps;
    }
    
}
