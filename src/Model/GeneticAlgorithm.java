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
public class GeneticAlgorithm {
    
    Chromosome[] parent;
    int tournamentSize = 3;
    double crossoverRate = 0.9, mutationRate = 0.1;
    
    public Chromosome roulette(Population pop) {
        float total = pop.getTotalFitness();
        
        total *= new Random().nextFloat();
        
        int p = 0, x = 0;
        
        while ((p < total) && (x < pop.getPopulation().length)) {
            p += pop.getPopulation()[x].getFitness();
            x++;
        }
        
        if (x == 0) {
            return pop.getPopulation()[x];
        } else {
            return pop.getPopulation()[x-1];
        }
        
    }
    
    public Chromosome tournament(Population pop) {
        
        Population tmp = new Population();
        
        List<Integer> numOut = new ArrayList<>();
        
        int rand;
        
        for (int i = 0; i < tournamentSize; i++) {
            do {
                rand = new Random().nextInt(pop.getPopulation().length) + 0;
            } while (numOut.contains(rand));
            tmp.getPopulation()[i] = pop.getPopulation()[rand];
            numOut.add(rand);
        }
        
        return tmp.getFittest();
        
    }
    
    public void parentSelection(Population pop) {
        parent = new Chromosome[2];
        List<Integer> index = new ArrayList<>();
        
        int sel = new Random().nextInt(2);
        
        Chromosome p1 = new Chromosome();
        Chromosome p2 = new Chromosome();
        
        if (sel == 0) {
            do {
                p1 = roulette(pop);
                p2 = roulette(pop);
            } while (p1.equals(p2));
        } else if (sel == 1) {
            do {
                p1 = tournament(pop);
                p2 = tournament(pop);
            } while (p1.equals(p2));
        }
        
        parent[0] = p1;
        parent[1] = p2;
        
    }
    
    public Chromosome[] crossover(Population pop) {
        Chromosome[] child = new Chromosome[2];
        
        parentSelection(pop);
        
        System.out.println(parent[0].getGenes().length);
        
        int size1 = parent[0].getGenes().length;
        int size2 = parent[1].getGenes().length;
        
        int iter = Integer.min(size1, size2);
        
        int point = new Random().nextInt(iter);
        
        String[] temp = new String[point];
        System.arraycopy(parent[0].getGenes(), 0, temp, 0, point);
        
        System.arraycopy(parent[0].getGenes(), 0, parent[1].getGenes(), 0, point);
        System.arraycopy(parent[1].getGenes(), 0, parent[0].getGenes(), 0, point);
        
        child[0] = parent[0];
        child[1] = parent[1];
        child[0].setFitness();
        child[1].setFitness();
        
        return child;
        
    }
    
    public void mutation(Chromosome c) {
        boolean valid;
        
        do {
            valid = true;
            
            int gen = new Random().nextInt(c.getGenes().length);
            String init = c.getGenes()[gen];
            
            int mut = new Random().nextInt(c.getSteps().length);
            String elem = c.getSteps()[mut];
            
            if (init != elem) {
                c.getGenes()[gen] = elem;
            } else {
                valid = false;
            }
            
        } while (!valid);
        
    }
    
    public void survivorSelection(Chromosome[] child, Population pop) {
        
        double min;
        int idxMin;
        boolean status;
        
        for (int i = 0; i < child.length; i++) {
            idxMin = -1;
            min = 9999;
            status = true;
            
            for (int j = 0; j < pop.getPopulation().length; j++) {
                if (pop.getPopulation()[j].getFitness() < min) {
                    min = pop.getPopulation()[j].getFitness();
                    idxMin = j;
                }
                
                Chromosome c = pop.getPopulation()[j];
                
                int size1 = pop.getPopulation()[j].getGenes().length;
                int size2 = child[i].getGenes().length;
                
                int iter = Integer.min(size1, size2);
                
                for (int x = 0; x < iter; x++) {
                    if (c.getGenes()[x].equals(child[i].getGenes()[x])) {
                        status = status && true;
                    } else {
                        status = status && false;
                    }
                }
            }
            
            if (!status) {
                pop.getPopulation()[idxMin] = child[i];
            }
            
        }
        
    }
    
    public boolean termination(Double[] fittestList) {
        double fit = fittestList[0];
        int count = -1;
        
        for (int i = 0; i < fittestList.length; i++) {
            if (fittestList[i] == fit) {
                count += 1;
            } else {
                fit = fittestList[i];
                count = 0;
            }
        }
        
        if (count < 6) {
            return false;
        } else {
            return true;
        }
        
    }
    
    public Chromosome[] getParent() {
        return parent;
    }
    
    public double getCrossoverRate() {
        return crossoverRate;
    }
    
    public double getMutationRate() {
        return mutationRate;
    }
    
}
