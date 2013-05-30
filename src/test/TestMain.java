package test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import loadData.DataLoad;
import dataRanking.EdgeRank;

public class TestMain {

	/**
	 * @author ArvinH
	 */
	private static SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
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
			//FindCoauthor fc = new FindCoauthor();
			DataFind fc = new DataFind();
			String[] co_author = fc.findCoauthor(CoauthorSet,TheGuy);
			String[] cc_author_cc = null;
			String temp = null;
			Map<String,String[]> cc_author = new HashMap<String,String[]>();
			// add from index 1 ( cauz' index 0 is author itself )
			for(int i = 1; i < co_author.length; i++){
				//find all co-authors' co-authors (put into map)
				cc_author.put(co_author[i],fc.findCoauthor(CoauthorSet,co_author[i]));
			}
			// list all co-authors' co-authors
			Collection collection = cc_author.keySet();
    		Iterator iter = collection.iterator();
    		EdgeRank edgeRank = new EdgeRank();
    		double ac_weight = 0.0;
    		double ab_weight = 0.0;
    		double bc_weight = 0.0;
    		double temp_weight = 0.0;
    		TreeMap<Double, String> max_temp_weight = new TreeMap<Double, String>(); 
    		TreeMap<Double, String> final_weight = new TreeMap<Double, String>();
    		while(iter.hasNext()){
    			temp = (String) iter.next();		// temp is current co-author
    			cc_author_cc = cc_author.get(temp); // cc_author_cc is current co-author's co-author arrays
    			// operate A->C weight and C->B weight
    			ac_weight = edgeRank.domainRanking(DomainSet, TheGuy, temp);
    			
    			for(int j = 1; j < cc_author_cc.length; j++){
    				bc_weight = edgeRank.interRanking(CoauthorSet, temp, cc_author_cc[j]);
    				ab_weight = edgeRank.interRanking(CoauthorSet, TheGuy, cc_author_cc[j]);
    				// Algorithm
    				temp_weight = ac_weight/(bc_weight+ab_weight);
    				// need to check if there is same cc_author and co_author
    				if(!cc_author.keySet().contains(cc_author_cc[j])){
    				max_temp_weight.put(temp_weight, cc_author_cc[j]);
    				}
    			}
    			// find max value in temp_weight and add ac_weight, then put into final_weight map( record the person b and it weight)
    			final_weight.put(max_temp_weight.firstKey()+ac_weight, max_temp_weight.get(max_temp_weight.firstKey()));
    		}
    		long end = System.currentTimeMillis( );
    		
			
    		System.out.println("hightest weight is: "+final_weight.firstKey());
    		System.out.println("and the person is ... "+final_weight.get(final_weight.firstKey()));
    		
    		
    		
    		long diff = end - start;
    		System.out.println("run time : "+diff/1000);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
