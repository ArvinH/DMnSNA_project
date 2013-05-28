package test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * 
 * @author Arvin
 *
 */
public class FindCoauthor {

	private String personName;
	public FindCoauthor(){
		
	}
	public String[] find(String personName){
		this.personName = personName;
		String dataArray[] = null;
	 try{
		@SuppressWarnings("resource")
		BufferedReader csvFile = new BufferedReader(new FileReader("parserResult/parser_result.csv"));
		String target;
		while((target = csvFile.readLine())!= null){
			 dataArray = target.split(",");
			 if(dataArray[0].equals(personName)){
				 break;
			 }
			 else{
				 continue;
			 }
		}
		
		
	 }catch(IOException e){
		 
	 }
	return dataArray;
	}
}
