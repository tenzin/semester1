/*
 * Stage3.java
 * Author: Tenzin Dendup (u3149399)
 * Date Created: 14 April 2017
 * Date Last Changed: 17 April 2017
 * This is a Java GUI application to calculate number of days alive.
 * It is stage 3 of IIT Java Assignment, University of Canberra.
 * Stage 3 is implemented with GUI and Object.
 * Stage 3 uses two other java source files, Person.java (for Person Object) and MyDate.java (For MyDate object)
 * For drawing purposes, stage 3 uses the MyJPanel inner class from lecture examples, with modifications.
 * Input: List of Names and sets of dates (Date of birth and another date) read from text file
 * Output: Number of days alive and corresponding bar graph on GUI. Also GUI option for user to set date.
 * 
 */

package javaProgrammingAssignment;

import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.event.ListSelectionListener;

import javaProgrammingAssignment.MyDate;
import javaProgrammingAssignment.Person;

import javax.swing.event.ListSelectionEvent;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JTextField;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.border.BevelBorder;

public class Stage3 {

	private JFrame frmDaysAliveCalculator;
	private static ArrayList<Person> jArrayListPeople; //ArrayList to store details of people
	private static ArrayList<Rectangle2D.Double> jArrayListRectangles; //Array List to store rectangles to draw bar graph
	private static ArrayList<MyDate> jArrayListOriginalGivenDates; ////Array List to store original Given dates.
	
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JTextField jTextField_Day;
	private JTextField jTextField_Month;
	private JTextField jTextField_Year;
	private int iPreviousIndex = -1;
	
	// Declare a BufferedImage and its corresponding Graphics2D context object
	private BufferedImage img;
	private Graphics2D g2dImg;


	/**
	 * Launch the application.
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		
		jArrayListPeople = new ArrayList<Person>();
		jArrayListOriginalGivenDates = new ArrayList<MyDate>();
		
		//Read input from text file
		BufferedReader in = new BufferedReader(new FileReader("dates.txt"));
		String sLine;
		
		//Store input details as ArrayList of Person objects
		while((sLine = in.readLine()) != null) {
			String[] saTemp1, saTemp2, saTemp3;
			MyDate birthDate, givenDate;
			saTemp1 = sLine.split(";");
			saTemp2 = saTemp1[1].split(",");
			saTemp3 = saTemp1[2].split(",");
			birthDate = new MyDate(Integer.parseInt(saTemp2[0]), Integer.parseInt(saTemp2[1]), Integer.parseInt(saTemp2[2]));
			givenDate = new MyDate(Integer.parseInt(saTemp3[0]), Integer.parseInt(saTemp3[1]), Integer.parseInt(saTemp3[2]));
			jArrayListPeople.add(new Person(saTemp1[0], birthDate, givenDate));
			
			//Store original given dates safely in ArrayList of MyDate objects
			jArrayListOriginalGivenDates.add(new MyDate(Integer.parseInt(saTemp3[0]), Integer.parseInt(saTemp3[1]), Integer.parseInt(saTemp3[2])));
		}
		in.close();
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Stage3 window = new Stage3();
					window.frmDaysAliveCalculator.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Stage3() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmDaysAliveCalculator = new JFrame();
		frmDaysAliveCalculator.setTitle("Days Alive Calculator - Stage3 - u3149399");
		frmDaysAliveCalculator.setBounds(100, 100, 648, 447);
		frmDaysAliveCalculator.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmDaysAliveCalculator.getContentPane().setLayout(null);
		
		JLabel jLabel_ResultName = new JLabel("");
		jLabel_ResultName.setBounds(238, 337, 257, 16);
		frmDaysAliveCalculator.getContentPane().add(jLabel_ResultName);
		
		JLabel jLabel_ResultBirthDate = new JLabel("");
		jLabel_ResultBirthDate.setBounds(238, 356, 257, 16);
		frmDaysAliveCalculator.getContentPane().add(jLabel_ResultBirthDate);
		
		JLabel jLabel_ResultGivenDate = new JLabel("");
		jLabel_ResultGivenDate.setBounds(238, 373, 257, 16);
		frmDaysAliveCalculator.getContentPane().add(jLabel_ResultGivenDate);
		
		JLabel jLabel_ResultDaysAlive = new JLabel("");
		jLabel_ResultDaysAlive.setBounds(238, 392, 238, 16);
		frmDaysAliveCalculator.getContentPane().add(jLabel_ResultDaysAlive);
		
		MyJPanel jPanel_DrawingArea = new MyJPanel();
		jPanel_DrawingArea.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		jPanel_DrawingArea.setBackground(Color.WHITE);
		jPanel_DrawingArea.setBounds(238, 35, 404, 295);
		frmDaysAliveCalculator.getContentPane().add(jPanel_DrawingArea);
		
		JLabel jLabel_ErrorMessage = new JLabel("");
		jLabel_ErrorMessage.setForeground(Color.RED);
		jLabel_ErrorMessage.setBounds(6, 373, 220, 16);
		frmDaysAliveCalculator.getContentPane().add(jLabel_ErrorMessage);
		
		JLabel jLabel_List = new JLabel("List of People");
		jLabel_List.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		jLabel_List.setBounds(6, 15, 119, 16);
		frmDaysAliveCalculator.getContentPane().add(jLabel_List);
		
		DefaultListModel<String> model = new DefaultListModel<>();
		//Add name of all Person objects to List Model
		for(Person person: jArrayListPeople)
			model.addElement(person.getName());
		
		//Call function to create an ArrayList of rectangles corresponding to ArrayList of Person objects
		//These rectangles will be used to draw the bar graph
		//Width and Height of Drawing Panel are passed as arguments which will be used to determine rectangle dimension
		createRectangles(jPanel_DrawingArea.getWidth(), jPanel_DrawingArea.getHeight());
		
		JScrollPane jScrollPane_List = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		jScrollPane_List.setBounds(6, 35, 220, 118);
		JList<String> jList_ListOfPeople = new JList<String>(model);
		jList_ListOfPeople.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) { //Action listener when user selects names in JList
				
				if (!e.getValueIsAdjusting()) {
					
					jLabel_ErrorMessage.setText(null);
					
					ArrayList<Rectangle2D.Double> rectangleList = new ArrayList<Rectangle2D.Double>();
					
					int iIndex = jList_ListOfPeople.getSelectedIndex();
					
					if(iPreviousIndex != -1) { //List has been selected before. Hence need to redraw two rectangles
						rectangleList.add(jArrayListRectangles.get(iPreviousIndex)); //previous rectangle
						rectangleList.add(jArrayListRectangles.get(iIndex)); //currently selected
						jPanel_DrawingArea.setBarGraphList(rectangleList);
						jPanel_DrawingArea.setColor(new Color[] {Color.BLACK, Color.BLUE});
						jPanel_DrawingArea.repaint();
					}
					
					else { //List has not been selected before. So draw all
						
						Color[] color = new Color[jArrayListRectangles.size()];
						jPanel_DrawingArea.setBarGraphList(jArrayListRectangles);
						for(int iI = 0; iI < jArrayListRectangles.size(); iI++) {
							if(iI == iIndex)
								color[iI] = Color.BLUE;
							else
								color[iI] = Color.BLACK;
						}
						jPanel_DrawingArea.setColor(color);
						jPanel_DrawingArea.repaint();
						
						//Reset input fields to null
						
					}
					
					//Show details of selected person
					jLabel_ResultName.setText("Name: " + jArrayListPeople.get(iIndex).getName());
					jLabel_ResultBirthDate.setText("Date of Birth: " + jArrayListPeople.get(iIndex).getBirthDateFormatted());
					jLabel_ResultGivenDate.setText("Given Date: " + jArrayListPeople.get(iIndex).getGivenDateFormatted());
					jLabel_ResultDaysAlive.setText("Number of Days Alive: " + jArrayListPeople.get(iIndex).getDaysAlive());
					
					//Set iPreviousIndex to iIndex for future use
					iPreviousIndex = iIndex;
				}
			}
		});
		jScrollPane_List.setViewportView(jList_ListOfPeople);
		jList_ListOfPeople.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		frmDaysAliveCalculator.getContentPane().add(jScrollPane_List);
		
		JLabel jLabel_SetDate = new JLabel("Set Given Date for Selected Person");
		jLabel_SetDate.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
		jLabel_SetDate.setBounds(6, 165, 220, 23);
		frmDaysAliveCalculator.getContentPane().add(jLabel_SetDate);
		
		JPanel jPanel_Input = new JPanel();
		jPanel_Input.setBorder(new TitledBorder(null, "Input", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		jPanel_Input.setBounds(26, 245, 162, 119);
		jPanel_Input.setVisible(false);
		frmDaysAliveCalculator.getContentPane().add(jPanel_Input);
		jPanel_Input.setLayout(null);
		
		JLabel jLabel_Day = new JLabel("Day");
		jLabel_Day.setBounds(10, 25, 45, 16);
		jPanel_Input.add(jLabel_Day);
		
		jTextField_Day = new JTextField();
		jTextField_Day.setBounds(6, 43, 42, 26);
		jPanel_Input.add(jTextField_Day);
		jTextField_Day.setColumns(10);
		
		JLabel jLabel_Month = new JLabel("Month");
		jLabel_Month.setBounds(55, 25, 45, 16);
		jPanel_Input.add(jLabel_Month);
		
		jTextField_Month = new JTextField();
		jTextField_Month.setBounds(52, 43, 45, 26);
		jPanel_Input.add(jTextField_Month);
		jTextField_Month.setColumns(10);
		
		JLabel jLabel_Year = new JLabel("Year");
		jLabel_Year.setBounds(109, 25, 45, 16);
		jPanel_Input.add(jLabel_Year);
		
		jTextField_Year = new JTextField();
		jTextField_Year.setBounds(100, 43, 54, 26);
		jPanel_Input.add(jTextField_Year);
		jTextField_Year.setColumns(10);
		
		JButton jButton_SetDataFile = new JButton("Set");
		jButton_SetDataFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { //Action Listener when user wants to reset given date to that from file
				
				int iIndex = jList_ListOfPeople.getSelectedIndex();
				
				if(iIndex == -1) { //Nothing selected
					jLabel_ErrorMessage.setText("No selection made. Nothing to set.");
				}
				
				else {
					
					//Check if the given date input has changed or not.
					//If not changed, display a message
					if(jArrayListPeople.get(iIndex).getGivenDateObject().equals(jArrayListOriginalGivenDates.get(iIndex))) {
						System.out.println("old date: " + jArrayListOriginalGivenDates.get(iIndex).getDateFormatted());
						System.out.println("new date: " + jArrayListPeople.get(iIndex).getGivenDateObject().getDateFormatted());
						jLabel_ErrorMessage.setText("Given date not changed");
					}
					
					else { 
						
						//Change back the given date to the original date
						int[] temp = jArrayListOriginalGivenDates.get(iIndex).getDate();
						jArrayListPeople.get(iIndex).setGivenDate(temp[0], temp[1], temp[2]);
						
						//now update the rectangles list and
						createRectangles(jPanel_DrawingArea.getWidth(), jPanel_DrawingArea.getHeight());
						
						//redraw all bar graphs
						//Clear the panel first
						jPanel_DrawingArea.clear(jPanel_DrawingArea.getGraphics());
						
						Color[] color = new Color[jArrayListRectangles.size()];
						jPanel_DrawingArea.setBarGraphList(jArrayListRectangles);
						for(int iI = 0; iI < jArrayListRectangles.size(); iI++) {
							if(iI == iIndex)
								color[iI] = Color.BLUE;
							else
								color[iI] = Color.BLACK;
						}
						jPanel_DrawingArea.setColor(color);
						jPanel_DrawingArea.repaint();

						jLabel_ErrorMessage.setText(null);
						
						//Update Result Labels
						jLabel_ResultName.setText("Name: " + jArrayListPeople.get(iIndex).getName());
						jLabel_ResultBirthDate.setText("Date of Birth: " + jArrayListPeople.get(iIndex).getBirthDateFormatted());
						jLabel_ResultGivenDate.setText("Given Date: " + jArrayListPeople.get(iIndex).getGivenDateFormatted());
						jLabel_ResultDaysAlive.setText("Number of Days Alive: " + jArrayListPeople.get(iIndex).getDaysAlive());
						
					}	
				}
				
			}
		});
		jButton_SetDataFile.setBounds(125, 187, 75, 29);
		frmDaysAliveCalculator.getContentPane().add(jButton_SetDataFile);
		jButton_SetDataFile.setVisible(false);
		
		JButton jButton_SetDateUser = new JButton("Set");
		jButton_SetDateUser.setForeground(Color.BLACK);
		jButton_SetDateUser.setBounds(16, 81, 117, 29);
		jPanel_Input.add(jButton_SetDateUser);
		jButton_SetDateUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { //Action Listener when user wants to input new given date
				//Get input from user and update it in its object in jArrayListPeople
				int iDay, iMonth, iYear;
				int[] iaBirthDate = new int[3];
				int iIndex = jList_ListOfPeople.getSelectedIndex();

				if(iIndex == -1) { //Nothing selected
					jLabel_ErrorMessage.setText("No selection made");
				}
				
				else { //Selection is made
					
					try {
						iDay = Integer.parseInt(jTextField_Day.getText().trim());
						iMonth = Integer.parseInt(jTextField_Month.getText().trim());
						iYear = Integer.parseInt(jTextField_Year.getText().trim());
						
						//Check that date input from user is not less than the birth date of selected person
						iaBirthDate = jArrayListPeople.get(iIndex).getBirthDate();
						
						if(iYear < iaBirthDate[2] || (iYear == iaBirthDate[2] && iMonth < iaBirthDate[1]) || 
								(iYear == iaBirthDate[2] && iMonth == iaBirthDate[1] && iDay < iaBirthDate[0])) { //Input date is before birthdate
							jLabel_ErrorMessage.setText("Invalid Input");
						}
						else { //Input is valid. Update Person object with new given date.
							
							jArrayListPeople.get(iIndex).setGivenDate(iDay, iMonth, iYear);
							
							//now update the rectangles list
							createRectangles(jPanel_DrawingArea.getWidth(), jPanel_DrawingArea.getHeight());
							
							//redraw all the bar graph
							//Clear the panel first
							jPanel_DrawingArea.clear(jPanel_DrawingArea.getGraphics());
							
							Color[] color = new Color[jArrayListRectangles.size()];
							jPanel_DrawingArea.setBarGraphList(jArrayListRectangles);
							for(int iI = 0; iI < jArrayListRectangles.size(); iI++) {
								if(iI == iIndex)
									color[iI] = Color.BLUE;
								else
									color[iI] = Color.BLACK;
							}
							jPanel_DrawingArea.setColor(color);
							jPanel_DrawingArea.repaint();
							
							jPanel_DrawingArea.repaint();
						
							jLabel_ErrorMessage.setText(null);
							
							//Update Result Labels
							jLabel_ResultName.setText("Name: " + jArrayListPeople.get(iIndex).getName());
							jLabel_ResultBirthDate.setText("Date of Birth: " + jArrayListPeople.get(iIndex).getBirthDateFormatted());
							jLabel_ResultGivenDate.setText("Given Date: " + jArrayListPeople.get(iIndex).getGivenDateFormatted());
							jLabel_ResultDaysAlive.setText("Number of Days Alive: " + jArrayListPeople.get(iIndex).getDaysAlive());
						}
						
					}
					
					catch(NumberFormatException e1) { //Error in Input
						jLabel_ErrorMessage.setText("Invalid Input");
					}	
				}
			}
		});
		
		JRadioButton jRadioButton_File = new JRadioButton("From File");
		jRadioButton_File.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jPanel_Input.setVisible(false);
				jButton_SetDataFile.setVisible(true);
				jLabel_ErrorMessage.setText(null);
			}
		});
		jRadioButton_File.setSelected(true);
		buttonGroup.add(jRadioButton_File);
		jRadioButton_File.setBounds(16, 188, 98, 23);
		frmDaysAliveCalculator.getContentPane().add(jRadioButton_File);
		
		JRadioButton jRadioButton_UserInput = new JRadioButton("User Input");
		jRadioButton_UserInput.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jTextField_Day.setText(null);
				jTextField_Month.setText(null);
				jTextField_Year.setText(null);
				jPanel_Input.setVisible(true);
				jLabel_ErrorMessage.setText(null);
				jButton_SetDataFile.setVisible(false);
			}
		});
		buttonGroup.add(jRadioButton_UserInput);
		jRadioButton_UserInput.setBounds(16, 210, 98, 23);
		frmDaysAliveCalculator.getContentPane().add(jRadioButton_UserInput);
		
		JLabel jLabel_GraphLabel = new JLabel("Bar Graph of Number of Days Alive");
		jLabel_GraphLabel.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		jLabel_GraphLabel.setBounds(238, 15, 238, 16);
		frmDaysAliveCalculator.getContentPane().add(jLabel_GraphLabel);
		
		// Instantiate the BufferedImage object and give it the same width 
		// and height as that of the drawing area JPanel
		img = new BufferedImage(jPanel_DrawingArea.getWidth(), 
				                jPanel_DrawingArea.getHeight(), 
				                BufferedImage.TYPE_INT_RGB);
				
		// Get its graphics context. A graphics context of a particular object allows us to draw on it.
		g2dImg = (Graphics2D)img.getGraphics();

		// Draw a filled white colored rectangle on the entire area to clear it.
		g2dImg.setPaint(Color.WHITE);
		g2dImg.fill(new Rectangle2D.Double(0, 0, img.getWidth(), img.getHeight()));
		
		/* This part is not required since bar graph is be drawn only when user selects a person
		 * Draw all the bar graphs on MyJPane
		jPanel_DrawingArea.setBarGraphList(jArrayListRectangles);
		System.out.println("drawing all");
		Color[] color = new Color[jArrayListRectangles.size()];
		for(int iI = 0; iI < jArrayListRectangles.size(); iI++)
			color[iI] = Color.BLACK;
		jPanel_DrawingArea.setColor(color);
		jPanel_DrawingArea.repaint();
		*/
	}
	
	//Function to create ArrayList of rectangles based on ArrayList of People and their details
	private void createRectangles(int iWidth, int iHeight) {
		
		int iMaxYear = 0;
		int iNumPeople = jArrayListPeople.size();
		
		//Width of rectangle is proportionate to number of people and width of drawing area
		int iRectangleWidth = iWidth/((2 * iNumPeople) + 1);
		int iXCounter = 1;
		double iY = 10; // y coordinate of rectangle
		
		// Initialize Array List of Rectangles for drawing
		jArrayListRectangles = new ArrayList<Rectangle2D.Double>();
		
		//Find the highest number_of_days_alive
		for(Person person: jArrayListPeople) {
			if(person.getDaysAlive() > iMaxYear)
				iMaxYear = person.getDaysAlive();
		}
		
		//Calculate the rectangles which will be drawn as bar chart
		for(Person person: jArrayListPeople) {
			double iX = iXCounter * iRectangleWidth; // x coordinate of rectangle
			
			//height of rectangle/bar-graph will be proportionate to height of drawing area and height of other rectangles
			//Bar-graph for Person with highest days_alive will be longest and others will be calculated proportionately
			double iRectangleHeight = (((double)person.getDaysAlive() / (double) iMaxYear) * (double) (iHeight - iY - 5));
			jArrayListRectangles.add(new Rectangle2D.Double(iX, (iHeight - iRectangleHeight - 10), iRectangleWidth, iRectangleHeight));
			iXCounter += 2;
		}
	}


	/**
	 * Inner class for drawing. Extends JPanel by providing
	 * new paintComponent and clear methods.
	 */
	
	class MyJPanel extends JPanel {
		
		// Since this class is used to draw on bar-graphs, its attributes are only 2
		//ArrayList of Rectangles (bar-graphs) and their corresponding colors in an Array
		private ArrayList<Rectangle2D.Double> jArrayListBars;
		private Color[] color;
		
		public MyJPanel() {
			jArrayListBars = new ArrayList<Rectangle2D.Double>();
		}
		
		//Method to set Color
		public void setColor(Color[] color) {
			this.color = color;
		}
		
		//Method to set ArrayList of Rectangles
		public void setBarGraphList(ArrayList<Rectangle2D.Double> bars) {
			jArrayListBars = bars;
		}


		 /**
		  * Override paintComnponent method with our draw commands
		  * 
		  */
		 public void paintComponent(Graphics g) 
		 {
			 //Must be called to draw the JPanel control. 
			 // As a side effect, it also clears it.
			 super.paintComponent(g);
			 
			 //Draws the Rectangles in their corresponding color on BufferedImage
			 int iColorIndex = 0;
			 for(Rectangle2D.Double rectangle: jArrayListBars) {
				 g2dImg.setPaint(color[iColorIndex]);
				 g2dImg.fill(rectangle);
				 iColorIndex++;
			 }
			 
			 //Transfers the BufferedImage content to JPanel drawing area
			 g.drawImage(img, 0, 0, null);
		 }
		 
		 
		 // super.paintComponent clears off screen pixmap,
		 // since we're using double buffering by default.
		 protected void clear(Graphics g) {
			 super.paintComponent(g);
			 
			 // Also clear the BufferedImage object by drawing a white coloured filled rectangle all over.
			 g2dImg.setPaint(Color.WHITE);
			 g2dImg.fill(new Rectangle2D.Double(0, 0, img.getWidth(), img.getHeight()));
		 }
	}
}
