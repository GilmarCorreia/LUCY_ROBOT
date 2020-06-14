package Swing;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;

import TouchSensor.FuzzyProperties;
import TouchSensor.TouchSensorSerial;
import arm.Arm;

public class SwingController extends JFrame{
	private static Timer clock;
	
    private JPanel window;

	public SwingController() {//FuzzyProperties fuzzyCl, TouchSensorSerial ts, Arm arm, String emotion) {
		super("Controller Window");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 850, 600);
		
		window = new JPanel();
		//setContentPane(window);
		
		init();//fuzzyCl,ts,arm,emotion);
		
		window.setLayout(null);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	private void init() {//FuzzyProperties fuzzyCl, TouchSensorSerial ts, Arm arm, String emotion) {
		
		JLabel []texts = new JLabel[9];
		
		for(int i=0;i<texts.length;i++) {
			texts[i] = new JLabel();
			texts[i].setFont(new Font("Arial", Font.BOLD, 16));
		}
		
		// create a progressbar 
		JProgressBar []pb = new JProgressBar[3];
				
		for(int i = 0;i<pb.length;i++) {
			pb[i] = new JProgressBar();
			pb[i].setStringPainted(true);
			pb[i].setValue(0);
			pb[i].setSize(new Dimension(50,50));
		}

		texts[0].setText("Modelo: ");//+ fuzzyCl.getModel());
		texts[0].setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 20));
		texts[0].setBounds((int)(400-(texts[0].getPreferredSize().getWidth()/2)),30, (int)(texts[0].getPreferredSize().getWidth()), (int)(texts[0].getPreferredSize().getHeight()));
		window.add(texts[0]);
		
		
		texts[1].setText("Emocao Analisada: ");// + emotion);
		texts[1].setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 20));
		texts[1].setBounds((int)(400-(texts[1].getPreferredSize().getWidth()/2)), 80,(int)(texts[1].getPreferredSize().getWidth()), (int)(texts[1].getPreferredSize().getHeight()));
		window.add(texts[1]);
		
		
		texts[2].setText("RefSig");
		texts[2].setBounds(70, 150,(int)(texts[2].getPreferredSize().getWidth()), (int)(texts[2].getPreferredSize().getHeight()));
		window.add(texts[2]);
		
		pb[0].setValue(0);
		pb[0].setBounds(20, 175,(int)(pb[0].getPreferredSize().getWidth()), (int)(pb[0].getPreferredSize().getHeight()));
		window.add(pb[0]);
		
		texts[3].setText("Error: ");
		texts[4].setText("Kp*Error: ");
		texts[5].setText("M3: ");
		texts[6].setText("Forca: ");
		texts[7].setText("Tempo: ");
		texts[8].setText("Fuzzy: ");
		
		
		
		this.add(window); 
//		clock = new Timer(1, new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				int force = ts.getForce();
//				double initialTime = ts.getMillisInitialTime();
//				double currentTime = System.currentTimeMillis();
//				double deltaT = currentTime-initialTime;
//	            	
//				double value = fuzzyCl.fuzzyClassifier(fuzzyCl.fis,emotion,force,deltaT/1000.0);
//				
//				value = (double)((Math.abs(value)/50.0)*100.0);
//				
//				//System.out.println(value);
//				double ref = 70.0;
//				
//				double error = ref-value;
//				
//				double kp = 0.01;
//				double controller = kp*error;
//				
//				try {
//					arm.setPHome(1,1,arm.getPHome()[1][1]+controller);
//					arm.getBioloid().move(4, (int)(arm.getPHome()[1][1]));
//				} catch (InterruptedException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
//			
//				/*for(int i =0;i<3;i++) {
//					for(int j = 0;j<8;j++) {
//						if(i == 0) {
//							pb[j+(8*i)].setValue((int)((Math.abs(values[j])/50.0)*100.0));
//							pb[j+(8*i)].setString((int)((Math.abs(values[j])/50.0)*100.0) + "%");
//						}
//						else if(i == 1) {
//							pb[j+(8*i)].setValue((int)((Math.abs(values2[j])/50.0)*100.0));
//							pb[j+(8*i)].setString((int)((Math.abs(values2[j])/50.0)*100.0) + "%");
//						}
//						else if(i == 2) {
//							pb[j+(8*i)].setValue((int)((Math.abs(values3[j])/50.0)*100.0));
//							pb[j+(8*i)].setString((int)((Math.abs(values3[j])/50.0)*100.0) + "%");
//						}
//					}
//				}*/
//				texts[8].setText("Fuzzy " +emotion + " = " + value);
//				texts[6].setText("Forca = " + Math.round((force/1023.0)*100.0)+"%");
//				texts[7].setText("Tempo = " + Math.round(deltaT) + "ms");
//				
//				//System.out.println("Entrei");
//			}
//		});
//		
//		
//		clock.setRepeats(true);
//		clock.start();
		
	}
	/*
	private void add(JLabel texts[], JProgressBar pb[]) {
		JPanel window = new JPanel();

		window.setLayout(new GridBagLayout());
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(0, 0, 2, 50);
		
		gbc.gridwidth = 7;
		window.add(texts[0],gbc);
		gbc.gridy++;
		window.add(texts[1],gbc);
		gbc.gridy++;
		window.add(Box.createVerticalStrut(50),gbc);
		
		gbc.gridwidth = 1;
		gbc.gridx = 0;
		gbc.gridy++;
		window.add(texts[2],gbc); //error
		gbc.gridx++;
		window.add(Box.createHorizontalStrut(100),gbc); // ARROW
		gbc.gridx++;
		// Sum Block
		gbc.gridx++;
		// ARROW
		window.add(texts[3],gbc); // kp*error
		gbc.gridx++;
		//this.rect = new RectComp();
		//window.add(this.rect,gbc);
		gbc.gridx++;
		// ARROW
		gbc.gridx++;
		// Motors
		
		gbc.gridy++;
		gbc.gridx = 0;
		gbc.gridx+=3;
		window.add(texts[8],gbc); // emotion
		
		this.add(window);
	}*/
	
	@Override
	public void paint(Graphics g){  
		super.paint(g);
		Graphics2D n = (Graphics2D) g;
	    n.draw(new SwingArrow(n,180,210,50,4));
	    n.draw(new SwingSumBlock(n,180+20+50+30,210+2,50));
	    n.draw(new SwingArrow(n,180+20+50+30+30,210,100,4));
	    n.draw(new SwingBlock(n,180+20+50+30+30+130,210,100,50, "Controller"));
	    n.draw(new SwingArrow(n,180+20+50+30+30+130+110,210,100,4));
	    n.draw(new SwingBlock(n,180+20+50+30+30+130+110+130,210,90,50, "  Motors  "));
	}
}


