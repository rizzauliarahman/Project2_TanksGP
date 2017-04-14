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
    Chromosome[] population;
    int nChromosome;
    
    public Population() {
        population = new Chromosome[10];
        nChromosome = 0;
    }
    
    public void showPopulation() {
        for (Chromosome c : population) {
            for (String s : c.getGenes()) {
                System.out.print(s + " - ");
            }
            
            System.out.println("Fitness : " + c.getFitness());
            
        }
    }
    
    public void createPopulation() {
        Chromosome c;
        
        for (int i = 0; i < 10; i++) {
            boolean found = false;
            
            do {
                c = new Chromosome();
                c.createPlayerGenes();
                
                boolean[] duplicateFound = new boolean[population.length];
                
                int count = 0;
                
                if (i != 0) {
                    
                    for (int k = 0; k < nChromosome; k++) {
                        found = true;
                        Chromosome ch = population[k];

                        String[] genes1 = new String[ch.getGenes().length];
                        String[] genes2 = new String[c.getGenes().length];
                        genes1 = ch.getGenes();
                        genes2 = c.getGenes();

                        int iter = Integer.min(genes1.length, genes2.length);
                        int x = 0;

                        while ((x < iter) && (found)) {
                            if ((genes1[x] != genes2[x])) {
                                found = false;
                            }
                            x++;
                        }

                        duplicateFound[count] = found;
                        count++;

                    }

                    found = false;
                    for (int k = 0; k < nChromosome; k++) {
                        boolean b = duplicateFound[k];
                        found = found || b;
                    }
                }
                
            } while (found);
            
            population[i] = c;
            this.nChromosome++;
            
        }
    }
    
    public Chromosome[] getPopulation() {
        return population;
    }
    
    public Chromosome getFittest() {
        Chromosome fittest = new Chromosome();
        
        float maxFitness = -1;
        
        for (int i = 0; i < this.nChromosome; i++) {
            Chromosome c = population[i];
            if (c.getFitness() > maxFitness) {
                fittest = c;
            }
        }
        return fittest;
    }
    
    public float getTotalFitness() {
        float total = 0;
        
        for (Chromosome c : population) {
            total += c.getFitness();
        }
        
        return total;
    }
    
    public void setNChromosome() {
        this.nChromosome++;
    }
    
    public int getNChromosome() {
        return nChromosome;
    }
    
}
