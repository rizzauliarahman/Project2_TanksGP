/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 *
 * @author rizzauliarahman
 */
public class GeneticAlgorithm {
    
    // Array to contain the parent chromosome in
    // the generation
    Chromosome[] parent;
    
    // Number of chromosome to be in selection on
    // tournament parent selection method
    int tournamentSize = 3;
    
    // Set the crossover and mutation rate
    double crossoverRate = 0.9, mutationRate = 0.1;
    
    // @method to do the roulette survivor selection
    public Chromosome[] roulette(Population pop) {
        
        // Array to contain the parent selected in
        // this method
        Chromosome[] tmp = new Chromosome[2];
        
        // Total of all fitness value in the population
        float total = pop.getTotalFitness();
        
        // Multiply the total fitness with random
        // number in range 0-1
        total *= new Random().nextFloat();
        
        // Contain the fitness value of the first taken
        // chromosome, so it will be excluded in next
        // selection
        double fitnessTaken = 0;
        
        // Repeat the selection process 2 times
        for (int i = 0; i < 2; i++) {
            
            int x = 0; // Index of the chromosome
            double p = 0; // Contain the fitness value
            
            // Subtract the total fitness value with
            // the fitness value from taken chromosome
            total -= fitnessTaken;
            
            // repeat adding the fitness value while p doesn't
            // exceed the total fitness
            while ((p < total) && (x < pop.getPopulation().length)) {
                p += pop.getPopulation()[x].getFitness();
                x++;
            }
            
            // If the first chromosome is selected, get it
            // If not, select the chromosome in x-1 position
            if (x == 0) {
                tmp[i] =  pop.getPopulation()[x];
                fitnessTaken += pop.getPopulation()[x].getFitness();
            } else {
                tmp[i] = pop.getPopulation()[x-1];
                fitnessTaken += pop.getPopulation()[x-1].getFitness();
            }
            
        }
        
        // return the selected chromosomes
        return tmp;
    }
    
    // @method to do the tournament parent selection
    public Chromosome tournament(Population pop) {
        
        // Create new population to contain the
        // chromosomes in this method
        Population tmp = new Population();
        
        // Create new list to contain the index of chromosome
        // that has been taken
        List<Integer> numOut = new ArrayList<>();
        
        // Contain the randomized index
        int rand;
        
        // Repeat the selection process based on the tournament
        // size (3)
        for (int i = 0; i < tournamentSize; i++) {
            
            // Keep randomize the index while the randomized
            // index has been generated before
            do {
                rand = new Random().nextInt(pop.getPopulation().length) + 0;
            } while (numOut.contains(rand));
            
            // Get the chromosome in rand index and add it
            // to the population tmp
            tmp.getPopulation()[i] = pop.getPopulation()[rand];
            tmp.setNChromosome();
            
            // add the generated number to the list
            numOut.add(rand);
        }
        
        // Return the chromosome with highest fitness value
        // in the population tmp
        return tmp.getFittest();
        
    }
    
    // @method to decide what method to be used
    // in parent selection process
    public void parentSelection(Population pop) {
        
        // Array to contain the parent chromosomes
        // got by the selection process
        parent = new Chromosome[2];
        
        // Random number to decide what method
        // to be used
        int sel = new Random().nextInt(2);
        
        // Create 2 new chromosomes to contain the
        // chromosomes from the selection process
        Chromosome p1 = new Chromosome();
        Chromosome p2 = new Chromosome();
        
        // If random number is 0, do the roulette selection
        if (sel == 0) {
            do {
                Chromosome[] tmp = roulette(pop);
                p1 = tmp[0];
                p2 = tmp[1];
            } while (p1.equals(p2));
            
        // Else if random number is 1, do the tournament
        // selection
        } else if (sel == 1) {            
            do {
                p1 = tournament(pop);
                p2 = tournament(pop);
            } while (p1.equals(p2));
        }
        
        // Add chromosome p1 and p2 to the array
        parent[0] = p1;
        parent[1] = p2;
        
    }
    
    // @method to do the crossover evolution process
    public Chromosome[] crossover(Population pop) {
        
        // Create new array to contain the child chromosomes
        // generated in this process
        Chromosome[] child = new Chromosome[2];
        
        // Do the parent selection on the population first
        parentSelection(pop);
        
        // Get the genome size of each parent
        int size1 = parent[0].getGenes().length;
        int size2 = parent[1].getGenes().length;
        
        // Set the iter with minimum value between those two
        int iter = Integer.min(size1, size2);
        
        // Random a number between 0-iter
        int point = new Random().nextInt(iter);
        
        // Create new array to contain the sliced chromosome
        String[] temp = new String[point];
        
        // Copy the sliced chromosome with size of point from
        // first parent to temp
        System.arraycopy(parent[0].getGenes(), 0, temp, 0, point);
        
        // Copy the sliced chromosome from first parent to second parent,
        // and do the same process with second parent with the sliced
        // chromosome in temp
        System.arraycopy(parent[0].getGenes(), 0, parent[1].getGenes(), 0, point);
        System.arraycopy(parent[1].getGenes(), 0, parent[0].getGenes(), 0, point);
        
        // Set the child chromosomes with both parent chromosomes
        // and set their new fitness value
        child[0] = parent[0];
        child[1] = parent[1];
        child[0].setFitness();
        child[1].setFitness();
        
        // Return the generated child chromosomes
        return child;
        
    }
    
    // @method to do the mutation process
    public void mutation(Chromosome c) {
        boolean valid; // check if the mutated genome is valid
        
        // Do the process while the mutated genomes isn't valid
        do {
            valid = true; // Initialize the valid @attribute
            
            // Get the genome at random index in chromosome c
            int gen = new Random().nextInt(c.getGenes().length);
            String init = c.getGenes()[gen];
            
            // Get the genome at random index in the available steps
            int mut = new Random().nextInt(c.getSteps().length);
            String elem = c.getSteps()[mut];
            
            // Check if those two genome is the same
            // If they're not the same, swap them
            if (init != elem) {
                c.getGenes()[gen] = elem;
            } else {
                valid = false;
            }
            
        } while (!valid);
        
    }
    
    // @method to do the survivor selection
    public void survivorSelection(Chromosome[] child, Population pop) {
        
        double min; // Minimum fitness value in population
        int idxMin; // Index where the minimum fitness is found
        boolean status; // Check if the chromosome with minimum fitness
        // is the same with the child chromosome
        
        // repeat process for each child chromosome
        for (int i = 0; i < child.length; i++) {
            
            // Initialize the minimum fitness, index and
            // status
            idxMin = -1;
            min = 9999;
            status = true;
            
            // Loop for all chromosome in population
            for (int j = 0; j < pop.getPopulation().length; j++) {
                
                // Check if the fitness value is less than the
                // minimum fitness value
                if (pop.getPopulation()[j].getFitness() < min) {
                    min = pop.getPopulation()[j].getFitness();
                    idxMin = j;
                }
                
                // Get the chromosome with the minimum fitness value
                Chromosome c = pop.getPopulation()[idxMin];
                
                // Get the size of the minimum chromosome and
                // the child chromosome
                int size1 = pop.getPopulation()[j].getGenes().length;
                int size2 = child[i].getGenes().length;
                
                // Get the minimum value between both size
                int iter = Integer.min(size1, size2);
                
                // Repeat from 0-iter
                // Check if both chromosome is the same by
                // checking each of their genome
                for (int x = 0; x < iter; x++) {
                    
                    // If the genome is equal, do the AND
                    // operation between previous status and true
                    // Else, do the AND operation between previous
                    // status with false
                    if (c.getGenes()[x].equals(child[i].getGenes()[x])) {
                        status = status && true;
                    } else {
                        status = status && false;
                    }
                }
            }
            
            // If the status is false, insert the child
            // chromosome at minimum chromosome position
            if (!status) {
                pop.getPopulation()[idxMin] = child[i];
            }
            
        }
        
    }
    
    // @method to check the termination process is met or not
    public boolean termination(Double[] fittestList, Population pop) {
        
        // Get the first fitness value in fittest value list
        double fit = fittestList[0];
        
        // Initialize attribute to count the consecutive same
        // fitness value
        int count = -1, i = 0;
        
        // Repeat for each fitness value in the list and check
        // if it's the same with previous fitness value
        // If they're the same, increase count by 1
        // Else, reset count to 0
        for (i = 0; i < fittestList.length; i++) {
            if (fittestList[i] == fit) {
                count += 1;
            } else {
                fit = fittestList[i];
                count = 0;
            }
        }
        
        // Create a new Set to contain the chromosome in
        // population
        // NOTE : Set doesn't allow duplicate element. So, if
        //        there's more than two same element, there will
        //        be only one element added
        Set<Chromosome> tmp = new HashSet<>();
        tmp.addAll(Arrays.asList(pop.getPopulation()));
        
        // Check if the last fitness value has been repeated for
        // 10 times OR the size of chromosome set is 1 (all
        // chromosomes is the same)
        if ((count >= 10) || (tmp.size() == 1)) {
            return true;
        } else {
            return false;
        }
        
    }
    
    // @method to return the parent chromosomes
    public Chromosome[] getParent() {
        return parent;
    }
    
    // @method to return the crossover rate
    public double getCrossoverRate() {
        return crossoverRate;
    }
    
    // @method to return the mutation rate
    public double getMutationRate() {
        return mutationRate;
    }
    
    // @method to create the enemy moves
    public Chromosome enemyMoves(int stepsCount) {
        
        // Create new enemy chromosome and generate its
        // steps
        Chromosome enemy = new Chromosome();
        enemy.createEnemyGenes(stepsCount);
        
        // return the enemy chromosomes
        return enemy;
    }
    
}
