package test;

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
		DataLoad dataload = new DataLoad();
		HashMap<String, String[]> coauthorSet = dataload.loadCoauthor();
		Collection<String> collection = coauthorSet.keySet();
		Iterator<String> iter = collection.iterator();
		while(iter.hasNext()){
			person = iter.next();
			temp = coauthorSet.get(person);
			if(temp.length >= 100){
				Master.add(person);
			}
		}
		return Master;
	}
	
	public ArrayList<String> find_baseNumOfPublications(){
		DataLoad dataload = new DataLoad();
		HashMap<String, String[]> domainSet = dataload.loadDomain();
		Collection<String> collection = domainSet.keySet();
		Iterator<String> iter = collection.iterator();
		while(iter.hasNext()){
			person = iter.next();
			temp = domainSet.get(person);
			if(temp.length >= 50){
				Master.add(person);
			}
		}
		return Master;
	}	
	public static void main(String[] args){
		MasterFind masterfind = new MasterFind();
		ArrayList<String> Master = masterfind.find_baseNumOfPublications();
		for(int i = 0; i < Master.size(); i++){
			System.out.println(Master.get(i));
		}
	}
}