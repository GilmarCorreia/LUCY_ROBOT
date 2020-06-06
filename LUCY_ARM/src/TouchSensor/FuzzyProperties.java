package TouchSensor;
import net.sourceforge.jFuzzyLogic.FIS;
import net.sourceforge.jFuzzyLogic.FunctionBlock;

public class FuzzyProperties {
	
	private String fileName = "fcl/Hertenstein.fcl";
	private String fileName2 = "fcl/Andreasson.fcl";
	private String fileName3 = "fcl/Mix.fcl";
	
	public FIS fis;
	public FIS fis2;
	public FIS fis3;

	public FuzzyProperties(){
		this.fis = FIS.load(fileName, true);
		this.fis2 = FIS.load(fileName2, true);
		this.fis3 = FIS.load(fileName3, true);
		
		if (fis == null || fis2 == null || fis3 ==null){ 
    		// Error while loading?
    		System.err.println("Can't load file: '" + fileName + "'");
    		throw new Error("Can't load the File");
    	}  
		
	}
	
    public double[] fuzzyClassifier(FIS fis, long force, double time) {
    	// Show variables
    	FunctionBlock functionBlock = fis.getFunctionBlock(null);

    	double emotion[] = new double[8];

    	functionBlock.setVariable("forca",force);
    	functionBlock.setVariable("tempo",time);
    	functionBlock.evaluate();
    	emotion[0] = functionBlock.getVariable("raiva").getValue();
    	emotion[1] = functionBlock.getVariable("medo").getValue();
    	emotion[2] = functionBlock.getVariable("nojo").getValue();
    	emotion[3] =  functionBlock.getVariable("tristeza").getValue();
    	emotion[4] =  functionBlock.getVariable("amor").getValue();
    	emotion[5] = functionBlock.getVariable("gratidao").getValue();
    	emotion[6] = functionBlock.getVariable("felicidade").getValue();
    	emotion[7] = functionBlock.getVariable("simpatia").getValue();
    	
    	return emotion;
    }
}
