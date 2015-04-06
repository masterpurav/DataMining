package kNN;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;
import java.util.StringTokenizer;

public class WeighIngridients {
	
    public static HashMap<String,Integer> run(FileReader fr) throws IOException{
    	System.out.println("Calculation of weights of ingridients started");
    	FileReader f1r = new FileReader("src/kNN/training-data.txt");
    	BufferedReader br = new BufferedReader(f1r);
    	String line;
    	HashSet<String> ingr = new HashSet<String>();
    	HashMap<String,ArrayList<Integer>> hm = new HashMap<String,ArrayList<Integer>>();
    	HashMap<Integer,Integer> cuisineDistr = new HashMap<Integer,Integer>();
    	while((line = br.readLine()) != null){
    		String str[] = line.split(" ");
    		int index = Integer.parseInt(str[0])-1;
    		if(cuisineDistr.containsKey(Integer.parseInt(str[0])))
    			cuisineDistr.put(Integer.parseInt(str[0]), cuisineDistr.get(Integer.parseInt(str[0]))+1);
    		else
    			cuisineDistr.put(Integer.parseInt(str[0]),1);
    		for(int i = 1 ; i < str.length ; i++){
    			ingr.add(str[i]);
    			if(hm.containsKey(str[i])){
    				ArrayList<Integer> a = hm.get(str[i]);
    				int x = a.get(index);
    				a.set(index, x+1);
        			hm.put(str[i],a);
        		}
        		else{
        			ArrayList<Integer> a = new ArrayList<Integer>();
        			for (int j = 0 ; j < 7 ; j++)
        				a.add(0);
        			a.set(index, 1);
        			hm.put(str[i], a);
        		}
    		}
    	}
    	Iterator<Entry<String, ArrayList<Integer>>> n = hm.entrySet().iterator();
    	HashMap<String,Integer> ingrWeights = new HashMap<String,Integer>();
    	while(n.hasNext()){
    		Entry <String,ArrayList<Integer>> p = n.next();
    		ArrayList<Integer> avgIngr = p.getValue();
    		int sum = 0;
    		for(Integer freq : avgIngr){
    			sum+=freq;
    		}
    		sum = sum/7;
    		for(int k = 0 ; k < 7 ; k++){
    			avgIngr.set(k,Math.max(avgIngr.get(k)-sum,0));
    		}
    		int count = 0;
    		for(Integer x : avgIngr){
    			if (x == 0)
    					count++;
    		}
    		ingrWeights.put(p.getKey(), count);
    	}
    	System.out.println("Calculation of weights of ingridients completed.");
    	Iterator i = ingrWeights.entrySet().iterator();
    	return ingrWeights;
    }	
}