import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.text.JTextComponent;

import javafx.scene.control.ProgressBar;

public class SwingProgressBar extends JFrame{
	
		private static Timer clock;

		public SwingProgressBar(FuzzyProperties fuzzyCl, TouchSensorSerial ts) {
			super("Touch Sensor Window");
			setDefaultCloseOperation(EXIT_ON_CLOSE);
			setSize(new Dimension(800,400));
			setLocationRelativeTo(null);
			init(fuzzyCl, ts);
			setVisible(true);
		}

		private void init(FuzzyProperties fuzzyCl, TouchSensorSerial ts) {
			JLabel []texts = new JLabel[13];
	    	for(int i=0;i<texts.length;i++) {
	    		texts[i] = new JLabel();
	    		texts[i].setFont(new Font ("Arial", Font.BOLD,20));
	    	}

	        texts[0].setText("Raiva");
	        texts[1].setText("Medo");
	        texts[2].setText("Nojo");
	        texts[3].setText("Tristeza");
	        texts[4].setText("Amor");
	        texts[5].setText("Gratidao");
	        texts[6].setText("Alegria");
	        texts[7].setText("Simpatia");
	        texts[8].setText("Forca = ");
	        texts[9].setText("Tempo = ");
	        texts[10].setText("Hertenstein");
	        texts[11].setText("Andreasson");
	        texts[12].setText("Mix");
	        
	        // create a progressbar 
	        int []p = new int[8];
	        JProgressBar []pb = new JProgressBar[24];
	        
	        for(int i = 0;i<pb.length;i++) {
	        	pb[i] = new JProgressBar();
	        	pb[i].setStringPainted(true);
	        	pb[i].setValue(0);
	        	pb[i].setSize(new Dimension(100,50));
	        }
			
			
			JPanel window = add(texts,pb);
			
			clock = new Timer(1, new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					int force = ts.getForce();
	            	double initialTime = ts.getMillisInitialTime();
	            	double currentTime = System.currentTimeMillis();
	            	double deltaT = currentTime-initialTime;
	            	
	            	double values[] = fuzzyCl.fuzzyClassifier(fuzzyCl.fis,force,deltaT/1000.0);
	            	double values2[] = fuzzyCl.fuzzyClassifier(fuzzyCl.fis2,force,deltaT/1000.0);
	            	double values3[] = fuzzyCl.fuzzyClassifier(fuzzyCl.fis3,force,deltaT/1000.0);
	            	
	            	for(int i =0;i<3;i++) {
	            		for(int j = 0;j<8;j++) {
	            			if(i == 0) {
	            				pb[j+(8*i)].setValue((int)((Math.abs(values[j])/50.0)*100.0));
	            				pb[j+(8*i)].setString((int)((Math.abs(values[j])/50.0)*100.0) + "%");
	            			}
	            			else if(i == 1) {
	            				pb[j+(8*i)].setValue((int)((Math.abs(values2[j])/50.0)*100.0));
	            				pb[j+(8*i)].setString((int)((Math.abs(values2[j])/50.0)*100.0) + "%");
	            			}
	            			else if(i == 2) {
	            				pb[j+(8*i)].setValue((int)((Math.abs(values3[j])/50.0)*100.0));
	            				pb[j+(8*i)].setString((int)((Math.abs(values3[j])/50.0)*100.0) + "%");
	            			}
	            		}
	            	}
	            	texts[8].setText("Forca = " + Math.round((force/1023.0)*100.0)+"%");
	            	texts[9].setText("Tempo = " + Math.round(deltaT) + "ms");
	            	
	            	//System.out.println("Entrei");
				}
			});
			
			clock.setRepeats(true);
			clock.start();
			
		}
		
		private JPanel add(JLabel texts[], JProgressBar pb[]) {
			JPanel window = new JPanel();

			window.setLayout(new GridBagLayout());
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.gridx = 0;
			gbc.gridy = 0;
			gbc.insets = new Insets(0, 0, 2, 50);
			
			gbc.gridwidth = 2;
			window.add(texts[8],gbc);
			gbc.gridx++;
			gbc.gridx++;
			window.add(texts[9],gbc);
			
			gbc.gridwidth = 1;
			gbc.gridx = 0;
			gbc.gridy++;
			gbc.gridx++;
			window.add(texts[10],gbc);
			gbc.gridx++;
			window.add(texts[11],gbc);
			gbc.gridx++;
			window.add(texts[12],gbc);
			
			gbc.gridx = 0;
			gbc.gridy++;
			window.add(texts[0],gbc);
			gbc.gridx++;
			window.add(pb[0],gbc);
			gbc.gridx++;
			window.add(pb[8],gbc);
			gbc.gridx++;
			window.add(pb[16],gbc);
			
			gbc.gridx = 0;
			gbc.gridy++;
			window.add(texts[1],gbc);
			gbc.gridx++;
			window.add(pb[1],gbc);
			gbc.gridx++;
			window.add(pb[9],gbc);
			gbc.gridx++;
			window.add(pb[17],gbc);
			
			gbc.gridx = 0;
			gbc.gridy++;
			window.add(texts[2],gbc);
			gbc.gridx++;
			window.add(pb[2],gbc);
			gbc.gridx++;
			window.add(pb[10],gbc);
			gbc.gridx++;
			window.add(pb[18],gbc);
			
			gbc.gridx = 0;
			gbc.gridy++;
			window.add(texts[3],gbc);
			gbc.gridx++;
			window.add(pb[3],gbc);
			gbc.gridx++;
			window.add(pb[11],gbc);
			gbc.gridx++;
			window.add(pb[19],gbc);
			
			gbc.gridx = 0;
			gbc.gridy++;
			window.add(texts[4],gbc);
			gbc.gridx++;
			window.add(pb[4],gbc);
			gbc.gridx++;
			window.add(pb[12],gbc);
			gbc.gridx++;
			window.add(pb[20],gbc);
			
			gbc.gridx = 0;
			gbc.gridy++;
			window.add(texts[5],gbc);
			gbc.gridx++;
			window.add(pb[5],gbc);
			gbc.gridx++;
			window.add(pb[13],gbc);
			gbc.gridx++;
			window.add(pb[21],gbc);
			
			gbc.gridx = 0;
			gbc.gridy++;
			window.add(texts[6],gbc);
			gbc.gridx++;
			window.add(pb[6],gbc);
			gbc.gridx++;
			window.add(pb[14],gbc);
			gbc.gridx++;
			window.add(pb[22],gbc);
			
			gbc.gridx = 0;
			gbc.gridy++;
			window.add(texts[7],gbc);
			gbc.gridx++;
			window.add(pb[7],gbc);
			gbc.gridx++;
			window.add(pb[15],gbc);
			gbc.gridx++;
			window.add(pb[23],gbc); 
			gbc.gridx++;
			
			add(window);
			return window;
		}
}
