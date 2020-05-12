
/**
 * 
 * @author Gilmar Correia
 *
 */
public class main{
	public static void main(String[] args) {
		TouchSensorSerial TS8 = new TouchSensorSerial();
		FuzzyProperties fuzzyCl = new FuzzyProperties();
		new SwingProgressBar(fuzzyCl,TS8);
	}
}