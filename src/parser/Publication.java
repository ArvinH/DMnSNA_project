package parser;

import java.util.*;

/**
 * @author ley
 * created first in project xml5_coauthor_graph
 * @author ArvinH
 * add some method to extract author's conf/journal and co-author dataset
 */
public class Publication {
    private static Set<Publication> ps= new HashSet<Publication>(650000);
    private static int maxNumberOfAuthors = 0;
    private String key;
    private Person[] authors;	// or editors
    private String domain;		// add publication domain
    public Publication(String key, Person[] persons, String publishDomain) {
        this.key = key;
        authors = persons;
        domain = publishDomain;
        ps.add(this);
        if (persons.length > maxNumberOfAuthors)
            maxNumberOfAuthors = persons.length;
    }
    
    public static int getNumberOfPublications() {
        return ps.size();
    }
    
    public static int getMaxNumberOfAuthors() {
        return maxNumberOfAuthors;
    }
    public String getDomain(){
    	return domain;
    } 
    public Person[] getAuthors() {
        return authors;
    }
    
    static Iterator iterator() {
        return ps.iterator();
    }
}
