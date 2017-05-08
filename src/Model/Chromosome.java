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
    // Array of available tank steps
    private String[] steps = {"walk", "turnRight", "turnLeft", "fire"};
    // This chromosome fitness value
    private double fitness;    
    // The steps taken in this chromosome
    private String[] genes;
    
    
    // Return the steps in this chromosome
    public String[] getGenes() {
        return genes;
    }
    
    // Set the steps of this chromosome
    public void setGenes(String[] genes) {
        this.genes = genes;
    }
    
    // Set the fitness of this chromosome by 100 divided by
    // the number of steps taken in this chromosome multiplied
    // by random number from 1.0-2.0
    public void setFitness() {
        this.fitness = 100 / (((new Random().nextFloat()) + 1) * this.genes.length);
    }
    
    // Return the fitness value of this chromosome
    public double getFitness() {
        return fitness;
    }
    
    // @method to generate the player's steps
    public void createPlayerGenes() {
        
        // Create new list to contain the generated
        // steps
        List<String> list = new ArrayList<>();
        
        // The prediction of enemy's health
        int projHealth = 100;
        
        // Keep generate a step while the predicted health
        // still more than 0
        while (projHealth > 0) {
            
            // Get a random step from the available steps
            String taken = this.steps[new Random().nextInt(steps.length)];
            
            // If step taken is fire, subtract the predicted
            // health by 5 multiplied with random number from 0-1
            if (taken.equals("fire")) {
                projHealth -= new Random().nextFloat() * 5;
            }
            
            // Add the taken step to the list
            list.add(taken);
            
        }
        
        // Convert the list into an array, and set
        // this chromosome's step with that array
        // Set this chromosome's fitness
        this.genes = new String[list.size()];
        this.genes = list.toArray(this.genes);
        setFitness();
        
    }
    
    // Return the available steps
    public String[] getSteps() {
        return steps;
    }
    
}
