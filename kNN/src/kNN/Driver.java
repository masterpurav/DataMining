package kNN;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Set;
import java.util.StringTokenizer;
import java.io.*;

import javax.swing.text.html.HTMLDocument.Iterator;

import kNN.Recipe.cdTuple;

public class Driver {
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		long start = System.currentTimeMillis()/1000;
		/*
		 * 1. Read training data, create the data structures
		 */
		
		String line;
		ArrayList<Recipe> everything = new ArrayList<Recipe>();
		FileReader f = new FileReader("src/kNN/training-data.txt");
		BufferedReader br = new BufferedReader(f);
		File file = new File("src/kNN/out.txt");
		FileWriter fw = new FileWriter(file.getAbsoluteFile());
		int count = 0;
		while((line = br.readLine())!= null){
				String[] st = line.split(" ");
				int cuisine = Integer.parseInt(st[0]);
				HashSet<String> a = new HashSet<String>();
				for (int  i = 1 ; i < st.length ; i++){
					a.add(st[i]);
				}
				Recipe r = new Recipe(cuisine,a);
				everything.add(r);
		}
		System.out.println(everything.size());
		
		
		/*
		 * 2. Read training data, classify recipes.
		 */
		
		FileReader test = new FileReader("src/kNN/testing-data-small.txt");
		BufferedReader testReader = new BufferedReader(test);
		HashMap weights = WeighIngridients.run(f);
		while((line = testReader.readLine())!=null){
			String[] str = line.split(" ");
			HashSet<String> ingr = new HashSet<String>();
			for(int c = 0; c<str.length; c++){
				ingr.add(str[c]);
			}
			Recipe r = new Recipe();
			r.ingridients = ingr;
			ArrayList<Recipe.cdTuple> closest = new ArrayList<Recipe.cdTuple>();			
			/*
			 * Calculate distance
			 */
			
			closest = r.createDistanceArray(everything,weights);
			
			
			/*
			 * 4. Voting
			 */
			int k = 12;
			Collections.sort(closest);
			java.util.Iterator<cdTuple> iter = closest.iterator();
			//System.out.println("New Recipe:");
			HashMap<Integer, Integer> hm = new HashMap<Integer,Integer>();
			for(int c = 0 ; c < k ; c++){
				Recipe.cdTuple x = iter.next();
				if(!hm.containsKey(x.cuisine))
					hm.put(x.cuisine, 1);
				else
					hm.put(x.cuisine,hm.get(x.cuisine)+1);
			}
			Entry<Integer,Integer> max = null;
			java.util.Iterator<Entry<Integer, Integer>> hmiter = hm.entrySet().iterator();
			
			while (hmiter.hasNext()){
				Entry<Integer,Integer> p = hmiter.next();
				if (max == null || p.getValue() > max.getValue())
					max = p;
			}
			System.out.println(max.getKey());
		}
		CrossValidation.validate(everything, 10, weights);
		long end = System.currentTimeMillis()/1000;
		
		System.out.println("Time taken : "+ (float)(end - start)/(float)60);
		}
		//testReader.close();	
		
	}




