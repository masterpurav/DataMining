package kNN;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map.Entry;

public class Sandbox {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		String line;
		ArrayList<Recipe> everything = new ArrayList<Recipe>();
		FileReader f = new FileReader("src/kNN/training-data.txt");
		BufferedReader br = new BufferedReader(f);
		HashMap<String,ArrayList<Integer>> chi = new HashMap<String,ArrayList<Integer>>();
		File file = new File("src/kNN/out.txt");
		//FileWriter fw = new FileWriter(file.getAbsoluteFile());
		int count = 0;
		while((line = br.readLine())!= null){
				String[] st = line.split(" ");
				int cuisine = Integer.parseInt(st[0]);
				if(cuisine == 4 || cuisine == 5){
					//HashSet<String> a = new HashSet<String>();
					ArrayList<Integer> a = new ArrayList<Integer>();
					for (int  i = 1 ; i < st.length ; i++){
						if (chi.containsKey(st[i])){
							ArrayList<Integer> x = chi.get(st[i]);
							x.set(cuisine - 4, x.get(cuisine - 4)+1);
						}
						else{
							ArrayList<Integer> x = new ArrayList<Integer>();
							x.add(0);
							x.add(0);
							x.set(cuisine - 4, x.get(cuisine - 4)+1);
							chi.put(st[i], x);
						}
						
					}
					//Recipe r = new Recipe(cuisine,a);
					//everything.add(r);
				}
		}
		Iterator<Entry<String, ArrayList<Integer>>> iter = chi.entrySet().iterator();
		System.out.println(chi.size());
		while(iter.hasNext()){
			Entry<String, ArrayList<Integer>> x = iter.next();
			System.out.println(x.getKey()+" "+x.getValue());
		}
		//System.out.println(everything.size());
	}

}
