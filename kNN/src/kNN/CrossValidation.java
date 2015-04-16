package kNN;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
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
				int k = 9;
				Collections.sort(distances);
				java.util.Iterator<cdTuple> iter = distances.iterator();
				HashMap<Integer, Integer> hm = new HashMap<Integer,Integer>();
				for(int p = 0 ; p < k ; p++){
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
				int predicted = max.getKey();
				count++;
				if(predicted != a.get(c).cuisine){
					System.out.println(count+"/ "+"Predicted : "+predicted+" Actual : "+a.get(c).cuisine + "\t"+(1- (float)wrong/(float)count));
					wrong++;
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
