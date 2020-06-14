package TouchSensor;
import net.sourceforge.jFuzzyLogic.FIS;
import net.sourceforge.jFuzzyLogic.FunctionBlock;

public class FuzzyProperties {
	
	private String fileName = "fcl/Hertenstein.fcl";
	private String fileName2 = "fcl/Andreasson.fcl";
	private String fileName3 = "fcl/Mix.fcl";
	private String model;
	
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
		
		if (fis2 == null){ 
    		// Error while loading?
    		System.err.println("Can't load file: '" + fileName2 + "'");
    		throw new Error("Can't load the File");
    	}  	
		
		if (fis3 == null){ 
    		// Error while loading?
    		System.err.println("Can't load file: '" + fileName3 + "'");
    		throw new Error("Can't load the File");
    	}  	
	}
	
	public FuzzyProperties(String model){
		
		String fn = null;
		
		if (model.equals(new String("Hertenstein"))){
			this.fis = FIS.load(fileName, true);
			fn = fileName;
			setModel("Hertenstein");
		} else if (model.equals(new String("Andreasson"))) {
			this.fis = FIS.load(fileName2, true);
			fn = fileName2;
			setModel("Andreasson");
		} else if (model.equals(new String("Mix"))) {
			this.fis = FIS.load(fileName3, true);
			fn = fileName3;
			setModel("Mix");
		}
		
		if (fis == null){ 
    		// Error while loading?
    		System.err.println("Can't load file: '" + fn + "'");
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
    	emotion[3] = functionBlock.getVariable("tristeza").getValue();
    	emotion[4] = functionBlock.getVariable("amor").getValue();
    	emotion[5] = functionBlock.getVariable("gratidao").getValue();
    	emotion[6] = functionBlock.getVariable("felicidade").getValue();
    	emotion[7] = functionBlock.getVariable("simpatia").getValue();
    	
    	return emotion;
    }
    
    public double fuzzyClassifier(FIS fis, String Emotion, long force, double time) {
    	// Show variables
    	FunctionBlock functionBlock = fis.getFunctionBlock(null);

    	double emotion = -1;

    	functionBlock.setVariable("forca",force);
    	functionBlock.setVariable("tempo",time);
    	functionBlock.evaluate();
    	
    	if(Emotion.equals(new String("Raiva")))
    		emotion = functionBlock.getVariable("raiva").getValue();
    	else if(Emotion.equals(new String("Medo")))
    		emotion = functionBlock.getVariable("medo").getValue();
    	else if(Emotion.equals(new String("Nojo")))
    		emotion = functionBlock.getVariable("nojo").getValue();
    	else if(Emotion.equals(new String("Tristeza")))
    		emotion = functionBlock.getVariable("tristeza").getValue();
    	else if(Emotion.equals(new String("Amor")))
    		emotion = functionBlock.getVariable("amor").getValue();
    	else if(Emotion.equals(new String("Gratidao")))
    		emotion = functionBlock.getVariable("gratidao").getValue();
    	else if(Emotion.equals(new String("Felicidade")))
    		emotion = functionBlock.getVariable("felicidade").getValue();
    	else if(Emotion.equals(new String("Simpatia")))
    		emotion = functionBlock.getVariable("simpatia").getValue();
    	
    	return emotion;
    }
    
    private void setModel(String model) {
    	this.model = model;
    }
    
    public String getModel() {
    	return this.model;
    }
}
