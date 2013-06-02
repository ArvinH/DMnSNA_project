package dataRanking;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import test.DataFind;
import test.FindDomain;
/**
 * 
 * @author Arvin
 * 
 */
public class EdgeRank {
	
	private double domainWeight = 0.0;
	private double interWeight = 0.0;
	private double friendshipWeight = 0.0;
	private double smallWorldExtend = 0.0;
	public EdgeRank(){
		
	}
	
	/**
	 * 
	 * @param A
	 * person A
	 * @param B
	 * person B
	 * @return
	 * weight of the edge between A and B, according to their common domain
	 */
	public double domainRanking(HashMap<String, String[]> domainResult, String A, String B){
		//FindDomain findDomain = new FindDomain();
		String[] A_domain = DataFind.findDomain(domainResult, A);
		String[] B_domain = DataFind.findDomain(domainResult, B);
		Map<String, Integer> commonDomain = new HashMap<String, Integer>();
		int index_a, index_b;
		for( index_a = 1; index_a < A_domain.length; index_a++){
			for(index_b = 1; index_b < B_domain.length; index_b++){
				// if domain contains '/', means homepage or conf
				if(A_domain[index_a].contains("/") && B_domain[index_b].contains("/")){
				// if both are conf, than compare which conf (not consider which year)
					if(A_domain[index_a].split("/")[0].equals("conf") && B_domain[index_b].split("/")[0].equals("conf")){
						if(A_domain[index_a].split("/")[1].equals(B_domain[index_b].split("/")[1])){
							// same year, the weight plus one
							if(A_domain[index_a].split("/")[2].equals(B_domain[index_b].split("/")[2])){
								domainWeight++;
								commonDomain.put(A_domain[index_a].split("/")[1], 1);
							} // if not same year, then check if it is counted or not 
							else if(!commonDomain.containsKey(A_domain[index_a].split("/")[1])){
								domainWeight++;
								commonDomain.put(A_domain[index_a].split("/")[1], 1);
							}
						}
					}
				} // means journal or workshop or book or something else
				else {	
					if(A_domain[index_a].equals(B_domain[index_b])){
						domainWeight++;
						//System.out.print("A not conf:"+ A_domain[index_a]);
					}
				}
			}
		}
		
		
		return domainWeight;
	}
	/**
	 * 
	 * @param A
	 *  person A
	 * @param B
	 *  person B
	 * @return
	 *  weighting between person A and person B, according to their number of common co-author set and
	 *  domain weighting between common co-author betewwen A/B
	 */
	public double interRanking(HashMap<String, String[]> coauthorResult, String A, String B){
			//FindCoauthor findC = new FindCoauthor();
			String[] A_co = DataFind.findCoauthor(coauthorResult, A);
			String[] B_co = DataFind.findCoauthor(coauthorResult, B);
			// initial a linked list to get the set of A_co and B_co, should use Arrays.asList() to initial the LinkedList
			// cauz' Array.asList() will return an fixed list which can't remove or add or something operation..
			List<String> ListAco = new LinkedList<String>(Arrays.asList(A_co));
			List<String> ListBco = new LinkedList<String>(Arrays.asList(B_co));
			ListAco.remove(0);
			ListBco.remove(0);
			ListAco.retainAll(ListBco);
			/*
			for(int i = 0; i < ListAco.size(); i++){
				System.out.println(ListAco.get(i).toString());
			}
			*/
			interWeight = ListAco.size();
		return interWeight;
	}
	/**
	 * 
	 * @param coauthorResult
	 * @param A
	 * @param B
	 * @return
	 * weighting of person A and person B's friendship, according to their co-edit publications
	 */
	public double friendshipRanking(HashMap<String, String[]> coauthorResult, String A, String B, HashMap<String, String[]> domainResult){
		String[] A_co = DataFind.findFriendship(coauthorResult, A);
		String[] temp = null;
		for(int j = 0; j < A_co.length; j++){
			if(A_co[j].contains(B)){
				temp = A_co[j].split(":");
				friendshipWeight = Double.parseDouble(temp[1]);
			}
		}
		String[] C_Domain = DataFind.findDomain(domainResult, B);
		friendshipWeight = friendshipWeight / C_Domain.length;
		return friendshipWeight;
	}
	public double smallWorldExtend(List<String> A, List<String> B){
			double tempB = B.size();
			B.retainAll(A);
			double BinterA = B.size();
			smallWorldExtend = tempB - BinterA;
		return smallWorldExtend;
	}

}
