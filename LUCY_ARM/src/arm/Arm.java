package arm;

import Bioloid.Bioloid;
import Swing.SwingArmControl;

public class Arm{
	
	private Bioloid Lucy;
	
	private double L1 = 6.790;     // centimeters
	private double L2 = 6.855;     // centimeters
	private double L3 = 10.650;    // centimeters
	private double a1 = 1.320;     // centimeters
	
	private double pHome[] = new double[3]; 
	
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
		  		  		  {0           ,-1, 0            , L3},
						  {0           , 0, 0            , 1}};

		double A30[][] = matrixMultiplication(A10,matrixMultiplication(A21,A32));
		
		return A30;
	}
	
	private double[] IK(double x, double y, double z) {
		
		double T1 = Math.atan2(y,x);
		double T3 = -(Math.acos((Math.pow(x,2) +Math.pow(y, 2) + Math.pow((z-L1),2) - Math.pow(L3,2) - Math.pow((L2-a1),2))/(2*(L2-a1)*L3)));

		double tangPhi = (z - L1)/(Math.sqrt(Math.pow(x,2)+Math.pow(y,2)));
		double tangBeta = (Math.sin(T3)*L3)/((L2-a1)+(Math.cos(T3)*L3));

		double T2 = Math.atan2((tangPhi - tangBeta),(1+(tangPhi*tangBeta)));

		double angles[] = new double[] {Math.toDegrees(T1), Math.toDegrees(T2), Math.toDegrees(T3)};
		
		double anglesToDec[] = new double[] {1023-(512+((511/150)*(90+angles[0]))), 1023-(512+((angles[1]-90)*(511/150))), 1023-(512+(511/150)*angles[2])};
		
		System.out.println("M0:"+ anglesToDec[0]);
		System.out.println("M1:"+ anglesToDec[1]);
		System.out.println("M2:"+ anglesToDec[2]);
		
		return anglesToDec;
	}
	
	public void setHome() throws InterruptedException, Exception{
		SwingArmControl sac = new SwingArmControl(Lucy);
		
		while(!sac.getDone());
		
		int angles[] = sac.getAngles();
		double orientation[][] = FK(angles[0],angles[1],angles[2]);  
		
		pHome = new double[] {orientation[0][3],orientation[1][3],orientation[2][3]};
		
		System.out.println("pHome = (" + pHome[0]+","+pHome[1]+","+pHome[2]+")");
	}
	
	private double[][] matrixMultiplication(double[][] M1, double[][] M2) {
		if(M1[1].length == M2.length) {
		
		
		double M[][] = new double[M1.length][M2[0].length];
		
		int i = 0;
		for(int rows = 0; rows<M.length; rows++){
			for(int cols = 0;cols < M[0].length; cols++) {
				for(int j = 0;i<M1[0].length && j<M2.length;j++) {
					M[rows][cols] = M[rows][cols] + (M1[i][j] * M2[j][i]); 
				}		
			}
			i++;
		}
			return M;
		}
		else
			throw new Error("Invalid Multiplication, check matrix sizes");	
	}
}
