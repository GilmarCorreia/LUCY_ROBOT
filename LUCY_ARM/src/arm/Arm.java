package arm;

import Bioloid.Bioloid;
import Swing.SwingArmControl;
import Swing.SwingController;
import TouchSensor.FuzzyProperties;
import TouchSensor.TouchSensorSerial;

public class Arm{
	
	private Bioloid Lucy;
	
	private double L1 = 6.790;     // centimeters
	private double L2 = 6.855;     // centimeters
	private double L3 = 10.650;    // centimeters
	private double a1 = 1.320;     // centimeters
	
	private double pHome[][] = new double[2][3]; 
	
	public Arm () throws InterruptedException, Exception{
		this.Lucy = new Bioloid(6);
		setHome();
	}
	
	private double[][] FK(double theta1, double theta2, double theta3){
		double T1 = Math.toRadians(theta1);
		double T2 = Math.toRadians(theta2);
		double T3 = Math.toRadians(theta3);

		double A10[][] = {{Math.cos(T1), 0, -Math.sin(T1), (-a1*Math.cos(T1))},
						  {Math.sin(T1), 0, Math.cos(T1) , (-a1*Math.sin(T1))},
						  {0           ,-1, 0            , L1},
						  {0           , 0, 0            , 1}};
		
		double A21[][] = {{Math.cos(-T2), -Math.sin(-T2), 0, (L2*Math.cos(-T2))},
				  		  {Math.sin(-T2), Math.cos(-T2) , 0, (L2*Math.sin(-T2))},
				          {0            , 0             , 1, 0},
				          {0            , 0             , 0, 1}};
		
		double A32[][] = {{Math.cos(-T3), 0, -Math.sin(-T3), (L3*Math.cos(-T3))},
		  		  		  {Math.sin(-T3), 0, Math.cos(-T3) , (L3*Math.sin(-T3))},
		  		  		  {0           ,-1, 0            , 0},
						  {0           , 0, 0            , 1}};

		double A30[][] = matrixMultiplication(A10,matrixMultiplication(A21,A32));
		
		//printMatrix(A10);
		//printMatrix(A21);
		//printMatrix(A32);
		//printMatrix(A30);
		
		return A30;
	}
	
	private double[] IK(double x, double y, double z) {
		
		double T1 = Math.atan2(y,x);
		double T3 = -(Math.acos((Math.pow(x,2) +Math.pow(y, 2) + Math.pow((z-L1),2) - Math.pow(L3,2) - Math.pow((L2-a1),2))/(2*(L2-a1)*L3)));

		double tangPhi = (z - L1)/(Math.sqrt(Math.pow(x,2)+Math.pow(y,2)));
		double tangBeta = (Math.sin(T3)*L3)/((L2-a1)+(Math.cos(T3)*L3));

		double T2 = Math.atan2((tangPhi - tangBeta),(1+(tangPhi*tangBeta)));

		double angles[] = new double[] {Math.toDegrees(T1), Math.toDegrees(T2), Math.toDegrees(T3)};
		
		double anglesToDec[] = anglesToDec(angles);
		
		System.out.println("M0:"+ anglesToDec[0]);
		System.out.println("M1:"+ anglesToDec[1]);
		System.out.println("M2:"+ anglesToDec[2]);
		
		return anglesToDec;
	}
	
	private double[] anglesToDec(double[] angles){
		double anglesToDec[] = new double[] {1023.0-(512.0+((511.0/150.0)*(90.0+angles[0]))), 1023.0-(512.0+((angles[1]-90.0)*(511.0/150.0))), 1023.0-(512.0+(511.0/150.0)*angles[2])};
		
		return anglesToDec;
	}
	
	private double[] decToAngles(int[] dec){
		double decToAngles[] = new double[] {((150.0/511.0)*(511.0-dec[0]))-90.0, ((150.0/511.0)*(511.0-dec[1]))+90.0, (150.0/511.0)*(511.0-dec[2])};
		
		return decToAngles;
	}
	
	public void setHome() throws InterruptedException, Exception{
		SwingArmControl sac = new SwingArmControl(Lucy);
		
		Thread.sleep(2000);
		while(sac.isActive());
		
		double angles[] = decToAngles(sac.getDec());
		double orientation[][] = FK(angles[0],angles[1],angles[2]);  
		
		pHome[0] = new double[] {orientation[0][3],orientation[1][3],orientation[2][3]};
		pHome[1] = new double[] {sac.getDec()[0],sac.getDec()[1],sac.getDec()[2]};
		
		System.out.printf("pHome = (%.2f, %.2f, %.2f)\n", pHome[0][0], pHome[0][1], pHome[0][2]);
		
		//double[] teste = IK(pHome[0], pHome[1], pHome[2]);
	}
	
	private double[][] matrixMultiplication(double[][] M1, double[][] M2) {
		if(M1[1].length == M2.length) {
		
			double M[][] = new double[M1.length][M2[0].length];
			
			for(int rows = 0; rows<M.length; rows++){
				for(int cols = 0;cols < M[0].length; cols++) {
					for(int j = 0;j<M2.length;j++) 
						M[rows][cols] = M[rows][cols] + (M1[rows][j] * M2[j][cols]); 		
				}
			}
			return M;
		}
		else
			throw new Error("Invalid Multiplication, check matrix sizes");	
	}
	
	private void printMatrix(double[][] M){
		for(int i = 0; i< M.length; i++){
			for(int j = 0;j<M[0].length;j++)
				System.out.printf("%.2f , ",M[i][j]);
			System.out.println("");
		}
	}
	
	public Bioloid getBioloid() {
		return this.Lucy;
	}
	
	public void setPHome(int row, int col, double value) {
		this.pHome[row][col] = value;
	}
	
	public double[][] getPHome() {
		return this.pHome;
	}
}
