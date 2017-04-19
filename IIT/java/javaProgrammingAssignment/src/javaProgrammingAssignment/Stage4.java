/*
 * Stage4.java
 * Author: Tenzin Dendup (u3149399)
 * Date Created: 18 April 2017
 * Date Last Changed: 19 April 2017
 * This is a Java GUI application to calculate number of days alive.
 * It is stage 4 of IIT Java Assignment, University of Canberra.
 * Stage 4 is implemented with GUI and Object.
 * Stage 4 uses two other java source files, Person.java (for Person Object) and MyDate.java (For MyDate object)
 * Stage 4 uses Java ArrayList to store People data.
 * For drawing purposes, stage 4 uses the MyJPanel inner class from lecture examples by Roland Goecke, with modifications.
 * For printing report, Stage 4 reused code from Star8.java by Roland Goecke with modifications.
 * 
 * Input: List of Names and sets of dates (Date of birth and another date) read from M.S. Access database
 * Output: Number of days alive and corresponding bar graph (on GUI) and Database report as text file.
 * 
 */

package javaProgrammingAssignment;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;


public class Stage4 {

	private static final int MAX_LINES_PER_PAGE = 5;
	
	private JFrame frmDaysAliveCalculator;
	private static ArrayList<Person> jArrayListPeople; //ArrayList to store details of people
	private static ArrayList<Rectangle2D.Double> jArrayListRectangles; //Array List to store rectangles to draw bar graph
	
	private int iPreviousIndex = -1;
	
	// Declare a BufferedImage and its corresponding Graphics2D context object
	private BufferedImage img;
	private Graphics2D g2dImg;


	/**
	 * Launch the application.
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Stage4 window = new Stage4();
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
	public Stage4() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * Read data from database and populate ArrayList
	 */
	private void initialize() {
		
		jArrayListPeople = new ArrayList<Person>();
		
		//Read data from database
		//Connect to database
		java.sql.Connection conn = null;
		
		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
	    	conn = DriverManager.getConnection("jdbc:ucanaccess://dates.mdb");
	        Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
	                ResultSet.CONCUR_UPDATABLE);
	        
	        String sSql = "SELECT * FROM tblDates";
	        
	        // Get the ResultSet from the database
	        ResultSet rs = stmt.executeQuery(sSql);
	        
	        //Read through ResultSet and populate ArrayList of People objects
	        while (rs.next()) {
	        	MyDate birthDate, givenDate;
	        	givenDate = new MyDate(rs.getInt(2), rs.getInt(3), rs.getInt(4));
	        	birthDate = new MyDate(rs.getInt(5), rs.getInt(6), rs.getInt(7));
	        	jArrayListPeople.add(new Person(rs.getString(1), birthDate, givenDate));
			}
		} catch(SQLException s) {
            System.out.println(s);
        } catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
            if (conn != null) {
                try {
                    conn.close();		// Close connection to database
                } catch(SQLException ignore) {}
            }
        }
		
		
		frmDaysAliveCalculator = new JFrame();
		frmDaysAliveCalculator.setResizable(false);
		frmDaysAliveCalculator.setTitle("Days Alive Calculator - Stage4 - u3149399");
		frmDaysAliveCalculator.setBounds(100, 100, 648, 423);
		frmDaysAliveCalculator.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmDaysAliveCalculator.getContentPane().setLayout(null);
		
		JLabel jLabel_ResultName = new JLabel("");
		jLabel_ResultName.setBounds(6, 213, 220, 16);
		frmDaysAliveCalculator.getContentPane().add(jLabel_ResultName);
		
		JLabel jLabel_ResultBirthDate = new JLabel("");
		jLabel_ResultBirthDate.setBounds(6, 233, 220, 16);
		frmDaysAliveCalculator.getContentPane().add(jLabel_ResultBirthDate);
		
		JLabel jLabel_ResultGivenDate = new JLabel("");
		jLabel_ResultGivenDate.setBounds(6, 253, 220, 16);
		frmDaysAliveCalculator.getContentPane().add(jLabel_ResultGivenDate);
		
		JLabel jLabel_ResultDaysAlive = new JLabel("");
		jLabel_ResultDaysAlive.setBounds(6, 273, 220, 16);
		frmDaysAliveCalculator.getContentPane().add(jLabel_ResultDaysAlive);
		
		MyJPanel jPanel_DrawingArea = new MyJPanel();
		jPanel_DrawingArea.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		jPanel_DrawingArea.setBackground(Color.WHITE);
		jPanel_DrawingArea.setBounds(238, 35, 404, 295);
		frmDaysAliveCalculator.getContentPane().add(jPanel_DrawingArea);
		
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
		jScrollPane_List.setBounds(6, 35, 220, 162);
		JList<String> jList_ListOfPeople = new JList<String>(model);
		jList_ListOfPeople.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) { //Action listener when user selects names in JList
				
				if (!e.getValueIsAdjusting()) {
					
					ArrayList<Rectangle2D.Double> rectangleList = new ArrayList<Rectangle2D.Double>();
					
					int iIndex = jList_ListOfPeople.getSelectedIndex();
					
					if(iPreviousIndex != -1) { //List has been selected before. Hence need to redraw two rectangles
						rectangleList.add(jArrayListRectangles.get(iPreviousIndex)); //previous rectangle
						rectangleList.add(jArrayListRectangles.get(iIndex)); //currently selected
						jPanel_DrawingArea.setBarGraphList(rectangleList);
						jPanel_DrawingArea.setColor(new Color[] {Color.BLACK, Color.BLUE});
						jPanel_DrawingArea.repaint();
					}
					
					else { //List has not been selected before. Draw all and select change color of currently selected person
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
					}
					
					//Show details of selected person
					jLabel_ResultName.setText(jArrayListPeople.get(iIndex).getName());
					jLabel_ResultBirthDate.setText("Date of Birth: " + jArrayListPeople.get(iIndex).getBirthDateFormatted());
					jLabel_ResultGivenDate.setText("Given Date: " + jArrayListPeople.get(iIndex).getGivenDateFormatted());
					jLabel_ResultDaysAlive.setText("Days Alive: " + jArrayListPeople.get(iIndex).getDaysAlive());
					
					//Set iPreviousIndex to iIndex for future use
					iPreviousIndex = iIndex;
				}
			}
		});
		jScrollPane_List.setViewportView(jList_ListOfPeople);
		jList_ListOfPeople.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		frmDaysAliveCalculator.getContentPane().add(jScrollPane_List);
		
		JLabel jLabel_GraphLabel = new JLabel("Bar Graph of Number of Days Alive");
		jLabel_GraphLabel.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		jLabel_GraphLabel.setBounds(238, 15, 238, 16);
		frmDaysAliveCalculator.getContentPane().add(jLabel_GraphLabel);
		
		JButton jButton_PrintReport = new JButton("Print Report");
		jButton_PrintReport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				// Connect to database
		        java.sql.Connection conn = null;
		        try {
		        	Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
		        	conn = DriverManager.getConnection("jdbc:ucanaccess://dates.mdb");
		            Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
		                    ResultSet.CONCUR_UPDATABLE);
		            String sSql = "SELECT * FROM tblDates";
		              	
		            // Get the ResultSet from the database
		            ResultSet rs = stmt.executeQuery(sSql);
		    		
		            printReport(rs, "Days Alive", "days_alive_report.txt");

		        } catch(SQLException s) {
		            System.out.println(s);
		        } catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} finally {
		            if (conn != null) {
		                try {
		                    conn.close();		// Close connection to database
		                } catch(SQLException ignore) {}
		            }
		        }
				
				
			}
		});
		jButton_PrintReport.setBounds(58, 364, 117, 29);
		frmDaysAliveCalculator.getContentPane().add(jButton_PrintReport);
		
		JButton jButton_Quit = new JButton("Quit");
		jButton_Quit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		jButton_Quit.setBounds(424, 364, 117, 29);
		frmDaysAliveCalculator.getContentPane().add(jButton_Quit);
		
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
		
	}
	
	//Method to create ArrayList of rectangles based on ArrayList of People and their details
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
	 * Code reused from Roland Goecke's code with modification (Added class attributes, constructor and methods)
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
	
	
	/**
	 * Print the header of the report page
	 * 
	 * @param sHeader
	 * @param pw
	 * Code reused from Roland Goecke's code with modification
	 */
	private void pageHead(String sHeader, PrintWriter pw) 
	{
		pw.println(sHeader);	// Print the header string
		
		// Print a number of = signs under it as a way of underlining
		// Work out how many = we need from the sHeader length
		for (int iI = 0; iI < sHeader.length(); iI++)
			pw.print("=");
		pw.println();
		
		// Print an empty line at the end
		pw.println();
	}
	
	/**
	 * Print footer of the report page
	 * @param iPageNum
	 * @param pw
	 */
	private void pageFooter(int iPageNum, PrintWriter pw) {
		pw.println();	// Print an empty line at the beginning of the footer
		
		// Print the page number
		pw.println("--- page "+ iPageNum +" ---");
		
		// Print 3 lines after it to simulate the beginning of a new report page
		for (int iI = 0; iI < 3; iI++)
			pw.println();
	}
	
	/**
	 * This method prints the body of the report page. It prints
	 * MAX_LINES_PER_PAGE number of lines.
	 * 
	 * @param rs
	 * @param iFirstRow
	 * @param pw
	 * @throws SQLException
	 */
	private void pageBody(ResultSet rs, int iFirstRow, int iNumRows, PrintWriter pw) throws SQLException {
		int iLastRow = iFirstRow + MAX_LINES_PER_PAGE - 1;
		
		// As our last report page may not have the exact number of rows
		// required per page, we need to fill it up with blank rows.
		// Work out how many we need.
		int iBlankRows = 0;
		if (iLastRow > iNumRows - 1)
		{
			iBlankRows = iLastRow - (iNumRows - 1);
			iLastRow = iNumRows - 1;
		}
		
		// Print the column heads
		// In addition to 7 column heads from database table, "daysAlive" column head is added
		
		ResultSetMetaData rmsd = rs.getMetaData();
		String columnHeads = String.format("%1$-20s %2$-12s %3$-15s %4$-15s %5$-10s %6$-10s %7$-10s %8$-10s",
				rmsd.getColumnName(1), rmsd.getColumnName(2),
				rmsd.getColumnName(3), rmsd.getColumnName(4), rmsd.getColumnName(5), rmsd.getColumnName(6), rmsd.getColumnName(7), "daysAlive");
		pw.println(columnHeads);
		pw.println();

		// Print the report lines for the current report page
		for (int iRow = iFirstRow; iRow <= iLastRow; iRow++)
		{
			// Report line consists of 7 columns read from database plus "number of days alive" from ArrayList
			String reportLine = String.format("%1$-20s %2$10s %3$14s %4$14s %5$10s %6$12s %7$9s %8$12s", 
					rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), 
					rs.getString(7), Integer.toString(jArrayListPeople.get(iRow).getDaysAlive())); 
			pw.println(reportLine);
			rs.next();
		}
		
		// Now print the blank rows, if needed
		for (int iRow = 0; iRow < iBlankRows; iRow++)
			pw.println();
	
	}
	
	/**
	 * Print a database report
	 * 
	 * @param stars
	 * @param sHeader
	 * @param sFileName
	 */
	private void printReport(ResultSet rs, String sHeader, String sFileName) {
		try {
			PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(sFileName)));
			
        	rs.last();						// Move to last row
        	int iNumRows = rs.getRow();		// Get the number of rows
        	rs.first();						// Move back to first row			
        	int iNumPages = iNumRows / MAX_LINES_PER_PAGE;	// Work out how many report pages we need
        	if (iNumPages * MAX_LINES_PER_PAGE < iNumRows)
        		iNumPages++;
			int iFirstRow = 0;				// Need to keep track of starting row for next report page
			
			// Write report
			for (int iPageNum = 0; iPageNum < iNumPages; iPageNum++) {
				pageHead(sHeader, out);
				pageBody(rs, iFirstRow, iNumRows, out);
				pageFooter(iPageNum + 1, out);
				iFirstRow += MAX_LINES_PER_PAGE;
			}
			
			out.close();
			
			// Show a pop up message when completed
			JOptionPane.showMessageDialog(frmDaysAliveCalculator, "Database query report written to "+ 
					sFileName +".");

		} catch(IOException ioex) {
			System.out.println(ioex);
		} catch(SQLException s) {
			System.out.println(s);
		}

	}
	
	
}
