package com.tenzindendup.iit;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JSeparator;

public class MyFirstJavaSwingGUI {

	private JFrame frmFirstJavaGui;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MyFirstJavaSwingGUI window = new MyFirstJavaSwingGUI();
					window.frmFirstJavaGui.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MyFirstJavaSwingGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmFirstJavaGui = new JFrame();
		frmFirstJavaGui.setTitle("First Java GUI");
		frmFirstJavaGui.setBounds(100, 100, 450, 300);
		frmFirstJavaGui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmFirstJavaGui.getContentPane().setLayout(null);
		
		JLabel jLabel_FirstJavaLabel = new JLabel("First Java Label");
		jLabel_FirstJavaLabel.setBounds(33, 20, 381, 32);
		frmFirstJavaGui.getContentPane().add(jLabel_FirstJavaLabel);
		
		JButton jButton_Quit = new JButton("Quit");
		jButton_Quit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(jButton_Quit.getText().equals("Quit")) {
						jLabel_FirstJavaLabel.setForeground(Color.red);
						jLabel_FirstJavaLabel.setText("You have clicked the Quit button. Dont ever do that!!");
						jButton_Quit.setText("Sorry!!");
				}
				else {
					jLabel_FirstJavaLabel.setForeground(Color.black);
					jLabel_FirstJavaLabel.setText("Thats ok :)");
					jButton_Quit.setText("Quit");
				}
				
			}
		});
		jButton_Quit.setBounds(44, 126, 105, 29);
		frmFirstJavaGui.getContentPane().add(jButton_Quit);
		
		JCheckBox jCheckbox_Event = new JCheckBox("Unchecked");
		jCheckbox_Event.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jCheckbox_Event.setText(jCheckbox_Event.isSelected()?"Checked":"Unchecked");
			}
		});
		jCheckbox_Event.setBounds(44, 81, 128, 23);
		frmFirstJavaGui.getContentPane().add(jCheckbox_Event);
		
		JSeparator jSeparator_seperator = new JSeparator();
		jSeparator_seperator.setForeground(Color.RED);
		jSeparator_seperator.setBounds(44, 102, 135, 12);
		frmFirstJavaGui.getContentPane().add(jSeparator_seperator);
	}
}
