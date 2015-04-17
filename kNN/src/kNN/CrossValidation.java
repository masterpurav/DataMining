package kNN;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import kNN.Recipe.cdTuple;

public class CrossValidation {
	
	public static void validate(ArrayList<Recipe> a,int folds,HashMap<String,Integer> weights,HashMap<String,Integer> f){
		int[][] confusionMatrix = new int[7][7];
		System.out.println(a.size());
		int foldSize = a.size()/folds;
		/* Iterate through all folds */
		int count = 0;
		int wrong = 0;
		for (int i = 0 ; i < folds ; i++){
			int foldMin = i*foldSize;
			int foldMax = (i+1)*foldSize;
			/* Iterate through all indices within the fold */
			for (int c = foldMin ; c <= foldMax ; c++ ){
				Recipe test = a.get(c);
				ArrayList<Recipe.cdTuple> distances = new ArrayList<Recipe.cdTuple>();
				/* Iterate through the training data */
				for(int j = 0 ; j < a.size() ; j++){
					if(j<foldMin || j>foldMax){
						Recipe trained = a.get(j);
						distances.add(new Recipe.cdTuple(trained.cuisine,trained.calculateDistance(test,weights,f)));
					}
					
				}
				/* Predict and compare */
				int k = 9 ;
				Collections.sort(distances);
				StringBuffer sb = new StringBuffer();
				float maxDist = distances.get(k-1).dist;
				float minDist = distances.get(0).dist;
				java.util.Iterator<cdTuple> iter = distances.iterator();
				HashMap<Integer, Float> hm = new HashMap<Integer,Float>();
				HashMap<Integer, Integer> hm2 = new HashMap<Integer,Integer>();
				
				for(int p = 0 ; p < k ; p++){
					Recipe.cdTuple x = iter.next();
					if(!hm.containsKey(x.cuisine)){
						hm.put(x.cuisine, (maxDist - x.dist)/(maxDist - minDist));
						hm2.put(x.cuisine,1);
					}
					else{
						hm.put(x.cuisine,hm.get(x.cuisine)+(maxDist - x.dist)/(maxDist - minDist));
						hm2.put(x.cuisine,hm2.get(x.cuisine)+1);
					}
					sb.append(x.cuisine+" "+x.dist+"\n");
				}
				Entry<Integer, Float> max = null;
				HashMap<Integer,Float> hm3 = new HashMap<Integer,Float>();
				Iterator<Entry<Integer, Integer>> it = hm2.entrySet().iterator();
				while (it.hasNext()){
					Entry<Integer,Integer> p = it.next();
					hm3.put(p.getKey(), p.getValue()*hm.get(p.getKey()));
				}
				java.util.Iterator<Entry<Integer, Float>> hmiter = hm3.entrySet().iterator();
				while (hmiter.hasNext()){
					Entry<Integer,Float> p = hmiter.next();
					if (max == null || p.getValue() > max.getValue())
						max = p;
				}
				int predicted = max.getKey();
				count++;
				if(predicted != a.get(c).cuisine){
					wrong++;
					System.out.println(count+"/ "+"Predicted : "+predicted+" Actual : "+a.get(c).cuisine + "\t"+(1- (float)wrong/(float)count));
					System.out.println(sb);
					System.out.println("****");
				}
				confusionMatrix[predicted-1][a.get(c).cuisine-1]++;
			}
		}
		for(int i = 0 ; i < 7 ; i++){
			for(int j = 0 ; j < 7 ; j++){
				System.out.print(confusionMatrix[i][j]+"\t");
			}
			System.out.println();
		}
		System.out.println("Statistics : ");
		int correct = 0;
		for(int i = 0 ; i < 7 ; i++)
			correct+=confusionMatrix[i][i];
		System.out.println("Accuracy : "+(float) correct / (float) a.size());
	}
	
	
}
