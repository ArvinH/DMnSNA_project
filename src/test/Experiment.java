package test;
/**
 * 
 * @author Arvin
 *
 */
public class Experiment {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		TestMain experimentTest = new TestMain();
		
		//for(double i = 0; i <=0.3; i+=0.3){
		experimentTest.test("Rajeev Jain", "_Rajeev Jain"+"_cheked_pruning", true);
		experimentTest.test("Rajeev Jain", "_Rajeev Jain"+"_cheked_new_", false);
		//}
		
		
	}

}
