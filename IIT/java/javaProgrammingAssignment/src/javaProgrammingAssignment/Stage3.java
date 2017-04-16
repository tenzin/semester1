package javaProgrammingAssignment;

import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

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
import javax.swing.border.EtchedBorder;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.border.BevelBorder;

public class Stage3 {

	private JFrame frame;
	private static ArrayList<Person> jArrayListPeople;
	private static ArrayList<Rectangle2D.Double> jArrayListRectangles;
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
		
		BufferedReader in = new BufferedReader(new FileReader("dates.txt"));
		String sLine;
		
		while((sLine = in.readLine()) != null) {
			String[] saTemp1, saTemp2, saTemp3;
			MyDate birthDate, givenDate;
			saTemp1 = sLine.split(";");
			saTemp2 = saTemp1[1].split(",");
			saTemp3 = saTemp1[2].split(",");
			birthDate = new MyDate(Integer.parseInt(saTemp2[0]), Integer.parseInt(saTemp2[1]), Integer.parseInt(saTemp2[2]));
			givenDate = new MyDate(Integer.parseInt(saTemp3[0]), Integer.parseInt(saTemp3[1]), Integer.parseInt(saTemp3[2]));
			jArrayListPeople.add(new Person(saTemp1[0], birthDate, givenDate));
		}
		in.close();
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Stage3 window = new Stage3();
					window.frame.setVisible(true);
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
		frame = new JFrame();
		frame.setBounds(100, 100, 648, 447);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel jLabel_ResultName = new JLabel("Name");
		jLabel_ResultName.setBounds(385, 331, 257, 16);
		frame.getContentPane().add(jLabel_ResultName);
		
		JLabel jLabel_ResultBirthDate = new JLabel("Birth Date");
		jLabel_ResultBirthDate.setBounds(385, 347, 257, 16);
		frame.getContentPane().add(jLabel_ResultBirthDate);
		
		JLabel jLabel_ResultGivenDate = new JLabel("New label");
		jLabel_ResultGivenDate.setBounds(385, 365, 257, 16);
		frame.getContentPane().add(jLabel_ResultGivenDate);
		
		JLabel jLabel_ResultDaysAlive = new JLabel("Days Alive");
		jLabel_ResultDaysAlive.setBounds(385, 384, 238, 16);
		frame.getContentPane().add(jLabel_ResultDaysAlive);
		
		MyJPanel jPanel_DrawingArea = new MyJPanel();
		jPanel_DrawingArea.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		jPanel_DrawingArea.setBackground(Color.WHITE);
		jPanel_DrawingArea.setBounds(238, 15, 404, 295);
		frame.getContentPane().add(jPanel_DrawingArea);
		
		JLabel jLabel_List = new JLabel("List of People");
		jLabel_List.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		jLabel_List.setBounds(21, 15, 104, 16);
		frame.getContentPane().add(jLabel_List);
		
		DefaultListModel<String> model = new DefaultListModel<>();
		//Add items to List Model
		for(Person person: jArrayListPeople)
			model.addElement(person.getName());
		//Call function to create an Array List of rectangles corresponding to People's details
		//These rectangles will be used to draw the bar graph
		createRectangles(jPanel_DrawingArea.getWidth(), jPanel_DrawingArea.getHeight());
		
		JScrollPane jScrollPane_List = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		jScrollPane_List.setBounds(21, 35, 205, 118);
		JList<String> jList_ListOfPeople = new JList<String>(model);
		jList_ListOfPeople.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				
				if (!e.getValueIsAdjusting()) {
					
					ArrayList<Rectangle2D.Double> rectangleList = new ArrayList<Rectangle2D.Double>();
					
					int iIndex = jList_ListOfPeople.getSelectedIndex();
					
					if(iPreviousIndex != -1) { //List has been selected before. Hence need to redraw two rectangles
						rectangleList.add(jArrayListRectangles.get(iPreviousIndex)); //previous rectangle
						rectangleList.add(jArrayListRectangles.get(iIndex)); //currently selected
						jPanel_DrawingArea.setBarGraphList(rectangleList);
						jPanel_DrawingArea.setColor(new Color[] {Color.BLACK, Color.BLUE});
						System.out.println("Drawing old one in black " + iPreviousIndex);
						jPanel_DrawingArea.repaint();
					}
					
					else { //List has not been selected before. Just draw the currently selected one
						rectangleList.add(jArrayListRectangles.get(iIndex));
						jPanel_DrawingArea.setBarGraphList(rectangleList);
						System.out.println("drawing selected");
						jPanel_DrawingArea.setColor(new Color[] {Color.BLUE});
						System.out.println("Drawing new in blue " + iIndex);
						jPanel_DrawingArea.repaint();
					}
					
					//Show details of selected person
					jLabel_ResultName.setText(jArrayListPeople.get(iIndex).getName());
					jLabel_ResultBirthDate.setText(jArrayListPeople.get(iIndex).getBirthDateFormatted() + "," + jArrayListPeople.get(iIndex).getGivenDateFormatted());
					jLabel_ResultDaysAlive.setText("" + jArrayListPeople.get(iIndex).getDaysAlive());
					
					//Set iPreviousIndex to iIndex for future use
					iPreviousIndex = iIndex;
				}
			}
		});
		jScrollPane_List.setViewportView(jList_ListOfPeople);
		jList_ListOfPeople.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		frame.getContentPane().add(jScrollPane_List);
		
		JLabel lblNewLabel = new JLabel("Second Date Source");
		lblNewLabel.setBounds(21, 157, 130, 16);
		frame.getContentPane().add(lblNewLabel);
		
		JPanel jPanel_Input = new JPanel();
		jPanel_Input.setBorder(new TitledBorder(null, "Input", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		jPanel_Input.setBounds(39, 234, 162, 80);
		jPanel_Input.setVisible(false);
		frame.getContentPane().add(jPanel_Input);
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
		
		JRadioButton jRadioButton_File = new JRadioButton("From File");
		jRadioButton_File.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jPanel_Input.setVisible(false);
			}
		});
		jRadioButton_File.setSelected(true);
		buttonGroup.add(jRadioButton_File);
		jRadioButton_File.setBounds(21, 178, 98, 23);
		frame.getContentPane().add(jRadioButton_File);
		
		JRadioButton jRadioButton_UserInput = new JRadioButton("User Input");
		jRadioButton_UserInput.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jTextField_Day.setText(null);
				jTextField_Month.setText(null);
				jTextField_Year.setText(null);
				jPanel_Input.setVisible(true);
			}
		});
		buttonGroup.add(jRadioButton_UserInput);
		jRadioButton_UserInput.setBounds(21, 205, 98, 23);
		frame.getContentPane().add(jRadioButton_UserInput);
		
		JButton jButton_Calculate = new JButton("Calculate");
		jButton_Calculate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		jButton_Calculate.setBounds(34, 326, 117, 29);
		frame.getContentPane().add(jButton_Calculate);
		
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
		
		//Draw all the bar graphs on MyJPane
		jPanel_DrawingArea.setBarGraphList(jArrayListRectangles);
		System.out.println("drawing all");
		Color[] color = new Color[jArrayListRectangles.size()];
		for(int iI = 0; iI < jArrayListRectangles.size(); iI++)
			color[iI] = Color.BLACK;
		jPanel_DrawingArea.setColor(color);
		jPanel_DrawingArea.repaint();
	}
	
	//Function to create ArrayList of rectangles based on ArrayList of People and their details
	private void createRectangles(int iWidth, int iHeight) {
		
		int iMaxYear = 0;
		int iNumPeople = jArrayListPeople.size();
		int iRectangleWidth = iWidth/((2 * iNumPeople) + 1);
		int iXCounter = 1;
		double iY = 10;
		// Initialize List of Rectangles for drawing
		jArrayListRectangles = new ArrayList<Rectangle2D.Double>();
		
		//Find the longest age
		for(Person person: jArrayListPeople) {
			if(person.getDaysAlive() > iMaxYear)
				iMaxYear = person.getDaysAlive();
		}
		
		//Calculate the rectangles which will be drawn as bar chart
		for(Person person: jArrayListPeople) {
			double iX = iXCounter * iRectangleWidth;
			double iRectangleHeight = (((double)person.getDaysAlive() / (double) iMaxYear) * (double) (iHeight - iY - 5));
			jArrayListRectangles.add(new Rectangle2D.Double(iX, 10, iRectangleWidth, iRectangleHeight));
			iXCounter += 2;
		}
		
	}


	/**
	 * Inner class for drawing. Extends JPanel by providing
	 * new paintComponent and clear methods.
	 */
	
	class MyJPanel extends JPanel {
		
		// Declare and instantiate an ellipse and rectangle object of 
		// dimension zero. We will later just change their dimension 
		// and location for efficiency.

		ArrayList<Rectangle2D.Double> jArrayListBars;
		Color[] color;
		
		public MyJPanel() {
			jArrayListBars = new ArrayList<Rectangle2D.Double>();
		}
		public void setColor(Color[] color) {
			this.color = color;
		}
		
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
			 
			 int iColorIndex = 0;
			 System.out.println("New set of drawing");
			 for(Rectangle2D.Double rectangle: jArrayListBars) {
				 System.out.println("Rect = " + rectangle.getX() + "," + rectangle.getY() + "," + rectangle.getWidth() + "," + rectangle.getHeight());
				 g2dImg.setPaint(color[iColorIndex]);
				 g2dImg.fill(rectangle);
				 iColorIndex++;
			 }
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
