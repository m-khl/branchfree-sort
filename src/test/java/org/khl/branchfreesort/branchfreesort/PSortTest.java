package org.khl.branchfreesort.branchfreesort;

import java.util.Arrays;
import java.util.Collections;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;

import com.carrotsearch.junitbenchmarks.BenchmarkOptions;
import com.carrotsearch.junitbenchmarks.BenchmarkRule;
import com.carrotsearch.randomizedtesting.RandomizedTest;
import com.carrotsearch.randomizedtesting.annotations.Seed;

//@BenchmarkOptions(benchmarkRounds=1000000, warmupRounds=1000)
@BenchmarkOptions(benchmarkRounds=10000, warmupRounds=1000)
public class PSortTest extends RandomizedTest {
    @Rule
    public TestRule benchmarkRun = new BenchmarkRule();
    private static int [][] shuffles = new int[4][];
    private static int[] output;
    private static int[] buf;
    
    @BeforeClass
    public static void prepare(){
        int p = 1;
        for(int n = 0 ; n<shuffles.length; n++){
            shuffles[n] = shuffle(p*=10);
        }
        
        output = new int[shuffles[shuffles.length-1].length];
        buf = new int[shuffles[shuffles.length-1].length];
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
    public void testPSort10(){
        doPSort(shuffles[0]);
    }
    
    @Test
    public void testSort100(){
        doSortInplace(shuffles[1]);
    }
    
    @Test
    public void testBranchFree100(){
        doSortPos(shuffles[1]);
    }
    
    @Test
    public void testPSort100(){
        doPSort(shuffles[1]);
    }
    
    //@Ignore
    @Test
    public void testSort1000(){
        doSortInplace(shuffles[2]);
    }
    

    //@Ignore
    @Test
    public void testBranchFree1000(){
        doSortPos(shuffles[2]);
    }
    
    @Test
    public void testPSort1000(){
        doPSort(shuffles[2]);
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
    
    void doPSort(final int[] inp){
        Arrays.fill(buf, 0, inp.length, 0);
        psort(inp, output, buf);        
    }
    
    private void psort(int[] in, int[] out, int[] buffer) {
        //System.out.println(Arrays.toString(in));
        for(int c=0;c<in.length;c++){
            final int colelem = in[c];
            for(int r=c+1;r<in.length;r++){
                final int t = ((colelem-in[r])>>31);
                buffer[c]+=t;
                buffer[r]+=t ^ -1;
            }
        }
        //System.out.println(Arrays.toString(buffer));
        for(int c=0;c<in.length;c++){
            out[-buffer[c]] = in[c];
        } 
        //System.out.println(Arrays.toString(out));
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
    
    //@Seed("DEADBEEF")
    //@Test
    public void tstProof(){
        int [] input = new int [10];
        int [] output = new int [input.length];
        
        {
            boolean uniq=false;
            for(int i =0;i<input.length;i++){
                while(true){
                    zz:{
                    input[i]= randomInt(3*input.length);
                    if(uniq){
                        for(int j=0;j<i;j++){
                           if(input[j]==input[i]){
                               break zz;
                           } 
                        }
                    }
                    break;
                }
                }
                output[i]=0;
            }
        }
        System.out.println(Arrays.toString(input));
        
//        for(int o=0;o<output.length;o++){
//            int r = 0;
//            
//            for(int i=0;i<input.length;i++){
//                final int d = (input[o]-input[i])>>31;
//                r += d;
//            }
//            
//            output[-r] = input[o];
//        }
        for(int c=0;c<input.length;c++){
            for(int r=c+1;r<input.length;r++){
                final int t = ((input[c]-input[r])>>31);
                output[c]+=t;
                output[r]+=t ^ -1;
            }
        }
        System.out.println(Arrays.toString(output));
        int buf[] = new int[output.length];
        for(int c=0;c<input.length;c++){
            buf[-output[c]] = input[c];
        }
        System.out.println(Arrays.toString(buf));
    }
}
