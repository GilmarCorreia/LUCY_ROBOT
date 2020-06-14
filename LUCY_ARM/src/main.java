
import arm.Arm;
import Swing.SwingController;
import TouchSensor.FuzzyProperties;
import TouchSensor.TouchSensorSerial;

/**
 * 
 * @author Gilmar Correia
 *
 */

 
public class main{
	public static void main(String[] args) throws InterruptedException, Exception {
		
		TouchSensorSerial TS8 = new TouchSensorSerial();
		FuzzyProperties fuzzyCl = new FuzzyProperties("Hertenstein");
		Thread.sleep(2000);
		
		Arm Lucy = new Arm();
	
		new SwingController(fuzzyCl,TS8,Lucy,"Simpatia");
	}
}
