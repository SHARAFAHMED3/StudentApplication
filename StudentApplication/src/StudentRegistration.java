import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Color;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;

import net.proteanit.sql.DbUtils;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.*; //importing all the classes from sql package

public class StudentRegistration {

	private JFrame frame;
	private JTextField txtRegNo;
	private JTextField txtName;
	private JTextField txtEmail;
	private JTable StudentTable;
	private JTextField txtId;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					StudentRegistration window = new StudentRegistration();
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
	public StudentRegistration() {
		initialize();
		Connect();
		table_load();
	}
	Connection con;
	PreparedStatement pst;
	ResultSet rs;
	public void Connect()
	{
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost/studentregistration","root","");
			}
		catch (ClassNotFoundException ex) 
		{
			ex.printStackTrace();
		}
		catch (SQLException ex) 
		{
			ex.printStackTrace();
		}
		
		
	}
	
	public void table_load()
	{
		try 
		{
			pst = con.prepareStatement("select * from student");
			rs = pst.executeQuery();
			StudentTable.setModel(DbUtils.resultSetToTableModel(rs));
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		} 
		
		
	}
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 873, 306);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Student Registration");
		lblNewLabel.setBounds(153, 11, 160, 19);
		lblNewLabel.setForeground(new Color(128, 0, 0));
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
		frame.getContentPane().add(lblNewLabel);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 64, 231, 118);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("Registration No");
		lblNewLabel_1.setBounds(10, 11, 92, 14);
		panel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("Name");
		lblNewLabel_1_1.setBounds(10, 47, 79, 14);
		panel.add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_1_1_1 = new JLabel("Email");
		lblNewLabel_1_1_1.setBounds(10, 82, 79, 14);
		panel.add(lblNewLabel_1_1_1);
		
		txtRegNo = new JTextField();
		txtRegNo.setBounds(103, 8, 118, 20);
		panel.add(txtRegNo);
		txtRegNo.setColumns(10);
		
		txtName = new JTextField();
		txtName.setColumns(10);
		txtName.setBounds(103, 44, 118, 20);
		panel.add(txtName);
		
		txtEmail = new JTextField();
		txtEmail.setColumns(10);
		txtEmail.setBounds(103, 79, 118, 20);
		panel.add(txtEmail);
		
		JButton btnNewButton = new JButton("Save");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String regno, name, email;
				regno=txtRegNo.getText();
				name=txtName.getText();
				email=txtEmail.getText();
				
				try {
					pst = con.prepareStatement("insert into student(registrationNo,name,email)values(?,?,?)");
					pst.setString(1,regno);
					pst.setString(2,name);
					pst.setString(3,email);
					pst.executeUpdate();
					JOptionPane.showMessageDialog(null, "Record Addedddd!!!!!");
   				table_load();
					//clearing the text fields
					txtRegNo.setText("");
					txtName.setText("");
					txtEmail.setText("");
					txtRegNo.requestFocus();
				}
				catch (SQLException e1) 
				{ 
					e1.printStackTrace();
				}
			}
		});
		btnNewButton.setBounds(10, 192, 89, 23);
		frame.getContentPane().add(btnNewButton);
		
		JButton btnClear = new JButton("Clear");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtRegNo.setText("");
				txtName.setText("");
				txtEmail.setText("");
				txtRegNo.requestFocus();
			}
		});
		btnClear.setBounds(153, 192, 89, 23);
		frame.getContentPane().add(btnClear);
		
		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnExit.setBounds(74, 226, 89, 23);
		frame.getContentPane().add(btnExit);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(460, 94, 387, 111);
		frame.getContentPane().add(scrollPane);
		
		StudentTable = new JTable();
		scrollPane.setViewportView(StudentTable);
		
		JButton btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String regno, name, email, id;
				regno=txtRegNo.getText();
				name=txtName.getText();
				email=txtEmail.getText();
				id=txtId.getText();			
				try {
					pst = con.prepareStatement("update Student set registrationno=?, name=?, email=? where id=? ");
					pst.setString(1, regno);
					pst.setString(2, name);
					pst.setString(3, email);
					pst.setString(4, id);
					pst.executeUpdate();
					JOptionPane.showMessageDialog(null, "Record updated!!");
					table_load();
					//clearing the text fields
					txtRegNo.setText("");
					txtName.setText("");
					txtEmail.setText("");
					txtRegNo.requestFocus();
				}
				catch (SQLException e1) 
				{ 
					e1.printStackTrace();
				}
			}
		});
		btnUpdate.setBounds(460, 226, 81, 23);
		frame.getContentPane().add(btnUpdate);
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
                String id;
				
				id=txtId.getText();			
				try {
					pst = con.prepareStatement("delete from student where id=? ");
					
					pst.setString(1, id);
					pst.executeUpdate();
					JOptionPane.showMessageDialog(null, "Record deleted!!");
					table_load();
					//clearing the text fields
					txtRegNo.setText("");
					txtName.setText("");
					txtEmail.setText("");
					txtRegNo.requestFocus();
				}
				catch (SQLException e1) 
				{ 
					e1.printStackTrace();
				}
			}
			
		});
		btnDelete.setBounds(766, 216, 81, 23);
		frame.getContentPane().add(btnDelete);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "Search", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(294, 27, 220, 42);
		frame.getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblNewLabel_2 = new JLabel("Student Id");
		lblNewLabel_2.setBounds(10, 20, 78, 14);
		panel_1.add(lblNewLabel_2);
		
		txtId = new JTextField();
		txtId.setColumns(10);
		txtId.setBounds(103, 14, 107, 20);
		panel_1.add(txtId);
		
		JButton btnNewButton_1 = new JButton("Search");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					
					String id=txtId.getText();
					pst=con.prepareStatement("Select RegistrationNo, Name, Email from student where id=?");
					pst.setString(1, id);
					ResultSet rs=pst.executeQuery();
					if (rs.next()==true)
					{
						String regNo=rs.getString(1);
						String name=rs.getString(2);
						String email=rs.getString(3);
						
						txtRegNo.setText(regNo);
						txtName.setText(name);
						txtEmail.setText(email);
						
					}
					
					else
					{
						txtRegNo.setText("");
						txtName.setText("");
						txtEmail.setText("");
						
					}
				}
				
				catch (SQLException ex){
					
					
				}
				
			}
		});
		btnNewButton_1.setBounds(524, 47, 94, 19);
		frame.getContentPane().add(btnNewButton_1);
	}
}
