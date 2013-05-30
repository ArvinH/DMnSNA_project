package test;

import java.util.HashMap;
/**
 * 
 * @author Arvin
 *
 */
public class DataFind {

	public DataFind(){
		
	}
	public String[] findDomain(HashMap<String, String[]> domainResult, String personName){
		String domainArray[] = domainResult.get(personName);
		return domainArray;
	}
	public String[] findCoauthor(HashMap<String, String[]> coauthorResult, String personName){
		String coauthorArray[] = coauthorResult.get(personName);
		return coauthorArray;
	}
}
