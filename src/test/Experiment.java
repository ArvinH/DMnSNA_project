package test;

public class Experiment {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		TestMain experimentTest = new TestMain();
		
		//for(double i = 0; i <=0.3; i+=0.3){
		experimentTest.test("Yeonseung Ryu", "_Yeonseung Ryu"+"_new_pruning", true);
		experimentTest.test("Yeonseung Ryu", "_Yeonseung Ryu"+"_new_", false);
		//}
		
		
	}

}
