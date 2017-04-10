package javaProgrammingAssignment;

import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Hashtable;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.border.BevelBorder;
import javax.swing.ListSelectionModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JTextField;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import java.awt.Color;
import javax.swing.border.EtchedBorder;
import java.awt.Font;

public class Stage3 {

	private JFrame frame;
	
	private static Hashtable<String, Dates> hPeople;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JTextField jTextField_Day;
	private JTextField jTextField_Month;
	private JTextField jTextField_Year;
	
	public static class Dates {
		String sBirthDate;
		String sGivenDate;
		
		public Dates(String sBirthDate, String sGivenDate) {
			this.sBirthDate = sBirthDate;
			this.sGivenDate = sGivenDate;
		}
	}
	

	/**
	 * Launch the application.
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		
		hPeople = new Hashtable<String, Dates>();
		String[] saItem;
		BufferedReader in = new BufferedReader(new FileReader("dates.txt"));
		String sLine;
		while((sLine = in.readLine()) != null) {
			saItem = sLine.split(";");
			hPeople.put(saItem[0], new Dates(saItem[1], saItem[2]));
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
		frame.setBounds(100, 100, 648, 393);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel jLabel_List = new JLabel("List of People");
		jLabel_List.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		jLabel_List.setBounds(21, 15, 104, 16);
		frame.getContentPane().add(jLabel_List);
		
		DefaultListModel<String> model = new DefaultListModel<>();
		//Add items to List Model
		for(String key: hPeople.keySet())
			model.addElement(key);
		JScrollPane jScrollPane_List = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		jScrollPane_List.setBounds(21, 35, 205, 118);
		JList<String> jList_NameList = new JList<String>(model);
		jList_NameList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				//String[] saS = hPeople.get(jList_NameList.getSelectedValue()).sBirthDate.split(",");
				//jLabel_BirthDate.setText("Date of Birth: " + saS[0] + "/" + saS[1] + "/" + saS[2] );
				//jLabel_GivenDate.setText("Second Date: " + hPeople.get(jList_NameList.getSelectedValue()).sGivenDate);
			}
		});
		jScrollPane_List.setViewportView(jList_NameList);
		jList_NameList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		frame.getContentPane().add(jScrollPane_List);
		
		JButton jButton_Calculate = new JButton("Calculate");
		jButton_Calculate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		jButton_Calculate.setBounds(34, 326, 117, 29);
		frame.getContentPane().add(jButton_Calculate);
		
		JLabel lblNewLabel = new JLabel("Second Date Source");
		lblNewLabel.setBounds(21, 157, 130, 16);
		frame.getContentPane().add(lblNewLabel);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Input", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(39, 234, 162, 80);
		panel.setVisible(false);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel jLabel_Day = new JLabel("Day");
		jLabel_Day.setBounds(10, 25, 45, 16);
		panel.add(jLabel_Day);
		
		jTextField_Day = new JTextField();
		jTextField_Day.setBounds(6, 43, 42, 26);
		panel.add(jTextField_Day);
		jTextField_Day.setColumns(10);
		
		JLabel jLabel_Month = new JLabel("Month");
		jLabel_Month.setBounds(55, 25, 45, 16);
		panel.add(jLabel_Month);
		
		jTextField_Month = new JTextField();
		jTextField_Month.setBounds(52, 43, 45, 26);
		panel.add(jTextField_Month);
		jTextField_Month.setColumns(10);
		
		JLabel jLabel_Year = new JLabel("Year");
		jLabel_Year.setBounds(109, 25, 45, 16);
		panel.add(jLabel_Year);
		
		jTextField_Year = new JTextField();
		jTextField_Year.setBounds(100, 43, 54, 26);
		panel.add(jTextField_Year);
		jTextField_Year.setColumns(10);
		
		JRadioButton jRadioButton_File = new JRadioButton("From File");
		jRadioButton_File.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panel.setVisible(false);
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
				panel.setVisible(true);
			}
		});
		buttonGroup.add(jRadioButton_UserInput);
		jRadioButton_UserInput.setBounds(21, 205, 98, 23);
		frame.getContentPane().add(jRadioButton_UserInput);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Drawing", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_1.setBackground(Color.WHITE);
		panel_1.setBounds(238, 15, 404, 295);
		frame.getContentPane().add(panel_1);

	}
}
