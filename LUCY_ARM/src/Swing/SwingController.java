package Swing;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.Timer;

import TouchSensor.FuzzyProperties;
import TouchSensor.TouchSensorSerial;
import arm.Arm;

public class SwingController extends JFrame{
	private static Timer clock;

	public SwingController(FuzzyProperties fuzzyCl, TouchSensorSerial ts, Arm arm, String emotion) {
		super("Controller Window");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(new Dimension(800,400));
		setLocationRelativeTo(null);
		init(fuzzyCl,ts,arm,emotion);
		setVisible(true);
	}

	private void init(FuzzyProperties fuzzyCl, TouchSensorSerial ts, Arm arm, String emotion) {
		JLabel []texts = new JLabel[9];
		for(int i=0;i<texts.length;i++) {
			texts[i] = new JLabel();
			texts[i].setFont(new Font ("Arial", Font.BOLD,20));
		}

		texts[0].setText("Modelo: " + fuzzyCl.getModel());
		texts[1].setText("Emocao Analisada: " + emotion);
	 
		texts[2].setText("RefSig: ");
		texts[3].setText("Error: ");
		texts[4].setText("Kp*Error: ");
		texts[5].setText("M3: ");
		texts[6].setText("Forca: ");
		texts[7].setText("Tempo: ");
		texts[8].setText("Fuzzy: ");
		
		// create a progressbar 
		JProgressBar []pb = new JProgressBar[3];
		
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
	            	
				double value = fuzzyCl.fuzzyClassifier(fuzzyCl.fis,emotion,force,deltaT/1000.0);
				
				value = (double)((Math.abs(value)/50.0)*100.0);
				
				//System.out.println(value);
				double ref = 70.0;
				
				double error = ref-value;
				
				double kp = 0.01;
				double controller = kp*error;
				
				try {
					arm.setPHome(1,1,arm.getPHome()[1][1]+controller);
					arm.getBioloid().move(4, (int)(arm.getPHome()[1][1]));
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			
				/*for(int i =0;i<3;i++) {
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
				}*/
				texts[8].setText("Fuzzy " +emotion + " = " + value);
				texts[6].setText("Forca = " + Math.round((force/1023.0)*100.0)+"%");
				texts[7].setText("Tempo = " + Math.round(deltaT) + "ms");
				
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
		
		gbc.gridwidth = 7;
		window.add(texts[0],gbc);
		gbc.gridy++;
		window.add(texts[1],gbc);
		gbc.gridy++;
		window.add(Box.createVerticalStrut(75),gbc);
		
		gbc.gridwidth = 1;
		gbc.gridx = 0;
		gbc.gridy++;
		window.add(texts[2],gbc); //error
		gbc.gridx++;
		// ARROW
		gbc.gridx++;
		// Sum Block
		gbc.gridx++;
		// ARROW
		window.add(texts[3],gbc); // kp*error
		gbc.gridx++;
		// BLOCK
		gbc.gridx++;
		// ARROW
		gbc.gridx++;
		// Motors
		
		gbc.gridy++;
		gbc.gridx = 0;
		gbc.gridx+=3;
		window.add(texts[8],gbc); // emotion
		
		add(window);
		return window;
	}
	
	//public void paint(Graphics g){
	//    g.drawRect(100,100,100,100);
	//}
}
