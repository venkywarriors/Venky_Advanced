package testbase;

import java.util.Random; 

public class GenerateRandom{ 
  
    public static void main(String args[]) 
    { 
        // create instance of Random class 
        Random rand = new Random(); 
        
        int rand_int1 = rand.nextInt(1000); // Generate random integers in range 0 to 999 
        double rand_dub1 = rand.nextDouble();  // Generate Random doubles
        float float_random=rand.nextFloat(); // Generate Random float 0.0 and 1.0
        // Print random integers 
        System.out.println("Random Integers: "+rand_int1); 
        System.out.println("Random Doubles: "+rand_dub1); 
        System.out.println("Random float value: "+float_random); 
        
        // Generate random number without using random class
        int min = 50;
        int max = 100;
        //Generate random double value from 50 to 100 
        System.out.println("Random value in double from "+min+" to "+max+ ":");
        double random_double = Math.random() * (max - min + 1) + min; 
        System.out.println("without using random class "+random_double);
          
        //Generate random int value from 50 to 100 
        System.out.println("Random value in int from "+min+" to "+max+ ":");
        int random_int = (int)(Math.random() * (max - min + 1) + min);
        System.out.println("without using random class "+random_int); 
    } 
} 
