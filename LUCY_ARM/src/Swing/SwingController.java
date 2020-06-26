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

	public SwingController(FuzzyProperties fuzzyCl, TouchSensorSerial ts, Arm arm, String emotion) {
		super("Controller Window");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1000, 600);
		
		window = new JPanel();
		//setContentPane(window);
		
		init(fuzzyCl,ts,arm,emotion);
		
		window.setLayout(null);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	private void init(FuzzyProperties fuzzyCl, TouchSensorSerial ts, Arm arm, String emotion) {
		
		JLabel []texts = new JLabel[9];
		
		for(int i=0;i<texts.length;i++) {
			texts[i] = new JLabel();
			texts[i].setFont(new Font("Arial", Font.BOLD, 16));
		}
		
		// create a progressbar 
		JProgressBar []pb = new JProgressBar[4];
				
		for(int i = 0;i<pb.length;i++) {
			pb[i] = new JProgressBar();
			pb[i].setStringPainted(true);
			pb[i].setValue(0);
			pb[i].setSize(new Dimension(50,50));
		}

		texts[0].setText("Modelo: "+ fuzzyCl.getModel());
		texts[0].setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 20));
		texts[0].setBounds((int)(500-(texts[0].getPreferredSize().getWidth()/2)),30, (int)(texts[0].getPreferredSize().getWidth()), (int)(texts[0].getPreferredSize().getHeight()));
		window.add(texts[0]);
		
		
		texts[1].setText("Emocao Analisada: " + emotion);
		texts[1].setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 20));
		texts[1].setBounds((int)(500-(texts[1].getPreferredSize().getWidth()/2)), 80,(int)(texts[1].getPreferredSize().getWidth()), (int)(texts[1].getPreferredSize().getHeight()));
		window.add(texts[1]);
		
		
		texts[2].setText("RefSig");
		texts[2].setBounds(70, 150,(int)(texts[2].getPreferredSize().getWidth()), (int)(texts[2].getPreferredSize().getHeight()));
		window.add(texts[2]);
		
		pb[0].setValue(0);
		pb[0].setBounds(20, 175,(int)(pb[0].getPreferredSize().getWidth()), (int)(pb[0].getPreferredSize().getHeight()));
		window.add(pb[0]);
		
		texts[3].setText("Error: ");
		texts[3].setBounds(300, 150, (int)(texts[3].getPreferredSize().getWidth()), (int)(texts[3].getPreferredSize().getHeight()));
		window.add(texts[3]);
		
		texts[4].setText("Kp*Error: ");
		texts[4].setBounds(430, 125, (int)(texts[4].getPreferredSize().getWidth()), (int)(texts[4].getPreferredSize().getHeight()));
		window.add(texts[4]);
		
		texts[5].setText("M2: ");
		texts[5].setBounds(770, 140, (int)(texts[5].getPreferredSize().getWidth()), (int)(texts[5].getPreferredSize().getHeight()));
		window.add(texts[5]);
		
		pb[1].setValue(0);
		pb[1].setBounds(770+35, 140,(int)(pb[1].getPreferredSize().getWidth()), (int)(pb[1].getPreferredSize().getHeight()));
		window.add(pb[1]);
		
		texts[6].setText("Forca: ");
		texts[6].setBounds(230+60, 480-120, (int)(texts[6].getPreferredSize().getWidth()), (int)(texts[6].getPreferredSize().getHeight()));
		window.add(texts[6]);
		
		pb[2].setValue(0);
		pb[2].setBounds(230+60+55, 480-120,(int)(pb[2].getPreferredSize().getWidth()), (int)(pb[2].getPreferredSize().getHeight()));
		window.add(pb[2]);
		
		texts[7].setText("Tempo: ");
		texts[7].setBounds(228+60, 505-120, (int)(texts[7].getPreferredSize().getWidth()), (int)(texts[7].getPreferredSize().getHeight()));
		window.add(texts[7]);
		
		texts[8].setText(emotion+ ": ");
		texts[8].setBounds(228+60, 505-270, (int)(texts[8].getPreferredSize().getWidth()), (int)(texts[8].getPreferredSize().getHeight()));
		window.add(texts[8]);
		
		pb[3].setValue(0);
		pb[3].setBounds(228+60 + (int)(texts[8].getPreferredSize().getWidth()), 505-270,(int)(pb[3].getPreferredSize().getWidth()), (int)(pb[3].getPreferredSize().getHeight()));
		window.add(pb[3]);
		
		this.add(window); 
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
				pb[0].setValue((int) ref);
				pb[0].setString(Math.round(ref) + "%");
				
				pb[1].setValue((int) ((arm.getPHome()[1][1]/1023.0)*100.0));
				pb[1].setString(Math.round((arm.getPHome()[1][1]/1023.0)*100.0) + "%");
				
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
				pb[3].setValue((int) value);
				pb[3].setString(Math.round(value) + "%");
				
				pb[2].setValue((int) ((force/1023.0)*100.0));
				pb[2].setString(Math.round((force/1023.0)*100.0) + "%");
				
				texts[3].setText("Error: " + Math.round(error));
				texts[4].setText("Kp*Error: " + Math.round(controller));
			    texts[7].setText("Tempo: " + Math.round(deltaT) + "ms");
				
				//System.out.println("Entrei");
			}
		});
		
		
		clock.setRepeats(true);
		clock.start();
		
	}

	@Override
	public void paint(Graphics g){  
		super.paint(g);
		Graphics2D n = (Graphics2D) g;
		SwingArrow arrow1 = new SwingArrow(n,180,210,50,4,0);
		SwingSumBlock sumBlock = new SwingSumBlock(n,arrow1.getX()+20+50+30,arrow1.getY()+2,50);
		SwingArrow arrow2 = new SwingArrow(n,sumBlock.getX()+30,arrow1.getY(),100,4,0);
		SwingBlock block1 = new SwingBlock(n,arrow2.getX()+130,arrow1.getY(),100,50, "Controller");
		SwingArrow arrow3 = new SwingArrow(n,block1.getX()+110,arrow1.getY(),100,4,0);
		SwingBlock block3 = new SwingBlock(n,arrow3.getX()+130,arrow1.getY(),90,50, "  Motors  ");
		SwingArrow arrow4 = new SwingArrow(n,block3.getX()+block3.getWidth()+10,arrow1.getY(),75,4,0);
	    n.fillRect(block3.getX()+block3.getWidth()+30, arrow1.getY(), 4, 275);
	    SwingArrow arrow5 = new SwingArrow(n,sumBlock.getX()+75,arrow1.getY()+(block3.getHeight()/2)+250,block3.getX()+block3.getWidth()+30-sumBlock.getX()+4-75,4,1);
	    SwingBlock block4 = new SwingBlock(n,sumBlock.getX()-45,arrow5.getY(),90,50, "    PET   ");
	    SwingArrow arrow6 = new SwingArrow(n,block4.getX()+(block4.getWidth()/2),block4.getY()-(block4.getHeight()/2)-50-10,4,50,2);
	    SwingBlock block5 = new SwingBlock(n,block4.getX(),arrow6.getY()-45-10,90,50, "    UPT   ");
	    SwingArrow arrow7 = new SwingArrow(n,block5.getX()+(block5.getWidth()/2),block5.getY()-(block5.getHeight()/2)-40-10,4,40,2);
	}
}


