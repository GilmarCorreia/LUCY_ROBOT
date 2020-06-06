package Swing;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import Bioloid.Bioloid;


public class SwingArmControl extends JFrame{

	private int angles[] = new int[3];
	private boolean done = false;
	private int increment = 0;
	
	public SwingArmControl(Bioloid Lucy) throws InterruptedException, Exception {
		super("Touch Sensor Window");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(new Dimension(600,300));
		setLocationRelativeTo(null);
		init(Lucy);
		setVisible(true);
	}
	
	private void init(Bioloid Lucy) throws InterruptedException, Exception { 
	
		Lucy.move(2,204);
		Lucy.move(4,818);
		Lucy.move(6,512);
		
		angles[0] = 204;
		angles[1] = 818;
		angles[2] = 512;
		
		JProgressBar M[] = new JProgressBar[3];
		
		for(int index = 0; index<M.length;index++) {
			M[index] = new JProgressBar();
			M[index].setValue(100*(angles[index]/1023));
			M[index].setStringPainted(true);
			M[index].setString("M"+index+" : "+ angles[index]);
			M[index].setSize(new Dimension(100,50));
		}
		
		JButton B[] = new JButton[10];
		
		B[0] = new JButton(">");
		B[1] = new JButton("<");
		B[2] = new JButton(">");
		B[3] = new JButton("<");
		B[4] = new JButton(">");	
		B[5] = new JButton("<");
		B[6] = new JButton("Done");
		B[7] = new JButton("100");
		B[8] = new JButton("10");
		B[9] = new JButton("1");
		
		JLabel T[] = new JLabel[2];
		for(int i=0;i<T.length;i++) {
    		T[i] = new JLabel();
    		T[i].setFont(new Font ("Arial", Font.BOLD,20));
    	}
		
		T[0].setText("Defining pHome for Lucy Arm");
		T[1].setText("Increment:");
		
		add(M,B,T);
		
		actionListeners(Lucy,M,B);		
	}
	
	private void add(JProgressBar M[], JButton B[], JLabel T[]) {
		JPanel window = new JPanel();

		window.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(0, 0, 2, 50);
		
		gbc.gridwidth = 5;
		window.add(T[0],gbc);
		gbc.gridx+=3;
		window.add(T[1],gbc);
		gbc.gridy++;
		
		gbc.gridx = 0;
		gbc.gridwidth = 1;
		window.add(B[1],gbc);
		gbc.gridx++;
		window.add(M[0],gbc);
		gbc.gridx++;
		window.add(B[0],gbc);
		gbc.gridx+=2;
		window.add(B[7],gbc);
		
		gbc.gridx = 0;
		gbc.gridy++;
		window.add(B[3],gbc);
		gbc.gridx++;
		window.add(M[1],gbc);
		gbc.gridx++;
		window.add(B[2],gbc);
		gbc.gridx+=2;
		window.add(B[8],gbc);
		
		gbc.gridx = 0;
		gbc.gridy++;
		window.add(B[5],gbc);
		gbc.gridx++;
		window.add(M[2],gbc);
		gbc.gridx++;
		window.add(B[4],gbc);
		gbc.gridx+=2;
		window.add(B[9],gbc);
				
		gbc.gridx = 0;
		gbc.gridy++;
		window.add(B[6],gbc);		
		add(window);
		
	}
	
	private void actionListeners(Bioloid Lucy, JProgressBar[] M, JButton[] B) {
		B[0].addActionListener(new ActionListener() {
	         public void actionPerformed(ActionEvent e) {
	            try {
	            	angles[0] = angles[0] + increment;
					Lucy.move(2,angles[0]);
					M[0].setValue(100*(angles[0]/1023));
					M[0].setString("M0 : "+ angles[0]);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	         }          
	    });
		
		B[1].addActionListener(new ActionListener() {
	         public void actionPerformed(ActionEvent e) {
	            try {
	            	angles[0] = angles[0] - increment;
					Lucy.move(2,angles[0]);
					M[0].setValue(100*(angles[0]/1023));
					M[0].setString("M0 : "+ angles[0]);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	         }          
	    });
		
		B[2].addActionListener(new ActionListener() {
	         public void actionPerformed(ActionEvent e) {
	            try {
	            	angles[1] = angles[1] + increment;
					Lucy.move(4,angles[1]);
					M[1].setValue(100*(angles[1]/1023));
					M[1].setString("M1 : "+ angles[1]);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	         }          
	    });
		
		B[3].addActionListener(new ActionListener() {
	         public void actionPerformed(ActionEvent e) {
	            try {
	            	angles[1] = angles[1] - increment;
					Lucy.move(4,angles[1]);
					M[1].setValue(100*(angles[1]/1023));
					M[1].setString("M1 : "+ angles[1]);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	         }          
	    });
		
		B[4].addActionListener(new ActionListener() {
	         public void actionPerformed(ActionEvent e) {
	            try {
	            	angles[2] = angles[2] + increment;
					Lucy.move(6,angles[2]);
					M[2].setValue(100*(angles[2]/1023));
					M[2].setString("M2 : "+ angles[2]);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	         }          
	    });
		
		B[5].addActionListener(new ActionListener() {
	         public void actionPerformed(ActionEvent e) {
	            try {
	            	angles[2] = angles[2] - increment;
					Lucy.move(6,angles[2]);
					M[2].setValue(100*(angles[2]/1023));
					M[2].setString("M2 : "+ angles[2]);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	         }          
	    });
	    
		B[6].addActionListener(new ActionListener() {
	         public void actionPerformed(ActionEvent e) {
	            done = true;
	            System.out.println(done);
	            dispose();
	         }          
	    });
		
		B[7].addActionListener(new ActionListener() {
	         public void actionPerformed(ActionEvent e) {
	        	increment = 100;
	        	B[7].setEnabled(false);
	        	B[8].setEnabled(true);
	        	B[9].setEnabled(true);
	         }          
	    });
		
		B[8].addActionListener(new ActionListener() {
	         public void actionPerformed(ActionEvent e) {
	        	increment = 10;
	        	B[7].setEnabled(true);
	        	B[8].setEnabled(false);
	        	B[9].setEnabled(true);
	         }          
	    });
		
		B[9].addActionListener(new ActionListener() {
	         public void actionPerformed(ActionEvent e) {
	        	increment = 1;
	        	B[7].setEnabled(true);
	        	B[8].setEnabled(true);
	        	B[9].setEnabled(false);
	         }          
	    });
	}
	
	public int[] getAngles() {
		return angles;
	}
	
	public boolean getDone(){
		return this.done;
	}
}
