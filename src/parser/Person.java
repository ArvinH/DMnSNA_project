package parser;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

/**
 * @author ley
 * @author ArvinH
 */
public class Person {
    private static int maxPublCount = 0;
    private static int maxNameLength = 0;
    private static Map<String,Person> personMap = new HashMap<String,Person>(600000);
    private static Set<Person> tmpSet = new HashSet<Person>(500);
    
    private String name;
    private Set nameParts;
    private int count;
    private int tmp;  //record how many publication that the author publish
    private Publication[] publs;
    private HashMap<Person,Integer> coauthors = new HashMap<Person, Integer>();
    private Map<String, Integer> domainMap = new HashMap<String, Integer>();
    public Person(String n) {
        name = n;
        count = 0;
        personMap.put(name,this);
        if (maxNameLength < name.length())
            maxNameLength = name.length();
        nameParts = new HashSet();
        StringTokenizer st = new StringTokenizer(name," -");
        while (st.hasMoreTokens()) {
            nameParts.add(st.nextToken());
        }
    }
    
    public void increment() {
        count++;
        if (count > maxPublCount)
            maxPublCount = count;
    }
    
    public String getName() {
        return name;
    }
    
    public Set getNameParts() {
        return nameParts;
    }
    
    public int getNumberOfCoauthors() {
        return (coauthors == null) ? 0 : coauthors.size();
    }
    
    public HashMap<Person, Integer> getCoauthors() {
        return coauthors;
    }
    
    public int getCount() {
        return count;
    }
    
    static public int getMaxPublCount() {
        return maxPublCount;
    }
    
    static public int getMaxNameLength() {
        return maxNameLength;
    }
    
    static public Iterator iterator() {
        return personMap.values().iterator();
    }
    
    static public Person searchPerson(String name) {
        return (Person) personMap.get(name);
    }
    
    static public int numberOfPersons() {
        return personMap.size();
    }
    
    static public void enterPublications() {
        Iterator publIt = Publication.iterator();
        Publication publ;
        Person[] persArray;
        Person pers;
        Person key;
        while (publIt.hasNext()) {
            publ = (Publication) publIt.next();
            persArray = publ.getAuthors();
            if (persArray == null)
                continue;
            if (persArray.length == 0)
                continue;
            for (int i=0; i<persArray.length; i++) {
                pers = (Person) persArray[i];
                if (pers == null)
                    continue;
                if (pers.publs == null) {
                    pers.publs = new Publication[pers.count];
                    pers.tmp = 0;
                }
                pers.publs[pers.tmp++] = publ;
                // check if domain is already in domainMap or not
                if(pers.domainMap.containsKey(publ.getDomain())){
                	pers.domainMap.put(publ.getDomain(),pers.domainMap.get(publ.getDomain())+1);
                }
                else{
                	pers.domainMap.put(publ.getDomain(), 0);
                }
            }
        }
        
        Iterator persIt = Person.iterator();
        Boolean flag = false;
        Collection collection = null;
        Iterator iter = null;
        while (persIt.hasNext()) {
            pers = (Person)persIt.next();
            Person authors[];
            
            tmpSet.clear();
            if (pers.publs != null) {
                for (int i=0; i<pers.publs.length; i++) {
                    publ = pers.publs[i];
                    authors = publ.getAuthors();
                    if (authors == null)
                        continue;
                    for (int j=0; j<authors.length; j++) {
                        if (authors[j] == null)
                            continue;
                        if (authors[j] == pers) 
                            continue;
                     // 
                        collection = pers.coauthors.keySet();
                        iter = collection.iterator();
                        
                        while(iter.hasNext()){
                        	key = (Person) iter.next();
                        	if (key.name.equals(authors[j].name)){
                            	pers.coauthors.put(authors[j], pers.coauthors.get(authors[j])+1);
                            	flag = true;
                            	break;
                            }
                            else{
                            flag = false;
                            }
                        }
                        if ( !flag ){
                        	pers.coauthors.put(authors[j], 1);
                        }
                    }
                }
            }
            flag = false;
        }
    }
    
    static public void printCoauthorTable() {
        Iterator it = Person.iterator();
        int coauthors[] = new int[501];
        Person pers;
        int c;
        
        while (it.hasNext()) {
            pers = (Person) it.next();
            c = pers.getNumberOfCoauthors();
            if (c > 500)
                c = 500;
            coauthors[c]++;
        }
        System.out.println();
        System.out.println("person: coauthors");
        
        System.out.println("Number of coauthors: Number of persons");
        int n = 0;
        for (int j=0; j <= 500; j++) {
            if (coauthors[j] == 0)
                continue;
            n++;
            System.out.print(j + ": " + coauthors[j]+ "  ");
            if (n%5 == 0)
                System.out.println();
        }
        System.out.println();      
    }
    
    static public void printNamePartTable() {
        int numberOfParts[] = new int[11];
        Iterator it = Person.iterator();
        Person pers;
        int x;
        
        while (it.hasNext()) {
            pers = (Person) it.next();
            x = pers.getNameParts().size();
            if (x>10)
                x = 10;
            numberOfParts[x]++;
        }
        System.out.println();
        System.out.println("Number of Name Parts: Number of Persons");
        for (int n = 0; n < 10; n++)
            System.out.print(n + ":" + numberOfParts[n] + " ");
        System.out.println(">=10:" + numberOfParts[10]);
    }
   /* 
    static public void findSimilarNames() {
        Iterator it = Person.iterator();
        Person pers;
        
        System.out.println("Name part permutation:");
        while (it.hasNext()) {
            pers = (Person) it.next();
            for (int i=0; i<pers.coauthors.length; i++) {
                for (int j=i+1; j<pers.coauthors.length; j++) {
                    if (pers.coauthors[i].nameParts.containsAll(pers.coauthors[j].nameParts) ||
                        pers.coauthors[j].nameParts.containsAll(pers.coauthors[i].nameParts))
                        System.out.println(pers.name + ": " +
                                           pers.coauthors[i].name + " - " +
                                	       pers.coauthors[j].name);
                }
            }
        }
    }
    */
    static public void findCoauthorNames(){
    	Iterator it = Person.iterator();
    	Person pers;
    	FileWriter fw = null;
    	PrintWriter pw = null;
    	Person key = null;
		try {
			fw = new FileWriter("parserResult/parser_result2.csv");
		 	pw = new PrintWriter(fw);
	    	System.out.println("Person and their Coauthors");
	    	//pw.println("Person and their Coauthors");
	    	while (it.hasNext()){
	    		pers = (Person) it.next();
	    		//System.out.println(pers.name + " 's Co-authors are: ");
	    		//pw.println(pers.name + " 's Co-authors are: ");
	    		if(pers.coauthors.size() != 0){
	    			pw.print(pers.name+",");
	    		}
	    		else{
	    			pw.print(pers.name);	
	    		}/*
	    		for (int i=0; i< pers.coauthors.length; i++){
	    			System.out.println(pers.coauthors[i].name);
	    			pw.print(pers.coauthors[i].name);
	    			pw.print((i==pers.coauthors.length-1)?"":",");
	    		}
	    		*/
	    		Collection collection = pers.coauthors.keySet();
	    		Iterator iter = collection.iterator();
	    		while(iter.hasNext()){
	    			key = (Person) iter.next();
	    			pw.print((iter.hasNext())?key.name+":"+pers.coauthors.get(key)+":"+",":key.name+":"+pers.coauthors.get(key)+":");
	    		}
	    		//System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
	    		pw.println();
	    	}
	    	
	    	
	    	pw.close();
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			
		}
    }
    static public void findPersonDomain(){
    	Iterator it = Person.iterator();
    	Person pers;
    	FileWriter fw = null;
    	PrintWriter pw = null;
    	String key = null;
		try {
			fw = new FileWriter("parserResult/authorDomin.csv");
		 	pw = new PrintWriter(fw);
		 	//pw.println("Authors' Domain");
	    	while (it.hasNext()){
	    		pers = (Person) it.next();
	    		System.out.println(pers.name + " 's Domain are: ");
	    		//pw.println(pers.name + " 's Domain are: ");
	    		pw.print(pers.name+",");
	    		Collection collection = pers.domainMap.keySet();
	    		Iterator domainit = collection.iterator();
	    		while(domainit.hasNext()){
	    			key = (String)domainit.next();
	    			System.out.print((domainit.hasNext())?key+",":key+"");
	    			pw.print((domainit.hasNext())?key+",":key+"");
	    			//pw.print(key+",");
	    		}
	    		pw.println();
	    		System.out.println("\nxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
	    	}
	    	
	    	
	    	pw.close();
			fw.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			
		}
    }
}
