package test;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import loadData.DataLoad;
import dataRanking.EdgeRank;

public class TestMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			BufferedReader buf = new BufferedReader(new InputStreamReader(System.in));
			System.out.println("Who are you? ");
			String TheGuy = buf.readLine();
			// record the time
			long start = System.currentTimeMillis( );
			HashMap<String, String[]> CoauthorSet = new HashMap<String, String[]>();
			HashMap<String, String[]> DomainSet = new HashMap<String, String[]>();
			DataLoad dataLoad = new DataLoad();
			
			System.out.println("So...you are " + TheGuy);
			System.out.println("The person you should and you could know is..");
			// read the parser_result.csv to get the co-author map about TheGuy 
			CoauthorSet = dataLoad.loadCoauthor();
			DomainSet = dataLoad.loadDomain();
			String[] co_author = DataFind.findCoauthor(CoauthorSet,TheGuy);
			String[] cc_author_cc = null;
			String temp = null;
			Map<String,String[]> cc_author = new HashMap<String,String[]>();
			// add from index 1 ( cauz' index 0 is author itself )
			for(int i = 1; i < co_author.length; i++){
				//find all co-authors' co-authors (put into map)
				cc_author.put(co_author[i],DataFind.findCoauthor(CoauthorSet,co_author[i]));
			}
			// list all co-authors' co-authors
			Collection<String> collection = cc_author.keySet();
    		Iterator<String> iter = collection.iterator();
    		EdgeRank edgeRank = new EdgeRank();
    		double ac_weight = 0.0;
    		double bc_weight = 0.0;
    		double ab_domainWeight = 0.0;
    		double temp_weight = 0.0;
    		TreeMap<Double, String> max_temp_weight = new TreeMap<Double, String>(); 
    		TreeMap<Double, String> final_weight = new TreeMap<Double, String>();
    		//ArrayList or LinkedList?
    		List<String> A_Co_Co = new ArrayList<String>();
    		List<String> B_Co = new ArrayList<String>();
    		List<String> A_Temp_CoCo = new ArrayList<String>();
    		TreeMap<Double, Double> B_Extend = new TreeMap<Double, Double>();
    		String[] b_co = null;
    		FileWriter fw = new FileWriter("parserResult/smallExResult.csv");
		 	PrintWriter pw = new PrintWriter(fw);
    		while(iter.hasNext()){
    			temp = iter.next();		// temp is current co-author
    			cc_author_cc = cc_author.get(temp); // cc_author_cc is current co-author's co-author arrays
    			A_Temp_CoCo.addAll(Arrays.asList(cc_author_cc));
    			A_Co_Co.addAll(A_Temp_CoCo);
    			// operate A->C weight and C->B weight
    			ac_weight = edgeRank.friendshipRanking(CoauthorSet,TheGuy, temp, DomainSet);
    			
    			if (ac_weight > 0){
	    			//System.out.println("temp:"+temp+"--ac_weight"+ac_weight);
	    			for(int j = 1; j < cc_author_cc.length; j++){
	    				bc_weight = edgeRank.interRanking(CoauthorSet, temp, cc_author_cc[j]);
	    				ab_domainWeight = edgeRank.domainRanking(DomainSet, TheGuy, cc_author_cc[j]);
	    				// Algorithm
	    				b_co = DataFind.findCoauthor(CoauthorSet, cc_author_cc[j]);
	    				B_Co.clear();
	    	    		B_Co.addAll(Arrays.asList(b_co));
	    	    		B_Co.remove(0);
	    	    		double smallWorldExtend = edgeRank.smallWorldExtend(A_Co_Co, B_Co);
	    				if(ab_domainWeight >= 1){
		    				temp_weight = ac_weight*bc_weight*smallWorldExtend;
		    				// need to check if there is same cc_author and co_author
		    			//	System.out.println("small: "+smallWorldExtend+", temp:"+temp_weight);
		    				
		    				if(!cc_author.keySet().contains(cc_author_cc[j])){
		    				pw.println(smallWorldExtend+","+temp_weight);
			    			B_Extend.put(smallWorldExtend, temp_weight);
		    					//what if the same temp_weight? just override?
		    				max_temp_weight.put(temp_weight, cc_author_cc[j]);
		    				}
	    				}
	    			}
	    			// find max value in temp_weight and add ac_weight, then put into final_weight map( record the person b and it weight)
	    			final_weight.put(max_temp_weight.lastKey(), max_temp_weight.get(max_temp_weight.lastKey()));
    			}
    		}
    		long end = System.currentTimeMillis( );
    		
			
    		System.out.println("hightest weight is: "+final_weight.lastKey());
    		System.out.println("and the person is ... "+final_weight.get(final_weight.lastKey()));
    		b_co = DataFind.findCoauthor(CoauthorSet, final_weight.get(final_weight.lastKey()));
    		B_Co.clear();
    		B_Co.addAll(Arrays.asList(b_co));
    		B_Co.remove(0);
    		double smallWorldExtend = edgeRank.smallWorldExtend(A_Co_Co, B_Co);
    		System.out.println("The small world extends: "+smallWorldExtend);
    		
    		long diff = end - start;
    		System.out.println("run time : "+diff/1000);
    		pw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
