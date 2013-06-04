package checking;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
/**
 * 
 * @author Arvin
 *
 */
public class Datacheck {

	public Datacheck(){
		
	}
	public static void percent(){
		String[] dataArray = null;
		String target;
		double numOfone = 0.0;
		double percent = 0.0;
		double[] pnum = new double[10];
		int totalLine = 0;
		FileWriter fw;
		try {
			@SuppressWarnings("resource")
			BufferedReader csvFile = new BufferedReader(new FileReader("parserResult/coauthor_degree.csv"));
			fw = new FileWriter("parserResult/percent_percent.csv");
			PrintWriter pw = new PrintWriter(fw);
			while((target = csvFile.readLine())!= null){
				 totalLine++;
				 dataArray = target.split(",");
				 for(int i = 1; i < dataArray.length; i++){
						if(dataArray[i].equals("1")){
							numOfone++;
						}	
				 }
				 percent = numOfone/(dataArray.length -1);
				 if(percent > 0.0  && percent <= 0.1){
					 pnum[0]++;
				 }else if(percent > 0.1  && percent <= 0.2){
					 pnum[1]++;
				 }else if(percent > 0.2  && percent <= 0.3){
					 pnum[2]++;
				 }else if(percent > 0.3  && percent <= 0.4){
					 pnum[3]++;
				 }else if(percent > 0.4  && percent <= 0.5){
					 pnum[4]++;
				 }else if(percent > 0.5  && percent <= 0.6){
					 pnum[5]++;
				 }else if(percent > 0.6  && percent <= 0.7){
					 pnum[6]++;
				 }else if(percent > 0.7  && percent <= 0.8){
					 pnum[7]++;
				 }else if(percent > 0.8  && percent <= 0.9){
					 pnum[8]++;
				 }else if(percent > 0.9  && percent <= 1.0){
					 pnum[9]++;
				 }
				 
				 
					numOfone = 0;
			}
			for(int j = 0; j < pnum.length; j++){
				pw.println("0."+j+"~0."+(j+1)+","+pnum[j]);
			}
			pw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static boolean checking(String person){
		String[] dataArray = null;
		String target;
		double numOfone = 0;
		boolean notOne = false;
		try {
			BufferedReader csvFile = new BufferedReader(new FileReader("parserResult/coauthor_degree.csv"));
			while((target = csvFile.readLine())!= null){
				 dataArray = target.split(",");
				if(dataArray[0].equals(person)){
				  break;
				}
			}
			for(int i = 1; i < dataArray.length; i++){
				if(dataArray[i].equals("1")){
					numOfone++;
				}
				else{
					notOne = true;
				}
			}
			csvFile.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		double flag = (double)(numOfone/(dataArray.length -1));
		if( flag > (1/2) && notOne){
			return true;
		}
		else{
			return false;
		}
		
	}
	
	public static void main(String[] args){
		/*if(Datacheck.checking("Rajeev Jain")){
			System.out.println("true");
		}
		else{
			System.out.println("false");
		}
		*/
		Datacheck.percent();
	}

}
