package loadData;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

/** 
 * 
 * @author Arvin
 *
 */
public class DataLoad {
	
	private HashMap<String, String[]> coauthordataresult = new HashMap<String, String[]>();
	private HashMap<String, String[]> domaindataresult = new HashMap<String, String[]>(); 
	public DataLoad(){
		
	}
	public HashMap<String, String[]> loadCoauthor(){
		
		try{
		@SuppressWarnings("resource")
		BufferedReader csvFile = new BufferedReader(new FileReader("parserResult/parser_result2.csv"));
		String[] dataArray;
		String target;
		while((target = csvFile.readLine())!= null){
			 dataArray = target.split(",");
			 coauthordataresult.put(dataArray[0], dataArray); 
		}
		}catch(IOException e){
			
		}
		return coauthordataresult; 
	}	
	public HashMap<String, String[]> loadDomain(){
		
		try{
		@SuppressWarnings("resource")
		BufferedReader csvFile = new BufferedReader(new FileReader("parserResult/authorDomain.csv"));
		String[] dataArray;
		String target;
		while((target = csvFile.readLine())!= null){
			 dataArray = target.split(",");
		domaindataresult.put(dataArray[0], dataArray); 
		}
		}catch(IOException e){
			
		}
		return domaindataresult;
	}
}
