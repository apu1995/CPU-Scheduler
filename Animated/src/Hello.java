import java.awt.*;
import java.awt.event.*;
import java.io.*;

import javax.swing.*;

public class Hello extends JFrame
{
        public Hello() {
        super("Welcome Screen");       
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        Container c = getContentPane();
        c.setLayout(new FlowLayout());
        JLabel head =new JLabel("Welcome to CPU Scheduling World");

        head.setBounds(20, 30, 250, 50);
        add(head);
        ButtonGroup group = new ButtonGroup();
        JRadioButton bRadioButton = new JRadioButton("Click here to start");
         ActionListener sliceActionListener = new ActionListener() {
        public void actionPerformed(ActionEvent actionEvent) {
        	if(bRadioButton.isSelected())
	        {
	        	AlgAnime n= new AlgAnime();
	        	n.init();
	        }
          }
         };
    group.add(bRadioButton);
    add(bRadioButton);
   
    bRadioButton.setBounds(50,150,150,50);
    setSize(600,600);
    bRadioButton.addActionListener(sliceActionListener);
    setLayout(null);
    setSize(300, 300);
   	}

}


