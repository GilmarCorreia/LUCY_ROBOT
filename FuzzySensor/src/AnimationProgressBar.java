import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.TilePane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;


public class AnimationProgressBar{
    
    public static void run(Stage primaryStage, FuzzyProperties fuzzyCl, TouchSensorSerial ts){  
        // set title for the stage 
    	primaryStage.setTitle("creating progressbar"); 
  
        // Textos
    	Text []texts = new Text[12];
    	for(int i=0;i<texts.length;i++)
    		texts[i] = new Text(); 
    	
        texts[0].setText("Raiva");
        texts[1].setText("Medo");
        texts[2].setText("Nojo");
        texts[3].setText("Tristeza");
        texts[4].setText("Amor");
        texts[5].setText("Gratidão");
        texts[6].setText("Alegria");
        texts[7].setText("Simpatia");
        texts[8].setText("Força =       Tempo = ");
        texts[9].setText("Hertenstein");
        texts[10].setText("Andreasson");
        texts[11].setText("Mix");
        
        // create a progressbar 
        int []p = new int[8];
        ProgressBar []pb = new ProgressBar[24];
        
        for(int i = 0;i<pb.length;i++)
        	pb[i] = new ProgressBar(); 

        // create a tile pane 
        TilePane r = new TilePane(); 
        
        Timeline updateProgressBar = new Timeline(new KeyFrame(Duration.millis(25), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {            	
            	int force = ts.getForce();
            	double initialTime = ts.getMillisInitialTime();
            	double currentTime = System.currentTimeMillis();
            	double deltaT = currentTime-initialTime;
            	
            	double values[] = fuzzyCl.fuzzyClassifier(fuzzyCl.fis,force,deltaT/1000.0);
            	double values2[] = fuzzyCl.fuzzyClassifier(fuzzyCl.fis2,force,deltaT/1000.0);
            	double values3[] = fuzzyCl.fuzzyClassifier(fuzzyCl.fis3,force,deltaT/1000.0);
            	
            	for(int i =0;i<3;i++) {
            		for(int j = 0;j<8;j++) {
            			if(i == 0)
            				pb[j+(8*i)].setProgress(Math.abs(values[j])/50.0);
            			else if(i == 1)
            				pb[j+(8*i)].setProgress(Math.abs(values2[j])/50.0);
            			else if(i == 2)
            				pb[j+(8*i)].setProgress(Math.abs(values3[j])/50.0);
            		}
            	}
            	texts[8].setText("Força = " + Math.round((force/1023.0)*100.0)+"%    Tempo = " + Math.round(deltaT) + "ms");
              
            }
        }));
        
        updateProgressBar.setCycleCount(Timeline.INDEFINITE);
        updateProgressBar.play();
        
        // add button 
        r.getChildren().addAll(texts[8],texts[9],texts[10],texts[11],texts[0],pb[0],pb[8],pb[16],
        						texts[1],pb[1],pb[9],pb[17],texts[2],pb[2],pb[10],pb[18],texts[3],
        						pb[3],pb[11],pb[19],texts[4],pb[4],pb[12],pb[20],texts[5],pb[5],
        						pb[13],pb[21],texts[6],pb[6],pb[14],pb[22],texts[7],pb[7],pb[15],pb[23]); 
        
        // create a scene 
        Scene sc = new Scene(r, 900, 225); 
  
        // set the scene 
        primaryStage.setScene(sc); 
  
        primaryStage.show(); 
    } 
}

/*
 *         Timeline updateProgressBar = new Timeline(new KeyFrame(Duration.millis(25), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {            	
            	double values[] = fuzzyCl.fuzzyClassifier(fuzzyCl.fis,forca,tempo/1000.0);
            	double values2[] = fuzzyCl.fuzzyClassifier(fuzzyCl.fis2,forca,tempo/1000.0);
            	double values3[] = fuzzyCl.fuzzyClassifier(fuzzyCl.fis3,forca,tempo/1000.0);
            	
            	for(int i =0;i<3;i++) {
            		for(int j = 0;j<8;j++) {
            			if(i == 0)
            				pb[j+(8*i)].setProgress(Math.abs(values[j])/50.0);
            			else if(i == 1)
            				pb[j+(8*i)].setProgress(Math.abs(values2[j])/50.0);
            			else if(i == 2)
            				pb[j+(8*i)].setProgress(Math.abs(values3[j])/50.0);
            		}
            	}
            	texts[8].setText("Força = " + Math.round((forca/1024.0)*100.0)+"%    Tempo = " + tempo/1000 + "s");
                forca++;
                
                if(forca >=1024) {
                	forca = 0;
                	tempo+=2000.0;
                }
            }
        }));
 */
