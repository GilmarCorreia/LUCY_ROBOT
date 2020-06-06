import FX.AnimationProgressBar;
import TouchSensor.FuzzyProperties;
import TouchSensor.TouchSensorSerial;
import javafx.application.Application;
import javafx.stage.Stage;

public class mainFX extends Application{
	
	TouchSensorSerial TS8 = new TouchSensorSerial();
    
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		FuzzyProperties fuzzyCl = new FuzzyProperties();
		AnimationProgressBar.run(primaryStage,fuzzyCl,TS8);
	}

}

