package org.khl.branchfreesort.branchfreesort;

import java.util.Arrays;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;

import com.carrotsearch.junitbenchmarks.BenchmarkOptions;
import com.carrotsearch.junitbenchmarks.BenchmarkRule;
import com.carrotsearch.randomizedtesting.RandomizedTest;
import com.carrotsearch.randomizedtesting.annotations.Seed;

@BenchmarkOptions(benchmarkRounds=1000000, warmupRounds=1000)
public class BranchFreeSortTest extends RandomizedTest {
    @Rule
    public TestRule benchmarkRun = new BenchmarkRule();
    private static int [][] shuffles = new int[4][];
    private static int[] output;
    
    @BeforeClass
    public static void prepare(){
        int p = 1;
        for(int n = 0 ; n<shuffles.length; n++){
            shuffles[n] = shuffle(p*=10);
        }
        
        output = new int[shuffles[shuffles.length-1].length];
    }
    
    private static int[] shuffle(int i) {
        final int[] r = new int[i];
        for(int p=0;p<r.length;p++){
            r[p] = between(0, i);
        }
        return r;
    }

    @Test
    public void testSort10(){
        doSortInplace(shuffles[0]);
    }
    
    @Test
    public void testBranchFree10(){
        doSortPos(shuffles[0]);
    }
    
    @Test
    public void testSort100(){
        doSortInplace(shuffles[1]);
    }
    
    @Test
    public void testBranchFree100(){
        doSortPos(shuffles[1]);
    }
    
    @Ignore
    @Test
    public void testSort1000(){
        doSortInplace(shuffles[2]);
    }
    

    @Ignore
    @Test
    public void testBranchFree1000(){
        doSortPos(shuffles[2]);
    }
    

    @Ignore
    @Test
    public void testSort10000(){
        doSortInplace(shuffles[3]);
    }
    

    @Ignore
    @Test
    public void testBranchFree10000(){
        doSortPos(shuffles[3]);
    }
    
    void doSortInplace(final int[] inp){
         //= shuffles[0];
        final int[] cpy = inp.clone();
        Arrays.sort(cpy);        
    }
    
    void doSortPos(final int[] inp){
        Arrays.fill(output, 0, inp.length, Integer.MIN_VALUE);
        sortPositions(inp, output);        
    }
    
    void sortPositions(int input[], int output[]){
        for(int o=0;o<input.length;o++){
            int r = 0;
            
            for(int i=0;i<input.length;i++){
                final int d = (input[o]-input[i])>>31;
                r += d;
            }
            
            output[-r] = input[o];
        }
    }
    
    @Seed("DEADBEEF")
    public void tstProof(){
        int [] input = new int [10];
        int [] output = new int [input.length];
        
        for(int i =0;i<input.length;i++){
            input[i]=randomInt(input.length);
            output[i]=-1;
        }
        
        System.out.println(Arrays.toString(input));
        
        for(int o=0;o<output.length;o++){
            int r = 0;
            
            for(int i=0;i<input.length;i++){
                final int d = (input[o]-input[i])>>31;
                r += d;
            }
            
            output[-r] = input[o];
           // System.out.println("["+(-r)+"]="+input[o]);
        }
        
        //System.out.println(Arrays.toString(output));
    }
}
