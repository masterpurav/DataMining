package kNN;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;

import javax.swing.text.html.HTMLDocument.Iterator;

public class Recipe {
	int cuisine;
	HashSet<String> ingridients = new HashSet<String>();
	
	public Recipe(){}
	
	public String toString(){
		StringBuffer str = null;
		str.append(this.cuisine+"\n");
		java.util.Iterator<String> it = this.ingridients.iterator();
		while (it.hasNext())
			str.append(it.next()+" ");
		String r = new String(str);
		return r;
	}
	
	public Recipe(int cuisine, HashSet<String> ingridients){
		this.cuisine = cuisine;
		this.ingridients = ingridients;
	}

	public static class cdTuple implements Comparable{
		int cuisine;
		float dist;
		public cdTuple(int cuisine, float dist){
			this.cuisine = cuisine;
			this.dist = dist;
		}
		
		public String toString(){
			return (this.cuisine+" : "+this.dist);
		}
		
		@Override
		public int compareTo(Object o) {
			// TODO Auto-generated method stub
			cdTuple x = (cdTuple) o;
			if (this.dist > x.dist)
				return 1;
			else if (this.dist < x.dist)
				return -1;
			else return 0;
		}
	}
	
	public float calculateDistance(Recipe p,HashMap<String,Integer> hm, HashMap<String,Integer> f){
		java.util.Iterator<Entry<String, Integer>> i = hm.entrySet().iterator();
		HashSet<String> union = new HashSet<String>(this.ingridients);
		HashSet<String> intersection = new HashSet<String>(this.ingridients);
		union.addAll(p.ingridients);
		intersection.retainAll(p.ingridients);
		float unionWeight = 0;
		float intersectionWeight = 0;
		for (String s : union){ 
			if (f.get(s) > 25){
				try{
				unionWeight+=((float)hm.get(s)/(float)6);
				//unionWeight++;
				}
				catch (Exception e){
					unionWeight+=(1/(float)6);
				}
			}
		}
		for (String s : intersection){
			if (f.get(s) > 25){
				try{
					intersectionWeight += ((float)hm.get(s)/(float)6);
				}
				catch (Exception e){
					intersectionWeight += (1/(float)6);
				}//intersectionWeight ++;
				}
		}
		return 1-((float)intersectionWeight/(float)unionWeight);
	}
	
	public ArrayList<cdTuple> createDistanceArray(ArrayList<Recipe> a,HashMap<String,Integer> hm,HashMap<String,Integer> f){
		ArrayList<cdTuple> tuples = new ArrayList<cdTuple>();
		java.util.Iterator<Recipe> i = a.iterator();
		while(i.hasNext()){
			Recipe r = i.next();
			tuples.add(new cdTuple(r.cuisine,r.calculateDistance(this,hm,f)));
		}
		return tuples;
	}
	
	
}
