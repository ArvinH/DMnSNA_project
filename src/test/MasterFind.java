package test;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import loadData.DataLoad;
/**
 * 
 * @author Arvin
 *
 */
public class MasterFind {

	private ArrayList<String> Master = new ArrayList<String>();
	private String person;
	private String[] temp;
	public MasterFind(){
		
	}
	public ArrayList<String> find_baseNumOfCowork(){
		HashMap<String, String[]> coauthorSet = DataLoad.loadCoauthor();
		Collection<String> collection = coauthorSet.keySet();
		Iterator<String> iter = collection.iterator();
		while(iter.hasNext()){
			person = iter.next();
			temp = coauthorSet.get(person);
			if(temp.length >= 500){
				Master.add(person);
			}
		}
		return Master;
	}
	
	public ArrayList<String> find_baseNumOfPublications(){
		HashMap<String, String[]> domainSet = DataLoad.loadDomain();
		Collection<String> collection = domainSet.keySet();
		Iterator<String> iter = collection.iterator();
		while(iter.hasNext()){
			person = iter.next();
			temp = domainSet.get(person);
			if(temp.length >= 300){
				Master.add(person);
			}
		}
		return Master;
	}	
	public static void main(String[] args){
		MasterFind masterfind = new MasterFind();
		ArrayList<String> Master = masterfind.find_baseNumOfCowork();
		FileWriter fw;
		try {
			fw = new FileWriter("parserResult/master.csv");
			PrintWriter pw = new PrintWriter(fw);
			for(int i = 0; i < Master.size(); i++){
				pw.println(Master.get(i));
			}
			pw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}
