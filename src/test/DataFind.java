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
		String temp[] = null;
		String tempString = null;
		String ResultArray[] = new String[coauthorArray.length];
		ResultArray[0] = coauthorArray[0];
		for(int i = 1; i < coauthorArray.length; i++){
			tempString = coauthorArray[i];
			temp = tempString.split(":");
			ResultArray[i] = temp[0];
		}
		return ResultArray;
	}
	public String[] findFriendship(HashMap<String, String[]> coauthorResult, String personName){
		String coauthorArray[] = coauthorResult.get(personName);
		return coauthorArray;
	}
}
