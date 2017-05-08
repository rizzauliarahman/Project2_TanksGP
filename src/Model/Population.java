/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author rizzauliarahman
 */
public class Population {    
    // Array to contain the chromosomes in population
    private Chromosome[] population;
    private int nChromosome; // Number of chromosomes in population
    
    
    // Constructor to initialize the array of chromosomes
    // and number of chromosome
    public Population() {
        population = new Chromosome[10];
        nChromosome = 0;
    }
    
    // @method to show all chromosomes in population
    public void showPopulation() {
        
        // for each chromosome, show the steps and
        // its fitness value
        for (Chromosome c : population) {
            for (String s : c.getGenes()) {
                System.out.print(s + " - ");
            }
            
            System.out.println("Fitness : " + c.getFitness());
            
        }
    }
    
    // @method to initialize the chromosomes in population
    public void createPopulation() {
        
        population = new Chromosome[10];
        nChromosome = 0;
        
        // @attribute c to contain the generated chromosome
        Chromosome c;
        
        // Repeat the chromosome generate process 10 times
        for (int i = 0; i < 10; i++) {
            
            // @attribute to show whether the chromosome has
            // been generated before or not
            boolean found = false;
            
            // Do chromosome generate process while the found
            // status is true
            do {
                
                // Initialize the chromosome c
                // and create its steps
                c = new Chromosome();
                c.createPlayerGenes();
                
                // @attribute to check if the chromosome
                // has been generated before
                boolean[] duplicateFound = new boolean[population.length];
                
                // @attribute to contain the duplicate number of
                // the chromosome in population
                int count = 0;
                
                // If its the first loop, no need to check
                // the chromosome duplication
                if (i != 0) {
                    
                    // Loop to compare the generated chromosome
                    // with each chromosome in current population
                    for (int k = 0; k < nChromosome; k++) {
                        
                        // Set the found status to true
                        found = true;
                        
                        // Get the chromosome at k-position
                        Chromosome ch = population[k];

                        // Get each chromosome steps
                        String[] genes1 = new String[ch.getGenes().length];
                        String[] genes2 = new String[c.getGenes().length];
                        genes1 = ch.getGenes();
                        genes2 = c.getGenes();

                        // Get the minimum size between those two
                        // chromosome
                        int iter = Integer.min(genes1.length, genes2.length);
                        
                        // Initialize the @attribute x for each genome
                        // loop
                        int x = 0;

                        // While x is less than iter, and genomes
                        // is still the same
                        while ((x < iter) && (found)) {
                            
                            // Compare both genome at x-position
                            if ((genes1[x] != genes2[x])) {
                                found = false;
                            }
                            x++;
                        }

                        // Add the chromosome comparison result to
                        // duplicateFound array
                        duplicateFound[count] = found;
                        count++;

                    }

                    // Set the found status back to false
                    found = false;
                    
                    // for each status in duplicateFound, check if
                    // there's any duplicate found
                    for (int k = 0; k < nChromosome; k++) {
                        boolean b = duplicateFound[k];
                        found = found || b;
                    }
                }
                
            } while (found);
            
            // Set the chromosome at i-position with
            // the generated chromosome
            population[i] = c;
            this.nChromosome++;
            
        }
    }
    
    // Return the chromosomes in population
    public Chromosome[] getPopulation() {
        return population;
    }
    
    // @method to get the chromosome with the highest
    // fitness value in population
    public Chromosome getFittest() {
        
        // Create new chromosome to contain the fittest
        // chromosome in population
        Chromosome fittest = new Chromosome();
        
        // Set initial maximum fitness to -1
        float maxFitness = -1;
        
        // Repeat for each chromosome, compare whether if
        // its fitness value is more than maximum fitness
        // value
        for (int i = 0; i < this.nChromosome; i++) {
            Chromosome c = population[i];
            if (c.getFitness() > maxFitness) {
                fittest = c;
            }
        }
        
        // Return the fittest chromosome
        return fittest;
    }
    
    // @method to get the total fitness value in the
    // population
    public float getTotalFitness() {
        
        // Initialize 
        float total = 0;
        
        // Repeat for each chromosome, add the fitness
        // value to total
        for (int i = 0; i < nChromosome; i++) {
            Chromosome c = population[i];
            total += c.getFitness();
        }
        
        // Return the total fitness value
        return total;
    }
    
    // Increase the number of chromosome by 1
    public void setNChromosome() {
        this.nChromosome++;
    }
    
    // Return the number of chromosome in this population
    public int getNChromosome() {
        return nChromosome;
    }
    
}
