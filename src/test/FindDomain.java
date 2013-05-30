package test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
/**
 * 
 * @author Arvin
 *
 */
public class FindDomain {
	
	private String personName;
	public FindDomain(){
		
	}
	public String[] find(HashMap<String, String[]> domainResult, String personName){
		this.personName = personName;
		String domainArray[] = domainResult.get(personName);
		return domainArray;
	}
	public String[] find(String personName){
		this.personName = personName;
		String domainArray[] = null;
	 try{
		@SuppressWarnings("resource")
		BufferedReader csvFile = new BufferedReader(new FileReader("parserResult/authorDomin.csv"));
		String target;
		while((target = csvFile.readLine())!= null){
			 domainArray = target.split(",");
			 if(domainArray[0].equals(personName)){
				 break;
			 }
			 else{
				 continue;
			 }
		}
		
	 }catch(IOException e){
		 
	 }
	return domainArray;
	}
}
