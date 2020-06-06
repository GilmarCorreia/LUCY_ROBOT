package Swing;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
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
	
	public SwingArmControl(Bioloid Lucy) throws InterruptedException, Exception {
		super("Touch Sensor Window");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(new Dimension(800,400));
		setLocationRelativeTo(null);
		init(Lucy);
		setVisible(true);
	}
	
	private void init(Bioloid Lucy) throws InterruptedException, Exception { 
		JProgressBar M0 = new JProgressBar();
		JProgressBar M1 = new JProgressBar();
		JProgressBar M2 = new JProgressBar();
		
		M0.setFont(new Font ("Arial", Font.BOLD,20));
		M1.setFont(new Font ("Arial", Font.BOLD,20));
		M2.setFont(new Font ("Arial", Font.BOLD,20));
		
		angles[0] = Lucy.getPosition(2);
		Thread.sleep(2000);
		angles[1] = Lucy.getPosition(4);
		Thread.sleep(2000);
		angles[2] = Lucy.getPosition(6);
		Thread.sleep(2000);
		
		M0.setValue(100*(angles[0]/1023));
		M1.setValue(100*(angles[1]/1023));
		M2.setValue(100*(angles[2]/1023));
		
		JButton b1 = new JButton(">");
		JButton b2 = new JButton("<");
		JButton b3 = new JButton(">");
		JButton b4 = new JButton("<");
		JButton b5 = new JButton(">");	
		JButton b6 = new JButton("<");
		
		JPanel window = new JPanel();

		window.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(0, 0, 2, 50);
		
		gbc.gridwidth = 1;
		window.add(b2,gbc);
		gbc.gridx++;
		window.add(M0,gbc);
		gbc.gridx++;
		window.add(b1,gbc);
		
		gbc.gridx = 0;
		gbc.gridy++;
		window.add(b4,gbc);
		gbc.gridx++;
		window.add(M1,gbc);
		gbc.gridx++;
		window.add(b3,gbc);
		
		gbc.gridx = 0;
		gbc.gridy++;
		window.add(b6,gbc);
		gbc.gridx++;
		window.add(M2,gbc);
		gbc.gridx++;
		window.add(b5,gbc);
				
		add(window);
		
		b1.addActionListener(new ActionListener() {
	         public void actionPerformed(ActionEvent e) {
	            try {
					Lucy.move(2,++angles[0]);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	         }          
	    });
		
		b2.addActionListener(new ActionListener() {
	         public void actionPerformed(ActionEvent e) {
	            try {
					Lucy.move(2,--angles[0]);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	         }          
	    });
		
		b3.addActionListener(new ActionListener() {
	         public void actionPerformed(ActionEvent e) {
	            try {
					Lucy.move(4,++angles[1]);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	         }          
	    });
		
		b4.addActionListener(new ActionListener() {
	         public void actionPerformed(ActionEvent e) {
	            try {
					Lucy.move(4,--angles[1]);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	         }          
	    });
		
		b5.addActionListener(new ActionListener() {
	         public void actionPerformed(ActionEvent e) {
	            try {
					Lucy.move(6,++angles[2]);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	         }          
	    });
		
		b6.addActionListener(new ActionListener() {
	         public void actionPerformed(ActionEvent e) {
	            try {
					Lucy.move(6,--angles[2]);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	         }          
	    });
		
	}
	
	public int[] getAngles() {
		return angles;
	}
}
