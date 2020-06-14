package Swing;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import TouchSensor.FuzzyProperties;
import TouchSensor.TouchSensorSerial;
import arm.Arm;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JProgressBar;

public class SwingController2 extends JFrame {

	private JPanel contentPane;
	
	/**
	 * Create the frame.
	 */
	public SwingController2(FuzzyProperties fuzzyCl, TouchSensorSerial ts, Arm arm, String emotion) {
		super("Controller Window");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Modelo: " + fuzzyCl.getModel());
		lblNewLabel.setBounds(0, 60, 300, 20);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 16));
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Emocao Analisada: " + emotion);
		lblNewLabel_1.setBounds(256, 163, 269, 20);
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 16));
		contentPane.add(lblNewLabel_1);
		
		JProgressBar progressBar = new JProgressBar();
		progressBar.setBounds(41, 216, 10, 14);
		contentPane.add(progressBar);
		setLocationRelativeTo(null);
		//init(fuzzyCl, ts, arm, emotion);
		setVisible(true);
	}

}
