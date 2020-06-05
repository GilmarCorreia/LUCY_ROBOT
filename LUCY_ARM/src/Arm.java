
public class Arm(){
	
	Bioloid Lucy;
	
	public Arm (){
		this.Lucy = new Bioloid(6);
	}
	
	public FK(float theta1, float theta2, float theta3){
		T1 = 0;
		T2 = 0;
		T3 = 0;

		L1 = 6.790;     // centimeters
		L2 = 6.855;     // centimeters
		L3 = 10.650;    // centimeters
		a1 = 1.320;     // centimeters


		float A10 = new float[4][4];
		A10[1] = new float[4]{cosd(T1), 0, -sind(T1), (-a1*cosd(T1))};
		; sind(T1) 0 cosd(T1) (-a1*sind(T1)); 0 -1 0 L1; 0 0 0 1];
		A21 = [cosd(-T2) -sind(-T2) 0 (L2*cosd(-T2)); sind(-T2) cosd(-T2) 0 (L2*sind(-T2)); 0 0 1 0; 0 0 0 1];
		A32 = [cosd(-T3) 0 -sind(-T3) (L3*cosd(-T3)); sind(-T3) 0 cosd(-T3) (L3*sind(-T3)); 0 -1 0 0; 0 0 0 1];

		A30 = A10*A21*A32
		
	}
}
