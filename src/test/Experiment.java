package test;

public class Experiment {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		TestMain experimentTest = new TestMain();
		
		for(double i = 0; i <=0.3; i+=0.3){
		experimentTest.test("Yi Zhang", i, "_Yi Zhang"+"_"+i);
		
		}
		
		
	}

}
