import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class WeekFiveJavaLab {

	private JFrame frmCheckYourResult;
	private JTextField jTextField_ExamGrade;
	private JTextField jTextField_AssignGrade;
	private static int EXAM_PASS_MARK = 50;
	private static int ASSGT_PASS_MARK = 60;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WeekFiveJavaLab window = new WeekFiveJavaLab();
					window.frmCheckYourResult.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public WeekFiveJavaLab() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmCheckYourResult = new JFrame();
		frmCheckYourResult.setTitle("Check Your Result");
		frmCheckYourResult.setBounds(100, 100, 450, 300);
		frmCheckYourResult.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmCheckYourResult.getContentPane().setLayout(null);
		
		JLabel jLabel_ExamGrade = new JLabel("Exam Grade");
		jLabel_ExamGrade.setBounds(16, 45, 79, 16);
		frmCheckYourResult.getContentPane().add(jLabel_ExamGrade);
		
		JLabel jLabel_AssignGrade = new JLabel("Assig. Grade");
		jLabel_AssignGrade.setBounds(16, 73, 79, 16);
		frmCheckYourResult.getContentPane().add(jLabel_AssignGrade);
		
		jTextField_ExamGrade = new JTextField();
		jTextField_ExamGrade.setBounds(107, 40, 85, 26);
		frmCheckYourResult.getContentPane().add(jTextField_ExamGrade);
		jTextField_ExamGrade.setColumns(10);
		
		jTextField_AssignGrade = new JTextField();
		jTextField_AssignGrade.setBounds(107, 68, 85, 26);
		frmCheckYourResult.getContentPane().add(jTextField_AssignGrade);
		jTextField_AssignGrade.setColumns(10);
		
		JLabel jLabel_Result = new JLabel("");
		jLabel_Result.setBounds(114, 139, 78, 16);
		frmCheckYourResult.getContentPane().add(jLabel_Result);
		
		JButton jButton_Check = new JButton("Check");
		jButton_Check.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int iExam = Integer.parseInt(jTextField_ExamGrade.getText());
				int iAssgt = Integer.parseInt(jTextField_AssignGrade.getText());
				if((iExam >= EXAM_PASS_MARK) && (iAssgt >= ASSGT_PASS_MARK))
					jLabel_Result.setText("Pass");
				else
					jLabel_Result.setText("Fail");
			}
		});
		jButton_Check.setBounds(209, 68, 85, 29);
		frmCheckYourResult.getContentPane().add(jButton_Check);
		
		
	}
}
