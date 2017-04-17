/*
 * Stage2.java
 * Author: Tenzin Dendup (u3149399)
 * Date Created: 6 April 2017
 * Date Last Changed: 14 April 2017
 * This is a Java GUI application to calculate number of days alive.
 * It is stage 2 of IIT Java Assignment, University of Canberra.
 * Stage 2 is implemented with GUI and simple functions called from action events.
 * Input: Name and two sets of dates (Date of birth and another date) entered through GUI
 * Output: Number of days alive from date of birth to second date
 * 
 */

package javaProgrammingAssignment;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.Font;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.GregorianCalendar;
import java.awt.event.ActionEvent;

public class Stage2 {
	
	//Constants
	public static final int NUM_DAYS_NORMAL_YEAR = 365;
	public static final int NUM_DAYS_LEAP_YEAR = 366;
	public static final int[] NUMBER_OF_MONTH_DAYS = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
	
	private JFrame jFrame_DaysAliveCalculator;
	private JTextField jTextField_BirthDay;
	private JTextField jTextField_BirthMonth;
	private JTextField jTextField_BirthYear;
	private JTextField jTextField_GivenDay;
	private JTextField jTextField_GivenMonth;
	private JTextField jTextField_GivenYear;
	private JTextField jTextField_Name;
	
	int iBirthDay, iBirthMonth, iBirthYear;
	int iGivenDay, iGivenMonth, iGivenYear;
	int iNumDaysAlive = 0;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Stage2 window = new Stage2();
					window.jFrame_DaysAliveCalculator.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Stage2() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		jFrame_DaysAliveCalculator = new JFrame();
		jFrame_DaysAliveCalculator.setTitle("Days Alive Calculator");
		jFrame_DaysAliveCalculator.setBounds(100, 100, 360, 210);
		jFrame_DaysAliveCalculator.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jFrame_DaysAliveCalculator.getContentPane().setLayout(null);
		
		JPanel jPanel_BirthYear = new JPanel();
		jPanel_BirthYear.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Birth Year", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		jPanel_BirthYear.setBounds(6, 45, 163, 75);
		jFrame_DaysAliveCalculator.getContentPane().add(jPanel_BirthYear);
		jPanel_BirthYear.setLayout(null);
		
		JLabel jLabel_BirthDay = new JLabel("Day");
		jLabel_BirthDay.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
		jLabel_BirthDay.setBounds(16, 22, 29, 16);
		jPanel_BirthYear.add(jLabel_BirthDay);
		
		jTextField_BirthDay = new JTextField();
		jTextField_BirthDay.setBounds(16, 41, 39, 26);
		jPanel_BirthYear.add(jTextField_BirthDay);
		jTextField_BirthDay.setColumns(10);
		
		JLabel jLabel_BirthMonth = new JLabel("Month");
		jLabel_BirthMonth.setBounds(57, 22, 46, 16);
		jPanel_BirthYear.add(jLabel_BirthMonth);
		
		JLabel jLabel_BirthYear = new JLabel("Year");
		jLabel_BirthYear.setBounds(108, 22, 31, 16);
		jPanel_BirthYear.add(jLabel_BirthYear);
		
		jTextField_BirthMonth = new JTextField();
		jTextField_BirthMonth.setBounds(57, 41, 46, 26);
		jPanel_BirthYear.add(jTextField_BirthMonth);
		jTextField_BirthMonth.setColumns(10);
		
		jTextField_BirthYear = new JTextField();
		jTextField_BirthYear.setBounds(108, 41, 46, 26);
		jPanel_BirthYear.add(jTextField_BirthYear);
		jTextField_BirthYear.setColumns(10);
		
		JPanel jPanel_GivenYear = new JPanel();
		jPanel_GivenYear.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Given Year", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		jPanel_GivenYear.setBounds(192, 45, 163, 75);
		jFrame_DaysAliveCalculator.getContentPane().add(jPanel_GivenYear);
		jPanel_GivenYear.setLayout(null);
		
		JLabel jLabel_GivenDay = new JLabel("Day");
		jLabel_GivenDay.setBounds(16, 22, 29, 16);
		jPanel_GivenYear.add(jLabel_GivenDay);
		
		JLabel jLabel_GivenMonth = new JLabel("Month");
		jLabel_GivenMonth.setBounds(57, 22, 46, 16);
		jPanel_GivenYear.add(jLabel_GivenMonth);
		
		JLabel jLabel_GivenYear = new JLabel("Year");
		jLabel_GivenYear.setBounds(109, 22, 31, 16);
		jPanel_GivenYear.add(jLabel_GivenYear);
		
		jTextField_GivenDay = new JTextField();
		jTextField_GivenDay.setBounds(16, 41, 39, 26);
		jPanel_GivenYear.add(jTextField_GivenDay);
		jTextField_GivenDay.setColumns(10);
		
		jTextField_GivenMonth = new JTextField();
		jTextField_GivenMonth.setBounds(57, 41, 46, 26);
		jPanel_GivenYear.add(jTextField_GivenMonth);
		jTextField_GivenMonth.setColumns(10);
		
		jTextField_GivenYear = new JTextField();
		jTextField_GivenYear.setBounds(108, 41, 46, 26);
		jPanel_GivenYear.add(jTextField_GivenYear);
		jTextField_GivenYear.setColumns(10);
		
		JLabel jLabel_Result = new JLabel("");
		jLabel_Result.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		jLabel_Result.setBounds(55, 124, 300, 29);
		jFrame_DaysAliveCalculator.getContentPane().add(jLabel_Result);
		
		JButton jButton_Calculate = new JButton("Calculate");
		jButton_Calculate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Get values entered by the user in try block to check for valid input
				//Input values are trimmed to remove accidental leading and trailing spaces
				try {
					iBirthDay = Integer.parseInt(jTextField_BirthDay.getText().trim());
					iBirthMonth = Integer.parseInt(jTextField_BirthMonth.getText().trim());
					iBirthYear = Integer.parseInt(jTextField_BirthYear.getText().trim());
					iGivenDay = Integer.parseInt(jTextField_GivenDay.getText().trim());
					iGivenMonth = Integer.parseInt(jTextField_GivenMonth.getText().trim());
					iGivenYear = Integer.parseInt(jTextField_GivenYear.getText().trim());
					
					//Check for days greater than 31 and months greater than 12
					if(iBirthDay > 31 || iGivenDay > 31 || iBirthMonth > 12 || iGivenMonth > 12)
						jLabel_Result.setText("Error in Input");
					else {
						//Input has no error.
						calculateNumberOfDays();
						//After calculation is done, Display the result in GUI
						jLabel_Result.setText(jTextField_Name.getText() + " is alive for " + Integer.toString(iNumDaysAlive) + " days");
					}
					
				}
				catch(NumberFormatException e1) {
					//input is invalid
					jLabel_Result.setText("Error in Input");
				}
			}
		});
		
		jButton_Calculate.setBounds(6, 153, 86, 29);
		jFrame_DaysAliveCalculator.getContentPane().add(jButton_Calculate);
		
		JLabel jLabel_ResultLabel = new JLabel("Result:");
		jLabel_ResultLabel.setBounds(6, 124, 47, 29);
		jFrame_DaysAliveCalculator.getContentPane().add(jLabel_ResultLabel);
		
		JButton jButton_Quit = new JButton("Quit");
		jButton_Quit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0); 	// Exit the application when user clicks on Quit button
			}
		});
		jButton_Quit.setBounds(291, 153, 64, 29);
		jFrame_DaysAliveCalculator.getContentPane().add(jButton_Quit);
		
		JLabel jLabel_Name = new JLabel("Name:");
		jLabel_Name.setBounds(62, 17, 47, 16);
		jFrame_DaysAliveCalculator.getContentPane().add(jLabel_Name);
		
		jTextField_Name = new JTextField();
		jTextField_Name.setBounds(109, 12, 147, 26);
		jFrame_DaysAliveCalculator.getContentPane().add(jTextField_Name);
		jTextField_Name.setColumns(10);
		
		JButton jButton_Reset = new JButton("Reset");
		jButton_Reset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Reset all fields to empty when user clicks on Reset button
				jTextField_BirthDay.setText(null);
				jTextField_BirthMonth.setText(null);
				jTextField_BirthYear.setText(null);
				jTextField_GivenDay.setText(null);
				jTextField_GivenMonth.setText(null);
				jTextField_GivenYear.setText(null);
				jTextField_Name.setText(null);
				jLabel_Result.setText(null);
				
			}
		});
		jButton_Reset.setBounds(141, 153, 78, 29);
		jFrame_DaysAliveCalculator.getContentPane().add(jButton_Reset);
		
	}

	/** Functions to calculate number of days when user clicks on Calculate button
	 *  These functions are same ones used in Stage 1.
	 */
	protected void calculateNumberOfDays() {
		
		int iNumFullYear = 0;
		int iNumLeapYear = 0;
		int iNumNormalYear = 0;
		
		// Calculate the number of full years. If we omit the birth year and the given year, the years in between are full years
		iNumFullYear = iGivenYear - iBirthYear - 1;
				
		// Now calculate the number of leap years in those full years
		for(int iI=iBirthYear + 1; iI < iGivenYear; iI++) {
			if(new GregorianCalendar().isLeapYear(iI))
				iNumLeapYear++;
			}
				
		iNumNormalYear = iNumFullYear - iNumLeapYear;
		
		//Number of Days alive is the sum of days in birth year, given year and full years in between
		iNumDaysAlive = calculateDaysBirthYear(iBirthDay, iBirthMonth, iBirthYear) + calculateDaysGivenYear(iGivenDay, iGivenMonth, iGivenYear) + calculateDaysFullYears(iNumNormalYear, iNumLeapYear);
	}

	private int calculateDaysFullYears(int iNumNormalYear, int iNumLeapYear) {
		
		return((iNumNormalYear * NUM_DAYS_NORMAL_YEAR) + (iNumLeapYear * NUM_DAYS_LEAP_YEAR));
	}

	private int calculateDaysGivenYear(int iDay, int iMonth, int iYear) {

		int iNumDays = 0;
		
		//Calculate number of days from January to iMonth - 1
		for(int iI = 0; iI < iMonth - 1; iI++)
			iNumDays += NUMBER_OF_MONTH_DAYS[iI];
		
		//Add the days in given month which is iDate
		iNumDays += iDay;
		
		//Check for LEAP year && month beyond February. If TRUE add 1 to iNumDays
		if(new GregorianCalendar().isLeapYear(iYear) && iMonth > 2)
			iNumDays++;
		
		return(iNumDays);

	}

	private int calculateDaysBirthYear(int iDay, int iMonth, int iYear) {

		int iNumDays = 0;
		
		//First calculate number of days in the birth month
		iNumDays = NUMBER_OF_MONTH_DAYS[iMonth - 1] - iDay + 1; // +1 is to make the birth date inclusive
		
		//Now calculate remaining number of days in birth year
		for(int iI = iMonth + 1; iI <= 12; iI++)
			iNumDays += NUMBER_OF_MONTH_DAYS[iI - 1];
		
		//Check for LEAP year && February month. If TRUE add 1 to iNumDays;
		if(new GregorianCalendar().isLeapYear(iYear) && iMonth == 2)
			iNumDays++;

		return(iNumDays);
	}
}
