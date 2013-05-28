package test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import dataRanking.EdgeRank;

public class TestMain {

	/**
	 * @author ArvinH
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			BufferedReader buf = new BufferedReader(new InputStreamReader(System.in));
			System.out.println("Who are you? ");
			String TheGuy = buf.readLine();
			System.out.println("So...you are " + TheGuy);
			System.out.println("The person you should and you could know is..");
			// read the parser_result.csv to get the co-author map about TheGuy 
			FindCoauthor fc = new FindCoauthor();
			String[] co_author = fc.find(TheGuy);
			String[] cc_author_cc = null;
			String temp = null;
			Map<String,String[]> cc_author = new HashMap<String,String[]>();
			// add from index 1 ( cauz' index 0 is author itself )
			for(int i = 1; i < co_author.length; i++){
				//find all co-authors' co-authors (put into map)
				cc_author.put(co_author[i],fc.find(co_author[i]));
			}
			// list all co-authors' co-authors
			Collection collection = cc_author.keySet();
    		Iterator iter = collection.iterator();
    		EdgeRank edgeRank = new EdgeRank();
    		double ac_weight = 0.0;
    		double ab_weight = 0.0;
    		double bc_weight = 0.0;
    		double[] temp_weight = null;
    		Map<String, Double> final_weight = new TreeMap<String, Double>();
    		while(iter.hasNext()){
    			temp = (String) iter.next();		// temp is current co-author
    			cc_author_cc = cc_author.get(temp); // cc_author_cc is current co-author's co-author arrays
    			/*System.out.println(temp);
    			for(int j = 1; j < cc_author_cc.length; j++){
    				System.out.print((cc_author_cc.length == j+1)? cc_author_cc[j]+"": cc_author_cc[j]+",");
    			}*/
    			// operate A->C weight and C->B weight
    			ac_weight = edgeRank.domainRanking(TheGuy, temp);
    			
    			for(int j = 1, weight_count = 0; j < cc_author_cc.length; j++, weight_count++){
    				bc_weight = edgeRank.interRanking(temp, cc_author_cc[j]);
    				// Algorithm??
    				temp_weight[j] = ac_weight/bc_weight;
    			}
    			// find max value in temp_weight and add ac_weight, then put into final_weight map( record the person b and it weight)
    			
    			
    			System.out.println();
    		}
    		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}


}
