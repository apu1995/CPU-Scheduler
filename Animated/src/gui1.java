import javax.swing.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
//import programs1.secondexample.ButtonHandler;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class gui1 {

	Connection con=null;
	Statement stmt;
	ResultSet rs;
	JFrame jf;

	JButton jb,insert;
	JRadioButton jbfirst,jblast,jbprev,jbnext,jrb1,jrb2,jrb3,jrb4;
	JTextField jtf1,jtf2,jtf3;
	JPanel jp1,jp2,jp3,jp4;
	JTable table;
	gui1()
	{}

	gui1(int n){
		jf = new JFrame("DashBoard");

		jb = new JButton("Insert into the file");


		jp1=new JPanel();

		jp1.add(jb);

		jp2=new JPanel();
		String[] columns = new String[] {
	            "Process Id", "Process Name", "Burst Time", "Arrival Time","Priority",
	        };


	        Object[][] data = new Object[n][6];

	        //create table with data
	        JTable table = new JTable(data, columns);
	        JScrollPane pane = new JScrollPane(table);
	        pane.setMinimumSize(new Dimension(1000, 200));
	        table.setBounds(10,300,800,600);
	        //table.setSize(800,600);
	        //table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
	        //add the table to the frame


	        jf.add(pane);
	    //    jf.add(insert);

	        jf.setTitle("Table Example");
	        //this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        //this.pack();
	        //this.setVisible(true);

		//jf.setLayout(new GridLayout(3, 2));

		BoxLayout boxLayout = new BoxLayout(jf.getContentPane(), BoxLayout.Y_AXIS); // top to bottom
	   jf.setLayout(boxLayout);

		//jf.setLayout(new BoxLayout());

		jf.add(jb);
		//jf.add(jp1);



		jf.setVisible(true);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setSize(600,600);
		jb.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				try {
				File file= new File("Table_full.txt");

					FileWriter fw= new FileWriter(file.getAbsoluteFile());
					BufferedWriter bw = new BufferedWriter(fw);

					for(int i=0;i<table.getRowCount();i++)
					{
						for(int j=0;j<table.getColumnCount();j++){
							bw.write((String)table.getModel().getValueAt(i, j));
							bw.write(" ");
							bw.append('\n');
						}
					bw.append('\n');
					bw.write("\n______\n");
					}
					bw.close();
					fw.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}


			}
		});



		jb.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				try {
				// this(BurstArrival) file stores burst time and arrival time
				File file1= new File("BurstArrival.txt");

					FileWriter fw1= new FileWriter(file1.getAbsoluteFile());
					BufferedWriter bw1 = new BufferedWriter(fw1);

					for(int i=0;i<table.getRowCount();i++)
					{


							bw1.write((String)table.getModel().getValueAt(i, 2));
							bw1.write(" ");
							bw1.write((String)table.getModel().getValueAt(i, 3));
							bw1.newLine();
							bw1.write(" ");
							bw1.append('\n');

					bw1.append('\n');
					//bw1.write("\n______\n");
					}
					bw1.close();
					fw1.close();

					//
					File file2= new File("ProcessId.txt");

					FileWriter fw2= new FileWriter(file2.getAbsoluteFile());
					BufferedWriter bw2 = new BufferedWriter(fw1);

					for(int i=0;i<table.getRowCount();i++)
					{

							bw2.write((String)table.getModel().getValueAt(i, 0));
							bw2.newLine();
					bw2.append('\n');
					//bw1.write("\n______\n");
					}
					bw2.close();
					fw2.close();
					//
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}


			}
		});
	}
	public static void main(String args[])
	{
		new gui1();
	}
}
